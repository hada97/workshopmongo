package br.com.workshop.workshopmongo.controllers;

import br.com.workshop.workshopmongo.domain.Post;
import br.com.workshop.workshopmongo.dto.PostDto;
import br.com.workshop.workshopmongo.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService service;

    @GetMapping
    public ResponseEntity<List<PostDto>> findAll() {
        List<Post> list = service.findAll();
        List<PostDto> listDto = list.stream().map(PostDto::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> findById(@PathVariable String id) {
        Post obj = service.findById(id);
        return ResponseEntity.ok().body(new PostDto(obj));
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody PostDto objDto) {
        Post obj = service.fromDto(objDto);
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody PostDto objDto, @PathVariable String id) {
        Post obj = service.fromDto(objDto);
        obj.setId(id);
        obj = service.update(obj);
        return ResponseEntity.noContent().build();
    }
}
