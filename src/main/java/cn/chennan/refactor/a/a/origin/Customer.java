package cn.chennan.refactor.a.a.origin;

import java.util.ArrayList;
import java.util.List;

/**
 * 顾客
 *
 * @author cn
 * @date 2020-07-17 20:56
 */
public class Customer {
    private String name;
    private List<Rental> rentals = new ArrayList();

    public Customer(String name) {
        this.name = name;
    }

    public void addRental(Rental rental) {
        rentals.add(rental);
    }

    public String getName() {
        return name;
    }

    public String statement() {
        double totalAmount = 0;
        int frequentRenterPoints = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("Rental Record for " + getName() + "\n");
        for (Rental rental : rentals) {
            double thisAmount = 0;
            switch (rental.getMovie().getPriceCode()) {
                case Movie.REGULAR:
                    thisAmount += 2;
                    if (rental.getDaysRented() > 2) {
                        thisAmount += (rental.getDaysRented() - 2) * 1.5;
                    }
                    break;
                case Movie.NEW_RELEASE:
                    thisAmount += rental.getDaysRented() * 3;
                    break;
                case Movie.CHILDRENS:
                    thisAmount += 1.5;
                    if (rental.getDaysRented() > 3) {
                        thisAmount += (rental.getDaysRented() - 3) * 1.5;
                    }
                    break;
            }
            // add frequent renter points
            frequentRenterPoints++;
            // add bonus for a two day new release rental
            if ((rental.getMovie().getPriceCode() == Movie.NEW_RELEASE) &&
                    rental.getDaysRented() > 1) {
                frequentRenterPoints++;
            }
            // show figures for this rental
            sb.append(String.format("\t%s\t%d\n", rental.getMovie().getTitle(), thisAmount));
            totalAmount += thisAmount;
        }
        // add footer lines
        sb.append(String.format("Amount owed is %lf\n", totalAmount));
        sb.append(String.format("You earned %d frequent renter points", frequentRenterPoints));
        return sb.toString();
    }
}
