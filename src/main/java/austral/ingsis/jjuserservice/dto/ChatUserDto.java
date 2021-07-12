package austral.ingsis.jjuserservice.dto;

import java.util.ArrayList;
import java.util.List;


public class ChatUserDto {
    private UserDto me;
    private List<UserDto> contacts;


    public ChatUserDto(UserDto me, List<UserDto> contacts) {
        this.me = me;
        this.contacts = contacts;
    }

    public UserDto getMe() {
        return me;
    }

    public void setMe(UserDto me) {
        this.me = me;
    }

    public List<UserDto> getContacts() {
        return contacts;
    }

    public void setContacts(List<UserDto> contacts) {
        this.contacts = contacts;
    }
}
