package com.uma.wanchinchun.services;

import java.util.List;
import java.util.stream.Collectors;
//import com.uma.wanchinchun.clients.AccountPlanClient;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uma.wanchinchun.dtos.RelationDTO;
import com.uma.wanchinchun.models.Relationship;
import com.uma.wanchinchun.repositories.RelationRepository;

@Service
@Transactional
public class RelationService {

    private final RelationRepository repo;
    //private final AccountPlanClient accountClient;

    public RelationService(RelationRepository repo) {
        this.repo = repo;
    }

    /*public List<Relationship> findAll(/*Long accountId) {
        /*if (!accountClient.hasAccessToAccount(accountId)) {
            throw new IllegalStateException("No access to account " + accountId);
        }
        return repo.findAll()
                   .stream()
                   //.map(r -> mapToRelationDTO(r, Relationship.class))
                   .collect(Collectors.toList());
    }

    public RelationDTO findById(Long id) {
        Relationship r = repo.findById(id)
                             .orElseThrow(() -> new IllegalArgumentException("No existe relación " + id));
        return mapToRelationDTO(r, RelationDTO.class);
    }

    public RelationDTO create(/*Long accountId, RelationDTO dto) {
        /* comprobar límite en cuentas/planes
        if (!accountClient.checkRelationshipLimit(accountId)) {
            throw new IllegalStateException("Límite de relaciones alcanzado para la cuenta " + accountId);
        }
        Relationship entity = mapToRelationDTO(dto, Relationship.class);
        Relationship saved = repo.save(entity);
        // notificar al microservicio de cuentas/planes
        dto.setId(saved.getId());
        //accountClient.notifyNewRelationship(accountId, dto);
        return mapToRelationDTO(saved, RelationDTO.class);
    }
    
    public RelationDTO update(Long id, RelationDTO dto) {
        Relationship existing = repo.findById(id)
                                   .orElseThrow(() -> new IllegalArgumentException("No existe relación " + id));
        mapToRelationDTO(dto, existing);
        existing.setId(id);
        Relationship updated = repo.save(existing);
        return mapToRelationDTO(updated, RelationDTO.class);
    }*/





    public List<Relationship> findAll(/*Long accountId*/) {
        /*if (!accountClient.hasAccessToAccount(accountId)) {
            throw new IllegalStateException("No access to account " + accountId);
        }*/
        return repo.findAll();
    }

    public Relationship findById(Long id) {
        Relationship r = repo.findById(id)
                             .orElseThrow(() -> new IllegalArgumentException("No existe relación " + id));
        return r;
    }

    public Relationship create(/*Long accountId,*/ Relationship relacion) {
        /* comprobar límite en cuentas/planes
        if (!accountClient.checkRelationshipLimit(accountId)) {
            throw new IllegalStateException("Límite de relaciones alcanzado para la cuenta " + accountId);
        }*/
        // notificar al microservicio de cuentas/planes
        //accountClient.notifyNewRelationship(accountId, dto);
        return repo.save(relacion);
    }

    public Relationship update(Long id, Relationship dto) {
        Relationship existing = repo.findById(id)
                                   .orElseThrow(() -> new IllegalArgumentException("No existe relación " + id));
        existing.setId(id);
        return repo.save(existing);
    }

    public void delete(Long id) {
        // aquí podría notificar borrado
        repo.deleteById(id);
    }

    private RelationDTO mapToRelationDTO(Relationship product) {
        return new RelationDTO();
        /*return RelationDTO().builder()
            .id(product.getId())
            .idOrigen(product.getIdOrigen())
            .idDestino(product.getIdDestino())
            .tipoRelacion(product.getTipoRelacion())
            .build();*/
    }
}
