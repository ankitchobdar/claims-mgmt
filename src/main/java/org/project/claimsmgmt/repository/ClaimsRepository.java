package org.project.claimsmgmt.repository;

import org.project.claimsmgmt.model.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClaimsRepository extends JpaRepository<Claim, String> {
}
