package hu.yokudlela.comment.datamodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode()
@NoArgsConstructor
@Entity
@javax.persistence.Table(name = "comment")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Schema(description = "Komment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Schema(description = "Komment szerzője")
    private String author;

    @Schema(description = "Komment szövege")
    private String content;

    @Schema(description = "Kitűzött komment?")
    @JsonIgnore
    private boolean pinned = false;

    @Builder
    public Comment(String author, String content) {
        this.author = author;
        this.content = content;
    }
}
