#include "espSocketIo.h"
#include "espWebServer.h"
#include "inverter.h"

float dataRes[10] = {};
float dataRes_low[9] = {99999,99999,99999,99999,99999,99999,99999,99999,99999};
float dataRes_high[9] = {};

#define timeOutConnect 3000 //3s
unsigned long timeCheckConnect;
#define timeDistanceGetDataInverter 1333 //0.5s
unsigned long timeGetDataInverter;

void setup() {
  Serial.begin(115200);
  socketInit();
  espWebServerInit();
  inverterInit();
  delay(2000);
  timeCheckConnect = millis();
  timeGetDataInverter = millis();
  Serial.println("init done");
}

void loop() {
  if (millis() - timeGetDataInverter >= timeDistanceGetDataInverter){
    timeGetDataInverter = millis();
    if(getDataInverter(true)) { //lay duoc data
      timeCheckConnect = millis();
      dataRes[9] = 1; //status connect = connected
    }
  }
  if (dataRes[9] == 1) {
    if(millis() - timeCheckConnect >= timeOutConnect) {
      dataRes[9] = 0; //status connect = disconnected
    }
  }
  espSocketIoLoop();
  espWebServerLoop();
}
