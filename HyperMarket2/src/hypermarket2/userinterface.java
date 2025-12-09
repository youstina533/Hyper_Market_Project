
package hypermarket2;
import java.io.*;
interface UserActions {
    void displayUserInfo();
    void logAction(String action);
}

abstract class AbstractUser implements UserActions {
 private  String id;
    private String name;
    private String username;
    private String password;
    private String role; 

    public AbstractUser(String id, String name, String username, String password, String role) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public abstract void updateUserInfo();

    public abstract void displayUserInfo() ;

    public void logAction(String action) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("employee_actions.txt", true))) { 
            writer.write("User ID: " + id + ", Role: " + role + ", Action: " + action + "\n");
            writer.flush();
        } catch (IOException e) {
            System.out.println("Error logging action: " + e.getMessage());
        }
    }
    
}

   