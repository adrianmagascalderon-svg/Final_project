package com.mycompany.pethotel;

import java.sql.*;

public class ReservationManager {

    public void changeRoom(int bookingId, String newCheckIn, String newCheckOut) {

        String sql = "UPDATE room_booking SET check_in=?, check_out=? WHERE booking_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newCheckIn);
            ps.setString(2, newCheckOut);
            ps.setInt(3, bookingId);

            int updated = ps.executeUpdate();

            if (updated > 0) {
                System.out.println("Room reservation updated successfully.");
            } else {
                System.out.println("Booking not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeSpa(int bookingId, String newDate, String newTime) {

        String sql = "UPDATE spa_booking SET date=?, time=? WHERE booking_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newDate);
            ps.setString(2, newTime);
            ps.setInt(3, bookingId);

            int updated = ps.executeUpdate();

            if (updated > 0) {
                System.out.println("Spa reservation updated successfully.");
            } else {
                System.out.println("Booking not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelRoom(int bookingId) {

        String sql = "UPDATE room_booking SET status='CANCELLED' WHERE booking_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, bookingId);

            int updated = ps.executeUpdate();

            if (updated > 0) {
                System.out.println("Room reservation cancelled.");
            } else {
                System.out.println("Booking not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelSpa(int bookingId) {

        String sql = "UPDATE spa_booking SET status='CANCELLED' WHERE booking_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, bookingId);

            int updated = ps.executeUpdate();

            if (updated > 0) {
                System.out.println("Spa reservation cancelled.");
            } else {
                System.out.println("Booking not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewRoomHistory() {

        String sql = "SELECT * FROM room_booking";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n=== ROOM BOOKING HISTORY ===");

            while (rs.next()) {
                System.out.println("Booking ID: " + rs.getInt("booking_id"));
                System.out.println("Pet ID: " + rs.getInt("pet_id"));
                System.out.println("Room Type: " + rs.getString("room_type"));
                System.out.println("Check-in: " + rs.getString("check_in"));
                System.out.println("Check-out: " + rs.getString("check_out"));
                System.out.println("Status: " + rs.getString("status"));
                System.out.println("-----------------------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewSpaHistory() {

        String sql = "SELECT * FROM spa_booking";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n=== SPA BOOKING HISTORY ===");

            while (rs.next()) {
                System.out.println("Booking ID: " + rs.getInt("booking_id"));
                System.out.println("Pet ID: " + rs.getInt("pet_id"));
                System.out.println("Service: " + rs.getString("service"));
                System.out.println("Date: " + rs.getString("date"));
                System.out.println("Time: " + rs.getString("time"));
                System.out.println("Status: " + rs.getString("status"));
                System.out.println("-----------------------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔥 SHOW DETAILS
    public void showRoomDetails(int bookingId) {

        String sql = "SELECT * FROM room_booking WHERE booking_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, bookingId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    System.out.println("\nCURRENT ROOM BOOKING:");
                    System.out.println("Booking ID: " + rs.getInt("booking_id"));
                    System.out.println("Room Type: " + rs.getString("room_type"));
                    System.out.println("Check-in: " + rs.getString("check_in"));
                    System.out.println("Check-out: " + rs.getString("check_out"));
                    System.out.println("Status: " + rs.getString("status"));
                    System.out.println("---------------------------");
                } else {
                    System.out.println("Booking not found.");
                }
            }

        } catch(Exception e){ e.printStackTrace(); }
    }

    public void showSpaDetails(int bookingId) {

        String sql = "SELECT * FROM spa_booking WHERE booking_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, bookingId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    System.out.println("\nCURRENT SPA BOOKING:");
                    System.out.println("Booking ID: " + rs.getInt("booking_id"));
                    System.out.println("Service: " + rs.getString("service"));
                    System.out.println("Date: " + rs.getString("date"));
                    System.out.println("Time: " + rs.getString("time"));
                    System.out.println("Status: " + rs.getString("status"));
                    System.out.println("---------------------------");
                } else {
                    System.out.println("Booking not found.");
                }
            }

        } catch(Exception e){ e.printStackTrace(); }
    }
}