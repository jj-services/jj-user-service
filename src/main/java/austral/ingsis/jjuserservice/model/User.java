package austral.ingsis.jjuserservice.model;

import austral.ingsis.jjuserservice.dto.UserDto;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {

    //TODO check if relation to post is missing in this side

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private String id;

    //for login
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    //profile data
    @Column(name = "email")
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public UserDto toUserDto() {
        return new UserDto(this.id, this.username, this.email, this.firstName, this.lastName);
    }
}
