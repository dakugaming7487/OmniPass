package core;

public class Main {
    public static void main(String[] args) {
        PasswordEntry github = new PasswordEntry();

        github.website = "github.com";
        github.username = "daku";
        github.password = "password123";
        github.notes = "My Github account";

        github.display();
    }
}
