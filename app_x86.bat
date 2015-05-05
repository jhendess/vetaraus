SET PATH=%PATH%;lib_x86
java -d32 -Djava.library.path=lib_x86 -cp lib_x86/NeticaJ.jar;target/vetaraus-1.0-SNAPSHOT-jar-with-dependencies.jar de.dhbw.vetaraus.Application --learn P003_learn.csv P003_classify.csv
PAUSE
