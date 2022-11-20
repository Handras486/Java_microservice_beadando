package hu.yokudlela.forum.rest;

import hu.yokudlela.forum.datamodel.Post;
import hu.yokudlela.forum.store.PostRepository;
import hu.yokudlela.forum.service.ForumService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.reflect.InvocationTargetException;
import java.security.Principal;
import java.util.List;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Bejegyzéseket kezelő REST kontroller
 */
@RestController()
@RequestMapping(path = "/post")

public class PostController {

    @Autowired
    private PostRepository postService;

    @Autowired
    private ForumService  forumService;


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sikeres lekérdezés",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Post.class))) }),
    })
    @Operation(summary = "Az összes bejegyzés lekérdezése")
    @GetMapping(path = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Post> findAll()
    {
        return postService.findAll();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A keresett bejegyzés",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Post.class)) }),
            @ApiResponse(responseCode = "500", description = "Sikertelen lekérdezés",
                    content = { @Content(mediaType = "application/json") })
    })
    @Operation(summary = "Bejegyzés lekérdezés cím alapján")
    @GetMapping(path = "/getbytitle/{title}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Post getByTitle(
            @Parameter(description="Bejegyzés címe", required = true, example = "Új bejegyzés")
            @PathVariable(name = "title", required = true) String pId) throws Exception {
        return postService.findByTitle(pId);
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sikeres felvitel",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Post.class)) }),
            @ApiResponse(responseCode = "500", description = "Bejegyzés már létezik",
                    content = { @Content(mediaType = "application/json") })
    })
    @Operation(summary = "Új bejegyzés létrehozása")
    @PostMapping(path = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public Post add(
            Principal principal,
            @Parameter(description = "Az új bejegyzés",required = true) @RequestBody(required = true) Post pPost) throws Exception {
        KeycloakPrincipal kPrincipal = (KeycloakPrincipal) principal;
        AccessToken token = kPrincipal.getKeycloakSecurityContext().getToken();
        AccessToken.Access customClaims = token.getResourceAccess("account");
        System.out.println("ROLES:"+customClaims.getRoles());
        postService.save(pPost);
        return pPost;
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sikeres művelet",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", description = "Bejegyzés már létezik",
                    content = { @Content(mediaType = "application/json") })
    })
    @Operation(summary = "Bejegyzés törlése")
    @DeleteMapping(path = "/delete/{title}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(
            @Parameter(description = "Bejegyzés címe", required = true, example = "Új bejegyzés")
            @PathVariable(name = "title", required = true) Post pPost) {
        postService.delete(pPost);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sikeres módosítás",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Post.class)) }),
            @ApiResponse(responseCode = "500", description = "Bejegyzés már létezik",
                    content = { @Content(mediaType = "application/json") })
    })
    @Operation(summary = "Bejegyzés módosítása")
    @PutMapping(path = "/modify", produces = MediaType.APPLICATION_JSON_VALUE)
    public Post modify(
            @Parameter(description = "Bejegyzés", required = true)
            @RequestBody(required = true) Post pPost) throws IllegalAccessException, InvocationTargetException {
        postService.save(pPost);
        return pPost;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sikeres lekérdezés",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Post.class))) }),
    })
    @Operation(summary = "Bejegyzések lekérdezése Téma alapján")
    @GetMapping(path = "/postsbytopic/{topic_name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Post> postsByTopic(
            @Parameter(description = "Téma neve", required = true, example = "News")
            @PathVariable(name = "topic name", required = true) String pId) throws Exception {
        return forumService.getAllPostsByTopicName(pId);
    }


}
