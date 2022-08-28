package main.service;

import main.model.DatePosition;
import main.model.Price;

public class PriceDateAnalizator {

    public static DatePosition getPosition(Price oldPrice, Price newPrice) {
        if (newPrice.getDateBegin().isBefore(oldPrice.getDateBegin()) && newPrice.getDateEnd().isBefore(oldPrice.getDateBegin())) {
            return DatePosition.BEFORE;
        } else if (newPrice.getDateBegin().isBefore(oldPrice.getDateBegin()) && newPrice.getDateEnd().isAfter(oldPrice.getDateBegin())
                && newPrice.getDateEnd().isBefore(oldPrice.getDateEnd())) {
            return DatePosition.AT_THE_BEGIN;
        } else if (newPrice.getDateBegin().isAfter(oldPrice.getDateBegin()) && newPrice.getDateEnd().isBefore(oldPrice.getDateEnd())) {
            return DatePosition.IN_THE_MIDDLE;
        } else if (newPrice.getDateBegin().isAfter(oldPrice.getDateBegin()) && newPrice.getDateBegin().isBefore(oldPrice.getDateEnd())) {
            return DatePosition.IN_THE_END;
        } else if (newPrice.getDateBegin().isAfter(oldPrice.getDateEnd())) {
            return DatePosition.AFTER;
        } else return DatePosition.OVER;
    }
}
