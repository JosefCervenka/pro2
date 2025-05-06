package cz.uhk.pro2_d.service.service;

import cz.uhk.pro2_d.model.Comment;
import cz.uhk.pro2_d.repository.CommentRepository;
import cz.uhk.pro2_d.service.Interfaces.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService implements ICommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment findById(long id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment update(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment delete(long id) {
        var comment = commentRepository.findById(id);

        if (comment.isPresent()) {
            commentRepository.delete(comment.get());
            return comment.get();
        }

        throw new RuntimeException("Film not found");
    }
}
