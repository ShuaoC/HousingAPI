package com.example.HousingAPI;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Optional;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

@RestController
public class HousingAPIController {

    @GetMapping("/housing-data/{field}/{statistic}/{zipCode}/{startDate}/{endDate}")
    public String fetchData(
            @PathVariable("field") String field,
            /*field: price or squareFeet*/
            @PathVariable("statistic") String statistic,
            /*statistic: "min", "max", "average", "sum", "range"*/
            @PathVariable("zipCode") Optional<Integer> zipCode,
            @PathVariable("startDate") Optional<String> startDate,
            @PathVariable("endDate") Optional<String> endDate
            ) {
        return "Success";
    }

    public String housingData() throws IOException {
        int price;
        int squareFeet;

        Reader in = new FileReader("real-estate-data.csv");
        Iterable<CSVRecord> records = CSVFormat.RFC4180.parse(in);


        return "";
    }

}
