package br.com.workshop.workshopmongo.config;

import br.com.workshop.workshopmongo.domain.Post;
import br.com.workshop.workshopmongo.domain.User;
import br.com.workshop.workshopmongo.dto.AuthorDto;
import br.com.workshop.workshopmongo.dto.CommentDTO;
import br.com.workshop.workshopmongo.repository.PostRepository;
import br.com.workshop.workshopmongo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

//Cria objetos no banco assim que inicializado
@Configuration
public class Instantiation implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public void run(String... args) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        userRepository.deleteAll();
        postRepository.deleteAll();

        User maria = new User(null, "Maria Brown", "maria@gmail.com");
        User alex = new User(null, "Alex Green", "alex@gmail.com");
        User bob = new User(null, "Bob Grey", "bob@gmail.com");
        userRepository.saveAll(Arrays.asList(maria,alex,bob));

        Post post1 = new Post(null, sdf.parse("21/03/2018"), "Partiu viagem", "Vou viajar pra Manaus!", new AuthorDto(maria));
        Post post2 = new Post(null, sdf.parse("23/03/2018"), "Dia top", "acordei feliz!", new AuthorDto(maria));

        CommentDTO c1= new CommentDTO("Boa viagem!", sdf.parse("21/03/2018"), new AuthorDto(alex));
        post1.getComments().addAll(Arrays.asList(c1));

        postRepository.saveAll(Arrays.asList(post1,post2));

        maria.getPosts().addAll(Arrays.asList(post1,post2));
        userRepository.save(maria);

    }

}
