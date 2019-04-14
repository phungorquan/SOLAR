#include "espWebServer.h"
#include "globalVar.h"

ESP8266WebServer espWebServer(80); //Server on port 80


//SSID and Password of your WiFi router
const char* ssid_espWebServer = "esp";
const char* password_espWebServer = "417417417";

IPAddress local_IP(192,168,4,22);
IPAddress gateway(192,168,4,9);
IPAddress subnet(255,255,255,0);

void handleRoot() {
	String s = MAIN_page; //Read HTML contents
	espWebServer.send(200, "text/html", s); //Send web page
}

void handleCurrent() {
	DynamicJsonBuffer jsonBuffer;
	String input = "{\"PV_Vol\": 0, \"PV_Amp\": 0, \"Bus\": 0, \"AC_Vol\": 0, \"AC_Hz\": 0, \"Tem\": 0, \"Pac\": 0, \"EToday\": 0, \"EAll\": 0, \"StatusConnect\": 1}";
	JsonObject& root = jsonBuffer.parseObject(input);
	root["PV_Vol"]=dataRes[0]++;
	root["PV_Amp"]=dataRes[1];
	root["Bus"]=dataRes[2];
	root["AC_Vol"]=dataRes[3];
	root["AC_Hz"]=dataRes[4];
	root["Tem"]=dataRes[5];
	root["Pac"]=dataRes[6];
	root["EToday"]=dataRes[7];
	root["EAll"]=dataRes[8];

	String output;
 	root.printTo(output);
 	espWebServer.send(200, "text/plane", output);
}

void espWebServerInit() {
	WiFi.softAPConfig(local_IP, gateway, subnet);
	WiFi.softAP(ssid_espWebServer,password_espWebServer);
	espWebServer.on("/", handleRoot);      //Which routine to handle at root location. This is display page
  espWebServer.on("/current", handleCurrent); //This page is called by java Script AJAX
  espWebServer.begin();
}

void espWebServerLoop() {
  espWebServer.handleClient();          //Handle client requests
}
