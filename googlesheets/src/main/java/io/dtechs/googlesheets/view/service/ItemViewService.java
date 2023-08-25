package io.dtechs.googlesheets.view.service;

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

    @Transactional(readOnly = true)
    public List<ItemView> findAll() {

        var itemViewIterable = itemViewRepository.findAll();

        return (List<ItemView>) itemViewIterable;
    }
}
