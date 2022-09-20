package e24u.auth.data.role;

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
@Table(name = "roles")
public class Role {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;
    private String name;
}
