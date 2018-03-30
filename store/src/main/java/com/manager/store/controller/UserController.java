package com.manager.store.controller;

import com.google.common.collect.Lists;
import com.manager.store.entities.User;
import com.manager.store.model.UserModel;
import com.manager.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController extends AbstractCrudController<User, UserModel> {

    protected static final String SECTION_MAPPING = "users";

    @Value("${new.user.password}")
    private String defaultPassword;

    @Autowired
    @Qualifier("userService")
    private UserService service;

    @Override
    protected String getSectionName() {
        return SECTION_MAPPING;
    }

    @Override
    protected String getMainSectionPage() {
        return "users/UsersMain";
    }

    @Override
    protected String getDetailsPage() {
        return "users/UsersDetails";
    }

    @Override
    protected UserModel setUpNewModel() {
        return new UserModel();
    }

    @Override
    protected UserService getService() {
        return service;
    }

    @Override
    protected String getPageTitle() {
        return "Workers";
    }

    @ModelAttribute("userTypes")
    public List<UserModel.UserType> userTypes(){
        return Lists.newArrayList(UserModel.UserType.values());
    }

    @RequestMapping(value = "/resetPassword/{usersId}", method = RequestMethod.PUT)
    @ResponseBody
    public String resetPassword(@PathVariable Long usersId){
        service.setPasswordToUser(usersId, defaultPassword);
        return "Password has been reset successfully.";
    }
}
