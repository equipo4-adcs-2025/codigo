package mx.tec.mna.adcs.team4.backend.skillcategories;

import java.util.List;
import java.util.UUID;

public interface SkillCategoryService {
    List<SkillCategoryEntity> getAll();
    SkillCategoryEntity getById(UUID id);
    SkillCategoryEntity create(SkillCategoryEntity category);
    SkillCategoryEntity update(UUID id, SkillCategoryEntity updated);
    void delete(UUID id);
}
