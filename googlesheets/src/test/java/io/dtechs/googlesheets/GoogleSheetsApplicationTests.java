package io.dtechs.googlesheets;

import io.dtechs.googlesheets.aws.service.AwsService;
import io.dtechs.googlesheets.service.ScheduledService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class GoogleSheetsApplicationTests {

	@MockBean
	private ScheduledService scheduledService;

	@MockBean
	private AwsService awsService;

	@Test
	void contextLoads() {
	}

	@Test
	void testScheduledTask() {

		scheduledService.updateDatabase();
	}

	@Test
	void listIcons() {

		awsService.listIconKeys("Худи/Carhartt gray");
	}
}
