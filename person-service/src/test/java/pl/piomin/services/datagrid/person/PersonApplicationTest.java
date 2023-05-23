package pl.piomin.services.datagrid.person;

import org.springframework.boot.SpringApplication;

public class PersonApplicationTest {

    public static void main(String[] args) {
        SpringApplication.from(PersonApplication::main)
                .with(MysqlAndHazelcastContainersDevMode.class)
                .run(args);
    }

}
