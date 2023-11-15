package io.my_kinda_space.social_network.repo;

import io.my_kinda_space.social_network.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findPersonByName(String name);
    //todo - people with same name
}
