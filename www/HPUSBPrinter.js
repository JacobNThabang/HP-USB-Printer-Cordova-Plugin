
var exec = require('cordova/exec');

var USBPrinter = {
    //A device must be open, claim, and DeviceEnable=true for it to be used.
    setup: function (msg, successCallback, errorCallback) {
        console.log('HPUSBPrinter setup method invoked');
        exec(successCallback, errorCallback, "HPUSBPrinter", "setup", [msg]);
    },

    // Sent printJob.
    print: function (msg, successCallback, errorCallback) {
        console.log('HPUSBPrinter setup method invoked');
        exec(successCallback, errorCallback, "HPUSBPrinter", "print", [msg]);
    },

    // Print Image.
    printBitmap: function (msg, successCallback, errorCallback) {
        console.log('HPUSBPrinter setup method invoked');
        exec(successCallback, errorCallback, "HPUSBPrinter", "printBMP", [msg]);
    },

    // Print a new line.
    println: function (msg, successCallback, errorCallback) {
        console.log('HPUSBPrinter setup method invoked');
        exec(successCallback, errorCallback, "HPUSBPrinter", "printLine", [msg]);
    },

    // Cut Paper
    cutPaper: function (msg, successCallback, errorCallback) {
        console.log('HPUSBPrinter setup method invoked');
        exec(successCallback, errorCallback, "HPUSBPrinter", "cut", [msg]);
    },

    disconnect: function (msg, successCallback, errorCallback) {
        console.log('HPUSBPrinter setup method invoked');
        exec(successCallback, errorCallback, "HPUSBPrinter", "disconnect", [msg]);
    }
};

module.exports = USBPrinter;