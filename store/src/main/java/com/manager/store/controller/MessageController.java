package com.manager.store.controller;

import com.manager.store.model.ContactModel;
import com.manager.store.model.MessageModel;
import com.manager.store.service.CredentialsService;
import com.manager.store.service.MailService;
import com.manager.store.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping(value = MessageController.SECTION_NAME)
public class MessageController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

    public static final String SECTION_NAME = "messages";

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Autowired
    private CredentialsService credentialsService;

    @Override
    protected String getSectionName() {
        return SECTION_NAME;
    }

    @Override
    protected String getMainSectionPage() {
        return "messages/MessagesMain";
    }

    @Override
    protected String getDetailsPage() {
        return null;
    }

    @Override
    protected String getPageTitle() {
        return "Write Mail";
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView newMessagePage() {
        return new ModelAndView(getMainSectionPage(), "model", new MessageModel());
    }

    @RequestMapping(value = "mail/autocomplete", method = RequestMethod.GET)
    @ResponseBody
    public List<ContactModel> autocomplete(@RequestParam String query) {
        return userService.getEmails(query);
    }

    @RequestMapping(value = "send", method = RequestMethod.POST)
    @ResponseBody
    public String sendMessage(@ModelAttribute MessageModel model, HttpServletResponse response) {
        try {
            mailService.sendMessage(model.getMessageContent(), credentialsService.getCurrentUsername(), model.getToMails(), model.getCcMails());
            return "Message send successfully.";
        } catch (Exception e) {
            LOGGER.error("Unsuccessfull e-mail send.", e);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return e.getMessage();
        }
    }
}
