package be.pxl.services.repository;

import be.pxl.services.domain.RejectionReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IReviewRepository extends JpaRepository<RejectionReview, Long> {
    RejectionReview findByPostId(Long postId);
}
