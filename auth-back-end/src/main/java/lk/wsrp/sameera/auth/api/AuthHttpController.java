package lk.wsrp.sameera.auth.api;

import lk.wsrp.sameera.auth.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.Stream;

@RestControllerAdvice
@RequestMapping("/auth")
public class AuthHttpController {
    @Autowired
    private Connection connection;

    @PostMapping(value = "/signup", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody @Validated(UserDTO.SignUp.class) UserDTO userDTO) throws SQLException {
        PreparedStatement stm = connection.prepareStatement("INSERT INTO user(username, password, full_name) VALUES (?,?,?)");
        stm.setString(1, userDTO.getUsername());
        stm.setString(2, userDTO.getPassword());
        stm.setString(3, userDTO.getFullName());
        stm.executeUpdate();
    }

    @PostMapping(value = "/login", consumes = "application/json")
    public void login(@RequestBody @Valid UserDTO userDTO, HttpServletRequest request) throws SQLException {
        PreparedStatement stm = connection.prepareStatement("SELECT * FROM user WHERE username=? AND password=?");
        stm.setString(1, userDTO.getUsername());
        stm.setString(2, userDTO.getPassword());
        ResultSet rst = stm.executeQuery();
        if (!rst.next()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Access Denied");
        } else {
            HttpSession session = request.getSession();
            Optional<Cookie> optSessionID = Stream.of(request.getCookies())
                    .filter(cookie -> cookie.getName().equals("JSESSIONID"))
                    .findFirst();

            if (optSessionID.isPresent()) {
                Cookie sessionCookie = optSessionID.get();
                sessionCookie.setDomain(request.getServerName());
                System.out.println(sessionCookie.getDomain());
            }

            session.setAttribute("username", userDTO.getUsername());
            /* This will create a new session for this user
            *  This will also set the set-cookie header with the SESSION ID
            * */
        }
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}
