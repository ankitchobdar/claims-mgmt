package org.project.claimsmgmt.service;

import org.project.claimsmgmt.model.Claim;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClaimService {
    // Use an ArrayList to mimic a database
    private final List<Claim> claims = new ArrayList<>();

    public ClaimService() {
        // seed with a sample claim (optional)
        Claim sample = new Claim(UUID.randomUUID().toString(), LocalDate.now(), "PAID", new BigDecimal("123.45"));
        claims.add(sample);
    }

    public List<Claim> findAll() {
        return new ArrayList<>(claims);
    }

    public Optional<Claim> findById(String id) {
        return claims.stream().filter(c -> c.getClaimid() != null && c.getClaimid().equals(id)).findFirst();
    }

    public Claim create(Claim claim) {
        if (claim.getClaimid() == null || claim.getClaimid().isEmpty()) {
            claim.setClaimid(UUID.randomUUID().toString());
        }
        claims.add(claim);
        return claim;
    }

    public Optional<Claim> update(String id, Claim updated) {
        Optional<Claim> existingOpt = findById(id);
        existingOpt.ifPresent(existing -> {
            // preserve id
            updated.setClaimid(existing.getClaimid());
            // remove and re-add or modify fields in-place
            int idx = claims.indexOf(existing);
            if (idx >= 0) {
                claims.set(idx, updated);
            }
        });
        return existingOpt.map(c -> updated);
    }

    public boolean delete(String id) {
        Optional<Claim> existingOpt = findById(id);
        existingOpt.ifPresent(claims::remove);
        return existingOpt.isPresent();
    }
}
