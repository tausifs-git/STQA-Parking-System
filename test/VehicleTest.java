import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("VehicleTest")
class VehicleTest {


    @Nested
    @DisplayName("Constructor — (int, VehicleType, double)")
    class DoubleConstructor {

        @Test
        @DisplayName("stores vehicleId correctly")
        void storesVehicleId() {
            Vehicle v = new Vehicle(42, VehicleType.CAR, 100.0);
            assertEquals(42, v.getVehicleId());
        }

        @Test
        @DisplayName("stores vehicleType correctly")
        void storesVehicleType() {
            Vehicle v = new Vehicle(1, VehicleType.MOTORCYCLE, 100.0);
            assertEquals(VehicleType.MOTORCYCLE, v.getVehicleType());
        }

        @Test
        @DisplayName("stores initial balance correctly")
        void storesInitialBalance() {
            Vehicle v = new Vehicle(1, VehicleType.CAR, 250.0);
            assertEquals(250.0, v.getBalance());
        }

        @Test
        @DisplayName("creates a non-null Wallet internally")
        void createsNonNullWallet() {
            Vehicle v = new Vehicle(1, VehicleType.CAR, 100.0);
            assertNotNull(v.getWallet());
        }

        @Test
        @DisplayName("zero initial balance is accepted")
        void zeroBalanceAccepted() {
            Vehicle v = new Vehicle(1, VehicleType.BICYCLE, 0.0);
            assertEquals(0.0, v.getBalance());
        }

