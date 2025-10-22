package mx.tec.mna.adcs.team4.backend.users;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewUserRequest {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String title;
    private String practice;
    private String country;
    private String lanId;

    public UserEntity toEntity(String encryptedPassword) {
        var userId = UUID.randomUUID();
        UserEntity userEntity = UserEntity.builder()
                .userId(userId)
                .email(getEmail())
                .password(encryptedPassword)
                .build();
        UserInfoEntity userInfo = UserInfoEntity.builder()
                .user(userEntity)
                .firstName(getFirstName())
                .lastName(getLastName())
                .title(getTitle())
                .practice(getPractice())
                .country(getCountry())
                .lanId(getLanId())
                .build();

        userEntity.setUserInfo(userInfo);

        return userEntity;
    }

}
