package e24u.auth.data.role;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public record RoleService(RoleDbRepository roleDbRepository) {

    public List<Role> allRoles() {
        return roleDbRepository.allRoles();
    }

    public Role roleById(UUID id) {
        return roleDbRepository.roleById(id);
    }

    public void saveOrUpdate(Role role) throws Exception {
        if (role.getId() == null || roleById(role.getId()) == null) {
            save(role);
        } else {
            update(role);
        }
    }


    private void save(Role role) throws Exception {
        UUID id = role.getId() == null ? UUID.randomUUID() : role.getId();
        Role created = new Role(id, role.getName());
        isContained(created.getName());
        roleDbRepository.save(created);
    }

    private void update(Role role) throws Exception {
        isContained(role.getName());
        roleDbRepository.update(role);
    }

    private void isContained(String name) throws Exception {
        if (roleDbRepository.isContained(name)) {
            throw new Exception("Роль с таким именем существует");
        }
    }

    public void delete(UUID id) {
        roleDbRepository.delete(id);
    }
}
