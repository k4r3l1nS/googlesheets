package io.dtechs.googlesheets.icon.model;

import io.dtechs.googlesheets.item.model.Item;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Icon {

    @Id
    @GeneratedValue
    private Long id;

    private URL storageUrl;

    private String key;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "icon")
    private List<Item> items = new ArrayList<>();

    public static Icon getIcon(URL storageUrl, String awsKey) {

        if (storageUrl == null)
            return null;

        Icon icon = new Icon();

        icon.setKey(awsKey);
        icon.setStorageUrl(storageUrl);

        return icon;
    }

    public void addItem(Item item) {

        item.setIcon(this);
        this.items.add(item);
    }

    public void deleteItem(Item item) {

        item.setIcon(null);
        this.items.remove(item);
    }
}
