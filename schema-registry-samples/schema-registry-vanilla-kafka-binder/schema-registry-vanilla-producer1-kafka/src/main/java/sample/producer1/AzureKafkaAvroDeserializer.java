package sample.producer1;

import com.azure.core.credential.TokenCredential;
import com.azure.core.util.serializer.TypeReference;
import com.azure.data.schemaregistry.SchemaRegistryClientBuilder;
import com.azure.data.schemaregistry.apacheavro.SchemaRegistryApacheAvroSerializer;
import com.azure.data.schemaregistry.apacheavro.SchemaRegistryApacheAvroSerializerBuilder;
import com.azure.identity.DefaultAzureCredentialBuilder;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.ByteArrayInputStream;
import java.util.Map;

public class AzureKafkaAvroDeserializer implements Deserializer<Object> {

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
    public Object deserialize(String s, byte[] bytes) {
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        return serializer.deserialize(in, TypeReference.createInstance(Object.class));
    }

    @Override
    public void close() {

    }
}
