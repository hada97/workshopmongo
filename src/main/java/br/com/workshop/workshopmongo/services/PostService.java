package br.com.workshop.workshopmongo.services;

import br.com.workshop.workshopmongo.domain.Post;
import br.com.workshop.workshopmongo.dto.PostDto;
import br.com.workshop.workshopmongo.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository repository;

    public List<Post> findAll() {
        return repository.findAll();
    }

    public Post findById(String id) {
        Optional<Post> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    public Post insert(Post obj) {
        return repository.insert(obj);
    }

    public void delete(String id) {
        findById(id);
        repository.deleteById(id);
    }

    public Post update(Post obj) {
        Post newObj = findById(obj.getId());
        updateData(newObj, obj);
        return repository.save(newObj);
    }

    private void updateData(Post newObj, Post obj) {
        newObj.setTitle(obj.getTitle());
        newObj.setBody(obj.getBody());
    }

    //CRIA UM USER COM OS DADOS DO DTO
    public Post fromDto(PostDto objDto) {
        return new Post(objDto.getId(), objDto.getDate(), objDto.getTitle(), objDto.getBody(), objDto.getAuthor());
    }
}