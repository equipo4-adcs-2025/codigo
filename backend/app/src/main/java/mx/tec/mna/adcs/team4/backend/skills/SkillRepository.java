package mx.tec.mna.adcs.team4.backend.skills;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SkillRepository extends JpaRepository<SkillEntity, UUID> {
}
