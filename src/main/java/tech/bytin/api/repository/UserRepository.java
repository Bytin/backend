package tech.bytin.api.repository;

import org.springframework.stereotype.Repository;
import core.entity.User;
import core.gateway.UserGateway;
import tech.bytin.api.jpaEntity.UserJpaEnity;
import tech.bytin.api.util.Utils;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

public class UserRepository implements UserGateway {

        @Autowired
        private JpaUserRepository jpaRepo;

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
                return jpaRepo.findAll().stream().map(Utils::mapJpaUserToUserEntity).collect(Collectors.toList());
        }

        @Override
        public Optional<User> findById(Long id) {
                return jpaRepo.findById(id).map(Utils::mapJpaUserToUserEntity);
        }

        @Override
        public int getSize() {
                return 0;//TODO what is this for again?
        }

        @Override
        public void save(User userentity) {
                jpaRepo.save(Utils.mapUserToJpaEntity(userentity));
        }

        @Override
        public boolean existsByUsername(String username) {
                return jpaRepo.existsByUsername(username);
        }

        @Override
        public Optional<User> findByUserName(String username) {
                return jpaRepo.findByUsername(username).map(Utils::mapJpaUserToUserEntity);
        }
}


@Repository
interface JpaUserRepository extends JpaRepository<UserJpaEnity, Long> {
        Optional<UserJpaEnity> findByUsername(String username);
        boolean existsByUsername(String username);
}
