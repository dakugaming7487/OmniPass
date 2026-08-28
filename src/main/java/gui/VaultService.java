package gui;

import core.PasswordEntry;
import core.Vault;

import java.util.ArrayList;

public class VaultService {
    
    private final Vault vault;

    public VaultService(Vault vault){
        this.vault = vault;
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
    }

    public ArrayList<PasswordEntry> getEntries(){
        return vault.getEntries();
    }

    public void addEntry(String website,String username,String password,String notes){

        PasswordEntry entry = new PasswordEntry(website, username, password, notes);

        vault.addEntry(entry);
    }

    public void deleteEntry(PasswordEntry entry){
        vault.removeEntry(entry);
    }

    public ArrayList<PasswordEntry> search(String website){
        return vault.searchByWebsite(website);
    }

}
