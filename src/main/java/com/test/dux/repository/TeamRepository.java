package com.test.dux.repository;

import com.test.dux.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity,Long> {
    @Query(value = "SELECT * FROM TEAMS t WHERE LOWER(t.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))",nativeQuery = true)
    List<TeamEntity> findByNombreContainingIgnoreCase(@Param("nombre") String nombre);
}
