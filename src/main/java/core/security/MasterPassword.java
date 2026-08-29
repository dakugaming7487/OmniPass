package core.security;

import core.crypto.EncryptionManager;

import java.util.Scanner;

import java.io.*;

import javax.crypto.SecretKey;

public class MasterPassword {

    private static final String MASTER_FILE = "data/master.dat";

    private static Boolean generator(String password){

        try (FileWriter writer = new FileWriter(MASTER_FILE)) {

            byte[] salt = EncryptionManager.generateSalt();

            String saltHex = EncryptionManager.bytesToHex(salt);

            String hash = EncryptionManager.pbkdf2Hash(password, salt);

            writer.write(saltHex + ":" + hash);

            return true;

        } catch (FileNotFoundException e){
            System.out.println("file not there");
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean exists(){
        return new java.io.File(MASTER_FILE).exists();
    }

    public static SecretKey cli(Scanner scanner) {

        System.out.print("Enter Master Password: ");
        String enteredPassword = scanner.nextLine();

        SecretKey key = authenticate(enteredPassword);

        if (key != null) {
            System.out.println("Access Granted!");
        } else {
            System.out.println("Incorrect Master Password.");
        }

        return key;
    }

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

            boolean created = generator(password);

            if (created == false){
                System.out.println("Master password was not created");
                return;
            }
            else{
                System.out.println("Master password created successfully!");
                return;
            }
        }

    }

    public static void create(String password){

        boolean created = generator(password);
        if (created) {}
        return;
    }

    public static SecretKey authenticate(String enteredPassword) {

        try (BufferedReader reader = new BufferedReader(new FileReader(MASTER_FILE))) {

            String line = reader.readLine();

            String[] parts = line.split(":");

            if (parts.length != 2) {
                System.out.println("Invalid master password file.");
                return null;
            }

            String saltHex = parts[0];
            String savedHash = parts[1];

            byte[] salt = EncryptionManager.hexToBytes(saltHex);

            String enteredHash = EncryptionManager.pbkdf2Hash(enteredPassword, salt);

            if (savedHash.equals((enteredHash))) {
                return EncryptionManager.deriveKey(enteredPassword, salt);
            }

            return null;

        }catch (IOException e) {
            System.out.println("Failed to read master password.");
            e.printStackTrace();
            return null;
        }
    }
}
