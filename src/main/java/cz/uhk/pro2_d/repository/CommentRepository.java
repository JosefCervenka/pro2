package cz.uhk.pro2_d.repository;

import cz.uhk.pro2_d.model.Comment;
import cz.uhk.pro2_d.model.FilmRole;
import cz.uhk.pro2_d.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
