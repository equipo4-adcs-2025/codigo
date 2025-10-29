package mx.tec.mna.adcs.team4.backend.skills;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/skills")
@CrossOrigin(origins = "*")
public class SkillController {

    private final SkillService service;

    public SkillController(SkillService service) {
        this.service = service;
    }

    @GetMapping
    public List<SkillEntity> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public SkillEntity getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping
    public SkillEntity create(@RequestBody SkillEntity skill) {
        return service.create(skill);
    }

    @PutMapping("/{id}")
    public SkillEntity update(@PathVariable UUID id, @RequestBody SkillEntity updated) {
        return service.update(id, updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
