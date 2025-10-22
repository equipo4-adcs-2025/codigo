package mx.tec.mna.adcs.team4.backend.users;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Value;

@Value
public class UserId {

    @JsonValue private UUID value;

    public UserId(UUID value) {
        this.value = value;
    }

    public UserId(String value) {
        this.value = UUID.fromString(value);
    }

}
