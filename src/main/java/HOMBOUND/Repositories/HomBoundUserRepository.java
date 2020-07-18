package HOMBOUND.Repositories;

import HOMBOUND.Models.HomBoundUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HomBoundUserRepository extends JpaRepository<HomBoundUser, Long> {

    public HomBoundUser findByUsername(String Username);
}
