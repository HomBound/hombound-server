package HOMBOUND.Repositories;

import HOMBOUND.Models.HomBoundUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomBoundUserRepository extends JpaRepository<HomBoundUser, Long> {
}
