package tech.bytin.api;

import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.mock.web.MockHttpServletResponse;
import core.dto.SnippetDTO;
import core.entity.Snippet;
import core.entity.User.UserRole;
import tech.bytin.api.jpaEntity.SnippetJpaEntity;
import tech.bytin.api.jpaEntity.UserJpaEnity;
import tech.bytin.api.util.EntityMapper;

public final class TestUtils {

    private TestUtils() {
    }

    public static SnippetJpaEntity sampleSnippet(long id) {
        return SnippetJpaEntity.builder().id(id).title("good code").language("java")
                .code("System.out.println(\"Yo!\")")
                .owner(new UserJpaEnity(1, "test", "test@gmail.com", "sldkafjlkj", UserRole.USER))
                .description("robust").whenCreated(LocalDateTime.now())
                .whenLastModified(LocalDateTime.now()).build();
    }

    public static SnippetDTO[] asDtoArray(List<SnippetJpaEntity> snippets) {
        return snippets.stream().map(EntityMapper::mapJpaSnippetToSnippetEntity)
                .map(Snippet::toSnippetDto).toArray(SnippetDTO[]::new);
    }

    public static SnippetDTO[] readSnippetsFrom(MockHttpServletResponse response,
            ObjectMapper mapper) throws Exception {
        var snippets_json =
                mapper.readTree(response.getContentAsString()).get("snippets").toString();
        System.out.println(snippets_json);
        var snippets = mapper.readValue(snippets_json, SnippetDTO[].class);
        return snippets;
    }

    public static SnippetDTO readSnippetFrom(MockHttpServletResponse response, ObjectMapper mapper)
            throws Exception {
        var snippets_json =
                mapper.readTree(response.getContentAsString()).get("snippet").toString();
        System.out.println(snippets_json);
        var snippets = mapper.readValue(snippets_json, SnippetDTO.class);
        return snippets;
    }

    public static SnippetDTO asDto(SnippetJpaEntity snippet) {
        return EntityMapper.mapJpaSnippetToSnippetEntity(snippet).toSnippetDto();
    }
}
