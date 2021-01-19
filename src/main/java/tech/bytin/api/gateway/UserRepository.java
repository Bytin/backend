package tech.bytin.api.gateway;

import core.entity.User;
import core.gateway.UserGateway;
import lombok.RequiredArgsConstructor;
import tech.bytin.api.gateway.jpaRepo.JpaUserRepository;
import tech.bytin.api.util.EntityMapper;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserRepository implements UserGateway {

        private final JpaUserRepository jpaRepo;

        @Override
        public void deleteById(Long id) {
                jpaRepo.deleteById(id);
        }

        @Override
        public boolean existsById(Long id) {
                return jpaRepo.existsById(id);
        }

        @Override
        public Collection<User> findAll() {
                return jpaRepo.findAll().stream().map(EntityMapper::mapJpaUserToUserEntity).collect(Collectors.toList());
        }

        @Override
        public Optional<User> findById(Long id) {
                return jpaRepo.findById(id).map(EntityMapper::mapJpaUserToUserEntity);
        }

        @Override
        public void save(User userentity) {
                jpaRepo.save(EntityMapper.mapUserToJpaEntity(userentity));
        }

        @Override
        public boolean existsByUsername(String username) {
                return jpaRepo.existsByUsername(username);
        }

        @Override
        public Optional<User> findByUserName(String username) {
                return jpaRepo.findByUsername(username).map(EntityMapper::mapJpaUserToUserEntity);
        }

        @Override
        public void delete(User user) {
                jpaRepo.delete(EntityMapper.mapUserToJpaEntity(user));
        }

        @Override
        public long count() {
            return jpaRepo.count();
        }
}


