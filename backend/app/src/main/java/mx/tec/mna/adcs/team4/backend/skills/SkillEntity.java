package mx.tec.mna.adcs.team4.backend.skills;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "skill_catalog")
public class SkillEntity {

    @Id
    @Column(name = "skill_id", columnDefinition = "BINARY(16)")
    private UUID skillId;

    @Column(name = "skill_category_id", columnDefinition = "BINARY(16)")
    private UUID skillCategoryId;

    @Column(name = "skill_name", columnDefinition = "TEXT", nullable = false)
    private String skillName;

    @PrePersist
    public void generateId() {
        if (skillId == null) {
            skillId = UUID.randomUUID();
        }
    }

    // Getters y setters
    public UUID getSkillId() { return skillId; }
    public void setSkillId(UUID skillId) { this.skillId = skillId; }

    public UUID getSkillCategoryId() { return skillCategoryId; }
    public void setSkillCategoryId(UUID skillCategoryId) { this.skillCategoryId = skillCategoryId; }

    public String getSkillName() { return skillName; }
    public void setSkillName(String skillName) { this.skillName = skillName; }
}
