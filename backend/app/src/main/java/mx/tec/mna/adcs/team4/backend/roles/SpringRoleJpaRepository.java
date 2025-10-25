package mx.tec.mna.adcs.team4.backend.roles;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringRoleJpaRepository extends JpaRepository<RoleEntity, UUID> {

    Optional<RoleEntity> findRoleByEntityId(UUID id);

}



