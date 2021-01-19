package tech.bytin.api.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import core.boundary.SnippetIOBoundary;
import core.usecase.snippet.RetrieveAllPublicSnippets;
import core.usecase.snippet.RetrievePublicSnippet;
import core.usecase.snippet.RetrieveRecentSnippets;
import core.usecase.snippet.SearchSnippets;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("snippets/public")
public class PublicSnippetController {

    private final SnippetIOBoundary snippetInteractor;

    @GetMapping("")
    ResponseEntity<?> getAll(Pageable pageable) {
        return ResponseEntity.ok()
                .body(snippetInteractor.retrieveAllPublicSnippets(
                        new RetrieveAllPublicSnippets.RequestModel(pageable.getPageNumber(),
                                pageable.getPageSize())));
    }

    @GetMapping("recent")
    ResponseEntity<?> getRecentOnes(@RequestParam int size) {
        return ResponseEntity.ok().body(snippetInteractor
                .retrieveRecentSnippets(new RetrieveRecentSnippets.RequestModel(size)));
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getOne(@PathVariable long id) {
        return ResponseEntity.ok().body(snippetInteractor
                .retrievePublicSnippet(new RetrievePublicSnippet.RequestModel(id)));
    }

    @PostMapping("/search")
    ResponseEntity<?> search(@RequestBody SearchSnippets.RequestModel requestModel) {
        requestModel.predicate = snippet -> !snippet.isHidden();
        return ResponseEntity.ok().body(snippetInteractor.searchPublicSnippets(requestModel));
    }


}
