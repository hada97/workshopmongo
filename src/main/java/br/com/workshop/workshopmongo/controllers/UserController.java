package br.com.workshop.workshopmongo.controllers;

import br.com.workshop.workshopmongo.domain.Post;
import br.com.workshop.workshopmongo.domain.User;
import br.com.workshop.workshopmongo.dto.UserDto;
import br.com.workshop.workshopmongo.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    @Transactional(readOnly = true)
    @Cacheable("users")
    public ResponseEntity<List<UserDto>> findAll(){
        List<User> list = service.findAll();
        List<UserDto> listDto = list.stream().map(UserDto::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<UserDto> findById(@PathVariable String id){
        User obj = service.findById(id);
        return ResponseEntity.ok().body(new UserDto(obj));
    }

    @PostMapping
    @CacheEvict(value = "users", allEntries = true)
    public ResponseEntity<Void> insert(@Valid @RequestBody UserDto objDto){
        User obj = service.fromDto(objDto);
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "users", allEntries = true)
    public ResponseEntity<Void> delete(@PathVariable String id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "users", allEntries = true)
    public ResponseEntity<Void> update(@Valid @RequestBody UserDto objDto, @PathVariable String id){
        User obj = service.fromDto(objDto);
        obj.setId(id);
        obj = service.update(obj);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/posts")
    @Cacheable
    public ResponseEntity<List<Post>> findPosts(@Valid @PathVariable String id){
        User obj = service.findById(id);
        return ResponseEntity.ok().body(obj.getPosts());
    }

}
