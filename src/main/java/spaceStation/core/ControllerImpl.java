package spaceStation.core;

import spaceStation.common.ConstantMessages;
import spaceStation.common.ExceptionMessages;
import spaceStation.models.astronauts.Astronaut;
import spaceStation.models.astronauts.Biologist;
import spaceStation.models.astronauts.Geodesist;
import spaceStation.models.astronauts.Meteorologist;
import spaceStation.models.mission.Mission;
import spaceStation.models.mission.MissionImpl;
import spaceStation.models.planets.Planet;
import spaceStation.models.planets.PlanetImpl;
import spaceStation.repositories.AstronautRepository;
import spaceStation.repositories.PlanetRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerImpl implements Controller {

    private AstronautRepository astronautRepository;
    private PlanetRepository planetRepository;
    private Mission mission;
    private static int EXPLORED_PLANETS = 0;

    public ControllerImpl() {
        this.astronautRepository = new AstronautRepository();
        this.planetRepository = new PlanetRepository();
        this.mission = new MissionImpl();
    }

    @Override
    public String addAstronaut(String type, String astronautName) {
        Astronaut astronaut = null;

        switch (type) {
            case "Biologist":
                astronaut = new Biologist(astronautName);
                break;
            case "Geodesist":
                astronaut = new Geodesist(astronautName);
                break;
            case "Meteorologist":
                astronaut = new Meteorologist(astronautName);
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.ASTRONAUT_INVALID_TYPE);
        }

        astronautRepository.add(astronaut);

        return String.format(ConstantMessages.ASTRONAUT_ADDED, type, astronautName);
    }

    @Override
    public String addPlanet(String planetName, String... items) {

        Planet planet = new PlanetImpl(planetName);

        planet.getItems().addAll(Arrays.asList(items));

        planetRepository.add(planet);

        return String.format(ConstantMessages.PLANET_ADDED, planetName);
    }

    @Override
    public String retireAstronaut(String astronautName) {

        Astronaut astronaut = astronautRepository.getModels().stream()
                .filter(a -> a.getName().equals(astronautName))
                .findFirst()
                .orElse(null);

        if (astronaut == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.ASTRONAUT_DOES_NOT_EXIST, astronautName));
        }

        astronautRepository.remove(astronaut);

        return String.format(ConstantMessages.ASTRONAUT_RETIRED, astronautName);
    }

    @Override
    public String explorePlanet(String planetName) {

        Planet planet = planetRepository.findByName(planetName);

        List<Astronaut> astronauts = astronautRepository.getModels().stream()
                .filter(a -> a.getOxygen() > 60)
                .collect(Collectors.toList());

        mission.explore(planet, astronauts);
        EXPLORED_PLANETS++;

        List<Astronaut> deadAstronauts = astronauts.stream()
                .filter(a -> a.getOxygen() <= 0)
                .collect(Collectors.toList());

        return String.format(ConstantMessages.PLANET_EXPLORED,
                planetName, deadAstronauts.size());
    }

    @Override
    public String report() {
        StringBuilder builder = new StringBuilder();

        builder.append(String.format(ConstantMessages.REPORT_PLANET_EXPLORED, EXPLORED_PLANETS)).append(System.lineSeparator())
        .append(ConstantMessages.REPORT_ASTRONAUT_INFO).append(System.lineSeparator());

        Collection<Astronaut> astronauts = astronautRepository.getModels();

        for (Astronaut astronaut : astronauts) {
            builder.append(astronaut).append(System.lineSeparator());
        }

        return builder.toString().trim();
    }
}
