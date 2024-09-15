package br.com.workshop.workshopmongo.domain;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
public class User implements Serializable {

    private  static final long serialVersionUID=1l;

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
