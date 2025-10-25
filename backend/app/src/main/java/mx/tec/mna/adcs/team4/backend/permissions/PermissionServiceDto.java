package mx.tec.mna.adcs.team4.backend.permissions;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class PermissionServiceDto {

    private final PermissionId permissionId;
    private final String name;
    private String displayName;
    private String description;
    private LocalDateTime creationDate;
    private Boolean enabled;

    public static PermissionServiceDto from(PermissionEntity foundPermission) {
        return PermissionServiceDto.builder()
                .permissionId(new PermissionId(foundPermission.getEntityId()))
                .name(foundPermission.getName())
                .displayName(foundPermission.getDisplay_name())
                .description(foundPermission.getDescription())
                .creationDate(foundPermission.getCreation_date())
                .enabled(foundPermission.getEnabled())
                .build();
    }

}
