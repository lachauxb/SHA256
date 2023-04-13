all: clean build

clean:
	rm -f *.o
	rm -f sha256

build: 
	gcc -c sha256.c -o sha256.o
	gcc -c util.c -o util.o
	gcc -c main.c -o main.o
	gcc sha256.o util.o main.o -o sha256