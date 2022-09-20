package e24u.auth.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import e24u.auth.security.config.DataConfiguration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

//import static e24u.auth.security.JWTAuthenticationFilter.HEADER_STRING;
//import static e24u.auth.security.JWTAuthenticationFilter.SECRET;
//import static e24u.auth.security.JWTAuthenticationFilter.TOKEN_PREFIX;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter{

    private final DataConfiguration dataConfiguration;
    public JWTAuthorizationFilter(AuthenticationManager authManager, DataConfiguration dataConfiguration) {
        super(authManager);
        this.dataConfiguration = dataConfiguration;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(dataConfiguration.getHEADER_STRING());

        if (header == null || !header.startsWith(dataConfiguration.getTOKEN_PREFIX())) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(dataConfiguration.getHEADER_STRING());
        if (token != null) {
            /* parse the token. */
            String user = JWT.require(Algorithm.HMAC512(dataConfiguration.getSECRET().getBytes()))
                    .build()
                    .verify(token.replace(dataConfiguration.getTOKEN_PREFIX(), ""))
                    .getSubject();

            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }
            return null;
        }
        return null;
    }
}
