# amazon-autocomplete-keyword-score
Project to estimate keyword score of amazon autocomplete API.

a. What assumptions did you make?
	The keyword can have more than one word. In other words its a sentence. So "iphone" and "iphone x" are two different keywords.


b. How does your algorithm work?
	

c. Do you think the (*hint) that we gave you earlier is correct and if so - why?
	The hint is not correct. The 10 returned words are ordered by search-volume in a descenant way (the firsts elements have high search-volume than the lower elements). Additionally doesn't make sense from amazon to send an unsorted order of keyword if the intention is to show the most searched keywords in the top of the results. Also if the order is insignificant we should different order results for the same prefix, which is not true.
d. How precise do you think your outcome is and why?
	.


# Future improvements
1. Use the order of appearance of the keyword in the prefixes searches to calculate the score more precisely
2. Improve the occurrencies match to accept multiple words
3. Improve Amazon API request time
4. 
