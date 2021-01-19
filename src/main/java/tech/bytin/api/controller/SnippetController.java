package tech.bytin.api.controller;

import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import core.boundary.SnippetIOBoundary;
import core.usecase.snippet.CreateSnippet;
import core.usecase.snippet.DeleteSnippetOfUser;
import core.usecase.snippet.UpdateSnippet;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("snippets")
public class SnippetController {

    private final SnippetIOBoundary snippetInteractor;

    @PostMapping("add")
    public ResponseEntity<?> addASnippet(@RequestBody CreateSnippet.RequestModel requestModel) {
        requestModel.getSnippet().setWhenCreated(LocalDateTime.now());
        requestModel.getSnippet().setWhenLastModified(LocalDateTime.now());
        return ResponseEntity.ok().body(snippetInteractor.createSnippet(requestModel));
    }

    @PutMapping("update")
    public ResponseEntity<?> updateOne(@RequestBody UpdateSnippet.RequestModel requestModel) {
        requestModel.getSnippet().setWhenCreated(LocalDateTime.now());
        requestModel.getSnippet().setWhenLastModified(LocalDateTime.now());
        return ResponseEntity.ok().body(snippetInteractor.updateSnippet(requestModel));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteSnippet(@PathVariable long id) {
        var req = new DeleteSnippetOfUser.RequestModel(id);
        return ResponseEntity.ok().body(snippetInteractor.deleteOne(req));
    }

}
