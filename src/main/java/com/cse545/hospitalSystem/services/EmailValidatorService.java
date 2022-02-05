package com.cse545.hospitalSystem.services;

import java.util.function.Predicate;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class EmailValidatorService implements Predicate<String> {

    @Override
    public boolean test(String t) {
      //TODO: regex to validate email
        return true;
    }

}
