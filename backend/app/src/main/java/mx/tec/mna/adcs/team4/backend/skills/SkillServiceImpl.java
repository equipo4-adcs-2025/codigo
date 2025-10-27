package mx.tec.mna.adcs.team4.backend.skills;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class SkillServiceImpl implements SkillService {

    private final SkillRepository repository;

    public SkillServiceImpl(SkillRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<SkillEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public SkillEntity getById(UUID id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public SkillEntity create(SkillEntity skill) {
        return repository.save(skill);
    }

    @Override
    public SkillEntity update(UUID id, SkillEntity updated) {
        SkillEntity existing = repository.findById(id).orElse(null);
        if (existing == null) return null;
        existing.setSkillCategoryId(updated.getSkillCategoryId());
        existing.setSkillName(updated.getSkillName());
        return repository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
