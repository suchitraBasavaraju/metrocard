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
    void totalCollectionAtAirport_And_Airport_With_Varied_Checkin1() throws InvalidAmountException, InsufficientBalanceException {
        MetroCard mc1 = new MetroCard("MC1");
        MetroCard mc2 = new MetroCard("MC2");
        MetroCard mc3 = new MetroCard("MC3");
        MetroCard mc4 = new MetroCard("MC4");
        MetroCard mc5 = new MetroCard("MC5");
        mc1.credit(600);
        mc2.credit(500);
        mc3.credit(50);
        mc4.credit(50);
        mc5.credit(200);
        Metro metro = new Metro();
        Station airport = Station.airport();
        Station central = Station.central();

        metro.checkin(mc1,PassengerType.Adult, central);
        metro.checkin(mc2,PassengerType.SeniorCitizen, central);
        metro.checkin(mc1,PassengerType.Adult, airport);
        metro.checkin(mc3,PassengerType.kids, airport);
        metro.checkin(mc4,PassengerType.Adult, airport);
        metro.checkin(mc5,PassengerType.kids, airport);

        assertEquals(300,metro.collectionAt(central));
        assertEquals(0,metro.discountAt(central));
        assertEquals(403,metro.collectionAt(airport));
        assertEquals(100,metro.discountAt(airport));

    }

    @Test
    void totalCollectionAtAirport_And_Airport_With_Varied_Checkin2() throws InvalidAmountException, InsufficientBalanceException {
        MetroCard mc1 = new MetroCard("MC1");
        MetroCard mc2 = new MetroCard("MC2");
        MetroCard mc3 = new MetroCard("MC3");
        MetroCard mc4 = new MetroCard("MC4");
        mc1.credit(400);
        mc2.credit(100);
        mc3.credit(50);
        mc4.credit(50);
        Metro metro = new Metro();
        Station airport = Station.airport();
        Station central = Station.central();

        metro.checkin(mc1,PassengerType.SeniorCitizen, airport);
        metro.checkin(mc2,PassengerType.kids, airport);
        metro.checkin(mc3,PassengerType.Adult, central);
        metro.checkin(mc1,PassengerType.SeniorCitizen, central);
        metro.checkin(mc3,PassengerType.Adult, airport);
        metro.checkin(mc3,PassengerType.Adult, central);

        assertEquals(457,metro.collectionAt(central));
        assertEquals(50,metro.discountAt(central));
        assertEquals(252,metro.collectionAt(airport));
        assertEquals(100,metro.discountAt(airport));
    }

}
