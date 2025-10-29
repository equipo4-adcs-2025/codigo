package mx.tec.mna.adcs.team4.backend.skillcategories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SkillCategoryRepository extends JpaRepository<SkillCategoryEntity, UUID> {
}
