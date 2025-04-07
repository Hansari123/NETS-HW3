Overall Assumptions: Users will not enter any "bad"/"faulty"
inputs, "table name" is the name found at the top left part
of the table, Summer Olympics were cancelled in 1916, 1940, 1944.

Question 1: My program finds the table named "Sport" on the base Wikipedia
link, iterates over the rows of the table, and adds the names of all the sports
into a set (to avoid duplicates). Then, I iterate over all the elements of the
set print only those which start with the letter of interest. I did it this way
because I wanted to be able to see if all sports were taken account of.
Assumptions:
1. The question is referring to "Summer Olympic" sports
1. Users can enter both uppercase and lowercase letters (Should also be able to press "3")
2. The table named "Sport" will hold the names of all Summer Olympic sports (past and present)
3. The first column within each row will hold the name of the sport
Answer: Canoeing, Cricket, Croquet, Cycling

Question 2: I began by iterating over all the links on the base Wikipedia page and
searched for the link called "List of participating nations at the Summer Olympic Games".
I went to the page of that link. Then, I found a table with the name "A". I iterated over
the rows of the table and checked if the "bgcolor" was "#e0e0e0". If so, I added the name
of the country to a set. Once I finished iterating over the rows of the table, I printed
out all the obsolete nations.
Assumptions:
1. Link called "List of participating nations at the Summer Olympic Games" will retain that name
2. Table named "A" will not change names
3. The "bgcolor" which represents the obsolete nations will remain "#e0e0e0"
4. The name of the country of interest will always be in the first column
5. ROC is a valid answer to this question because we can't find more info.
Answer: Australasia, Bohemia, British West Indies, Czechoslovakia,
East Germany, Independent Olympic Participants, Individual Neutral Athletes,
Malaya, Netherlands Antilles, North Borneo, North Yemen, ROC, Republic of China,
Russian Empire, Saar, Serbia and Montenegro, South Yemen, Soviet Union, Unified Team,
United Team of Germany, West Germany, Yugoslavia

Question 3: I begin by iterating over the "Olympiad" table and getting the link associated
to the year of interest from the third column. I go to the page and search for the table
named "Rank". Then, I iterate over each row and check if the number of silver medals won by
the current country is >= the number of interest. If so, I add the country name to a set.
Once I iterate over the entire table, I output the country names.
Assumptions:
1. "Olympiad" table will retain its name.
2. 2nd column of the Olympiad table will link to the "[Year] at the Summer Olympics page"
3. The "Rank" table will hold all the medals given for that year
4. The 3rd column will hold the number of silver medals (makes sense bceause
the name of the country must be first. Then even if we switch the order of gold or bronze,
silver should always be in the middle logically).
Answer: Australia, China, France, Germany, Great Britain, Italy, Japan, Russia,
United States

Question 4: I begin by iterating over the "Olympiad" table and getting the link associated
to the year of interest from the third column. I go to the page and search for the table
named "Date". For each row, I select the entry in the 4th column and add it to my set of
countries. I output this set once I finish iterating over the table.
Assumptions:
1. "Olympiad" table will retain its name.
2. 2nd column of the Olympiad table will link to the "[Year] at the Summer Olympics page"
3. The "Date" table will hold all the information needed for podium sweeps of a given year
4. The 4th column of the "Date" table will hold the name of the countries that had the sweep
5. If no podium sweep table is found then that year had no podium sweeps.
Answer: China, Italy, Jamaica

Question 5: I first iterated over the links on the base Wikipedia page and found the one
that said "All-time Olympic Games medal table" and navigated to the page. Then, I iterated
over the "Team" table and found the row which corresponded to the country of interest. I
navigated to the corresponding link. From there, I went to the tables called "Sport" and
found the row which corresponded to the sport of interest. From there, I got the total
medals from the last row of the table.
Assumptions:
1. The name of the "All-time Olympic Games medal table" link will not change
2. The name of the "Team" table will not change
3. The first column will hold the corresponding page of a given country's medals
4. The "Sport" tables will have the name of the sports and the number of the medals
the country won
5. The last row of the "Sport" table will have the total medals for the given sport.
Answer: 13

Question 6:
Assumptions:
Answer: 20

Question 7:
Assumptions:
Answer: 2

Question 8:
Assumptions:
Answer (Default input: 2012): The city was London and the current mayor is Sadiq Khan.




 // // UNITED STATES NOT US Q6 // Q7 NOT GOING TO ENTER YEAR BEFORE OLYMPICS STARTED


// HANDLE FRANCE IN Q7 France and Monocco// VALID SILVER MEDALS
valid year for q7!


Q8 LORD MAYOR


 //IRELAND PART OF THE UK???????????????????????Q7 "AND"


 //ASSUME FIRST TABLE WITH HEADQUARTERS IS THE ONE OR IF ANY HEAD GOOD


 // fifa+ is an extension of fifa so its counted (ASSUMPTION)


 / ASSUMPTION: VALID COUNTRY GIVEN first box!!!!! what counts!
         // UNITED KINGDOM!!!!!!!!!!
         // HEADQUARTER THEN RIGHT AFTER IS THE COUNTRY

         // Q8: SOME CITIES CANT GET MAYORS ETC