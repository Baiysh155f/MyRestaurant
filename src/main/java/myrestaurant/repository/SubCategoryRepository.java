package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import peaksoft.entity.SubCategory;
@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
}