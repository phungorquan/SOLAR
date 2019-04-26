#include "inverter.h"

#include <SoftwareSerial.h>
#include <ModbusMaster.h>

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

bool getDataInverter(bool debugSerial=false) {
	float sample=0;
	uint8_t result;
	uint32_t EAll=0x00000000;
	result= node.readInputRegisters(0,leng_get);
	if (result == node.ku8MBSuccess) {
		for (int i=0; i<leng_get; i++){
			switch(i) {
				case PV_V:
					sample = node.getResponseBuffer(i) * PV_V_Conf;
          if(sample < dataRes_low[PV_V_Index]) dataRes_low[PV_V_Index]=sample;
          if(sample > dataRes_high[PV_V_Index]) dataRes_high[PV_V_Index]=sample;
          dataRes[PV_V_Index] = (dataRes_high[PV_V_Index] + dataRes_low[PV_V_Index])/2;
          
					if(debugSerial) {
						Serial.println();
						Serial.print("PV Vol: "); 
						Serial.print(sample);
						Serial.print(" V");
					}
					break;
				case PV_A:
					sample = node.getResponseBuffer(i) * PV_A_Conf;
          if(sample < dataRes_low[PV_A_Index]) dataRes_low[PV_A_Index]=sample;
          if(sample > dataRes_high[PV_A_Index]) dataRes_high[PV_A_Index]=sample;
          dataRes[PV_A_Index] = (dataRes_high[PV_A_Index] + dataRes_low[PV_A_Index])/2;
          
					if(debugSerial) {
						Serial.println();
						Serial.print("PV Amp: ");
						Serial.print(sample);
						Serial.print(" A");
						break;
					}
				case BUS:
					sample = node.getResponseBuffer(i) * BUS_Conf;
          if(sample < dataRes_low[BUS_Index]) dataRes_low[BUS_Index]=sample;
          if(sample > dataRes_high[BUS_Index]) dataRes_high[BUS_Index]=sample;
          dataRes[BUS_Index] = (dataRes_high[BUS_Index] + dataRes_low[BUS_Index])/2;
          
					if(debugSerial) {
						Serial.println();
						Serial.print("BUS Vol: ");
						Serial.print(sample);
						Serial.print(" V");
						break;
					}
				case AC_V:
					sample = node.getResponseBuffer(i) * AC_V_Conf;
          if(sample < dataRes_low[AC_V_Index]) dataRes_low[AC_V_Index]=sample;
          if(sample > dataRes_high[AC_V_Index]) dataRes_high[AC_V_Index]=sample;
          dataRes[AC_V_Index] = (dataRes_high[AC_V_Index] + dataRes_low[AC_V_Index])/2;
          
					if(debugSerial) {
						Serial.println();
						Serial.print("AC Vol: ");
						Serial.print(sample);
						Serial.print(" V");
						break;
					}
				case AC_Hz:
					sample = node.getResponseBuffer(i) * AC_Hz_Conf;
          if(sample < dataRes_low[AC_Hz_Index]) dataRes_low[AC_Hz_Index]=sample;
          if(sample > dataRes_high[AC_Hz_Index]) dataRes_high[AC_Hz_Index]=sample;
          dataRes[AC_Hz_Index] = (dataRes_high[AC_Hz_Index] + dataRes_low[AC_Hz_Index])/2;
          
					if(debugSerial) {
						Serial.println();
						Serial.print("AC Hz: ");
						Serial.print(sample);
						Serial.print(" Hz");
						break;
					}
				case TEM:
					sample = node.getResponseBuffer(i) * TEM_Conf;
          if(sample < dataRes_low[TEM_Index]) dataRes_low[TEM_Index]=sample;
          if(sample > dataRes_high[TEM_Index]) dataRes_high[TEM_Index]=sample;
          dataRes[TEM_Index] = (dataRes_high[TEM_Index] + dataRes_low[TEM_Index])/2;
          
					if(debugSerial) {
						Serial.println();
						Serial.print("Temperature C: ");
						Serial.print(sample);
						Serial.print(" C");
						break;
					}
				case Pac:
					sample = node.getResponseBuffer(i) * Pac_Conf;
          if(sample < dataRes_low[Pac_Index]) dataRes_low[Pac_Index]=sample;
          if(sample > dataRes_high[Pac_Index]) dataRes_high[Pac_Index]=sample;
          dataRes[Pac_Index] = (dataRes_high[Pac_Index] + dataRes_low[Pac_Index])/2;
          
					if(debugSerial) {
						Serial.println();
						Serial.print("Pac W: ");
						Serial.print(sample);
						Serial.print(" W");
						break;
					}
				case EToday:
					sample = node.getResponseBuffer(i) * EToday_Conf;
          if(sample < dataRes_low[EToday_Index]) dataRes_low[EToday_Index]=sample;
          if(sample > dataRes_high[EToday_Index]) dataRes_high[EToday_Index]=sample;
          dataRes[EToday_Index] = (dataRes_high[EToday_Index] + dataRes_low[EToday_Index])/2;
          
					if(debugSerial) {
						Serial.println();
						Serial.print("EToday kWh: ");
						Serial.print(sample);
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
    if(sample < dataRes_low[EAll_Index]) dataRes_low[EAll_Index]=sample;
    if(sample > dataRes_high[EAll_Index]) dataRes_high[EAll_Index]=sample;
    dataRes[EAll_Index] = (dataRes_high[EAll_Index] + dataRes_low[EAll_Index])/2;

		if(debugSerial) {
			Serial.println();
			Serial.print("EAll kWh: ");
			Serial.print(sample);
			Serial.println(" kWh");
			Serial.println("---------------------------");
		}
    return true;
	} else {
		if(debugSerial) {
			Serial.println("Read fail");
		}
    return false;
	}
}
