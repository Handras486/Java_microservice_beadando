package hu.yokudlela.forum.datamodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@EqualsAndHashCode()
@Entity
@javax.persistence.Table(name = "topic")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Schema(description = "Téma")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Schema(description = "Téma neve")
    private String name;

    @Schema(description = "Téma leírása")
    private String description;

    @Schema(description = "Téma keletkezésének dátuma", example = "2022-10-10T10:00:10")
    @JsonSerialize(using = DateSerializer.class)
    private LocalDateTime date;

    @Builder
    public Topic(String name, String description) {
        this.name = name;
        this.description = description;
    }


}