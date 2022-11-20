//package hu.yokudlela.forum.store;
//
//import hu.yokudlela.forum.datamodel.Topic;
//import org.apache.commons.beanutils.BeanUtils;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
//import java.lang.reflect.InvocationTargetException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
///**
// * Témák kezelése
//
// */
//@Service
//public class TopicRepository_old {
//    public static final List<Topic> topics = new ArrayList<>();
//
//    @PostConstruct
//    public void  init(){
//        topics.add(Topic.builder().name("Hírek").description("Hírek mindenhonnan a világból").build());
//        topics.add(Topic.builder().name("Viccek").description("Viccek").build());
//        topics.add(Topic.builder().name("Sport").description("Sport").build());
//        topics.add(Topic.builder().name("Kultúra").description("Kultúra").build());
//    }
//
//    /**
//     * Új Téma
//     *
//     * @param pTopic az új Téma leírása
//     * @throws Exception ha már ilyen névvel létezik téma
//     */
//    public void add(Topic pTopic) throws Exception {
//        if (getOptionalByName(pTopic.getName()).isEmpty()) {
//            TopicRepository_old.topics.add(pTopic);
//        } else throw new Exception();
//    }
//
//
//    /**
//     * Téma törlése
//     *
//     * @param pName a téma neve
//     * @return sikeres művelet esetén true;
//     */
//    public boolean delete(String pName) {
//        Optional<Topic> tmp = getOptionalByName(pName);
//        if (tmp.isEmpty()) return false;
//        return TopicRepository_old.topics.remove(tmp.get());
//    }
//
//    /**
//     * Téma módosítása
//     *
//     * @param pTopic az új téma leírása
//     * @throws IllegalAccessException
//     * @throws InvocationTargetException
//     */
//    public void modify(Topic pTopic) throws IllegalAccessException, InvocationTargetException {
//        Optional<Topic> tmp = getOptionalByName(pTopic.getName());
//        if (!tmp.isEmpty()) {
//            BeanUtils.copyProperties(tmp.get(), pTopic);
//        }
//    }
//
//    /**
//     * Téma lekérdezés név alapján
//     * @param pName téma neve
//     * @return Téma
//     */
//    public Optional<Topic> getOptionalByName(String pName){
//        Optional<Topic> res =topics.stream().filter(
//                element -> element.getName().equals(pName)
//        ).findFirst();
//        return res;
//    }
//}