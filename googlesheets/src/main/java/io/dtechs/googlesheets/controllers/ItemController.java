package io.dtechs.googlesheets.controllers;

import io.dtechs.googlesheets.view.model.ItemView;
import io.dtechs.googlesheets.view.service.ItemViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/get")
public class ItemController {

    private final ItemViewService itemViewService;

    @GetMapping("/item-view")
    public List<ItemView> get() {

        return itemViewService.findAll();
    }
}
