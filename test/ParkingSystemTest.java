import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParkingSystemTest {
    private final LocalDateTime start = LocalDateTime.of(2026, Month.JUNE, 1, 4, 0);
    private final LocalDateTime end = LocalDateTime.of(2026, Month.JUNE, 1, 5, 0);

    private final VehicleType car = VehicleType.CAR;
    private final VehicleType motorcycle = VehicleType.MOTORCYCLE;
    private final VehicleType truck = VehicleType.TRUCK;
    private final VehicleType bicycle = VehicleType.BICYCLE;
    private final VehicleType microcar = VehicleType.MICROCAR;
    private final VehicleType bus = VehicleType.BUS;

    private final ParkingSlot compact = new ParkingSlot("S1", ParkingSlotType.COMPACT);
    private final ParkingSlot regular = new ParkingSlot("S2", ParkingSlotType.REGULAR);
    private final ParkingSlot large = new ParkingSlot("S3", ParkingSlotType.LARGE);
    private final ParkingSlot handicapped = new ParkingSlot("S4", ParkingSlotType.HANDICAPPED);

    @BeforeEach
    void resetSystem() {
        ParkingSystem ps = ParkingSystem.getInstance();

        ps.setVehicles(new ArrayList<>());
        ps.setParkingSlots(new ArrayList<>());
        ps.setBookings(new ArrayList<>());
        ps.setSYSTEM_WALLET(new Wallet());
    }

    // ---------------------------------------------------------------------------
    // getAvailableParkingSlots(Vehicle, startTime, endTime);
    @Test
    void testAvailableParkingSlotForCar() {
        Vehicle vehicle = new Vehicle(1001, car, 100);

        ParkingSystem ps = ParkingSystem.getInstance();

        ps.addParkingSlot(compact);
        ps.addParkingSlot(regular);
        ps.addParkingSlot(large);
        ps.addParkingSlot(handicapped);

        List<ParkingSlot> availableSlots = ps.getAvailableParkingSlots(vehicle, start, end);

        assertEquals(2, availableSlots.size());
        assertTrue(availableSlots.contains(regular));
        assertTrue(availableSlots.contains(large));
    }

    @Test
    void testAvailableParkingSlotForMotorCycle() {
        Vehicle vehicle = new Vehicle(1001, motorcycle, 100);

        ParkingSystem ps = ParkingSystem.getInstance();

        ps.addParkingSlot(compact);
        ps.addParkingSlot(regular);
        ps.addParkingSlot(large);
        ps.addParkingSlot(handicapped);

        List<ParkingSlot> availableSlots = ps.getAvailableParkingSlots(vehicle, start, end);

        assertEquals(3, availableSlots.size());
        assertTrue(availableSlots.contains(regular));
        assertTrue(availableSlots.contains(large));
        assertTrue(availableSlots.contains(compact));
    }

    @Test
    void testAvailableParkingSlotForBicycle() {
        Vehicle vehicle = new Vehicle(1001, bicycle, 100);

        ParkingSystem ps = ParkingSystem.getInstance();

        ps.addParkingSlot(compact);
        ps.addParkingSlot(regular);
        ps.addParkingSlot(large);
        ps.addParkingSlot(handicapped);

        List<ParkingSlot> availableSlots = ps.getAvailableParkingSlots(vehicle, start, end);

        assertEquals(4, availableSlots.size());
        assertTrue(availableSlots.contains(regular));
        assertTrue(availableSlots.contains(large));
        assertTrue(availableSlots.contains(compact));
        assertTrue(availableSlots.contains(handicapped));
    }

    @Test
    void testAvailableParkingSlotForMicroCar() {
        Vehicle vehicle = new Vehicle(1001, microcar, 100);

        ParkingSystem ps = ParkingSystem.getInstance();

        ps.addParkingSlot(compact);
        ps.addParkingSlot(regular);
        ps.addParkingSlot(large);
        ps.addParkingSlot(handicapped);

        List<ParkingSlot> availableSlots = ps.getAvailableParkingSlots(vehicle, start, end);

        assertEquals(2, availableSlots.size());
        assertTrue(availableSlots.contains(regular));
        assertTrue(availableSlots.contains(compact));
    }

    @Test
    void testAvailableParkingSlotForBus() {
        Vehicle vehicle = new Vehicle(1001, bus, 100);

        ParkingSystem ps = ParkingSystem.getInstance();

        ps.addParkingSlot(compact);
        ps.addParkingSlot(regular);
        ps.addParkingSlot(large);
        ps.addParkingSlot(handicapped);

        List<ParkingSlot> availableSlots = ps.getAvailableParkingSlots(vehicle, start, end);

        assertEquals(1, availableSlots.size());
        assertTrue(availableSlots.contains(large));
    }

    // bug
    @Test
    void testAvailableParkingSlotForTruck() {
        Vehicle vehicle = new Vehicle(1001, truck, 100);

        ParkingSystem ps = ParkingSystem.getInstance();

        ps.addParkingSlot(compact);
        ps.addParkingSlot(regular);
        ps.addParkingSlot(large);
        ps.addParkingSlot(handicapped);

        List<ParkingSlot> availableSlots = ps.getAvailableParkingSlots(vehicle, start, end);

        assertEquals(1, availableSlots.size());
        assertTrue(availableSlots.contains(large));
    }


    // -------------------------------------------------------------------
    // addVehicle(Vehicle)
    @Test
    void testAddVehicle() {
        Vehicle vehicle_01 = new Vehicle(1001, car, 100);
        Vehicle vehicle_02 = new Vehicle(1002, bus, 100);

        ParkingSystem ps = ParkingSystem.getInstance();

        ps.addVehicle(vehicle_01);
        ps.addVehicle(vehicle_02);

        List<Vehicle> vehiclesAdded = ps.getVehicles();

        assertEquals(2, vehiclesAdded.size());
        assertTrue(vehiclesAdded.contains(vehicle_01));
        assertTrue(vehiclesAdded.contains(vehicle_02));
    }


    // -------------------------------------------------------------------
    // setVehicle(List<Vehicle>)
    @Test
    void testSetVehicle() {
        Vehicle vehicle_01 = new Vehicle(1001, car, 100);
        Vehicle vehicle_02 = new Vehicle(1002, bus, 100);
        Vehicle vehicle_03 = new Vehicle(1003, bicycle, 100);

        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle_01);
        vehicles.add(vehicle_02);
        vehicles.add(vehicle_03);

        ParkingSystem ps = ParkingSystem.getInstance();
        ps.setVehicles(vehicles);

        List<Vehicle> vehiclesAdded = ps.getVehicles();

        assertEquals(3, vehiclesAdded.size());
        assertTrue(vehiclesAdded.contains(vehicle_01));
        assertTrue(vehiclesAdded.contains(vehicle_02));
        assertTrue(vehiclesAdded.contains(vehicle_03));

        assertEquals(0, vehiclesAdded.indexOf(vehicle_01));
        assertEquals(1, vehiclesAdded.indexOf(vehicle_02));
        assertEquals(2, vehiclesAdded.indexOf(vehicle_03));
    }


    // -------------------------------------------------------------------
    // Booking book(Vehicle, ParkingSlot, startTime, endTime)
    @Test
    void testBooking() {
        Vehicle vehicle_01 = new Vehicle(1001, car, 100);

        ParkingSystem ps = ParkingSystem.getInstance();

        ps.book(vehicle_01, regular, start, end);

        List<Booking> bookings = ps.getBookings();

        assertEquals(1, bookings.size());
    }

    @Test
    void rejectBooksWithIllegalTime() {
        Vehicle vehicle = new Vehicle(1001, car, 100);
        ParkingSlot slot = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        ParkingSystem ps = ParkingSystem.getInstance();

        assertThrows(IllegalBookingTimeException.class, () -> ps.book(vehicle, slot, start, start));
        assertThrows(IllegalBookingTimeException.class, () -> ps.book(vehicle, slot, start, start.minusMinutes(1)));
    }

    @Test
    void testCompatibility() {
        Vehicle vehicle_01 = new Vehicle(1001, bus, 100);

        ParkingSystem ps = ParkingSystem.getInstance();

        assertThrows(IllegalArgumentException.class, () ->  ps.book(vehicle_01, compact, start, end));
    }





}
