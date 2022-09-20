package e24u.auth.data.role;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
@Transactional
@RequiredArgsConstructor
public class RoleDbRepository {

    private final JdbcTemplate jdbc;

    public List<Role> allRoles() {
        return jdbc.query("select * from roles",
                (rs, row) -> {
                    Role role = new Role();
                    role.setId(UUID.fromString(rs.getString("id")));
                    role.setName(rs.getString("name"));
                    return role;
                });
    }

    public Role roleById(UUID id) {
        return jdbc.query("select id, name from roles where id = ?", (rs, row) -> {
            return new Role((UUID.fromString(rs.getString("id"))),
                    rs.getString("name"));
        }, id).stream().findFirst().orElse(new Role());
    }

    public boolean isContained(String name) {
        return jdbc.queryForObject("select count(*) from roles WHERE name = ?", Boolean.class, name);
    }

    public void save(Role role) {
        jdbc.update("insert into roles(id, name) values (?, ?)",
                role.getId(), role.getName());
    }

    public void update(Role role) {
        jdbc.update("update roles set name = ? where id = ?",
                role.getName(), role.getId());
    }

    public List<Role> rolesByUser(UUID userId) {
        return jdbc.query("select * from roles as r left join users_roles ur on ur.role_id = r.id where ur.user_id = ?",
                (rs, row) -> {
                    Role role = new Role();
                    role.setId(UUID.fromString(rs.getString("role_id")));
                    role.setName(rs.getString("name"));
                    return role;
                }, userId);
    }

    public void delete(UUID id) {
       try {
           jdbc.update("delete from roles where id = ?", id);
       } catch (Exception e) {
           e.printStackTrace();
       }
    }
}

