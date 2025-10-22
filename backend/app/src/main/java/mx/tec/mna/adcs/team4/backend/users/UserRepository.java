package mx.tec.mna.adcs.team4.backend.users;

import java.util.List;
import java.util.Optional;

import mx.tec.mna.adcs.team4.backend.exceptions.NotFoundException;

public interface UserRepository {

    Optional<UserEntity> findUserByUserId(UserId id);

    UserEntity getUserByUserId(UserId id) throws NotFoundException;

    List<UserEntity> findAll();

    UserEntity save(UserEntity entity);

    UserEntity deleteUserByUserId(UserId id);

}
