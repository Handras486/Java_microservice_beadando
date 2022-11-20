//package hu.yokudlela.forum.store;
//
//import hu.yokudlela.forum.datamodel.Post;
//import hu.yokudlela.forum.datamodel.Topic;
//import org.apache.commons.beanutils.BeanUtils;
//import org.springframework.stereotype.Service;
//
//import java.lang.reflect.InvocationTargetException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
///*
//
///**
// * Bejegyzések kezelése
// */
//@Service
//public class PostRepository_old {
//    private static final List<Post> posts = new ArrayList<>();
//
//
//
//    /**
//     * Bejegyzések lekérdezése
//     * @return bejegyzések listája
//     */
//    public List<Post> getAll(){
//        return posts.stream().collect(Collectors.toList());
//    }
//
//    /**
//     * Bejegyzés lekérdezés cím alapján
//     * @param pTitle bejegyzés egyedi címe
//     * @throws NullPointerException Nincs ilyen című bejegyzés
//     * @return bejegyzés
//     */
//    public Post getByTitle(String pTitle) throws NullPointerException{
//        Optional<Post> tmp = getOptionalByTitle(pTitle);
//        if(tmp.isEmpty()) throw new NullPointerException();
//        return tmp.get();
//    }
//
//
//
//    /**
//     * Új bejegyzés létrehozása
//     * @param pPost az új Bejegyzés leírása
//     * @throws Exception  ha már ilyen címmel létezik bejegyzés, vagy ha nem létezik a Topic
//     */
//    public void add(Post pPost) throws Exception{
//        if(getOptionalByTitle(pPost.getTitle()).isEmpty()){
//            Optional<Topic> topic = TopicRepository.topics.stream().filter(element -> element.getName().equals(pPost.getTopic_name())).findFirst();
//            if(topic.isPresent()) {
//                pPost.setTopic(topic.get());
//                PostRepository_old.posts.add(pPost);
//            }
//            else throw new Exception();
//        }
//        else throw new Exception();
//    }
//
//    /**
//     * Meglévő bejegyzés törlése
//     * @param pPost az bejegyzés leírása
//     * @return sikeres művelet esetén true;
//     */
//    public boolean delete(Post pPost){
//        return PostRepository_old.posts.remove(pPost);
//    }
//
//
//    /**
//     * Meglévő bejegyzés törlése
//     * @param pTitle a bejegyzés neve
//     * @return sikeres művelet esetén true;
//     */
//    public boolean delete(String pTitle){
//        Optional<Post> tmp = getOptionalByTitle(pTitle);
//        if(tmp.isEmpty()) return false;
//        return PostRepository_old.posts.remove(tmp.get());
//    }
//
//
//    /**
//     * Meglévő bejegyzés módosítása
//     * @param pPost a Bejegyzés új leírása
//     * @throws IllegalAccessException
//     * @throws InvocationTargetException
//     */
//    public void modify(Post pPost) throws IllegalAccessException, InvocationTargetException {
//        Optional<Post> tmp = getOptionalByTitle(pPost.getTitle());
//        if(!tmp.isEmpty()){
//            Optional<Topic> topic = TopicRepository.topics.stream().filter(element -> element.getName().equals(pPost.getTopic_name())).findFirst();
//            if(topic.isPresent()) {
//                BeanUtils.copyProperties(tmp.get(), pPost);
//                pPost.setTopic(topic.get());
//            }
//        }
//    }
//    
//    /**
//     * Bejegyzés lekérdezés cím alapján
//     * @param pName bejegyzés egyedi neve
//     * @return bejegyzés
//     */
//    public Optional<Post> getOptionalByTitle(String pName){
//        Optional<Post> res =posts.stream().filter(element -> element.getTitle().equals(pName)).findFirst();
//        return res;
//    }
//
//
//}
//
//
//
