package mx.tec.mna.adcs.team4.backend.permissions;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Value;

@Value
public class PermissionId {

    @JsonValue private UUID value;

    public PermissionId(UUID value) {
        this.value = value;
    }

    public PermissionId(String value) {
        this.value = UUID.fromString(value);
    }

}
