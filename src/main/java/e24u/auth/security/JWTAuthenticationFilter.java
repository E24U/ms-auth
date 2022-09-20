package e24u.auth.security;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import e24u.auth.security.config.DataConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@RequiredArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

//    public static final String SECRET = "SecretKeyToGenJWTs";
//    public static final long EXPIRATION_TIME = 864_000_000; /* 10 days */
//    public static final String TOKEN_PREFIX = "Bearer ";
//    public static final String HEADER_STRING = "Authorization";
//    public static final String SIGN_UP_URL = "/users/sign-up";

    private final AuthenticationManager auth;
    private final DataConfiguration dataConfiguration;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {
        try {
            e24u.auth.data.user.User huares = new ObjectMapper()
                    .readValue(req.getInputStream(), e24u.auth.data.user.User.class);

            return auth.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            huares.getLogin(),
                            huares.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) {

        String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + dataConfiguration.getEXPIRATION_TIME()))
                .sign(HMAC512(dataConfiguration.getSECRET().getBytes()));
        res.addHeader(dataConfiguration.getHEADER_STRING(), dataConfiguration.getTOKEN_PREFIX() + token);
    }
}

