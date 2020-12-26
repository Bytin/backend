package tech.bytin.api.gateway;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import core.entity.Snippet;
import core.gateway.SnippetGateway;
import lombok.RequiredArgsConstructor;
import tech.bytin.api.gateway.jpaRepo.JpaSnippetRepository;
import tech.bytin.api.util.EntityMapper;

@RequiredArgsConstructor
public class SnippetRepository implements SnippetGateway {

        private final JpaSnippetRepository springRepo;

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