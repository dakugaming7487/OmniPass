package core.storage;

import core.PasswordEntry;
import core.Vault;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

public class VaultStorage {

    public static Vault load(String filename) {
        Vault vault = new Vault();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");

                PasswordEntry entry = new PasswordEntry(parts[0], parts[1], parts[2], parts[3]);
                vault.addEntry(entry);
            }
        } catch (IOException e) {
            System.out.println("Failed to load vault.");
            e.printStackTrace();
        }
        return vault;
    }

    public static void save(Vault vault, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (PasswordEntry entry : vault.getEntries()) {
                writer.write(entry.getWebsite() + "|" + entry.getUsername() + "|" + entry.getPassword() + "|"
                        + entry.getNotes());
                writer.newLine();
            }

            System.out.println("Vault saved successfully.");

        } catch (FileNotFoundException e) {

            System.out.println("No existing vault found. Starting with an empty vault.");

        } catch (IOException e) {
            System.out.println("failed to save vault.");
            e.printStackTrace();
        }

    }
}