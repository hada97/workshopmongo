package br.com.workshop.workshopmongo.domain;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "user")
@Setter
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class User implements Serializable {

    @Serial
    private  static final long serialVersionUID=1L;

    @Id
    private String id;

    @NotBlank
    @Email(message = "Email should be valid")
    @Indexed(unique = true)
    private String email;

    @Size(min = 3, max = 24)
    @NotBlank
    private String name;

    @DBRef(lazy = false)
    private List<Post> posts = new ArrayList<>();

    public User(){
    }

    public User(String id, String name, String email){
        super();
        this.id = id;
        this.name = name;
        this.email = email;
    }

}
