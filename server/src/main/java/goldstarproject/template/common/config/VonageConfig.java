package goldstarproject.template.common.config;

import com.vonage.client.VonageClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VonageConfig {

    @Value("${spring.vonage.api.key}")
    private String apiKey;

    @Value("${spring.vonage.api.secret}")
    private String apiSecret;


    @Bean
    public VonageClient vonageClient() {
        return new VonageClient.Builder()
                .apiKey(apiKey)
                .apiSecret(apiSecret)
                .build();
    }
}
