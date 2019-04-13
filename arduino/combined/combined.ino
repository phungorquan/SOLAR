#include <ESP8266WiFi.h>
#include <SocketIoClient.h>
#include <SoftwareSerial.h>
#include <ModbusMaster.h>
#include <ArduinoJson.h>
#include <Crypto.h>
#include <base64.hpp>

//encryption================================
#define BLOCK_SIZE 16

uint8_t key[BLOCK_SIZE] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16 };
uint8_t iv[BLOCK_SIZE] = { 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34,35, 36 };
 

void bufferSize(char* text, int &length)
{
  int i = strlen(text);
  int buf = round(i / BLOCK_SIZE) * BLOCK_SIZE;
  length = (buf <= i) ? buf + BLOCK_SIZE : length = buf;
}
void myDelay(int x) {
  for (;x;x--)
    delay(1);
}
bool convertPassToArray (char Pass[]);
//==================================Giao tiep voi server va ket noi wifi======================================
SocketIoClient webSocket;
const char* ssid = "Baby I'm unreal";  
const char* password = "417417417";  
     
const char* Host_Socket = "ceecsolarsystem.herokuapp.com";
unsigned int Port_Socket = 80;

//const char* Host_Socket = "192.168.0.108";
//unsigned int Port_Socket = 3484;

unsigned long previousMillisSendAut = 0;
unsigned long intervalSendAut = 4000;

unsigned long previousMillisSendCurrent = 0;
unsigned long previousMillisSendCollected = 0;
unsigned long intervalSendCurrent = 10000;
unsigned long intervalSendCollected = 1800000;

//json object
  DynamicJsonBuffer jsonBuffer;
  //define data array index
  /*
   * PV_Vol 0
   * PV_Amp 1
   * Bus 2
   * AC_Vol 3
   * AC_Hz 4
   * Tem 5
   * Pac 6
   * EToday 7
   * EAll 8
   * StatusConnect 9
   * current or collected 10
   */
  char Pass[]="ce.uit.edu.vn";
  String input =
      "{\"typeClient\":\"esp-client\",\"username\":\"ceec\",\"password\":[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\"NodeID\":\"ceec_0\",\"dataInvert\":[540.2,5.3,458.6,250,49.6,52,1356,2.66,158.8,1,0]}";
  JsonObject& root = jsonBuffer.parseObject(input);
void parseInputInverter (float pv_v,float pv_a,float bus,float ac_v,float ac_hz,float tem,float pac,float etoday,float eall, byte statusConnect);

String output;
//==================================================Dung cho giao tiep voi inverter=======================================

//6->DI 5->RO
#define TX_Pin 6
#define RX_Pin 5

#define DE_RE_Pin 4

//12 : PAC
  //0 : PV , 1 : Amp
  //6 : BUS , 10 do C
  // 13 : PAC
  // 7 : AC : 228 , 9 Mhz

  //(16 : Etoday maybe)
  //17 , 18 : 4bytes Eall
#define PV_V 0
#define PV_V_Conf 1.0

#define PV_A 1
#define PV_A_Conf 0.1

#define BUS 6
#define BUS_Conf 1.0

#define AC_V 7
#define AC_V_Conf 1.0

#define AC_Hz 9
#define AC_Hz_Conf 0.1

#define TEM 10
#define TEM_Conf 1.0

#define Pac 13 //13, 12
#define Pac_Conf 1.0

#define EToday 16
#define EToday_Conf 0.01

#define EAll_High 17
#define EAll_Low 18
#define EAll_Conf 0.01

#define leng_get 19

//9600 serial
SoftwareSerial mySerial (RX_Pin, TX_Pin);
// instantiate ModbusMaster object
ModbusMaster node;

void preTransmission() {
  digitalWrite(DE_RE_Pin, HIGH);
}

void postTransmission() {
  digitalWrite(DE_RE_Pin, LOW);
}

//thời gian reset lại
unsigned long time_out; //time out reset

void(* resetFunc) (void) = 0;// reset function

bool joinedRoom = false;
//hàm handle sự kiện join room thành công do server gửi về
void join_success(const char * payload, size_t length) {
  joinedRoom = true;
  Serial.printf("join success");
}

void setup() {
  Serial.begin(115200);
  pinMode(2, OUTPUT);
  digitalWrite(2, HIGH);
  Serial.setDebugOutput(true);
  for(uint8_t t = 4; t > 0; t--) {
        Serial.printf("[SETUP] BOOT WAIT %d...\n", t);
        Serial.flush();
        delay(500);
  }
  Serial.println();
  //-------------encryption----------------
  randomSeed(analogRead(A0));
  //wifi
  Serial.print("Ket noi vao mang ");
  Serial.println(ssid);
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
      delay(500);
      Serial.print('.');
  }
  Serial.println();
  Serial.println(F("Da ket noi WiFi"));
  Serial.println(F("Di chi IP cua ESP8266 (Socket Client ESP8266): "));
  Serial.println(WiFi.localIP());
     
  webSocket.on("esp-join-success", join_success);

  webSocket.begin(Host_Socket, Port_Socket, "/socket.io/?transport=websocket");
  
  /*
  //for modbus node
  mySerial.begin(9600);
  delay(1);
  node.preTransmission(preTransmission);
  node.postTransmission(postTransmission);
  //slave ID=1
  node.begin(1, mySerial);
  //enter recive mode
  pinMode(DE_RE_Pin, OUTPUT);
  digitalWrite(DE_RE_Pin, LOW);
  delay(1);
  */
  previousMillisSendCurrent=millis();
  previousMillisSendCollected=millis();
  previousMillisSendAut=millis();
}

char sendSocketString[400] = {};

void loop() {
  //tạo một task cứ sau "interval" giây thì chạy lệnh:
  if ((millis() - previousMillisSendCurrent > intervalSendCurrent) && joinedRoom) {
      Serial.println("send current data");
      digitalWrite(2, !digitalRead(2));
      output="";
      convertPassToArray(Pass);
      parseInputInverter(random(256),random(256),random(256),random(256),random(256),random(256),random(256),random(256),random(256),1);
      root["dataInvert"][10]=0; //0 -> send current data
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
      Serial.println("send collected data");
      output="";
      convertPassToArray(Pass);
      parseInputInverter(random(256),random(256),random(256),random(256),random(256),random(256),random(256),random(256),random(256),1);
      root["dataInvert"][10]=1; //1 -> send collected data
      root.printTo(output);
      int i = 0;
      for (i=0; output[i]; i++) {
        sendSocketString[i]=output[i];
      }
      sendSocketString[i]='\0';
      webSocket.emit("esp_send_data", sendSocketString);
      previousMillisSendCollected = millis();
  }
  if (millis() - previousMillisSendCollected > intervalSendCollected+15000) {
    Serial.println("Reset");
    resetFunc();
  }
  //Kết nối lại!
  if ((millis() - previousMillisSendAut > intervalSendAut) && !joinedRoom) {
    Serial.println("try to authenticate");
    convertPassToArray(Pass);
    output="";
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

bool convertPassToArray (char Pass[]) {
  /*
   * frontString [0->14] data, [15]-> random, [16]->end string char, 
   * endString [0->14] -> data, [15] -> random, [16] -> end string char
   */
  char frontString[17]={};
  char endString[17]={};
  frontString[15]=random(256); //random to encryption
  endString[15]=random(256);
  uint8_t de = random(50);
  myDelay(de);
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
