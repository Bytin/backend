package tech.bytin.api.repository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import core.entity.Snippet;
import core.gateway.SnippetGateway;
import tech.bytin.api.jpaEntity.SnippetJpaEntity;
import tech.bytin.api.util.EntityMapper;

public class SnippetRepository implements SnippetGateway {

        @Autowired
        private JpaSnippetRepository springRepo;

        @Override
        public void deleteById(Long id) {
                springRepo.deleteById(id);
        }

        @Override
        public boolean existsById(Long id) {
                return springRepo.existsById(id);
        }

        @Override
        public Collection<Snippet> findAll() {
                return springRepo.findAll().stream().map(EntityMapper::mapJpaSnippetToSnippetEntity)
                                .collect(Collectors.toList());
        }

        @Override
        public Optional<Snippet> findById(Long id) {
                return springRepo.findById(id).map(EntityMapper::mapJpaSnippetToSnippetEntity);
        }

        @Override
        public int getSize() {
                // TODO Auto-generated method stub
                return 0;
        }

        @Override
        public void save(Snippet snippet) {
                springRepo.save(EntityMapper.mapSnippetToJpaEntity(snippet));
        }

        @Override
        public Collection<Snippet> findAllByOwnerUsername(String username) {
                return springRepo.findAllByOwnerUsername(username);
        }

        @Override
        public Collection<Snippet> findMostRecent(int size) {
                return springRepo.findMostRecent(size);
        }

}


@Repository
interface JpaSnippetRepository extends JpaRepository<SnippetJpaEntity, Long> {
        Collection<Snippet> findAllByOwnerUsername(String username);

        @Query(value = "SELECT * FROM snippet WHERE is_private <> true ORDER BY date_updated DESC FETCH FIRST ?1 ROWS ONLY",
                        nativeQuery = true)
        Collection<Snippet> findMostRecent(int size);
}
