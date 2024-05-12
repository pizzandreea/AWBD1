package com.project.demo.repositories;

import com.project.demo.dtos.SoupLightDto;
import com.project.demo.models.Soup;
import com.project.demo.models.SoupType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SoupRepository extends JpaRepository<Soup, Integer> {
    List<Soup> findByType(SoupType type);
    @Query("SELECT DISTINCT s FROM Soup s JOIN s.ingredients i WHERE i IN :ingredients")
    List<Soup> findByIngredients(@Param("ingredients") List<String> ingredients);

    @Query("SELECT s from Soup s WHERE s.name LIKE CONCAT('%', :query, '%')")
    List<Soup> findByName(String query);
    List<Soup> findAllByOrderByPriceAsc();

    List<Soup> findAllByOrderByPriceDesc();
    @Query("SELECT s, SIZE(s.reviews) AS reviewCount FROM Soup s ORDER BY SIZE(s.reviews)  DESC")
    List<Soup> findAllSortedByReviewCount();
    boolean existsByName(String name);
}
