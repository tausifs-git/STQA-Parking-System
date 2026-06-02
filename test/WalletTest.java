import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WalletTest {
    private static final double DELTA = 1e-9;

    @Test
    void defaultConstructorStartsWithZeroBalance() {
        Wallet wallet = new Wallet();
        assertEquals(0.0, wallet.getBalance(), DELTA);
    }

    @Test
    void addFundsIncreasesBalance() {
        Wallet wallet = new Wallet(100.0);
        wallet.addFunds(25.5);
        assertEquals(125.5, wallet.getBalance(), DELTA);
    }
}
