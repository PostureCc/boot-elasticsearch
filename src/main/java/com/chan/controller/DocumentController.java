package com.chan.controller;

import com.chan.repo.ExchangeElectricRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DocumentController {

    @Autowired
    private ExchangeElectricRepository exchangeElectricRepository;

    @GetMapping("/test")
    public void test() {
        System.out.println(exchangeElectricRepository.findAll());
    }



}
