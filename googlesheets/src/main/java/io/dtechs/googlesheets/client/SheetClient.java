package io.dtechs.googlesheets.client;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import io.dtechs.googlesheets.config.credentials.CredentialService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class SheetClient {

    @Value("${spreadsheet.id}")
    private String SPREADSHEET_ID;

    @Value("${application.name}")
    private String APPLICATION_NAME;

    @Value("${range.value}")
    private String range;

    private final CredentialService credentialService;

    private static final String SHEET_NAME = "'Склад Одежда'";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    /**
     * Считать данные из Google Sheets
     */
    public List<List<Object>> get() {

        try {
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

            Sheets service =
                    new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credentialService.getCredentials(HTTP_TRANSPORT))
                            .setApplicationName(APPLICATION_NAME)
                            .build();
            ValueRange response = service.spreadsheets().values()
                    .get(SPREADSHEET_ID, SHEET_NAME + "!" + range)
                    .execute();

            return response.getValues();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}