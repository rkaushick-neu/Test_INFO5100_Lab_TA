/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Patient;

/**
 *
 * @author rishabhkaushick
 */
public class DatabaseConnector {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/medicaldb?useSSL=false";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "my-secret-pw";
    
    // CRUD Operations
    
    // Create
    public static void addPatient(Patient p1) {
        String query = "INSERT INTO patient_test(name, gender, patient_type) VALUES(?,?,?)";
        try(Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, p1.getName());
            stmt.setString(2, p1.getGender());
            stmt.setString(3, p1.getPatientType());
            int rows = stmt.executeUpdate();
            System.out.println("Rows impacted : " + rows);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
    }
    
    // Read
    public static ArrayList<Patient> getAllPatients(){
        String query  = "SELECT * FROM patient_test";
        ArrayList<Patient> patients = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                Patient p1 = new Patient();
                p1.setId(rs.getInt("id"));
                p1.setName(rs.getString("name"));
                p1.setGender(rs.getString("gender"));  
                p1.setPatientType(rs.getString("patient_type"));  
                patients.add(p1);
            }
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
        
        return patients;
    }
    
    // Update
    public static void updatePatient(Patient oldPatient, Patient newPatient){
        String query = "UPDATE patient_test SET name=?, gender=?, patient_type=? WHERE id=?";
        try(Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)){
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, newPatient.getName());
            stmt.setString(2, newPatient.getGender());
            stmt.setString(3, newPatient.getPatientType());
            stmt.setInt(4, oldPatient.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            //print cant upadte user
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
    }
    
    
    // Delete
    public static void deletePatient(Patient p1){
        String query  = "DELETE FROM patient_test WHERE id = ?";
        try(Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, p1.getId());
            int rows = stmt.executeUpdate();
            System.out.println("Rows impacted : " + rows);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
    }
    
}
