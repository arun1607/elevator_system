#### Running the application

##### In IDE

Use `com.app.learning.Main` to run the program. The program will take input from console and 
output the lift position on console.

##### Command line

Use `mvn clean package`

`java -jar target/elevator-system-1.0.0.jar`

##### Sample output 

Please provide source floor

5

Please provide destination floor

0

5 0

T=0

LIFT 1 --> 0(CLOSE) with direction DOWN

T=1

LIFT 1 --> 1(CLOSE) with direction UP

T=2

LIFT 1 --> 2(CLOSE) with direction UP

T=3

LIFT 1 --> 3(CLOSE) with direction UP

T=4

LIFT 1 --> 4(CLOSE) with direction UP

T=6

LIFT 1 --> 5(OPEN) with direction UP

Please provide source floor

T=7

LIFT 1 --> 5(CLOSE) with direction UP

T=7

LIFT 1 --> 5(CLOSE) with direction UP

T=8

LIFT 1 --> 4(CLOSE) with direction DOWN

T=9

LIFT 1 --> 3(CLOSE) with direction DOWN

T=10

LIFT 1 --> 2(CLOSE) with direction DOWN

T=11

LIFT 1 --> 1(CLOSE) with direction DOWN

T=13

LIFT 1 --> 0(OPEN) with direction DOWN

T=14

LIFT 1 --> 0(CLOSE) with direction DOWN

LIFT 1 --> T = 14 

