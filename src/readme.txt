You should open the "Interactive Main" file and press run. From there you
will be able to see the prompts and interact with the program.

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

Question 6: For each row of the "Sport" table, I follow the link corresponding to the sport
name. I then search for the table that contains the words "Governing Body". From there, I
follow the link corresponding to "Governing Body". From there, I search for headquarters and
see if the country I'm searching for appears in the text next to "headquarters". If so, I add
1 to my count and output the total once all sports have been inspected.
Assumptions:
1. "Sport" table will not change (not the name, not the links corresponding to the sports).
2. If "Governing body" is not found, that sport has no governing body
3. United States should be entered when searching for US
4. If we encounter "headquarter" in a table, directly adjacent to it will be the names of the countries of interest
5. If there is no text directly next to the "headquarter" text we will not do anything more
6. We will only be counting each organization once to avoid double counting
7. Whenever we see "headquarter" on a table on the page, the text following will count: For example,
 fifa+ is an extension of fifa so it's counted for France even though it is not on the first table.
Answer: 20

Question 7: I first find the link on the base Wikipedia page called "Torch relays". I follow the link.
Then, I iterate over the "Site of the Olympic Games" table. I check if the current row's flag text
contains the country name. If so, I find the corresponding distance. If the distance is bigger than
the previously found distance for the country, I update the variables accordingly. Once I am able
to find the row corresponding to the max distance for a given country, I check if the last column of
the table includes a link to the Summer Olympics page for the given year. If it does not, I iterate
over the last column of the table and manually calculate the countries (which are those in the parenthesis).
Otherwise, I follow the link and search for "countries visited". I count the number of countries visited
based on the number of commas seen.
Assumptions:
1. Users should only enter valid years (years relating to an actual Olympics year)
2. When counting the number of countries, the counted countries will always be separated
by a comma. If there's countries like: United Kingdom and Ireland listed without a comma
it will be counted as 1 country. Similarly, with France and Monaco - it will get counted as
one country because there is no comma.
3. The "Torch relays" link will not change names
4. The "Site of the Olympic Games" table will not change name
5. The flag name will not get rid of the country name
6. The distance will be in the 3rd column
7. The country names will always be in parentheses for those countries who do not have
an article linked
8. The last column of the "Site of the Olympic Games" table will contain the list of countries
visited.
9. France only has 1 hosting that involved torch relays so the distance for France will not get
compared. This is handling the edge case for someone typing in "France" but not getting a result
because there is no distance listed.
10. No country name starts with "by"
Answer: 2

Question 8: I begin by iterating over the "Olympiad" table and getting the link associated to the
year of interest from the third column. On the year's page, I search for the table that contains
"location". Once found, I follow the first associated link. I search for "mayor" in the tables of this
new page and output the text that follows directly after "mayor".
Assumptions:
1. Lord Mayor is valid as "Mayor"
2. The cities for which we cannot find the mayors, we will assume that there is no mayor.
3. The "location" text will be followed immediately by a link to the Wiki page of the host
city (for some this may not exist and that's okay - we assume no mayor exists).
4. Only the text directly next to the "mayor" text will be considered when finding the mayor.
This means we will not be looking at oddly formatted boxes.
Answer (Default input: 2012): The city was London and the current mayor is Sadiq Khan.