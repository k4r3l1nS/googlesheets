package io.dtechs.googlesheets.item.repository;

import io.dtechs.googlesheets.item.model.Item;
import io.dtechs.googlesheets.size.model.Size;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {

    @Query("""
            SELECT I
            FROM Item I
            WHERE (I.clothingType = :clothingType OR I.clothingType is NULL AND :clothingType is NULL)
              AND (I.name = :name OR I.name is NULL AND :name is NULL)
              AND (I.size = :size OR I.size is NULL AND :size is NULL)
           """)
    Item findByTypeAndNameAndSize(Item.ClothingType clothingType, String name,
                                          Size size);
}
