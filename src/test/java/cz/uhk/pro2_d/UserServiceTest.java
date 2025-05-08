package cz.uhk.pro2_d;

import cz.uhk.pro2_d.model.User;
import cz.uhk.pro2_d.repository.UserRepository;
import cz.uhk.pro2_d.security.MyUserDetails;
import cz.uhk.pro2_d.service.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.List;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setPassword("testPassword");
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = List.of(user);
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertEquals(1, result.size());
        assertEquals("testUser", result.get(0).getUsername());
    }

    @Test
    public void testSaveUser() {
        userService.saveUser(user);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testGetUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUser(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("testUser", result.getUsername());
    }

    @Test
    public void testGetUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        User result = userService.getUser(1L);

        assertNull(result);
    }

    @Test
    public void testDeleteUser() {
        userService.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testLoadUserByUsername() {
        when(userRepository.findByUsername("testUser")).thenReturn(user);

        UserDetails userDetails = userService.loadUserByUsername("testUser");

        assertNotNull(userDetails);
        assertTrue(userDetails instanceof MyUserDetails);
        assertEquals("testUser", userDetails.getUsername());
    }

    @Test
    public void testLoadUserByUsernameNotFound() {
        when(userRepository.findByUsername("testUser")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername("testUser");
        });
    }
}
