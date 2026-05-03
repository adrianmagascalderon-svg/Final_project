package com.mycompany.pethotel;
import java.sql.*;

public class DailyCareService {

    public void logCare(int petId, String activity, String notes, String date) {

        String sql = "INSERT INTO daily_care(pet_id,activity,notes,date) VALUES(?,?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, petId);
            ps.setString(2, activity);
            ps.setString(3, notes);
            ps.setString(4, date);

            ps.executeUpdate();
            System.out.println("Care Logged");

        } catch(Exception e){ 
            e.printStackTrace(); 
        }
    }

    public void viewCareHistory(int petId){

        String sql = "SELECT * FROM daily_care WHERE pet_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, petId);

            try (ResultSet rs = ps.executeQuery()) {

                System.out.println("\nCARE HISTORY");

                while(rs.next()){
                    System.out.println("Care ID: " + rs.getInt("care_id"));
                    System.out.println("Activity: " + rs.getString("activity"));
                    System.out.println("Notes: " + rs.getString("notes"));
                    System.out.println("Date: " + rs.getString("date"));
                    System.out.println("------------------");
                }
            }

        } catch(Exception e){ 
            e.printStackTrace(); 
        }
    }
}
