package com.example.HousingAPI;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HousingAPIController {
    @GetMapping("/books")
    public String fetchBooks() {
        return "Success";
    }


}
