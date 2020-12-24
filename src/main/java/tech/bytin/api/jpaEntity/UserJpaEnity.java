package tech.bytin.api.jpaEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
        private String username, password, role;

}
