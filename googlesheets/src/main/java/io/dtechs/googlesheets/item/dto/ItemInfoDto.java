package io.dtechs.googlesheets.item.dto;

import io.dtechs.googlesheets.item.model.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ItemInfoDto {

    private List<ItemInfo> items = new ArrayList<>();

    @Getter
    @Setter
    @Builder
    public static class ItemInfo {

        /**
         * Тип одежды
         */
        private Item.ClothingType clothingType;

        /**
         * Название товара
         */
        private String name;

        /**
         * Отпускная цена
         */
        private BigDecimal sellingPrice;

        /**
         * URL главной фотографии элемента одежды
         */
        private URL mainIconUrl;

        /**
         * URL остальных фотографий элемента одежды
         */
        private List<URL> otherIconUrls;
    }
}
