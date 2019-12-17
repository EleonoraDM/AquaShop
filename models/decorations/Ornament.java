package models.decorations;

public class Ornament extends BaseDecoration {
    private static final int COMFORT_VALUE = 1;
    private static final int PRICE_VALUE = 5;


    public Ornament() {
        super(COMFORT_VALUE, PRICE_VALUE);
    }
}
