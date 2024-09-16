package br.com.workshop.workshopmongo.dto;

import br.com.workshop.workshopmongo.domain.User;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
public class AuthorDTO implements Serializable {

    private String id;
    private String name;

    public AuthorDTO(){
    }

    public AuthorDTO(User obj){
        id=obj.getId();
        name= obj.getName();
    }

}
