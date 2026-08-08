package core;

import java.util.ArrayList;

public class Vault {

    private ArrayList<PasswordEntry> entries;

    public Vault() {
        entries = new ArrayList<>();
    }

    public void addEntry(PasswordEntry entry) {
        entries.add(entry);
    }

    public void displayEntries() {

        if (entries.isEmpty()) {
            System.out.println("Vault is empty.");
            return;
        }

        for (PasswordEntry entry : entries) {
            entry.display();
            System.out.println("--------------------");
        }
    }

}
