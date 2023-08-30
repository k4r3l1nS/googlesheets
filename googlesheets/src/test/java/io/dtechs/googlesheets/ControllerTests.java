package io.dtechs.googlesheets;

import io.dtechs.googlesheets.controllers.ItemController;
import io.dtechs.googlesheets.view.repository.ItemViewRepository;
import io.dtechs.googlesheets.view.service.ItemViewService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@WebFluxTest(ItemController.class)
public class ControllerTests {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean(answer = Answers.RETURNS_DEFAULTS, extraInterfaces = ItemViewRepository.class)
    private ItemViewService itemViewService;

    @Test
    public void testItemController() {

        webTestClient.get()
                .uri("/get/items")
                .exchange()
                .expectStatus()
                .isOk();
    }
}
