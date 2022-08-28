package main.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;


@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Price implements Comparable<Price> {


    private Long id;

    private String productCode;

    private int number;

    private int depart;

    private LocalDateTime dateBegin;

    private LocalDateTime dateEnd;

    private long value;

    public Price(String productCode, int number, int depart, LocalDateTime dateBegin, LocalDateTime dateEnd, long value) {
        this.productCode = productCode;
        this.number = number;
        this.depart = depart;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.value = value;
    }


    @Override
    public int compareTo(Price o) {
        if (Long.valueOf(productCode).equals(Long.valueOf(o.getProductCode()))) {
            if (depart == o.getDepart()) {
                if (dateBegin.equals(o.dateBegin)) {
                    return dateEnd.compareTo(o.dateEnd);
                }
                return dateBegin.compareTo(o.getDateBegin());
            }
            return Integer.valueOf(depart).compareTo(o.getDepart());
        }
        return Long.valueOf(productCode).compareTo(Long.valueOf(o.getProductCode()));
    }

    public boolean checkProductCodeAndDepart(Price p) {
        if (this.getProductCode().equalsIgnoreCase(p.getProductCode()) && this.getDepart() == p.getDepart() && this.number == p.getNumber()) {
            return true;
        } else return false;
    }


}
