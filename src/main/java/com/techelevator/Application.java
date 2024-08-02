package com.techelevator;

import com.techelevator.exception.VMLogException;
import com.techelevator.menu.Menus;
import com.techelevator.util.VMLog;
import com.techelevator.vendingmachine.VendingMachine;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        //	Static method within the application class for readability
        run();

        //	Closing out the logger as we no longer need this running if the vending machine
        //	is no longer in use by the customer
        VMLog.closeLog();
    }

    /**
     * Helper method that handles vending machine and customer interactions
     *
     * @throws IOException Throws if an issue occurs with logginggitp
     *                     information to either or sales or transaction file.
     */
    public static void run() throws VMLogException, IOException {
        // put interface in run for now, maybe later if we have time we will put it in its own class.
        VendingMachine vendingMachine = new VendingMachine();
        vendingMachine.loadInVendingMachineData("vendingmachine.csv");

		//	Creating a menu and customer object class to demonstrate functionality of the vending machine
		Menus userInterface = new Menus();

		//	Handling logic of user input within this method
		userInterface.runMenu(vendingMachine);
	}
}
