#include <SPI.h>
#include<Wire.h>
#include <LiquidCrystal_I2C.h>
#include <stdio.h>
#include <Keypad.h>
 // create servo object to control a servo
// initialize the library with the numbers of the interface pins
LiquidCrystal_I2C lcd(0x27, 2, 1, 0, 4, 5, 6, 7, 3, POSITIVE);

#define Length 6


char Data[Length]; // 6 is the number of chars it can hold + the null char = 7
char st[Length];

byte data_count = 0, st_count = 0;
bool Pass_is_good;
char customKey;

 char station[1]="1"; //change here for other stations
 char tree[10]; 

//pachi nikalne
boolean startRead = false; // is reading?
int stringPos = 0; // string index counter
char inString[8]; // string for incoming serial data
int pos = 0; // for servo
//int CurrentPosition;

const byte ROWS = 4; //four rows
const byte COLS = 4; //three columns
char keys[ROWS][COLS] = {
  {'1','4','7','*'},
  {'2','5','8','0'},
  {'3','6','9','#'},
  {'A','B','C','D'}
  // ORIGINAL
  //{'1','2','3','A'},
  //{'4','5','6','B'},
  //{'7','8','9','C'},
  //{'*','0','#','D'}
};
byte rowPins[ROWS] = {5, 4, 3, 2};//connect to the row pinouts of the keypad  {9, 8, 7, 6}; original
byte colPins[COLS] =   {9, 8, 7, 6};//connect to the column pinouts of the keypad {5, 4, 3, 2}; original
char key[10];
char key1[10];
int dock;
int stat;
int timer;

//for stepper
  int oneway;
 
  int onerev = 1000;   

Keypad keypad = Keypad( makeKeymap(keys), rowPins, colPins, ROWS, COLS );
//char ssid[] = "our_network"; //  your network SSID (name)
//char pass[] = "larsson123";    // your network password (use for WPA, or use as key for WEP)
//int keyIndex = 0;            // your network key Index number (needed only for WEP)

//int status = WL_IDLE_STATUS;
// if you don't want to use DNS (and reduce your sketch size)
// use the numeric IP instead of the name for the server:
//IPAddress server(203, 246, 10, 87);  // numeric IP for Google (no DNS)
//char server[] = "localhost";    // name address for Google (using DNS)

// Initialize the Ethernet client library
// with the IP address and port of the server
// that you want to connect to (port 80 is default for HTTP):





void setup() 
{
  
  //stepper

    pinMode(30, OUTPUT);
  pinMode(31, OUTPUT);
  digitalWrite(30, HIGH);
  digitalWrite(31, LOW);
    pinMode(32, OUTPUT);
  pinMode(33, OUTPUT);
  digitalWrite(32, HIGH);
  digitalWrite(33, LOW);
    pinMode(34, OUTPUT);
  pinMode(35, OUTPUT);
  digitalWrite(34, HIGH);
  digitalWrite(35, LOW);
    pinMode(36, OUTPUT);
  pinMode(37, OUTPUT);
  digitalWrite(36, HIGH);
  digitalWrite(37, LOW);
    //Initialize serial and wait for port to open:
      lcd.begin(16, 2);
  // Print a message to the LCD.
  lcd.clear();
Serial.begin(115200);
 while (!Serial) 
  {
     // wait for serial port to connect. Needed for native USB port only
  }



}

