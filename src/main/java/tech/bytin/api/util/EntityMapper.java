package tech.bytin.api.util;

import core.entity.ActivationToken;
import core.entity.Snippet;
import core.entity.User;
import tech.bytin.api.jpaEntity.ActivationTokenJpaEntity;
import tech.bytin.api.jpaEntity.SnippetJpaEntity;
import tech.bytin.api.jpaEntity.UserJpaEnity;

public class EntityMapper {

        private EntityMapper() {
        }

        public static UserJpaEnity mapUserToJpaEntity(User user) {
                return new UserJpaEnity(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(),
                                user.getRole());
        }

        public static User mapJpaUserToUserEntity(UserJpaEnity user) {
                return new User(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(),
                                user.getRole());
        }

        public static Snippet mapJpaSnippetToSnippetEntity(SnippetJpaEntity snippet) {
                return Snippet.builder().id(snippet.getId()).title(snippet.getTitle())
                                .language(snippet.getLanguage()).framework(snippet.getFramework())
                                .code(snippet.getCode()).description(snippet.getDescription())
                                .owner(mapJpaUserToUserEntity(snippet.getOwner()))
                                .resource(snippet.getResource()).hidden(snippet.isHidden()).build();
        }

        public static SnippetJpaEntity mapSnippetToJpaEntity(Snippet snippet) {
                return SnippetJpaEntity.builder().id(snippet.getId()).title(snippet.getTitle())
                                .language(snippet.getLanguage()).framework(snippet.getFramework())
                                .code(snippet.getCode()).description(snippet.getDescription())
                                .owner(mapUserToJpaEntity(snippet.getOwner()))
                                .resource(snippet.getResource()).hidden(snippet.isHidden()).build();
        }

        public static ActivationTokenJpaEntity mapActivationTokenToJpa(ActivationToken token) {
          return new ActivationTokenJpaEntity(token.getUsername(), token);
        }

        public static ActivationToken mapJpaToCoreActivationToken(ActivationTokenJpaEntity token) {
          return new ActivationToken(token.getUsername(), token);
        }
}
