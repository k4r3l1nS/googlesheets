package io.dtechs.googlesheets;

import io.dtechs.googlesheets.view.service.ItemViewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ItemViewServiceTests {

    @Autowired
    private ItemViewService itemViewService;

    @Test
    public void testFindingAll() {

        System.out.println(itemViewService.findAll().size());
    }
}
