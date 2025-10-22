package mx.tec.mna.adcs.team4.backend.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;

@Data
@AllArgsConstructor
@Builder
public class UserResponse {
    private UserId id;
    private String email;
    private String firstName;
    private String lastName;
    private String title;
    private String country;
    private String practice;
    private String lanId;

    public static UserResponse from(UserServiceDto userServiceDto) {
        return UserResponse.builder()
                .id(userServiceDto.getUserId())
                .email(userServiceDto.getEmail())
                .firstName(userServiceDto.getFirstName())
                .lastName(userServiceDto.getLastName())
                .title(userServiceDto.getTitle())
                .country(userServiceDto.getCountry())
                .practice(userServiceDto.getPractice())
                .lanId(userServiceDto.getLanId())
                .build();
    }
}
