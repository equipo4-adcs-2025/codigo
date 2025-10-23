package mx.tec.mna.adcs.team4.backend.users;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserServiceDto> users = userService.getAllUsers();
        return ResponseEntity.ok().body(
                users.stream().map(userDto -> UserResponse.from(userDto))
                        .collect(Collectors.toUnmodifiableList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable UserId id) {
        UserServiceDto foundUser = userService.getUserById(id);
        return ResponseEntity.ok().body(UserResponse.from(foundUser));
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody NewUserRequest request) {
        UserServiceDto createdUser = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.from(createdUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> fullUpdateUserInfo(@PathVariable UserId id,
            @RequestBody FullUserUpdateRequest request) {
        UserServiceDto updatedUser = userService.updateUser(id, request);
        return ResponseEntity.ok().body(UserResponse.from(updatedUser));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> partialUpdateUser(@PathVariable UserId id,
            @RequestBody PartialUserUpdateRequest request) {
        UserServiceDto updatedUser = userService.updateUser(id, request);
        return ResponseEntity.ok().body(UserResponse.from(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable UserId id) {
        UserServiceDto deletedUser = userService.deleteUserByUserId(id);
        return ResponseEntity.ok().body(UserResponse.from(deletedUser));
    }
}
