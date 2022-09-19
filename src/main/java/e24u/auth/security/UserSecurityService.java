package e24u.auth.security;

import e24u.auth.data.user.UserDbRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService {
    private final UserDbRepository userDbRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        e24u.auth.data.user.User user = userDbRepository.userByLogin(login);
        if (user.getId() == null) {
            throw new UsernameNotFoundException(login);
        }
        return new User(user.getLogin(), user.getPassword(), emptyList());
    }
}

