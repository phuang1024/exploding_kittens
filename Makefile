CLASSPATH = ../common:../network:.

.PHONY: help common server client 4clients clean docs

help:
	@echo "Makefile help:"
	@echo "- make common: Build common classes."
	@echo "- make server: Build and run server."
	@echo "- make client: Build and run client."
	@echo "- make clean: Remove all class files."

common:
	cd ./common; \
	javac *.java
	cd ./network; \
	javac *.java

server: common
	cd ./server; \
	javac -cp $(CLASSPATH) *.java; \
	java -cp $(CLASSPATH) Main

client: common
	cd ./client; \
	javac -cp $(CLASSPATH) *.java; \
	java -cp $(CLASSPATH) Main

4clients: common
	make client &
	make client &
	make client &
	make client &

clean:
	find -name "*.class" -delete

docs:
	javadoc -d ./docs-client ./common/*.java ./network/*.java ./client/*.java
	javadoc -d ./docs-server ./common/*.java ./network/*.java ./server/*.java
