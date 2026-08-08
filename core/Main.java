package core;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Vault vault = new Vault();

        while (true) {

            System.out.println("\n====================");
            System.out.println("      DakuPass");
            System.out.println("====================");
            System.out.println("1. Add Password");
            System.out.println("2. View Passwords");
            System.out.println("3. Exit");
            System.out.print("Choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {

                System.out.print("Website: ");
                String website = scanner.nextLine();

                System.out.print("Username: ");
                String username = scanner.nextLine();

                System.out.print("Password: ");
                String password = scanner.nextLine();

                System.out.print("Notes: ");
                String notes = scanner.nextLine();

                PasswordEntry github = new PasswordEntry(website, username, password, notes);
                vault.addEntry(github);

                System.out.println("Password added successfully!");

            } else if (choice == 2) {
                vault.displayEntries();
            } else if (choice == 3) {
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Invalid choise");
            }
        }

        scanner.close();
    }
}
