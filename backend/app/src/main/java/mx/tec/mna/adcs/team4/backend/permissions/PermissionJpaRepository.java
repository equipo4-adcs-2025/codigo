package mx.tec.mna.adcs.team4.backend.permissions;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;
import mx.tec.mna.adcs.team4.backend.exceptions.NotFoundException;

@Repository
@AllArgsConstructor
public class PermissionJpaRepository implements PermissionRepository {
    private final SpringPermissionJpaRepository delegate;

    @Override
    public Optional<PermissionEntity> findPermissionByPermissionId(PermissionId id) {
        return delegate.findPermissionByEntityId(id.getValue());
    }

    @Override
    public List<PermissionEntity> findAll() {
        return delegate.findAll();
    }

    @Override
    public PermissionEntity save(PermissionEntity permission) {
        return delegate.save(permission);
    }

    @Override
    public PermissionEntity getPermissionByPermissionId(PermissionId id) throws NotFoundException {
        return findPermissionByPermissionId(id)
                .orElseThrow(() -> new NotFoundException("Permission with ID " + id.getValue() + " not found"));
    }

    @Override
    public PermissionEntity deletePermissionByPermissionId(PermissionId permissionId) {
        PermissionEntity permissionToDelete = getPermissionByPermissionId(permissionId);
        delegate.delete(permissionToDelete);
        return permissionToDelete;
    }

}



