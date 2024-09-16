package br.com.workshop.workshopmongo.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "user")
@Setter
@Getter
@EqualsAndHashCode
public class User implements Serializable {

    @Serial
    private  static final long serialVersionUID=1L;

    @Id
    private String id;
    private String email;
    private String name;

    @DBRef(lazy = true)
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
