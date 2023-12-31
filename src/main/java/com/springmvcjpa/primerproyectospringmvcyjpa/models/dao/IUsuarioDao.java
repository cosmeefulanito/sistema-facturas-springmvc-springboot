package com.springmvcjpa.primerproyectospringmvcyjpa.models.dao;

import com.springmvcjpa.primerproyectospringmvcyjpa.models.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface IUsuarioDao extends CrudRepository<Usuario,Long> {
    public Usuario findByUsername(String username);
}
