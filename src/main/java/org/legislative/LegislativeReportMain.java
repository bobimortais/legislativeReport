package org.legislative;

import org.legislative.exception.MissingInputArgumentException;

import java.io.IOException;

public class LegislativeReportMain {
    private static final ReportGenerationService reportGenerationService = new ReportGenerationService();

    public static void main(String[] args) {
        try {
            run(args);
        } catch(MissingInputArgumentException e) {
            throw e;
        }
        catch (Exception e) {
            System.out.println("There was an issue while generating the reports. Please refer to README.md for troubleshooting.");
        }
    }

    static void run(String[] args) throws IOException {
        if(args.length != 4) {
            throw new MissingInputArgumentException();
        }

        reportGenerationService.generateReports(args);
    }

}