package com.uma.wanchinchun.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/relacion")
public class RelationControler {
    private final RelationService relationService;
    public RelationControler(RelationService relationService) { this.relationService = relationService; }

    @GetMapping
    public List<Product> getAll() {
        return relationService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getOne(@PathVariable Long id) {
        return relationService.findById(id)
                  .map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Product> create(@Valid @RequestBody Product p) {
        Product saved = relationService.save(p);
        URI loc = ServletUriComponentsBuilder.fromCurrentRequest()
                  .path("/{id}")
                  .buildAndExpand(saved.getId())
                  .toUri();
        return ResponseEntity.created(loc).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id,
                                          @Valid @RequestBody Product p) {
        if (!relationService.findById(id).isPresent()) {
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
