package io.dtechs.googlesheets.size.model;

import io.dtechs.googlesheets.item.model.Item;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Getter
@Setter
public class Size {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * Тип размерной сетки
     */
    @Enumerated
    private SizeType sizeType;

    /**
     * Значение размера по европейской сетке
     */
    @Enumerated
    private EuropeanSize valueEur;

    /**
     * Значение размера по нумерической сетке
     */
    private Integer valueNum;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "size")
    private List<Item> items = new ArrayList<>();

    @Getter
    public enum SizeType {

        ONE_SIZE(Arrays.asList("ONE SIZE", "NONE", "1")),
        NUMERIC(Arrays.asList("NUMERIC")),
        EUROPEAN(Arrays.asList("EUROPEAN"));

        private final static Map<List<String>, SizeType> _map;

        static {

            _map = Stream.of(values()).collect(Collectors.toMap(SizeType::getNames, Function.identity()));
        }

        private final List<String> names;

        SizeType(List<String> names) {
            this.names = names;
        }

        public static SizeType resolveByName(String name) {

            SizeType sizeType = null;
            var keySet = _map.keySet();
            for (var namesList: keySet) {
                if (namesList.contains(name)) {
                    sizeType = _map.get(namesList);
                    break;
                }
            }

            if (sizeType == null) {

                if (EuropeanSize.resolveByName(name) != null)
                    sizeType = EUROPEAN;
                else
                {
                    try {
                        Integer.parseInt(name);
                        sizeType = NUMERIC;
                    } catch (NumberFormatException ex) {
//                        ex.printStackTrace();
                        return null;
                    }
                }
            }

            return sizeType;
        }
    }

    @Getter
    public enum EuropeanSize {

        XS(Arrays.asList("XS")),
        S(Arrays.asList("S")),
        M(Arrays.asList("M")),
        L(Arrays.asList("L")),
        XL(Arrays.asList("XL")),
        XL_2(Arrays.asList("2XL", "XXL")),
        XL_3(Arrays.asList("3XL", "XXXL")),
        XL_4(Arrays.asList("4XL", "XXXXL")),
        XL_5(Arrays.asList("5XL", "XXXXXL"));

        private final static Map<List<String>, EuropeanSize> _map;

        static {

            _map = Stream.of(values()).collect(Collectors.toMap(EuropeanSize::getNames, Function.identity()));
        }

        private final List<String> names;

        EuropeanSize(List<String> names) {
            this.names = names;
        }

        public static EuropeanSize resolveByName(String name) {

            EuropeanSize europeanSize = null;
            var keySet = _map.keySet();
            for (var namesList: keySet) {
                if (namesList.contains(name)) {
                    europeanSize = _map.get(namesList);
                    break;
                }
            }
            return europeanSize;
        }
    }

    public static Size getSize(String value) {

        if (value == null || value.isEmpty())
            return null;

        value = value.toUpperCase();

        Size size = new Size();

        size.setSizeType(SizeType.resolveByName(value));
        if (size.sizeType != null) {
            switch (size.sizeType) {

                case EUROPEAN -> size.setValueEur(EuropeanSize.resolveByName(value));
                case NUMERIC -> size.setValueNum(Integer.parseInt(value));
            }
        }

        return size;
    }

    public void addItem(Item item) {

        item.setSize(this);
        this.items.add(item);
    }
}
