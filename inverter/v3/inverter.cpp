#include "inverter.h"
//#include "globalVar.h"

//9600 serial
SoftwareSerial inverterSerial (RX_Pin, TX_Pin);
// instantiate ModbusMaster object
ModbusMaster node;

void preTransmission() {
	digitalWrite(DE_RE_Pin, HIGH);
}

void postTransmission() {
	digitalWrite(DE_RE_Pin, LOW);
}

void inverterInit() {
	//for modbus node
	inverterSerial.begin(9600);
	node.preTransmission(preTransmission);
	node.postTransmission(postTransmission);
	//slave ID=1
	node.begin(1, inverterSerial);
	//enter recive mode
	pinMode(DE_RE_Pin, OUTPUT);
	digitalWrite(DE_RE_Pin, LOW);
}

void getDataInverter(float *dataGet, bool debugSerial=false) {
	dataGet[0]=READ_ERR; //defaut read error
	float sample=0;
	uint8_t result;
	uint32_t EAll=0x00000000;
	result= node.readInputRegisters(0,leng_get);
	if (result == node.ku8MBSuccess) {
		for (int i=0; i<leng_get; i++){
			switch(i) {
				case PV_V:
					sample = node.getResponseBuffer(i) * PV_V_Conf;
          dataGet[PV_V_Index]=sample;
					if(debugSerial) {
						Serial.println();
						Serial.print("PV Vol: "); 
						Serial.print(sample);
						Serial.print(" V");
					}
					break;
				case PV_A:
					sample = node.getResponseBuffer(i) * PV_A_Conf;
          dataGet[PV_A_Index]=sample;
					if(debugSerial) {
						Serial.println();
						Serial.print("PV Amp: ");
						Serial.print(sample);
						Serial.print(" A");
						break;
					}
				case BUS:
					sample = node.getResponseBuffer(i) * BUS_Conf;
          dataGet[BUS_Index]=sample;
					if(debugSerial) {
						Serial.println();
						Serial.print("BUS Vol: ");
						Serial.print(sample);
						Serial.print(" V");
						break;
					}
				case AC_V:
					sample = node.getResponseBuffer(i) * AC_V_Conf;
          dataGet[AC_V_Index]=sample;
					if(debugSerial) {
						Serial.println();
						Serial.print("AC Vol: ");
						Serial.print(sample);
						Serial.print(" V");
						break;
					}
				case AC_Hz:
					sample = node.getResponseBuffer(i) * AC_Hz_Conf;
          dataGet[AC_Hz_Index]=sample;
					if(debugSerial) {
						Serial.println();
						Serial.print("AC Hz: ");
						Serial.print(sample);
						Serial.print(" Hz");
						break;
					}
				case TEM:
					sample = node.getResponseBuffer(i) * TEM_Conf;
          dataGet[TEM_Index]=sample;
					if(debugSerial) {
						Serial.println();
						Serial.print("Temperature C: ");
						Serial.print(sample);
						Serial.print(" C");
						break;
					}
				case Pac:
					sample = node.getResponseBuffer(i) * Pac_Conf;
          dataGet[Pac_Index]=sample;
					if(debugSerial) {
						Serial.println();
						Serial.print("Pac W: ");
						Serial.print(sample);
						Serial.print(" W");
						break;
					}
				case EToday:
					sample = node.getResponseBuffer(i) * EToday_Conf;
          dataGet[EToday_Index]=sample;
					if(debugSerial) {
						Serial.println();
						Serial.print("EToday kWh: ");
						Serial.print(node.getResponseBuffer(i) * sample);
						Serial.print(" kWh");
						break;
					}
				case EAll_High:
					EAll |= ((uint32_t)node.getResponseBuffer(i))<<16;
					break;
				case EAll_Low:
					EAll |= ((uint32_t)node.getResponseBuffer(i));
					break;
			}
		}
		sample = EAll * EAll_Conf;
    dataGet[EAll_Index]=sample;
		if(debugSerial) {
			Serial.println();
			Serial.print("EAll kWh: ");
			Serial.print(sample);
			Serial.println(" kWh");
			Serial.println("---------------------------");
		}
	} else {
		if(debugSerial) {
			Serial.println("Read fail");
		}
	}
}
