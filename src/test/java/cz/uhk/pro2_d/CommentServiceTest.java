package cz.uhk.pro2_d;


import cz.uhk.pro2_d.model.Comment;
import cz.uhk.pro2_d.model.Film;
import cz.uhk.pro2_d.model.User;
import cz.uhk.pro2_d.repository.CommentRepository;
import cz.uhk.pro2_d.service.service.CommentService;
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

class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    private Comment comment1;
    private Comment comment2;
    private Film film;
    private User author;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        film = new Film();
        film.setId(1L);
        film.setName("Test Film");

        author = new User();
        author.setId(1L);
        author.setName("Test User");

        comment1 = new Comment();
        comment1.setId(1L);
        comment1.setText("This is a great film!");
        comment1.setFilm(film);
        comment1.setAuthor(author);

        comment2 = new Comment();
        comment2.setId(2L);
        comment2.setText("I enjoyed it.");
        comment2.setFilm(film);
        comment2.setAuthor(author);
    }

    @Test
    void findAll_shouldReturnAllComments() {
        List<Comment> comments = Arrays.asList(comment1, comment2);
        when(commentRepository.findAll()).thenReturn(comments);

        List<Comment> result = commentService.findAll();

        assertEquals(2, result.size());
        assertEquals(comments, result);
        verify(commentRepository, times(1)).findAll();
    }

    @Test
    void findById_shouldReturnCommentIfExists() {
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment1));

        Comment result = commentService.findById(1L);

        assertNotNull(result);
        assertEquals(comment1, result);
        verify(commentRepository, times(1)).findById(1L);
    }

    @Test
    void findById_shouldReturnNullIfCommentNotFound() {
        when(commentRepository.findById(1L)).thenReturn(Optional.empty());

        Comment result = commentService.findById(1L);

        assertNull(result);
        verify(commentRepository, times(1)).findById(1L);
    }

    @Test
    void save_shouldSaveAndReturnComment() {
        when(commentRepository.save(comment1)).thenReturn(comment1);

        Comment result = commentService.save(comment1);

        assertNotNull(result);
        assertEquals(comment1, result);
        verify(commentRepository, times(1)).save(comment1);
    }

    @Test
    void update_shouldSaveAndReturnUpdatedComment() {
        Comment updatedComment = new Comment();
        updatedComment.setId(2L);
        updatedComment.setText("This film is amazing!");
        updatedComment.setFilm(film);
        updatedComment.setAuthor(author);
        when(commentRepository.save(updatedComment)).thenReturn(updatedComment);

        Comment result = commentService.update(updatedComment);

        assertNotNull(result);
        assertEquals(updatedComment, result);
        verify(commentRepository, times(1)).save(updatedComment);
    }

    @Test
    void delete_shouldDeleteCommentIfExists() {
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment1));
        doNothing().when(commentRepository).delete(comment1);

        Comment result = commentService.delete(1L);

        assertEquals(comment1, result);
        verify(commentRepository, times(1)).findById(1L);
        verify(commentRepository, times(1)).delete(comment1);
    }

    @Test
    void delete_shouldThrowExceptionIfCommentNotFound() {
        when(commentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> commentService.delete(1L));
        verify(commentRepository, times(1)).findById(1L);
        verify(commentRepository, never()).delete(any());
    }
}