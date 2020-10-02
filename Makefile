test:
	gradlew test
test-l:
	./gradlew test

build:
	gradlew build
build-l:
	./gradlew build

clean:
	gradlew clean
clean-l:
	./gradlew clean

run:
	gradlew run

run-sample-one:
	gradlew run --args='matchesFiles/firstMatch_sample.txt'

run-sample-two:
	gradlew run --args='firstMatch_sample.txt'

run-sample-one-old:
	gradlew run --args='firstMatch_sample.txt'

run-sample-two-old:
	gradlew run --args='firstMatch_sample.txt secondMatch.txt badlyFormatedFile.txt'

unlock-gradle:
	chmod +x gradlew