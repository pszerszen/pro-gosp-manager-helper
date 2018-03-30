package com.manager.store.validators;

import java.util.Set;

/**
 * An interface for validators.
 *
 * @author Piotr
 */
@FunctionalInterface
public interface Validator {

    /**
     * Performs a validation.
     *
     * @return set with validation messages if any validation didn't pass.
     */
    Set<String> validate();
}
