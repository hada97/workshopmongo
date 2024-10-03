package br.com.workshop.workshopmongo.services;

import br.com.workshop.workshopmongo.controllers.exceptions.EmailAlreadyExistsException;
import br.com.workshop.workshopmongo.domain.User;
import br.com.workshop.workshopmongo.dto.UserDto;
import br.com.workshop.workshopmongo.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(@Valid String id) {
        Optional<User> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    public User insert(@Valid User obj) {
        if (repository.findByEmail(obj.getEmail()) != null) {
            throw new EmailAlreadyExistsException("O email já está cadastrado");
        }
        return repository.insert(obj);
    }

    public void delete(String id) {
        findById(id);
        repository.deleteById(id);
    }

    public User update(@Valid User obj) {
        User newObj = findById(obj.getId());
        updateData(newObj, obj);
        return repository.save(newObj);
    }

    private void updateData(User newObj, User obj) {
        newObj.setName(obj.getName());
        newObj.setEmail(obj.getEmail());
    }

    //CRIA UM USER COM OS DADOS DO DTO
    public User fromDto(UserDto objDto) {
        return new User(objDto.getId(), objDto.getName(), objDto.getEmail());
    }
}
