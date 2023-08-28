package io.dtechs.googlesheets.icon.model;

import io.dtechs.googlesheets.item.model.Item;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Icon {

    @Id
    @GeneratedValue
    private Long id;

    private URL storageUrl;

    private String key;

    private boolean isMain = false;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "icons")
    private Set<Item> items = new HashSet<>();

    public static Icon getIcon(URL storageUrl, String awsKey) {

        if (storageUrl == null)
            return null;

        Icon icon = new Icon();

        icon.setKey(awsKey);
        icon.setStorageUrl(storageUrl);

        return icon;
    }

    public void addItem(Item item) {

        item.getIcons().add(this);
        this.items.add(item);
    }

    public void deleteItem(Item item) {

        item.getIcons().remove(item);
        this.items.remove(item);
    }
}
