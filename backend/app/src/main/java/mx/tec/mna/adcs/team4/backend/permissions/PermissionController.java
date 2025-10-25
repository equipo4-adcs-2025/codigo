package mx.tec.mna.adcs.team4.backend.permissions;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/permissions")
@AllArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @GetMapping
    public ResponseEntity<List<PermissionResponse>> getAllPermissions() {
        List<PermissionServiceDto> permissions = permissionService.getAllPermissions();
        return ResponseEntity.ok().body(
                permissions.stream().map(permissionDto -> PermissionResponse.from(permissionDto))
                        .collect(Collectors.toUnmodifiableList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PermissionResponse> getPermissionById(@PathVariable PermissionId id) {
        PermissionServiceDto foundPermission = permissionService.getPermissionById(id);
        return ResponseEntity.ok().body(PermissionResponse.from(foundPermission));
    }

    @PostMapping
    public ResponseEntity<PermissionResponse> createPermission(@RequestBody NewPermissionRequest request) {
        PermissionServiceDto createdPermission = permissionService.createPermission(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(PermissionResponse.from(createdPermission));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PermissionResponse> fullUpdatePermission(@PathVariable PermissionId id,
            @RequestBody FullPermissionUpdateRequest request) {
        PermissionServiceDto updatedPermission = permissionService.updatePermission(id, request);
        return ResponseEntity.ok().body(PermissionResponse.from(updatedPermission));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PermissionResponse> partialUpdatePermission(@PathVariable PermissionId id,
            @RequestBody PartialPermissionUpdateRequest request) {
        PermissionServiceDto updatedPermission = permissionService.updatePermission(id, request);
        return ResponseEntity.ok().body(PermissionResponse.from(updatedPermission));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PermissionResponse> deletePermission(@PathVariable PermissionId id) {
        PermissionServiceDto deletedPermission = permissionService.deletePermissionByPermissionId(id);
        return ResponseEntity.ok().body(PermissionResponse.from(deletedPermission));
    }
}



