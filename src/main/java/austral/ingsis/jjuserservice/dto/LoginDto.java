package austral.ingsis.jjuserservice.dto;

public class LoginDto {
    private String username;
    private char[] password;

    public LoginDto() {
        super();
    }

    public LoginDto(String username, char[] password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }
}
