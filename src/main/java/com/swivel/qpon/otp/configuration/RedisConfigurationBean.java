package com.swivel.qpon.otp.configuration;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * RedisConfigurationBean
 */
@Configuration
@Slf4j
public class RedisConfigurationBean {

    private final String url;
    private final int port;
    private final String password;

    public RedisConfigurationBean(@Value("${spring.redis.host}") String url,
                                  @Value("${spring.redis.port}") int port,
                                  @Value("${spring.redis.password}") String password) {
        this.url = url;
        this.port = port;
        this.password = password;
    }

    /**
     * Redis configuration
     *
     * @return redisStandaloneConfiguration
     */
    @Bean
    public RedisStandaloneConfiguration redisStandaloneConfiguration() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(url, port);
        redisStandaloneConfiguration.setPassword(password);
        return redisStandaloneConfiguration;
    }

    /**
     * Redis will retry to connect automatically when redis server is down
     *
     * @return client options
     */
    @Bean
    public ClientOptions clientOptions() {
        return ClientOptions.builder()
                .disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS)
                .autoReconnect(true)
                .build();
    }

    @Bean
    public RedisConnectionFactory connectionFactory(RedisStandaloneConfiguration redisStandaloneConfiguration) {

        LettuceClientConfiguration configuration = LettuceClientConfiguration.builder()
                .clientOptions(clientOptions()).build();

        return new LettuceConnectionFactory(redisStandaloneConfiguration, configuration);
    }

    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    @Primary
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean(destroyMethod = "shutdown")
    ClientResources clientResources() {
        return DefaultClientResources.create();
    }

}
