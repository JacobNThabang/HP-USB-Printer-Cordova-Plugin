
var exec = require('cordova/exec');

var USBPrinter = {
    // Check to see if the printer is enabled, thus opened.
    isDeviceEnabled: function (msg, successCallback, errorCallback) {
        console.log('HPUSBPrinter isDeviceEnabled method invoked');
        exec(successCallback, errorCallback, "HPUSBPrinter", "isDeviceEnabled", [msg]);
    },

    //A device must be open, claim, and DeviceEnable=true for it to be used.
    setup: function (msg, successCallback, errorCallback) {
        console.log('HPUSBPrinter setup method invoked');
        exec(successCallback, errorCallback, "HPUSBPrinter", "setup", [msg]);
    },

    // Sent printJob.
    print: function (msg, successCallback, errorCallback) {
        console.log('HPUSBPrinter print method invoked');
        exec(successCallback, errorCallback, "HPUSBPrinter", "print", [msg]);
    },

    // Print Image.
    printBitmapImage: function (msg, successCallback, errorCallback) {
        console.log('HPUSBPrinter printBitmap method invoked');
        exec(successCallback, errorCallback, "HPUSBPrinter", "printBMP", [msg]);
    },

    // Print a new line.
    println: function (msg, successCallback, errorCallback) {
        console.log('HPUSBPrinter println method invoked');
        exec(successCallback, errorCallback, "HPUSBPrinter", "printLine", [msg]);
    },

    // Cut Paper
    cutPaper: function (msg, successCallback, errorCallback) {
        console.log('HPUSBPrinter cutPaper method invoked');
        exec(successCallback, errorCallback, "HPUSBPrinter", "cut", [msg]);
    },

    // Disconnect printer and release resources
    disconnect: function (msg, successCallback, errorCallback) {
        console.log('HPUSBPrinter disconnect method invoked');
        exec(successCallback, errorCallback, "HPUSBPrinter", "disconnect", [msg]);
    }
};

module.exports = USBPrinter;