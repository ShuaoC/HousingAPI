package com.example.HousingAPI;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
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

    public String housingData(String field, String statistic, Integer zipCode, String startDate, String endDate) throws IOException {
        List<int[]> AllHousingData = new ArrayList<>();
        int[] houseData = new int[4]; /*0 - price, 1 - sqt, 2 - date, 3 - zipcode*/
        int minPrice;
        int maxPrice;
        int minSquareFeet;
        int maxSquareFeet;
        int averagePrice;
        int averageSquareFeet;
        int sumPrice;
        int sumSquareFeet;
        boolean firstLoop = true;


        Reader in = new FileReader("real-estate-data.csv");
        Iterable<CSVRecord> records = CSVFormat.RFC4180.parse(in);

        for (CSVRecord record : records) {
            if(firstLoop){
                firstLoop = false;
                continue;
            }
            houseData[0] = Integer.parseInt(record.get(9));
            houseData[1] = Integer.parseInt(record.get(6));
            houseData[2] = Integer.parseInt(record.get(8).substring(8,10));
            houseData[3] = Integer.parseInt(record.get(2));

            AllHousingData.add(houseData);

        }

        int rangePrice;
        int rangeSquareFeet;

        if(field.equals("price")){
            if(statistic)
        }else if(field.equals("statistic")){

        }else{
            return "Please enter the correct parameter";
        }

        return "";
    }

}
