package pl.mlata.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.mlata.dto.RegistrationDTO;
import pl.mlata.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by Mateusz on 05.04.2017.
 */
@RestController
public class AccountController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/auth/registration", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public /*ResponseEntity<String>*/void registerNewAccount(@RequestBody @Valid RegistrationDTO registrationData, HttpServletRequest servletRequest) {
        userService.registerNewAccount(registrationData);
    }
}
