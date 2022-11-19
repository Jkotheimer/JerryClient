
#ifndef LEFT

#define LEFT "left"
#define RIGHT "right"
#define FORWARD 1
#define REVERSE -1
#define STOP 0

// Motor pin definitions
#define LM_P1 D0
#define LM_P2 D1
#define LM_P3 D2
#define LM_P4 D3

#define RM_P1 D7
#define RM_P2 D8
#define RM_P3 D9
#define RM_P4 D10

#define STEP_DELAY 3

#endif

const uint8_t stepSequence[32] = {
  1,0,0,0,
  1,1,0,0,
  0,1,0,0,
  0,1,1,0,
  0,0,1,0,
  0,0,1,1,
  0,0,0,1,
  1,0,0,1
};

void setup() {
  Serial.begin(115200);
  Serial.println("START");
  
  pinMode(LM_P1, OUTPUT);
  pinMode(LM_P2, OUTPUT);
  pinMode(LM_P3, OUTPUT);
  pinMode(LM_P4, OUTPUT);
  pinMode(RM_P1, OUTPUT);
  pinMode(RM_P2, OUTPUT);
  pinMode(RM_P3, OUTPUT);
  pinMode(RM_P4, OUTPUT);
}

void loop() {}

void setMotorState(String side, int state) {
  Serial.print("Setting motor state: ");
  Serial.print(side);
  Serial.println(state);
  if (state == FORWARD) clockwiseStep(side);
  else if (state == REVERSE) ctrclockwiseStep(side);
}


void clockwiseStep(String side) {
  for (int8_t i = 0; i < sizeof(stepSequence);) {
    writeMotorPins(side, stepSequence[i++], stepSequence[i++], stepSequence[i++], stepSequence[i++]);
    delay(STEP_DELAY);
  }
}

void ctrclockwiseStep(String side) {
  for (int8_t i = sizeof(stepSequence); i > -1; i-=4) {
    writeMotorPins(side, stepSequence[i-3], stepSequence[i-2], stepSequence[i-1], stepSequence[i]);
    delay(STEP_DELAY);
  }
}

void writeMotorPins(String side, int a, int b, int c, int d) {
  if (side == LEFT) {
    digitalWrite(LM_P1, a);
    digitalWrite(LM_P2 ,b);
    digitalWrite(LM_P3, c);
    digitalWrite(LM_P4, d);
  } else if (side == RIGHT) {
    digitalWrite(RM_P1, a);
    digitalWrite(RM_P2 ,b);
    digitalWrite(RM_P3, c);
    digitalWrite(RM_P4, d);
  }
}