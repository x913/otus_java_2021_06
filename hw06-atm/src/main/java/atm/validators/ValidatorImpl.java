package atm.validators;

import atm.entities.Cell;
import atm.exceptions.NotEnoughMoneyException;
import atm.exceptions.WithdrawSumIsNotAllowedMultipleException;

import java.util.List;

public class ValidatorImpl implements Validator {

    @Override
    public void checkIfAtmHasEnoughMoney(List<Cell> cells, Integer withdrawSum) {
        Integer totalCellsSum = cells.stream().reduce(0, (sum, cell) -> sum + cell.sum(), Integer::sum);
        if(withdrawSum > totalCellsSum)
            throw new NotEnoughMoneyException();
    }

    @Override
    public void checkIfWithdrawIsMultipleOf(List<Cell> cells, Integer withdrawSum) {
        var isMultipleOfValid = cells.stream().anyMatch(cell -> withdrawSum % cell.getCurrencyRatio().getValue() == 0);
        if(!isMultipleOfValid)
            throw new WithdrawSumIsNotAllowedMultipleException();
    }

}
