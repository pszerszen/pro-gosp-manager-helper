package com.manager.store.controller;

import com.manager.store.entities.enums.RoleType;
import com.manager.store.model.PasswordChangeToken;
import com.manager.store.service.CredentialsService;
import com.manager.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;

/**
 * @author Piotr
 */
@Controller
public class SecurityController extends AbstractController {

    private static final String LOGIN_URL = "/login";

    private static final String TRANSACTIONS_URL = "/transactions";

    private static final String CHANGE_PASSWORD_URL = "/changePassword";

    private static final String MESSAGE_PARAM = "message";

    private static final String ERROR_PARAM = "error";

    private static final String ERROR_MESSAGE = "Invalid Credentials provided.";

    private static final String LOGOUT_MESSAGE = "Logged out from Store Manager successfully.";

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Autowired
    @Qualifier("credentialsService")
    private CredentialsService credentialsService;

    @RequestMapping(value = CHANGE_PASSWORD_URL, method = RequestMethod.GET)
    public ModelAndView changePasswordPage() {

        ModelAndView model = new ModelAndView("changePassword");
        model.addObject("title", "Change Password");
        model.addObject("passwordToken", new PasswordChangeToken());

        return model;
    }

    @RequestMapping(value = CHANGE_PASSWORD_URL, method = RequestMethod.POST)
    @ResponseBody
    public String changePassword(@ModelAttribute @Valid PasswordChangeToken passwordChangeToken, HttpServletResponse response) {
        try {
            credentialsService.updatePasswordToCurrentUser(passwordChangeToken);
            response.setStatus(HttpStatus.OK.value());
            return "Password has  been changed successfully. You will be automatically logged out now.";
        } catch (SecurityException se) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return se.getMessage();
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return "Internal Server Error";
        }

    }

    @RequestMapping(value = LOGIN_URL, method = RequestMethod.GET)
    public ModelAndView login(
            @RequestParam(value = ERROR_PARAM, required = false) String error,
            @RequestParam(value = "logout", required = false) String logout) {

        ModelAndView model = new ModelAndView();
        model.addObject("title", "Login");
        if (error != null) {
            model.addObject(ERROR_PARAM, ERROR_MESSAGE);
        }

        if (logout != null) {
            model.addObject(MESSAGE_PARAM, LOGOUT_MESSAGE);
        }

        model.setViewName("login");
        return model;
    }

    @RequestMapping(value = "/login_processor", method = RequestMethod.GET)
    public RedirectView loggingProcessor(Principal principal) {
        if (credentialsService.hasCurrentUserRole(RoleType.ROLE_ADMIN) && credentialsService.isUserTheOriginAdmin(principal.getName())) {
            return new RedirectView(CHANGE_PASSWORD_URL);
        }
        return new RedirectView(TRANSACTIONS_URL);
    }

    @RequestMapping(value = "/setPasswordToUser/{userId}/{password}", method = RequestMethod.PUT)
    public ModelAndView setPasswordToUser(
            @PathVariable("userId") Long userId,
            @PathVariable("password") String password) {
        userService.setPasswordToUser(userId, password);
        return new ModelAndView("login");
    }

    @Override
    protected String getSectionName() {
        return "changePassword";
    }

    @Override
    protected String getMainSectionPage() {
        return "changePassword";
    }

    @Override
    protected String getDetailsPage() {
        return null;
    }

    @Override
    protected String getPageTitle() {
        return "Change Password";
    }
}
