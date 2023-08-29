package io.dtechs.googlesheets.controllers;

import io.dtechs.googlesheets.item.dto.ItemInfoDto;
import io.dtechs.googlesheets.view.service.ItemViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/get")
public class ItemController {

    private final ItemViewService itemViewService;

    @GetMapping("/items")
    public ItemInfoDto get() {

        return itemViewService.get();
    }
}
