float dataRes[9] = {};
//=====================================================================
//                              encryption
//=====================================================================
#define BLOCK_SIZE 16

uint8_t key[BLOCK_SIZE] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16 };
uint8_t iv[BLOCK_SIZE] = { 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34,35, 36 };

char Pass[]="ce.uit.edu.vn";

//=====================================================================
//                              wifi and socket
//=====================================================================
const char* ssid = "UIT_Guest";
const char* password = "1denmuoi1";
     
const char* Host_Socket = "ceecsolarsystem.herokuapp.com";
unsigned int Port_Socket = 80;

char sendSocketString[400] = {};

//const char* Host_Socket = "192.168.0.108";
//unsigned int Port_Socket = 3484;

//=====================================================================
//                              time
//=====================================================================

unsigned long previousMillisSendAut = 0;
#define intervalSendAut 4000

unsigned long previousMillisSendCurrent = 0;
unsigned long previousMillisSendCollected = 0;
#define intervalSendCurrent 10000
#define intervalSendCollected 1800000

unsigned long previousMillisTimeOutReset = 0;
#define timeOutReset 1820000

//=====================================================================
//                              user define
//=====================================================================
bool joinedRoom = false;
