package mx.tec.mna.adcs.team4.backend.roles;

import java.util.List;
import java.util.Optional;

import mx.tec.mna.adcs.team4.backend.exceptions.NotFoundException;

public interface RoleRepository {

    Optional<RoleEntity> findRoleByRoleId(RoleId id);

    RoleEntity getRoleByRoleId(RoleId id) throws NotFoundException;

    List<RoleEntity> findAll();

    RoleEntity save(RoleEntity entity);

    RoleEntity deleteRoleByRoleId(RoleId id);

}



