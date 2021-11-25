package sample.consumer;

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

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class AzureEventHubSchemaRegistryClient implements SchemaRegistryClient {

    private final String namespace;
    private final String schemaGroup;
    private final SchemaRegistryAsyncClient client;

//    private ConcurrentMap> azureIdTo= new ConcurrentHashMap();

    @Autowired
    public AzureEventHubSchemaRegistryClient(@Value("${spring.cloud.azure.namespace}") String namespace,
                                             @Value("${spring.cloud.azure.schema-group}") String schemaGroup
    ) {

        this.namespace = namespace;
        this.schemaGroup = schemaGroup;

        TokenCredential tokenCredential = new DefaultAzureCredentialBuilder().build();

        this.client = new SchemaRegistryClientBuilder()
                .credential(tokenCredential)
                .fullyQualifiedNamespace(namespace)
                .buildAsyncClient();
    }

    @Override
    public SchemaRegistrationResponse register(String subject, String format, String schema) {
        System.out.printf("register was called with subject %s, format %s, schema %s!!!%n", subject, format, schema);
        var resp = client.registerSchema(schemaGroup, subject, schema, SchemaFormat.AVRO).block();
        var azSchema = client.getSchema(resp.getId()).block();
        System.out.printf("Got response from azure id %s format %s %n", resp.getId(), resp.getFormat());
        System.out.println("WARN -- Azure ID incompatible w/ integer format -- setting default value");
        System.out.println("WARN -- Azure does not return schema version when schema is registered -- setting default value");

        var schemaRegistrationResponse = new SchemaRegistrationResponse();
        var schemaReference = new SchemaReference(subject, 100, "avro");
        schemaRegistrationResponse.setId(1);
        schemaRegistrationResponse.setSchemaReference(schemaReference);
        return schemaRegistrationResponse;
    }

    @Override
    public String fetch(SchemaReference schemaReference) {
        System.out.printf("fetch was called with schemaReference %s %n", schemaReference);
        var resp = client.getSchema("6092fb18a3004b6bad94cb918a9132de").block();
        return resp.getDefinition();
    }

    @Override
    public String fetch(int id) {
        System.out.printf("fetch was called with id %s %n", id);
        var resp = client.getSchema("5011bf7002be47a1983e7574cf89875f").block();
        return resp.getDefinition();
    }
}