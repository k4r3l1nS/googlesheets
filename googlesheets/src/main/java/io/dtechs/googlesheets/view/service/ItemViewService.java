package io.dtechs.googlesheets.view.service;

import io.dtechs.googlesheets.icon.model.Icon;
import io.dtechs.googlesheets.icon.repository.IconRepository;
import io.dtechs.googlesheets.item.dto.ItemInfoDto;
import io.dtechs.googlesheets.item.repository.ItemRepository;
import io.dtechs.googlesheets.view.model.ItemView;
import io.dtechs.googlesheets.view.repository.ItemViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemViewService {

    private final ItemViewRepository itemViewRepository;
    private final IconRepository iconRepository;
    private final ItemRepository itemRepository;

    public ItemInfoDto get() {

        ItemInfoDto itemInfoDto = new ItemInfoDto();

        var itemViewIterable = itemViewRepository.findAll();
        itemViewIterable.forEach(itemView ->
                itemInfoDto.getItems().add(ItemInfoDto.ItemInfo.builder()
                        .clothingType(itemView.getClothingType())
                        .name(itemView.getName())
                        .sellingPrice(itemView.getSellingPrice())
                        .mainIconUrl(itemView.getMainIconUrl())
                        .otherIconUrls(
                                iconRepository.findNonMainByItemId
                                        (itemRepository.findById(itemView.getId()).orElseThrow())
                                .stream().map(Icon::getStorageUrl).toList())
                .build()));

        return itemInfoDto;
    }
}
