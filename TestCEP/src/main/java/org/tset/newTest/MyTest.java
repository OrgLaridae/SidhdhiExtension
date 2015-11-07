package org.tset.newTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by ruveni on 07/11/15.
 */
public class MyTest {
    StringBuilder message = null;
    String filePath = "/home/ruveni/ekxv0000.txt";


    public void checkTest() {

        int matrixSize = 240;
        int threshold = 1;
        int maxRow = 0, maxCol = 0, minRow = 240, minCol = 240;

        double[][] val = dBZToZ(filePath);

        String data = message.toString();
        String[] zValues = data.split(",");
        System.out.println(zValues.length);
        System.out.println(zValues[0]);
        double reflectVal = 0;
        int k = 1;
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                reflectVal = Double.parseDouble(zValues[k]);
                if (reflectVal > threshold) {
                    if (i > maxRow) {
                        maxRow = i;
                    }
                    if (i < minRow) {
                        minRow = i;
                    }
                    if (j > maxCol) {
                        maxCol = j;
                    }
                    if (j < minCol) {
                        minCol = j;
                    }
                    System.out.println(minRow + " " + maxRow + " " + minCol + " " + maxCol);
                }

                k++;
            }
        }
        System.out.println(minRow + " " + maxRow + " " + minCol + " " + maxCol);
    }

    public double[][] dBZToZ(String location) {
        message = new StringBuilder();
        message.append(filePath);
        double[][] Z = new double[240][240];
        double alpha = 0.5;
        double beta = -32;
        Path path = Paths.get(location);
        System.out.println("A");
        try (Stream<String> lines = Files.lines(path)) {
            String[] lineArray = lines.collect(Collectors.toList()).toArray(new String[0]);
            for (int i = 0; i < lineArray.length; i++) {
                String[] stringData = lineArray[i].split(",");
                for (int j = 0; j < 240; j++) {
                    double data = Double.parseDouble(stringData[j]);
                    data = (data == 255.0) ? 0 : data;
                    data = (alpha * data) + beta;
                    data = Math.pow(10, (data / 10));
                    //data=(data<1)?0:Math.round(data * 10000.0) / 10000.0;
                    Z[i][j] = data;
                    message.append("," + Z[i][j]);
                }
            }
            System.out.println("B");
            System.out.println("AAA" + message);
        } catch (IOException ex) {

        }
        return Z;
    }
}
