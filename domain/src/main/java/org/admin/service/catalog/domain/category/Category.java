package org.admin.service.catalog.domain.category;

import org.admin.service.catalog.domain.AggregateRoot;
import org.admin.service.catalog.domain.validation.ValidationHandler;

import java.time.Instant;

public class Category extends AggregateRoot<CategoryID> {

    private String name;
    private String description;
    private Boolean isActive;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    private Category(
            final CategoryID anId,
                    final String aName,
                    final String aDescription,
                    final Boolean isActive,
                    final Instant aCreationDate,
                    final Instant aUpdateDate,
                    final Instant aDeleteDate)
    {
        super(anId);
        this.name = aName;
        this.description = aDescription;
        this.isActive = isActive;
        this.createdAt = aCreationDate;
        this.updatedAt = aUpdateDate;
        this.deletedAt = aDeleteDate;
    }

    public static Category newCategory(final String aName,
                                       final String aDescription,
                                       final Boolean isActive) {
        final var id = CategoryID.unique();
        final var now = Instant.now();
        final var deletedAt = isActive ? null : now;
        return new Category(id, aName, aDescription, isActive, now, now, deletedAt);
    }

    public Category deactivate(){
        final var now = Instant.now();
        if(this.getDeletedAt() == null){
            this.deletedAt = now;
        }
        this.isActive = false;
        this.updatedAt = now;

        return this;
    }

    public Category activate(){
        final var now = Instant.now();
        this.deletedAt = null;
        this.isActive = true;
        this.updatedAt = now;

        return this;
    }

    public Category update(final String aName, final String aDescription, final boolean isActive){
        if (isActive){
            this.activate();
        }else{
            this.deactivate();
        }
        this.name = aName;
        this.description =aDescription;
        this.updatedAt = Instant.now();
        return this;
    }
    @Override
    public void validate(ValidationHandler handler) {
        new CategoryValidator(this, handler).validate();
    }

    public CategoryID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Boolean isActive() {
        return isActive;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }
}