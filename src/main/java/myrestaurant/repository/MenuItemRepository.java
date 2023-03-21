package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import peaksoft.entity.MenuItem;
@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
}