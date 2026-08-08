package core;

import java.util.Scanner;
import core.storage.VaultStorage;

public class Main {

    private static final String VAULT_FILE = "data/vault.txt";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Vault vault = VaultStorage.load(VAULT_FILE);

        while (true) {

            System.out.println("\n====================");
            System.out.println("      PasswdManager");
            System.out.println("====================");
            System.out.println("1. Add Password");
            System.out.println("2. View Passwords");
            System.out.println("3. Save Vault");
            System.out.println("4. Exit");
            System.out.print("Choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    addPassword(scanner, vault);
                    break;

                case 2:
                    vault.displayEntries();
                    break;

                case 3:
                    VaultStorage.save(vault, VAULT_FILE);
                    break;

                case 4:
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }

    }

    public static void addPassword(Scanner scanner, Vault vault) {
        System.out.print("Website: ");
        String website = scanner.nextLine();

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.print("Notes: ");
        String notes = scanner.nextLine();

        PasswordEntry entry = new PasswordEntry(website, username, password, notes);
        vault.addEntry(entry);

        System.out.println("Password added successfully!");
    }
}
