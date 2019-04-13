#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include <ESP8266WebServer.h>

#include <ArduinoJson.h>

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
  String input =
      "{\"PV_Vol\": 0, \"PV_Amp\": 0, \"Bus\": 0, \"AC_Vol\": 0, \"AC_Hz\": 0, \"Tem\": 0, \"Pac\": 0, \"EToday\": 0, \"EAll\": 0, \"StatusConnect\": 1}";
  JsonObject& root = jsonBuffer.parseObject(input);
 
#include "index2.h" //Our HTML webpage contents with javascripts
 
#define LED 2  //On board LED
 
//SSID and Password of your WiFi router
const char* ssid = "esp";
const char* password = "417417417";
 
ESP8266WebServer server(80); //Server on port 80
 
//===============================================================
// This routine is executed when you open its IP in browser
//===============================================================
void handleRoot() {
 String s = MAIN_page; //Read HTML contents
 server.send(200, "text/html", s); //Send web page
}

void parseJson(void) {
  root["PV_Vol"]=random(300);
  root["PV_Amp"]=random(300);
  root["Bus"]=random(300);
  root["AC_Vol"]=random(300);
  root["AC_Hz"]=random(300);
  root["Tem"]=random(300);
  root["Pac"]=random(300);
  root["EToday"]=random(300);
  root["EAll"]=random(300);
}
 
void handleCurrent() {
 parseJson();
 String output;
 root.printTo(output);
 digitalWrite(LED,!digitalRead(LED)); //Toggle LED on data request ajax
 server.send(200, "text/plane", output); //Send ADC value only to client ajax request
}

IPAddress local_IP(192,168,4,22);
IPAddress gateway(192,168,4,9);
IPAddress subnet(255,255,255,0);


//==============================================================
//                  SETUP
//==============================================================

void setup() {
  Serial.begin(115200);
  randomSeed(analogRead(0));
  pinMode(LED, OUTPUT);
  Serial.println();
  Serial.print("Setting soft-AP configuration ... ");
  Serial.println(WiFi.softAPConfig(local_IP, gateway, subnet) ? "Ready" : "Failed!");

  Serial.print("Setting soft-AP ... ");
  Serial.println(WiFi.softAP(ssid,password) ? "Ready" : "Failed!");

  Serial.print("Soft-AP IP address = ");
  Serial.println(WiFi.softAPIP());
 
  server.on("/", handleRoot);      //Which routine to handle at root location. This is display page
  server.on("/current", handleCurrent); //This page is called by java Script AJAX
 
  server.begin();                  //Start server
  Serial.println("HTTP server started");
}

//==============================================================
//                     LOOP
//==============================================================
void loop(void){
  server.handleClient();          //Handle client requests
}
