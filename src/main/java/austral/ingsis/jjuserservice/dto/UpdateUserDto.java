package austral.ingsis.jjuserservice.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class UpdateUserDto {
    private Long id;
    @Size(min = 4, message = "username should have at least 4 characters")
    private String username;
    @Email
    private String email;
    private String firstName;
    private String lastName;
    @Size(min = 6, message = "password should have at least 6 characters")
    private String password;

    public UpdateUserDto(Long id, String username, String email, String firstName, String lastName) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
