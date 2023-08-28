package io.dtechs.googlesheets.view.model;

import io.dtechs.googlesheets.item.model.Item;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Subselect;
import org.springframework.data.annotation.Immutable;

import java.math.BigDecimal;
import java.net.URL;

@Immutable
@Entity
@Setter
@Getter
@Subselect("SELECT * FROM V_ITEM_DATA")
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
     * URL главной фотографии элемента одежды
     */
    @Column(name = "main_icon")
    private URL mainIconUrl;
}
