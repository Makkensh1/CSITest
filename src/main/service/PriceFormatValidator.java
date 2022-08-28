package main.service;

import main.exceptions.PriceFormatException;
import main.model.Price;

import java.util.Collection;

public class PriceFormatValidator {

    public static void validate(Collection<Price> prices) throws PriceFormatException {
        for (Price price : prices) {
            if (price == null) {
                throw new PriceFormatException("Price collection contains null");
            } else if (price.getValue() < 0) {
                throw new PriceFormatException("Price with product code " + price.getProductCode() + " less than 0");
            } else if (price.getDateBegin().isAfter(price.getDateEnd())) {
                throw new PriceFormatException("Price with product code " + price.getProductCode() + " has invalid dates");
            }
        }
    }
}
