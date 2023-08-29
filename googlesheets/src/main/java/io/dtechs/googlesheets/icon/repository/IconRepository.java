package io.dtechs.googlesheets.icon.repository;

import io.dtechs.googlesheets.icon.model.Icon;
import io.dtechs.googlesheets.item.model.Item;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.net.URL;
import java.util.List;

@Repository
public interface IconRepository extends CrudRepository<Icon, Long> {

    boolean existsByStorageUrl(URL storageUrl);

    Icon findByStorageUrl(URL storageUrl);

    /**
     * Найти список неглавных фотографий элемента одежды
     *
     * @param item элемент одежды
     * @return Список неглавных фотографий элемента одежды
     */
    @Query("""
            SELECT I
            FROM Icon I
            WHERE :item member of I.items
              AND I.isMain = false
           """)
    List<Icon> findNonMainByItemId(Item item);
}
