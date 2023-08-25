package io.dtechs.googlesheets;

import io.dtechs.googlesheets.client.SheetClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.security.GeneralSecurityException;

@SpringBootTest
public class SheetClientTests {

    @Autowired
    private SheetClient sheetClient;

    @Test
    void testSheetClient() throws GeneralSecurityException, IOException {

        sheetClient.get();
    }
}
