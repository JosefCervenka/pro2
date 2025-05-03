package cz.uhk.pro2_d.service.Interfaces;

import cz.uhk.pro2_d.model.FilmRole;

import java.util.List;

public interface IFilmRoleService extends IEntityService<FilmRole> {

    List<FilmRole> getFilmRoleByFilmId(long filmId);

    List<FilmRole> getFilmRoleByActorId(long actorId);
}
