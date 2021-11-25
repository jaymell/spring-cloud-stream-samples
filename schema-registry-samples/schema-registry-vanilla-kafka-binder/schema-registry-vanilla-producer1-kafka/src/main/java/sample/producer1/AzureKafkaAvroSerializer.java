package sample.producer1;

import com.azure.core.credential.TokenCredential;
import com.azure.data.schemaregistry.SchemaRegistryClientBuilder;
import com.azure.data.schemaregistry.apacheavro.SchemaRegistryApacheAvroSerializer;
import com.azure.data.schemaregistry.apacheavro.SchemaRegistryApacheAvroSerializerBuilder;
import com.azure.identity.DefaultAzureCredentialBuilder;
import org.apache.kafka.common.serialization.Serializer;

import java.io.ByteArrayOutputStream;
import java.util.Map;

public class AzureKafkaAvroSerializer implements Serializer<Object> {

    private SchemaRegistryApacheAvroSerializer serializer;

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        System.out.printf("Got configs %s %n", configs);
        TokenCredential tokenCredential = new DefaultAzureCredentialBuilder().build();

        // instantiate client
        this.serializer = new SchemaRegistryApacheAvroSerializerBuilder()
                .schemaRegistryAsyncClient(
                        new SchemaRegistryClientBuilder()
                                .credential(tokenCredential)
                                //FIXME
                                .fullyQualifiedNamespace("james-event-hub-poc.servicebus.windows.net")
                                .buildAsyncClient()
                )
                //FIXME
                .schemaGroup("kafka-sr-poc")
                //FIXME
                .autoRegisterSchema(true)
                .buildSerializer();
    }

    @Override
    public byte[] serialize(String s, Object o) {
        if (o == null) {
            return null;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        serializer.serialize(out, o);
        return out.toByteArray();
    }

    @Override
    public void close() {

    }
}
