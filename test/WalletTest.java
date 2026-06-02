import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WalletTest {
    private static final double BIG_VALUE = 1e10;
    private static final double SMALL_VALUE = 1e-10;

//    @BeforeEach
//    void resetSystem() {
//        ParkingSystem ps = ParkingSystem.getInstance();
//
//        ps.setVehicles(new ArrayList<>());
//        ps.setParkingSlots(new ArrayList<>());
//        ps.setBookings(new ArrayList<>());
//        ps.setSYSTEM_WALLET(new Wallet());
//    }


    // ----------------------------------------------------------
    // Constructor Wallet(), Wallet(double balance) and getBalance()
    @Test
    void walletInitializedOnDefaultConstructor() {
        Wallet wallet = new Wallet();
        assertEquals(0.0, wallet.getBalance());
    }

    @Test
    void walletInitializedWithCustomValue() {
        Wallet wallet = new Wallet(25.5);
        assertEquals(25.5, wallet.getBalance());
    }

    @Test
    void walletInitializedWithVeryBigNumber() {
        Wallet wallet = new Wallet(BIG_VALUE);
        assertEquals(BIG_VALUE, wallet.getBalance());
    }

    @Test
    void walletInitializedWithVerySmallNumber() {
        Wallet wallet = new Wallet(SMALL_VALUE);
        assertEquals(SMALL_VALUE, wallet.getBalance());
    }

    @Test
    void walletInitializedWithNegativeValue() {
        assertThrows(InvalidAmountException.class, () -> {
            Wallet wallet = new Wallet(-10.25);
            wallet.getBalance();
        });
    }


    // ---------------------------------------------------------------
    // addFunds(double amount)
    @Test
    void addingFundToDefaultConstructor() {
        Wallet wallet = new Wallet();
        wallet.addFunds(10.25);
        assertEquals(10.25, wallet.getBalance());
    }

    @Test
    void addingFundToCustomConstructor() {
        Wallet wallet = new Wallet(9.75);
        wallet.addFunds(10.25);
        assertEquals(20.0, wallet.getBalance());
    }

    @Test
    void addingZeroFundToCustomConstructor() {
        Wallet wallet = new Wallet(9.75);
        assertThrows(InvalidAmountException.class, () -> {
            wallet.addFunds(0);
        });
    }

    @Test
    void addingNegativeFundToCustomConstructor() {
        Wallet wallet = new Wallet(9.75);
        assertThrows(InvalidAmountException.class, () -> {
            wallet.addFunds(-0.75);
        });
    }

    @Test
    void addingVeryBigFundToCustomConstructor() {
        Wallet wallet = new Wallet(100.0);
        wallet.addFunds(BIG_VALUE);
        assertEquals(BIG_VALUE +100, wallet.getBalance());
    }

    @Test
    void addingVerySmallFundToCustomConstructor() {
        Wallet wallet = new Wallet(100.0);
        wallet.addFunds(SMALL_VALUE);
        assertEquals(SMALL_VALUE +100, wallet.getBalance());
    }


    // --------------------------------------------------------------
    // deductFunds(double amount)
    @Test
    void deductingFundToDefaultConstructor() {
        Wallet wallet = new Wallet();
        assertThrows(InsufficientFundsException.class, () -> {
            wallet.deductFunds(10);
        });
    }

    @Test
    void deductingFundToCustomConstructor() {
        Wallet wallet = new Wallet(10.00);
        wallet.deductFunds(0.25);
        assertEquals(9.75, wallet.getBalance());
    }

    @Test
    void deductingExactBalance() {
        Wallet wallet = new Wallet(10.00);
        wallet.deductFunds(10.00);
        assertEquals(0, wallet.getBalance());
    }

    @Test
    void deductingInsufficientFundToCustomConstructor() {
        Wallet wallet = new Wallet(10.00);
        assertThrows(InsufficientFundsException.class, () -> {
            wallet.deductFunds(12.00);
        });
    }

    @Test
    void deductingZeroFundToCustomConstructor() {
        Wallet wallet = new Wallet(10.00);
        assertThrows(InvalidAmountException.class, () -> {
            wallet.deductFunds(0);
        });
    }

    @Test
    void deductingNegativeFundToCustomConstructor() {
        Wallet wallet = new Wallet(10.00);
        assertThrows(InvalidAmountException.class, () -> {
            wallet.deductFunds(-0.75);
        });
    }

    @Test
    void deductingVeryBigFundFromBigBalance() {
        Wallet wallet = new Wallet(BIG_VALUE);
        wallet.deductFunds(BIG_VALUE -20.25);
        assertEquals(20.25, wallet.getBalance());
    }

    @Test
    void deductingVerySmallFundFromBigBalance() {
        Wallet wallet = new Wallet(BIG_VALUE);
        wallet.deductFunds(BIG_VALUE - SMALL_VALUE);
        assertEquals(SMALL_VALUE, wallet.getBalance());
    }

    @Test
    void deductingVerySmallFundFromCustomBalance() {
        Wallet wallet = new Wallet(100);
        wallet.deductFunds(SMALL_VALUE);
        assertEquals(100 - SMALL_VALUE, wallet.getBalance());
    }

    @Test
    void deductASmallAmount() {
        Wallet wallet = new Wallet(100);
        wallet.deductFunds(0.0005);
        assertEquals(100 - 0.0005, wallet.getBalance());
    }


    // ---------------------------------------------------------------
    // transferFunds(Wallet toWallet, double amount)
    @Test
    void validTransfer() {
        Wallet wallet_01 = new Wallet();
        Wallet wallet_02 = new Wallet(100.00);
        wallet_02.transferFunds(wallet_01, 90.00);

        assertEquals(90, wallet_01.getBalance());
        assertEquals(10, wallet_02.getBalance());
    }

    @Test
    void transferBetweenTwoWallets() {
        Wallet wallet_01 = new Wallet(100.00);
        Wallet wallet_02 = new Wallet(100.00);

        wallet_01.transferFunds(wallet_02, 50.00);
        wallet_02.transferFunds(wallet_01, 10.00);

        assertEquals(60, wallet_01.getBalance());
        assertEquals(140, wallet_02.getBalance());
    }

    @Test
    void transferBetweenTwoWalletsWithExceedingBalance() {
        Wallet wallet_01 = new Wallet(100.00);
        Wallet wallet_02 = new Wallet(100.00);

        wallet_01.transferFunds(wallet_02, 50.00);
        wallet_02.transferFunds(wallet_01, 110.00);     // Transferring more money than initially present

        assertEquals(160, wallet_01.getBalance());
        assertEquals(40, wallet_02.getBalance());
    }

    @Test
    void transferBigAmount() {
        Wallet wallet_01 = new Wallet(100.00);
        Wallet wallet_02 = new Wallet(BIG_VALUE);

        wallet_02.transferFunds(wallet_01, BIG_VALUE - 10.0);

        assertEquals(100 + BIG_VALUE - 10.0, wallet_01.getBalance());
        assertEquals(10, wallet_02.getBalance());
    }

    @Test
    void transferSmallAmount() {
        Wallet wallet_01 = new Wallet(100.00);
        Wallet wallet_02 = new Wallet(100.00);

        wallet_02.transferFunds(wallet_01, SMALL_VALUE);

        assertEquals(100 + SMALL_VALUE, wallet_01.getBalance());
        assertEquals(100 - SMALL_VALUE, wallet_02.getBalance());
    }

    @Test
    void transferExactBalance() {
        Wallet wallet_01 = new Wallet(100.00);
        Wallet wallet_02 = new Wallet();

        wallet_01.transferFunds(wallet_02, 100.00);

        assertEquals(0.00, wallet_01.getBalance());
        assertEquals(100.00, wallet_02.getBalance());
    }

    @Test
    void inSufficientFundTransfer() {
        Wallet wallet_01 = new Wallet();
        Wallet wallet_02 = new Wallet(100.00);

        assertThrows(InsufficientFundsException.class, () -> {
            wallet_02.transferFunds(wallet_01, 110.00);
        });
    }

    @Test
    void inSufficientBalanceTransfer() {
        Wallet wallet_01 = new Wallet(100.00);
        Wallet wallet_02 = new Wallet(100.00);

        assertThrows(InvalidAmountException.class, () -> {
            wallet_02.transferFunds(wallet_01, 0);
        });
    }

    @Test
    void negativeBalanceTransfer() {
        Wallet wallet_01 = new Wallet(100.00);
        Wallet wallet_02 = new Wallet(100.00);

        assertThrows(InvalidAmountException.class, () -> {
            wallet_02.transferFunds(wallet_01, -10);
        });
    }

}
