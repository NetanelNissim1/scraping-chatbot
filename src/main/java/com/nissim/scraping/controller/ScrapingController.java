package com.nissim.scraping.controller;

import com.nissim.scraping.service.AnyWhoService;
import com.nissim.scraping.service.SpokeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@Service
@RestController
@RequestMapping("/bot")
public class ScrapingController {

    @Autowired
    AnyWhoService anyWhoService;

    @Autowired
    SpokeoService searchSpokeo;

    @RequestMapping(value = "/anywho", method = RequestMethod.GET)
    public ResponseEntity<?> getProduct(@RequestParam String firstName, @RequestParam String lastName) throws IOException {
        return new ResponseEntity<>(anyWhoService.searchAnyWho(firstName, lastName), HttpStatus.OK);
    }

    @RequestMapping(value = "/spokeo", method = RequestMethod.GET)
    public ResponseEntity<?> getSpokeo(@RequestParam String firstName, @RequestParam String lastName) throws IOException {
        return new ResponseEntity<>(searchSpokeo.searchSpokeo(firstName, lastName), HttpStatus.OK);
    }
}
