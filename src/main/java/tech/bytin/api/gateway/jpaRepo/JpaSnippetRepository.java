package tech.bytin.api.gateway.jpaRepo;

import java.util.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tech.bytin.api.jpaEntity.SnippetJpaEntity;

@Repository
public interface JpaSnippetRepository extends JpaRepository<SnippetJpaEntity, Long> {
        Collection<SnippetJpaEntity> findAllByOwnerUsername(String username);

        @Query(value = "SELECT * FROM snippet WHERE is_private <> true ORDER BY date_updated DESC FETCH FIRST ?1 ROWS ONLY",
                        nativeQuery = true)
        Collection<SnippetJpaEntity> findMostRecent(int size);

        Page<SnippetJpaEntity> findAllByOwnerUsername(String owner, Pageable pageable);

        Page<SnippetJpaEntity> findAllByHidden(boolean bool, Pageable pageable);
}

