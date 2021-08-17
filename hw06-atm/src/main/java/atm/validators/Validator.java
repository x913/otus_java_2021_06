package atm.validators;

import atm.entities.Cell;

import java.util.List;

public interface Validator {
    void checkIfAtmHasEnoughMoney(List<Cell> cells, Integer withdrawSum);

    void checkIfWithdrawIsMultipleOf(List<Cell> cells, Integer withdrawSum);
}
