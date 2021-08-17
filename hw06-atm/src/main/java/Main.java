import atm.entities.Cell;
import atm.enums.CurrencyRatio;
import atm.exceptions.NotEnoughMoneyException;
import atm.exceptions.WithdrawSumIsNotAllowedMultipleException;
import atm.storage.StorageImpl;
import atm.validators.ValidatorImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class Main {
    public static void main(String[] args) {
        var storage = new StorageImpl(new ValidatorImpl());

        storage.deposit(new Cell(CurrencyRatio.RATIO_50, 5));
        storage.deposit(new Cell(CurrencyRatio.RATIO_100, 5));
        storage.deposit(new Cell(CurrencyRatio.RATIO_500, 1));
        storage.deposit(new Cell(CurrencyRatio.RATIO_1000, 2));
        storage.deposit(new Cell(CurrencyRatio.RATIO_5000, 2));

        var sumsToWithdraw = List.of(100, 150, 900, 1050, 6050, 9000);
        for(var sum : sumsToWithdraw) {
            try {
                var withdrawn = storage.withdraw(sum);
                log.info("ATM withdrawn for sum of {}, result: {}", sum, withdrawn);
            } catch (NotEnoughMoneyException ex) {
                log.error("ATM doesn't have enough money to withdraw sum of {}", sum);
                storage.dumpCells();
            } catch (WithdrawSumIsNotAllowedMultipleException ex) {
                log.error("Withdraw sum should be multiple of ratios");
            }
        }



    }
}
