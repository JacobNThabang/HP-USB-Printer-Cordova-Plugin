
var exec = require('cordova/exec');

var USBPrinter = {
    // Check to see if the printer is enabled, thus opened.
    isDeviceEnabled: function (successCallback, errorCallback) {
        console.log('HPUSBPrinter isDeviceEnabled method invoked');
        exec(successCallback, errorCallback, "HPUSBPrinter", "isDeviceEnabled");
    },

    //A device must be open, claim, and DeviceEnable=true for it to be used.
    setup: function (successCallback, errorCallback) {
        console.log('HPUSBPrinter setup method invoked');
        exec(successCallback, errorCallback, "HPUSBPrinter", "setup");
    },

    // Sent printJob.
    print: function (msg, successCallback, errorCallback) {
        console.log('HPUSBPrinter print method invoked');
        exec(successCallback, errorCallback, "HPUSBPrinter", "print", [msg]);
    },

    // Print Image.
    printBitmapImage: function (image, align, successCallback, errorCallback) {
        console.log('HPUSBPrinter printBitmap method invoked');
        exec(successCallback, errorCallback, "HPUSBPrinter", "printBMP", [image, align]);
    },

    // Print a new line.
    println: function (successCallback, errorCallback) {
        console.log('HPUSBPrinter println method invoked');
        exec(successCallback, errorCallback, "HPUSBPrinter", "printLine");
    },

    // Cut Paper
    cutPaper: function (successCallback, errorCallback) {
        console.log('HPUSBPrinter cutPaper method invoked');
        exec(successCallback, errorCallback, "HPUSBPrinter", "cut");
    },

    // Disconnect printer and release resources
    disconnect: function (successCallback, errorCallback) {
        console.log('HPUSBPrinter disconnect method invoked');
        exec(successCallback, errorCallback, "HPUSBPrinter", "disconnect");
    }
};

module.exports = USBPrinter;