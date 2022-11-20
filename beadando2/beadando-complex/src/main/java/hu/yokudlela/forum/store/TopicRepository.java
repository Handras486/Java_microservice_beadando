package hu.yokudlela.forum.store;

import hu.yokudlela.forum.datamodel.Post;
import hu.yokudlela.forum.datamodel.Topic;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import javax.annotation.PostConstruct;
import javax.persistence.Table;

/**
 * Témák kezelése
 */
@Repository
public interface TopicRepository extends CrudRepository<Topic, Long> {

    /**
     * Új Téma
     *
     * @param pTopic az új Téma leírása
     * @throws java.lang.Exception ha már ilyen névvel létezik téma
     */
    public Topic save(Topic pTopic);


    /**
     * Téma törlése
     *
     * @param pName a téma neve
     * @return sikeres művelet esetén true;
     */
    public void delete(Topic pTopic);

    /**
     * Téma lekérdezés név alapján
     *
     * @param pName téma neve
     * @return Téma
     */
    public Topic findByName(String pName);
}