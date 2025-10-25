package mx.tec.mna.adcs.team4.backend.role;

import java.util.List;

public interface RoleService {

    RoleServiceDto getRoleById(RoleId id);

    List<RoleServiceDto> getAllRoles();

    RoleServiceDto createRole(NewRoleRequest request);

    RoleServiceDto updateRole(RoleId roleId, RoleUpdateRequest request);

    RoleServiceDto deleteRoleByRoleId(RoleId id);

}



