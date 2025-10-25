package mx.tec.mna.adcs.team4.backend.role;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public RoleServiceDto getRoleById(RoleId id) {
        return RoleServiceDto.from(roleRepository.getRoleByRoleId(id));
    }

    @Override
    public List<RoleServiceDto> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(foundRole -> RoleServiceDto.from(foundRole))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    @Transactional
    public RoleServiceDto createRole(NewRoleRequest request) {
        RoleEntity createdRole = roleRepository.save(request.toEntity());
        return RoleServiceDto.from(createdRole);
    }

    @Override
    @Transactional
    public RoleServiceDto updateRole(RoleId roleId, RoleUpdateRequest request) {
        RoleEntity roleToUpdate = roleRepository.getRoleByRoleId(roleId);

        RoleEntity updatedRole = roleRepository.save(
                request.mapToEntity(roleToUpdate));

        return RoleServiceDto.from(updatedRole);
    }

    @Override
    public RoleServiceDto deleteRoleByRoleId(RoleId id) {
        RoleEntity deletedRole = roleRepository.deleteRoleByRoleId(id);
        return RoleServiceDto.from(deletedRole);
    }

}



