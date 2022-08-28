package main.service;

import main.model.Price;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class PriceFilter {

    public static List<Price> filter(Collection<Price> oldPrices, Collection<Price> newPrices) {
        return oldPrices.stream()
                .filter(i -> newPrices.stream().map(e -> e.getProductCode()).collect(Collectors.toSet()).contains(i.getProductCode()))
                .toList();
    }
}
