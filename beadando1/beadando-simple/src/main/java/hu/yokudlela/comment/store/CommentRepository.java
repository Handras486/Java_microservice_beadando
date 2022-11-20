package hu.yokudlela.comment.store;

import hu.yokudlela.comment.datamodel.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Bejegyzések kezelése
 */
@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {


    /**
     * Kommentek lekérdezése
     * @return kommentek listája
     */
    public List<Comment> findAll();


    /**
     * Új komment létrehozása
     * @throws Exception
     */
    public Comment save(Comment pComment);

    /**
     * Meglévő komment törlése
     * @return sikeres művelet esetén true;
     */
    public void delete(Comment pPost);

}





