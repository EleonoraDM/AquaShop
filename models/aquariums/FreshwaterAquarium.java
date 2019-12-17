package models.aquariums;

import models.aquariums.BaseAquarium;

public class FreshwaterAquarium extends BaseAquarium {
    private static final int DEFAULT_CAPACITY = 50;


    public FreshwaterAquarium(String name) {
        super(name, DEFAULT_CAPACITY);
    }
}
