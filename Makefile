CLASSPATH = ../common:../network:.

.PHONY: help common server client clean

help:
	@echo "Makefile help:"
	@echo "- make common: Build common classes."
	@echo "- make server: Build and run server."
	@echo "- make client: Build and run client."
	@echo "- make clean: Remove all class files."

common:
	cd ./common; \
	javac *.java

server: common
	cd ./server; \
	javac -cp $(CLASSPATH) *.java; \
	java -cp $(CLASSPATH) Main

client: common
	cd ./client; \
	javac -cp $(CLASSPATH) *.java; \
	java -cp $(CLASSPATH) Main

clean:
	find -name "*.class" -delete
