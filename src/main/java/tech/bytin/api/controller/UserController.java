package tech.bytin.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
import core.boundary.UserIOBoundary;
import core.usecase.user.CreateUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/user")
public class UserController {

        private final UserIOBoundary userInteractor;

        @Autowired
        PasswordEncoder passwordEncoder;

        UserController(@Autowired UserIOBoundary userIOBoundary) {
                this.userInteractor = userIOBoundary;
        }

        @PostMapping(value = "/register")
        public ResponseEntity<?> createUser(@RequestBody CreateUser.RequestModel requestModel)
                        throws JsonProcessingException {
                var resModel = userInteractor.createUser(requestModel,
                                s -> passwordEncoder.encode(s));
                return ResponseEntity.ok().body(resModel);
        }

}
