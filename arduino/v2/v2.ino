#include "espSocketIo.h"
void setup() {
  Serial.begin(115200);
  socketInit();
}

void loop() {
  espSocketIoLoop();
}
