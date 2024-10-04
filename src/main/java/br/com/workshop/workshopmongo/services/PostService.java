package br.com.workshop.workshopmongo.services;

import br.com.workshop.workshopmongo.domain.Post;
import br.com.workshop.workshopmongo.domain.User;
import br.com.workshop.workshopmongo.repository.PostRepository;
import br.com.workshop.workshopmongo.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository repository;

    @Autowired
    private UserRepository userRepository;

    public List<Post> findAll() {
        return repository.findAll();
    }

    public Post findById(@Valid String id) {
        Optional<Post> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    public Post insert(@Valid Post obj) {
        return repository.insert(obj);
    }

    public void delete(@Valid String id) {
        findById(id);
        repository.deleteById(id);
    }

    public Post update(@Valid Post obj) {
        Post newObj = findById(obj.getId());
        updateData(newObj, obj);
        return repository.save(newObj);
    }

    private void updateData(Post newObj, Post obj) {
        newObj.setTitle(obj.getTitle());
        newObj.setBody(obj.getBody());
    }

    //CRIA UM USER COM OS DADOS DO DTO
    public Post fromDto(Post objDto) {
        return new Post(objDto.getId(), objDto.getDateTime(), objDto.getTitle(), objDto.getBody(), objDto.getAuthor());
    }


    public void atualizarUser(Post obj) {
        // Localiza o usuário pelo ID
        User user = userRepository.findById((obj.getAuthor().getId()))
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado")); // ou uma exceção específica
        // Associa o post ao usuário
        user.getPosts().add(obj);
        // Salva o usuário com o novo post
        userRepository.save(user);
        // Salva e retorna o post
    }

}



