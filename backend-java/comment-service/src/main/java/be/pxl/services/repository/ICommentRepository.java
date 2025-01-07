package be.pxl.services.repository;

import be.pxl.services.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICommentRepository extends JpaRepository<Comment, Long> {}
