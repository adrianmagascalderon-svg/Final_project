package com.mycompany.pethotel;
import java.util.Scanner;

public class MainCLI {

    public static int safeIntInput(Scanner sc, String message) {
        while (true) {
            System.out.print(message);
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    public static void main(String[] args) {

        DBInit.init();
        Scanner sc = new Scanner(System.in);

        PetOwnerService owner = new PetOwnerService();
        PetService pet = new PetService();
        RoomService room = new RoomService();
        SpaService spa = new SpaService();
        DailyCareService care = new DailyCareService();
        ReservationManager manager = new ReservationManager();

        while (true) {
            try {

                System.out.println("\nPET HOTEL & SPA SYSTEM");
                System.out.println("1. Register Pet Owner");
                System.out.println("2. Add Pet Profile");
                System.out.println("3. Book a Room");
                System.out.println("4. Book a Spa Service");
                System.out.println("5. Daily Care");
                System.out.println("6. Change/Cancel Reservation");
                System.out.println("7. View Booking History");
                System.out.println("8. Exit");

                int choice = safeIntInput(sc, "Enter choice: ");

                switch (choice) {

                    // CASE 1
                    case 1:
                        System.out.print("Name: ");
                        String name = sc.nextLine().trim();

                        System.out.print("Contact: ");
                        String contact = sc.nextLine().trim();

                        System.out.print("Address: ");
                        String address = sc.nextLine().trim();

                        if (name.isEmpty() || contact.isEmpty() || address.isEmpty()) {
                            System.out.println("All fields are required.");
                            break;
                        }

                        owner.registerOwner(name, contact, address);
                        break;

                    // CASE 2
                    case 2:
                        int ownerId = safeIntInput(sc, "Owner ID: ");

                        System.out.print("Pet Name: ");
                        String petName = sc.nextLine().trim();

                        System.out.print("Type: ");
                        String type = sc.nextLine().trim();

                        System.out.print("Breed: ");
                        String breed = sc.nextLine().trim();

                        int age = safeIntInput(sc, "Age: ");

                        if (ownerId <= 0 || petName.isEmpty() || type.isEmpty() || breed.isEmpty() || age <= 0) {
                            System.out.println("Invalid pet details.");
                            break;
                        }

                        pet.addPet(ownerId, petName, type, breed, age);
                        break;

                    // CASE 3
   			
			        case 3:
    		            System.out.println("\nBOOK A ROOM");

    		            int petId = safeIntInput(sc, "Pet ID: ");

    		            System.out.print("Check-in Date: ");
   		                String checkIn = sc.nextLine().trim();

    		            System.out.print("Check-out Date: ");
    		            String checkOut = sc.nextLine().trim();

    		            if (petId <= 0 || checkIn.isEmpty() || checkOut.isEmpty()) {
        	            System.out.println("Invalid booking details.");
        	            break;
    		        }

    		            boolean booked = false;
    		            int attempts = 0;

    		            while (!booked && attempts < 5) {
        	            attempts++;

        	            System.out.println("\nROOM STATUS:");
        	            room.showRoomCapacity("Small Room");
        	            room.showRoomCapacity("Medium Room");
        	            room.showRoomCapacity("Large Room");

        	            System.out.println("\n1. Small Room");
        	            System.out.println("2. Medium Room");
        	            System.out.println("3. Large Room");

        	            int roomChoice = safeIntInput(sc, "Select Room: ");

        	            String roomType;

        	            switch (roomChoice) {
            	            case 1: roomType = "Small Room"; break;
            	            case 2: roomType = "Medium Room"; break;
            	            case 3: roomType = "Large Room"; break;
            	        default:
                	        System.out.println("Invalid choice.");
                	    continue;
        	         }

        	        booked = room.bookRoom(petId, roomType, checkIn, checkOut);

        	        if (!booked) {
            	    System.out.println("\nSelected room is unavailable.");
            	    continue;
       		     }

        	        int days = safeIntInput(sc, "Enter number of days: ");

        	        if (days <= 0) {
            	    booked = false;
            	    continue;
        	}

        	        double price = room.getRoomPrice(roomType);
        	        double total = price * days;

        	        BookingPayment payment = new BookingPayment(
            	    "TXN" + System.currentTimeMillis(),
            	    total,
            	    0.10
        	);

        	        if (payment.validatePayment()) {
            	    payment.processInvoice("CUST001", total, 0.10);
        	        } else {
            	System.out.println(payment.getPaymentContextLabel());
        		}
    		}

    		break;
                    // CASE 4
                    // CASE 4
			case 4:
    			System.out.println("\nBOOK SPA SERVICE");

    			int spid = safeIntInput(sc, "Pet ID: ");

    			System.out.println("1. Facials");
    			System.out.println("2. Paw Spa");
    			System.out.println("3. Fur Color/Style");

    			int serviceChoice = safeIntInput(sc, "Select Service: ");

    			String service = null;

                        switch (serviceChoice) {
                        case 1: service = "Facials"; break;
                        case 2: service = "Paw Spa"; break;
                        case 3: service = "Fur Color/Style"; break;
                            default:
                        System.out.println("Invalid service.");
                            break;
                        }

                        if (service == null) break;

    			switch (serviceChoice) {
        			case 1: service = "Facials"; break;
        			case 2: service = "Paw Spa"; break;
        			case 3: service = "Fur Color/Style"; break;
        			default:
            			System.out.println("Invalid service.");
            			break;
    			}

    			System.out.print("Preferred Date: ");
    			String date = sc.nextLine().trim();

    			System.out.print("Preferred Time: ");
    			String time = sc.nextLine().trim();

    			if (date.isEmpty() || time.isEmpty()) {
        		System.out.println("Date and time cannot be empty.");
        		break;
    			}

    			spa.bookSpa(spid, service, date, time);

    			double spaPrice = spa.getServicePrice(service);

    			BookingPayment spaPayment = new BookingPayment(
        			"TXN" + System.currentTimeMillis(),
        			spaPrice,
        			0.05
   			 );

    			if (spaPayment.validatePayment()) {
        			spaPayment.processInvoice("CUST001", spaPrice, 0.05);
    			} else {
        			System.out.println(spaPayment.getPaymentContextLabel());
    			}

    			break;

                    // CASE 5
                    case 5:
                        System.out.println("\nDAILY CARE");
                        System.out.println("1. Log Care");
                        System.out.println("2. View History");

                        int dcChoice = safeIntInput(sc, "Choose: ");

                        if (dcChoice == 1) {

                            int pid = safeIntInput(sc, "Pet ID: ");

                            System.out.println("1. Feeding");
                            System.out.println("2. Walking");
                            System.out.println("3. Playing");

                            int act = safeIntInput(sc, "Select Activity: ");

                            String activity = null;

                            switch (act) {
                            case 1: activity = "Feeding"; break;
                            case 2: activity = "Walking"; break;
                            case 3: activity = "Playing"; break;
                            default:
                            System.out.println("Invalid activity.");
                            break;
                            }

                            if (activity == null) break;

                            switch (act) {
                                case 1: activity = "Feeding"; break;
                                case 2: activity = "Walking"; break;
                                case 3: activity = "Playing"; break;
                                default:
                                    System.out.println("Invalid activity.");
                                    break;
                            }

                            System.out.print("Notes: ");
                            String notes = sc.nextLine().trim();

                            System.out.print("Date: ");
                            String d = sc.nextLine().trim();

                            if (notes.isEmpty() || d.isEmpty()) {
                                System.out.println("Invalid input.");
                                break;
                            }

                            care.logCare(pid, activity, notes, d);

                        } else if (dcChoice == 2) {

                            int pid = safeIntInput(sc, "Pet ID: ");
                            care.viewCareHistory(pid);

                        } else {
                            System.out.println("Invalid choice.");
                        }

                        break;

                    // CASE 6
                    case 6:
                        int resId = safeIntInput(sc, "Reservation ID: ");

                        System.out.println("1. Change");
                        System.out.println("2. Cancel");

                        int opt = safeIntInput(sc, "Choose: ");

                        if (opt == 1) {

                            System.out.println("1. Room");
                            System.out.println("2. Spa");

                            int typeChoice = safeIntInput(sc, "Select Type: ");

                            if (typeChoice == 1) {

                                manager.showRoomDetails(resId);

                                System.out.print("New Check-in: ");
                                String ni = sc.nextLine();

                                System.out.print("New Check-out: ");
                                String no = sc.nextLine();

                                manager.changeRoom(resId, ni, no);

                            } else if (typeChoice == 2) {

                                manager.showSpaDetails(resId);

                                System.out.print("New Date: ");
                                String nd = sc.nextLine();

                                System.out.print("New Time: ");
                                String nt = sc.nextLine();

                                manager.changeSpa(resId, nd, nt);

                            }

                        } else if (opt == 2) {

                            System.out.println("1. Room");
                            System.out.println("2. Spa");

                            int t = safeIntInput(sc, "Select Type: ");

                            if (t == 1) manager.cancelRoom(resId);
                            else if (t == 2) manager.cancelSpa(resId);
                            else System.out.println("Invalid type.");
                        }

                        break;

                    // CASE 7
                    case 7:
                        System.out.println("1. Room History");
                        System.out.println("2. Spa History");

                        int h = safeIntInput(sc, "Choose: ");

                        if (h == 1) manager.viewRoomHistory();
                        else if (h == 2) manager.viewSpaHistory();
                        else System.out.println("Invalid choice.");

                        break;

                    case 8:
                        System.out.println("Exiting...");
                        return;

                    default:
                        System.out.println("Invalid menu choice.");
                }

            } catch (Exception e) {
                System.out.println("\nUnexpected error occurred. Please try again.");
                sc.nextLine();
            }
        }
    }
}