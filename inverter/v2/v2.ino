#include <SoftwareSerial.h>
#include <ModbusMaster.h>

//6->DI 5->RO
#define TX_Pin 6
#define RX_Pin 5

#define DE_RE_Pin 4

//9600 serial
SoftwareSerial mySerial (RX_Pin, TX_Pin);
// instantiate ModbusMaster object
ModbusMaster node;

void preTransmission() {
  digitalWrite(DE_RE_Pin, HIGH);
}

void postTransmission() {
  digitalWrite(DE_RE_Pin, LOW);
}

void setup() {
  Serial.begin(9600);
  
  //for modbus node
  mySerial.begin(9600);
  node.preTransmission(preTransmission);
  node.postTransmission(postTransmission);
  //slave ID=1
  node.begin(1, mySerial);
  //enter recive mode
  pinMode(DE_RE_Pin, OUTPUT);
  digitalWrite(DE_RE_Pin, LOW);
}

void loop() {
  uint8_t result;
  /*
  //read input reg -> function code = 0x04
  //page 14, address 42, 1 reg -> active power (kw 0.1)
  result= node.readInputRegisters(42, 1);
  if (result == node.ku8MBSuccess) {
    Serial.print("Active Power: ");
    Serial.println(node.getResponseBuffer(0)*0.1);
  } else {
    Serial.println("Read active power fail");
  }
  */
  result= node.readInputRegisters(5,15);
  if (result == node.ku8MBSuccess) {
    for (int i=0; i<15; i++){
      Serial.println();
      Serial.print(i);
      Serial.print(": ");
      Serial.println(node.getResponseBuffer(i));
    }
  } else {
    Serial.println("Read fail");
  }
  //12 : PAC 
  //0 : PV , 1 : Amp
  //6 : BUS , 10 do C
  // 13 : PAC
  // 7 : AC : 228 , 9 Mhz

// 15 -> 20 : PV information   (16 : Etoday maybe)
  //17 , 18 : 4bytes Eall
  /*
  //read holding reg -> function code = 0x03
  //page 17, address 0x6004, 1 reg -> baudrate, (by default) 0x03->9600
  result= node.readHoldingRegisters(0x02, 1);
  if (result == node.ku8MBSuccess) {
    Serial.print("Baud rate: ");
    Serial.println(node.getResponseBuffer(0));
  } else {
    Serial.println("Read baudrate fail");
  }
  */
  delay(1000);
}
