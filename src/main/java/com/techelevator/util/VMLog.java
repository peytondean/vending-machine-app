package com.techelevator.util;

import com.techelevator.exception.ErrorCode;
import com.techelevator.exception.VMLogException;
import com.techelevator.vendingmachine.VendingSlot;

import java.io.*;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * Logging class that keeps track of all the transactions that have occurred when
 * the machine was in use
 */
public class VMLog {

    //  Using these enums to determine what needs to be added to the log message when appended
    public enum LogType {
        DEPOSIT {
            @Override
            public String messageToAppend() {
                return "FEED MONEY:";
            }
        },
        GIVE {
            @Override
            public String messageToAppend() {
                return "GIVE CHANGE:";
            }
        };

        //  Made an abstract method for the enum class in order to force us to create
        //  new log messages if adding a new type of transaction to track
        public abstract String messageToAppend();
    }

    //  File extension used for log reports
    private static final String FILE_EXTENSION = ".txt";

    //  File path to generate Log reports
    private static final String LOG_FILE_PATH = "logs/Log" + FILE_EXTENSION;

    //  File path to generate Sales reports
    private static final String SALES_FILE_PATH = "salesreports/";

    //  Message to append in Sales reports
    private static final String SALES_REPORT_MESSAGE = "**TOTAL SALES**";

    //  This object allows the log to have a format of Hour:Minutes:Seconds am/pm when this pattern is used
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss a");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    //  Creating a static object here so there isn't a new PrinterWriter created everytime
    //  the logTransaction method is called
    private static PrintWriter logWriter = null;

    /**
     * Calling this will log ANY message into the Log.txt file with the exact time and date it was called
     *
     * @param transactionDetail The message that will be appended to the Log.txt file
     * @throws VMLogException During runtime, if there is an issue with reading/writing to the file, this will be thrown
     */
    public static void logTransaction(String transactionDetail) throws VMLogException {

        //  This will format our time according to how the documentation presents it
        String formattedTime = LocalTime.now().format(TIME_FORMATTER);

        String formattedDate = LocalDate.now().format(DATE_FORMATTER);

        //  Creates the String object that will be appended to the Log.txt
        String detailedTransaction = formattedDate + " " + formattedTime + " " + transactionDetail;

        // This if statement makes sure that the application will always make sure to check for a valid PrintWriter to use
        if (logWriter == null) {
            //  Capturing this code block in this try-catch in order account for exceptions
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(new File(LOG_FILE_PATH), true);
                logWriter = new PrintWriter(fileOutputStream);
                logWriter.println(detailedTransaction);

                //  Flushing to make sure we can continue to append when this method is called again
                logWriter.flush();
            } catch (VMLogException | FileNotFoundException e) {
                throw new VMLogException(e.getMessage(), e.getCause(), ErrorCode.LOG_FILE_PATH_INVALID);
            }
        } else {
            //  If the PrintWriter is already initialized, lets just append and flush
            logWriter.println(detailedTransaction);
            logWriter.flush();
        }
    }

    /**
     * Generates a new sales report file every time it is called with
     * up-to-date sales
     *
     * @param salesReportFileName Use to create the file name with the format specified
     * @param vendingMachine      Used to iterate through the current vending slots in the vending machine
     * @return The entire report in a String object
     * @throws VMLogException Handling any writing to file issues that make occur during the method call
     */
    public static String logSalesReport(String salesReportFileName, Map<String, VendingSlot> vendingMachine) throws VMLogException {
        //  This will format our time according to how the documentation presents it
        String formattedTime = LocalTime.now().format(TIME_FORMATTER);

        //  Replacing the ":" in the formatting due to being invalid for saving file names.
        formattedTime = formattedTime.replace(':', '_');
        //  Creates the String object that will be used as the final file name
        String completeFileName = LocalDate.now() + " " + formattedTime;
        completeFileName = SALES_FILE_PATH + salesReportFileName + completeFileName + FILE_EXTENSION;

        //  Creating this to build our string that we will append to the sales report
        StringBuilder saleHistory = new StringBuilder();

        //  Let's attempt to create an output stream to start writing to a new txt file
        try (FileOutputStream fileOutputStream = new FileOutputStream(new File(completeFileName), false);
             PrintWriter salesReportWriter = new PrintWriter(fileOutputStream)) {

            //  Keeping track of the total sales with a local double as we iterate through the map
            double totalSales = 0.0;
            for (VendingSlot currentSlot : vendingMachine.values()) {

                //  By subtracting the max quantity and current quantity, the remaining value can be used to multiply the price of the snack
                //  to get the total sales from that snack
                totalSales += ((currentSlot.getMaxQuantity() - currentSlot.getQuantity()) * Double.parseDouble(currentSlot.getPrice()));

                //  Appending all the data to the sales history buffer
                saleHistory.append(currentSlot.getSnack().getName()).append("|").append(currentSlot.getMaxQuantity() - currentSlot.getQuantity()).append("\n");
            }

            //  Doing a final append to write the total sales to the file
            saleHistory.append("\n").append(SALES_REPORT_MESSAGE + " ").append(NumberFormat.getCurrencyInstance().format(totalSales));

            //  Finally writing to the newly created file
            salesReportWriter.println(saleHistory);

            return saleHistory.toString();
        } catch (VMLogException | IOException e) {
            throw new VMLogException(e.getMessage(), e.getCause(), ErrorCode.SALES_REPORT_FAILED_TO_CREATE_WITH_NAME_GIVEN);
        }
    }

    /**
     * A simple call to close out our PrintWriter object once
     * we no longer have use for it
     */
    public static void closeLog() throws VMLogException {
        try{
            if(logWriter != null) {
                logWriter.close();
            }
        }catch(VMLogException e){
            throw new VMLogException(e.getMessage(), e.getCause(), ErrorCode.LOG_WRITER_CLOSING_ON_NULL_PTR);
        }
    }
}
