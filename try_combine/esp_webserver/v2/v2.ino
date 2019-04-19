#include "espWebServer.h"

void setup() {
  Serial.begin(9600);
  espWebServerInit();
}

void loop() {
  espWebServerLoop();
}
