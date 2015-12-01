# Description
	A simple REST implementation of Foreign Exchange Rate Service based on spring boot.
	Exchange rates are read from https://sdw-wsrest.ecb.europa.eu/.

#Java version
	java version "1.8.0_65"
	Java(TM) SE Runtime Environment (build 1.8.0_65-b17)
	Java HotSpot(TM) 64-Bit Server VM (build 25.65-b01, mixed mode)

#Maven
	Apache Maven 3.2.3 (33f8c3e1027c3ddde99d3cdebad2656a31e8fdf4; 2014-08-11T22:58:10+02:00)
	Default locale: en_US, platform encoding: Cp1252
	OS name: "windows 7", version: "6.1", arch: "amd64", family: "windows"

#How to run:
	java -jar fers-service-1.0-SNAPSHOT.jar
	or
	mvn spring-boot:run
	or 
	from IDE run the com.kazi.fers.FERApplication class

#How to use:
	There is a REST endpoint registered called 'fers'. It consumes GET request parameters: currency symbol and date in format: 'yyyy-MM-dd'.
	The service returns XML with an exchange rate details or error message.

#Request format 
	protocol://server:port/fers/currency/date

#Sample valid request:
	http://localhost:8080/fers/PLN/2015-11-30
	http://localhost:8080/fers/CHF/2015-11-22

#Sample valid response:
	<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
	<ExchangeRate>
		<currency>PLN</currency>
		<date>2015-11-30</date>
		<rate>4.2721</rate>
	</ExchangeRate>

#Sample error response:
	<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
	<ExchangeRate>
		<error>Given date: 2014-11-28 is out of the range: [90 days ago] - today</error>
	</ExchangeRate>