        @Test
        @DisplayName("negative initial balance should throw IllegalArgumentException")
        void negativeInitialBalanceShouldThrow() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Vehicle(1, VehicleType.CAR, -100.0),
                    "Vehicle should reject a negative initial balance");
        }

        @Test
        @DisplayName("null VehicleType should throw NullPointerException or IllegalArgumentException")
        void nullVehicleTypeShouldThrow() {
            assertThrows(NullPointerException.class,
                    () -> new Vehicle(1, null, 100.0),
                    "Vehicle should reject null VehicleType on construction");
        }

        @Test
        @DisplayName("vehicleId of 0 is stored (boundary)")
        void vehicleIdZero() {
            Vehicle v = new Vehicle(0, VehicleType.CAR, 100.0);
            assertEquals(0, v.getVehicleId());
        }

        @Test
        @DisplayName("negative vehicleId is stored (no validation)")
        void negativeVehicleIdStored() {
            Vehicle v = new Vehicle(-5, VehicleType.CAR, 100.0);
            assertEquals(-5, v.getVehicleId());
        }

        @Test
        @DisplayName("all VehicleType values can be stored")
        void allVehicleTypesAccepted() {
            for (VehicleType type : VehicleType.values()) {
                Vehicle v = new Vehicle(1, type, 100.0);
                assertEquals(type, v.getVehicleType());
            }
        }

        @Test
        @DisplayName("large initial balance stored correctly")
        void largeBalanceStored() {
            Vehicle v = new Vehicle(1, VehicleType.TRUCK, 1_000_000.0);
            assertEquals(1_000_000.0, v.getBalance());
        }
    }

    @Nested
    @DisplayName("Constructor — (int, VehicleType, Wallet)")
    class WalletConstructor {

        @Test
        @DisplayName("stores vehicleId correctly")
        void storesVehicleId() {
            Vehicle v = new Vehicle(7, VehicleType.BUS, new Wallet(50.0));
            assertEquals(7, v.getVehicleId());
        }

        @Test
        @DisplayName("stores vehicleType correctly")
        void storesVehicleType() {
            Vehicle v = new Vehicle(1, VehicleType.TRUCK, new Wallet(50.0));
            assertEquals(VehicleType.TRUCK, v.getVehicleType());
        }

        @Test
        @DisplayName("stores the exact Wallet reference (same instance)")
        void storesSameWalletReference() {
            Wallet w = new Wallet(300.0);
            Vehicle v = new Vehicle(1, VehicleType.CAR, w);
            assertSame(w, v.getWallet());
        }

        @Test
        @DisplayName("getBalance() delegates to the provided Wallet")
        void balanceDelegatesToWallet() {
            Wallet w = new Wallet(300.0);
            Vehicle v = new Vehicle(1, VehicleType.CAR, w);
            assertEquals(300.0, v.getBalance());
        }

        @Test
        @DisplayName("mutations on the passed Wallet are reflected in getBalance()")
        void walletMutationsReflected() {
            Wallet w = new Wallet(100.0);
            Vehicle v = new Vehicle(1, VehicleType.CAR, w);
            w.addFunds(50.0);
            assertEquals(150.0, v.getBalance());
        }

        @Test
        @DisplayName("null Wallet argument should throw NullPointerException")
        void nullWalletShouldThrow() {
            assertThrows(NullPointerException.class,
                    () -> new Vehicle(1, VehicleType.CAR, (Wallet) null),
                    "Vehicle constructor should reject a null Wallet");
        }

        @Test
        @DisplayName("Wallet with zero balance is accepted")
        void zeroBalanceWallet() {
            Vehicle v = new Vehicle(1, VehicleType.CAR, new Wallet(0.0));
            assertEquals(0.0, v.getBalance());
        }
    }


    @Nested
    @DisplayName("getVehicleId()")
    class GetVehicleId {

        @Test
        @DisplayName("returns the id supplied at construction")
        void returnsConstructedId() {
            assertEquals(99, new Vehicle(99, VehicleType.CAR, 0.0).getVehicleId());
        }

        @Test
        @DisplayName("id is immutable — no setter changes it")
        void idIsImmutable() {
            Vehicle v = new Vehicle(10, VehicleType.CAR, 0.0);
            v.getBalance();
            v.getWallet();
            v.getVehicleType();
            assertEquals(10, v.getVehicleId());
        }
    }

    @Nested
    @DisplayName("getVehicleType()")
    class GetVehicleType {

        @Test
        @DisplayName("returns the type supplied at construction (double constructor)")
        void returnsTypeDoubleConstructor() {
            Vehicle v = new Vehicle(1, VehicleType.BUS, 0.0);
            assertEquals(VehicleType.BUS, v.getVehicleType());
        }

        @Test
        @DisplayName("returns the type supplied at construction (Wallet constructor)")
        void returnsTypeWalletConstructor() {
            Vehicle v = new Vehicle(1, VehicleType.TRUCK, new Wallet());
            assertEquals(VehicleType.TRUCK, v.getVehicleType());
        }
    }

    @Nested
    @DisplayName("getWallet()")
    class GetWallet {

        @Test
        @DisplayName("never returns null when constructed with double balance")
        void notNullForDoubleConstructor() {
            Vehicle v = new Vehicle(1, VehicleType.CAR, 50.0);
            assertNotNull(v.getWallet());
        }

        @Test
        @DisplayName("never returns null when constructed with Wallet")
        void notNullForWalletConstructor() {
            Vehicle v = new Vehicle(1, VehicleType.CAR, new Wallet(50.0));
            assertNotNull(v.getWallet());
        }

        @Test
        @DisplayName("returned Wallet is fully usable (addFunds)")
        void returnedWalletIsUsable() {
            Vehicle v = new Vehicle(1, VehicleType.CAR, 50.0);
            assertDoesNotThrow(() -> v.getWallet().addFunds(10.0));
            assertEquals(60.0, v.getBalance());
        }
    }

    @Nested
    @DisplayName("getBalance()")
    class GetBalance {

        @Test
        @DisplayName("reflects the initial balance set via double constructor")
        void reflectsInitialDoubleBalance() {
            assertEquals(123.45, new Vehicle(1, VehicleType.CAR, 123.45).getBalance(), 0.0001);
        }

        @Test
        @DisplayName("reflects wallet balance after addFunds")
        void updatesAfterAddFunds() {
            Vehicle v = new Vehicle(1, VehicleType.CAR, 100.0);
            v.getWallet().addFunds(40.0);
            assertEquals(140.0, v.getBalance());
        }

        @Test
        @DisplayName("reflects wallet balance after deductFunds")
        void updatesAfterDeductFunds() {
            Vehicle v = new Vehicle(1, VehicleType.CAR, 100.0);
            v.getWallet().deductFunds(30.0);
            assertEquals(70.0, v.getBalance());
        }

        @Test
        @DisplayName("reflects wallet balance after transferFunds out")
        void updatesAfterTransferOut() {
            Vehicle v = new Vehicle(1, VehicleType.CAR, 100.0);
            Wallet other = new Wallet();
            v.getWallet().transferFunds(other, 60.0);
            assertEquals(40.0, v.getBalance());
        }

        @Test
        @DisplayName("balance is 0.0 when wallet starts empty")
        void zeroWhenEmpty() {
            Vehicle v = new Vehicle(1, VehicleType.CAR, new Wallet());
            assertEquals(0.0, v.getBalance());
        }

        @Test
        @DisplayName("two Vehicles with same data should be equal (missing equals())")
        void equalVehiclesShouldBeEqual() {
            Vehicle v1 = new Vehicle(1, VehicleType.CAR, 100.0);
            Vehicle v2 = new Vehicle(1, VehicleType.CAR, 100.0);
            assertEquals(v1, v2,
                    "Vehicles with same id, type, and balance should be equal");
        }
    }

    @Nested
    @DisplayName("toString()")
    class ToStringTest {

        @Test
        @DisplayName("contains vehicleId")
        void containsVehicleId() {
            String s = new Vehicle(77, VehicleType.CAR, 0.0).toString();
            assertTrue(s.contains("77"));
        }

        @Test
        @DisplayName("contains vehicleType name")
        void containsVehicleType() {
            String s = new Vehicle(1, VehicleType.TRUCK, 0.0).toString();
            assertTrue(s.contains("TRUCK"));
        }

        @Test
        @DisplayName("contains wallet balance value")
        void containsBalance() {
            String s = new Vehicle(1, VehicleType.CAR, 55.0).toString();
            assertTrue(s.contains("55.0"));
        }

        @Test
        @DisplayName("is not null")
        void notNull() {
            assertNotNull(new Vehicle(1, VehicleType.CAR, 0.0).toString());
        }

        @Test
        @DisplayName("is not empty")
        void notEmpty() {
            assertFalse(new Vehicle(1, VehicleType.CAR, 0.0).toString().isEmpty());
        }

        @Test
        @DisplayName("starts with 'Vehicle{'")
        void startsWithVehicle() {
            assertTrue(new Vehicle(1, VehicleType.CAR, 0.0).toString().startsWith("Vehicle{"));
        }

        @Test
        @DisplayName("ends with '}'")
        void endsWithBrace() {
            assertTrue(new Vehicle(1, VehicleType.CAR, 0.0).toString().endsWith("}"));
        }

        @Test
        @DisplayName("contains 'walletBalance' key")
        void containsWalletBalanceKey() {
            assertTrue(new Vehicle(1, VehicleType.CAR, 99.0).toString().contains("walletBalance"));
        }

        @Test
        @DisplayName("balance in toString updates after wallet mutation")
        void balanceUpdatesInToString() {
            Vehicle v = new Vehicle(1, VehicleType.CAR, 50.0);
            v.getWallet().addFunds(50.0);
            assertTrue(v.toString().contains("100.0"));
        }
    }

    @Nested
    @DisplayName("Package-private wallet field")
    class PackagePrivateWallet {

        @Test
        @DisplayName("direct field access matches getWallet() in same package")
        void directFieldMatchesGetter() {
            Wallet w = new Wallet(200.0);
            Vehicle v = new Vehicle(1, VehicleType.CAR, w);
            assertSame(v.wallet, v.getWallet());
        }

        @Test
        @DisplayName("mutating wallet via direct field affects getBalance()")
        void directFieldMutationAffectsBalance() {
            Vehicle v = new Vehicle(1, VehicleType.CAR, 100.0);
            v.wallet.addFunds(200.0);
            assertEquals(300.0, v.getBalance());
        }
    }
}
