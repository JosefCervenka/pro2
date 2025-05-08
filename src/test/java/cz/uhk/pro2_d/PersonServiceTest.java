package cz.uhk.pro2_d;

import cz.uhk.pro2_d.model.Person;
import cz.uhk.pro2_d.repository.PersonRepository;
import cz.uhk.pro2_d.service.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    private Person person1;
    private Person person2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        person1 = new Person();
        person1.setId(1L);
        person1.setFirstName("John");
        person1.setLastName("Doe");
        person1.setBirthDate(new Date());

        person2 = new Person();
        person2.setId(2L);
        person2.setFirstName("Jane");
        person2.setLastName("Smith");
        person2.setBirthDate(new Date());
    }

    @Test
    void findAll_shouldReturnAllPersons() {
        List<Person> persons = Arrays.asList(person1, person2);
        when(personRepository.findAll()).thenReturn(persons);

        List<Person> result = personService.findAll();

        assertEquals(2, result.size());
        assertEquals(persons, result);
        verify(personRepository, times(1)).findAll();
    }

    @Test
    void findById_shouldReturnPersonIfExists() {
        when(personRepository.findById(1L)).thenReturn(Optional.of(person1));

        Person result = personService.findById(1L);

        assertNotNull(result);
        assertEquals(person1, result);
        verify(personRepository, times(1)).findById(1L);
    }

    @Test
    void findById_shouldReturnNullIfPersonNotFound() {
        when(personRepository.findById(1L)).thenReturn(Optional.empty());

        Person result = personService.findById(1L);

        assertNull(result);
        verify(personRepository, times(1)).findById(1L);
    }

    @Test
    void save_shouldSaveAndReturnPerson() {
        when(personRepository.save(person1)).thenReturn(person1);

        Person result = personService.save(person1);

        assertNotNull(result);
        assertEquals(person1, result);
        verify(personRepository, times(1)).save(person1);
    }

    @Test
    void update_shouldSaveAndReturnUpdatedPerson() {
        Person updatedPerson = new Person();
        updatedPerson.setId(2L);
        updatedPerson.setFirstName("Updated Jane");
        updatedPerson.setLastName("Updated Smith");
        updatedPerson.setBirthDate(new Date());
        when(personRepository.save(updatedPerson)).thenReturn(updatedPerson);

        Person result = personService.update(updatedPerson);

        assertNotNull(result);
        assertEquals(updatedPerson, result);
        verify(personRepository, times(1)).save(updatedPerson);
    }

    @Test
    void delete_shouldDeletePersonIfExists() {
        when(personRepository.findById(1L)).thenReturn(Optional.of(person1));
        doNothing().when(personRepository).delete(person1);

        Person result = personService.delete(1L);

        assertEquals(person1, result);
        verify(personRepository, times(1)).findById(1L);
        verify(personRepository, times(1)).delete(person1);
    }

    @Test
    void delete_shouldThrowExceptionIfPersonNotFound() {
        when(personRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> personService.delete(1L));
        verify(personRepository, times(1)).findById(1L);
        verify(personRepository, never()).delete(any());
    }
}