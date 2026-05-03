package com.mycompany.pethotel;

import java.sql.*;

package com.mycompany.pethotel;

import java.sql.*;

public class RoomService {

    public boolean isRoomAvailable(String roomType) {

        String sql = "SELECT COUNT(*) FROM room_booking WHERE room_type=? AND status='ACTIVE'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, roomType);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) < 3;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean isDateAvailable(String roomType, String checkIn, String checkOut) {

        String sql = """
            SELECT COUNT(*) FROM room_booking
            WHERE room_type=? AND status='ACTIVE'
            AND NOT (check_out <= ? OR check_in >= ?)
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, roomType);
            ps.setString(2, checkIn);
            ps.setString(3, checkOut);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public void showRoomCapacity(String roomType) {

        String sql = "SELECT COUNT(*) FROM room_booking WHERE room_type=? AND status='ACTIVE'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, roomType);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int used = rs.getInt(1);
                int available = Math.max(0, 3 - used);
                System.out.println(roomType + " Available: " + available);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean bookRoom(int petId, String roomType, String checkIn, String checkOut) {

        if (!isRoomAvailable(roomType)) {
            System.out.println("Room is full.");
            return false;
        }

        if (!isDateAvailable(roomType, checkIn, checkOut)) {
            System.out.println("Room not available for selected dates.");
            return false;
        }

        String sql = "INSERT INTO room_booking VALUES(null,?,?,?,?, 'ACTIVE')";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, petId);
            ps.setString(2, roomType);
            ps.setString(3, checkIn);
            ps.setString(4, checkOut);

            ps.executeUpdate();
            System.out.println("Room Booked!");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public double getRoomPrice(String roomType) {
        return switch (roomType) {
            case "Small Room" -> 500.00;
            case "Medium Room" -> 800.00;
            case "Large Room" -> 1200.00;
            default -> 0.0;
        };
    }
}
