# 🌡️ Smart Temperature & Humidity Monitoring App

![Screenshot 2025-05-28 142250](https://github.com/user-attachments/assets/d25d2651-401d-4d0e-a383-f60580671f62)

An Android app for real-time temperature and humidity monitoring with remote LED control via Firebase. Perfect for smart home and IoT projects.

<p align="center">
  <img src="https://i.imgur.com/JKQ3W5v.png" alt="App Screenshot" width="300"/>
  <img src="https://i.imgur.com/8m7X3Yj.png" alt="Firebase Database" width="300"/>
</p>

## 🔥 Key Features
1.Real-time Sensor Monitoring

  - Reads temperature & humidity data (likely from DHT11/DHT22 sensor via Arduino/ESP8266/ESP32)

  - Displays live updates in the Android app

2.Remote LED Control

 - Toggle LED status (ON/OFF) from the app

 - Firebase acts as the cloud-based control hub

3.Firebase Integration

  - Realtime Database (for sensor data & LED state)

  - Authentication (optional, for user login)

4.Smart Home / IoT Use Case

  - Can be used for home automation, greenhouse monitoring, etc.

## 🎥 App Demo



https://github.com/user-attachments/assets/95fd4ac6-6243-432f-be1b-b70c44ddba82



## 🛠 Technologies Used

| Platform       | Technology Stack          |
|----------------|---------------------------|
| 📱 Mobile      | Android (Kotlin)          |
| 🖥️ Backend     | Firebase Realtime Database|
| 🔌 Hardware    | ESP32 + DHT11/DHT22 Sensor|
| 💻 IDE         | Android Studio, Arduino IDE |

## 🔌 Hardware Connections

| Component      | ESP32 Pin |
|----------------|----------|
| DHT11 (Signal) | D4       |
| LED (Anode)    | D2       |
| GND            | GND      |
| VCC            | 3.3V     |

## 📝 How It Works?

1.Hardware Side (ESP32/Arduino)
  - Reads sensor data → Sends to Firebase
  - Listens for LED commands from Firebase

2.Android App
  - Subscribes to Firebase for live updates
  - Sends LED toggle commands

3.Firebase
  - Acts as the middleman for bidirectional communication


## 🚀 Possible Improvements
✅ Add Historical Data Logging (Firestore + Charts)
✅ Push Notifications (if temperature exceeds threshold)
✅ Multi-user Support (Firebase Auth)
✅ ESP32 Camera Integration (for remote monitoring)

## 📥 Download Links
1. Android App (APK)
   
https://drive.google.com/file/d/1qwBcV7Qprr-AdJoaF2leR3sfZwoRu8I_/view?usp=sharing

2. Arduino/ESP32 Firmware
📌 Precompiled Binaries: Download HEX/BIN
📌 Source Code (PlatformIO/Arduino IDE): GitHub Firmware

### System Requirements
- Android Studio (Electric Eel or later)
- Firebase project with Realtime Database enabled
- ESP32 board, LED, DHT22 sensor

### Setup Instructions

```bash
git clone https://github.com/yourusername/smart-temp-humidity-app.git
cd smart-temp-humidity-app
