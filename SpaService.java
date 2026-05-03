
package com.mycompany.pethotel;
import java.sql.*;

public class PetService {

    public void addPet(int ownerId, String name, String type, String breed, int age) {
public class SpaService {

    public void bookSpa(int petId, String service, String date, String time) {

        String sql = "INSERT INTO spa_booking(pet_id,service,date,time,status) VALUES(?,?,?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, petId);
            ps.setString(2, service);
            ps.setString(3, date);
            ps.setString(4, time);
            ps.setString(5, "ACTIVE");

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int bookingId = rs.getInt(1);
                    System.out.println("Spa Booked! Reservation ID: " + bookingId);
                }
            }

        } catch(Exception e){ 
            e.printStackTrace(); 
        }
    }

    public double getServicePrice(String service) {
        return switch (service) {
            case "Facials" -> 300.00;
            case "Paw Spa" -> 250.00;
            case "Fur Color/Style" -> 600.00;
            default -> 200.00;
        };
    }
}


}
