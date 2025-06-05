package HospitalManagementSystem;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLDataException;
import java.sql.SQLException;
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
        System.out.println("Enter patient name: ");
        String name = scanner.nextLine();
        System.out.println("Enter patient age: ");
        int age = scanner.nextInt();
        System.out.println("Enter patient gender: ");
        String gender = scanner.nextLine();

        // try-catch-block to catch data entry
        try {
            String query = "INSERT INTO patients(name, age, gender) VALUES(?, ?, ?)";

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

}
