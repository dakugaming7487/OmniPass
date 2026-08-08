package core;

public class PasswordEntry {

    String website;
    String username;
    String password;
    String notes;

    public PasswordEntry(String website, String username, String password, String notes) {
        this.website = website;
        this.username = username;
        this.password = password;
        this.notes = notes;
    }

    public void display() {
        System.out.println("Website: " + website);
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        System.out.println("Notes: " + notes);
    }

}
