package org.admin.service.catalog.domain;

import org.admin.service.catalog.domain.category.Category;
import org.admin.service.catalog.domain.exceptions.DomainException;
import org.admin.service.catalog.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryTest {

    @Test
    public void givenAValidParams_whenCallNewCategory_thenInstantiateACategory() {
        final String expectedName = "name";
        final String expectedDescription = "description";
        final Boolean expectedActive = true;

        final var actualCategory =
                Category.newCategory(expectedName, expectedDescription, expectedActive);

        assertNotNull(actualCategory);
        assertNotNull(actualCategory.getId());
        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
        assertEquals(expectedActive, actualCategory.isActive());
        assertNotNull(actualCategory.getCreatedAt());
        assertNotNull(actualCategory.getUpdatedAt());
        assertNull(actualCategory.getDeletedAt());

    }

    @Test
    public void givenAnInvalidNullName_whenCallNewCategoryAndValidate_thenShouldThrowException() {
        final String expectedName = null;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be null";
        final String expectedDescription = "description";
        final Boolean expectedActive = true;

        final var actualCategory =
                Category.newCategory(expectedName, expectedDescription, expectedActive);

        final var actualException = assertThrows(DomainException.class, ()-> actualCategory.validate(new ThrowsValidationHandler()));

        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenAnInvalidEmptyName_whenCallNewCategoryAndValidate_thenShouldThrowException() {
        final var expectedName = " ";
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be empty";
        final String expectedDescription = "description";
        final Boolean expectedActive = true;

        final var actualCategory =
                Category.newCategory(expectedName, expectedDescription, expectedActive);

        final var actualException = assertThrows(DomainException.class, ()-> actualCategory.validate(new ThrowsValidationHandler()));

        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenAnInvalidNameLengthLessThan3_whenCallNewCategoryAndValidate_thenShouldThrowException() {
        final var expectedName = "Al ";
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters";
        final String expectedDescription = "description";
        final Boolean expectedActive = true;

        final var actualCategory =
                Category.newCategory(expectedName, expectedDescription, expectedActive);

        final var actualException = assertThrows(DomainException.class, ()-> actualCategory.validate(new ThrowsValidationHandler()));

        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenAnInvalidNameLengthMoreThan255_whenCallNewCategoryAndValidate_thenShouldThrowException() {
        final var expectedName = """
               destes problemas, uma vez que a competitividade nas transações comerciais não pode mais se dissociar de
               alternativas às soluções ortodoxas. Não obstante, a estrutura atual da organização promove a alavancagem
               dos métodos utilizados na avaliação de resultados. Continuando 
               """;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters";
        final String expectedDescription = "description";
        final Boolean expectedActive = true;

        final var actualCategory =
                Category.newCategory(expectedName, expectedDescription, expectedActive);

        final var actualException = assertThrows(DomainException.class, ()-> actualCategory.validate(new ThrowsValidationHandler()));

        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenAValidEmptyDescription_whenCallNewCategoryAndValidate_thenShouldReceiveOK() {
        final String expectedName = "name";
        final String expectedDescription = " ";
        final Boolean expectedActive = true;

        final var actualCategory =
                Category.newCategory(expectedName, expectedDescription, expectedActive);

        assertDoesNotThrow( () -> actualCategory.validate(new ThrowsValidationHandler()));

        assertNotNull(actualCategory);
        assertNotNull(actualCategory.getId());
        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
        assertEquals(expectedActive, actualCategory.isActive());
        assertNotNull(actualCategory.getCreatedAt());
        assertNotNull(actualCategory.getUpdatedAt());
        assertNull(actualCategory.getDeletedAt());

    }

    @Test
    public void givenAValidFalseIsActive_whenCallNewCategoryAndValidate_thenShouldReceiveOK() {
        final String expectedName = "name";
        final String expectedDescription = " ";
        final Boolean expectedActive = false;

        final var actualCategory =
                Category.newCategory(expectedName, expectedDescription, expectedActive);

        assertDoesNotThrow( () -> actualCategory.validate(new ThrowsValidationHandler()));

        assertNotNull(actualCategory);
        assertNotNull(actualCategory.getId());
        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
        assertEquals(expectedActive, actualCategory.isActive());
        assertNotNull(actualCategory.getCreatedAt());
        assertNotNull(actualCategory.getUpdatedAt());
        assertNotNull(actualCategory.getDeletedAt());

    }

    @Test
    public void givenAValidActiveCategory_whenCallDeactivate_thenReturnCategoryInactivated(){
        final String expectedName = "name";
        final String expectedDescription = " ";
        final Boolean expectedActive = false;

        final var aCategory =
                Category.newCategory(expectedName, expectedDescription, true);

        assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        final var createdAt = aCategory.getCreatedAt();
        final var updatedAt = aCategory.getUpdatedAt();

        assertTrue(aCategory.isActive());
        assertNull(aCategory.getDeletedAt());

        final var actualCategory = aCategory.deactivate();

        assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        assertEquals(aCategory.getId(), actualCategory.getId());
        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
        assertEquals(expectedActive, actualCategory.isActive());
        assertEquals(actualCategory.getCreatedAt(), createdAt );
        assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        assertNotNull(actualCategory.getDeletedAt());

    }

    @Test
    public void givenAValidInactiveCategory_whenCallActivate_thenReturnCategoryActivated() {
        final String expectedName = "name";
        final String expectedDescription = " ";
        final Boolean expectedActive = true;

        final var aCategory =
                Category.newCategory(expectedName, expectedDescription, false);

        assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        final var createdAt = aCategory.getCreatedAt();
        final var updatedAt = aCategory.getUpdatedAt();

        assertFalse(aCategory.isActive());
        assertNotNull(aCategory.getDeletedAt());

        final var actualCategory = aCategory.activate();

        assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        assertEquals(aCategory.getId(), actualCategory.getId());
        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
        assertEquals(expectedActive, actualCategory.isActive());
        assertEquals(actualCategory.getCreatedAt(), createdAt );
        assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        assertNull(actualCategory.getDeletedAt());
    }
}
