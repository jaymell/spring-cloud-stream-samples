//package sample.producer1;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.web.client.RestTemplateBuilder;
//import org.springframework.cloud.schema.registry.SchemaReference;
//import org.springframework.cloud.schema.registry.SchemaRegistrationResponse;
//import org.springframework.cloud.schema.registry.client.DefaultSchemaRegistryClient;
//import org.springframework.cloud.schema.registry.client.SchemaRegistryClient;
//import org.springframework.stereotype.Component;
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
//        var resp = client.register(subject, format, schema);
//        System.out.printf("Got response from azure id %s format %s %n", resp.getId(), resp.getSchemaReference());
//        return resp;
//    }
//
//    @Override
//    public String fetch(SchemaReference schemaReference) {
//        return client.fetch(schemaReference);
//    }
//
//    @Override
//    public String fetch(int id) {
//        return client.fetch(id);
//    }
//}
