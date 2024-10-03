package br.com.workshop.workshopmongo.dto;

import br.com.workshop.workshopmongo.domain.Post;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
public class PostDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private LocalDateTime dateTime;  // Certifique-se de adicionar a data
    private String title;   // Adicione o título do post
    private String body;    // Adicione o corpo do post
    private AuthorDto author; // Altere para o tipo correto

    public PostDto() {
    }

    public PostDto(Post obj) {
        this.id = obj.getId();
        this.dateTime = LocalDateTime.now();
        this.title = obj.getTitle();
        this.body = obj.getBody();
        this.author = new AuthorDto(obj.getAuthor()); // Supondo que você tenha um AuthorDto
    }

}
