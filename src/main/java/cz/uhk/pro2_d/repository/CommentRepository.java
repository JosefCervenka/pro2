package cz.uhk.pro2_d.repository;

import cz.uhk.pro2_d.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
