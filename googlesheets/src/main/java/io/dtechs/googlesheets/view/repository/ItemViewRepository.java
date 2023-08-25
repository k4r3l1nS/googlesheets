package io.dtechs.googlesheets.view.repository;

import io.dtechs.googlesheets.view.model.ItemView;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemViewRepository extends CrudRepository<ItemView, Long> {
}
