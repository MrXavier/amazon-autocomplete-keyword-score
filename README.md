# amazon-autocomplete-keyword-score
Project to estimate keyword score of amazon autocomplete API.

## Usage In Local Machine

###### Requirements
```
JDK - Java version 1.8.

Any Java IDE with support Maven.

Maven for build and dependecies.


###### After download the code, to install the application:
$ mvn clean package

###### To run the application the maven command:
$ mvn spring-boot:run

###### To get the keyword score
GET http://localhost:8080/estimate?keyword=iphone+charger
```

## Usage In Local Machine

a. What assumptions did you make?
	The keyword can have more than one word, in other words its a sentence. So "iphone x charger" is one keyword.
	The order of the returned keywords from Amazon API are sorted by search volume.

b. How does your algorithm work?
	Briefly it takes the input keyword and search for each prefix of this keyword in Amazon Autocomplete API. Then it processes the resulted suggestions and calculate a estimation score for the keyword.

The process to calculate the score takes the index of the first occurrence of the keyword in the resulted suggestions and send it to a score calculator. Then the score will be compute based on the parameters: the keyword lenth and the index of the first occurrence of the keyword. The score keyword starts with 100 and the score calculator will descrease it based on the parameters. 

The calculation use the proportion of the score (100) by letter of the keyword (100 / length), since we are computing the prefixes each letter at time. Then it used the index to calculate how much of this proportion it will decrease from the score. If the index is the last in the resulted suggestion then the calculation decrease full proportion from the score. If index is the middle, 50% of the proportion, and so on.

If the index of the first occurence of the keyword in the suggestion list, it means that following letters will not affect the result, so we finish the processing.

c. Do you think the (*hint) that we gave you earlier is correct and if so - why?
	The hint is not correct. The 10 returned words are ordered by search-volume in a descenant way (the firsts elements have high search-volume than the lower elements). Additionally doesn't make sense from amazon to send an unsorted order of keyword if the intention is to show the most searched keywords in the top of the results. Also if the order is insignificant we should different order results for the same prefix, which is not true.


d. How precise do you think your outcome is and why?
	I think the outcome is close to the Amazon score because it closely follows the real result from Amazon.com website search suggestions. This was concluded by manual tests.

If you search for "iphone charger" you got a score of 100 and it will first appear when you search for "iphone" and it's prefixes, like "i", "ip", "iph", and so on. If you search for "smartphone" you will got a score of 50, which is the exact number of suggestions it is returned with this keyword (this is because the keyword has 10 letters resulting in 100 suggested results).

Those are some hypotesis that can proof that the solution is precise.

# Future improvements
1. Improve Amazon API request time
2. Organize the code better using Design Patterns.

