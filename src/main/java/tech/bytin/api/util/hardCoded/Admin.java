package tech.bytin.api.util.hardCoded;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import core.entity.User.UserRole;
import tech.bytin.api.gateway.jpaRepo.JpaUserRepository;
import tech.bytin.api.jpaEntity.UserJpaEnity;

@Service
public class Admin {

    @Autowired
    HardCodedProperties props;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JpaUserRepository userRepo;
    
    @PostConstruct
    void put(){
        var adminData = props.getAdmin();
        UserJpaEnity admin = new UserJpaEnity();
        admin.setUsername(adminData.get("username"));
        admin.setEmail(adminData.get("email"));
        admin.setPassword(encoder.encode(adminData.get("password")));
        admin.setRole(UserRole.ADMIN);
        userRepo.save(admin);
    }
}
