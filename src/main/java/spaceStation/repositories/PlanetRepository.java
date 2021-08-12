package spaceStation.repositories;

import spaceStation.common.ExceptionMessages;
import spaceStation.models.astronauts.Astronaut;
import spaceStation.models.planets.Planet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PlanetRepository implements Repository<Planet> {
    private List<Planet> planets;

    public PlanetRepository() {
        this.planets = new ArrayList<>();
    }

    @Override
    public Collection<Planet> getModels() {
        return Collections.unmodifiableCollection(planets);
    }

    @Override
    public void add(Planet planet) {
        if (!planets.contains(planet)) {
            this.planets.add(planet);
        }
    }

    @Override
    public boolean remove(Planet planet) {
        return this.planets.remove(planet);
    }

    @Override
    public Planet findByName(String name) {
        return planets.stream()
                .filter(p -> p.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
