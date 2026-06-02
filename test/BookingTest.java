import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BookingTest {

    @BeforeEach
    void resetSystem() {
        ParkingSystem ps = ParkingSystem.getInstance();

        ps.setVehicles(new ArrayList<>());
        ps.setParkingSlots(new ArrayList<>());
        ps.setBookings(new ArrayList<>());
        ps.setSYSTEM_WALLET(new Wallet());
    }
}
