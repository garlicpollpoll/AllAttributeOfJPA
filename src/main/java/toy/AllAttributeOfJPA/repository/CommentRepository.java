package toy.AllAttributeOfJPA.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import toy.AllAttributeOfJPA.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @EntityGraph(attributePaths = {"member"})
    @Query("select c from Comment c where c.item.id = :itemId")
    List<Comment> itemComment(@Param("itemId") Long itemId);
}