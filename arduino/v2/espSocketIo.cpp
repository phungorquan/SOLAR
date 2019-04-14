#include "espSocketIo.h"
#include "globalVar.h"
#include <ESP8266WiFi.h>
#include <SocketIoClient.h>
#include <ArduinoJson.h>
#include <Crypto.h>
#include <base64.hpp>

SocketIoClient webSocket;

void(* resetFunc) (void) = 0;// reset function

void bufferSize(char* text, int &length)
{
  int i = strlen(text);
  int buf = round(i / BLOCK_SIZE) * BLOCK_SIZE;
  length = (buf <= i) ? buf + BLOCK_SIZE : length = buf;
}

//hàm handle sự kiện join room thành công do server gửi về
void join_success(const char * payload, size_t length) {
  joinedRoom = true;
}

void socketInit() {
	randomSeed(analogRead(A0));

	WiFi.begin(ssid, password);
	while (WiFi.status() != WL_CONNECTED) {
		delay(500);
	}

	webSocket.on("esp-join-success", join_success);
	webSocket.begin(Host_Socket, Port_Socket, "/socket.io/?transport=websocket");

	previousMillisSendCurrent=millis();
	previousMillisSendCollected=millis();
	previousMillisSendAut=millis();
	previousMillisTimeOutReset=millis();
}

DynamicJsonBuffer jsonBuffer;
String input =
      "{\"typeClient\":\"esp-client\",\"username\":\"ceec\",\"password\":[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\"NodeID\":\"ceec_0\",\"dataInvert\":[540.2,5.3,458.6,250,49.6,52,1356,2.66,158.8,1,0]}";
  JsonObject& root = jsonBuffer.parseObject(input);

bool convertPassToArray (char Pass[]) {
  /*
   * frontString [0->14] data, [15]-> random, [16]->end string char, 
   * endString [0->14] -> data, [15] -> random, [16] -> end string char
   */
  char frontString[17]={};
  char endString[17]={};
  frontString[15]=random(256); //random to encryption
  endString[15]=random(256);
  if (strlen(Pass) <= 30) {
    uint8_t i=0;
    for (i=0; Pass[i]; i++) {
      if(i<15) {
        frontString[i]=Pass[i];
      } else {
        endString[i-15]=Pass[i];
      }
    }
    for (; i<30; i++) {
      if(i<15) {
        frontString[i]='*';
      } else {
        endString[i-15]='*';
      }
    }
    frontString[16]=0x00; //end string
    endString[16]=0x00;
    //fill IV
    RNG::fill(iv, BLOCK_SIZE);
    for (i=0; i<16; i++) {
      root["password"][i+32]=iv[i];
    }
    // encrypt
    int length = 0;
    //frontstring first
    AES aesEncryptorFront(key, iv, AES::AES_MODE_128, AES::CIPHER_ENCRYPT);
    bufferSize(frontString, length);
    byte encryptedFront[length];
    aesEncryptorFront.process((uint8_t*)frontString, encryptedFront, length);
    for(i=0; i<16; i++) {
      //Serial.print(encryptedFront[i],HEX);
      root["password"][i]=encryptedFront[i];
    }
    
    //endstring
    AES aesEncryptorEnd(key, iv, AES::AES_MODE_128, AES::CIPHER_ENCRYPT);
    bufferSize(endString, length);
    byte encryptedEnd[length];
    aesEncryptorEnd.process((uint8_t*)endString, encryptedEnd, length);
    for(i=0; i<16; i++) {
      //Serial.print(encryptedEnd[i],HEX);
      root["password"][i+16]=encryptedEnd[i];
    }
    return true;
  } else {
    return false;
  }
}

void parseInputInverter (float pv_v,float pv_a,float bus,float ac_v,float ac_hz,float tem,float pac,float etoday,float eall, byte statusConnect) {
  root["dataInvert"][0]=pv_v;
  root["dataInvert"][1]=pv_a;
  root["dataInvert"][2]=bus;
  root["dataInvert"][3]=ac_v;
  root["dataInvert"][4]=ac_hz;
  root["dataInvert"][5]=tem;
  root["dataInvert"][6]=pac;
  root["dataInvert"][7]=etoday;
  root["dataInvert"][8]=eall;
  root["dataInvert"][9]=statusConnect;
}

void espSocketIoLoop() {
  if ((millis() - previousMillisSendCurrent > intervalSendCurrent) && joinedRoom) {
      convertPassToArray(Pass);
      parseInputInverter(random(256),random(256),random(256),random(256),random(256),random(256),random(256),random(256),random(256),1);
      root["dataInvert"][10]=0; //0 -> send current data
      String output="";
      root.printTo(output);
      int i = 0;
      for (i=0; output[i]; i++) {
        sendSocketString[i]=output[i];
      }
      sendSocketString[i]='\0';
      webSocket.emit("esp_send_data", sendSocketString);
      previousMillisSendCurrent = millis();
  }
  if ((millis() - previousMillisSendCollected > intervalSendCollected) && joinedRoom) {
      convertPassToArray(Pass);
      parseInputInverter(random(256),random(256),random(256),random(256),random(256),random(256),random(256),random(256),random(256),1);
      root["dataInvert"][10]=1; //1 -> send collected data
      String output="";
      root.printTo(output);
      int i = 0;
      for (i=0; output[i]; i++) {
        sendSocketString[i]=output[i];
      }
      sendSocketString[i]='\0';
      webSocket.emit("esp_send_data", sendSocketString);
      previousMillisSendCollected = millis();
  }
  if (millis() - previousMillisTimeOutReset > timeOutReset) {
    resetFunc();
  }
  //Kết nối lại!
  if ((millis() - previousMillisSendAut > intervalSendAut) && !joinedRoom) {
    convertPassToArray(Pass);
    String output="";
    root.printTo(output);
    int i = 0;
    for (i=0; output[i]; i++) {
      sendSocketString[i]=output[i];
    }
    sendSocketString[i]='\0';
    webSocket.emit("authentication", sendSocketString);
    Serial.println(sendSocketString);
    previousMillisSendAut=millis();
  }
  
  webSocket.loop();

  if(!webSocket.StatusConnectSocket) {
    resetFunc();
  }
}
