package io.dtechs.googlesheets.item.service;

import io.dtechs.googlesheets.icon.repository.IconRepository;
import io.dtechs.googlesheets.item.dto.ItemDto;
import io.dtechs.googlesheets.item.model.Item;
import io.dtechs.googlesheets.size.model.Size;
import io.dtechs.googlesheets.item.repository.ItemRepository;
import io.dtechs.googlesheets.size.repository.SizeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final SizeRepository sizeRepository;
    private final IconRepository iconRepository;

    public Item save(ItemDto itemDto, Long sizeId, Long iconId) {

        var sizeEntity = sizeRepository.findById(sizeId).orElseThrow();

        var itemEntity = itemDto.toEntity();
        sizeEntity.addItem(itemEntity);

        if (iconId != null) {

            var iconEntity = iconRepository.findById(iconId).orElseThrow();
            iconEntity.addItem(itemEntity);
        }

        return itemRepository.save(itemEntity);
    }

    public Item findByTypeAndNameAndSize(Item.ClothingType clothingType, String name,
                                                 Size size) {

        return itemRepository.findByTypeAndNameAndSize(clothingType, name, size);
    }

    public Iterable<Item> findAll() {

        return itemRepository.findAll();
    }

    public Item delete(Long id) {

        var itemEntity = itemRepository.findById(id).orElseThrow();

        itemRepository.delete(itemEntity);

        return itemEntity;
    }
}
