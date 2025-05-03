package cz.uhk.pro2_d.service.Interfaces;

import cz.uhk.pro2_d.model.Person;

import java.util.List;

public interface IEntityService<T>  {
    List<T> findAll();

    T findById(long id);

    T save(T entity);

    T update(T entity);

    T delete(long id);
}
