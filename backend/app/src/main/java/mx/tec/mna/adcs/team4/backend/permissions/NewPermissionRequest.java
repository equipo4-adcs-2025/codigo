package mx.tec.mna.adcs.team4.backend.permissions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewPermissionRequest {

    private String name;
    private String displayName;
    private String description;

    public PermissionEntity toEntity() {
        var permissionId = UUID.randomUUID();
        
        return PermissionEntity.builder()
                .entityId(permissionId)
                .name(getName())
                .display_name(getDisplayName())
                .description(getDescription())
                .creation_date(LocalDateTime.now())
                .enabled(true) // Por defecto habilitado
                .build();
    }

}


