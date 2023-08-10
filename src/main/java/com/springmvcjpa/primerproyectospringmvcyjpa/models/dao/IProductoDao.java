package com.springmvcjpa.primerproyectospringmvcyjpa.models.dao;


import com.springmvcjpa.primerproyectospringmvcyjpa.models.entity.Producto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IProductoDao extends CrudRepository<Producto, Long> {
    @Query("select p FROM Producto p WHERE p.nombre LIKE CONCAT('%',:term,'%')")
    public List<Producto> findByName(@Param("term") String term);

    public List<Producto> findByNombreLikeIgnoreCase(String term);
}
