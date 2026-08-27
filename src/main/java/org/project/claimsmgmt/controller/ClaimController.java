package org.project.claimsmgmt.controller;

import org.project.claimsmgmt.model.Claim;
import org.project.claimsmgmt.service.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/claims")
public class ClaimController {

    @Autowired
    private ClaimService claimService;

    @GetMapping
    public List<Claim> getAll() {
        return claimService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Claim> getById(@PathVariable String id) {
        return claimService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Claim> create(@RequestBody Claim claim) {
        Claim created = claimService.create(claim);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Claim> update(@PathVariable String id, @RequestBody Claim claim) {
        return claimService.update(id, claim)
                .map(c -> ResponseEntity.ok(claim))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        boolean deleted = claimService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
