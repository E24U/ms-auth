package e24u.auth.data.role;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController()
public record RoleController(RoleService roleService) {


    @GetMapping("roles")
    public List<Role> findAll() {
       return roleService.allRoles();
    }
    @GetMapping("roles/{id}")
    public Role roleById(@PathVariable UUID id) {
        return roleService.roleById(id);
    }
    @PostMapping("role")
    @ApiOperation("Создание и обновление ролей")
    public void save(@RequestBody Role role) throws Exception {
        roleService.saveOrUpdate(role);
    }
    @DeleteMapping("roles/{id}")
    public void delete(@PathVariable UUID id) {
        roleService.delete(id);
    }
}
