import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BookingTest {

    @Test
    void newBookingStoresConstructorValuesAndStartsActive() {
        Vehicle vehicle = new Vehicle(301, VehicleType.CAR, 100.0);
        ParkingSlot slot = new ParkingSlot("R1", ParkingSlotType.REGULAR);
        LocalDateTime start = LocalDateTime.of(2026, 5, 24, 9, 0);
        LocalDateTime end = LocalDateTime.of(2026, 5, 24, 11, 0);

        Booking booking = new Booking(77, vehicle, slot, start, end, 20.0);

        assertEquals(77, booking.getBookingId());
        assertSame(vehicle, booking.getVehicle());
        assertSame(slot, booking.getParkingSlot());
        assertEquals(start, booking.getStartTime());
        assertEquals(end, booking.getEndTime());
        assertEquals(20.0, booking.getAmount());
        assertEquals(BookingStatus.ACTIVE, booking.getBookingStatus());
    }

    @Test
    void CompleteBookingChangesStatusToCompleted() {
        Booking booking = sampleBooking();

        booking.completeBooking();

        assertEquals(BookingStatus.COMPLETED, booking.getBookingStatus());
    }

    @Test
    void CancelBookingChangesStatusToCancelled() {
        Booking booking = sampleBooking();

        booking.cancelBooking();

        assertEquals(BookingStatus.CANCELLED, booking.getBookingStatus());
    }

    @Test
    void CompletedBookingShouldNotBeCancellable() {
        Booking booking = sampleBooking();

        booking.completeBooking();
        booking.cancelBooking();

        assertEquals(BookingStatus.COMPLETED, booking.getBookingStatus(),
                "Completed booking should not be changed to cancelled.");
    }

    @Test
    void CancelledBookingShouldNotBeCompletable() {
        Booking booking = sampleBooking();

        booking.cancelBooking();
        booking.completeBooking();

        assertEquals(BookingStatus.CANCELLED, booking.getBookingStatus(),
                "Cancelled booking should not be changed to completed.");
    }

    @Test
    void ToStringIncludesBookingDetails() {
        Booking booking = sampleBooking();

        String result = booking.toString();

        assertTrue(result.contains("bookingId=1"));
        assertTrue(result.contains("amount=10.0"));
        assertTrue(result.contains("bookingStatus=ACTIVE"));
    }

    private Booking sampleBooking() {
        Vehicle vehicle = new Vehicle(100, VehicleType.MOTORCYCLE, 100.0);
        ParkingSlot slot = new ParkingSlot("C1", ParkingSlotType.COMPACT);
        LocalDateTime start = LocalDateTime.of(2026, 5, 24, 9, 0);
        LocalDateTime end = LocalDateTime.of(2026, 5, 24, 10, 0);
        return new Booking(1, vehicle, slot, start, end, 10.0);
    }
}
