#ifndef INVERTER_H
#define INVERTER_H

#include <SoftwareSerial.h>
#include <ModbusMaster.h>

//6->DI 5->RO
#define TX_Pin 6
#define RX_Pin 5

#define DE_RE_Pin 4

#define PV_V 0
#define PV_V_Index 0
#define PV_V_Conf 1.0

#define PV_A 1
#define PV_A_Index 1
#define PV_A_Conf 0.1

#define BUS 6
#define BUS_Index 2
#define BUS_Conf 1.0

#define AC_V 7
#define AC_V_Index 3
#define AC_V_Conf 1.0

#define AC_Hz 9
#define AC_Hz_Index 4
#define AC_Hz_Conf 0.1

#define TEM 10
#define TEM_Index 5
#define TEM_Conf 1.0

#define Pac 13 //13, 12
#define Pac_Index 6
#define Pac_Conf 1.0

#define EToday 16
#define EToday_Index 7
#define EToday_Conf 0.01

#define EAll_High 17
#define EAll_Low 18
#define EAll_Index 4
#define EAll_Conf 0.01

#define leng_get 19 //LENGH GET DATA FROM INVERTER.

#define READ_ERR -1

void preTransmission();

void postTransmission();

void inverterInit();

void getDataInverter(float *, bool);

#endif //INVERTER_H
