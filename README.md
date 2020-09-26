## The Bowling score CLI

### Requirements
 - Gradle 6.3, gradlew available with the source code
 - Jdk11 / JRE11
 - Lombok anotation processor
 - Junit5

### How to run?
txt file with scores in the expected format
a. Each line represents a player score
b. An F represents a FOUL which should count as zero
c. The rolls are tab separated

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
gradle build

### Usefull commands
 - Makefile
 - Or commands bellow
```
java -jar PROGRAM -in scores.txt 
```


#### TODO list

    - The program should handle bad input like more than ten throws (i.e., no chance will
      produce a negative number of knocked down pins or more than 10, etc), invalid score
      value or incorrect format 
      
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