package pl.piomin.services.datagrid.person;

import org.springframework.boot.SpringApplication;

public class PersonDevModeApplication {

    public static void main(String[] args) {
        SpringApplication.from(PersonApplication::main)
                .with(MysqlAndHazelcastContainersDevMode.class)
                .run(args);
    }

}
