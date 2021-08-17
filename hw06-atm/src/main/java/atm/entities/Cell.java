package atm.entities;

import atm.enums.CurrencyRatio;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@AllArgsConstructor
public class Cell {
    private CurrencyRatio currencyRatio;
    private Integer amount;

    public Integer sum() {
        return currencyRatio.getValue() * amount;
    }

    public boolean isRatioValid(Integer withdrawSum) {
        return withdrawSum % currencyRatio.getValue() == 0;
    }

    public boolean isNotEmpty() {
        return amount != 0;
    }

    public boolean isEmpty() {
        return amount == 0;
    }

    public Integer withdrawOne() {
        if(amount > 0) {
            amount--;
            return currencyRatio.getValue();
        }
        return 0;
    }

}
