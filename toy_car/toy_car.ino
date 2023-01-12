#ifndef RIGHT_MOTOR

// Motor pin definitions
#define RIGHT_FORWARD 7
#define RIGHT_BACKWARD 8

#endif

uint8_t rightMotorSpeed = 0;
uint8_t leftMotorSpeed = 0;


void setup() {
  Serial.begin(115200);
  Serial.println("START");

  pinMode(RIGHT_FORWARD, OUTPUT);
  pinMode(RIGHT_BACKWARD, OUTPUT);
}

void loop() {
  int i = 0;
  while (i < 255) {
    Serial.print("Writing:pin ");
    Serial.print(RIGHT_FORWARD);
    Serial.print(": ");
    Serial.println(i);
    analogWrite(RIGHT_FORWARD, i++);
    delay(20);
  }
  while (i > 0) {
    Serial.print("Writing pin ");
    Serial.print(RIGHT_FORWARD);
    Serial.print(": ");
    Serial.println(i);
    analogWrite(RIGHT_FORWARD, --i);
    delay(10);
  }
  while (i < 255) {
    Serial.print("Writing pin ");
    Serial.print(RIGHT_BACKWARD);
    Serial.print(": ");
    Serial.println(i);
    analogWrite(RIGHT_BACKWARD, i++);
    delay(20);
  }
  while (i > 0) {
    Serial.print("Writing pin ");
    Serial.print(RIGHT_BACKWARD);
    Serial.print(": ");
    Serial.println(i);
    analogWrite(RIGHT_BACKWARD, --i);
    delay(10);
  }
}
