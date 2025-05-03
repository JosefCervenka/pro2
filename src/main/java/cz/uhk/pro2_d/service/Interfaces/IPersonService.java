package cz.uhk.pro2_d.service.Interfaces;

import cz.uhk.pro2_d.model.Person;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface IPersonService extends IEntityService<Person> {

}
