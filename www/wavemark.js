
	 	

var exec = require('cordova/exec') ; 
	module.exports = {
    getRingtone: function(successCallback, errorCallback) {
        //exec(successCallback, errorCallback, "ContactVcardPicker", "getContactVcard", []);
        exec(successCallback, errorCallback, 'RingtonePicker', 'getRingtone', []);
    }
	
	};
function success(data)
{
alert("success"+data); 
}

 function failure(data)
{
alert("failure");
}