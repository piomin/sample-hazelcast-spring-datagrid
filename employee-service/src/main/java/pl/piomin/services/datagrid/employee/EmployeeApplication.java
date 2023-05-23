package pl.piomin.services.datagrid.employee;

import com.hazelcast.config.Config;
import com.hazelcast.config.ManagementCenterConfig;
import com.hazelcast.config.SerializerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.piomin.services.datagrid.employee.data.EmployeeSerializer;
import pl.piomin.services.datagrid.employee.model.Employee;

@SpringBootApplication
public class EmployeeApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeApplication.class, args);
    }

    @Bean
    Config config() {
        Config c = new Config();
        c.setInstanceName("cache-1");
        c.setClusterName("dev");
        ManagementCenterConfig mcc = new ManagementCenterConfig()
                .setConsoleEnabled(true);
        c.setManagementCenterConfig(mcc);
        SerializerConfig sc = new SerializerConfig()
                .setTypeClass(Employee.class)
                .setClass(EmployeeSerializer.class);
        c.getSerializationConfig().addSerializerConfig(sc);
        return c;
    }

}
