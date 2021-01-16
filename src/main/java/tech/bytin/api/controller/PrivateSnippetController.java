package tech.bytin.api.controller;

import java.security.Principal;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import core.boundary.SnippetIOBoundary;
import core.usecase.snippet.RetrieveAllSnippetsOfUser;
import core.usecase.snippet.RetrieveSnippetOfUser;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("snippets/private")
public class PrivateSnippetController {

    private final SnippetIOBoundary snippetInteractor;

    @GetMapping("")
    ResponseEntity<?> getAll(Pageable pageable, Principal principal) {
        return ResponseEntity.ok()
                .body(snippetInteractor.retrieveSnippetsOfUser(
                        new RetrieveAllSnippetsOfUser.RequestModel(principal.getName(),
                                pageable.getPageNumber(), pageable.getPageSize())));
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getOne(@PathVariable long id, Principal principal) {
        return ResponseEntity.ok().body(snippetInteractor.retrieveSnippetOfUser(
                new RetrieveSnippetOfUser.RequestModel(id, principal.getName())));
    }

}
