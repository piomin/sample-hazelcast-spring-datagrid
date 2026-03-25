package pl.piomin.services.datagrid.employee.data;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.piomin.services.datagrid.employee.model.Employee;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.logging.Logger;


@SpringBootTest
@Testcontainers
@TestExecutionListeners(listeners = {
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        ServletTestExecutionListener.class
})
public class AddEmployeeRepositoryTest {

    protected Logger logger = Logger.getLogger(AddEmployeeRepositoryTest.class.getName());

    @Autowired
    EmployeeRepository repository;

    @Container
    @ServiceConnection
    private static final MySQLContainer<?> MYSQL = new MySQLContainer<>("mysql:8.0")
            .withUsername("datagrid")
            .withPassword("datagrid");

    @Test
    void add() {
        for (int i = 0; i < 1000; i++) {
            int ix = new Random().nextInt(100);
            Employee e = new Employee();
            e.setPersonId(i);
            e.setCompany("TEST" + new DecimalFormat("000").format(ix));
            e = repository.save(e);
            logger.info("Add: " + e);
        }
    }

}
