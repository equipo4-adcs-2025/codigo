package mx.tec.mna.adcs.team4.backend.users;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartialUserUpdateRequest implements UserUpdateRequest {

    private String firstName;
    private String lastName;
    private String title;
    private String practice;
    private String country;
    private String lanId;

    @Override
    public UserEntity mapToEntity(UserEntity source) {
        UserInfoEntity userInfoEntity = Optional.ofNullable(source.getUserInfo()).orElse(new UserInfoEntity());
        userInfoEntity.setUser(source);

        if (getFirstName() != null) {
            userInfoEntity.setFirstName(getFirstName());
        }

        if (getLastName() != null) {
            userInfoEntity.setLastName(getLastName());
        }

        if (getTitle() != null) {
            userInfoEntity.setTitle(getTitle());
        }

        if (getPractice() != null) {
            userInfoEntity.setPractice(getPractice());
        }

        if (getCountry() != null) {
            userInfoEntity.setCountry(getCountry());
        }

        if (getLanId() != null) {
            userInfoEntity.setLanId(getLanId());
        }

        source.setUserInfo(userInfoEntity);
        return source;
    }

}
