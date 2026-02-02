package jotalac.market_viewer.market_viewer_app.util;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    @Qualifier("coingeckoClient")
    public RestClient coinGeckoClient(RestClient.Builder builder) {
        return builder.baseUrl("https://api.coingecko.com/api/v3/").build();
    }

    @Bean
    @Qualifier("twelveDataClient")
    public RestClient twelveDataClient(RestClient.Builder builder) {
        return builder.baseUrl("https://api.twelvedata.com/").build();
    }

    @Bean
    @Qualifier("geminiClient")
    public RestClient geminiClient(RestClient.Builder builder) {
        return builder.baseUrl("https://generativelanguage.googleapis.com").build();
    }
}
