package tech.bytin.api.config.security;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.RequiredArgsConstructor;
import tech.bytin.api.jpaEntity.UserJpaEnity;


@RequiredArgsConstructor
public class SecurityUser implements UserDetails {

        private static final long serialVersionUID = -8516136395348612646L;
        private final UserJpaEnity userJpaEnity;

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of(new SimpleGrantedAuthority(userJpaEnity.getRole()));
        }

        @Override
        public String getPassword() {
                return userJpaEnity.getPassword();
        }

        @Override
        public String getUsername() {
                return userJpaEnity.getUsername();
        }

        @Override
        public boolean isAccountNonExpired() {
                return true;
        }

        @Override
        public boolean isAccountNonLocked() {
                return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
                return true;
        }

        @Override
        public boolean isEnabled() {
                return true;
        }
}
