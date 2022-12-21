package com.hubilon.dj_etri.fileloader.config;

import com.hubilon.dj_etri.rdb.RDBDataConnector;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Setter
@Configuration
@ConfigurationProperties(prefix = "djmc.datasource")
public class RDBConfiguration {

    private String driverClassName;
    private String url;
    private String username;
    private String password;
    private Integer maximumPoolSize;
    private String poolName;

    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(this.driverClassName);
        dataSource.setJdbcUrl(this.url);
        dataSource.setUsername(this.username);
        dataSource.setPassword(this.password);
        dataSource.setMaximumPoolSize(this.maximumPoolSize);
        dataSource.setPoolName(this.poolName);
        return dataSource;
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(@Autowired DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public RDBDataConnector rdbDataConnector(@Autowired NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new RDBDataConnector(namedParameterJdbcTemplate);
    }
}
