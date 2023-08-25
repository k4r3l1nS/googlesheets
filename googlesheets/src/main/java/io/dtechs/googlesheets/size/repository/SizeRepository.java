package io.dtechs.googlesheets.size.repository;

import io.dtechs.googlesheets.size.model.Size;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeRepository extends CrudRepository<Size, Long> {

    @Query("""
            SELECT S
            FROM Size S
            WHERE (S.sizeType = :sizeType OR S.sizeType is NULL AND :sizeType is NULL)
              AND (S.valueEur = :valueEur OR S.valueEur is NULL AND :valueEur is NULL)
              AND (S.valueNum = :valueNum OR S.valueNum is NULL AND :valueNum is NULL)
            """)
    Size findByTypeAndValue(Size.SizeType sizeType, Size.EuropeanSize valueEur, Integer valueNum);
}
