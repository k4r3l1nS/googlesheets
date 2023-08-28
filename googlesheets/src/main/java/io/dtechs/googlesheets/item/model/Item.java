package io.dtechs.googlesheets.item.model;

import io.dtechs.googlesheets.icon.model.Icon;
import io.dtechs.googlesheets.size.model.Size;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Setter
@Getter
public class Item {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * Тип одежды
     */
    @Enumerated
    private ClothingType clothingType;

    /**
     * Название товара
     */
    private String name;

    /**
     * Артикль
     */
    private String article;

    /**
     * Размер
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "size_id")
    private Size size;

    /**
     * Количество оставшихся товаров
     */
    private Integer numberOfRemaining;

    /**
     * Закупочная цена
     */
    private BigDecimal purchasePrice;

    /**
     * Отпускная цена
     */
    private BigDecimal sellingPrice;

    /**
     * Выгружать в интернет-магазин
     */
    private boolean isPresent = false;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private boolean isActive = true;

    private LocalDateTime deletedAt;

    /**
     * Фотография элемента одежды
     */
    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "icon_id")
    private Set<Icon> icons = new HashSet<>();

    public void deactivate() {

        isActive = false;
        deletedAt = LocalDateTime.now();
    }

    @Getter
    public enum ClothingType {

        BALACLAVA("Балаклава"),
        BANDANA("Бандана"),
        THERMO_MUG("Банка"),
        BOMBER("Бомбер"),
        TROUSERS("Брюки"),
        WINDBREAKER("Ветровка"),
        JEAN_JACKET("Джинсовка"),
        JEANS("Джинсы"),
        JOGGERS("Джоггеры"),
        SUBCHARGE("Доплата"),  //???
        VEST("Жилетка"),
        CAP("Кепка"),
        SET_OF_CLOTHES("Комплект"),
        SUIT("Костюм"),
        JACKET("Куртка"),
        LONG_SLEEVE("Лонгслив"),
        SINGLET("Майка"),
        MANTLE("Мантия"),
        MESSENGER("Мессенджер"),
        SOCKS("Носки"),
        OLYMPIC("Олимпийка"),
        PANAMA("Панама"),
        SHIRT("Рубашка"),
        BACKPACK("Рюкзак"),
        SWEATER("Свитер"),
        SWEATSHIRT("Свитшот"),
        BAG("Сумка"),
        BELT_BAG("Сумка-пояс"),
        SLIPPERS("Тапочки"),
        T_SHIRT("Футболка"),
        HOODIE("Худи"),
        SHORTS("Шорты");

        private final static Map<String, ClothingType> _map;

        static {

            _map = Stream.of(values()).collect(Collectors.toMap(ClothingType::getName, Function.identity()));
        }

        private final String name;

        ClothingType(String name) {
            this.name = name;
        }

        public static ClothingType resolveByName(String name) {

            return _map.getOrDefault(name, null);
        }
    }
}
