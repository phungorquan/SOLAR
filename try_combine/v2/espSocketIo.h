#ifndef ESP_SOCKET_IO_H
#define ESP_SOCKET_IO_H

#include <ESP8266WiFi.h>

void bufferSize(char* text, int &length);
bool convertPassToArray (char Pass[]);
void socketInit();
void parseInputInverter (float pv_v,float pv_a,float bus,float ac_v,float ac_hz,float tem,float pac,float etoday,float eall, byte statusConnect);
void espSocketIoLoop();
#endif //ESP_SOCKET_IO_H
