# 🌡️ Smart Temperature & Humidity Monitoring App

![Screenshot 2025-05-28 142250](https://github.com/user-attachments/assets/d25d2651-401d-4d0e-a383-f60580671f62)

An Android app for real-time temperature and humidity monitoring with remote LED control via Firebase. Perfect for smart home and IoT projects.

<p align="center">
  <img src="https://i.imgur.com/JKQ3W5v.png" alt="App Screenshot" width="300"/>
  <img src="https://i.imgur.com/8m7X3Yj.png" alt="Firebase Database" width="300"/>
</p>

## ✨ Key Features

- 📊 Real-time temperature & humidity monitoring via ESP32 sensors
- 💡 Remote LED on/off control through the app
- ☁️ Firebase integration for cloud data synchronization
- 🏠 Smart home ready - easily adaptable for IoT systems

## 🎥 App Demo

[![Watch the video](https://img.youtube.com/vi/T-D1KVIuvjA/maxresdefault.jpg)](https://youtu.be/T-D1KVIuvjA)

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

## 🚀 Installation & Setup

### System Requirements
- Android Studio (Electric Eel or later)
- Firebase project with Realtime Database enabled
- ESP32 board, LED, DHT22 sensor

### Setup Instructions
```bash
git clone https://github.com/yourusername/smart-temp-humidity-app.git
cd smart-temp-humidity-app
