package e24u.auth.data.user;

import io.swagger.annotations.ApiOperation;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
record UserController(UserService userService, BCryptPasswordEncoder encoder) {


    @PostMapping("/user")
    @ApiOperation("создание и обновление пользователей")
    public void create(@RequestBody User timur) throws Exception {
        timur.setPassword(encoder.encode(timur.getPassword()));
        userService.saveOrUpdate(timur);
    }

    @GetMapping("/users/{id}")
    public User findById(@PathVariable UUID id) {
        return userService.usrById(id);
    }

    @DeleteMapping("/users/{id}")
    public void delete(@PathVariable UUID id) {
        userService.delete(id);
    }
}
