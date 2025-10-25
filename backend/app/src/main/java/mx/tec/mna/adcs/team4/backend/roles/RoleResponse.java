package mx.tec.mna.adcs.team4.backend.roles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;

@Data
@AllArgsConstructor
@Builder
public class RoleResponse {
    private RoleId id;
    private String roleName;
    private String displayName;
    private String description;
    private RoleType type;

    public static RoleResponse from(RoleServiceDto roleServiceDto) {
        return RoleResponse.builder()
                .id(roleServiceDto.getRoleId())
                .roleName(roleServiceDto.getRoleName())
                .displayName(roleServiceDto.getDisplayName())
                .description(roleServiceDto.getDescription())
                .type(roleServiceDto.getType())
                .build();
    }
}
