package com.springmvcjpa.primerproyectospringmvcyjpa.models.dao;

import com.springmvcjpa.primerproyectospringmvcyjpa.models.entity.Factura;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IFacturaDao extends CrudRepository<Factura, Long> {
    @Query("select f from Factura f " +
            "JOIN fetch f.cliente c " +
            "JOIN fetch f.items l " +
            "JOIN fetch l.producto WHERE f.id=?1")
    public Factura fetchByIdWithClienteWithItemFacturaWithProducto(Long id);
}
