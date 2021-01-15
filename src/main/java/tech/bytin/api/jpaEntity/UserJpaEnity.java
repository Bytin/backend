package tech.bytin.api.jpaEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import core.entity.User.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_user")
public class UserJpaEnity {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;
        @Column(unique = true)
        private String username, email;
        private String password;
        @Enumerated(EnumType.STRING)
        private UserRole role;

}
