package org.project.claimsmgmt.service;

import lombok.extern.slf4j.Slf4j;
import org.project.claimsmgmt.model.Claim;
import org.project.claimsmgmt.repository.ClaimsRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class ClaimService {

    private final ClaimsRepository claimsRepository;

    public ClaimService(ClaimsRepository claimsRepository) {
        this.claimsRepository = claimsRepository;
        // seed with a sample claim if empty
        if (claimsRepository.count() == 0) {
            Claim sample = new Claim(UUID.randomUUID().toString(), LocalDate.now(), "PAID", new BigDecimal("123.45"));
            claimsRepository.save(sample);
            log.info("Seeded sample claim id={}", sample.getClaimId());
        }
        log.info("ClaimService initialized, repository record count={}", claimsRepository.count());
    }

    public List<Claim> findAll() {
        log.debug("findAll called");
        List<Claim> list = claimsRepository.findAll();
        log.debug("findAll returning {} claims", list.size());
        return list;
    }

    public Optional<Claim> findById(String id) {
        log.debug("findById called id={}", id);
        Optional<Claim> res = claimsRepository.findById(id);
        if (res.isPresent()) {
            log.debug("findById found id={}", id);
        } else {
            log.debug("findById not found id={}", id);
        }
        return res;
    }

    public Claim create(Claim claim) {
        if (claim.getClaimId() == null || claim.getClaimId().isEmpty()) {
            claim.setClaimId(UUID.randomUUID().toString());
        }
        Claim saved = claimsRepository.save(claim);
        log.info("Created claim id={}", saved.getClaimId());
        return saved;
    }

    public Optional<Claim> update(String id, Claim updated) {
        log.info("update called for id={}", id);
        Optional<Claim> result = claimsRepository.findById(id).map(existing -> {
            updated.setClaimId(existing.getClaimId());
            Claim saved = claimsRepository.save(updated);
            log.info("Updated claim id={}", saved.getClaimId());
            return saved;
        });
        if (result.isEmpty()) {
            log.warn("Update failed, claim id={} not found", id);
        }
        return result;
    }

    public boolean delete(String id) {
        log.info("delete called for id={}", id);
        if (claimsRepository.existsById(id)) {
            claimsRepository.deleteById(id);
            log.info("Deleted claim id={}", id);
            return true;
        } else {
            log.warn("Delete failed, claim id={} not found", id);
            return false;
        }
    }
}
