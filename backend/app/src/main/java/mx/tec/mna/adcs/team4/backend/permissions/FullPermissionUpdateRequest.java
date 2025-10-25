package mx.tec.mna.adcs.team4.backend.permissions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FullPermissionUpdateRequest implements PermissionUpdateRequest {
    private String name;
    private String displayName;
    private String description;
    private Boolean enabled;

    @Override
    public PermissionEntity mapToEntity(PermissionEntity source) {
        source.setName(getName());
        source.setDisplay_name(getDisplayName());
        source.setDescription(getDescription());
        source.setEnabled(getEnabled());
        return source;
    }

}



