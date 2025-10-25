package mx.tec.mna.adcs.team4.backend.role;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "`role`")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID entityId;

    @Column(name = "role_name", nullable = false, unique = true)
    private String role_name;

    @Column(name = "display_name")
    private String display_name;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    private RoleType type;
}
