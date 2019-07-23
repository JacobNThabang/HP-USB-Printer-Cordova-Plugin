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
import android.graphics.BitmapFactory;
import android.util.Base64;
import java.io.File;
import java.io.IOException;

import com.jacob.BitmapConvertor;
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
            printImage(args.getString(0), args.getString(1), callbackContext);
            return true;
        }
        else if ("printLine".equals(action)) {
            // Print the line.
            println(callbackContext);
            return true;
        }
        else if ("cut".equals(action)) {
            // Cut paper.
            cut(callbackContext);
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
            printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, msg + "\n");
            callbackContext.success("print: Success!");
        } catch (JposException e) {
            callbackContext.error("print: " + e.getLocalizedMessage().toString());
        }

    };

    private void println(CallbackContext callbackContext) {
        // PrintLine
        try {
            printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\n");
            callbackContext.success("println: Success!");
        } catch (JposException e) {
            callbackContext.error("println: " + e.getLocalizedMessage().toString());
        }

    };

    private byte[] convertBitmap2BmpBytes(Bitmap bitmap) {

        byte[] dataBytes;

        BitmapConvertor convertor = new BitmapConvertor();

        dataBytes  = convertor.convertBitmapToBuffer(bitmap);

        return dataBytes;

    }

    private void printImage(String msg, String align, CallbackContext callbackContext){
        // Print

        int width = POSPrinterConst.PTR_BM_ASIS;
        int alignment = 0;
        switch(Integer.parseInt(align)) {
           case 0:
              alignment = POSPrinterConst.PTR_BM_LEFT;
              break; // optional

           case 1:
              alignment = POSPrinterConst.PTR_BM_CENTER;
              break; // optional

           default : // Optional
              break;
        }


        try {
            byte[] decodedString = Base64.decode(msg, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);



            byte[] dataBytes = convertBitmap2BmpBytes(decodedByte);

            printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\n");
            printer.printMemoryBitmap(POSPrinterConst.PTR_S_RECEIPT, dataBytes, POSPrinterConst.PTR_BMT_BMP, width, alignment);
            callbackContext.success("Printing Image Success! "  + msg);
        } catch (JposException e) {
            callbackContext.error("Printing Image: " + e.getLocalizedMessage().toString());
        }

    };

    private void cut(CallbackContext callbackContext) {
        // Cut paper, call println first.

        try {
            printer.cutPaper(100);
            callbackContext.success("cut: Success!");
        } catch (JposException e) {
            callbackContext.error("cut: " + e.getLocalizedMessage().toString());
        }

    };

    private void disconnect(CallbackContext callbackContext) {
        // Disconnect printer, but first do some resource management and disable the printer.

        try {
            printer.setDeviceEnabled(false);
            printer.release();
            printer.close();
            callbackContext.success("disconnect: Success!");
        } catch (JposException e) {
            callbackContext.error("disconnect: " + e.getLocalizedMessage().toString());
        }

    };

}