#include "espSocketIo.h"
#include "inverter.h"
#include <ESP8266WiFi.h>
#include <SocketIoClient.h>
#include <ArduinoJson.h>
#include <Crypto.h>
#include <base64.hpp>

//=====================================================================
//                              user define
//=====================================================================
bool joinedRoom = false;


//=====================================================================
//                              encryption
//=====================================================================
#define BLOCK_SIZE 16

static uint8_t key[BLOCK_SIZE] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16 };
static uint8_t iv[BLOCK_SIZE] = { 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34,35, 36 };

static char Pass[]="ce.uit.edu.vn";

//=====================================================================
//                              wifi and socket
//=====================================================================
const char* ssid = "TP-LINK_49C732";
const char* password = "41748435";
     
const char* Host_Socket = "ceecsolarsystem.herokuapp.com";
unsigned int Port_Socket = 80;

static char sendSocketString[400] = {};

//const char* Host_Socket = "192.168.0.108";
//unsigned int Port_Socket = 3484;

//=====================================================================
//                              time
//=====================================================================

static unsigned long previousMillisSendAut = 0;
#define intervalSendAut 4000

static unsigned long previousMillisSendCurrent = 0;
static unsigned long previousMillisSendCollected = 0;
#define intervalSendCurrent 10000
#define intervalSendCollected 1233000

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
	WiFi.begin(ssid, password);
  Serial.println("connect wifi");
	while (WiFi.status() != WL_CONNECTED) {
		delay(500);
    Serial.print('.');
	}

	webSocket.on("esp-join-success", join_success);
	webSocket.begin(Host_Socket, Port_Socket, "/socket.io/?transport=websocket");

	previousMillisSendCurrent=millis();
	previousMillisSendCollected=millis();
	previousMillisSendAut=millis();
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
      parseInputInverter(dataRes[0],dataRes[1],dataRes[2],dataRes[3],dataRes[4],dataRes[5],dataRes[6],dataRes[7],dataRes[8],dataRes[9]);
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
      if(dataRes[9]==1) {
        for (int i=0; i<8; i++) {
          dataRes_low[i] = 99999;
          dataRes_high[i] = 0;
        }
        dataRes[9]=0;
      }
  }
  if ((millis() - previousMillisSendCollected > intervalSendCollected) && joinedRoom) {
      convertPassToArray(Pass);
      parseInputInverter(dataRes[0],dataRes[1],dataRes[2],dataRes[3],dataRes[4],dataRes[5],dataRes[6],dataRes[7],dataRes[8],dataRes[9]);
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
      if(dataRes[9]==1) {
        for (int i=0; i<8; i++) {
          dataRes_low[i] = 99999;
          dataRes_high[i] = 0;
        }
        dataRes[9]=0;
      }
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
    previousMillisSendAut=millis();
  }
  if(!webSocket.StatusConnectSocket) {
    webSocket.disconnect();
    delay(500);
    joinedRoom=false;
    webSocket.begin(Host_Socket, Port_Socket, "/socket.io/?transport=websocket");
    webSocket.StatusConnectSocket=true;
    delay(500);
  }
  webSocket.loop();
}
