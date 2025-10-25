package mx.tec.mna.adcs.team4.backend.roles;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;
import mx.tec.mna.adcs.team4.backend.exceptions.NotFoundException;

@Repository
@AllArgsConstructor
public class RoleJpaRepository implements RoleRepository {
    private final SpringRoleJpaRepository delegate;

    @Override
    public Optional<RoleEntity> findRoleByRoleId(RoleId id) {
        return delegate.findRoleByEntityId(id.getValue());
    }

    @Override
    public List<RoleEntity> findAll() {
        return delegate.findAll();
    }

    @Override
    public RoleEntity save(RoleEntity role) {
        return delegate.save(role);
    }

    @Override
    public RoleEntity getRoleByRoleId(RoleId id) throws NotFoundException {
        return findRoleByRoleId(id)
                .orElseThrow(() -> new NotFoundException("Role with ID " + id.getValue() + " not found"));
    }

    @Override
    public RoleEntity deleteRoleByRoleId(RoleId roleId) {
        RoleEntity roleToDelete = getRoleByRoleId(roleId);
        delegate.delete(roleToDelete);
        return roleToDelete;
    }

}



