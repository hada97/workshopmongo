package br.com.workshop.workshopmongo.repository;

import br.com.workshop.workshopmongo.domain.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.yaml.snakeyaml.events.Event;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findByEmail(String email);

    Optional<User> findById(Id id);
}
