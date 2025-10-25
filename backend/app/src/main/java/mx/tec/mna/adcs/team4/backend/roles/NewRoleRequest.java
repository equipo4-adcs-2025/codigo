package mx.tec.mna.adcs.team4.backend.roles;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewRoleRequest {

    private String roleName;
    private String displayName;
    private String description;

    public RoleEntity toEntity() {
        var roleId = UUID.randomUUID();

        return RoleEntity.builder()
                .entityId(roleId)
                .role_name(getRoleName())
                .display_name(getDisplayName())
                .description(getDescription())
                .type(RoleType.CUSTOM)
                .build();
    }

}


