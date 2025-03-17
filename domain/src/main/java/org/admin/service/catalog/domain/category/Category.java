package org.admin.service.catalog.domain.category;

import java.time.Instant;
import java.util.UUID;

public class Category {

    private String id;
    private String name;
    private String description;
    private Boolean isActive;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    public Category() {
    }

    private Category(String id,
                    String name,
                    String description,
                    Boolean isActive,
                    Instant createdAt,
                    Instant updatedAt,
                    Instant deletedAt)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static Category newCtegory(final String aName,
                                      final String aDescription,
                                      final Boolean isActive) {
        String id = UUID.randomUUID().toString();
        Instant now = Instant.now();
        return new Category(id, aName, aDescription, isActive, now, now, null);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getActive() {
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