import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WalletTest {
    private static final double DELTA = 1e-9;

    @BeforeEach
    void resetSystem() {
        ParkingSystem ps = ParkingSystem.getInstance();

        ps.setVehicles(new ArrayList<>());
        ps.setParkingSlots(new ArrayList<>());
        ps.setBookings(new ArrayList<>());
        ps.setSYSTEM_WALLET(new Wallet());
    }

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
