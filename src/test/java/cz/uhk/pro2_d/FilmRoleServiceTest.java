package cz.uhk.pro2_d;

import cz.uhk.pro2_d.model.FilmRole;
import cz.uhk.pro2_d.repository.FilmRoleRepository;
import cz.uhk.pro2_d.service.service.FilmRoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FilmRoleServiceTest {

    @Mock
    private FilmRoleRepository filmRoleRepository;

    @InjectMocks
    private FilmRoleService filmRoleService;

    private FilmRole filmRole1;
    private FilmRole filmRole2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        filmRole1 = new FilmRole();
        filmRole1.setId(1L);
        filmRole1.setRoleName("Lead Actor");

        filmRole2 = new FilmRole();
        filmRole2.setId(2L);
        filmRole2.setRoleName("Supporting Actress");
    }

    @Test
    void findAll_shouldReturnAllFilmRoles() {
        List<FilmRole> filmRoles = Arrays.asList(filmRole1, filmRole2);
        when(filmRoleRepository.findAll()).thenReturn(filmRoles);

        List<FilmRole> result = filmRoleService.findAll();

        assertEquals(2, result.size());
        assertEquals(filmRoles, result);
        verify(filmRoleRepository, times(1)).findAll();
    }

    @Test
    void findById_shouldReturnFilmRoleIfExists() {
        when(filmRoleRepository.findById(1L)).thenReturn(Optional.of(filmRole1));

        FilmRole result = filmRoleService.findById(1L);

        assertNotNull(result);
        assertEquals(filmRole1, result);
        verify(filmRoleRepository, times(1)).findById(1L);
    }

    @Test
    void findById_shouldReturnNullIfFilmRoleNotFound() {
        when(filmRoleRepository.findById(1L)).thenReturn(Optional.empty());

        FilmRole result = filmRoleService.findById(1L);

        assertNull(result);
        verify(filmRoleRepository, times(1)).findById(1L);
    }

    @Test
    void save_shouldSaveAndReturnFilmRole() {
        when(filmRoleRepository.save(filmRole1)).thenReturn(filmRole1);

        FilmRole result = filmRoleService.save(filmRole1);

        assertNotNull(result);
        assertEquals(filmRole1, result);
        verify(filmRoleRepository, times(1)).save(filmRole1);
    }

    @Test
    void update_shouldSaveAndReturnUpdatedFilmRole() {
        when(filmRoleRepository.save(filmRole2)).thenReturn(filmRole2);

        FilmRole result = filmRoleService.update(filmRole2);

        assertNotNull(result);
        assertEquals(filmRole2, result);
        verify(filmRoleRepository, times(1)).save(filmRole2);
    }

    @Test
    void delete_shouldDeleteFilmRoleIfExists() {
        when(filmRoleRepository.findById(1L)).thenReturn(Optional.of(filmRole1));
        doNothing().when(filmRoleRepository).delete(filmRole1);

        FilmRole result = filmRoleService.delete(1L);

        assertEquals(filmRole1, result);
        verify(filmRoleRepository, times(1)).findById(1L);
        verify(filmRoleRepository, times(1)).delete(filmRole1);
    }

    @Test
    void delete_shouldThrowExceptionIfFilmRoleNotFound() {
        when(filmRoleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> filmRoleService.delete(1L));
        verify(filmRoleRepository, times(1)).findById(1L);
        verify(filmRoleRepository, never()).delete(any());
    }

    @Test
    void getFilmRoleByFilmId_shouldReturnFilmRolesForGivenFilmId() {
        List<FilmRole> filmRoles = Arrays.asList(filmRole1);
        when(filmRoleRepository.findByFilmId(10L)).thenReturn(filmRoles);

        List<FilmRole> result = filmRoleService.getFilmRoleByFilmId(10L);

        assertEquals(1, result.size());
        assertEquals(filmRole1, result.get(0));
        verify(filmRoleRepository, times(1)).findByFilmId(10L);
    }

    @Test
    void getFilmRoleByActorId_shouldReturnFilmRolesForGivenActorId() {
        List<FilmRole> filmRoles = Arrays.asList(filmRole2);
        when(filmRoleRepository.findByActorId(20L)).thenReturn(filmRoles);

        List<FilmRole> result = filmRoleService.getFilmRoleByActorId(20L);

        assertEquals(1, result.size());
        assertEquals(filmRole2, result.get(0));
        verify(filmRoleRepository, times(1)).findByActorId(20L);
    }
}