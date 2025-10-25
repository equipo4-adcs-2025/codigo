package mx.tec.mna.adcs.team4.backend.roles;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Value;

@Value
public class RoleId {

    @JsonValue private UUID value;

    public RoleId(UUID value) {
        this.value = value;
    }

    public RoleId(String value) {
        this.value = UUID.fromString(value);
    }

}
