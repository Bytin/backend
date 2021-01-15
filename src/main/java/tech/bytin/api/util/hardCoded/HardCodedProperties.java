package tech.bytin.api.util.hardCoded;

import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import lombok.Getter;
import lombok.Setter;

@Service
@ConfigurationProperties("hardcoded")
@Getter @Setter
class HardCodedProperties {
    private Map<String, String> admin;
}
