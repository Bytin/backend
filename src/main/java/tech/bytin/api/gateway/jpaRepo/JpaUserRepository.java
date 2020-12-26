package tech.bytin.api.gateway.jpaRepo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.bytin.api.jpaEntity.UserJpaEnity;

@Repository
public interface JpaUserRepository extends JpaRepository<UserJpaEnity, Long> {
        Optional<UserJpaEnity> findByUsername(String username);
        boolean existsByUsername(String username);
}
