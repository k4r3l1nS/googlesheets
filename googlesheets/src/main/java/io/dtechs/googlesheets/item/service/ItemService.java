package io.dtechs.googlesheets.item.service;

import io.dtechs.googlesheets.icon.model.Icon;
import io.dtechs.googlesheets.item.dto.ItemDto;
import io.dtechs.googlesheets.item.model.Item;
import io.dtechs.googlesheets.size.model.Size;
import io.dtechs.googlesheets.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Item save(ItemDto itemDto, Size sizeEntity, Set<Icon> icons) {


        var itemEntity = itemDto.toEntity();
        sizeEntity.addItem(itemEntity);

        icons.forEach(icon -> icon.addItem(itemEntity));

        return itemRepository.save(itemEntity);
    }

    public Item findActiveByTypeAndNameAndSize(Item.ClothingType clothingType, String name,
                                                 Size size) {

        return itemRepository.findActiveByTypeAndNameAndSize(clothingType, name, size);
    }

    public List<Item> findActiveItems() {

        return itemRepository.findActiveItems();
    }
}
