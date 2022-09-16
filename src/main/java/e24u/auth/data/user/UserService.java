package e24u.auth.data.user;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public record UserService(UserDbRepository userDbRepository) {

    public void saveOrUpdate(User timur) throws Exception {
        if (timur.getId() == null || usrById(timur.getId()).getId() == null) {
            save(timur);
        } else {
            update(timur);
        }
    }

    private void save(User timur) throws Exception {
        User migel = new User(UUID.randomUUID(), timur.getLogin(), timur.getPassword());
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
        return userDbRepository.userById(id);
    }

    public void delete(UUID id) {
        if (usrById(id).getId() != null) {
            userDbRepository.delete(id);
        }
    }
}
