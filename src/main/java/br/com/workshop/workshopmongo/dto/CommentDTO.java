package br.com.workshop.workshopmongo.dto;

import lombok.Getter;
import lombok.Setter;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
public class CommentDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String text;
    private Date date;
    private AuthorDto author;

    public CommentDTO(){

    }

    public CommentDTO(String text, Date date, AuthorDto authorDto) {
        this.text = text;
        this.date = date;
        this.author = new AuthorDto(authorDto);
    }
}
