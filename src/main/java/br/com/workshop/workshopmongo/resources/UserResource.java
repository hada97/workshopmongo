package br.com.workshop.workshopmongo.resources;

import br.com.workshop.workshopmongo.domain.User;
import br.com.workshop.workshopmongo.dto.UserDto;
import br.com.workshop.workshopmongo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserResource {

    //Classe equivale a Controller

    @Autowired
    private UserService service;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UserDto>> findAll(){
        List<User> list = service.findAll();
        List<UserDto> listDto = list.stream().map(UserDto::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable String id){
        User obj = service.findById(id);
        return ResponseEntity.ok().body(new UserDto(obj));
    }


    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody UserDto objDto){
        User obj = service.fromDto(objDto);
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }



}
