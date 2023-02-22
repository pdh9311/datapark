package donpark.datapark.repository;

import donpark.datapark.domain.Icon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IconRepository extends JpaRepository<Icon, Long> {
  Optional<Icon> findByStoredName(String originName);
}
