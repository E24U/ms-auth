package e24u.auth.data.user;

import e24u.auth.data.role.Role;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Table("users")
public class User {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;
    private String login;
    private String password;
    private Set<Role> roles = new HashSet<>();
}