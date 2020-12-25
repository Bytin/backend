package tech.bytin.api.jpaEntity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "snippet")
@Builder
@AllArgsConstructor
public class SnippetJpaEntity{
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;
        private String title, language, framework, description, resource;
        @Column(columnDefinition = "TEXT")
        private String code;
        private boolean hidden;

        @ManyToOne(fetch = FetchType.LAZY)
        private UserJpaEnity owner;
        private LocalDateTime whenCreated, whenLastModified;

}
