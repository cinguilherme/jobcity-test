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
	gradlew run --args='filepaths: some.txt some-other.txt'
