package tech.bytin.api.util;

import core.entity.User;
import core.entity.User.UserRole;
import tech.bytin.api.jpaEntity.UserJpaEnity;

public class Utils {

        private Utils() {
        }

        public static UserJpaEnity mapUserToJpaEntity(User user) {
                return new UserJpaEnity(user.getId(), user.getUsername(), user.getPassword(),
                                user.getRole().toString());
        }

        public static User mapJpaUserToUserEntity(UserJpaEnity user) {
                return new User(user.getUsername(), user.getPassword(),
                                UserRole.valueOf(user.getRole()));
        }
}
