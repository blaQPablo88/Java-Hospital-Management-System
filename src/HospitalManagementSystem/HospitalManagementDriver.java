package HospitalManagementSystem;

import java.net.ConnectException;
import java.sql.*;
import java.util.Scanner;

public class HospitalManagementDriver {

    // class fields
    private static final String url = "jdbc:mysql://localhost:3306/hospital_manager";
    private static final String username = "root";
    private static final String password = "Maritimo.88";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        // try to connect to database
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Patient patient = new Patient(connection, scanner);
            Doctor doctor = new Doctor(connection);

            while(true) {
                System.out.println("1. Add Patient");
                System.out.println("2. View Patients");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("0. Exit");
                System.out.println("Enter your choice> ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        patient.addPatient(); // Add Patient
                        System.out.println();
                        break;
                    case 2:
                        patient.viewPatients(); // View Patients
                        System.out.println();
                        break;
                    case 3:
                        doctor.viewDoctors(); // View Doctors
                        System.out.println();
                        break;
                    case 4:
                        bookAppointment(patient, doctor, connection, scanner); // Book Appointment
                        System.out.println();
                        break;
                    case 0:
                        return; // Exit
                    default:
                        System.out.println("Enter a valid choice.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // book appointment method
    public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner) {
        System.out.println("Enter Patient id: ");
        int patientId = scanner.nextInt();
        System.out.println("Enter Doctor id: ");
        int doctorId = scanner.nextInt();
        System.out.println("Enter Appointment date (YYYY-MM-DD): ");
        String appointmentDate = scanner.nextLine();

        if (patient.patientExists(patientId) && doctor.doctorExists(doctorId)) {
            if (checkDoctorAvailability(doctorId, appointmentDate, connection)) {
                String appointmentQuery = "INSERT INTO appointments(patient_id, doctor_id, appointment_date) VALUES(?, ?, ?)";

                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
                    preparedStatement.setInt(1, patientId);
                    preparedStatement.setInt(2, doctorId);
                    preparedStatement.setString(3, appointmentDate);

                    int affectedRows = preparedStatement.executeUpdate();
                    if (affectedRows > 0) {
                        System.out.println("Appointment successfully booked for: " + appointmentDate + ".");
                    } else {
                        System.out.println("Failed to book appointment.");
                    }


                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {
                System.out.println("Doctor not available on this day.");
            }

        } else {
            System.out.println("Doctor or Patient don't exist.");
        }
    }

    private static boolean checkDoctorAvailability(int doctorId, String appointmentDate, Connection connection) {
        String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, doctorId);
            preparedStatement.setString(2, appointmentDate);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count == 0) {
                    return true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}