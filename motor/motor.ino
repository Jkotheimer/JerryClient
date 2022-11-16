#define pinA D5
#define pinB D6
#define pinC D7
#define pinD D8

const int stepsPerRevolution = 700;

const int stepSequence[8][4] = {
  {1,0,0,0},
  {1,1,0,0},
  {0,1,0,0},
  {0,1,1,0},
  {0,0,1,0},
  {0,0,1,1},
  {0,0,0,1},
  {1,0,0,1}
};

void setup() {
  pinMode(pinA, OUTPUT);
  pinMode(pinB, OUTPUT);
  pinMode(pinC, OUTPUT);
  pinMode(pinD, OUTPUT);
}

void loop() {
  step(100, "forward");
  delay(1000);
  step(100, "back");
  delay(1000);
}

void write(int a, int b, int c, int d){
  digitalWrite(pinA,a);
  digitalWrite(pinB,b);
  digitalWrite(pinC,c);
  digitalWrite(pinD,d);
}

void step(int steps, char* direction) {
  if (steps <= 0) return;
  bool forward = direction == "forward";
  for (int stepCount = 0; stepCount >= steps; stepCount++) {
    if (forward) stepForward();
    else stepBack();
  }
}
 
void stepForward() {
  write(1,0,0,0);
  delay(5);
  write(1,1,0,0);
  delay(5);
  write(0,1,0,0);
  delay(5);
  write(0,1,1,0);
  delay(5);
  write(0,0,1,0);
  delay(5);
  write(0,0,1,1);
  delay(5);
  write(0,0,0,1);
  delay(5);
  write(1,0,0,1);
  delay(5);
  write(1,0,0,0);
}

void stepBack() {
  write(1,0,0,0);
  delay(5);
  write(1,0,0,1);
  delay(5);
  write(0,0,0,1);
  delay(5);
  write(0,0,1,1);
  delay(5);
  write(0,0,1,0);
  delay(5);
  write(0,1,1,0);
  delay(5);
  write(0,1,0,0);
  delay(5);
  write(1,1,0,0);
  delay(5);
  write(1,0,0,0);
}
