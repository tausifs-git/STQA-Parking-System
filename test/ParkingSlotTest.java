import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParkingSlotTest {
    private final LocalDateTime start = LocalDateTime.of(2026, Month.JUNE, 1, 14, 0);
    private final LocalDateTime end = LocalDateTime.of(2026, Month.JUNE, 1, 14, 30);

    private final VehicleType car = VehicleType.CAR;
    private final VehicleType motorcycle = VehicleType.MOTORCYCLE;
    private final VehicleType truck = VehicleType.TRUCK;
    private final VehicleType bicycle = VehicleType.BICYCLE;
    private final VehicleType microcar = VehicleType.MICROCAR;
    private final VehicleType bus = VehicleType.BUS;

    // --------------------------------------------------------------------------------
    // ParkingSlot(String slotId, ParkingSlotType slotType)
    @Test
    void testDefaultConstructor() {
        ParkingSlot slot = new ParkingSlot("S1", ParkingSlotType.LARGE);

        assertEquals("S1", slot.getSlotId());
        assertSame(ParkingSlotType.LARGE, slot.getSlotType());
        assertTrue(slot.isActive());
        assertTrue(slot.getBookings().isEmpty());
        assertEquals(0, slot.getBalance());
    }

    // --------------------------------------------------------------------------------
    // isActive(), activate(), deactivate()
    @Test
    void testActive() {
        ParkingSlot slot_01 = new ParkingSlot("S1", ParkingSlotType.LARGE);

        assertTrue(slot_01.isActive());

        slot_01.deactivate();
        assertFalse(slot_01.isActive());

        slot_01.activate();
        assertTrue(slot_01.isActive());
    }

    // --------------------------------------------------------------------------------
    // isCompatible(VehicleType type, LocalDateTime startTime, LocalDateTime endTime)
    @Test
    void testCompatibilityMatrix() {
        ParkingSlot slot_01 = new ParkingSlot("S1", ParkingSlotType.COMPACT);
        ParkingSlot slot_02 = new ParkingSlot("S2", ParkingSlotType.REGULAR);
        ParkingSlot slot_03 = new ParkingSlot("S3", ParkingSlotType.LARGE);
        ParkingSlot slot_04 = new ParkingSlot("S4", ParkingSlotType.HANDICAPPED);

        // car
        assertFalse(slot_01.isCompatible(car, start, end));
        assertTrue(slot_02.isCompatible(car, start, end));
        assertTrue(slot_03.isCompatible(car, start, end));
        assertFalse(slot_04.isCompatible(car, start, end));

        // motorcycle
        assertTrue(slot_01.isCompatible(motorcycle, start, end));
        assertTrue(slot_02.isCompatible(motorcycle, start, end));
        assertTrue(slot_03.isCompatible(motorcycle, start, end));
        assertFalse(slot_04.isCompatible(motorcycle, start, end));

        // bicycle
        assertTrue(slot_01.isCompatible(bicycle, start, end));
        assertTrue(slot_02.isCompatible(bicycle, start, end));
        assertTrue(slot_03.isCompatible(bicycle, start, end));
        assertTrue(slot_04.isCompatible(bicycle, start, end));

        // bus
        assertFalse(slot_01.isCompatible(bus, start, end));
        assertFalse(slot_02.isCompatible(bus, start, end));
        assertTrue(slot_03.isCompatible(bus, start, end));
        assertFalse(slot_04.isCompatible(bus, start, end));

        // microcar
        assertTrue(slot_01.isCompatible(microcar, start, end));
        assertTrue(slot_02.isCompatible(microcar, start, end));
        assertFalse(slot_03.isCompatible(microcar, start, end));
        assertFalse(slot_04.isCompatible(microcar, start, end));

        // truck -> has no conditions for compatibility (So for now test for all false)
        assertFalse(slot_01.isCompatible(truck, start, end));
        assertFalse(slot_02.isCompatible(truck, start, end));
        assertFalse(slot_03.isCompatible(truck, start, end));
        assertFalse(slot_04.isCompatible(truck, start, end));
    }

    @Test
    void compatibilityMatrixForTruck() {
        ParkingSlot slot_01 = new ParkingSlot("S1", ParkingSlotType.COMPACT);
        ParkingSlot slot_02 = new ParkingSlot("S2", ParkingSlotType.REGULAR);
        ParkingSlot slot_03 = new ParkingSlot("S3", ParkingSlotType.LARGE);
        ParkingSlot slot_04 = new ParkingSlot("S4", ParkingSlotType.HANDICAPPED);

        // assuming truck should be compatible with LARGE slot
        assertFalse(slot_01.isCompatible(truck, start, end));
        assertFalse(slot_02.isCompatible(truck, start, end));
        assertTrue(slot_03.isCompatible(truck, start, end));
        assertFalse(slot_04.isCompatible(truck, start, end));
    }

    @Test
    void testForInactiveSlot() {
        ParkingSlot slot_01 = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        slot_01.deactivate();

        assertFalse(slot_01.isCompatible(car, start, end));
    }
}
