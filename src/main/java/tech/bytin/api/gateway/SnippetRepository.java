package tech.bytin.api.gateway;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import core.entity.Snippet;
import core.gateway.SnippetGateway;
import lombok.RequiredArgsConstructor;
import tech.bytin.api.gateway.jpaRepo.JpaSnippetRepository;
import tech.bytin.api.jpaEntity.SnippetJpaEntity;
import tech.bytin.api.util.EntityMapper;
import static tech.bytin.api.util.EntityMapper.*;

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
    public void save(Snippet snippet) {
        springRepo.save(mapSnippetToJpaEntity(snippet));
    }

    @Override
    public Collection<Snippet> findAllByOwnerUsername(String username) {
        return springRepo.findAllByOwnerUsername(username).stream()
                .map(EntityMapper::mapJpaSnippetToSnippetEntity).collect(Collectors.toList());
    }

    @Override
    public Collection<Snippet> findMostRecent(int size) {
        return springRepo.findMostRecent(size).stream()
                .map(EntityMapper::mapJpaSnippetToSnippetEntity).collect(Collectors.toList());
    }

    @Override
    public void delete(Snippet snippet) {
        springRepo.delete(mapSnippetToJpaEntity(snippet));
    }

    @Override
    public core.utils.Page<Snippet> findAllPublic(int page, int pageSize) {
        Page<SnippetJpaEntity> jpaPage =
                springRepo.findAllByIsHidden(false, PageRequest.of(page, pageSize));
        return mapJpaSnippetPageToCoreSnippetPage(jpaPage);
    }

    @Override
    public core.utils.Page<Snippet> findAllByOwnerUsername(String owner, int page, int pageSize) {
        Page<SnippetJpaEntity> jpaPage =
                springRepo.findAllByOwnerUsername(owner, PageRequest.of(page, pageSize));
        return mapJpaSnippetPageToCoreSnippetPage(jpaPage);
    }

}
