package mx.tec.mna.adcs.team4.backend.users;

import lombok.Data;

import java.util.Optional;

import lombok.Builder;

@Data
@Builder
public class UserServiceDto {

    private final UserId userId;
    private final String email;
    private String firstName;
    private String lastName;
    private String title;
    private String country;
    private String practice;
    private String lanId;

    public static UserServiceDto from(UserEntity foundUser) {
        UserInfoEntity userInfo = Optional.ofNullable(foundUser.getUserInfo()).orElse(new UserInfoEntity());
        return UserServiceDto.builder()
                .userId(new UserId(foundUser.getUserId()))
                .email(foundUser.getEmail())
                .firstName(userInfo.getFirstName())
                .lastName(userInfo.getLastName())
                .title(userInfo.getTitle())
                .country(userInfo.getCountry())
                .practice(userInfo.getPractice())
                .lanId(userInfo.getLanId())
                .build();
    }

}
