#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <ArduinoJson.h>
const char* ssid = "UIT Public";
const char* password = "";

/*---------------------send data and json------------------*/
/*$Receive_Data =
{
    "Field":
    {
        "Field1":   ,
        "Field2":   ,
        "Field3":
    },
    "Current":
    {
        "Voltage":
        "Intensity":
        "Status":
    }
    "User":
    {
        "ID":
        "Pass":
    }
}
*/
void Encode_Json(int Field1, int Field2, int Field3, int Voltage, float Intensity, bool Status, String ID, String Pass);
String Send_Data;
void setup(void)
{
  // Start Serial
  Serial.begin(115200);
  // Connect to WiFi
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
  delay(500);
  Serial.print(".");
  }
  Serial.println("");
  Serial.println("WiFi connected");
  // Print the IP address
  Serial.println(WiFi.localIP());
}

void loop() {
  Encode_Json(10,20,30,220,2.1,1,String("ID001"),String("123456"));
  HTTPClient http;
  http.begin("http://lee-ceec.000webhostapp.com/solar/Receive_From_Esp.php");
  http.addHeader("Content-Type", "application/x-www-form-urlencoded");
  int httpCode=http.POST(String("Json_Data=")+Send_Data);
  String payload=http.getString();
  http.end();
  //if send successful
  if(httpCode==200) {
    Serial.println(payload);
  }
  else {
    Serial.println("fail");
  }
  delay(1000);
}

void Encode_Json(int Field1, int Field2, int Field3, int Voltage, float Intensity, bool Status, String ID, String Pass) {
  Send_Data=String("{");
  
  Send_Data+=String("\"Field\":{");
  Send_Data+=String("\"Field1\":");
  Send_Data+=Field1;
  Send_Data+=String(",");
  Send_Data+=String("\"Field2\":");
  Send_Data+=Field2;
  Send_Data+=String(",");
  Send_Data+=String("\"Field3\":");
  Send_Data+=Field3;
  Send_Data+=String("},");

  Send_Data+=String("\"Current\":{");
  Send_Data+=String("\"Voltage\":");
  Send_Data+=Voltage;
  Send_Data+=String(",");
  Send_Data+=String("\"Intensity\":");
  Send_Data+=Intensity;
  Send_Data+=String(",");
  Send_Data+=String("\"Status\":");
  Send_Data+=Status;
  Send_Data+=String("},");

  Send_Data+=String("\"User\":{");
  Send_Data+=String("\"ID\":");
  Send_Data+=String('"');
  Send_Data+=ID;
  Send_Data+=String('"');
  Send_Data+=String(",");
  Send_Data+=String("\"Pass\":");
  Send_Data+=String('"');
  Send_Data+=Pass;
  Send_Data+=String('"');
  Send_Data+=String("}");
  
  Send_Data+=String("}");
}

