package br.com.workshop.workshopmongo.dto;

import br.com.workshop.workshopmongo.domain.User;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class AuthorDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;

    @NotNull
    private String name;

    public AuthorDto(){
    }

    public AuthorDto(User obj){
        id=obj.getId();
        name= obj.getName();
    }

}
