package org.admin.service.catalog.application.category.create;

import org.admin.service.catalog.domain.category.Category;
import org.admin.service.catalog.domain.category.CategoryID;

public record CreateCategoryOutput(CategoryID id) {

    public static CreateCategoryOutput from(final Category aCategory) {
        return new CreateCategoryOutput(aCategory.getId());
    }
}
