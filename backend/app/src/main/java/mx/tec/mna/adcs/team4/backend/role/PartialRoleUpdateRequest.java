package mx.tec.mna.adcs.team4.backend.role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartialRoleUpdateRequest implements RoleUpdateRequest {

    private String roleName;
    private String displayName;
    private String description;
    private RoleType type;

    @Override
    public RoleEntity mapToEntity(RoleEntity source) {
        if (getRoleName() != null) {
            source.setRole_name(getRoleName());
        }

        if (getDisplayName() != null) {
            source.setDisplay_name(getDisplayName());
        }

        if (getDescription() != null) {
            source.setDescription(getDescription());
        }

        if (getType() != null) {
            source.setType(getType());
        }

        return source;
    }

}



