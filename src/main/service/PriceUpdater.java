package main.service;

import main.exceptions.PriceFormatException;
import main.model.DatePosition;
import main.model.Price;

import java.util.*;

public class PriceUpdater {

    public static Set<Price> updatePrices(Set<Price> newPrices, Set<Price> oldPrices) throws PriceFormatException {
        if (newPrices == null || newPrices.size() == 0) {
            return oldPrices;
        }
        if (oldPrices == null || oldPrices.size() == 0) {
            return newPrices;
        }
        PriceFormatValidator.validate(newPrices);
        List<Price> pricesToUpdate = PriceFilter.filter(oldPrices, newPrices);
        Set<Price> result = new TreeSet<>(oldPrices);
        result.removeAll(pricesToUpdate);
        Iterator<Price> newPriceIterator = newPrices.iterator();
        Set<Price> finalPrices = new TreeSet<>();
        finalPrices.addAll(pricesToUpdate);
        Set<Price> resultPrices = new TreeSet<>();
        List<Price> pricesToDelete = new ArrayList<>();
        while (newPriceIterator.hasNext()) {
            Price newPrice = newPriceIterator.next();
            boolean isNeedToDelete = false;
            resultPrices.addAll(finalPrices);
            Iterator<Price> resIterator = resultPrices.iterator();
            while (resIterator.hasNext()) {
                Price price = resIterator.next();
                if (newPrice.checkProductCodeAndDepart(price)) {
                    if (PriceDateAnalizator.getPosition(price, newPrice) == DatePosition.BEFORE) {
                        isNeedToDelete = true;
                        finalPrices.add(newPrice);
                    } else if (PriceDateAnalizator.getPosition(price, newPrice) == DatePosition.AT_THE_BEGIN) {
                        if (price.getValue() == newPrice.getValue()) {
                            isNeedToDelete = true;
                            newPrice.setDateEnd(price.getDateEnd());
                            finalPrices.add(newPrice);
                        } else {
                            price.setDateBegin(newPrice.getDateEnd().plusSeconds(1));
                            isNeedToDelete = true;
                            finalPrices.add(newPrice);
                        }
                    } else if (PriceDateAnalizator.getPosition(price, newPrice) == DatePosition.IN_THE_MIDDLE) {
                        if (price.getValue() == newPrice.getValue()) {
                            isNeedToDelete = true;
                            finalPrices.add(price);
                        } else {
                            isNeedToDelete = true;
                            Price priceAfterNewPrice = new Price(newPrice.getProductCode(), newPrice.getNumber(), newPrice.getDepart(),
                                    newPrice.getDateEnd().plusSeconds(1), price.getDateEnd(), price.getValue());
                            Price priceBeforeNewPrice = new Price(newPrice.getProductCode(), newPrice.getNumber(), newPrice.getDepart(),
                                    price.getDateBegin(), newPrice.getDateBegin().minusSeconds(1), price.getValue());
                            finalPrices.add(priceBeforeNewPrice);
                            finalPrices.add(newPrice);
                            finalPrices.add(priceAfterNewPrice);
                            pricesToDelete.add(price);
                        }
                    } else if (PriceDateAnalizator.getPosition(price, newPrice) == DatePosition.IN_THE_END) {
                        if (price.getValue() == newPrice.getValue()) {
                            isNeedToDelete = true;
                            price.setDateEnd(newPrice.getDateEnd());
                            finalPrices.add(price);
                        } else {
                            isNeedToDelete = true;
                            price.setDateEnd(newPrice.getDateBegin().minusSeconds(1));
                            finalPrices.add(price);
                            finalPrices.add(newPrice);
                        }
                    } else if (PriceDateAnalizator.getPosition(price, newPrice) == DatePosition.AFTER) {
                        isNeedToDelete = true;
                        finalPrices.add(newPrice);
                    } else if (PriceDateAnalizator.getPosition(price, newPrice) == DatePosition.OVER) {
                        isNeedToDelete = true;
                        finalPrices.add(newPrice);
                        pricesToDelete.add(price);
                    }
                }
            }
            if (isNeedToDelete) {
                newPriceIterator.remove();
            }
        }
        finalPrices.removeAll(pricesToDelete);
        result.addAll(newPrices);
        result.addAll(finalPrices);
        return result;
    }
}
