package tech.bytin.api.jpaEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import core.entity.ActivationToken;
import core.utils.ExpiringToken;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "activation_token")
public class ActivationTokenJpaEntity extends ActivationToken{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String username;

    public ActivationTokenJpaEntity(String username, ExpiringToken token){
        super(username, token);
        this.username = username;
    }
    
}
