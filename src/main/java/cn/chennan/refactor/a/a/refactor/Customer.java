package cn.chennan.refactor.a.a.refactor;

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

    /**
     * 分解并充足statement()
     * 1. 任何不会被修改的变量都可以被当成参数传入新的函数, 至于被修改的变量就需要格外小心
     *    thisAmount是个临时变量, 器值在每次循环起始处都会被设为0, 并且在switch语句之前不会改变, 因此可以吧函数的返回值复制给它
     * @return
     */
    public String statement() {
        StringBuilder sb = new StringBuilder();
        sb.append("Rental Record for " + getName() + "\n");
        for (Rental rental : rentals) {
            double thisAmount = 0;
            /**
             * 5. 使用新的方法即可
             * 6. 但是发现thisAmount变得多余了, 去掉thisAmount
             */
//            thisAmount = amountFor(rental);
//            thisAmount = rental.getCharge();
            /**
             * 7. 提炼常客积分计算代码
             */
            // show figures for this rental
            sb.append(String.format("\t%s\t%d\n", rental.getMovie().getTitle(), rental.getCharge()));
        }
        // add footer lines
        sb.append(String.format("Amount owed is %lf\n", getTotalCharge()));
        sb.append(String.format("You earned %d frequent renter points", getTotalFrequentRenterPoints()));
        return sb.toString();
    }

    /**
     * 8. 去掉临时变量
     * @return
     */
    private double getTotalCharge() {
        double result = 0;
        for (Rental rental : rentals) {
            result += rental.getCharge();
        }
        return result;
    }
    private double getTotalFrequentRenterPoints() {
        double result = 0;
        for (Rental rental : rentals) {
            result += rental.getFrequentRenterPoints();
        }
        return result;
    }

    /**
     * 2. 修改方法内的某些变量名称, 好的代码应该清楚表达出自己的功能, 变量名称是代码清晰的关键.
     * 4. 只要改变这个函数内容, 是他委托调用新的函数即可
     * @param rental
     * @return
     */
    private double amountFor(Rental rental) {
        return rental.getCharge();
    }
}
