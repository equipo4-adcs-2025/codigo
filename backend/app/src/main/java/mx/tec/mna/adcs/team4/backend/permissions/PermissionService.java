package mx.tec.mna.adcs.team4.backend.permissions;

import java.util.List;

public interface PermissionService {

    PermissionServiceDto getPermissionById(PermissionId id);

    List<PermissionServiceDto> getAllPermissions();

    PermissionServiceDto createPermission(NewPermissionRequest request);

    PermissionServiceDto updatePermission(PermissionId permissionId, PermissionUpdateRequest request);

    PermissionServiceDto deletePermissionByPermissionId(PermissionId id);

}



