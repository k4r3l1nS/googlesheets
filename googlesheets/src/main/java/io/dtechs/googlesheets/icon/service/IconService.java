package io.dtechs.googlesheets.icon.service;

import io.dtechs.googlesheets.aws.service.AwsService;
import io.dtechs.googlesheets.icon.model.Icon;
import io.dtechs.googlesheets.icon.repository.IconRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class IconService {

    private final AwsService awsService;

    private final IconRepository iconRepository;

    public Icon saveIfNotExists(String awsKey) {

        var iconEntity = Icon.getIcon(awsService.getIconUrl(awsKey), awsKey);

        if (iconEntity != null && !iconRepository.existsByStorageUrl(iconEntity.getStorageUrl())) {
            iconEntity = iconRepository.save(iconEntity);
        } else iconEntity = iconEntity == null ? null :
                iconRepository.findByStorageUrl(iconEntity.getStorageUrl());

        return iconEntity;
    }

    public void deleteIfNotExists(Icon iconEntity) {

        if (iconEntity.getKey() == null || !awsService.fileExists(iconEntity.getKey())
                || iconEntity.getItems().isEmpty())
            iconRepository.delete(iconEntity);
    }

    public List<Icon> findAll() {

        return (List<Icon>) iconRepository.findAll();
    }
}
