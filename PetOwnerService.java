package com.mycompany.pethotel;
import java.sql.*;

public class PetOwnerService {

    public void registerOwner(String name, String contact, String address) {

        String sql = "INSERT INTO pet_owner(name, contact, address) VALUES(?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, name);
            ps.setString(2, contact);
            ps.setString(3, address);

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int ownerId = rs.getInt(1);
                    System.out.println("Owner Registered! Owner ID: " + ownerId);
                }
            }

        } catch(Exception e){ 
            e.printStackTrace(); 
        }
    }
}