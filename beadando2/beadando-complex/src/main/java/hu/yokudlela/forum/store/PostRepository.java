package hu.yokudlela.forum.store;

import hu.yokudlela.forum.datamodel.Post;
import hu.yokudlela.forum.datamodel.Topic;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Bejegyzések kezelése
 */
@Repository
public interface PostRepository extends CrudRepository<Post, Long> {


    /**
     * Bejegyzések lekérdezése
     * @return bejegyzések listája
     */
    public List<Post> findAll();

    /**
     * Bejegyzés lekérdezés cím alapján
     * @param pTitle bejegyzés egyedi címe
     * @throws java.lang.NullPointerException Nincs ilyen című bejegyzés
     * @return bejegyzés
     */
    public Post findByTitle(String pTitle);



    /**
     * Új bejegyzés létrehozása
     * @param pPost az új Bejegyzés leírása
     * @throws java.lang.Exception  ha már ilyen címmel létezik bejegyzés, vagy ha nem létezik a Topic
     */
    public Post save(Post pPost);

    /**
     * Meglévő bejegyzés törlése
     * @param pPost a bejegyzés egyedi címe
     * @return sikeres művelet esetén true;
     */
    public void delete(Post pPost);


}





