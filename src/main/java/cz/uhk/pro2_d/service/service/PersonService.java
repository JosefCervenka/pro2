package cz.uhk.pro2_d.service.service;

import cz.uhk.pro2_d.model.Person;
import cz.uhk.pro2_d.repository.PersonRepository;
import cz.uhk.pro2_d.service.Interfaces.IPersonService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService implements IPersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public Person findById(long id) {
        return personRepository.findById(id).orElse(null);
    }

    @Override
    public Person save(Person person) {
        return personRepository.save(person);
    }

    @Override
    public Person update(Person person) {
        return personRepository.save(person);
    }

    @Override
    public Person delete(long id) {
        var person = personRepository.findById(id);

        if (person.isPresent()) {
            personRepository.delete(person.get());
            return person.get();
        }

        throw new RuntimeException("Person not found");
    }
}
