package mx.tec.mna.adcs.team4.backend.skillcategories;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/skill-categories")
@CrossOrigin(origins = "*")
public class SkillCategoryController {

    private final SkillCategoryService service;

    public SkillCategoryController(SkillCategoryService service) {
        this.service = service;
    }

    @GetMapping
    public List<SkillCategoryEntity> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public SkillCategoryEntity getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping
    public SkillCategoryEntity create(@RequestBody SkillCategoryEntity category) {
        return service.create(category);
    }

    @PutMapping("/{id}")
    public SkillCategoryEntity update(@PathVariable UUID id, @RequestBody SkillCategoryEntity updated) {
        return service.update(id, updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
