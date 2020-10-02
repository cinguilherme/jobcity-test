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

run-sample-one-ref:
	gradlew run --args='firstMatch_sample.txt'

run-sample-one:
	gradlew run --args='firstMatch_sample.txt'

run-sample-two:
	gradlew run --args='firstMatch_sample.txt secondMatch.txt badlyFormatedFile.txt'

unlock-gradle:
	chmod +x gradlew