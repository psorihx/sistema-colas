package com.idat.sistema_colas.service;

import com.idat.sistema_colas.entity.TipoTramite;
import com.idat.sistema_colas.repository.TipoTramiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoTramiteService {

    @Autowired
    private TipoTramiteRepository tipoTramiteRepository;

    public List<TipoTramite> findAll() {
        return tipoTramiteRepository.findAll();
    }

    public TipoTramite findById(Integer id) {
        return tipoTramiteRepository.findById(id).orElse(null);
    }
}
