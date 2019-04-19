#include "espSocketIo.h"
#include "espWebServer.h"

void setup() {
  Serial.begin(115200);
  socketInit();
  espWebServerInit();
}

void loop() {
  espSocketIoLoop();
  espWebServerLoop();
}
