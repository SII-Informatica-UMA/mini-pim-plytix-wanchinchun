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
    public List<Relationship> getAll() {
        return relationService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Relationship> getOne(@PathVariable Long id) {
        return relationService.findById(id)
                  .map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Relationship> create(@RequestBody Relationship p) {
        Relationship saved = relationService.save(p);
        URI loc = ServletUriComponentsBuilder.fromCurrentRequest()
                  .path("/{id}")
                  .buildAndExpand(saved.getId())
                  .toUri();
        return ResponseEntity.created(loc).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Relationship> update(@PathVariable Long id,
                                          @RequestBody Relationship p) {
        if (relationService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        p.setId(id);
        return ResponseEntity.ok(relationService.save(p));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        relationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
