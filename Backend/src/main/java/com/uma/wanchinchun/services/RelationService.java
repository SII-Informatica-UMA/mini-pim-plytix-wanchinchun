package com.uma.wanchinchun.services;

import java.util.List;
import java.util.stream.Collectors;
//import com.uma.wanchinchun.clients.AccountPlanClient;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uma.wanchinchun.dtos.RelationDTO;
import com.uma.wanchinchun.models.Relationship;
import com.uma.wanchinchun.repositories.RelationRepository;

import org.springframework.web.client.RestTemplate;
import java.util.Optional;

@Service
@Transactional
public class RelationService {

    private final RelationRepository repo;
    //private final AccountPlanClient accountClient;
    private final RestTemplate restTemplate;

    public RelationService(RelationRepository repo, RestTemplate restTemplate) {
        this.repo = repo;
        this.restTemplate = restTemplate;
    }

    public List<RelationDTO> findAll(/*Long accountId*/) {
        /*if (!accountClient.hasAccessToAccount(accountId)) {
            throw new IllegalStateException("No access to account " + accountId);
        }*/
        return repo.findAll().stream()
            .map(this::mapToRelationDTO)
            .collect(Collectors.toList());
    }

    public RelationDTO findById(Long id) {
        /*Relationship r = repo.findById(id)
                             .orElseThrow(() -> new IllegalArgumentException("No existe relación " + id));*/
        Optional<Relationship> relacion = repo.findById(id);
        if (relacion.isEmpty()) {
            throw new IllegalArgumentException("No existe relación " + id);
        }
        return mapToRelationDTO(relacion.get());
    }

    public RelationDTO create(/*Long accountId,*/ Relationship relacion) {
        /* comprobar límite en cuentas/planes
        if (!accountClient.checkRelationshipLimit(accountId)) {
            throw new IllegalStateException("Límite de relaciones alcanzado para la cuenta " + accountId);
        }*/
        // notificar al microservicio de cuentas/planes
        //accountClient.notifyNewRelationship(accountId, dto);
        return mapToRelationDTO(repo.save(relacion));
    }

    public RelationDTO update(Long id, Relationship dto) {
        Relationship relacion = repo.findById(id)
                                   .orElseThrow(() -> new IllegalArgumentException("No existe relación " + id));
        dto.setId(id);
        return mapToRelationDTO(repo.save(dto));
    }

    public void deleteById(Long id) {
        // aquí podría notificar borrado
        repo.deleteById(id);
    }

    private RelationDTO mapToRelationDTO(Relationship product) {
        /*return new RelationDTO();
        return RelationDTO().builder()
            .id(product.getId())
            .idOrigen(product.getIdOrigen())
            .idDestino(product.getIdDestino())
            .tipoRelacion(product.getTipoRelacion())
            .build();*/
        return new RelationDTO(product.getId(), product.getNombre());
    }
}
