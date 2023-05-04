package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class DemoController {
    @Autowired
    MessageSource messageSource;

    public void changeLocal(Locale locale){
        LocaleContextHolder.setLocale(locale);
    }

    @GetMapping("/message")
    public void message() {
        changeLocal(Locale.FRANCE);
        String s1 = messageSource.getMessage("error.message", null, LocaleContextHolder.getLocale());
        System.out.println(s1);
    }
}
