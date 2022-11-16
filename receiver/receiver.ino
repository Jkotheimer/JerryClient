//#include <ESP8266WiFi.h>

//WiFiServer server(80);

const String ssid = "Cornelia 2S";
const String password = "4tnayti0gd";

bool isConnected = true;

/*
struct Button {
  String wheelSide; // "left" or "right"
  String direction; // "forward" or "back"
  boolean state;
};

const Button buttons[] = {
  {
    "left", "forward", 0
  }, {
    "left", "back", 0
  }, {
    "right", "forward", 0
  }, {
    "right", "back", 0
  }
};
*/

const int pins[] = {
  D0, D1, D2, D3
};

const int steps[] = {
  1, 1, 0, 0,
  0, 1, 1, 0,
  0, 0, 1, 1,
  0, 0, 0, 1,
};

void setup() {
  /*
  Serial.begin(1200);
  Serial.flush();
  Serial.println();
  Serial.println("HERE!");
  */
  //connect();

  for (int pin = 0; pin < sizeof(pins); pin++) {
    /*
    Serial.print("setting pin ");
    Serial.print(pin);
    Serial.print(" to ");
    Serial.println(OUTPUT);
    */
    pinMode(pin, OUTPUT);
  }
}

void loop() {
  //Serial.println("In loop");
  
  if (isConnected) {
    //Serial.println("Taking a step");
    stepForward();
  }


  // put your main code here, to run repeatedly:
  /*
  WiFiClient client = server.available();
  // wait for a client (web browser) to connect
  if (client) {
    Serial.println("\n[Client connected]");
    while (client.connected()) {
      // read line by line what the client (web browser) is requesting
      if (client.available()) {
        String line = client.readStringUntil('\r');
        Serial.print(line);
        // wait for end of client's request, that is marked with an empty line
        if (line.length() == 1 && line[0] == '\n') {
          client.println(handleRequest());
          break;
        }
      }
    }

    while (client.available()) {
      // but first, let client finish its request
      // that's diplomatic compliance to protocols
      // (and otherwise some clients may complain, like curl)
      // (that is an example, prefer using a proper webserver library)
      client.read();
    }

    // close the connection:
    client.stop();
    Serial.println("[Client disconnected]");
  }
  */
}

void stepForward() {
  for (int step = 0; step < sizeof(steps);) {
    for (int pin : pins) {
      digitalWrite(pin, steps[step++]);
    }
    delay(5);
  }
}
/*
String handleRequest() {
  return "HTTP/1.1 204 No Content\r\n";
}

const int connectInterval = 1000;
const int connectTimeout = 10000;
int numRetries = 0;

void connect() {
  WiFi.mode(WIFI_STA);
  Serial.flush();
  Serial.print("Searching for ");
  Serial.println(ssid);
  Serial.print("Try number ");
  Serial.println(numRetries);
  int numNetworks = WiFi.scanNetworks();
  for (int i = 0; i < numNetworks; i++) {
    if (WiFi.SSID(i) == ssid) {
      WiFi.begin(WiFi.SSID(i), password, WiFi.channel(i), WiFi.BSSID(i), true);
      Serial.print("Found ");
      Serial.println(WiFi.SSID(i));
      break;
    }
  }
  int elapsedTime = 0;
  while (WiFi.status() != WL_CONNECTED) {
    delay(connectInterval);
    elapsedTime += connectInterval;
    if (elapsedTime >= connectTimeout) {
      Serial.println("Timeout");
      numRetries++;
      return connect();
    }
    Serial.print("Connecting...");
    Serial.println(WiFi.status());
    Serial.println(elapsedTime);
    Serial.flush();
  }
  Serial.println("Connected");

  //server.begin();

  isConnected = true;
}
*/