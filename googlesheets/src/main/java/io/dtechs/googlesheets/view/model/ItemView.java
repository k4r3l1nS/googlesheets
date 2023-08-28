package io.dtechs.googlesheets.view.model;

import io.dtechs.googlesheets.item.model.Item;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Subselect;
import org.springframework.data.annotation.Immutable;

import java.math.BigDecimal;

@Immutable
@Entity
@Setter
@Getter
@Subselect("SELECT * FROM V_ITEM_DATA WHERE isActive = true")
public class ItemView {

    @Id
    private Long id;

    /**
     * Тип одежды
     */
    @Enumerated
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
     * URL фотографии элемента одежды
     */
    private String iconUrl;
}
