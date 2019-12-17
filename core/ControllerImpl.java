package core;

import common.ConstantMessages;
import common.ExceptionMessages;
import models.aquariums.BaseAquarium;
import models.aquariums.FreshwaterAquarium;
import models.aquariums.SaltwaterAquarium;
import models.decorations.Decoration;
import models.decorations.Ornament;
import models.decorations.Plant;
import models.fish.BaseFish;
import models.fish.Fish;
import models.fish.FreshwaterFish;
import models.fish.SaltwaterFish;
import repositories.DecorationRepository;

import java.util.ArrayList;
import java.util.Collection;

public class ControllerImpl implements Controller {
    private DecorationRepository decorationRepository;
    private Collection<BaseAquarium> aquariums;

    public ControllerImpl() {
        this.decorationRepository = new DecorationRepository();
        this.aquariums = new ArrayList<>();
    }

    @Override
    public String addAquarium(String aquariumType, String aquariumName) {
        BaseAquarium aquarium;

        if (aquariumType.equals("FreshwaterAquarium")) {
            aquarium = new FreshwaterAquarium(aquariumName);
        } else if (aquariumType.equals("SaltwaterAquarium")) {
            aquarium = new SaltwaterAquarium(aquariumName);
        } else {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_AQUARIUM_TYPE);
        }
        this.aquariums.add(aquarium);

        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_AQUARIUM_TYPE, aquariumType);
    }

    @Override
    public String addDecoration(String type) {
        Decoration decoration;

        if (type.equals("Ornament")) {
            decoration = new Ornament();
        } else if (type.equals("Plant")) {
            decoration = new Plant();
        } else {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_DECORATION_TYPE);
        }
        this.decorationRepository.add(decoration);

        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_DECORATION_TYPE, type);
    }

    @Override
    public String insertDecoration(String aquariumName, String decorationType) {
        BaseAquarium aquarium = this.aquariums.
                stream().
                filter(baseAquarium -> baseAquarium.getName().equals(aquariumName)).
                findFirst().
                orElse(null);
        Decoration decoration = this.decorationRepository.getDecorations().
                stream().
                filter(decor -> decor.getClass().getSimpleName().equals(decorationType)).
                findFirst()
                .orElse(null);

        if (decoration == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.NO_DECORATION_FOUND, decorationType));
        }
        if (aquarium != null){
            aquarium.addDecoration(decoration);
        }
        return String.format
                (ConstantMessages.SUCCESSFULLY_ADDED_DECORATION_IN_AQUARIUM, decorationType, aquariumName);
    }

    @Override
    public String addFish(String aquariumName, String fishType, String fishName, String fishSpecies, double price) {
        BaseFish fish;

        if (fishType.equals("FreshwaterFish")) {
            fish = new FreshwaterFish(fishName, fishSpecies, price);
        } else if (fishType.equals("SaltwaterFish")) {
            fish = new SaltwaterFish(fishName, fishSpecies, price);
        } else {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_FISH_TYPE);
        }
        BaseAquarium aquarium = this.aquariums.
                stream().
                filter(a -> a.getName().equals(aquariumName)).
                findFirst().
                orElse(null);

        if (aquarium != null) {
            String aquariumType = aquarium.getClass().getSimpleName();

            if (aquariumType.equals("FreshwaterAquarium") && fishType.equals("FreshwaterFish")) {
                aquarium.addFish(fish);
            } else if (aquariumType.equals("SaltwaterAquarium") && fishType.equals("SaltwaterFish")) {
                aquarium.addFish(fish);
            } else {
                return ConstantMessages.WATER_NOT_SUITABLE;
            }
        }
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_FISH_IN_AQUARIUM, fishType, aquariumName);
    }

    @Override
    public String feedFish(String aquariumName) {
        BaseAquarium aquarium = this.aquariums.
                stream().
                filter(a -> a.getName().equals(aquariumName)).
                findFirst().
                orElse(null);

        int size = 0;

        if (aquarium != null) {
            aquarium.feed();
            size = aquarium.getFish().size();
        }
        return String.format(ConstantMessages.FISH_FED, size);
    }

    @Override
    public String calculateValue(String aquariumName) {
        BaseAquarium aquarium = this.aquariums.
                stream().
                filter(a -> a.getName().equals(aquariumName)).
                findFirst().
                orElse(null);

        double value = 0;

        if (aquarium != null) {
            double fishSum = aquarium.getFish().stream().mapToDouble(Fish::getPrice).sum();
            double decorationPrice = aquarium.getDecorations().stream().mapToDouble(Decoration::getPrice).sum();
            value = fishSum + decorationPrice;
        }
        return String.format(ConstantMessages.VALUE_AQUARIUM, aquariumName, value);
    }

    @Override
    public String report() {
        StringBuilder sb = new StringBuilder();
        this.aquariums.forEach(baseAquarium -> sb.append(baseAquarium.getInfo()));
        return sb.toString();
    }
}
