# 🏥 Hospital Management System

A simple Java console application to manage hospital patients, doctors, and appointments using MySQL as the database.

---

## 💡 Features

- ➕ Add new patients
- 📋 View all patients
- 🧑‍⚕️ View all doctors
- 📅 Book appointments (one per doctor per day)
- ❌ Prevent double-booking of doctors

---

## 🛠️ Technologies Used

- Java (JDK 17+ recommended)
- MySQL (8.0+)
- JDBC (Java Database Connectivity)
- IntelliJ IDEA / VS Code (or any Java IDE)

---

## 🗃️ Database Setup

Run the following SQL commands to set up your database:

```sql
CREATE DATABASE hospital_manager;

USE hospital_manager;

CREATE TABLE patients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    patient_name VARCHAR(100),
    patient_age INT,
    patient_gender VARCHAR(10)
);

CREATE TABLE doctors (
    id INT AUTO_INCREMENT PRIMARY KEY,
    doctor_name VARCHAR(100),
    doctor_specialization VARCHAR(100)
);

CREATE TABLE appointments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT,
    doctor_id INT,
    appointment_date DATE,
    FOREIGN KEY (patient_id) REFERENCES patients(id),
    FOREIGN KEY (doctor_id) REFERENCES doctors(id)
);
