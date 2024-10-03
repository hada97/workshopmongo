package br.com.workshop.workshopmongo.config;

import br.com.workshop.workshopmongo.domain.Post;
import br.com.workshop.workshopmongo.domain.User;
import br.com.workshop.workshopmongo.dto.AuthorDto;
import br.com.workshop.workshopmongo.dto.CommentDTO;
import br.com.workshop.workshopmongo.repository.PostRepository;
import br.com.workshop.workshopmongo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.TimeZone;

//@Configuration para carregar a casse
public class Instantiation implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public void run(String... args) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        postRepository.deleteAll();

        User maria = new User(null, "Maria Brown", "maria@gmail.com");
        User alex = new User(null, "Alex Green", "alex@gmail.com");
        User bob = new User(null, "Bob Grey", "bob@gmail.com");
        userRepository.saveAll(Arrays.asList(maria,alex,bob));
        System.out.println("---------------BANCO INSERIU DADOS--------------------");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        // Cria um LocalDateTime a partir de uma string
        LocalDateTime dateTime = LocalDateTime.parse("21/03/2018 10:15", formatter);
        LocalDateTime dateTime2 = LocalDateTime.parse("22/03/2018 09:15", formatter);

        // Criação do Post com LocalDateTime
        Post post1 = new Post(null, dateTime, "Partiu viagem", "Vou viajar pra Manaus!", new AuthorDto(maria));
        Post post2 = new Post(null, dateTime2, "Dia top", "Acordei feliz!", new AuthorDto(maria));

        CommentDTO c1= new CommentDTO("Boa viagem!", sdf.parse("21/03/2018"), new AuthorDto(alex));
        post1.getComments().addAll(Arrays.asList(c1));

        postRepository.saveAll(Arrays.asList(post1,post2));

        maria.getPosts().addAll(Arrays.asList(post1,post2));
        userRepository.save(maria);

    }

}
