package com.teste.teste.repository;

import com.teste.teste.model.Categoria;
import com.teste.teste.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MarcaRepository extends JpaRepository<Marca,Long> {

    List<Marca> findAllByAtivoIsTrue();

    List<Marca> findAllByAtivoIsFalse();

    Marca findByDescricaoIgnoreCase(String descricao);

    @Query(value = "select marca from Marca marca" +
            " where marca.descricao like concat('%', :descricao, '%')" +
            " and marca.id <> :idMarca ")
    Marca findByDescricaoAndIdDifferent(@Param("descricao") String descricao, @Param("idMarca") Long idMarca);
}