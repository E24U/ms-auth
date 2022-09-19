package e24u.auth.security.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Getter
@Setter
@Component
public class DataConfiguration {

    private final String SECRET = "SecretKeyToGenJWTs";
    private final long EXPIRATION_TIME = 864_000_000; /* 10 days */
    private final String TOKEN_PREFIX = "Bearer ";
    private final String HEADER_STRING = "Authorization";
    private final String SIGN_UP_URL = "/user";
}
