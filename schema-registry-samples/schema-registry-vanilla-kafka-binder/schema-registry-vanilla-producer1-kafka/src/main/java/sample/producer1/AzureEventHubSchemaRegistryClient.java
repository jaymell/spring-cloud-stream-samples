package sample.producer1;

import com.azure.core.credential.TokenCredential;
import com.azure.data.schemaregistry.SchemaRegistryAsyncClient;
import com.azure.data.schemaregistry.SchemaRegistryClientBuilder;
import com.azure.data.schemaregistry.models.SchemaFormat;
import com.azure.identity.DefaultAzureCredentialBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.schema.registry.SchemaReference;
import org.springframework.cloud.schema.registry.SchemaRegistrationResponse;
import org.springframework.cloud.schema.registry.client.SchemaRegistryClient;
import org.springframework.stereotype.Component;

@Component
public class AzureEventHubSchemaRegistryClient implements SchemaRegistryClient {

    private final String namespace;
    private final String schemaGroup;
    private final SchemaRegistryAsyncClient schemaRegistryAsyncClient;

    @Autowired
    public AzureEventHubSchemaRegistryClient(@Value("${spring.cloud.azure.namespace}") String namespace,
                                             @Value("${spring.cloud.azure.schema-group}") String schemaGroup
    ) {

        this.namespace = namespace;
        this.schemaGroup = schemaGroup;

        TokenCredential tokenCredential = new DefaultAzureCredentialBuilder().build();

        this.schemaRegistryAsyncClient = new SchemaRegistryClientBuilder()
                .credential(tokenCredential)
                .fullyQualifiedNamespace(namespace)
                .buildAsyncClient();
    }

    @Override
    public SchemaRegistrationResponse register(String subject, String format, String schema) {
        System.out.printf("register was called with subject %s, format %s, schema %s!!!%n", subject, format, schema);
        var resp = schemaRegistryAsyncClient.registerSchema(schemaGroup, subject, schema, SchemaFormat.AVRO).block();
        System.out.printf("Got response from azure id %s format %s %n", resp.getId(), resp.getFormat());
        var schemaRegistrationResponse = new SchemaRegistrationResponse();
        System.out.println("WARN -- Azure ID incompatible w/ integer format -- setting default value");
        System.out.println("WARN -- Azure does not return schema version when schema is registered -- setting default value");
        var schemaReference = new SchemaReference(subject, 1, "avro");
        schemaRegistrationResponse.setId(1);
        schemaRegistrationResponse.setSchemaReference(schemaReference);
        return schemaRegistrationResponse;
    }

    @Override
    public String fetch(SchemaReference schemaReference) {
        System.out.printf("fetch was called with schemaReference %s %n", schemaReference);
        return null;
    }

    @Override
    public String fetch(int id) {
        System.out.printf("fetch was called with id %s %n", id);
        return null;
    }
}