CLASSPATH = ../common:../network:.

MANIFEST = "Manifest-version: 1.0\nMain-Class: Main"

.PHONY: help common server client 4clients clean build docs

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

build:
	mkdir -p ./build-client ./build-server
	echo $(MANIFEST) | tee ./build-client/manifest.mf ./build-server/manifest.mf
	cp ./common/*.java ./network/*.java ./build-client
	cp ./common/*.java ./network/*.java ./build-server
	cp ./client/*.java ./build-client
	cp ./server/*.java ./build-server
	cd ./build-client; \
	javac *.java; \
	jar cfm client.jar manifest.mf *.class
	cd ./build-server; \
	javac *.java; \
	jar cfm server.jar manifest.mf *.class
	rm -f ./build-client/*.java ./build-server/*.java
	cp -r ./client/images ./build-client
	cd ./build-client; \
	find -name "*.xcf" -delete; \
	mkdir -p ./ExplodingKittensClient; \
	mv client.jar images ExplodingKittensClient; \
	zip -r client.zip ./ExplodingKittensClient

docs:
	javadoc -d ./docs-client ./common/*.java ./network/*.java ./client/*.java
	javadoc -d ./docs-server ./common/*.java ./network/*.java ./server/*.java
