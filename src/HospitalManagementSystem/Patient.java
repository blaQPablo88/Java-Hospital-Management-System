package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class Patient {

    private Connection connection;
    private Scanner scanner;

    // patient constructor
    public Patient(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    // add patient method
    public void addPatient() {
        System.out.println("Enter Patient name: ");
        String name = scanner.nextLine();  // ✅ reads full line like "Katlego Masilo"

        System.out.println("Enter Patient age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // ✅ clear leftover newline after number input

        System.out.println("Enter Patient gender: ");
        String gender = scanner.nextLine(); // ✅ full gender input

        // try-catch-block to catch data entry
        try {
            String query = "INSERT INTO patients(patient_name, patient_age, patient_gender) VALUES(?, ?, ?)";

            // parse in the query into the connected database
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // create column placeholders and assign them
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, gender);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Patient added successfully.");
            } else {
                System.out.println("FAILED TO ADD PATIENT: " + name + "!!!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // view patients method
    public void viewPatients() {
        String query = "SELECT * FROM patients";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Patients");
            System.out.println("+------------+----------------------------+------+--------+");
            System.out.println("| Patient Id | Name                       | Age  | Gender |");
            System.out.println("+------------+----------------------------+------+--------+");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("patient_name");
                int age = resultSet.getInt("patient_age");
                String gender = resultSet.getString("patient_gender");

                System.out.printf("| %-10s | %-26s | %-4s | %-6s |\n", id, name, age, gender);
            }
            System.out.println("+------------+----------------------------+------+--------+");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // get patient by id method
    public boolean patientExists(int id) {
        String query = "SELECT * FROM patients WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) { return true; } // if row (id) exists

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
