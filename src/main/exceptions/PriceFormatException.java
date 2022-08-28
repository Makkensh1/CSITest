package main.exceptions;

import java.io.IOException;

public class PriceFormatException extends IOException {

    public PriceFormatException(String message) {
        super(message);
    }
}
