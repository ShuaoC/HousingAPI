package com.example.HousingAPI;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Date;

@SpringBootApplication
public class HousingApiApplication {

	public static void main(String[] args) throws IOException {
		String city;
		boolean firstLoop = true;
		SpringApplication.run(HousingApiApplication.class, args);
		Reader in = new FileReader("real-estate-data.csv");
		Iterable<CSVRecord> records = CSVFormat.RFC4180.parse(in);
		for (CSVRecord record : records){
			if(firstLoop){
				firstLoop = false;
				continue;
			}
			city = record.get(8);
			System.out.print(city.substring(8,10));
		}

	}

	public int x(){
		int y = 0;
		return y;
	}

}
