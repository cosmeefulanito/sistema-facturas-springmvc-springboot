package com.springmvcjpa.primerproyectospringmvcyjpa.models.dao;

import com.springmvcjpa.primerproyectospringmvcyjpa.models.entity.Cliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long> {
    @Query("SELECT c FROM Cliente c LEFT JOIN FETCH c.facturas f WHERE c.id =?1")
    public Cliente fetchByIdWithFactura(Long id);

}
