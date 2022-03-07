package com.example.HousingAPI;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @RequestMapping("/housing-data/{field}/{statistic}/{zipCode}/{startDate}/{endDate}")
    public String fetchData(
            @PathVariable("field") String field,
            /*field: price or squareFeet*/
            @PathVariable("statistic") String statistic,
            /*statistic: "min", "max", "average", "sum", "range"*/
            @PathVariable("zipCode") Optional<Integer> zipCode,
            @PathVariable("startDate") Optional<String> startDate,
            @PathVariable("endDate") Optional<String> endDate
            ) throws IOException {
        return processData(field,statistic,zipCode,startDate,endDate);
    }

    public String processData(String field, String statistic, Optional<Integer> zipCode, Optional<String> startDate, Optional<String> endDate) throws IOException {
        List<int[]> AllHousingData = new ArrayList<>();
        int[] houseData = new int[4]; /*0 - price, 1 - sqt, 2 - date, 3 - zipcode*/
        int sDate = Integer.parseInt(startDate.get().substring(8,10));
        int eDate = Integer.parseInt(endDate.get().substring(8,10));
        int minPrice = 2147483647;
        int maxPrice = 0;
        int minSquareFeet = 2147483647;
        int maxSquareFeet = 0;
        int averagePrice = 0;
        int averageSquareFeet = 0;
        int sumPrice = 0;
        int sumSquareFeet = 0;
        boolean firstLoop = true;


        Reader in = new FileReader("real-estate-data.csv");
        Iterable<CSVRecord> records = CSVFormat.RFC4180.parse(in);

        //Read in all houseing date, and saved the essential date into a list of array
        for (CSVRecord record : records) {
            if(firstLoop){
                firstLoop = false;
                continue;
            }
            houseData[0] = Integer.parseInt(record.get(9));//price
            houseData[1] = Integer.parseInt(record.get(6));//sqt
            houseData[2] = Integer.parseInt(record.get(8).substring(8,10));//date
            houseData[3] = Integer.parseInt(record.get(2));//zipcode

            AllHousingData.add(houseData);

        }

        //Filter out housing date from the list
        for(int i = 0; i < AllHousingData.size(); i++){
            if(zipCode.isPresent()){
                if(AllHousingData.get(i)[3] != zipCode.get()){
                    AllHousingData.remove(i);
                    i--;
                }
            }
            if(startDate.isPresent() || endDate.isPresent()){
                if(startDate.isPresent() && endDate.isPresent()){
                    if(AllHousingData.get(i)[2] <= sDate && AllHousingData.get(i)[2] >= eDate){
                        AllHousingData.remove(i);
                        i--;
                    }
                }else if(startDate.isPresent()){
                    if(AllHousingData.get(i)[2] <= sDate){
                        AllHousingData.remove(i);
                        i--;
                    }
                }else if(endDate.isPresent()){
                    if(AllHousingData.get(i)[2] >= eDate){
                        AllHousingData.remove(i);
                        i--;
                    }
                }
            }
        }

        //Calculate using whatever housing data are remained
        for(int i = 0; i < AllHousingData.size(); i++){
            sumPrice += AllHousingData.get(i)[0];
            sumSquareFeet += AllHousingData.get(i)[1];
            if(AllHousingData.get(i)[0]<minPrice) minPrice = AllHousingData.get(i)[0];
            if(AllHousingData.get(i)[0]>maxPrice) maxPrice = AllHousingData.get(i)[0];
            if(AllHousingData.get(i)[1]<minSquareFeet) minSquareFeet = AllHousingData.get(i)[1];
            if(AllHousingData.get(i)[1]>maxSquareFeet) maxSquareFeet = AllHousingData.get(i)[1];
        }

        int rangePrice = maxPrice - minPrice;
        int rangeSquareFeet = maxSquareFeet - minSquareFeet;

        if(field.equals("price")){
            if(statistic.equals("min")){
                return "result: " + minPrice;
            }else if(statistic.equals("max")){
                return "result: " + maxPrice;
            }else if(statistic.equals("average")){
                return "result: " + (sumPrice/AllHousingData.size());
            }else if(statistic.equals("sum")){
                return "result: " + sumPrice;
            }else if(statistic.equals("range")){
                return "result: " + rangePrice;
            }else{
                return "Please enter the correct statistic";
            }
        }else if(field.equals("squareFeet")){
            if(statistic.equals("min")){
                return "result: " + minSquareFeet;
            }else if(statistic.equals("max")){
                return "result: " + maxSquareFeet;
            }else if(statistic.equals("average")){
                return "result: " + (sumSquareFeet/AllHousingData.size());
            }else if(statistic.equals("sum")){
                return "result: " + sumSquareFeet;
            }else if(statistic.equals("range")){
                return "result: " + rangeSquareFeet;
            }
        }else{
            return "Please enter the correct field";
        }

        return "";
    }

}
