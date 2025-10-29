package mx.tec.mna.adcs.team4.backend.skills;

import java.util.List;
import java.util.UUID;

public interface SkillService {
    List<SkillEntity> getAll();
    SkillEntity getById(UUID id);
    SkillEntity create(SkillEntity skill);
    SkillEntity update(UUID id, SkillEntity updated);
    void delete(UUID id);
}
