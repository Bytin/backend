package tech.bytin.api.controller;

import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import core.boundary.SnippetIOBoundary;
import core.usecase.snippet.CreateSnippet;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("snippets")
public class SnippetController {
        
        private final SnippetIOBoundary snippetInteractor;

        @PostMapping("add")
        public ResponseEntity<?> addASnippet(@RequestBody CreateSnippet.RequestModel requestModel ){
                requestModel.getSnippet().setWhenCreated(LocalDateTime.now());
                requestModel.getSnippet().setWhenLastModified(LocalDateTime.now());
                return ResponseEntity.ok().body(snippetInteractor.createSnippet(requestModel));
        }

        @GetMapping("testauth")
        public String test(){
                return "You're good";
        }


}
