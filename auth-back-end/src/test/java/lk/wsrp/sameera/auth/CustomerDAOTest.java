package lk.wsrp.sameera.auth;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@SpringJUnitWebConfig(WebRootConfig.class)
public class CustomerDAOTest {
    @Autowired
    private Connection connection;

    @AfterEach
    void tearDown() throws SQLException {
        connection.close();
    }

    @RepeatedTest(50)
    void addCustomers() throws SQLException {
        Faker faker = new Faker();
        PreparedStatement stm = connection.prepareStatement("INSERT INTO customer (address, name) VALUES (?,?)");
        stm.setString(1, faker.address().cityName());
        stm.setString(2, faker.name().fullName());
        stm.executeUpdate();
    }
}
