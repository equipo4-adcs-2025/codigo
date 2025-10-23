package mx.tec.mna.adcs.team4.backend.users;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FullUserUpdateRequest implements UserUpdateRequest {
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
        userInfoEntity.setFirstName(getFirstName());
        userInfoEntity.setLastName(getLastName());
        userInfoEntity.setTitle(getTitle());
        userInfoEntity.setPractice(getPractice());
        userInfoEntity.setCountry(getCountry());
        userInfoEntity.setLanId(getLanId());
        source.setUserInfo(userInfoEntity);
        return source;
    }

}
