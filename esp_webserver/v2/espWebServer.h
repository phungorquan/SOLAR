#ifndef ESP_WEB_SERVER_H
#define ESP_WEB_SERVER_H

#include <ESP8266WiFi.h>
#include <ESP8266WebServer.h>
#include <ArduinoJson.h>
#include "index.h"
#include <WiFiClient.h>

void handleRoot();
void handleCurrent();
void espWebServerInit();
void espWebServerLoop();

#endif //ESP_WEB_SERVER_H
