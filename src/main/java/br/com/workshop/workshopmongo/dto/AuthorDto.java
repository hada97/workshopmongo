package br.com.workshop.workshopmongo.dto;

import br.com.workshop.workshopmongo.domain.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class AuthorDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank
    private String id;

    @NotBlank
    private String name;

    public AuthorDto(){
    }

    public AuthorDto(User obj){
        id=obj.getId();
        name= obj.getName();
    }

}
