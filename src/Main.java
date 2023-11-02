import java.io.*;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.*;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws IOException {

        logger.setLevel(Level.INFO);
        Handler   handler = new ConsoleHandler();

            Handler fileHandler = new FileHandler("ooutput_logs/logs.log",true);

        SimpleFormatter formatter = new SimpleFormatter();
        fileHandler.setFormatter(formatter);
        logger.setUseParentHandlers(false);

        logger.addHandler(handler);
        logger.addHandler(fileHandler);





        List<String> inputFiles = new ArrayList<>();
        inputFiles.add("input_files/random-file1.txt");
        inputFiles.add("input_files/random-file2.txt");
        inputFiles.add("input_files/random-file3.txt");
        inputFiles.add("input_files/random-file4.txt");

        for (String path : inputFiles) {
            FileReader fileReader = new FileReader(path);


            logger.log(Level.SEVERE, "check logger");

            ScheduledExecutorService pool = Executors.newScheduledThreadPool(4);
            pool.scheduleAtFixedRate(new Runnable() {
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    StringBuffer stringBuffer = new StringBuffer();

                @Override
                public void run() {


                    String lines = null;
                    try {
                        lines = bufferedReader.readLine();
                        while (lines != null) {
                            stringBuffer.append(lines);
                            lines = bufferedReader.readLine();
                        }
                    } catch (IOException e) {
                        logger.log(Level.SEVERE, "Problem with inputStream");
                    }


                    String[] words = stringBuffer.toString().split(" ");
                    char[] letters = stringBuffer.toString().toCharArray();


                    int result = (letters.length - words.length) / words.length;
                     System.out.println("The number of words in the file "+path+" is "+words.length);
                    System.out.println("Average length of each word in the file " + path + " is " + result);
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        logger.log(Level.SEVERE, "ios exception ");
                    }


                }

            } , 5,  5, TimeUnit.SECONDS);
        }



    }
}
