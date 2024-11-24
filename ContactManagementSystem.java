import java.io.*;
import java.util.*;

public class ContactManagementSystem {
    static final String FILE_NAME = "contacts.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Contact Management System ---");
            System.out.println("1. Add Contact");
            System.out.println("2. View Contacts");
            System.out.println("3. Delete Contact");
            System.out.println("4. Edit Contact");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addContact(sc);
                    break;
                case 2:
                    viewContacts();
                    break;
                case 3:
                    deleteContact(sc);
                    break;
                case 4:
                    editContact(sc);
                    break;
                case 5:
                    System.out.println("Exiting the program. Goodbye!");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Method to add a contact
    public static void addContact(Scanner sc) {
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Phone Number: ");
        String phone = sc.nextLine();
        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        String contact = name + "," + phone + "," + email;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(contact);
            writer.newLine();
            System.out.println("Contact added successfully!");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the contact.");
        }
    }

    // Method to view all contacts
    public static void viewContacts() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            int count = 0;
            System.out.println("\n--- Contact List ---");
            while ((line = reader.readLine()) != null) {
                count++;
                String[] details = line.split(",");
                System.out.println(count + ". Name: " + details[0] + ", Phone: " + details[1] + ", Email: " + details[2]);
            }
            if (count == 0) {
                System.out.println("No contacts found.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the contacts.");
        }
    }

    // Method to delete a contact
    public static void deleteContact(Scanner sc) {
        List<String> contacts = readContacts();
        if (contacts.isEmpty()) {
            System.out.println("No contacts found to delete.");
            return;
        }

        viewContacts();
        System.out.print("Enter the number of the contact to delete: ");
        int deleteIndex = sc.nextInt() - 1;
        sc.nextLine(); // consume newline

        if (deleteIndex < 0 || deleteIndex >= contacts.size()) {
            System.out.println("Invalid selection. No contact deleted.");
            return;
        }

        contacts.remove(deleteIndex);
        writeContacts(contacts);
        System.out.println("Contact deleted successfully!");
    }

    // Method to edit a contact
    public static void editContact(Scanner sc) {
        List<String> contacts = readContacts();
        if (contacts.isEmpty()) {
            System.out.println("No contacts found to edit.");
            return;
        }

        viewContacts();
        System.out.print("Enter the number of the contact to edit: ");
        int editIndex = sc.nextInt() - 1;
        sc.nextLine(); // consume newline

        if (editIndex < 0 || editIndex >= contacts.size()) {
            System.out.println("Invalid selection. No contact edited.");
            return;
        }

        String[] contactDetails = contacts.get(editIndex).split(",");
        System.out.println("Current Name: " + contactDetails[0]);
        System.out.print("Enter new Name (or press Enter to keep unchanged): ");
        String newName = sc.nextLine();
        if (!newName.isEmpty()) {
            contactDetails[0] = newName;
        }

        System.out.println("Current Phone: " + contactDetails[1]);
        System.out.print("Enter new Phone (or press Enter to keep unchanged): ");
        String newPhone = sc.nextLine();
        if (!newPhone.isEmpty()) {
            contactDetails[1] = newPhone;
        }

        System.out.println("Current Email: " + contactDetails[2]);
        System.out.print("Enter new Email (or press Enter to keep unchanged): ");
        String newEmail = sc.nextLine();
        if (!newEmail.isEmpty()) {
            contactDetails[2] = newEmail;
        }

        // Update the contact in the list
        contacts.set(editIndex, String.join(",", contactDetails));
        writeContacts(contacts);
        System.out.println("Contact edited successfully!");
    }

    // Helper method to read contacts from the file
    private static List<String> readContacts() {
        List<String> contacts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contacts.add(line);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the contacts.");
        }
        return contacts;
    }

    // Helper method to write contacts back to the file
    private static void writeContacts(List<String> contacts) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String contact : contacts) {
                writer.write(contact);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while saving the contacts.");
        }
    }
}

