package io.dtechs.googlesheets.service;

import io.dtechs.googlesheets.client.SheetClient;
import io.dtechs.googlesheets.icon.service.IconService;
import io.dtechs.googlesheets.item.dto.ItemDto;
import io.dtechs.googlesheets.item.service.ItemService;
import io.dtechs.googlesheets.size.service.SizeService;
import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduledService {

    private final SheetClient sheetClient;

    private final ItemService itemService;
    private final SizeService sizeService;
    private final IconService iconService;

    /**
     * Обновление БД из Google Sheets
     */
    @Scheduled(fixedDelay = 60000)
    public void updateDatabase() {

        var values = sheetClient.get();

        if (values != null) {

            // Список Id элементов одежды, не подлежащих удалению
            List<Long> protectedItemIdList = new ArrayList<>();

            try {
                addNewEntities(values, protectedItemIdList);
                deleteOddEntities(protectedItemIdList);
            } catch (HibernateException ex) {
                ex.printStackTrace();
                // Перезапись таблицы
                protectedItemIdList.clear();
                deleteOddEntities(protectedItemIdList);
                addNewEntities(values, protectedItemIdList);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Добавить новые entity в БД
     *
     * @param values таблица из Google Sheets
     * @param protectedItemIdList Список Id элементов одежды, не подлежащих удалению
     */
    private void addNewEntities(List<List<Object>> values, List<Long> protectedItemIdList) {

        Map<String, Integer> nameMap = new HashMap<>();
        if (!values.isEmpty()) {

            final var titles = values.get(0);
            int index = 0;
            for (var title : titles) {
                nameMap.put(title.toString().toUpperCase(), index);
                ++index;
            }
        }
        values.remove(0);

        for (var row : values) {

            var itemDto = ItemDto.valueFrom(row, nameMap);

            if (itemDto != null && !itemDto.isInvalid()) {

                final var sizeEntity = sizeService.saveIfNotExists(itemDto.getSize());
                final var iconEntity = iconService.saveIfNotExists(itemDto.getAwsKey());

                itemDto.setIcon(iconEntity);

                var itemEntity = itemService.findByTypeAndNameAndSize(itemDto.getClothingType(),
                        itemDto.getName(), sizeEntity);

                if (itemEntity != null) {
                    itemDto.mapTo(itemEntity);
                } else {
                    itemEntity = itemService.save(itemDto, sizeEntity.getId(),
                            iconEntity == null ? null : iconEntity.getId());
                }

                protectedItemIdList.add(itemEntity.getId());
            }
        }

        protectedItemIdList.sort(Comparator.naturalOrder());
    }

    /**
     * Удалить лишние entity из БД
     *
     * @param protectedItemIdList Список Id элементов одежды, не подлежащих удалению
     */
    private void deleteOddEntities(List<Long> protectedItemIdList) {

        final var allItems = itemService.findAll();
        allItems.forEach(item -> {
            if (!protectedItemIdList.contains(item.getId()))
                itemService.delete(item.getId());
        });

        final var allIcons = iconService.findAll();
        allIcons.forEach(iconService::deleteIfNotExists);

        final var allSizes = sizeService.findAll();
        allSizes.forEach(sizeService::deleteIfNotExists);
    }

}

