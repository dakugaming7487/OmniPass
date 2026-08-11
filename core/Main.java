package core;

import java.util.Scanner;
import java.util.ArrayList;
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
            System.out.println("3. Search Password");
            System.out.println("4. Edit Password");
            System.out.println("5. Save Vault");
            System.out.println("6. Delete Password");
            System.out.println("7. Exit");
            System.out.print("Choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1: {
                    addPassword(scanner, vault);
                    break;
                }

                case 2: {
                    vault.displayEntries();
                    break;
                }

                case 3: {
                    System.out.print("Website: ");
                    String website = scanner.nextLine();

                    ArrayList<PasswordEntry> results = vault.searchByWebsite(website);

                    if (results.isEmpty()) {
                        System.out.println("No matching Websites found.");
                    } else {
                        for (PasswordEntry entry : results) {
                            entry.display();
                            System.out.println("--------------------");
                        }
                    }

                    break;
                }

                case 4: {
                    System.out.print("Website: ");
                    String website = scanner.nextLine();

                    ArrayList<PasswordEntry> results = vault.searchByWebsite(website);

                    if (results.isEmpty()) {
                        System.out.println("No matching Websites found");
                    } else {
                        int i = 1;
                        for (PasswordEntry entry : results) {
                            System.out.println(i + ".");
                            entry.displaySummary();
                            System.out.println("--------------------");
                            i++;
                        }
                        System.out.print("Choose Entry: ");
                        int selected = scanner.nextInt();
                        scanner.nextLine();
                        PasswordEntry entry = results.get(selected - 1);

                        System.out.print("New Website: ");
                        String newWebsite = scanner.nextLine();
                        entry.setWebsite(newWebsite);

                        System.out.print("New Username: ");
                        String newUsername = scanner.nextLine();
                        entry.setUsername(newUsername);

                        System.out.print("New Password: ");
                        String newPassword = scanner.nextLine();
                        entry.setPassword(newPassword);

                        System.out.print("New Notes: ");
                        String newNotes = scanner.nextLine();
                        entry.setNotes(newNotes);
                    }

                    break;
                }

                case 5: {
                    VaultStorage.save(vault, VAULT_FILE);
                    break;
                }

                case 6: {
                    System.out.print("Website: ");
                    String website = scanner.nextLine();

                    ArrayList<PasswordEntry> results = vault.searchByWebsite(website);

                    if (results.isEmpty()) {
                        System.out.println("No matching Websites found");
                    } else {
                        int i = 1;
                        for (PasswordEntry entry : results) {
                            System.out.println(i + ".");
                            entry.displaySummary();
                            System.out.println("--------------------");
                            i++;
                        }

                        System.out.print("Choose Entry: ");
                        int selected = scanner.nextInt();
                        scanner.nextLine();
                        PasswordEntry entry = results.get(selected - 1);

                        System.out.print("Are you sure (y/n): ");
                        String Decision = scanner.nextLine();

                        if (Decision.startsWith("y")) {
                            vault.removeEntry(entry);
                        } else if (Decision == "n") {
                            System.out.println("Permission denied");
                        } else {
                            System.out.println("Unknown decision");
                        }

                    }
                    break;
                }

                case 7: {
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;
                }

                default: {
                    System.out.println("Invalid choice");
                }
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
