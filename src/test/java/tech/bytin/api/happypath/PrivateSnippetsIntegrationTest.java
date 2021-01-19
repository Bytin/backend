package tech.bytin.api.happypath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import core.dto.SnippetDTO;
import core.utils.Page;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import tech.bytin.api.TestCase;
import tech.bytin.api.jpaEntity.SnippetJpaEntity;
import static tech.bytin.api.TestUtils.*;

public class PrivateSnippetsIntegrationTest extends TestCase {

    @Autowired
    ObjectMapper mapper;

    List<SnippetJpaEntity> sampleSnippets;

    SnippetJpaEntity oneSnippet;

    final String username = "noah";

    @BeforeEach
    void init() {
        oneSnippet = sampleSnippet(1, username);
        sampleSnippets = List.of(sampleSnippet(1, username), sampleSnippet(2, username),
                sampleSnippet(3, username));
        Mockito.when(snippets.streamAll()).thenReturn(sampleSnippets.stream());
        Mockito.when(snippets.findAllByOwnerUsername(username, PageRequest.of(0, 3)))
                .thenReturn(new PageImpl<>(sampleSnippets, PageRequest.of(0, 3), 1));
        Mockito.when(snippets.findById(1l)).thenReturn(Optional.of(oneSnippet));
    }

    @Test
    @WithMockUser(username = username, roles = {"USER"})
    void allSnippetsTest() throws Exception {
        var request = get("/snippets/private?page=0&size=3");
        var response = mvc.perform(request).andExpect(status().isOk()).andReturn().getResponse();
        var page_json = mapper.readTree(response.getContentAsString()).get("page").toString();
        var page = mapper.readValue(page_json, new TypeReference<Page<SnippetDTO>>() {
        });
        assertEquals(0, page.getNumber());
        assertEquals(3, page.getSize());
        assertEquals(1, page.getTotal());
        var snippetDtos = asDtoArray(sampleSnippets);
        assertArrayEquals(snippetDtos, page.getContent().toArray());
    }

    @Test
    @WithMockUser(username = username, roles = {"USER"})
    void oneSnippetTest() throws Exception {
        var request = get("/snippets/private/1");
        var response = mvc.perform(request).andExpect(status().isOk()).andReturn().getResponse();
        var snippet = readSnippetFrom(response, mapper);
        assertEquals(asDto(oneSnippet), snippet);
    }

    @ParameterizedTest
    @CsvSource(value = {"java, SIMPLE, 0, 2", "jav., REGEX, 0, 2", "jav.*, REGEX, 1, 1"})
    @WithMockUser(username = username, roles = {"USER"})
    void searchAllSnippetsTest(String phrase, String mode, int pageNum, int pageSize)
            throws Exception {
        var request = post("/snippets/private/search").contentType("application/json")
                .content(String.format("""
                                {
                                    "phrase": "%s",
                                    "mode": "%s",
                                    "page": %d,
                                    "size": %d
                                }
                        """, phrase, mode, pageNum, pageSize));

        var response = mvc.perform(request).andExpect(status().isOk()).andReturn().getResponse();
        var page_json = mapper.readTree(response.getContentAsString()).get("page").toString();
        var page = mapper.readValue(page_json, new TypeReference<Page<SnippetDTO>>() {
        });

        assertEquals(pageNum, page.getNumber());
        assertEquals(pageSize, page.getSize());
        assertEquals(snippets.count() / pageSize, page.getTotal());
        var snippetDtos = asDtoArray(sampleSnippets);
        var expected = Stream.of(snippetDtos).skip(pageNum * pageSize).limit(pageSize).toArray();
        assertArrayEquals(expected, page.getContent().toArray());
    }

}
