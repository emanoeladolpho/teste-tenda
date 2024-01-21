package com.teste.teste.repository;

import com.teste.teste.model.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProdutoRepository extends JpaRepository<Produto,Long> {

    Page<Produto> findAllByAtivoIsTrue(Pageable pageable);

    Page<Produto> findAllByAtivoIsFalse(Pageable pageable);

    Page<Produto> findAllByMarcaIdAndAtivoIsTrue(Long idMarca,Pageable pageable);

    Page<Produto> findAllByCategoriaIdAndAtivoIsTrue(Long idCategoria, Pageable pageable);

    Produto findBySku(String sku);

    @Query(value = "select produto from Produto produto " +
            "where produto.sku = :sku " +
            " and produto.id <> :idProduto")
    Produto findBySkuAndDifferentId(@Param("sku") String sku, @Param("idProduto") Long idProduto);
}