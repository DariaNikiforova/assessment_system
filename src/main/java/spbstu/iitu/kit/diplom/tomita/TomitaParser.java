package spbstu.iitu.kit.diplom.tomita;

import java.io.*;

/**
 * Static class provides methods for working with Yandex Tomita parser.
 * @author Daria Nikiforova
 */
public final class TomitaParser {

    private static final String WORKING_DIRECTORY =
            "C:\\Users\\Asus\\Desktop\\diploma_work\\assessment_system\\src\\main\\resources\\tomita";

    private static final String ANALYSING_FILENAME = WORKING_DIRECTORY + "\\analyzed_answer.txt";

    private static final String OUTPUT_XML = WORKING_DIRECTORY + "\\output.xml";

    private TomitaParser() {}

    public static String executeAnalysis() {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(WORKING_DIRECTORY + "\\tomitaparser.exe config.proto",
                    null,
                    new File(WORKING_DIRECTORY));

            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            System.out.printf("Output of running is:");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        process.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return OUTPUT_XML;
    }

    public static String getOutputXml() {
        return OUTPUT_XML;
    }

    public static boolean writeToFile(String text) {
        try {
            Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(ANALYSING_FILENAME), "utf-8"));
            writer.write(text);
            writer.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
