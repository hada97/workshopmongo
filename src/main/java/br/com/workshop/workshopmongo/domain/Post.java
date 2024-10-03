package br.com.workshop.workshopmongo.domain;

import br.com.workshop.workshopmongo.dto.AuthorDto;
import br.com.workshop.workshopmongo.dto.CommentDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.data.annotation.Id;

@Document(collection = "post")
@Getter
@Setter
@EqualsAndHashCode
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private LocalDateTime dateTime;
    private String title;
    private String body;
    private AuthorDto author;

    private List<CommentDTO> comments = new ArrayList<>();

    public Post() {
        this.dateTime = LocalDateTime.now();
    }

    public Post(String id, LocalDateTime dateTime, String title, String body, AuthorDto author) {
        super();
        this.id = id;
        this.dateTime = LocalDateTime.now();
        this.title = title;
        this.body = body;
        this.author = author;
    }
}


