package mx.tec.mna.adcs.team4.backend.permissions;

import java.util.List;
import java.util.Optional;

import mx.tec.mna.adcs.team4.backend.exceptions.NotFoundException;

public interface PermissionRepository {

    Optional<PermissionEntity> findPermissionByPermissionId(PermissionId id);

    PermissionEntity getPermissionByPermissionId(PermissionId id) throws NotFoundException;

    List<PermissionEntity> findAll();

    PermissionEntity save(PermissionEntity entity);

    PermissionEntity deletePermissionByPermissionId(PermissionId id);

}



