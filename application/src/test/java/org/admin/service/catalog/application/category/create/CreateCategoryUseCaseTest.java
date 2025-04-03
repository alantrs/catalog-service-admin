package org.admin.service.catalog.application.category.create;

import org.admin.service.catalog.domain.category.CategoryGateway;
import org.admin.service.catalog.domain.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateCategoryUseCaseTest {

    @InjectMocks
    private DefaultCreateCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Test
    public void givenAValidCommand_whenCallsCreateCategory_shouldReturnCategoryId() {
        final var expectedName = "Name";
        final var expectedDescription = "Description";
        final var expectedIsActive = true;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        when(categoryGateway.create(any()))
                .thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand);

        assertNotNull(actualOutput);
        assertNotNull(actualOutput.id());

        verify(categoryGateway, times(1))
                .create(argThat(aCategory ->
                Objects.equals(expectedName, aCategory.getName())
                    && Objects.equals(expectedDescription, aCategory.getDescription())
                    && Objects.equals(expectedIsActive, aCategory.isActive())
                    && Objects.nonNull(aCategory.getId())
                    && Objects.nonNull(aCategory.getCreatedAt())
                    && Objects.nonNull(aCategory.getUpdatedAt())
                    && Objects.isNull(aCategory.getDeletedAt())
                ));
    }

    @Test
    public void givenAInvalidName_whenCallsCreateCategory_shouldReturnDomainException() {
        final var expectedDescription = "Description";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var aCommand =
                CreateCategoryCommand.with(null, expectedDescription, expectedIsActive);

        final var actualException = assertThrows(DomainException.class, ()-> useCase.execute(aCommand));

        assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
        verify(categoryGateway, times(0)).create(any());
    }

    @Test
    public void givenAValidCommandInactiveCategory_whenCallsCreateCategory_shouldReturnInactiveCategoryId() {
        final var expectedName = "Name";
        final var expectedDescription = "Description";
        final var expectedIsActive = false;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        when(categoryGateway.create(any()))
                .thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand);

        assertNotNull(actualOutput);
        assertNotNull(actualOutput.id());

        verify(categoryGateway, times(1))
                .create(argThat(aCategory ->
                        Objects.equals(expectedName, aCategory.getName())
                                && Objects.equals(expectedDescription, aCategory.getDescription())
                                && Objects.equals(expectedIsActive, aCategory.isActive())
                                && Objects.nonNull(aCategory.getId())
                                && Objects.nonNull(aCategory.getCreatedAt())
                                && Objects.nonNull(aCategory.getUpdatedAt())
                                && Objects.nonNull(aCategory.getDeletedAt())
                ));
    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsRandomException_shouldReturnAException() {
        final var expectedName = "Name";
        final var expectedDescription = "Description";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "Gateway exception";

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        when(categoryGateway.create(any()))
                .thenThrow(new IllegalStateException(expectedErrorMessage));

        final var actualException =
                assertThrows(IllegalStateException.class, ()-> useCase.execute(aCommand));

        assertEquals(expectedErrorMessage, actualException.getMessage());
        verify(categoryGateway, times(1))
                .create(argThat(aCategory ->
                        Objects.equals(expectedName, aCategory.getName())
                                && Objects.equals(expectedDescription, aCategory.getDescription())
                                && Objects.equals(expectedIsActive, aCategory.isActive())
                                && Objects.nonNull(aCategory.getId())
                                && Objects.nonNull(aCategory.getCreatedAt())
                                && Objects.nonNull(aCategory.getUpdatedAt())
                                && Objects.isNull(aCategory.getDeletedAt())
                ));
    }

}