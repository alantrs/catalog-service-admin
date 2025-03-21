package org.admin.service.catalog.domain.category;

import org.admin.service.catalog.domain.validation.Error;
import org.admin.service.catalog.domain.validation.ValidationHandler;
import org.admin.service.catalog.domain.validation.Validator;

public class CategoryValidator extends Validator {

    private final Category category;

    protected CategoryValidator(final Category category, final ValidationHandler aHandler) {
        super(aHandler);
        this.category = category;
    }

    @Override
    public void validate() {
        if (this.category.getName() == null || this.category.getName().isEmpty()) {
            this.validationHandler().append(new Error("'name' should not be null"));
        }
    }
}
