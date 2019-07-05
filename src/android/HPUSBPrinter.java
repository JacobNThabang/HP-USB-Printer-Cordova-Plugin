package com.jacob;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import jpos.JPOSApp;
import jpos.JposException;
import jpos.POSPrinter;
import jpos.POSPrinterConst;
import android.content.Context;

import com.hp.android.possdk.IJPOSInitCompleteCallBack;


public class HPUSBPrinter extends CordovaPlugin implements IJPOSInitCompleteCallBack {

    @Override
    public void onComplete() { // IJPOSInitCompleteCallBack callback for SDK initialization complete

    }

    POSPrinter printer = new POSPrinter();
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        JPOSApp.setsContext(webView.getContext(),(IJPOSInitCompleteCallBack)this);

        if ("setup".equals(action)) {
            // Set-up the printer for use.
            setup(callbackContext);
            return true;
        }
        else if ("isDeviceEnabled".equals(action)) {
            // Set-up the printer for use.
            isDeviceEnabled(callbackContext);
            return true;
        }
        else if ("print".equals(action)) {
            // Print the line.
            print(args.getString(0), callbackContext);
            return true;
        }
        else if ("printBMP".equals(action)) {
            // Print the image.
            printBMP(args.getString(0), callbackContext);
            return true;
        }
        else if ("printLine".equals(action)) {
            // Print the line.
            print(args.getString(0), callbackContext);
            return true;
        }
        else if ("cut".equals(action)) {
            // Cut paper.
            cut(args.getString(0), callbackContext);
            return true;
        }
        else if ("disconnect".equals(action)) {
            // Disconnect the printer.
            disconnect(callbackContext);
            return true;
        }
        return false;
    };

    private void isDeviceEnabled(CallbackContext callbackContext) {
        // A device must be open, claim, and DeviceEnable=true for it to be used.

        try {
            boolean printerStatus = printer.getDeviceEnabled();
            callbackContext.success(printerStatus + "");
        } catch (JposException e) {
            callbackContext.error(e.getLocalizedMessage().toString());
        }

    };

    private void setup(CallbackContext callbackContext) {
        // A device must be open, claim, and DeviceEnable=true for it to be used.

        try {
            printer.open("HPEngageOnePrimePrinter"); // HP Engage One Prime White Receipt Printer
            printer.claim(1000);
            printer.setDeviceEnabled(true);
            callbackContext.success("Success!");
        } catch (JposException e) {
            callbackContext.error(e.getLocalizedMessage().toString());
        }

    };

    private void print(String msg, CallbackContext callbackContext) {
        // Print
        try {
            printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, msg);
            callbackContext.success("Success!");
        } catch (JposException e) {
            callbackContext.error(e.getLocalizedMessage().toString());
        }

    };

    private void printBMP(String msg, CallbackContext callbackContext) {
        // Print
        int width = POSPrinterConst.PTR_BM_ASIS;
        int alignment = POSPrinterConst.PTR_BM_CENTER;
        try {
            printer.printBitmap(POSPrinterConst.PTR_S_RECEIPT, msg, width, alignment);
            callbackContext.success("Success!");
        } catch (JposException e) {
            callbackContext.error(e.getLocalizedMessage().toString());
        }

    };

    private void cut(String msg, CallbackContext callbackContext) {
        // Feed 6-lines(msg) before cutting paper.

        try {
            printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, msg);
            printer.cutPaper(100);
            callbackContext.success("Success!");
        } catch (JposException e) {
            callbackContext.error(e.getLocalizedMessage().toString());
        }

    };

    private void disconnect(CallbackContext callbackContext) {
        // Disconnect printer, but first do some resource management and disable the printer.

        try {
            printer.setDeviceEnabled(false);
            printer.release();
            printer.close();
            callbackContext.success("Success!");
        } catch (JposException e) {
            callbackContext.error(e.getLocalizedMessage().toString());
        }

    };

}