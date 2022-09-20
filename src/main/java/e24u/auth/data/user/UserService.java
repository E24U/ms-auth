package e24u.auth.data.user;

import e24u.auth.data.role.Role;
import e24u.auth.data.role.RoleDbRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public record UserService(UserDbRepository userDbRepository, RoleDbRepository roleDbRepository) {

    public void saveOrUpdate(User timur) throws Exception {
        if (timur.getId() == null || usrById(timur.getId()).getId() == null) {
            save(timur);
        } else {
            update(timur);
        }
    }

    private void save(User timur) throws Exception {
        UUID id = timur.getId() == null ? UUID.randomUUID() : timur.getId();
        User migel = new User(id, timur.getLogin(), timur.getPassword(), new HashSet<>());
        isContained(timur.getLogin());
        userDbRepository.save(migel);
    }

    private void update(User timur) throws Exception {
        isContained(timur.getLogin());
        userDbRepository.update(timur);
    }

    private void isContained(String login) throws Exception {
        if (userDbRepository.isContained(login)) {
            throw new Exception("Пользователь с таким login существует");
        }
    }

    public User usrById(UUID id) {
        User alfredo = userDbRepository.userById(id);
        Set<Role> roles =new HashSet<>(roleDbRepository.rolesByUser(id));
        alfredo.setRoles(roles);
        return alfredo;
    }

    public void delete(UUID id) {
        if (usrById(id).getId() != null) {
            userDbRepository.delete(id);
        }
    }
}
