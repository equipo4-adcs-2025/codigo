package mx.tec.mna.adcs.team4.backend.skillcategories;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class SkillCategoryServiceImpl implements SkillCategoryService {

    private final SkillCategoryRepository repository;

    public SkillCategoryServiceImpl(SkillCategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<SkillCategoryEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public SkillCategoryEntity getById(UUID id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public SkillCategoryEntity create(SkillCategoryEntity category) {
        return repository.save(category);
    }

    @Override
    public SkillCategoryEntity update(UUID id, SkillCategoryEntity updated) {
        SkillCategoryEntity existing = repository.findById(id).orElse(null);
        if (existing == null) return null;
        existing.setSkillCategoryName(updated.getSkillCategoryName());
        return repository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
