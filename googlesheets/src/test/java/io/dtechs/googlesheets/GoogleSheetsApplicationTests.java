package io.dtechs.googlesheets;

import io.dtechs.googlesheets.service.ScheduledService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class GoogleSheetsApplicationTests {

	@MockBean
	private ScheduledService scheduledService;

	@Test
	void contextLoads() {
	}

	@Test
	@Transactional
	void testScheduledTask() {

		scheduledService.updateDatabase();
	}
}
