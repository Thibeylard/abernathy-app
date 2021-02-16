package com.mediscreen.abernathyapp.patient.config;

import brave.sampler.Sampler;
import com.mediscreen.abernathyapp.patient.validators.PatientValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
public class ApplicationConfig {

    @Bean
    public PatientValidator beforeCreatePatientValidator() {
        return new PatientValidator();
    }

    @Bean
    public PatientValidator beforeSavePatientValidator() {
        return new PatientValidator();
    }

    @Bean
    public Logger getClientPatientLogger() {
        return LoggerFactory.getLogger("patient-logger");
    }

    // Spring Sleuth tracing configuration
    @Bean
    public Sampler defaultSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }

    @Bean
    @Profile("docker")
    public DataSource mySqlDataSource() {
        DataSourceBuilder<? extends DataSource> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("com.mysql.jdbc.Driver");
        dataSourceBuilder.url("jdbc:mysql://mysqldb:3306/abernathy?serverTimezone=Europe/Paris");
        dataSourceBuilder.username("root");
        dataSourceBuilder.password("mysqlPass");
        return new RetryableDataSourceWrapper(dataSourceBuilder.build());
    }

    @Bean
    @Profile("test")
    public DataSource h2DataSource() {
        DataSourceBuilder<? extends DataSource> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.h2.Driver");
        dataSourceBuilder.url("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1");
        dataSourceBuilder.username("thibaut");
        dataSourceBuilder.password("h2Pass");
        return dataSourceBuilder.build();
    }
}
