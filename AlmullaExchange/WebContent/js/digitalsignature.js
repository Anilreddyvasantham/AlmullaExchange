var tmr;

//Signature Draw
function onSign(){
	var ctx = document.getElementById('cnv').getContext('2d');
	SigWebSetDisplayTarget(ctx);
	tmr = setInterval(SigWebRefresh, 50);
	SetDisplayXSize( 300 );
	SetDisplayYSize( 100 );
	SetJustifyMode(0);
	SetTabletState(1);
	KeyPadClearHotSpotList();
	ClearSigWindow(1);
	ClearTablet();
}

//Signature Clear
function onClear()	{
	ClearTablet();
	document.getElementById('form1:digitalsign').value = "";
}


// Signature Capturing
function onDone(){
	 
	if(NumberOfTabletPoints() == 0) {
        //alert("Please sign before continuing");
    } else{
    	SetTabletState(0);
        SetSigCompressionMode(1);
        
        
        var cvs = document.createElement('canvas');
		cvs.width = GetImageXSize();
		cvs.height = GetImageYSize();
		
		var canvas = document.getElementById('cnv');
		var img = canvas.toDataURL("image/png"); 
		
		var loc = img.search("base64,");
		var retstring = img.slice(loc + 7, img.length);
		
		/*if(retstring.length>4000){
			alert("Signature Length exceed maximum size");
			ClearTablet();
		}*/
		document.getElementById('form1:digitalsign').value = retstring;
		//alert(document.getElementById('form1:digitalsign').value);        
    }    
}



/*  Previous

function onDone(){
	
	if(NumberOfTabletPoints() == 0) {
        alert("Please sign before continuing");
    } else{
    	SetTabletState(0);
        SetSigCompressionMode(1);
        
        
        var cvs = document.createElement('canvas');
		cvs.width = GetImageXSize();
		cvs.height = GetImageYSize();
		
		var canvas = document.getElementById('cnv');
		var img = canvas.toDataURL("image/png"); 
		
		alert("hellllllllllllllll"+img);

		var xhr2 = new XMLHttpRequest();
		xhr2.open("GET", baseUri + "SigImage/1", true);
		xhr2.responseType = "blob";
		xhr2.send(null);
		xhr2.onload = function ()
			{
				var cntx = cvs.getContext('2d');
				
				var canvas  = document.getElementById('cvs');
				
				var img = new Image();
				alert(xhr2.response);
				img.src = window.URL.createObjectURL(xhr2.response);
				alert(img.src);
				
				cntx.drawImage(img, 0, 0);
				var dataURL = cvs.toDataURL("image/png");
				
				var loc = dataURL.search("base64,");
				var retstring = dataURL.slice(loc + 7, dataURL.length);
				alert("hai"+retstring);
				
				document.getElementById('form1:digitalsign').value = retstring;
				alert(document.getElementById('form1:digitalsign').value);
			
			}
        
    }    
}
*/

/*  
function onDone()
{
       if(NumberOfTabletPoints() == 0)
       {
           alert("Please sign before continuing");
       }
       else
       {
   SetTabletState(0);
          //RETURN TOPAZ-FORMAT SIGSTRING
           SetSigCompressionMode(1);
           //alert("String Value"+GetSigString());
           document.signatureform.bioSigData.value=GetSigString();
           document.signatureform.sigStringData.value += GetSigString();


           //this returns the signature in Topaz's own format, with biometric information


           //RETURN BMP BYTE ARRAY CONVERTED TO BASE64 STRING
   SetImageXSize(500);
   SetImageYSize(100);
           SetImagePenWidth(5);

           //GetSigImageB64(document.getElementById("SigImg"));
           GetSigImageB64(SigImageCallback);
           
        
           //document.FORM1.sigImageData.value+=b64String;
           //this example returns a bitmap image converted to a base64 string
           //convert the string back to a byte array on the server for final imaging


           //document.FORM1.submit(); //SUBMIT THE FORM HERE
        }

}

*/

function SigImageCallback( str )
{
document.signatureform.sigImageData.value = str;	
document.getElementById('form1:digitalsign').value= str;
//document.getElementById('form1:digitalSign').value= str;
//alert(document.getElementById('form1:digitalsign').value);

}



function initDemo()	{
	ResetParameters();
}

function endDemo()	{
	ResetParameters();
	LcdRefresh(0, 0, 0, 640, 480);
	LCDSetWindow(0, 0, 640, 480);
    SetSigWindow(1, 0, 0, 640, 480);
	KeyPadClearHotSpotList();
	SetLCDCaptureMode(1);
	SetTabletState(0);
}

window.onload = function (){
	initDemo();
};

window.onunload = function (){
		//endDemo();
};
