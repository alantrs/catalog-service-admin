package org.admin.service.catalog.infrastructure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ApplicationTest {

    @Test
    public void testApplication(){
        Assertions.assertNotNull(new Application());
        Application.main(new String[]{});
    }
}
