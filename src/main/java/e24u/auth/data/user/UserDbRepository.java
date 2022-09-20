package e24u.auth.data.user;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Transactional
public class UserDbRepository {
    private final JdbcTemplate jdbc;

    public void save(User timur) {
        jdbc.update("insert into users(id, login, password) values (?, ?, ?)",
                timur.getId(), timur.getLogin(), timur.getPassword());
    }

    public User userById(UUID id) {
        return jdbc.query("select u.id, u.login, u.password from users as u left join users_roles ur on u.id = user_id where u.id = ?", (rs, row) -> {
            return new User((UUID.fromString(rs.getString("id"))),
                    rs.getString("login"), rs.getString("password"), new HashSet<>());
        }, id).stream().findFirst().orElse(new User());
    }
    public Boolean isContained(String login) {
        return jdbc.queryForObject("select count(*) from users WHERE login = ?", Boolean.class, login);
    }
    public void update(User timur) {
        jdbc.update("update users set (login, password) = (?, ?) where id = ?",
                timur.getLogin(), timur.getPassword(), timur.getId());
    }
    public void delete(UUID id) {
        jdbc.update("delete from users where id = ?",
                id);
    }
}


