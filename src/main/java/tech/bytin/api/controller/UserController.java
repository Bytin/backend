package tech.bytin.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
import core.boundary.UserIOBoundary;
import core.usecase.user.ActivateUser;
import core.usecase.user.CreateUser;
import core.usecase.user.RetrieveProfile;
import core.usecase.user.UpdateUserInfo;
import lombok.RequiredArgsConstructor;
import tech.bytin.api.service.mail.ActivationLinkMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

        private final UserIOBoundary userInteractor;
        private final PasswordEncoder passwordEncoder;
        private final ActivationLinkMailSender activationMail;

        @PostMapping("register")
        public ResponseEntity<?> createUser(@RequestBody CreateUser.RequestModel requestModel)
                        throws JsonProcessingException {
                var req = new CreateUser.RequestModel(requestModel.getUsername(), requestModel.getEmail(), requestModel.getPassword(), token -> {
                    System.out.println("Activation Token: " + token + " -- for " + token.getUsername());
                    activationMail.sendActivationLink(requestModel.getEmail(), token);
                });
                var resModel = userInteractor.createUser(req,
                                password -> passwordEncoder.encode(password));
                var revisedResponse = new CreateUser.ResponseModel(resModel.getMessage() + " Check your email for instructions on how to activate the account.");
                return ResponseEntity.ok().body(revisedResponse);
        }

        @PostMapping("activate")
        public ResponseEntity<?> activateUser(@RequestBody ActivateUser.RequestModel requestModel){
                return ResponseEntity.ok().body(userInteractor.activateUser(requestModel));
        }

        @PostMapping("profile")
        public ResponseEntity<?> getProfile(@RequestBody RetrieveProfile.RequestModel requestModel){
                return ResponseEntity.ok().body(userInteractor.retrieveProfile(requestModel));
        }

        @PutMapping("update")
        public ResponseEntity<?> updateUser(@RequestBody UpdateUserInfo.RequestModel requestModel){
                return ResponseEntity.ok().body(userInteractor.updateUserInfo(requestModel));
        }

}
