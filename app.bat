SET PATH=%PATH%;lib
java -Djava.library.path=lib -cp lib/NeticaJ.jar;target/vetaraus-1.0-SNAPSHOT-jar-with-dependencies.jar de.dhbw.vetaraus.Application --learn P003_learn.csv P003_classify.csv
PAUSE
