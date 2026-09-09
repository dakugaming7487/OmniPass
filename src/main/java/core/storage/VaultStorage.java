package core.storage;

import core.PasswordEntry;
import core.Vault;
import core.crypto.EncryptionManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

import javax.crypto.SecretKey;

public class VaultStorage {

    public static Vault load(String filename, SecretKey key) {

        Vault vault = new Vault();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {

            String encrypted = reader.readLine();

            if (encrypted == null || encrypted.isEmpty()) {return vault;}

            String decrypted = EncryptionManager.decrypt(encrypted, key);

            String[] lines = decrypted.split("\n");

            for (String line : lines) {
                if (line.isBlank()) {continue;}
                String[] parts = line.split("\\|", 4);

                PasswordEntry entry = new PasswordEntry(parts[0],parts[1],parts[2],parts[3]);

                vault.addEntry(entry);
            }

        } catch (FileNotFoundException e) {
            System.out.println("No existing vault found. Starting with an empty vault.");
        } catch (IOException e) {
            System.out.println("Failed to load vault.");
            e.printStackTrace();
        }
        return vault;
    }

    public static void save(Vault vault, String filename, SecretKey key) {
        try {
            File file = new File(filename);
            File parent = file.getParentFile();

            if (parent != null && !parent.exists()) {parent.mkdirs();}

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

                StringBuilder builder = new StringBuilder();

                for (PasswordEntry entry : vault.getEntries()) {

                    builder.append(entry.getWebsite());
                    builder.append("|");

                    builder.append(entry.getUsername());
                    builder.append("|");

                    builder.append(entry.getPassword());
                    builder.append("|");

                    builder.append(entry.getNotes());
                    builder.append("\n");
                }

                String encrypted = EncryptionManager.encrypt(builder.toString(), key);

                writer.write(encrypted);
            }


        } catch (IOException e) {
            System.out.println("Failed to save vault.");
            e.printStackTrace();
        }
    }
}