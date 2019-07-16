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
import android.graphics.Bitmap;
import android.util.Base64;
import java.io.File;
import java.io.IOException;

import com.hp.android.possdk.IJPOSInitCompleteCallBack;
import com.sunmi.utils.BitmapUtils;


public class HPUSBPrinter extends CordovaPlugin implements IJPOSInitCompleteCallBack {
    private BitmapUtils bitMapUtils;

    @Override
    public void onComplete() { // IJPOSInitCompleteCallBack callback for SDK initialization complete

    }

    POSPrinter printer = new POSPrinter();
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        JPOSApp.setsContext(webView.getContext(),(IJPOSInitCompleteCallBack)this);
        bitMapUtils = new BitmapUtils(webView.getContext());

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
            printImage(args.getString(0), callbackContext);
            return true;
        }
        else if ("printBarcodeQR".equals(action)) {
            // Print the image.
            printBarcode(args.getString(0), callbackContext);
            return true;
        }
        else if ("printLine".equals(action)) {
            // Print the line.
            println(args.getString(0), callbackContext);
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
            callbackContext.success("true");
        } catch (JposException e) {
            callbackContext.error("isDeviceEnabled" + e.getLocalizedMessage().toString());
        }

    };

    private void setup(CallbackContext callbackContext) {
        // A device must be open, claim, and DeviceEnable=true for it to be used.

        try {

            printer.open("HPEngageOnePrimePrinter"); // HP Engage One Prime White Receipt Printer
            printer.claim(1000);
            printer.setDeviceEnabled(true);
            callbackContext.success("setup: Success!");
        } catch (JposException e) {
            callbackContext.error("setup: " + e.getLocalizedMessage().toString());
        }

    };

    private void print(String msg, CallbackContext callbackContext) {
        // Print
        try {
            printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\r" + msg + "\n");
            callbackContext.success("print: Success!");
        } catch (JposException e) {
            callbackContext.error("print: " + e.getLocalizedMessage().toString());
        }

    };

    private void println(String msg, CallbackContext callbackContext) {
        // PrintLine
        try {
            printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\n\n");
            callbackContext.success("println: Success!");
        } catch (JposException e) {
            callbackContext.error("println: " + e.getLocalizedMessage().toString());
        }

    };

    private void printImage(String msg, CallbackContext callbackContext){
        // Print

        int width = POSPrinterConst.PTR_BM_ASIS;
        int alignment = POSPrinterConst.PTR_BM_CENTER;

        final byte[] decodedBytes = Base64.decode(msg, Base64.DEFAULT);
        //Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);


        try {
            printer.printMemoryBitmap(POSPrinterConst.PTR_S_RECEIPT, decodedBytes, POSPrinterConst.PTR_BMT_BMP, width, alignment);
            printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\n");
            callbackContext.success("Printing Image Success! "  + msg);
        } catch (JposException e) {
            callbackContext.error(e.getLocalizedMessage().toString());
        }

    };

    private void printBarcode(String msg, CallbackContext callbackContext) {
        // Print the barcode
        // Data and Settings
        int symbology = POSPrinterConst.PTR_BCS_QRCODE;
        int height = 150;
        int width = 150;
        int alignment = POSPrinterConst.PTR_BC_CENTER;
        int textPosition = POSPrinterConst.PTR_BC_TEXT_NONE;

        try {
            printer.printBarCode(POSPrinterConst.PTR_S_RECEIPT, msg, symbology, height, width, alignment, textPosition);
            printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\n");
            callbackContext.success("printBarcode: Success!");
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