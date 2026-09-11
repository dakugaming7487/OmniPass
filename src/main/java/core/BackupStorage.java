package core;


import core.crypto.EncryptionManager;

import java.io.*;
import javax.crypto.SecretKey;

public class BackupStorage {
    
    private static final String HEADER = "OMNIPASS_BACKUP_V1";

    public static void exportVault(Vault vault,String filename,String exportPassword){

        try{
            byte[] salt = EncryptionManager.generateSalt();

            SecretKey key = EncryptionManager.deriveKey(exportPassword, salt);

            StringBuilder builder = new StringBuilder();

            for (PasswordEntry entry : vault.getEntries()){

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

            File file = new File(filename);

            File parent = file.getParentFile();

            if (parent != null && !parent.exists()){parent.mkdirs();}

            try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))){
                writer.write(HEADER);
                writer.newLine();

                writer.write(EncryptionManager.bytesToHex(salt));

                writer.newLine();

                writer.write(encrypted);
            }
        } catch (IOException e){
            throw new RuntimeException("Failed to export vault.",e);
        }
    }

    public static Vault importVault(String filename,String exportPassword){

        try(BufferedReader reader = new BufferedReader(new FileReader(filename))){

            String header = reader.readLine();

            if (!HEADER.equals(header)){throw new RuntimeException("Invalid OmniPass backup file");}

            String saltHex = reader.readLine();
            String encrypted = reader.readLine();

            if (saltHex == null || encrypted == null){throw new RuntimeException("Invalid Omnipass backup format.");}

            byte[] salt = EncryptionManager.hexToBytes(saltHex);

            SecretKey key = EncryptionManager.deriveKey(exportPassword, salt);

            String decrypted = EncryptionManager.decrypt(encrypted, key);

            Vault vault = new Vault();

            String[] lines = decrypted.split("\n");

            for (String line : lines){

                if (line.isBlank()){continue;}

                String[] parts = line.split("\\|",4);

                if (parts.length != 4){throw new RuntimeException("Invalid entry in backup");}

                PasswordEntry entry = new PasswordEntry(parts[0], parts[1],parts[2], parts[3]);

                vault.addEntry(entry);
            }

            return vault;
        } catch (IOException e){
            throw new RuntimeException("Failed to import vault.",e);
        }
    }
}
