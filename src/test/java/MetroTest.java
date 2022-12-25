import exceptions.InsufficientBalanceException;
import exceptions.InvalidAmountException;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MetroTest {

    @Test
    void adultCheckInShouldBeCharged200() throws InvalidAmountException, InsufficientBalanceException {
        MetroCard mc1 = new MetroCard("MC1");
        mc1.credit(200);
        Metro metro = new Metro();
        Station central = Station.central();

        metro.checkIn(mc1,PassengerType.ADULT, central);

        assertEquals(200,metro.collectionAt(central));
    }

    @Test
    void SeniorCitizenCheckInShouldBeCharged100() throws InvalidAmountException, InsufficientBalanceException {
        MetroCard mc1 = new MetroCard("MC1");
        mc1.credit(200);
        Metro metro = new Metro();
        Station central = Station.central();

        metro.checkIn(mc1,PassengerType.SENIOR_CITIZEN,central);

        assertEquals(100,metro.collectionAt(central));
    }

    @Test
    void KidsCheckInShouldBeCharged50() throws InvalidAmountException, InsufficientBalanceException {
        MetroCard mc1 = new MetroCard("MC1");
        mc1.credit(200);
        Metro metro = new Metro();
        Station central = Station.central();

        metro.checkIn(mc1,PassengerType.KID,central);

        assertEquals(50,metro.collectionAt(central));
    }

    @Test
    void adultCheckInShouldBeCharged200AtAirportAndNotChargedToCentral() throws InvalidAmountException, InsufficientBalanceException {
        MetroCard mc1 = new MetroCard("MC1");
        mc1.credit(200);
        Metro metro = new Metro();
        Station airport = Station.airport();
        Station central = Station.central();

        metro.checkIn(mc1,PassengerType.ADULT, airport);

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

        metro.checkIn(mc1,PassengerType.ADULT, airport);
        metro.checkIn(mc1,PassengerType.ADULT, airport);

        assertEquals(300,metro.collectionAt(airport));
        assertEquals(100,metro.discountAt(airport));
        assertEquals(0,metro.collectionAt(central));
    }

    @Test
    void numberOfAdultPassengersTravelledToBe2AtAirport() throws InvalidAmountException, InsufficientBalanceException {
        MetroCard mc1 = new MetroCard("MC1");
        mc1.credit(400);
        Metro metro = new Metro();
        Station airport = Station.airport();

        metro.checkIn(mc1,PassengerType.ADULT, airport);
        metro.checkIn(mc1,PassengerType.ADULT, airport);

        Map<PassengerType, Integer> actual = metro.passengersAt(airport);
        assertEquals(2, actual.get(PassengerType.ADULT));
    }

    @Test
    void differentPassengerTypeSortedInDescendingOrderOfCollection() throws InvalidAmountException, InsufficientBalanceException {
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

        metro.checkIn(mc1,PassengerType.ADULT, central);
        metro.checkIn(mc2,PassengerType.SENIOR_CITIZEN, central);
        metro.checkIn(mc1,PassengerType.ADULT, airport);
        metro.checkIn(mc3,PassengerType.KID, airport);
        metro.checkIn(mc4,PassengerType.ADULT, airport);
        metro.checkIn(mc5,PassengerType.KID, airport);

        Map<PassengerType, Integer> centralPassengerCollectionOrder = metro.passengersAt(central);
        Map<PassengerType, Integer> airportPassengerCollectionOrder = metro.passengersAt(airport);

        assertEquals(1, centralPassengerCollectionOrder.get(PassengerType.ADULT));
        assertEquals(1, centralPassengerCollectionOrder.get(PassengerType.SENIOR_CITIZEN));
        assertEquals(0, centralPassengerCollectionOrder.get(PassengerType.KID));
        assertEquals(2, airportPassengerCollectionOrder.get(PassengerType.ADULT));
        assertEquals(0, airportPassengerCollectionOrder.get(PassengerType.SENIOR_CITIZEN));
        assertEquals(2, airportPassengerCollectionOrder.get(PassengerType.KID));
    }

    @Test
    void differentPassengerTypeSortedInDescendingOrderOfCollection1() throws InvalidAmountException, InsufficientBalanceException {
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

        metro.checkIn(mc1,PassengerType.SENIOR_CITIZEN, airport);
        metro.checkIn(mc2,PassengerType.KID, airport);
        metro.checkIn(mc3,PassengerType.ADULT, central);
        metro.checkIn(mc1,PassengerType.SENIOR_CITIZEN, central);
        metro.checkIn(mc3,PassengerType.ADULT, airport);
        metro.checkIn(mc3,PassengerType.ADULT, central);

        Map<PassengerType, Integer> centralPassengerCollectionOrder = metro.passengersAt(central);
        Map<PassengerType, Integer> airportPassengerCollectionOrder = metro.passengersAt(airport);

        assertEquals(2, centralPassengerCollectionOrder.get(PassengerType.ADULT));
        assertEquals(1, centralPassengerCollectionOrder.get(PassengerType.SENIOR_CITIZEN));
        assertEquals(0, centralPassengerCollectionOrder.get(PassengerType.KID));
        assertEquals(1, airportPassengerCollectionOrder.get(PassengerType.ADULT));
        assertEquals(1, airportPassengerCollectionOrder.get(PassengerType.SENIOR_CITIZEN));
        assertEquals(1, airportPassengerCollectionOrder.get(PassengerType.KID));
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

        metro.checkIn(mc1,PassengerType.ADULT, central);
        metro.checkIn(mc2,PassengerType.SENIOR_CITIZEN, central);
        metro.checkIn(mc1,PassengerType.ADULT, airport);
        metro.checkIn(mc3,PassengerType.KID, airport);
        metro.checkIn(mc4,PassengerType.ADULT, airport);
        metro.checkIn(mc5,PassengerType.KID, airport);

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

        metro.checkIn(mc1,PassengerType.SENIOR_CITIZEN, airport);
        metro.checkIn(mc2,PassengerType.KID, airport);
        metro.checkIn(mc3,PassengerType.ADULT, central);
        metro.checkIn(mc1,PassengerType.SENIOR_CITIZEN, central);
        metro.checkIn(mc3,PassengerType.ADULT, airport);
        metro.checkIn(mc3,PassengerType.ADULT, central);

        assertEquals(457,metro.collectionAt(central));
        assertEquals(50,metro.discountAt(central));
        assertEquals(252,metro.collectionAt(airport));
        assertEquals(100,metro.discountAt(airport));
    }

}
