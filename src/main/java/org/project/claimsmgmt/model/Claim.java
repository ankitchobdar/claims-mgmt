package org.project.claimsmgmt.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@Entity
@Table(name = "claims")
public class Claim {
    @Id
    @Column(name = "claim_id", nullable = false)
    private String claimId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "claim_paid_date")
    private LocalDate claimPaidDate;

    @Column(name = "claim_status")
    private String claimStatus;

    @Column(name = "claim_amount")
    private BigDecimal claimAmount;
}
