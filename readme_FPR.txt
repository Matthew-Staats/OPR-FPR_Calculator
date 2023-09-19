So what is happening here? I made FPR based on the concept of OPR from Robotics: https://blog.thebluealliance.com/2017/10/05/the-math-behind-opr-an-introduction/.
Instead of giving each football team an offensive rank I gave a defensive rank and an offensive rank.
The football power ranking (FPR) of a team is the Offensive rank (OR) + the defensive rank (DR).
The idea is when team 1 plays team 2 the score should be roughly:
Team 1 Scores: Team 1 OR - Team 2 DR
Team 2 Scores: Team 2 OR - Team 1 DR
This is the model I have the programs calculate the least squares solution for.


How to make it work: (should be able to work from the start)
So you make your file of previous games, and a file for games to predict (optional) & choose it in the Main (in Main_FPR) method (currently it is set to use the command line arguments). You list the previous games file first, and the pgames to predict 2nd.


Files made for FPR include:
scores
scoresWk10AndLater
nextWeek


When making your file do it following the correct format:
something (tab) Team 1 (tab) score 1 (tab) Team 2 (tab) Score 2 (tab) anything (inlcuding tabs)...
ex:
11/10/2022	Atlanta Falcons	15	Carolina Panthers	25		Boxscore

Between weeks, you can add pretty much whatever you want. Except don't add any team names. Anything else should be fine but to be safe ideally keep it short or without numbers:
Week 10
Date	Visitor		Home			Box
But anything should work as long as you don't use team names.


Classes:
Main_FPR is the main file for calculating FPR
Key is a generic class for holding other info I wanted that was useful for this project
MatrixOperations holds all the matrix operations needed for calculating OPR.
OPRCalulcator is where most stuff happens, it reads the file and uses the Matrix operations to calculate OPR. If you want to change what is printed or how it's printed go to the findAndPrintOPR method. There are a lot of unnecessary prints you can delete if you wish.
LabeledSports is for sorting the data while keeping it labeled to the team they belong to, which is how the teams are ordered by FPR.


Note:
If somehow your final matrix ends up with a free variable my code will not warn you but be aware if any OPR is exactly 0.0 that might be why. There might be some very slightly rounding errors after row reducing the final matrix but very small errors. Currently, the program doesn't sort the data at all just prints it out in the order was entered (the order the teams first appeared)
