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
    public void testGettingDto() {

        var dto = itemViewService.get();

        System.out.println("Size = " + dto.getItems().size() + "\n");

        dto.getItems().forEach(itemInfo -> {

            if (itemInfo.getMainIconUrl() != null) {
                System.out.println("Type: " + itemInfo.getClothingType().getName());
                System.out.println("Name: " + itemInfo.getName());
                System.out.println("Selling price: " + itemInfo.getSellingPrice());
                System.out.println("Main photo: " + itemInfo.getMainIconUrl());
                System.out.println("Other photos: " + itemInfo.getOtherIconUrls());

                System.out.println();
            }
        });
    }
}
