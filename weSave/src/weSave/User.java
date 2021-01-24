package weSave;

public class User {
    private String Username;
    private String Password;
    private String UserType;
    
    public User(){
        
    }

    User(String Username, String Password, String UserType) {
        this.Username = Username;
        this.Password = Password;
        this.UserType = UserType;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String UserType) {
        this.UserType = UserType;
    }
}
