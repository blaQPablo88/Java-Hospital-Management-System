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

CREATE TABLE patients(
    -> id INT AUTO_INCREMENT PRIMARY KEY,
    -> patient_name VARCHAR(255) nOT NULL,
    -> patient_age INT NOT NULL,
    -> patient_gender VARCHAR(10) NOT NULL
    -> );

CREATE TABLE doctors(
    -> id INT AUTO_INCREMENT PRIMARY KEY,
    -> doctor_name VARCHAR(255) NOT NULL,
    -> doctor_specialization VARCHAR(255) NOT NULL,
    -> );

CREATE TABLE appointments(
    -> id INT AUTO_INCREMENT PRIMARY KEY,
    -> patient_id INT NOT NULL,
    -> doctor_id INT NOT NULL,
    -> appointment_date DATE NOT NULL,
    -> FOREIGN KEY (patient_id) REFERENCES patients(id),
    -> FOREIGN KEY (doctor_id) REFERENCES doctors(id)
    -> );
