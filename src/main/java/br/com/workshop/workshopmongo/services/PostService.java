package br.com.workshop.workshopmongo.services;

import br.com.workshop.workshopmongo.domain.Post;
import br.com.workshop.workshopmongo.domain.User;
import br.com.workshop.workshopmongo.dto.UserDto;
import br.com.workshop.workshopmongo.repository.PostRepository;
import br.com.workshop.workshopmongo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PostService {

    @Autowired
    private PostRepository repository;

    public Post findById(String id) {
        Optional<Post> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

}
