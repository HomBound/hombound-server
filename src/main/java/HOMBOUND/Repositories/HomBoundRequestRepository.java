package HOMBOUND.Repositories;

import HOMBOUND.Models.HomBoundRequest;
import HOMBOUND.Models.enums.HomBoundStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HomBoundRequestRepository extends JpaRepository<HomBoundRequest, Long> {
    public List<HomBoundRequest> findAllByStatus(String homBoundStatus);
}
