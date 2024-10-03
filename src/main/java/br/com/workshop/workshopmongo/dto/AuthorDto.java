package br.com.workshop.workshopmongo.dto;

import br.com.workshop.workshopmongo.domain.User;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
public class AuthorDto implements Serializable {

    private String id;
    private String name;

    public AuthorDto(){
    }

    public AuthorDto(User obj){
        id=obj.getId();
        name= obj.getName();
    }

    public AuthorDto(AuthorDto author) {
    }

}
