all: clean build launch

clean:
	rm -f test

build:
	gcc -c testSha256.c -o test.o
	gcc test.o ../unitTest/unitTest.o ../sha256/sha256.o ../sha256/util.o  -o test

launch:
	./test