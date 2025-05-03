package cz.uhk.pro2_d.service.service;

import cz.uhk.pro2_d.model.Film;
import cz.uhk.pro2_d.repository.FilmRepository;
import cz.uhk.pro2_d.service.Interfaces.IFilmService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmService implements IFilmService {

    private final FilmRepository filmRepository;

    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @Override
    public List<Film> findAll() {
        return filmRepository.findAll();
    }

    @Override
    public Film findById(long id) {
        return filmRepository.findById(id).orElse(null);
    }

    @Override
    public Film save(Film film) {
        return filmRepository.save(film);
    }

    @Override
    public Film update(Film film) {
        return filmRepository.save(film);
    }

    @Override
    public Film delete(long id) {
        var film = filmRepository.findById(id);

        if (film.isPresent()) {
            filmRepository.delete(film.get());
            return film.get();
        }

        throw new RuntimeException("Film not found");
    }
}
