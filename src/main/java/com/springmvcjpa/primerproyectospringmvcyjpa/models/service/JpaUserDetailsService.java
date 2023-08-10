package com.springmvcjpa.primerproyectospringmvcyjpa.models.service;

import com.springmvcjpa.primerproyectospringmvcyjpa.models.dao.IUsuarioDao;
import com.springmvcjpa.primerproyectospringmvcyjpa.models.entity.Role;
import com.springmvcjpa.primerproyectospringmvcyjpa.models.entity.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("jpaUserDetailsService")
public class JpaUserDetailsService implements UserDetailsService {
    @Autowired
    private IUsuarioDao usuarioDao;

    private Logger logger = LoggerFactory.getLogger(JpaUserDetailsService.class);

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioDao.findByUsername(username);
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        if(usuario == null){
            logger.error("Error login, no existe el usuario " + username);
            throw new UsernameNotFoundException("Username" + username + " no existe en la bd");
        }
        for (Role role: usuario.getRoles()){
            logger.info("rol del usuario " + username ,
                    role.getAuthority());
            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }

        if(authorities.isEmpty()){
            logger.error("Error login, " + username + " no tiene rol asignado");
            throw new UsernameNotFoundException("Username" + username + " NO tiene rol(es) asignado(s)");
        }


        return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(),true, true, true, authorities);
    }
}
