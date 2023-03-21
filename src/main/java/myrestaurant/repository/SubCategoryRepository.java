package myrestaurant.repository;

import myrestaurant.dto.response.subCategories.SubCategoryResponse;
import myrestaurant.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    @Query("select new myrestaurant.dto.response.subCategories.SubCategoryResponse(s.name)from Category s where s.id = ?1")
    List<SubCategoryResponse> getSubCategoryByCategoryIdis(Long categoryId);

    @Query("select new myrestaurant.dto.response.subCategories.SubCategoryResponse(s.name) from SubCategory s")
    List<SubCategoryResponse>getAll();
    @Query("select new myrestaurant.dto.response.subCategories.SubCategoryResponse(s.name)from Category c join c.subCategory s where c.id = ?1")
    List<SubCategoryResponse> getAllByCategoryId(Long categoryId);
}