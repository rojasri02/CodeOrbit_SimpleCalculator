# 🧮 Simple Calculator

A simple Android application developed using Kotlin and XML that allows users to perform basic arithmetic calculations through an easy-to-use interface.

The application supports addition, subtraction, multiplication, and division. It also provides a delete option and a History screen to view previous calculations.

---

## 📌 Project Overview

Performing basic mathematical calculations is a common everyday requirement. A simple calculator application makes these calculations quick and convenient.

The Simple Calculator application provides an easy-to-use interface where users can enter numbers, select arithmetic operations, and view the calculated results.

The application provides:

- Basic arithmetic operations
- Addition
- Subtraction
- Multiplication
- Division
- Delete option
- Calculation result display
- Calculation history
- Simple user interface

---

## ✨ Key Features

### ➕ Basic Arithmetic Operations

The calculator supports four basic mathematical operations.

- Addition (+)
- Subtraction (-)
- Multiplication (×)
- Division (÷)

Users can select the required operation and calculate the result.

### 🔢 Number Input

Users can enter numbers using the calculator buttons to perform mathematical calculations.

### 🗑️ Delete Operation

The DEL button allows users to delete entered values and correct their input.

### 🟰 Calculate Result

The equals (=) button processes the entered calculation and displays the result.

### 📋 Calculation History

The application includes a History screen where users can view their previous calculations.

This feature helps users refer to earlier calculation results.

### 📱 Simple User Interface

The application provides a straightforward interface with calculator buttons and a result display for easy interaction.

---

## 🛠️ Technologies Used

### Frontend / User Interface

- XML
- Android UI Components

### Backend / Application Logic

- Kotlin

### Development Environment

- Android Studio
- Gradle

### Platform

- Android

---

## 🧠 Calculation Approach

The calculator uses basic arithmetic operations to process user inputs and display results.

### 1. Number Input

Users enter numbers through the calculator interface.

### 2. Operation Selection

Users select one of the supported arithmetic operators:

- Addition
- Subtraction
- Multiplication
- Division

### 3. Calculation Processing

The application processes the entered values based on the selected arithmetic operator.

### 4. Result Display

The calculated result is displayed on the calculator screen.

### 5. History Viewing

Users can open the History screen to view previous calculations.

---

## 🔄 System Workflow

```text
          ┌──────────────────────┐
          │   Launch Calculator  │
          └──────────┬───────────┘
                     │
                     ▼
          ┌──────────────────────┐
          │    Enter Numbers     │
          └──────────┬───────────┘
                     │
                     ▼
          ┌──────────────────────┐
          │ Select Arithmetic    │
          │     Operation        │
          └──────────┬───────────┘
                     │
                     ▼
          ┌──────────────────────┐
          │  Process Calculation │
          └──────────┬───────────┘
                     │
                     ▼
          ┌──────────────────────┐
          │   Display Result     │
          └──────────┬───────────┘
                     │
                     ▼
          ┌──────────────────────┐
          │  View Calculation    │
          │      History         │
          └──────────────────────┘
```

---

## 🖥️ Application Pages

The application contains the following main screens:

| Page | Description |
|---|---|
| Calculator | Main screen for entering numbers and performing calculations |
| History | Displays previous calculations |

---

## 📸 Screenshots

Add screenshots of your running application here.

### 🧮 Calculator Screen

<!-- Add Calculator Screen Screenshot Here -->

### 📋 History Screen

<!-- Add History Screen Screenshot Here -->

---

## 📂 Project Structure

```text
SimpleCalculator/
│
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── [Application Package]/
│   │       │       └── MainActivity.kt
│   │       │
│   │       ├── res/
│   │       │   ├── layout/
│   │       │   ├── drawable/
│   │       │   └── values/
│   │       │
│   │       └── AndroidManifest.xml
│   │
│   └── build.gradle.kts
│
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

*Note: This is a simplified representation. Actual project files and folders may vary.*

---

## ⚙️ Installation

### 1. Clone the Repository

```bash
git clone <your-repository-url>
```

### 2. Navigate to the Project Folder

```bash
cd SimpleCalculator
```

### 3. Open the Project

- Open Android Studio.
- Select Open Project.
- Choose the downloaded SimpleCalculator folder.

### 4. Gradle Sync

- Allow Android Studio to sync the Gradle files.
- Wait until the project dependencies are configured.

---

## ▶️ How to Run

1. Open the project in Android Studio.
2. Connect an Android device or start an emulator.
3. Wait for the Gradle sync to complete.
4. Click the Run button.
5. The Simple Calculator application will launch on the device or emulator.

---

## 🧪 Testing

The application can be tested using the following basic test cases:

- Check addition with different numbers.
- Check subtraction with different numbers.
- Check multiplication with different numbers.
- Check division with valid inputs.
- Verify the DEL button functionality.
- Verify that the equals button displays the result.
- Open the History screen and check previous calculations.
- Check the application on an Android emulator or device.

---

## 🚀 Future Enhancements

Possible future improvements include:

- Scientific calculator operations.
- Percentage calculation.
- Improved calculation history management.
- Enhanced user interface.
- Dark mode support.
- Additional mathematical functions.

---

## 🎯 Project Objectives

The main objectives of this project are:

1. Develop a basic Android calculator application.
2. Implement arithmetic operations using Kotlin.
3. Design the application interface using XML.
4. Understand button click event handling.
5. Display calculation results accurately.
6. Implement navigation to the History screen.
7. Gain practical experience in Android application development.

---

## 👩‍💻 Author

K.Rojasri

Android Application Development Project

---

## ⭐ Project

If you find this project useful, consider giving the repository a ⭐ on GitHub.
