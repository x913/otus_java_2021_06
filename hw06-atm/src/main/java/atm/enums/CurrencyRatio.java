package atm.enums;

public enum CurrencyRatio {
    RATIO_50(50),
    RATIO_100(100),
    RATIO_200(200),
    RATIO_500(500),
    RATIO_1000(1000),
    RATIO_2000(2000),
    RATIO_5000(5000);


    private int val;

    public int getValue() {
        return val;
    }

    private CurrencyRatio(int val) {
        this.val = val;
    }
}
