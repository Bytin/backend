package tech.bytin.api.gateway.jpaRepo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import lombok.NonNull;
import tech.bytin.api.jpaEntity.ActivationTokenJpaEntity;

public interface JpaActivationTokenRepo extends JpaRepository<ActivationTokenJpaEntity, Long> {

  Optional<ActivationTokenJpaEntity> findByUsername(@NonNull String username);
    
}
