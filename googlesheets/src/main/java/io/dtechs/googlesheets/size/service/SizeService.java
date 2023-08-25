package io.dtechs.googlesheets.size.service;

import io.dtechs.googlesheets.item.dto.ItemDto;
import io.dtechs.googlesheets.size.model.Size;
import io.dtechs.googlesheets.size.repository.SizeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SizeService {

    private final SizeRepository sizeRepository;

    public Size saveIfNotExists(Size size) {

        var sizeEntity = findByTypeAndValue(size);

        return sizeEntity == null ? sizeRepository.save(size) : sizeEntity;
    }

    public Size findByTypeAndValue(Size size) {

        return sizeRepository.findByTypeAndValue(size.getSizeType(),
                size.getValueEur(), size.getValueNum());
    }

    public List<Size> findAll() {

        return (List<Size>) sizeRepository.findAll();
    }

    public void deleteIfNotExists(Size size) {

        if (size.getItems().isEmpty())
            sizeRepository.delete(size);
    }
}
