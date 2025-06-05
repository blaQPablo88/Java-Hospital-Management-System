package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class Doctor {

    private Connection connection;
//    private Scanner scanner;

    // doctor constructor
    public Doctor(Connection connection, Scanner scanner) {
        this.connection = connection;
//        this.scanner = scanner;
    }

    // view doctors method
    public void viewDoctors() {
        String query = "SELECT * FROM doctors";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Doctors");
            System.out.println("+-----------+---------------------------+------------------+");
            System.out.println("| Doctor Id | Name                      | Specialty        |");
            System.out.println("+-----------+---------------------------+------------------+");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("doctor_name");
                String specialization = resultSet.getString("doctor_specialization");

                System.out.printf("| %-11s | %-27s | %-18s |\n", id , name, specialization);
            }
            System.out.println("+-----------+---------------------------+------------------+");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // get doctor by id method
    public boolean doctorExists(int id) {
        String query = "SELECT * FROM doctors WHERE id = ?";

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
