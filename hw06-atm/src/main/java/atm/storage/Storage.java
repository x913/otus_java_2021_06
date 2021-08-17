package atm.storage;

import atm.entities.Cell;
import atm.enums.CurrencyRatio;

import java.util.Map;

public interface Storage {
    void deposit(Cell cell);
    void dumpCells();
    Map<CurrencyRatio, Integer> withdraw(Integer amount);
}

