package e24u.auth.data.user;

import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
record UserController(UserService userService) {

    @PostMapping("/user/new")
    public void create(@RequestBody User timur) throws Exception {
        userService.saveOrUpdate(timur);
    }

    @GetMapping("/user/{id}")
    public User findById(@PathVariable UUID id) {
        return userService.usrById(id);
    }

    @DeleteMapping("/user/drop/{uuid}")
    public void delete(@PathVariable UUID uuid) {
        userService.delete(uuid);
    }
}
