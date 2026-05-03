package com.mycompany.pethotel;
import java.sql.*;

public class PetService {

    public void addPet(int ownerId, String name, String type, String breed, int age) {

        String sql = "INSERT INTO pet(owner_id,name,type,breed,age) VALUES(?,?,?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, ownerId);
            ps.setString(2, name);
            ps.setString(3, type);
            ps.setString(4, breed);
            ps.setInt(5, age);

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int petId = rs.getInt(1);
                    System.out.println("Pet Added! Pet ID: " + petId);
                }
            }

        } catch(Exception e){ 
            e.printStackTrace(); 
        }
    }
}