package spaceStation.models.astronauts;

import spaceStation.common.ConstantMessages;
import spaceStation.common.ExceptionMessages;
import spaceStation.models.bags.Backpack;
import spaceStation.models.bags.Bag;

import java.util.Collection;

public abstract class BaseAstronaut implements Astronaut {
    private String name;
    private double oxygen;
    private Bag bag;

    public BaseAstronaut(String name, double oxygen) {
        this.setName(name);
        this.setOxygen(oxygen);

        this.bag = new Backpack();
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new NullPointerException(ExceptionMessages.ASTRONAUT_NAME_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    @Override
    public double getOxygen() {
        return oxygen;
    }

    public void setOxygen(double oxygen) {
        if (oxygen < 0) {
            throw new IllegalArgumentException(ExceptionMessages.ASTRONAUT_OXYGEN_LESS_THAN_ZERO);
        }
        this.oxygen = oxygen;
    }

    @Override
    public Bag getBag() {
        return bag;
    }

    public void setBag(Bag bag) {
        this.bag = bag;
    }

    @Override
    public void breath() {
        setOxygen(getOxygen() - 10);
    }

    @Override
    public boolean canBreath() {
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        Collection<String> items = getBag().getItems();
        String bagItems = items.isEmpty() ? "none" : String.join(ConstantMessages.REPORT_ASTRONAUT_BAG_ITEMS_DELIMITER, items);

        builder.append(String.format(ConstantMessages.REPORT_ASTRONAUT_NAME, name)).append(System.lineSeparator())
                .append(String.format(ConstantMessages.REPORT_ASTRONAUT_OXYGEN, oxygen)).append(System.lineSeparator())
                .append(String.format(ConstantMessages.REPORT_ASTRONAUT_BAG_ITEMS, bagItems));

        return builder.toString().trim();
    }
}
