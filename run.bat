set classpath=lib/common-lang3.jar;lib/jsoup-1.6.2.jar;lib/jfreechart-1.0.13.jar;lib/jfreechart-1.0.13-experimental.jar;lib/jfreechart-1.0.13-swt.jar;lib/jcommon-1.0.16.jar;.;
javac -d . *.java
java -Xmx1000M duplicate.MainScreen
pause