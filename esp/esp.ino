#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <Ticker.h>

const char* ssid = "UIT Public";//"Baby I'm unreal";
const char* password = "";//"417417417";

String ID_Solar = "CEEC-Solar";
String Pass_Solar = "ce.uit.edu.vn";

const char* Host_Send_Data = "http://lee-ceec.000webhostapp.com/solar/Process_Data/Receive_From_Esp.php";

/*
 * This ticker will jump into Enable_Send_Collected and enable Flag ready to send collected data 1time/1minute
*/
#define TIME_SEND_COLLECTED 600 //10 minutes
Ticker Ticker_Send_Collected;
bool Send_Collected_Flag=false;
void Enable_Send_Collected(void);
void Encode_Json_Collected(float Field1, float Field2, float Field3, String ID, String Pass);
String Collected_Send_Data="";
void Send_Collected_Data(void);

/*
 * Send continuously current data to server in loop()
*/
void Encode_Json_Current(float Voltage, float Intensity, float Wat, bool Status_Connect, String ID, String Pass);
String Current_Send_Data="";
void Send_Current_Data(void);

/*---------------------send data and json------------------*/
/*$Current_Data =
{
    "Current":
    {
        "Voltage":  ,
        "Intensity":  ,
        "Wat":  ,
        "Status_Connect":
    },
    "User":
    {
        "ID":  ,
        "Pass":  ,
    }
}
*/
/*$Collected_Data =
{
    "Field":
    {
        "Field1":   ,
        "Field2":   ,
        "Field3":
    },
    "User":
    {
        "ID":  ,
        "Pass":  ,
    }
}
*/
void setup(void) {
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

  Ticker_Send_Collected.attach(TIME_SEND_COLLECTED,Enable_Send_Collected);
}

void loop() {
  Serial.println("Sending current data:");
  Send_Current_Data();
  if(Send_Collected_Flag) {
    Serial.println("Sending collected data:");
    Send_Collected_Data();
    Send_Collected_Flag=false;
  }
}

void Enable_Send_Collected(void) {
  Send_Collected_Flag=true;
}
void Encode_Json_Collected(float Field1, float Field2, float Field3, String ID, String Pass) {
  Collected_Send_Data=String("{");
  
  Collected_Send_Data+=String("\"Field\":{");
  Collected_Send_Data+=String("\"Field1\":");
  Collected_Send_Data+=Field1;
  Collected_Send_Data+=String(",");
  Collected_Send_Data+=String("\"Field2\":");
  Collected_Send_Data+=Field2;
  Collected_Send_Data+=String(",");
  Collected_Send_Data+=String("\"Field3\":");
  Collected_Send_Data+=Field3;
  Collected_Send_Data+=String("},");

  Collected_Send_Data+=String("\"User\":{");
  Collected_Send_Data+=String("\"ID\":");
  Collected_Send_Data+=String('"');
  Collected_Send_Data+=ID;
  Collected_Send_Data+=String('"');
  Collected_Send_Data+=String(",");
  Collected_Send_Data+=String("\"Pass\":");
  Collected_Send_Data+=String('"');
  Collected_Send_Data+=Pass;
  Collected_Send_Data+=String('"');
  Collected_Send_Data+=String("}");
  
  Collected_Send_Data+=String("}");
}
void Send_Collected_Data(void) {
  Encode_Json_Collected((float)random(501),(float)random(501),(float)random(501),ID_Solar,Pass_Solar);
  HTTPClient http;
  http.begin(Host_Send_Data);
  http.addHeader("Content-Type", "application/x-www-form-urlencoded");
  int httpCode=http.POST(String("Collected=")+Collected_Send_Data);
  String payload=http.getString();
  http.end();
  //if send successful
  if(httpCode==200) {
    Serial.println(payload);
  }
  else {
    Serial.println("fail");
  }
}

void Encode_Json_Current(float Voltage, float Intensity, float Wat, bool Status_Connect, String ID, String Pass) {
  Current_Send_Data=String("{");

  Current_Send_Data+=String("\"Current\":{");
  Current_Send_Data+=String("\"Voltage\":");
  Current_Send_Data+=Voltage;
  Current_Send_Data+=String(",");
  Current_Send_Data+=String("\"Intensity\":");
  Current_Send_Data+=Intensity;
  Current_Send_Data+=String(",");
  Current_Send_Data+=String("\"Wat\":");
  Current_Send_Data+=Wat;
  Current_Send_Data+=String(",");
  Current_Send_Data+=String("\"Status_Connect\":");
  Current_Send_Data+=Status_Connect;
  Current_Send_Data+=String("},");

  Current_Send_Data+=String("\"User\":{");
  Current_Send_Data+=String("\"ID\":");
  Current_Send_Data+=String('"');
  Current_Send_Data+=ID;
  Current_Send_Data+=String('"');
  Current_Send_Data+=String(",");
  Current_Send_Data+=String("\"Pass\":");
  Current_Send_Data+=String('"');
  Current_Send_Data+=Pass;
  Current_Send_Data+=String('"');
  Current_Send_Data+=String("}");
  
  Current_Send_Data+=String("}");
}
void Send_Current_Data(void) {
  Encode_Json_Current((float)random(501),(float)random(50),(float)random(5000),1,ID_Solar,Pass_Solar);
  HTTPClient http;
  http.begin(Host_Send_Data);
  http.addHeader("Content-Type", "application/x-www-form-urlencoded");
  int httpCode=http.POST(String("Current=")+Current_Send_Data);
  String payload=http.getString();
  http.end();
  //if send successful
  if(httpCode==200) {
    Serial.println(payload);
  }
  else {
    Serial.println("fail");
  }
}

