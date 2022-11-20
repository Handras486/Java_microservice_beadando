package hu.yokudlela.comment.rest;


import hu.yokudlela.comment.datamodel.Comment;
import hu.yokudlela.comment.store.CommentRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.AccessToken.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping(path = "/admin")

public class AdminController {

    @Autowired
    CommentRepository commentService;


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sikeres felvitel",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Comment.class)) }),
            @ApiResponse(responseCode = "403", description = "Nincs megfelelő jogosultságod",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "401", description = "Lejárt token",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "302", description = "Nincs bejelentkezve, átirányítás a login oldalra",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", description = "Komment már létezik",
                    content = { @Content(mediaType = "application/json") })
    })
    @Operation(
            security = {
                    @SecurityRequirement(name = "apikey",scopes = {"comment"}),
                    @SecurityRequirement(name = "openid",scopes = {"comment"}),
                    @SecurityRequirement(name = "oauth2",scopes = {"comment"}),
            },
            summary = "Új téma létrehozása")
    @PutMapping(path = "/enable/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void enable(
            @Parameter(description = "Komment ", required = true)
            @PathVariable(name = "name", required = true) Long pId) throws Exception {
        Comment tmp = commentService.findById(pId).get();
        if(tmp!=null){
            tmp.setPinned(true);
        }
        else{
            throw new Exception();
        }
    }



    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sikeres felvitel",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Comment.class)) }),
            @ApiResponse(responseCode = "403", description = "Nincs megfelelő jogosultságod",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "401", description = "Lejárt token",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "302", description = "Nincs bejelentkezve, átirányítás a login oldalra",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", description = "Komment már létezik",
                    content = { @Content(mediaType = "application/json") })
    })
    @Operation(
            security = {
                    @SecurityRequirement(name = "apikey",scopes = {"comment"}),
                    @SecurityRequirement(name = "openid",scopes = {"comment"}),
                    @SecurityRequirement(name = "oauth2",scopes = {"comment"}),
            },
            summary = "Új téma létrehozása")
    @PutMapping(path = "/disable/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void disable(
            @Parameter(description = "Komment", required = true)
            @PathVariable(name = "name", required = true) Long pId) throws Exception {
        Comment tmp = commentService.findById(pId).get();
        if(tmp!=null){
            tmp.setPinned(false);

        }
        else{
            throw new Exception();
        }
    }



}
