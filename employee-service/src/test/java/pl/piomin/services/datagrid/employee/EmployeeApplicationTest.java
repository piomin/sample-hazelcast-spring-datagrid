package pl.piomin.services.datagrid.employee;

import org.springframework.boot.SpringApplication;

public class EmployeeApplicationTest {

    public static void main(String[] args) {
        SpringApplication.from(EmployeeApplication::main)
                .with(MysqlContainerDevMode.class)
                .run(args);
    }
}
