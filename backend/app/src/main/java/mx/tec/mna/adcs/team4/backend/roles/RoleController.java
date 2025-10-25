package mx.tec.mna.adcs.team4.backend.roles;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/roles")
@AllArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleResponse>> getAllRoles() {
        List<RoleServiceDto> roles = roleService.getAllRoles();
        return ResponseEntity.ok().body(
                roles.stream().map(roleDto -> RoleResponse.from(roleDto))
                        .collect(Collectors.toUnmodifiableList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> getRoleById(@PathVariable RoleId id) {
        RoleServiceDto foundRole = roleService.getRoleById(id);
        return ResponseEntity.ok().body(RoleResponse.from(foundRole));
    }

    @PostMapping
    public ResponseEntity<RoleResponse> createRole(@RequestBody NewRoleRequest request) {
        RoleServiceDto createdRole = roleService.createRole(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(RoleResponse.from(createdRole));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponse> fullUpdateRole(@PathVariable RoleId id,
            @RequestBody FullRoleUpdateRequest request) {
        RoleServiceDto updatedRole = roleService.updateRole(id, request);
        return ResponseEntity.ok().body(RoleResponse.from(updatedRole));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RoleResponse> partialUpdateRole(@PathVariable RoleId id,
            @RequestBody PartialRoleUpdateRequest request) {
        RoleServiceDto updatedRole = roleService.updateRole(id, request);
        return ResponseEntity.ok().body(RoleResponse.from(updatedRole));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RoleResponse> deleteRole(@PathVariable RoleId id) {
        RoleServiceDto deletedRole = roleService.deleteRoleByRoleId(id);
        return ResponseEntity.ok().body(RoleResponse.from(deletedRole));
    }
}



