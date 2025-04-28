package CloudBalance_Backend.Project.Confi;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class JacksonConfig {

    private static final String DATE_FORMAT = "dd-MM-yyyy";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        // Configure JavaTimeModule with custom serializers and deserializers
        JavaTimeModule javaTimeModule = new JavaTimeModule();

        // Add custom deserializer for LocalDate (incoming JSON)
        javaTimeModule.addDeserializer(
                LocalDate.class,
                new LocalDateDeserializer(DATE_FORMATTER));

        // Add custom serializer for LocalDate (outgoing JSON)
        javaTimeModule.addSerializer(
                LocalDate.class,
                new LocalDateSerializer(DATE_FORMATTER));

        // Register the module
        objectMapper.registerModule(javaTimeModule);

        // Disable writing dates as timestamps
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return objectMapper;
    }
}
