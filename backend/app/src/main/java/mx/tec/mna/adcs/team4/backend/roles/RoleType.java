package mx.tec.mna.adcs.team4.backend.roles;

/**
 * Enum representing the type of role in the system.
 * Distinguishes between system-defined roles and custom user-defined roles.
 */
public enum RoleType {
    /**
     * System-defined role that comes pre-configured.
     * These roles typically have special privileges and cannot be deleted.
     */
    SYSTEM,
    
    /**
     * Custom role created by administrators.
     * These roles can be freely created, modified, and deleted.
     */
    CUSTOM
}
