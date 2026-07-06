package com.idat.sistema_colas.repository;

import com.idat.sistema_colas.entity.TipoTramite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoTramiteRepository extends JpaRepository<TipoTramite, Integer> {
}
