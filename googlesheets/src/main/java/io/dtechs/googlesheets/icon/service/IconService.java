package io.dtechs.googlesheets.icon.service;

import io.dtechs.googlesheets.aws.service.AwsService;
import io.dtechs.googlesheets.icon.model.Icon;
import io.dtechs.googlesheets.icon.repository.IconRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class IconService {

    private final AwsService awsService;

    private final IconRepository iconRepository;

    public Set<Icon> saveIfNotExists(String awsFolder) {

        var iconKeys = awsService.listIconKeys(awsFolder);

        Set<Icon> iconEntities = new HashSet<>();
        iconKeys.forEach(awsKey -> {

            var iconEntity = Icon.getIcon(awsService.getIconUrl(awsKey), awsKey);

            if (!awsKey.substring(awsFolder.length() + 1).contains("/")) {
                iconEntity.setMain(true);
            }

            if (iconEntity != null && !iconRepository.existsByStorageUrl(iconEntity.getStorageUrl())) {
                iconEntity = iconRepository.save(iconEntity);
            } else iconEntity = iconEntity == null ? null :
                    iconRepository.findByStorageUrl(iconEntity.getStorageUrl());
            iconEntities.add(iconEntity);
        });

        return iconEntities;
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
