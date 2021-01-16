package tech.bytin.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import core.boundary.SnippetIOBoundary;
import core.usecase.snippet.RetrievePublicSnippet;
import core.usecase.snippet.RetrieveRecentSnippets;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("snippets/public")
public class PublicSnippetController {

    private final SnippetIOBoundary snippetInteractor;

    @GetMapping("recent")
    ResponseEntity<?> getRecentOnes(@RequestParam int size){
        return ResponseEntity.ok().body(snippetInteractor.RetrieveRecentSnippets(new RetrieveRecentSnippets.RequestModel(size)));
    } 

    @GetMapping("/{id}")
    ResponseEntity<?> getOne(@PathVariable long id){
        return ResponseEntity.ok().body(snippetInteractor.retrievePublicSnippet(new RetrievePublicSnippet.RequestModel(id)));
    }

}
