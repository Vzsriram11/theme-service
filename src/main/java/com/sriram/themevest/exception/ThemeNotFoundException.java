package com.sriram.themevest.exception;

public class ThemeNotFoundException
        extends RuntimeException {

    public ThemeNotFoundException(Long id) {
        super("Theme not found with id " + id);
    }
}
