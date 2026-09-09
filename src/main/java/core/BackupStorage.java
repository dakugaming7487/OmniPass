package core;


import core.crypto.EncryptionManager;

import  java.io.*;
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

            String encrypted = EncryptionManager.encrypt(exportPassword, key);

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
}
