package mx.tec.mna.adcs.team4.backend.role;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class RoleServiceDto {

    private final RoleId roleId;
    private final String roleName;
    private String displayName;
    private String description;
    private RoleType type;

    public static RoleServiceDto from(RoleEntity foundRole) {
        return RoleServiceDto.builder()
                .roleId(new RoleId(foundRole.getEntityId()))
                .roleName(foundRole.getRole_name())
                .displayName(foundRole.getDisplay_name())
                .description(foundRole.getDescription())
                .type(foundRole.getType())
                .build();
    }

}
