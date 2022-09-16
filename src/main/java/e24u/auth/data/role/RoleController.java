package e24u.auth.data.role;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController()
public record RoleController(RoleService roleService) {


    @GetMapping("role/all")
    public List<Role> findAll() {
       return roleService.allRoles();
    }
    @GetMapping("role/{id}")
    public Role roleById(@PathVariable UUID id) {
        return roleService.roleById(id);
    }

    @PostMapping("role/new")
    public void save(@RequestBody Role role) throws Exception {
        roleService.saveOrUpdate(role);
    }

    @DeleteMapping("role/drop/{id}")
    public void delete(@PathVariable UUID id) {
        roleService.delete(id);
    }
}
