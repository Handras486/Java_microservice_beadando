package hu.yokudlela.forum.datamodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import hu.yokudlela.forum.store.TopicRepository;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode()
@NoArgsConstructor
@Entity
@javax.persistence.Table(name = "post")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Schema(description = "Bejegyzés")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Schema(description = "Bejegyzés címe")
    private String title;

    @Schema(description = "Bejegyzés leírása")
    private String description;

    @Schema(description = "Bejegyzés keletkezésének dátuma", example = "2022-10-10T10:00:10")
    @JsonSerialize(using = DateSerializer.class)
    private LocalDateTime date;

    @OneToOne(fetch = FetchType.LAZY )
    @JoinColumn(name="topic_id", nullable=false)
    @Schema(description = "Bejegyzés témája")
    private Topic topic;

    @Builder
    public Post(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
