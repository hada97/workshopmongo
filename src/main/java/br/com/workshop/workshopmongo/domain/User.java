package br.com.workshop.workshopmongo.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Document(collection = "user")
@Setter
@Getter
public class User implements Serializable {

    @Serial
    private  static final long serialVersionUID=1L;

    @Id
    private String id;
    private String email;
    private String name;

    public User(){
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public User(String id, String name, String email){
        super();
        this.id = id;
        this.name = name;
        this.email = email;
    }

}
