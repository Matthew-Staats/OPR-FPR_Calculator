Hi! This is one of my first readme files ever!

So what is happening? Linear Algebra! The program finds the least squares solution (like fitting a line to data) of a bunch of data you give about how well each team does and outputs each team's OPR, or a ranking to give to each team for how good they are. This is a longer explanation of OPR and how we calcuate it: https://blog.thebluealliance.com/2017/10/05/the-math-behind-opr-an-introduction/


How to make it work: (should be able to work from the start)
So you make your file choose it in the Main (in Main_OPR) method (currently it is set to use the command line arguments) and make sure you have the right number, 2 for ftc and 3 for frc for the teams parameter/
For the arguments, the number of teams comes first, then the file name.


Files made for OPR include:
matches2 & matches2_2 are example FRC files from a random competition, matches1 & matches1_2.txt are example FTC files from my former robotics team's competition. The first of each of the 2 is just the qualification matches and the 2nd is both the qualification matches and the finals matches.


When making your file do it following the correct format:
team1red
team2red
team3red (if there is one)
team1blue
team2blue
team3blue (if there is one)
red score
blue score


You can use either a new line or tab between everything
You can have anything else in their as long as it will cause an exception when Integer.parseInt() is called on it.
Everything on its own line, between 2 tabs, or between a tab and a new line will be it's own String for the above purpose. So Quals 1 is fine but not Quals(tab)    1


Classes:
Main_OPR is the main file for calculating OPR
Key is a generic class for holding other info I wanted that was useful for this project
MatrixOperations holds all the matrix operations needed for calculating OPR.
OPRCalulcator is where most stuff happens, it reads the file and uses the Matrix operations to calculate OPR. If you want to change what is printed or how it's printed go to the findAndPrintOPR method. There are a lot of unnecessary prints you can delete if you wish.
LabeledSports is for sorting the data while keeping it labeled to the team they belong to it currently is working for FPR but not OPR.


Note:
If somehow your final matrix ends up with a free variable my code will not warn you but be aware if any OPR is exactly 0.0 that might be why. There might be some very slightly rounding errors after row reducing the final matrix but very small errors. Currently, the program doesn't sort the data at all just prints it out in the order was entered (the order the teams first appeared)
