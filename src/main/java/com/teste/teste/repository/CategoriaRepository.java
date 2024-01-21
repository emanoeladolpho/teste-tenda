package com.teste.teste.repository;

import com.teste.teste.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    List<Categoria> findAllByAtivoIsTrue();

    List<Categoria> findAllByAtivoIsFalse();

    Categoria findByDescricaoIgnoreCase(String descricao);

    @Query(value = "select categoria from Categoria categoria " +
            " where categoria.descricao like concat('%', :descricao, '%')" +
            " and categoria.id <> :idCategoria ")
    Categoria findByDescricaoAndIdDifferent(@Param("descricao") String descricao, @Param("idCategoria") Long idCategoria);
}