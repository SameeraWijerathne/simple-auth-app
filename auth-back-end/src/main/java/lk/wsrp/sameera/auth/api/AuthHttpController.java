package lk.wsrp.sameera.auth.api;

import lk.wsrp.sameera.auth.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@RestControllerAdvice
@RequestMapping("/auth")
public class AuthHttpController {
    @Autowired
    private Connection connection;

    @PostMapping(value = "/signup", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody UserDTO userDTO) throws SQLException {
        PreparedStatement stm = connection.prepareStatement("INSERT INTO user(username, password, full_name) VALUES (?,?,?)");
        stm.setString(1, userDTO.getUsername());
        stm.setString(2, userDTO.getPassword());
        stm.setString(3, userDTO.getFullName());
        stm.executeUpdate();
    }
}