void loop()
{
 
  first: lcd.clear();
  lcd.setCursor(0,0);
  lcd.print("borrow(4)/Park(5)");

  customKey = keypad.getKey();
  if (customKey) // makes sure a key is actually pressed, equal to (customKey != NO_KEY)
  {
    switch (customKey)
    {
      case '4':
      Data[0]='4';
      data_count=1;
         delay(1000);
         lcd.clear();
        pheri: lcd.setCursor(0,0);
         lcd.print("Enter Password");
         customKey = keypad.getKey();
         if (customKey) // makes sure a key is actually pressed, equal to (customKey != NO_KEY)
        { 
          if (customKey=='A')
          {
            clearData();  
            goto first; 
            }
          Data[data_count] = customKey; // store char into data array
          lcd.setCursor(data_count-1,1); // move cursor to show each new char
          lcd.print(Data[data_count]); // print char at said cursor
          data_count++; // increment data array by 1 to store new char, also keep track of the number of chars entered
          }
          if (data_count<5)
          {
            goto pheri;
            }
               
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print("Verifying borow");
    Client_Borrow(Data);
       timer=0;

    
    again:if (Serial.available())
    {
       dock=Serial.read();
        dock=dock-48;
    }
    else
    {
        delay(500);
      timer=timer+1;
      if (timer==20)
      {
        lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print("Server not found");
    clearData();
    clearSt();
    delay(2000);
        goto first;
        }
    goto again;
    }

  lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print(dock);
    delay (5000);
    
    
  if (dock==0)
    {
  
       lcd.clear();
   lcd.setCursor(0, 0);
    lcd.print("invalid");
    delay(2000);
    clearData();  
      }
      else
      {
 switch ( dock )
               {
      case 1:
        for (oneway = 0; oneway <onerev+1; oneway=oneway+1)
         {
      digitalWrite(30, LOW);   
       digitalWrite(31, LOW);
  delay(1);          
  digitalWrite(31, HIGH); 
  delay(1);   
        
    
    }
         break;
      case 2:
            for (oneway = 0; oneway <onerev+1; oneway=oneway+1)
         {
        digitalWrite(32, LOW);   
        digitalWrite(33, LOW);
        delay(1);          
       digitalWrite(33, HIGH); 
       delay(1);   
        }
         break;
      case 3:
                    for (oneway = 0; oneway <onerev+1; oneway=oneway+1)
         {
      digitalWrite(34, LOW);   
       digitalWrite(35, LOW);
  delay(1);          
  digitalWrite(35, HIGH); 
  delay(1);   
        
    
    }
         break;
      case 4:
     for (oneway = 0; oneway <onerev+1; oneway=oneway+1)
         {
      digitalWrite(36, LOW);   
       digitalWrite(37, LOW);
  delay(1);          
  digitalWrite(37, HIGH); 
  delay(1);   
          }
         break;
       default:
        break;
    }

                    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print("Take Bike");// tell servo to go to position in variable 'pos'
    delay(2000); 
    clearData();      // waits 15ms for the servo to reach the position
  }
   break;
  case '5':
   Data[0]='5';
   data_count=1;
  delay(1000);
    lcd.clear();
        pheri2: lcd.setCursor(0,0);
         lcd.print("Enter Password");
         customKey = keypad.getKey();
         if (customKey) // makes sure a key is actually pressed, equal to (customKey != NO_KEY)
        { 
          if (customKey=='A')
          {
            clearData();  
            goto first; 
            }
          Data[data_count] = customKey; // store char into data array
          lcd.setCursor(data_count-1,1); // move cursor to show each new char
          lcd.print(Data[data_count]); // print char at said cursor
          data_count++; // increment data array by 1 to store new char, also keep track of the number of chars entered
          }
          if (data_count<5)
          {
            goto pheri2;
            }
              dockagain:st_count=0;
              lcd.clear();
     lab:lcd.setCursor(0,0);
  lcd.print("Enter Dock& D");

  customKey = keypad.getKey();
  if (customKey) // makes sure a key is actually pressed, equal to (customKey != NO_KEY)
  {
     if (customKey=='A')
          {
             clearSt();
             clearData();
            goto first; 
            }
    st[st_count] = customKey; // store char into data array
    lcd.setCursor(st_count,1); // move cursor to show each new char
    lcd.print(st[st_count]); // print char at said cursor
    st_count++; // increment data array by 1 to store new char, also keep track of the number of chars entered
  }
     if (st[0]=='D')
     {
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print("invalid enter again");
    
    delay(500);
    // added 1 second delay to make sure the password is completely shown on screen before it gets cleared.
    lcd.clear();
    clearSt();
    clearData();
    goto first; 
    }


     else if(st[st_count-1]=='D')
   // if the array index is equal to the number of expected chars, compare data to master
  {
    
     lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print("Verifying park");
     Client_Park(Data, st);
        timer=0;
       agai:if (Serial.available())
    {
       dock=Serial.read();
        dock=dock-48;
    }
    else
    {
       delay(500);
      timer=timer+1;
      if (timer==20)
      {
        lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print("Server not found");
    clearData();
    clearSt();
    delay(2000);
        goto first;
        }
    goto agai;
    }
      lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print(dock);
    delay (2000);
   // dock=Client_Park_Verify();
  if (dock==0)
  {
    //Serial.print("NO MOVEMENT wrong");
       lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print("invalid");
    delay(2000);
    }
    else
    {
     switch ( dock ) {
      case 1:
        for (oneway = 1000; oneway >0; oneway=oneway-1)
         {
      digitalWrite(30, HIGH);   
       digitalWrite(31, LOW);
  delay(1);          
  digitalWrite(31, HIGH); 
  delay(1);   
        
    
    }
         break;
      case 2:
            for (oneway = 1000; oneway >0; oneway=oneway-1)
         {
      digitalWrite(32, HIGH);   
       digitalWrite(33, LOW);
  delay(1);          
  digitalWrite(33, HIGH); 
  delay(1);   
        
    
    }
         break;
      case 3:
                   for (oneway = 1000; oneway >0; oneway=oneway-1)
         {
      digitalWrite(34, HIGH);   
       digitalWrite(35, LOW);
  delay(1);          
  digitalWrite(35, HIGH); 
  delay(1);   
        
    
    }
         break;
      case 4:
                   for (oneway = 1000; oneway >0; oneway=oneway-1)
         {
      digitalWrite(36, HIGH);   
       digitalWrite(37, LOW);
  delay(1);          
  digitalWrite(37, HIGH); 
  delay(1);   
          }
         break;
          default:
        break;
    }
     lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print("Park Bike");// tell servo to go to position in variable 'pos'
    delay(500);  
      }

    lcd.clear();
    
    clearData();
    clearSt();
   goto fin;
    }
    goto lab;        
   
    break;
 default:
   lcd.clear();
   lcd.setCursor(0, 0);
    lcd.print("invalid");
    delay(2000);
    clearData(); 

            
            }
   
   
  }

fin:int tempe=1;
}
 
void Client_Borrow(char Data[8])
{
 char station[1]="1"; //change here for other stations
 char tree[10]; 

// Serial.println("\nStarting connection to pi");

sprintf(tree, "%s%s", Data, station); // this stores data
  Serial.println(tree);

}



void Client_Park(char Data[], char st[])
{
 
  char station[1]="1";// change for diff stations
  char tree[10];
  sprintf(tree, "%s%s%s", Data, st, station); // this stores data
  Serial.println(tree); 
 }
 


void clearData()
{
  while(data_count !=0)
  {   // This can be used for any array size, 
    Data[data_count--] = 0; //clear array for new data
  }
}

void clearSt()
{
  while(st_count !=0)
  {   // This can be used for any array size, 
    st[st_count--] = 0; //clear array for new data
  }
}
  
