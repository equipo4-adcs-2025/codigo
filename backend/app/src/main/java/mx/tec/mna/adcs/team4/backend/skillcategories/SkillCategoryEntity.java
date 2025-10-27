package mx.tec.mna.adcs.team4.backend.skillcategories;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "skill_category_catalog")
public class SkillCategoryEntity {

    @Id
    @Column(name = "skill_category_id", columnDefinition = "BINARY(16)")
    private UUID skillCategoryId;

    @Column(name = "skill_category_name", columnDefinition = "TEXT", nullable = false)
    private String skillCategoryName;

    @PrePersist
    public void generateId() {
        if (skillCategoryId == null) {
            skillCategoryId = UUID.randomUUID();
        }
    }

    // Getters y Setters
    public UUID getSkillCategoryId() {
        return skillCategoryId;
    }

    public void setSkillCategoryId(UUID skillCategoryId) {
        this.skillCategoryId = skillCategoryId;
    }

    public String getSkillCategoryName() {
        return skillCategoryName;
    }

    public void setSkillCategoryName(String skillCategoryName) {
        this.skillCategoryName = skillCategoryName;
    }
}
