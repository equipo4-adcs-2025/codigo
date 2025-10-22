package mx.tec.mna.adcs.team4.backend.users;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;
import mx.tec.mna.adcs.team4.backend.exceptions.NotFoundException;

@Repository
@AllArgsConstructor
public class UserJpaRepository implements UserRepository {
    private final SpringUserJpaRepository delegate;

    @Override
    public Optional<UserEntity> findUserByUserId(UserId id) {
        return delegate.findUserByUserId(id.getValue());
    }

    @Override
    public List<UserEntity> findAll() {
        return delegate.findAll();
    }

    @Override
    public UserEntity save(UserEntity user) {
        return delegate.save(user);
    }

    @Override
    public UserEntity getUserByUserId(UserId id) throws NotFoundException {
        return findUserByUserId(id)
                .orElseThrow(() -> new NotFoundException("User with ID " + id.getValue() + " not found"));
    }

    @Override
    public UserEntity deleteUserByUserId(UserId userId) {
        UserEntity userToDelete = getUserByUserId(userId);
        delegate.delete(userToDelete);
        return userToDelete;
    }

}
