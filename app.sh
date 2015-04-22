#!/bin/sh

neticajar=lib/NeticaJ.jar
jarfile=target/vetaraus-1.0-SNAPSHOT-jar-with-dependencies.jar

java -Djava.library.path=lib -cp ${neticajar}:${jarfile} de.dhbw.vetaraus.Application --learn P003_learn.csv P003_classify.csv