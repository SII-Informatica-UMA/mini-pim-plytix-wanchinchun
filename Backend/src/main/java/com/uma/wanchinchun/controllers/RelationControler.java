package com.uma.wanchinchun.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.uma.wanchinchun.services.RelationService;
import com.uma.wanchinchun.models.Relationship;
import com.uma.wanchinchun.dtos.RelationDTO;

@RestController
@RequestMapping(path = "/relacion")
public class RelationControler {
    private final RelationService relationService;
    public RelationControler(RelationService relationService) { this.relationService = relationService; }

    @GetMapping
    public ResponseEntity<List<RelationDTO>> getAll() {
        return ResponseEntity.ok(relationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RelationDTO> getOne(@PathVariable Long id) {
        RelationDTO dto = relationService.findById(id);
        if(dto == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<RelationDTO> create(@RequestBody Relationship p) {
        RelationDTO saved = relationService.create(p);
        URI loc = ServletUriComponentsBuilder.fromCurrentRequest()
                  .path("/{id}")
                  .buildAndExpand(saved.getId())
                  .toUri();
        return ResponseEntity.created(loc).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RelationDTO> update(@PathVariable Long id,
                                          @RequestBody Relationship p) {
        RelationDTO dto = relationService.findById(id);
        if(dto == null){
            return ResponseEntity.notFound().build();
        }
        /*if (relationService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }*/
        p.setId(id);
        return ResponseEntity.ok(relationService.create(p));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        relationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
