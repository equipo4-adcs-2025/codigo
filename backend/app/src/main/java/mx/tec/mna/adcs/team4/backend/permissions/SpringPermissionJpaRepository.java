package mx.tec.mna.adcs.team4.backend.permissions;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringPermissionJpaRepository extends JpaRepository<PermissionEntity, UUID> {

    Optional<PermissionEntity> findPermissionByEntityId(UUID id);

}



