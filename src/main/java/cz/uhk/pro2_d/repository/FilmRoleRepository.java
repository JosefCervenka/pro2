package cz.uhk.pro2_d.repository;

import cz.uhk.pro2_d.model.Film;
import cz.uhk.pro2_d.model.FilmRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmRoleRepository extends JpaRepository<FilmRole, Long> {
    List<FilmRole> findByFilmId(long filmId);
    List<FilmRole> findByActorId(long actorId);
}