package br.com.workshop.workshopmongo.dto;

import br.com.workshop.workshopmongo.domain.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
public class UserDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public UserDto() {
    }

    public UserDto (User obj){
        id= obj.getId();
        name=obj.getName();
        email= obj.getEmail();
    }

    @Id
    private String id;
    private String email;
    private String name;


}

