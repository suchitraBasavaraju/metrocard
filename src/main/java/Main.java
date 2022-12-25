import exceptions.InsufficientBalanceException;
import exceptions.InvalidAmountException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) {
        Station central = Station.central();
        Station airport = Station.airport();
        Metro metro = new Metro();
        List<MetroCard> cards = new ArrayList<>();

        List<String> lines = null;
        try {
            System.out.println("Enter file path");
            Scanner in = new Scanner(System.in);
            String filePath = in.nextLine();

            lines = readLinesFromFile(filePath);
        } catch (IOException e) {
            System.out.println("Cannot Read from File");
            exit(0);
        }
        for (String line : lines) {
            if (line.contains("BALANCE")) {
                MetroCard metroCard = null;
                try {
                    metroCard = updateBalanceToCard(line);
                } catch (InvalidAmountException e) {
                    System.out.println("Invalid amount");
                    exit(0);
                }
                cards.add(metroCard);
            }
            if (line.contains("CHECK_IN")) {
                String[] lineSplit = line.split(" ");
                String cardNumber = lineSplit[1];
                MetroCard metroCard = null;
                for (MetroCard card : cards) {
                    metroCard = card.hasNumber(cardNumber);
                    if (metroCard != null) {
                        break;
                    }
                }
                if (metroCard == null) {
                    System.out.println("invalid card");
                    exit(0);
                }
                PassengerType type = PassengerType.valueOf(lineSplit[2]);
                Station station = line.contains("CENTRAL") ? central : airport;
                try {
                    metro.checkIn(metroCard, type, station);
                } catch (InvalidAmountException e) {
                    System.out.println("Invalid Amount");
                } catch (InsufficientBalanceException e) {
                    System.out.println("Insufficient Amount");
                }
            }

            if (line.contains("PRINT_SUMMARY")) {
                double airportCollection = metro.collectionAt(airport);
                double airportDiscount = metro.discountAt(airport);
                double centralCollection = metro.collectionAt(central);
                double centralDiscount = metro.discountAt(central);
                System.out.println("TOTAL_COLLECTION" + " CENTRAL " + centralCollection + " " + centralDiscount);
                System.out.println("PASSENGER_TYPE_SUMMARY");
                Map<PassengerType, Integer> passengerSummaryAtCentral = metro.passengersAt(central);
                passengerSummaryAtCentral.forEach((key, value) -> print(key, value));
                System.out.println("TOTAL_COLLECTION" + " AIRPORT " + airportCollection + " " + airportDiscount);
                System.out.println("PASSENGER_TYPE_SUMMARY");
                Map<PassengerType, Integer> passengerSummaryAtAirport = metro.passengersAt(airport);
                passengerSummaryAtAirport.forEach((key, value) -> print(key, value));
            }
        }
    }

    private static void print(PassengerType key, Integer value) {
        if (value != 0) {
            System.out.println(key + " " + value);
        }
    }

    private static MetroCard updateBalanceToCard(String line) throws InvalidAmountException {
        String[] lineSplit = line.split(" ");
        String cardNumber = lineSplit[1];
        double amount = Double.parseDouble(lineSplit[2]);
        MetroCard metroCard = new MetroCard(cardNumber);
        metroCard.credit(amount);
        return metroCard;
    }


    private static List<String> readLinesFromFile(String pathname) throws IOException {
        File file = new File(pathname);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        List<String> lines = new ArrayList<>();
        while ((st = br.readLine()) != null)
            lines.add(st);
        return lines;
    }
}

