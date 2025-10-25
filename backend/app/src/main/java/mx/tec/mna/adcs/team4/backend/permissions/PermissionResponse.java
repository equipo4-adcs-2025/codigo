package mx.tec.mna.adcs.team4.backend.permissions;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;

@Data
@AllArgsConstructor
@Builder
public class PermissionResponse {
    private PermissionId id;
    private String name;
    private String displayName;
    private String description;
    private LocalDateTime creationDate;
    private Boolean enabled;

    public static PermissionResponse from(PermissionServiceDto permissionServiceDto) {
        return PermissionResponse.builder()
                .id(permissionServiceDto.getPermissionId())
                .name(permissionServiceDto.getName())
                .displayName(permissionServiceDto.getDisplayName())
                .description(permissionServiceDto.getDescription())
                .creationDate(permissionServiceDto.getCreationDate())
                .enabled(permissionServiceDto.getEnabled())
                .build();
    }
}
