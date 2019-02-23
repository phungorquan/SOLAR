#include <SoftwareSerial.h>
#include <ModbusMaster.h>

//6->DI 5->RO
#define TX_Pin 6
#define RX_Pin 5

#define DE_RE_Pin 4

//12 : PAC
  //0 : PV , 1 : Amp
  //6 : BUS , 10 do C
  // 13 : PAC
  // 7 : AC : 228 , 9 Mhz

  //(16 : Etoday maybe)
  //17 , 18 : 4bytes Eall
#define PV_V 0
#define PV_V_Conf 1.0

#define PV_A 1
#define PV_A_Conf 0.1

#define BUS 6
#define BUS_Conf 1.0

#define AC_V 7
#define AC_V_Conf 1.0

#define AC_Hz 9
#define AC_Hz_Conf 0.1

#define TEM 10
#define TEM_Conf 1.0

#define Pac 13 //13, 12
#define Pac_Conf 1.0

#define EToday 16
#define EToday_Conf 0.01

#define EAll_High 17
#define EAll_Low 18
#define EAll_Conf 0.01

#define leng_get 19

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
  uint32_t EAll=0x00000000;
  result= node.readInputRegisters(0,leng_get);
  if (result == node.ku8MBSuccess) {
    for (int i=0; i<leng_get; i++){
      switch(i) {
        case PV_V:
          Serial.println();
          Serial.print("PV Vol: "); 
          Serial.print(node.getResponseBuffer(i) * PV_V_Conf);
          Serial.print(" V");
          break;
        case PV_A:
          Serial.println();
          Serial.print("PV Amp: ");
          Serial.print(node.getResponseBuffer(i) * PV_A_Conf);
          Serial.print(" A");
          break;
        case BUS:
          Serial.println();
          Serial.print("BUS Vol: ");
          Serial.print(node.getResponseBuffer(i) * BUS_Conf);
          Serial.print(" V");
          break;
        case AC_V:
          Serial.println();
          Serial.print("AC Vol: ");
          Serial.print(node.getResponseBuffer(i) * AC_V_Conf);
          Serial.print(" V");
          break;
        case AC_Hz:
          Serial.println();
          Serial.print("AC Hz: ");
          Serial.print(node.getResponseBuffer(i) * AC_Hz_Conf);
          Serial.print(" Hz");
          break;
        case TEM:
          Serial.println();
          Serial.print("Temperature C: ");
          Serial.print(node.getResponseBuffer(i) * TEM_Conf);
          Serial.print(" C");
          break;
        case Pac:
          Serial.println();
          Serial.print("Pac W: ");
          Serial.print(node.getResponseBuffer(i) * Pac_Conf);
          Serial.print(" W");
          break;
        case EToday:
          Serial.println();
          Serial.print("EToday kWh: ");
          Serial.print(node.getResponseBuffer(i) * EToday_Conf);
          Serial.print(" kWh");
          break;
        case EAll_High:
          EAll |= ((uint32_t)node.getResponseBuffer(i))<<16;
          break;
        case EAll_Low:
          EAll |= ((uint32_t)node.getResponseBuffer(i));
          break;
      }
    }
    Serial.println();
    Serial.print("EAll kWh: ");
    Serial.print(EAll * EAll_Conf);
    Serial.println(" kWh");
    Serial.println("---------------------------");
  } else {
    Serial.println("Read fail");
  }
  delay(1000);
}
