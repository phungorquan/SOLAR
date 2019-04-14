#include "espSocketIo.h"
void setup() {
  Serial.begin(9600);
  socketInit();
}

void loop() {
  espSocketIoLoop();
}
