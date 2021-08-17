package atm.storage;

import atm.entities.Cell;
import atm.enums.CurrencyRatio;
import atm.exceptions.NotEnoughMoneyException;
import atm.validators.Validator;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
public class StorageImpl implements Storage {

    private final Validator validator;
    private List<Cell> cells = new ArrayList<>();

    public StorageImpl(Validator validator) {
        this.validator = validator;
    }

    @Override
    public void deposit(Cell cell) {
        cells.add(cell);
    }

    @Override
    public void dumpCells() {
        log.info(">>ATM cells {}", cells);
    }

    @Override
    public HashMap<CurrencyRatio, Integer> withdraw(Integer withdrawSum) {
        validator.checkIfAtmHasEnoughMoney(cells, withdrawSum);
        validator.checkIfWithdrawIsMultipleOf(cells, withdrawSum);

        var withdrawResult = new HashMap<CurrencyRatio, Integer>();
        var withdrawnSum = withdrawSum;

        do {
            var cellToWithdraw = getPossibleCellsToWithdraw(withdrawnSum);

            if (cellToWithdraw.isEmpty())
                throw new NotEnoughMoneyException();

            var currentWithdrawCell = cellToWithdraw.get();
            if(currentWithdrawCell.isEmpty())
                throw new NotEnoughMoneyException();

            int count = withdrawResult.getOrDefault(currentWithdrawCell.getCurrencyRatio(), 0);
            withdrawResult.put(cellToWithdraw.get().getCurrencyRatio(), count + 1);
            withdrawnSum -= currentWithdrawCell.withdrawOne();
        } while (withdrawnSum != 0);

        return withdrawResult;
    }

    private Optional<Cell> getPossibleCellsToWithdraw(Integer withdrawnSum) {
        return cells
                .stream()
                .filter(cell -> cell.isRatioValid(withdrawnSum) && cell.isNotEmpty())
                .max((o1, o2) -> o1.getCurrencyRatio().getValue() - o2.getCurrencyRatio().getValue());
    }
}
