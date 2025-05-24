#include <WiFi.h>
#include <Firebase_ESP_Client.h>
#include <DHT.h>

// WiFi & Firebase
#define WIFI_SSID "Hoa Trinh"
#define WIFI_PASSWORD "!!Hoa0304@$"
#define DATABASE_URL "https://fir-7118a-default-rtdb.firebaseio.com"
#define DATABASE_SECRET "BJ1apxzqPR6wcqTiNMxp4SR6NlLjHOLcQJ6zMJSU"

// DHT & LED
#define DHTPIN 4
#define DHTTYPE DHT22
#define LED_PIN 2
DHT dht(DHTPIN, DHTTYPE);

// Firebase
FirebaseData fbdo;
FirebaseAuth auth;
FirebaseConfig config;

// Timing
unsigned long lastUpdate = 0;
const unsigned long updateInterval = 5000;

void setup() {
  Serial.begin(115200);
  pinMode(LED_PIN, OUTPUT);
  dht.begin();
  Serial.println("Starting...");

  connectWiFi();

  config.database_url = DATABASE_URL;
  config.signer.tokens.legacy_token = DATABASE_SECRET;
  Firebase.begin(&config, &auth);
  Firebase.reconnectWiFi(true);

  if (Firebase.ready() && !Firebase.RTDB.getInt(&fbdo, "/led/state")) {
    if (fbdo.errorReason() == "path not exist") {
      Firebase.RTDB.setInt(&fbdo, "/led/state", 0);
      Serial.println("Initialized /led/state to 0");
    }
  }
}

void loop() {
  if (WiFi.status() != WL_CONNECTED) {
    Serial.println("WiFi disconnected. Reconnecting...");
    connectWiFi();
    Firebase.RTDB.setBool(&fbdo, "/device/connected", false);  
    return;
  }

  if (!Firebase.ready()) {
    Serial.println("Firebase not ready.");
    return;
  }

  if (millis() - lastUpdate >= updateInterval) {
    float t = dht.readTemperature();
    float h = dht.readHumidity();

    if (!isnan(t) && !isnan(h)) {
      Firebase.RTDB.setFloat(&fbdo, "/sensors/temperature", t);
      Firebase.RTDB.setFloat(&fbdo, "/sensors/humidity", h);
      Serial.print("Temperature: "); Serial.print(t); Serial.print(" °C, ");
      Serial.print("Humidity: "); Serial.print(h); Serial.println(" %");
    } else {
      Serial.println("Failed to read from DHT sensor!");
    }

    if (Firebase.RTDB.getInt(&fbdo, "/led/state")) {
      bool ledOn = fbdo.intData();
      digitalWrite(LED_PIN, ledOn ? HIGH : LOW);
      Serial.print("LED state: "); Serial.println(ledOn ? "ON" : "OFF");
    }

    Firebase.RTDB.setBool(&fbdo, "/device/connected", true);

    int rssi = WiFi.RSSI();
    int signalStrength = map(rssi, -100, -50, 0, 100);
    signalStrength = constrain(signalStrength, 0, 100);
    Firebase.RTDB.setInt(&fbdo, "/device/signal", signalStrength);
    Serial.print("WiFi Signal: "); Serial.print(signalStrength); Serial.println(" %");

    lastUpdate = millis();
  }
}

void connectWiFi() {
  Serial.print("Connecting to WiFi: ");
  Serial.println(WIFI_SSID);
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(500);
  }
  Serial.println("");
  Serial.println("WiFi connected!");
  Serial.print("IP address: ");
  Serial.println(WiFi.localIP());
}
