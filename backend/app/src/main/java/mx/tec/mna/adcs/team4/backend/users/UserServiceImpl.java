package mx.tec.mna.adcs.team4.backend.users;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordService passwordService;

    @Override
    public UserServiceDto getUserById(UserId id) {
        return UserServiceDto.from(userRepository.getUserByUserId(id));
    }

    @Override
    public List<UserServiceDto> getAllUsers() {
        return userRepository.findAll().stream().map(foundUser -> UserServiceDto.from(foundUser))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    @Transactional
    public UserServiceDto createUser(NewUserRequest request) {
        String encryptedPassword = passwordService.encode(request.getPassword());
        UserEntity createdUser = userRepository.save(request.toEntity(encryptedPassword));
        return UserServiceDto.from(createdUser);
    }

    @Override
    @Transactional
    public UserServiceDto updateUser(UserId userId, UserUpdateRequest request) {
        UserEntity userToUpdate = userRepository.getUserByUserId(userId);

        UserEntity updatedUser = userRepository.save(
                request.mapToEntity(userToUpdate));

        return UserServiceDto.from(updatedUser);
    }

    @Override
    public UserServiceDto deleteUserByUserId(UserId id) {
        UserEntity deletedUser = userRepository.deleteUserByUserId(id);
        return UserServiceDto.from(deletedUser);
    }

}
