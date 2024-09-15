package br.com.workshop.workshopmongo.services;

import br.com.workshop.workshopmongo.domain.User;
import br.com.workshop.workshopmongo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> findAll(){
        return repository.findAll();
    }

}
