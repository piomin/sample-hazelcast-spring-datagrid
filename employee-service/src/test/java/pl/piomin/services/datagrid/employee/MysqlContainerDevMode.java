package pl.piomin.services.datagrid.employee;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.mysql.MySQLContainer;

@TestConfiguration
public class MysqlContainerDevMode {

    @Bean
    @ServiceConnection
    public MySQLContainer mysql() {
        return new MySQLContainer("mysql:9.7")
                .withConfigurationOverride("mysql-conf")
                .withUsername("datagrid")
                .withPassword("datagrid");
    }

}
