package tech.bytin.api.jpaEntity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "activation_token")
@AllArgsConstructor
@Builder
public class ActivationTokenJpaEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String username;

    private UUID uuid;

    private Duration lifeSpan;

    @CreationTimestamp
    private LocalDateTime dateCreated;
    
}
