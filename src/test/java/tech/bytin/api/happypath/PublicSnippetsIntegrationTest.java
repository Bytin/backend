package tech.bytin.api.happypath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.List;
import java.util.Optional;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import tech.bytin.api.TestCase;
import tech.bytin.api.jpaEntity.SnippetJpaEntity;
import static tech.bytin.api.TestUtils.*;

public class PublicSnippetsIntegrationTest extends TestCase {

    @Autowired
    ObjectMapper mapper;

    List<SnippetJpaEntity> sampleSnippets;

    SnippetJpaEntity oneSnippet;

    @BeforeEach
    void init() {
        oneSnippet = sampleSnippet(1);
        sampleSnippets = List.of(sampleSnippet(1), sampleSnippet(2), sampleSnippet(3));
        Mockito.when(snippets.findMostRecent(anyInt())).thenReturn(sampleSnippets);
        Mockito.when(snippets.findById(1l)).thenReturn(Optional.of(oneSnippet));
    }

    @Test
    void recentSnippetsTest() throws Exception {
        var request = get("/snippets/public/recent?size=10");
        var response = mvc.perform(request).andExpect(status().isOk()).andReturn().getResponse();
        var snippets = readSnippetsFrom(response, mapper);
        var snippetDtos = asDtoArray(sampleSnippets);
        assertArrayEquals(snippetDtos, snippets);
    }

    @Test
    void oneSnippetTest() throws Exception {
        var request = get("/snippets/public/1");
        var response = mvc.perform(request).andExpect(status().isOk()).andReturn().getResponse();
        var snippet = readSnippetFrom(response, mapper);
        assertEquals(asDto(oneSnippet), snippet);
    }

}
