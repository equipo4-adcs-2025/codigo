package mx.tec.mna.adcs.team4.backend.permissions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartialPermissionUpdateRequest implements PermissionUpdateRequest {

    private String name;
    private String displayName;
    private String description;
    private Boolean enabled;

    @Override
    public PermissionEntity mapToEntity(PermissionEntity source) {
        if (getName() != null) {
            source.setName(getName());
        }

        if (getDisplayName() != null) {
            source.setDisplay_name(getDisplayName());
        }

        if (getDescription() != null) {
            source.setDescription(getDescription());
        }

        if (getEnabled() != null) {
            source.setEnabled(getEnabled());
        }

        return source;
    }

}



