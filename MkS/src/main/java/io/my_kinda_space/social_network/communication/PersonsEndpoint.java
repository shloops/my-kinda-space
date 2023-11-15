package io.my_kinda_space.social_network.communication;

import io.my_kinda_space.social_network.domain.PersonDTO;
import io.my_kinda_space.social_network.logic.SocialNetwork;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonsEndpoint {
    private final SocialNetwork socialNetwork;

    public PersonsEndpoint(SocialNetwork socialNetwork) {
        this.socialNetwork = socialNetwork;
    }

    @PostMapping
    PersonDTO addPerson(@RequestBody PersonDTO personDTO) {
        return socialNetwork.addPerson(personDTO);
    }
    //POST a PersonDTO with only its name to “/persons” -> transforms the PersonDTO into a Person, saves the Person, transforms it back to a PersonDTO and returns the new PersonDTO.

    @GetMapping
    List<PersonDTO> getAllPeople() {
        return socialNetwork.getAllPeople();
    }
    //GET to “/persons” -> returns all saved Persons as a list of PersonDTO.

    @PutMapping("/{id1}/friend/{id2}")
    List<String> friendTwoPersons(@PathVariable Long id1, @PathVariable Long id2) {
        return socialNetwork.friendTwoPersons(id1, id2);
    }
    @PutMapping("/{id1}/unfriend/{id2}")
    List<String> unfriendTwoPersons(@PathVariable Long id1, @PathVariable Long id2) {
        return socialNetwork.unfriendTwoPersons(id1, id2);
    }
    //PUT to “/persons/{id1}/friend/{id2}” -> connects the two Persons with id1 and id2 as friends.
    //PUT to “/persons/{id1}/unfriend/{id2}” -> disconnects the friendship between the two Persons with id1 and id2.

}
