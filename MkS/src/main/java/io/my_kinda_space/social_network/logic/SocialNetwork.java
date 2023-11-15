package io.my_kinda_space.social_network.logic;

import io.my_kinda_space.social_network.domain.Person;
import io.my_kinda_space.social_network.domain.PersonDTO;
import io.my_kinda_space.social_network.repo.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SocialNetwork {
    private final PersonRepository personRepository;

    public SocialNetwork(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    public PersonDTO addPerson(PersonDTO personDTO) {
        var person = transformPersonDtoToPerson(personDTO);
        return transformPersonToPersonDto(personRepository.save(person));
    }
    //POST a PersonDTO with only its name to “/persons” -> transforms the PersonDTO into a Person, saves the Person, transforms it back to a PersonDTO and returns the new PersonDTO.

    public List<PersonDTO> getAllPeople() {
        var persons = personRepository.findAll();
        return persons.stream().map(person -> transformPersonToPersonDto(person)).collect(Collectors.toList());
    }
    //GET to “/persons” -> returns all saved Persons as a list of PersonDTO.


    public List<String> friendTwoPersons(Long id1, Long id2) {
        var oPerson1 = personRepository.findById(id1);
        if (oPerson1.isPresent()) {
            var oPerson2 = personRepository.findById(id2);
            if (oPerson2.isPresent()) {
                var person1 = oPerson1.get();
                var person2 = oPerson2.get();
                if (!person1.getFriends().contains(person2)) {
                    person1.getFriends().add(person2);
                    person2.getFriends().add(person1);
                    personRepository.save(person1);
                    personRepository.save(person2);
                }
                return person1.getFriends()
                        .stream()
                        .map(pers -> pers.getName())
                        .collect(Collectors.toList());
            }
        }
        return List.of();
    }
    public List<String> unfriendTwoPersons(Long id1, Long id2) {
        //TODO
        var oPerson1 = personRepository.findById(id1);
        if (oPerson1.isPresent()) {
            var oPerson2 = personRepository.findById(id2);
            if (oPerson2.isPresent()) {
                var person1 = oPerson1.get();
                var person2 = oPerson2.get();
                person1.getFriends().remove(person2);
                person2.getFriends().remove(person1);
                personRepository.save(person1);
                personRepository.save(person2);
                return person1.getFriends()
                        .stream()
                        .map(pers -> pers.getName())
                        .collect(Collectors.toList());
            }
        }
        return List.of();
    }

    //PUT to “/persons/{id1}/friend/{id2}” -> connects the two Persons with id1 and id2 as friends.
    //PUT to “/persons/{id1}/unfriend/{id2}” -> disconnects the friendship between the two Persons with id1 and id2.

    private Person transformPersonDtoToPerson(PersonDTO personDTO) {
        //todo - people with same name
//        var friends = personDTO.getFriendNames().stream().map(friendName -> personRepository.findPersonByName(friendName)).collect(Collectors.toList());
//        Person person = new Person(personDTO.getName(), friends);
//        person.setId(personDTO.getId());
//        return person;
        var person = new Person();
        person.setName(personDTO.getName());
        person.setId(personDTO.getId());
        for (String friendName : personDTO.getFriendNames()) {
            Person friend = new Person();
            friend.setName(friendName);
            person.getFriends().add(friend);
        }
        return person;
    }
    private PersonDTO transformPersonToPersonDto(Person person) {
        //todo - people with same name
//        var friendNames = person.getFriends().stream().map(friend -> friend.getName()).collect(Collectors.toList());
//        PersonDTO personDTO = new PersonDTO(person.getName(), friendNames);
//        personDTO.setId(person.getId());
        var personDTO = new PersonDTO();
        personDTO.setName(person.getName());
        personDTO.setId(person.getId());
        for (Person friend : person.getFriends()) {
            personDTO.getFriendNames().add(friend.getName());
        }
        return personDTO;
    }
}
