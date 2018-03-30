package com.manager.store.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/admin")
@Secured({"ROLE_ADMIN"})
public class AdminController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    @RequestMapping(value = "/heartbeat", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public void heartbeat(){
        LOGGER.debug("HeartBeat on admin zone.");
    }
}
