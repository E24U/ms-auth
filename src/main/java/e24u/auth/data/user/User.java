package e24u.auth.data.user;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Table(name = "users")
public class User {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;
    private String login;
    private String password;
}