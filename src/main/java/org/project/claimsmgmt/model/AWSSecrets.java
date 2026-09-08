package org.project.claimsmgmt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AWSSecrets {
    private String username;
    private String password;
    private String host;
    private String port;
    private String engine;
}
