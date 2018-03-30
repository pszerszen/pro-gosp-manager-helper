package com.manager.store.controller;

import com.manager.store.entities.AbstractEntity;
import com.manager.store.exceptions.ValidationException;
import com.manager.store.model.AbstractModel;
import com.manager.store.model.ModelSummary;
import com.manager.store.service.DefaultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Set;

/**
 * The abstract Controller containing common operations.
 *
 * @param <E> Entity type
 * @param <M> Model type
 * @author Piotr
 */
@Controller
public abstract class AbstractCrudController<E extends AbstractEntity, M extends AbstractModel> extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCrudController.class);

    private static final String INT_MAX = Integer.MAX_VALUE + "";

    /**
     * @return a new model with default values.
     */
    protected abstract M setUpNewModel();

    /**
     * @return a default service for the controller handling specified entity
     * and Model types.
     */
    protected abstract DefaultService<E, M> getService();

    @RequestMapping(value = VALIDATION_MAPPING, method = RequestMethod.POST, produces = JSON_TYPE)
    @ResponseBody
    public Set<String> validate(@ModelAttribute M model) {
        LOGGER.debug("Requested for a validation of model: {}", model);
        return getService().validate(model);
    }

    @RequestMapping(value = CREATE_NEW_MAPPING, method = RequestMethod.GET)
    public ModelAndView createNew() {
        ModelAndView modelAndView = new ModelAndView(getDetailsPage(), MODEL_ATTRIBUTE_NAME, setUpNewModel());
        setPageMode(modelAndView, PageMode.create);
        return modelAndView;
    }

    @RequestMapping(value = EDIT_MAPPING + "/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable Long id) {
        M model = getService().get(id);
        if (model == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Entity with given ID doesn't exists.");
        }
        ModelAndView modelAndView = new ModelAndView(getDetailsPage(), MODEL_ATTRIBUTE_NAME, model);
        setPageMode(modelAndView, PageMode.edit);
        return modelAndView;
    }

    @RequestMapping(value = SAVE_MAPPING, method = RequestMethod.POST)
    @ResponseBody
    public Boolean save(@ModelAttribute M model) {
        try {
            getService().create(model);
            return true;
        } catch (ValidationException e) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Posted model did not passed validation.");
        }
    }

    @RequestMapping(value = UPDATE_MAPPING, method = RequestMethod.POST)
    @ResponseBody
    public Boolean update(@ModelAttribute M model) {
        try {
            getService().update(model);
            return true;
        } catch (ValidationException e) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Posted model did not passed validation.");
        }
    }

    @RequestMapping(value = DELETE_MAPPING, method = RequestMethod.DELETE)
    @ResponseBody
    public Boolean delete(@RequestParam("ids") List<Long> ids){
        return getService().delete(ids);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView getMainPage() {
        return new ModelAndView(getMainSectionPage(), "list", getService().search("", Integer.MAX_VALUE));
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    @ResponseBody
    public List<M> search(@RequestParam String term, @RequestParam(defaultValue = INT_MAX, required = false) int limit) {
        return getService().search(term, limit);
    }

    @RequestMapping(value = "/json/autocomplete", method = RequestMethod.GET)
    @ResponseBody
    public List<ModelSummary> autocomplete(@RequestParam String query, @RequestParam(required = false, defaultValue = "10") Integer max) {
        return getService().searchSummaries(query, max);
    }

    protected void setPageMode(ModelAndView modelAndView, PageMode pageMode){
        modelAndView.addObject("pageMode", pageMode);
    }
}
