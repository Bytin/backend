package tech.bytin.api.gateway;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import core.entity.ActivationToken;
import core.gateway.ActivationTokenGateway;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import tech.bytin.api.gateway.jpaRepo.JpaActivationTokenRepo;
import tech.bytin.api.util.EntityMapper;

@RequiredArgsConstructor
public class ActivationTokenRepo implements ActivationTokenGateway {

    private final JpaActivationTokenRepo springRepo;

    @Override
    public void delete(ActivationToken arg0) {
        springRepo.delete(EntityMapper.mapActivationTokenToJpa(arg0));
    }

    @Override
    public void deleteById(Long arg0) {
        springRepo.deleteById(arg0);
    }

    @Override
    public boolean existsById(Long arg0) {
        return springRepo.existsById(arg0);
    }

    @Override
    public Collection<ActivationToken> findAll() {
        return springRepo.findAll().stream().map(EntityMapper::mapJpaToCoreActivationToken)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ActivationToken> findById(Long id) {
        return springRepo.findById(id).map(EntityMapper::mapJpaToCoreActivationToken);
    }

    @Override
    public void save(ActivationToken token) {
        springRepo.save(EntityMapper.mapActivationTokenToJpa(token));
    }

    @Override
    public Optional<ActivationToken> findByUsername(@NonNull String username) {
        return springRepo.findByUsername(username).map(EntityMapper::mapJpaToCoreActivationToken);
    }



}