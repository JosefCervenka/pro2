package cz.uhk.pro2_d;

import cz.uhk.pro2_d.model.Film;
import cz.uhk.pro2_d.repository.FilmRepository;
import cz.uhk.pro2_d.service.service.FilmService;
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

class FilmServiceTest {

    @Mock
    private FilmRepository filmRepository;

    @InjectMocks
    private FilmService filmService;

    private Film film1;
    private Film film2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        film1 = new Film();
        film1.setId(1L);
        film1.setName("The Shawshank Redemption");
        film1.setGenre("Drama");
        film1.setDateOfCreation(new Date());

        film2 = new Film();
        film2.setId(2L);
        film2.setName("Pulp Fiction");
        film2.setGenre("Crime");
        film2.setDateOfCreation(new Date());
    }

    @Test
    void findAll_shouldReturnAllFilms() {
        List<Film> films = Arrays.asList(film1, film2);
        when(filmRepository.findAll()).thenReturn(films);

        List<Film> result = filmService.findAll();

        assertEquals(2, result.size());
        assertEquals(films, result);
        verify(filmRepository, times(1)).findAll();
    }

    @Test
    void findById_shouldReturnFilmIfExists() {
        when(filmRepository.findById(1L)).thenReturn(Optional.of(film1));

        Film result = filmService.findById(1L);

        assertNotNull(result);
        assertEquals(film1, result);
        verify(filmRepository, times(1)).findById(1L);
    }

    @Test
    void findById_shouldReturnNullIfFilmNotFound() {
        when(filmRepository.findById(1L)).thenReturn(Optional.empty());

        Film result = filmService.findById(1L);

        assertNull(result);
        verify(filmRepository, times(1)).findById(1L);
    }

    @Test
    void save_shouldSaveAndReturnFilm() {
        when(filmRepository.save(film1)).thenReturn(film1);

        Film result = filmService.save(film1);

        assertNotNull(result);
        assertEquals(film1, result);
        verify(filmRepository, times(1)).save(film1);
    }

    @Test
    void update_shouldSaveAndReturnUpdatedFilm() {
        Film updatedFilm = new Film();
        updatedFilm.setId(2L);
        updatedFilm.setName("Updated Pulp Fiction");
        updatedFilm.setGenre("Crime Thriller");
        updatedFilm.setDateOfCreation(new Date());
        when(filmRepository.save(updatedFilm)).thenReturn(updatedFilm);

        Film result = filmService.update(updatedFilm);

        assertNotNull(result);
        assertEquals(updatedFilm, result);
        verify(filmRepository, times(1)).save(updatedFilm);
    }

    @Test
    void delete_shouldDeleteFilmIfExists() {
        when(filmRepository.findById(1L)).thenReturn(Optional.of(film1));
        doNothing().when(filmRepository).delete(film1);

        Film result = filmService.delete(1L);

        assertEquals(film1, result);
        verify(filmRepository, times(1)).findById(1L);
        verify(filmRepository, times(1)).delete(film1);
    }

    @Test
    void delete_shouldThrowExceptionIfFilmNotFound() {
        when(filmRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> filmService.delete(1L));
        verify(filmRepository, times(1)).findById(1L);
        verify(filmRepository, never()).delete(any());
    }
}