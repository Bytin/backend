package tech.bytin.api.happypath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import core.dto.SnippetDTO;
import core.utils.Page;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
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
        Mockito.when(snippets.streamAll()).thenReturn(Stream.of(sampleSnippet(1), sampleSnippet(8), sampleSnippet(90)));
        Mockito.when(snippets.findMostRecent(anyInt())).thenReturn(sampleSnippets);
        Mockito.when(snippets.count()).thenReturn(sampleSnippets.size() + 0l);
        Mockito.when(snippets.findAllByHidden(false, PageRequest.of(0, 3)))
                .thenReturn(new PageImpl<>(sampleSnippets, PageRequest.of(0, 3), 1));
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
    void allSnippetsTest() throws Exception {
        var request = get("/snippets/public?page=0&size=3");
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

    @ParameterizedTest
    @CsvSource(value = {"java, SIMPLE, 0, 2", "jav., REGEX, 0, 2", "jav.*, REGEX, 1, 1"})
    void searchAllSnippetsTest(String phrase, String mode, int pageNum, int pageSize)
            throws Exception {
        var request = post("/snippets/public/search").contentType("application/json")
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

    @Test
    void oneSnippetTest() throws Exception {
        var request = get("/snippets/public/1");
        var response = mvc.perform(request).andExpect(status().isOk()).andReturn().getResponse();
        var snippet = readSnippetFrom(response, mapper);
        assertEquals(asDto(oneSnippet), snippet);
    }

}
