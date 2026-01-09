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
    @Qualifier("finhubClient")
    public RestClient finhubClient(RestClient.Builder builder) {
        return builder.baseUrl("https://some_finhub_endpoint").build();
    }
}
