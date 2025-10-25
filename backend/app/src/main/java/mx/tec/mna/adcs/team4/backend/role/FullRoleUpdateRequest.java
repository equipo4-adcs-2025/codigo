package mx.tec.mna.adcs.team4.backend.role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FullRoleUpdateRequest implements RoleUpdateRequest {
    private String roleName;
    private String displayName;
    private String description;
    private RoleType type;

    @Override
    public RoleEntity mapToEntity(RoleEntity source) {
        source.setRole_name(getRoleName());
        source.setDisplay_name(getDisplayName());
        source.setDescription(getDescription());
        source.setType(getType());
        return source;
    }

}



