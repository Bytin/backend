package tech.bytin.api.util;

import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import core.entity.ActivationToken;
import core.entity.Snippet;
import core.entity.User;
import core.utils.ExpiringToken;
import tech.bytin.api.jpaEntity.ActivationTokenJpaEntity;
import tech.bytin.api.jpaEntity.SnippetJpaEntity;
import tech.bytin.api.jpaEntity.UserJpaEnity;

public class EntityMapper {

    private EntityMapper() {
    }

    public static UserJpaEnity mapUserToJpaEntity(User user) {
        return new UserJpaEnity(user.getId(), user.getUsername(), user.getEmail(),
                user.getPassword(), user.getRole());
    }

    public static User mapJpaUserToUserEntity(UserJpaEnity user) {
        return new User(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(),
                user.getRole());
    }

    public static Snippet mapJpaSnippetToSnippetEntity(SnippetJpaEntity snippet) {
        return Snippet.builder().id(snippet.getId()).title(snippet.getTitle())
                .language(snippet.getLanguage()).framework(snippet.getFramework())
                .code(snippet.getCode()).description(snippet.getDescription())
                .owner(mapJpaUserToUserEntity(snippet.getOwner())).resource(snippet.getResource())
                .whenCreated(snippet.getWhenCreated())
                .whenLastModified(snippet.getWhenLastModified()).hidden(snippet.isHidden()).build();
    }

    public static SnippetJpaEntity mapSnippetToJpaEntity(Snippet snippet) {
        return SnippetJpaEntity.builder().id(snippet.getId()).title(snippet.getTitle())
                .language(snippet.getLanguage()).framework(snippet.getFramework())
                .code(snippet.getCode()).description(snippet.getDescription())
                .owner(mapUserToJpaEntity(snippet.getOwner())).resource(snippet.getResource())
                .whenCreated(snippet.getWhenCreated())
                .whenLastModified(snippet.getWhenLastModified()).hidden(snippet.isHidden()).build();
    }

    public static ActivationTokenJpaEntity mapActivationTokenToJpa(ActivationToken token) {
        return ActivationTokenJpaEntity.builder().id(0).username(token.getUsername())
                .uuid(token.getUuid()).lifeSpan(token.getLifeSpan())
                .dateCreated(token.getDateCreated()).build();
    }

    public static ActivationToken mapJpaToCoreActivationToken(ActivationTokenJpaEntity token) {
        var expiringToken =
                new ExpiringToken(token.getUuid(), token.getLifeSpan(), token.getDateCreated());
        return new ActivationToken(token.getId(), token.getUsername(), expiringToken);
    }

    public static core.utils.Page<Snippet> mapJpaSnippetPageToCoreSnippetPage(
            Page<SnippetJpaEntity> jpaPage) {
        return new core.utils.Page<Snippet>(jpaPage.getNumber(), jpaPage.getTotalPages(),
                jpaPage.getSize(),
                jpaPage.getContent().stream().map(EntityMapper::mapJpaSnippetToSnippetEntity)
                        .collect(Collectors.toList()));
    }

}
