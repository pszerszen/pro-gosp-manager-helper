package com.manager.store.controller;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.manager.store.entities.enums.RoleType;
import com.manager.store.model.PageSection;
import com.manager.store.service.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.StringJoiner;

@Controller
public abstract class AbstractController {

    protected static final String VALIDATION_MAPPING = "validate";

    protected static final String CREATE_NEW_MAPPING = "new";

    protected static final String SAVE_MAPPING = "save";

    protected static final String EDIT_MAPPING = "edit";

    protected static final String UPDATE_MAPPING = "update";

    protected static final String DELETE_MAPPING = "delete";

    protected static final String MODEL_ATTRIBUTE_NAME = "model";

    protected static final String JSON_TYPE = MediaType.APPLICATION_JSON_VALUE;

    @Autowired
    protected CredentialsService credentialsService;

    /**
     * @return name of the section, to be used as the controller's
     * RequestMapping.
     */
    protected abstract String getSectionName();

    /**
     * @return name of jsp resource displaying main sections page.
     */
    protected abstract String getMainSectionPage();

    /**
     * @return name of jsp recource displaying the details page.
     */
    protected abstract String getDetailsPage();

    protected abstract String getPageTitle();

    @ModelAttribute("title")
    public String title() {
        return getPageTitle();
    }

    @ModelAttribute("pageTitles")
    public List<PageSection> getPageTitles() {
        final Builder<PageSection> builder = new ImmutableList.Builder<>();
        for (PageSection page : PageSection.values()) {
            if (credentialsService.hasCurrentUserAnyOfRoles(page.getRoles())) {
                builder.add(page);
            }
        }
        return builder.build();
    }

    @ModelAttribute("isAdmin")
    public boolean isAdmin() {
        return credentialsService.hasCurrentUserRole(RoleType.ROLE_ADMIN);
    }

    @ModelAttribute("isManager")
    public boolean isManager() {
        return credentialsService.hasCurrentUserRole(RoleType.ROLE_MANAGER);
    }

    @ModelAttribute("isUser")
    public boolean isUser() {
        return credentialsService.hasCurrentUserRole(RoleType.ROLE_USER);
    }

    @ModelAttribute("basePath")
    public String basePath(){
        return new StringJoiner("", "/", "/").add(getSectionName()).toString();
    }

}
