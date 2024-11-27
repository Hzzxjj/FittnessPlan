import java.util.HashMap;
import java.util.Map;

public class User {
    private static Map<String, User> userDatabase = new HashMap<>(); 
    private String username;
    private String phone;
    private String passwordHash;

    public User(String username, String phone, String passwordHash) {
        this.username = username;
        this.phone = phone;
        this.passwordHash = passwordHash;
    }

    public String getUsername() {
        return username;
    }

    public String getPhone() {
        return phone;
    }

    // Updated login method: Check password hash
    public static User login(String username, String passwordHash) {
        User user = userDatabase.get(username);
        if (user != null && user.passwordHash.equals(passwordHash)) {
            return user;
        }
        return null; 
    }

    // Updated create account method: Store the password hash
    public static boolean createAccount(String username, String phone, String passwordHash) {
        if (userDatabase.containsKey(username)) {
            return false; // Username already exists
        }
        User newUser = new User(username, phone, passwordHash);
        userDatabase.put(username, newUser);
        return true;
    }
}
