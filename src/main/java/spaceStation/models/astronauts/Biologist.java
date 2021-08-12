package spaceStation.models.astronauts;

public class Biologist extends BaseAstronaut {

    private static double INITIAL_UNITS_OF_OXYGEN = 70;

    public Biologist(String name) {
        super(name, INITIAL_UNITS_OF_OXYGEN);
    }

    @Override
    public void breath() {
        setOxygen(getOxygen() - 5);
    }

}
