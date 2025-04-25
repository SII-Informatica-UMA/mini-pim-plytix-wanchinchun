package com.uma.wanchinchun.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uma.wanchinchun.clients.AccountPlanClient;
import com.uma.wanchinchun.dto.RelationshipDTO;
import com.uma.wanchinchun.models.Relationship;
import com.uma.wanchinchun.repositories.RelationRepository;

@Service
@Transactional
public class RelationshipService {

    private final RelationRepository repo;
    private final AccountPlanClient accountClient;
    private final ModelMapper mapper;

    public RelationshipService(RelationRepository repo,
                               ModelMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public List<RelationshipDTO> findAll(Long accountId) {
        /*if (!accountClient.hasAccessToAccount(accountId)) {
            throw new IllegalStateException("No access to account " + accountId);
        }*/
        return repo.findAll()
                   .stream()
                   .map(r -> mapper.map(r, RelationshipDTO.class))
                   .collect(Collectors.toList());
    }

    public RelationshipDTO findById(Long accountId, Long id) {
        Relationship r = repo.findById(id)
                             .orElseThrow(() -> new IllegalArgumentException("No existe relación " + id));
        // opcional: validar que el usuario tenga acceso a esos productos...
        return mapper.map(r, RelationshipDTO.class);
    }

    public RelationshipDTO create(Long accountId, RelationshipDTO dto) {
        // 1) comprobar límite en cuentas/planes
        if (!accountClient.checkRelationshipLimit(accountId)) {
            throw new IllegalStateException("Límite de relaciones alcanzado para la cuenta " + accountId);
        }
        // 2) mapear DTO a entidad
        Relationship entity = mapper.map(dto, Relationship.class);
        // 3) guardar
        Relationship saved = repo.save(entity);
        // 4) notificar al microservicio de cuentas/planes
        dto.setId(saved.getId());
        accountClient.notifyNewRelationship(accountId, dto);
        // 5) devolver DTO resultante
        return mapper.map(saved, RelationshipDTO.class);
    }

    public RelationshipDTO update(Long accountId, Long id, RelationshipDTO dto) {
        // similar: buscar, comprobar acceso, actualizar campos, guardar...
        Relationship existing = repo.findById(id)
                                   .orElseThrow(() -> new IllegalArgumentException("No existe relación " + id));
        mapper.map(dto, existing);
        existing.setId(id);
        Relationship updated = repo.save(existing);
        return mapper.map(updated, RelationshipDTO.class);
    }

    public void delete(Long accountId, Long id) {
        // aquí podrías notificar borrado si lo requiere la lógica
        repo.deleteById(id);
    }
}
