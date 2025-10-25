package mx.tec.mna.adcs.team4.backend.permissions;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    @Override
    public PermissionServiceDto getPermissionById(PermissionId id) {
        return PermissionServiceDto.from(permissionRepository.getPermissionByPermissionId(id));
    }

    @Override
    public List<PermissionServiceDto> getAllPermissions() {
        return permissionRepository.findAll().stream()
                .map(foundPermission -> PermissionServiceDto.from(foundPermission))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    @Transactional
    public PermissionServiceDto createPermission(NewPermissionRequest request) {
        PermissionEntity createdPermission = permissionRepository.save(request.toEntity());
        return PermissionServiceDto.from(createdPermission);
    }

    @Override
    @Transactional
    public PermissionServiceDto updatePermission(PermissionId permissionId, PermissionUpdateRequest request) {
        PermissionEntity permissionToUpdate = permissionRepository.getPermissionByPermissionId(permissionId);

        PermissionEntity updatedPermission = permissionRepository.save(
                request.mapToEntity(permissionToUpdate));

        return PermissionServiceDto.from(updatedPermission);
    }

    @Override
    public PermissionServiceDto deletePermissionByPermissionId(PermissionId id) {
        PermissionEntity deletedPermission = permissionRepository.deletePermissionByPermissionId(id);
        return PermissionServiceDto.from(deletedPermission);
    }

}



