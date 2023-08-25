package io.dtechs.googlesheets.icon.repository;

import io.dtechs.googlesheets.icon.model.Icon;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.net.URL;

@Repository
public interface IconRepository extends CrudRepository<Icon, Long> {

    boolean existsByStorageUrl(URL storageUrl);

    Icon findByStorageUrl(URL storageUrl);
}
