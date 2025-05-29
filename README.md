# 🌡️ Smart Temperature & Humidity Monitoring App

An Android app for real-time temperature and humidity monitoring, with remote LED control via Firebase.  
Ideal for smart home, greenhouse, or IoT-based automation systems.

## 🖼️ App UI & Hardware Connection
<p align="center">
  <img src="https://github.com/user-attachments/assets/d25d2651-401d-4d0e-a383-f60580671f62" width="300"/>
  <img src="https://github.com/user-attachments/assets/08b462bc-d23b-40d2-bd21-dcf6002a1333" width="300"/>
</p>

## 🔥 Key Features

### 1. Real-time Sensor Monitoring  
- Reads temperature and humidity from DHT11/DHT22 using ESP32.  
- Sends live data to Firebase.  
- Displays real-time values in the Android app.

### 2. Remote LED Control  
- Control the LED (ON/OFF) from the mobile app.  
- Sync LED state via Firebase Realtime Database.

### 3. Firebase Integration  
- Uses Firebase Realtime Database to sync sensor and LED data.  
- Firebase Authentication (optional) for user access control.
  
## 🎥 App Demo



https://github.com/user-attachments/assets/95fd4ac6-6243-432f-be1b-b70c44ddba82

## 🛠️ Technologies Used

| Platform      | Technology Stack                         |
|---------------|------------------------------------------|
| 📱 Mobile     | Android (Kotlin)                         |
| ☁️ Backend    | Firebase Realtime Database               |
| 🔌 Hardware   | ESP32, DHT11 or DHT22 Sensor, LED        |
| 💻 IDE        | Android Studio, Arduino IDE              |

## 🔌 Hardware Connections

| Component       | ESP32 Pin  |
|------------------|------------|
| DHT11 (Signal)   | D4         |
| LED (Anode)      | D2         |
| GND              | GND        |
| VCC              | 3.3V       |

## 📝 How It Works

### 1. ESP32 (Hardware Side)
- Reads sensor data periodically.
- Uploads temperature/humidity to Firebase.
- Listens for LED ON/OFF status from Firebase.

### 2. Android App
- Subscribes to Firebase Realtime Database.
- Displays live sensor readings.
- Sends LED control signals to Firebase.

### 3. Firebase
- Syncs data between hardware and app in real-time.
  
## ⚙️ Arduino IDE and Required Libraries Setup
**Arduino IDE** (https://www.arduino.cc/en/software/)  

**Libraries needed for the Arduino IDE:**

a. **DHT sensor library**  
Download ZIP from GitHub:  
https://github.com/adafruit/DHT-sensor-library  
In Arduino IDE: `Sketch` > `Include Library` > `Add .ZIP Library...`

Also install **Adafruit Unified Sensor Driver** library from:  
https://github.com/adafruit/Adafruit_Sensor

b. **ArduinoJson**  
Download ZIP from GitHub:  
https://github.com/bblanchon/ArduinoJson  
Install similarly via `Add .ZIP Library...`

c. **WiFi.h** (included in ESP32 Board Support Package)  
To install ESP32 Board Support Package (BSP):  
1. Open Arduino IDE.  
2. Go to `File` > `Preferences`.  
3. In **Additional Board Manager URLs**, add:  
`https://raw.githubusercontent.com/espressif/arduino-esp32/gh-pages/package_esp32_index.json`  
4. Click **OK**.  
5. Go to `Tools` > `Board` > `Boards Manager`.  
6. Search **ESP32** and install **esp32 by Espressif Systems**.

d.  **USB-UART CP2102 Driver Installation**

Download the USB-UART CP2102 driver for Windows here:  
https://drive.google.com/file/d/1TYlPo7rUvIC5w21dmxb1HJa0Xh89jtoC/view
## 🚀 Future Improvements

- Add saving past data with Firestore and charts  
- Send notifications when temperature gets too high  
- Support multiple users with Firebase Authentication  
- Add ESP32-CAM for live video monitoring

## 📥 Download Links
**Android App (APK)** 
   
https://drive.google.com/file/d/1qwBcV7Qprr-AdJoaF2leR3sfZwoRu8I_/view?usp=sharing

## 🧰 System Requirements

- Android Studio (Electric Eel or later recommended)  
- Firebase Project with Realtime Database enabled  
- ESP32 board  
- DHT11 or DHT22 sensor  
- LED + resistor  
- Stable Wi-Fi connection


## 👥 Contributors

- Trịnh Thị Hòa  
- Mai Việt Quang  
- Nguyễn Văn Chí  
- Tưởng Thế Bắc  

🎓 Students at: Hanoi University of Business and Technology  
📚 Major: Information Technology
