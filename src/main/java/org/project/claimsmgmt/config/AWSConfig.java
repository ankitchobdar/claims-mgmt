package org.project.claimsmgmt.config;

import com.google.gson.Gson;
import jakarta.annotation.PostConstruct;
import org.project.claimsmgmt.model.AWSSecrets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

import javax.sql.DataSource;

@Configuration
public class AWSConfig {

    @Value("${aws.region}")
    private String awsRegion;
    @Value("${database.schema}")
    private String databaseSchema;
    @Value("${aws.database.secret.name}")
    private String awsSecretsName;

    private SecretsManagerClient secretsManagerClient;
    private final Gson gson = new Gson();

    @PostConstruct
    public void init() {
            var builder = SecretsManagerClient.builder().region(Region.of(awsRegion));

            // Use the default provider chain (env, system props, profile, container, instance).
            // This will pick up environment variables (AWS_ACCESS_KEY_ID / AWS_SECRET_ACCESS_KEY) for local dev
            // and instance/task roles or container credentials in production.
            builder.credentialsProvider(DefaultCredentialsProvider.create());

            this.secretsManagerClient = builder.build();
    }

    public AWSSecrets getSecret(String secretName) {
        GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();

        GetSecretValueResponse getSecretValueResponse = secretsManagerClient.getSecretValue(getSecretValueRequest);
        String secretString = getSecretValueResponse.secretString();
        return gson.fromJson(secretString, AWSSecrets.class);
    }

    @Bean
    public DataSource dataSource() {
        AWSSecrets awsSecrets = getSecret(awsSecretsName);
        // Initialize and return your DataSource using the retrieved secrets
        return DataSourceBuilder.create()
                .url("jdbc:mysql://" + awsSecrets.getHost() + ":" + awsSecrets.getPort() + "/" + databaseSchema)
                .username(awsSecrets.getUsername())
                .password(awsSecrets.getPassword())
                .build();
    }
}