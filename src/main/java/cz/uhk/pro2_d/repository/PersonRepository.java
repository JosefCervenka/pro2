package cz.uhk.pro2_d.repository;

import cz.uhk.pro2_d.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}
