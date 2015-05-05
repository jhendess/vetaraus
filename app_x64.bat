SET PATH=%PATH%;lib_x64
java -d64 -Djava.library.path=lib_x64 -cp lib_x64/NeticaJ.jar;target/vetaraus-1.0-SNAPSHOT-jar-with-dependencies.jar de.dhbw.vetaraus.Application --learn P003_learn.csv P003_classify.csv
PAUSE
