package mx.tec.mna.adcs.team4.backend.users;

import java.util.List;

public interface UserService {

    UserServiceDto getUserById(UserId id);

    List<UserServiceDto> getAllUsers();

    UserServiceDto createUser(NewUserRequest request);

    UserServiceDto updateUser(UserId userId, UserUpdateRequest request);

    UserServiceDto deleteUserByUserId(UserId id);

}
