package tech.bytin.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
import core.boundary.UserIOBoundary;
import core.usecase.user.CreateUser;
import core.usecase.user.RetrieveProfile;
import core.usecase.user.UpdateUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

        private final UserIOBoundary userInteractor;
        private final PasswordEncoder passwordEncoder;

        @PostMapping("register")
        public ResponseEntity<?> createUser(@RequestBody CreateUser.RequestModel requestModel)
                        throws JsonProcessingException {
                var resModel = userInteractor.createUser(requestModel,
                                s -> passwordEncoder.encode(s));
                return ResponseEntity.ok().body(resModel);
        }

        @PostMapping("profile")
        public ResponseEntity<?> getProfile(@RequestBody RetrieveProfile.RequestModel requestModel){
                return ResponseEntity.ok().body(userInteractor.retrieveUserProfile(requestModel));
        }

        @PostMapping("update")
        public ResponseEntity<?> updateUser(@RequestBody UpdateUserInfo.RequestModel requestModel){
                return ResponseEntity.ok().body(userInteractor.updateUserInfo(requestModel));
        }

}
