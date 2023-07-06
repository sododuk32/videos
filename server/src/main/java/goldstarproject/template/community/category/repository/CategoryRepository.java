package goldstarproject.template.community.category.repository;

import goldstarproject.template.community.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
