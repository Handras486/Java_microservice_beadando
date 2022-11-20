package hu.yokudlela.forum.rest;

import hu.yokudlela.media.java.clients.api.ImageControllerApi;
import hu.yokudlela.media.java.clients.model.ImageProcessModel;
import hu.yokudlela.media.java.clients.invoker.ApiException;
import hu.yokudlela.forum.datamodel.Topic;
import hu.yokudlela.forum.store.PostRepository;
import hu.yokudlela.forum.store.TopicRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.AccessToken.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController()
@RequestMapping(path = "/admin")

public class AdminController {

    @Autowired
    TopicRepository topicService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A keresett téma",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Topic.class)) }),
            @ApiResponse(responseCode = "500", description = "Sikertelen lekérdezés",
                    content = { @Content(mediaType = "application/json") })
    })
    @Operation(summary = "Téma lekérdezés név alapján")
    @GetMapping(path = "/getbyname/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Topic getByName(
            @Parameter(description="Téma neve", required = true, example = "Hírek")
            @PathVariable(name = "name", required = true) String pId) throws Exception {
        return topicService.findByName(pId);
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sikeres felvitel",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Topic.class)) }),
            @ApiResponse(responseCode = "403", description = "Nincs megfelelő jogosultságod",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "401", description = "Lejárt token",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "302", description = "Nincs bejelentkezve, átirányítás a login oldalra",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", description = "Téma már létezik",
                    content = { @Content(mediaType = "application/json") })
    })
    @Operation(
            security = {
                    @SecurityRequirement(name = "apikey",scopes = {"forum"}),
                    @SecurityRequirement(name = "openid",scopes = {"forum"}),
                    @SecurityRequirement(name = "oauth2",scopes = {"forum"}),
            },
            summary = "Új téma létrehozása")
    @PostMapping(path = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed("user")
    public Topic add(
            Principal principal,
            @Parameter(description = "Az új téma",required = true) @RequestBody(required = true) Topic pTopic) throws Exception {
        KeycloakPrincipal kPrincipal = (KeycloakPrincipal) principal;
        AccessToken token = kPrincipal.getKeycloakSecurityContext().getToken();
        Access customClaims = token.getResourceAccess("account");
        System.out.println("ROLES:"+customClaims.getRoles());
        topicService.save(pTopic);
        return pTopic;
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sikeres művelet",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "403", description = "Nincs megfelelő jogosultságod",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "401", description = "Lejárt token",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "302", description = "Nincs bejelentkezve, átirányítás a login oldalra",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", description = "Téma már létezik",
                    content = { @Content(mediaType = "application/json") })
    })
    @Operation(summary = "Téma törlése")
    @DeleteMapping(path = "/delete/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(
            @Parameter(description = "Téma neve", required = true, example = "Hírek")
            @PathVariable(name = "name", required = true) Topic pTopic) {
        topicService.delete(pTopic);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sikeres módosítás",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Topic.class)) }),
            @ApiResponse(responseCode = "403", description = "Nincs megfelelő jogosultságod",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "401", description = "Lejárt token",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "302", description = "Nincs bejelentkezve, átirányítás a login oldalra",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", description = "Téma már létezik",
                    content = { @Content(mediaType = "application/json") })
    })
    @Operation(
            security = {
                    @SecurityRequirement(name = "apikey",scopes = {"forum"}),
                    @SecurityRequirement(name = "openid",scopes = {"forum"}),
                    @SecurityRequirement(name = "oauth2",scopes = {"forum"}),
            },

            summary = "Téma módosítása")
    @PutMapping(path = "/modify", produces = MediaType.APPLICATION_JSON_VALUE)
    public Topic modify(
            @Parameter(description = "Téma", required = true)
            @RequestBody(required = true) Topic pTopic) throws IllegalAccessException, InvocationTargetException {
        topicService.save(pTopic);
        return pTopic;
    }


    @Operation(summary = "Képtörlése",
            security = {
                    @SecurityRequirement(name = "apikey",scopes = {"file"}),
                    @SecurityRequirement(name = "openid",scopes = {"file"}),
                    @SecurityRequirement(name = "oauth2",scopes = {"file"}),
            })
    @DeleteMapping(path = "/deleteimage/{fileid}", produces = MediaType.TEXT_PLAIN_VALUE)
    @AspectLogger
    public String deleteImageUrl(
            HttpServletRequest request,
            @PathVariable(name = "fileid") String pId) throws ApiException {
        ImageControllerApi api = this.mediaClient.getClientInstanceWithToken(request.getHeader("Authorization"));
        return api.deleteFiles(pId, null).getId();
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sikeres képfeltöltés"),
            @ApiResponse(responseCode = "500", description = "Képfeltöltés nem lehetséges")
    })
    @Operation(summary = "Képfeltöltés",
            security = {
                    @SecurityRequirement(name = "apikey",scopes = {"file"}),
                    @SecurityRequirement(name = "openid",scopes = {"file"}),
                    @SecurityRequirement(name = "oauth2",scopes = {"file"}),
            })
    @PostMapping(value = "/addFiles", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })

    public List<ImageProcessModel> addImageUrl(
            HttpServletRequest request,
            @RequestPart("files") MultipartFile[] files) throws IOException, ApiException {
        ImageControllerApi api = this.mediaClient.getClientInstanceWithToken(request.getHeader("Authorization"));
        File f;
        List<ImageProcessModel> result = new ArrayList();


        for(MultipartFile f2: files){
            f = Files.createTempFile(UUID.randomUUID().toString(), ".tmp").toFile();
            f.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(f);
            outputStream.write(f2.getBytes());
            outputStream.close();
            result.addAll(api.addFiles(f, null));
        }
        return result;
    }



}
