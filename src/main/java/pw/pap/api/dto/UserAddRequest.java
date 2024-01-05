package pw.pap.api.dto;


public class UserAddRequest {
    private String name;
    private String password;

    public UserAddRequest() {
        // Default constructor for deserialization
    }

    public UserAddRequest(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
