package gui;

import core.PasswordEntry;
import core.Vault;
import core.storage.VaultStorage;

import java.util.ArrayList;

import javax.crypto.SecretKey;


public class VaultService {

    private static final String VAULT_FILE = "data/vault.dat";
    
    private final Vault vault;
    private final SecretKey key;

    public VaultService(Vault vault,SecretKey key){
        this.vault = vault;
        this.key = key;
    }

    public void updateEntry(
        PasswordEntry entry,
        String website,
        String username,
        String password,
        String notes
    ){
        entry.setWebsite(website);
        entry.setUsername(username);
        entry.setPassword(password);
        entry.setNotes(notes);

        VaultStorage.save(vault, VAULT_FILE, key);
    }

    public ArrayList<PasswordEntry> getEntries(){
        return vault.getEntries();
    }

    public void addEntry(String website,String username,String password,String notes){

        PasswordEntry entry = new PasswordEntry(website, username, password, notes);

        vault.addEntry(entry);

        VaultStorage.save(vault, VAULT_FILE, key);
    }

    public void deleteEntry(PasswordEntry entry){
        vault.removeEntry(entry);
        VaultStorage.save(vault, VAULT_FILE, key);
    }

    public ArrayList<PasswordEntry> search(String website){
        return vault.searchByWebsite(website);
    }

}
