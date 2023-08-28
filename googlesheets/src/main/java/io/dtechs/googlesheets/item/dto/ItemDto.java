package io.dtechs.googlesheets.item.dto;

import io.dtechs.googlesheets.icon.model.Icon;
import io.dtechs.googlesheets.item.model.Item;
import io.dtechs.googlesheets.size.model.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.*;

@Getter
@Setter
@Builder
public class ItemDto {

    /**
     * Тип одежды
     */
    private Item.ClothingType clothingType;

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
    private boolean isPresent;

    /**
     * Фото элемента одежды
     */
    private Set<Icon> icons = new HashSet<>();

    /**
     * Формирует entity элемента одежды по данному DTO
     *
     * @return entity
     */
    public Item toEntity() {

        Item itemEntity = new Item();

        itemEntity.setClothingType(clothingType);
        itemEntity.setName(name);
        itemEntity.setArticle(article);

        itemEntity.setNumberOfRemaining(numberOfRemaining);
        itemEntity.setPurchasePrice(purchasePrice);
        itemEntity.setSellingPrice(sellingPrice);

        return itemEntity;
    }

    /**
     * Формирует DTO по строке элементов из Google Sheets
     *
     * @param values - строка
     * @return DTO
     */
    public static ItemDto valueFrom(List<Object> values, Map<String, Integer> nameMap) {

        int maxIndex = nameMap.values().stream().max(Comparator.naturalOrder()).orElseThrow();
        while(values.size() <= maxIndex)
            values.add(null);

        try {
            String clothingType = getFromNameMap(nameMap, values, "Тип");
            String name = getFromNameMap(nameMap, values, "Название");
            String article = getFromNameMap(nameMap, values, "Артикль");
            String size = getFromNameMap(nameMap, values, "Размер");
            String numberOfRemaining = getFromNameMap(nameMap, values, "Количество");
            String purchasePrice = getFromNameMap(nameMap, values, "Закупочная цена");
            String sellingPrice = getFromNameMap(nameMap, values, "Отпускная цена");
            String isPresent = getFromNameMap(nameMap, values, "Выгружать в интернет-магазин");

            return ItemDto.builder()
                    .clothingType(Item.ClothingType.resolveByName(clothingType))
                    .name(name)
                    .article(article)
                    .size(Size.getSize(size))
                    .numberOfRemaining(numberOfRemaining == null || numberOfRemaining.isEmpty() ? null :
                            Integer.valueOf(numberOfRemaining))
                    .purchasePrice(purchasePrice == null || purchasePrice.isEmpty() ? null :
                            BigDecimal.valueOf(Double.parseDouble(purchasePrice)))
                    .sellingPrice(sellingPrice == null || sellingPrice.isEmpty() ? null :
                            BigDecimal.valueOf(Double.parseDouble(sellingPrice)))
                    .isPresent(isPresent != null && isPresent.equalsIgnoreCase("ДА"))
                    .icons(new HashSet<>())
                    .build();
        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return null;
    }

    /**
     * Обновляет поля передаваемой entity
     *
     * @param itemEntity - entity
     */
    public void mapTo(Item itemEntity) {

        itemEntity.setArticle(article);
        itemEntity.setNumberOfRemaining(numberOfRemaining);
        itemEntity.setSellingPrice(sellingPrice);
        itemEntity.setPurchasePrice(purchasePrice);
        itemEntity.setPresent(isPresent);

        itemEntity.getIcons().forEach(icon -> {
            if (!icons.contains(icon)) {
                icon.deleteItem(itemEntity);
            }
        });

        icons.forEach(icon -> {
            if (!itemEntity.getIcons().contains(icon) && icon != null) {
                icon.addItem(itemEntity);
            }
        });
    }

    public boolean isInvalid() {

        return clothingType == null || name == null || name.isEmpty() || size == null;
    }

    private static String getFromNameMap(Map<String, Integer> nameMap, List<Object> values, String key) {

        key = key.toUpperCase();

        var value = !nameMap.containsKey(key) ||
                values.get(nameMap.get(key)) == null ? null :
                values.get(nameMap.get(key)).toString();
        return value == null || value.isEmpty() ? null : value;
    }

    public String getAwsFolder() {

        return clothingType.getName() + "/" + name;
    }
}
