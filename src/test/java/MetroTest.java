import exceptions.InsufficientBalanceException;
import exceptions.InvalidAmountException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MetroTest {

    @Test
    void adultCheckInShouldBeCharged200() throws InvalidAmountException, InsufficientBalanceException {
        MetroCard mc1 = new MetroCard("MC1");
        mc1.credit(200);
        Metro metro = new Metro();
        Station central = Station.central();

        metro.checkin(mc1,PassengerType.Adult, central);

        assertEquals(200,metro.collectionAt(central));
    }

    @Test
    void SeniorCitizenCheckInShouldBeCharged100() throws InvalidAmountException, InsufficientBalanceException {
        MetroCard mc1 = new MetroCard("MC1");
        mc1.credit(200);
        Metro metro = new Metro();
        Station central = Station.central();

        metro.checkin(mc1,PassengerType.SeniorCitizen,central);

        assertEquals(100,metro.collectionAt(central));
    }

    @Test
    void KidsCheckInShouldBeCharged50() throws InvalidAmountException, InsufficientBalanceException {
        MetroCard mc1 = new MetroCard("MC1");
        mc1.credit(200);
        Metro metro = new Metro();
        Station central = Station.central();

        metro.checkin(mc1,PassengerType.kids,central);

        assertEquals(50,metro.collectionAt(central));
    }

    @Test
    void adultCheckInShouldBeCharged200AtAirportAndNotChargedToCentral() throws InvalidAmountException, InsufficientBalanceException {
        MetroCard mc1 = new MetroCard("MC1");
        mc1.credit(200);
        Metro metro = new Metro();
        Station airport = Station.airport();
        Station central = Station.central();

        metro.checkin(mc1,PassengerType.Adult, airport);

        assertEquals(200,metro.collectionAt(airport));
        assertEquals(0,metro.collectionAt(central));
    }

    @Test
    void travelChargeToBeFiftyPercentForReturnJourney() throws InvalidAmountException, InsufficientBalanceException {
        MetroCard mc1 = new MetroCard("MC1");
        mc1.credit(400);
        Metro metro = new Metro();
        Station airport = Station.airport();
        Station central = Station.central();

        metro.checkin(mc1,PassengerType.Adult, airport);
        metro.checkin(mc1,PassengerType.Adult, airport);

        assertEquals(300,metro.collectionAt(airport));
        assertEquals(100,metro.discountAt(airport));
        assertEquals(0,metro.collectionAt(central));
    }

    @Test
    void metroSummary() {

    }
}
