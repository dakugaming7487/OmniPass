package core.security;

import core.crypto.EncryptionManager;

import java.util.Scanner;

import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

import javax.crypto.SecretKey;

public class MasterPassword {

    public static void create(Scanner scanner) {

        while (true) {

            System.out.print("Create Master Password: ");
            String password = scanner.nextLine();

            System.out.print("Confirm Master Password: ");
            String confirm = scanner.nextLine();

            if (!password.equals(confirm)) {
                System.out.println("Passwords do not match!\n");
                continue;
            }

            try (FileWriter writer = new FileWriter("data/master.dat")) {

                byte[] salt = EncryptionManager.generateSalt();

                String saltHex = EncryptionManager.bytesToHex(salt);

                String hash = EncryptionManager.pbkdf2Hash(password, salt);

                writer.write(saltHex + ":" + hash);
            } catch (IOException e) {
                System.out.println("Failed to create master password.");
                e.printStackTrace();
                return;
            }

            System.out.println("Master password created successfully!");
            return;
        }

    }

    public static SecretKey login(Scanner scanner) {

        try (BufferedReader reader = new BufferedReader(new FileReader("data/master.dat"))) {

            String line = reader.readLine();

            String[] parts = line.split(":");

            if (parts.length != 2) {
                System.out.println("Invalid master password file.");
                return null;
            }

            String saltHex = parts[0];
            String savedHash = parts[1];

            byte[] salt = EncryptionManager.hexToBytes(saltHex);

            System.out.print("Enter Master Password: ");
            String enteredPassword = scanner.nextLine();

            String enteredHash = EncryptionManager.pbkdf2Hash(enteredPassword, salt);

            if (savedHash.equals((enteredHash))) {
                System.out.println("Access Granted!");
                return EncryptionManager.deriveKey(enteredPassword, salt);
            }

            System.out.println("Incorrect Master Password.");
            return null;

        } catch (IOException e) {
            System.out.println("Failed to read master password.");
            e.printStackTrace();
            return null;
        }
    }
}
