package spaceStation.models.mission;

import spaceStation.common.ExceptionMessages;
import spaceStation.models.astronauts.Astronaut;
import spaceStation.models.planets.Planet;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class MissionImpl implements Mission {

    @Override
    public void explore(Planet planet, Collection<Astronaut> astronauts) {

        Collection<String> items = planet.getItems();
        Iterator<String> iterator = items.iterator();

        if (astronauts.isEmpty()) {
            throw new IllegalArgumentException(ExceptionMessages.PLANET_ASTRONAUTS_DOES_NOT_EXISTS);
        }

        for (Astronaut astronaut : astronauts) {

            while (iterator.hasNext()) {
                if (astronautHasOxygen(astronaut)) {
                    String item = iterator.next();
                    astronaut.getBag().getItems().add(item);
                } else {
                    break;
                }
            }

            if (!iterator.hasNext()) {
                break;
            }
        }
    }

    private boolean astronautHasOxygen(Astronaut astronaut) {
        if (astronaut.getOxygen() <= 0) {
            return false;
        }

        astronaut.breath();

        return astronaut.getOxygen() > 0;
    }
}
