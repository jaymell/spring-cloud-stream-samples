package sample.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.schema.registry.SchemaReference;
import org.springframework.cloud.schema.registry.SchemaRegistrationResponse;
import org.springframework.cloud.schema.registry.client.DefaultSchemaRegistryClient;
import org.springframework.cloud.schema.registry.client.SchemaRegistryClient;
import org.springframework.stereotype.Component;
//
//@Component
//public class RegistryClientFacade implements SchemaRegistryClient {
//
//    private SchemaRegistryClient client;
//
//    @Autowired
//    public RegistryClientFacade(RestTemplateBuilder restTemplateBuilder) {
//        this.client = new DefaultSchemaRegistryClient(restTemplateBuilder);
//    }
//
//    @Override
//    public SchemaRegistrationResponse register(String subject, String format, String schema) {
//        System.out.printf("Got register request subject %s format %s schema %s %n", subject, format, schema);
//        var resp = client.register(subject, format, schema);
//        System.out.printf("Got register response from azure id %s format %s %n", resp.getId(), resp.getSchemaReference());
//        return resp;
//    }
//
//    @Override
//    public String fetch(SchemaReference schemaReference) {
//        System.out.printf("fetch called with %s", schemaReference);
//        return client.fetch(schemaReference);
//    }
//
//    @Override
//    public String fetch(int id) {
//        System.out.printf("fetch called with %s", id);
//        return client.fetch(id);
//    }
//}
