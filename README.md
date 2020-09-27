## The Bowling score CLI

### Requirements
 - Gradle 6.3, !! gradlew available with the source code, just user appropriate command according to Makefile
 - Jdk11 / JRE11 (not too much fancy stuff not present in JDK8)
 - Lombok anotation processor
 - Junit5

### How to run?
txt file with scores in the expected format and provide file path parameter
    a. Each line represents a player score
    b. An F represents a FOUL which should count as zero
    c. The rolls are tab separated on the output

### Input sample Bellow
```
Jeff 10
John 3
John 7
Jeff 7
Jeff 3
John 6
John 3
Jeff 9
Jeff 0
John 10
Jeff 10
John 8
John 1
Jeff 0
Jeff 8
John 10
Jeff 8
Jeff 2
John 10
Jeff F
```

### How to build?
gradlew build on windows
./gradlew build on Linux or mac

 - all usefull commands shortcuts are in the Makefile such as build, test, clean, etc

#### Usefull commands
 - Makefile e.g "make test" windows, "make test-l" linux or mac

#### pure java program execution? build project and have the Jar available
```
java -jar PROGRAM -in scores.txt 
```


#### TODO list

    - The program should handle bad input like more than ten throws 
        (i.e., no chance will produce a negative number of knocked 
        down pins or more than 10, etc), invalid score
      value or incorrect format 
      
      - rules check:
        - for a single player minimum trows (frames) 
        are 10(10 strikes) and maximum throws are 20(10 frames of 2 throws each)
        but the frames has to be 10 exactly. So any number from 10 to 20 inclusive is a valid number of throws.
        - EDGE case here: The last Frame has an aditional throw*
        
      - Score calculation logic, I did not play bowling knowing the rules before:
        - For a frame, the calculation can be one of the following:
            - Non Strike or Spare, closed frame.
            - Spare -> all 10 pins on the second throw "/"
            - Strike -> all 10 pins on the first throw "X"
            the frame result will be calculated in the following logic:
            
            for spairs: you get 10 + next ball (spare frame) 
            so to calculate the value of the frame the first ball of 
            the next frame is required.
            
            for strikes: you get 10 + you next two balls(throws) so unless the 
            next thow is also a strike,
             all other permutations can only sum up to 10 or less here.
             
           the last frame extra shot rule:
            on the frame 10 there is an extra shoo
            
            eg. frame 1 -> [8,2](spare): 17(8+2 + 7 next ball) 
                frame 2 -> [7,2]: 26 (previous 17 + 9)
                frame 3 -> [X,_](strike): 26+20 = 46 (10 + next two balls (2,8 => 10))
                frame 4 -> [2,8](spare): 46 + 20 = 66 (10 + first ball next frame -> 10 = 20)
                frame 5 -> [X]: 66+28 = 94 (10 + next 2 balls (10 + 8) => 28)
                frame 6 -> [X]: 94+18 = 112  (10 + next two balls 8+0 = 18)
                frame 7 -> [8,-]: 112+8 = 120
                frame 8 -> [X]:
                frame 9 -> [8,2]
                
                hipotesis
                frame 10 -> [2,2, ?] 0 extra-shot
                frame 10 -> [8,2,9](spare) + 1 extra-shot
                frame 10 -> [X,2,9](strike) + 2 extra-shot?
                frame 10 -> [X,X,X](3 strike) + 2 extra-shot?
                
                TOTAL: ????
      
    - The program should output the scoring for the associated game according to these
      guidelines:
        * For each player, print their name on a separate line before printing that player's
          pinfalls and score.
        * All values are tab-separated.
        * The output should calculate if a player scores a strike ('X'), a spare ('/') and allow
          for extra chances in the tenth frame.
          
    - Your program should be able to handle all possible cases 
        of a game both including agame where all rolls are 0, 
        all rolls are fouls (F) and a perfect game, 
        where all rolls arestrikes: