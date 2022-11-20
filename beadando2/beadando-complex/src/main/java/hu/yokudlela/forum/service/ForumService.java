package hu.yokudlela.forum.service;

import hu.yokudlela.forum.datamodel.Post;
import hu.yokudlela.forum.store.PostRepository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Fórum kezelése
 */
@Service
public class ForumService {
    @Autowired
    PostRepository pRep;

    public List<Post> getAllPostsByTopicName(String pTopicName)
    {
        return pRep.findAll().stream().filter(element -> element.getTopic().getName().equals(pTopicName)).collect(Collectors.toList());
    }

}
