package cz.uhk.pro2_d.service.service;

import cz.uhk.pro2_d.model.FilmRole;
import cz.uhk.pro2_d.repository.FilmRoleRepository;
import cz.uhk.pro2_d.service.Interfaces.IFilmRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmRoleService implements IFilmRoleService {

    private final FilmRoleRepository filmRoleRepository;

    public FilmRoleService(FilmRoleRepository filmRoleRepository) {
        this.filmRoleRepository = filmRoleRepository;
    }

    @Override
    public List<FilmRole> findAll() {
        return filmRoleRepository.findAll();
    }

    @Override
    public FilmRole findById(long id) {
        return filmRoleRepository.findById(id).orElse(null);
    }

    @Override
    public FilmRole save(FilmRole filmRole) {
        return filmRoleRepository.save(filmRole);
    }

    @Override
    public FilmRole update(FilmRole filmRole) {
        return filmRoleRepository.save(filmRole);
    }

    @Override
    public FilmRole delete(long id) {
        var filmRole = filmRoleRepository.findById(id);

        if (filmRole.isPresent()) {
            filmRoleRepository.delete(filmRole.get());
            return filmRole.get();
        }

        throw new RuntimeException("Film not found");
    }

    @Override
    public List<FilmRole> getFilmRoleByFilmId(long id) {
        return filmRoleRepository.findByFilmId(id);
    }

    @Override
    public List<FilmRole> getFilmRoleByActorId(long id) {
        return filmRoleRepository.findByActorId(id);
    }
}
