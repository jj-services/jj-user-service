package austral.ingsis.jjuserservice.dto;

import java.util.List;

public class UserDto {
    //response for a get and update request
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    List<PostDto> postDto;

    public List<PostDto> getPostDto() {
        return postDto;
    }

    public void setPostDto(List<PostDto> postDto) {
        this.postDto = postDto;
    }

    public UserDto(Long id, String firstName, String lastName, String username, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
