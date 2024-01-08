var dMinx = 928063.24826344;
var dMiny = 1939216.096867; 
var dMaxx = 931377.24826344;
var dMaxy = 1940640.096867;
var initCenterX = 126.705461;
var initCenterY = 37.455882;

var iLevel = 1;

var ServiceMap = null;
var tooltip = null;
var clickX = null;
var clickY = null;
var clickLngLat = null;

var template = {
        "strokeWidth": "${getWidth}" ,
        "strokeOpacity" : 0.3,	        
        "strokeColor": "${getColor}",
        "strokeLinecap" : "round"//,
        //"strokeDashstyle" : "dash"
};

var colorContext = {
	    getColor: function(feature) {
	    	return "#0042ED";
	    },
	    getWidth: function(feature) {
	    	switch( feature.layer.map.getZoom() ){
				case 13 : return 26;
				case 12 : return 26;
				case 11 : return 24;
				case 10 : return 24;
				case 9 : return 22;
				case 8 : return 20;	
				default : return 20;
	    	}
	    }
	};

/************************************************************
 * @type function 
 * @see 선택된 시설물 위치로 이동
 * @param lng, lat
 * @returns 
 ************************************************************/
function fnPanToFacilltyLocation(x, y){	
	ServiceMap.setCenter(x, y);
	ServiceMap.zoom(11);
}

(function ($) {
    // 숫자 제외하고 모든 문자 삭제.
    $.fn.removeText = function(_v){
        if (typeof(_v) === "undefined"){
            $(this).each(function(){
                this.value = this.value.replace(/[^0-9]/g,'');
            });
        }else{
            return _v.replace(/[^0-9]/g,'');
        }
    };
     
    $.fn.numberFormat = function(_v){
        this.proc = function(_v){
            var number = '', cutlen = 3, comma = ',' , i = 0, len = _v.length,
                  mod = (len % cutlen), k = cutlen - mod;
                 
            for (i; i < len; i++) {
                number = number + _v.charAt(i);
                if (i < len - 1){
                    k++;
                    if ((k % cutlen) == 0){
                        number = number + comma;
                        k = 0;
                    }
                }
            }
            
            if( number.length > 1 && number.charAt(0) == "0" ){
            	number = number.substring(1, number.length);
            }
            return number;
        };
         
        var proc = this.proc;
        if (typeof(_v)==="undefined"){
            $(this).each(function(){
                this.value = proc($(this).removeText(this.value));
            });
        }else{
            return proc(_v);
        }
    };
     
    // 위 두개의 합성.
    // 콤마 불필요시 numberFormat 부분을 주석처리.
    $.fn.onlyNumber = function (p) {
        $(this).each(function(i) {
            $(this).attr({'style':'text-align:right'});
             
            this.value = $(this).removeText(this.value);
            this.value = $(this).numberFormat(this.value);
             
            $(this).bind('keypress keyup',function(e){
                this.value = $(this).removeText(this.value);
                this.value = $(this).numberFormat(this.value);
            });
        });
    };
     
})(jQuery);

function AjaxToJson(url, data, fnSuccess, fnError){
	$.ajax({
			url : url,
			type : "post",
			dataType: "json",
			data:data,
			success:fnSuccess,
			error:fnError
	});
};

function AjaxToXml(url, data, fnSuccess, fnError){
	$.ajax({
			url : url,
			type : "post",
			dataType: "xml",
			data:data,
			success:fnSuccess,
			error:fnError
	});
};

function AjaxToText(url, data, fnSuccess, fnError){
	$.ajax({
			url : url,
			type : "post",
			dataType: "text",
			data:data,
			success:fnSuccess,
			error:fnError
	});
};

//필드값 Number 체크 함수
function IsNumber(strNumber) {
    //var reg = RegExp(/^ (\d|-)?(\d|,)*\.?\d*$/);
    var reg = RegExp(/^\d*\d*$/);    
        
    return reg .test(strNumber);      
    
}
//필드값 Number 체크 함수
function checkNumber(field) {

	if( !IsNumber(field.value) ) {
      alert("숫자형식만 입력해주십시오.");
      field.value="";
      field.focus();
      field.select();
   }
}
//IP 체크 함수
function checkIp(ip){
		
	var reg = /^(\d{1,3})\.(\d{1,3})\.(\d{1,3})\.(\d{1,3})$/;
	
	return reg .test(ip);
	
}

//이메일 확인 함수
function checkEmail(myEmail) {
          
	var regMail =/^[_a-zA-Z0-9-]+@[._a-zA-Z0-9-]+.[a-zA-Z]+$/; // Email 주소의 정규식
    if(!regMail.test(myEmail)) { 
    	return false ;
    }else { 
    	return true ;
    }
}

// 게시글 입력 특수문자 체크(태그입력 방지)
function fnXXSS(Objectname) {
	var intErr = 0;
	var strValue = Objectname.value;
	var re = /[<>;'&=^]/gi; //특수문자 정규식 변수 선언
	for (var i = 0; i < strValue.length; i++) {
		var retCode = strValue.charCodeAt(i);
		retCode = parseInt(retCode);
		if(re.test(strValue)) {
			intErr = -1;
			break;
		}
	}
	if (intErr < 0) {
		alert("특수문자 중  < > ; ' & = ^ 는 사용할수 없습니다.");
		Objectname.value = strValue.replace(/[<>;'&=^]/gi,'');
	}
}

//특정 키 이벤트(e는 키코드)
function Keycode(e){
	var result=0;
	if(window.event)
		result = window.event.keyCode;
	else if(e)
		result = e.which;
	return result;
}	

//-----------------------------
//쿠키가져오기
//-----------------------------
function getCookie( name ){
var nameOfCookie = name + "=";
var x = 0;
var endOfCookie = null;
while ( x <= document.cookie.length ){
	var y = (x+nameOfCookie.length);
	if ( document.cookie.substring( x, y ) == nameOfCookie ) {
	  if ( (endOfCookie=document.cookie.indexOf( ";", y )) == -1 )
		endOfCookie = document.cookie.length;
	  return unescape( document.cookie.substring( y, endOfCookie ) );
	}
	x = document.cookie.indexOf( " ", x ) + 1;
	if ( x == 0 )
	  break;
}
return "";
}

//-----------------------------
//숫자만 가능
//-----------------------------
function delChar(obj) {
	obj.value = obj.value.replace(/[^0-9]+/g, "");
}

//-----------------------------
//숫자와 , 만 가능
//-----------------------------
function delChar01(obj) {
	obj.value = obj.value.replace(/[^,0-9]+/g, "");
}

//-----------------------------
//숫자와 - 만 가능
//-----------------------------
function delChar02(obj) {
	obj.value = obj.value.replace(/[^-0-9]+/g, "");
}

//-----------------------------
//숫자와 - 만 가능
//-----------------------------
function delKey() {
if (event.keyCode < 47 || event.keyCode > 58) event.returnValue =false;
}


//-----------------------------
//알파벳 가능
//-----------------------------
function chkChar(obj) {
	obj.value = obj.value.replace(/[^0-9,a-z]+/g, "");
}

//-----------------------------
//알파벳 가능
//-----------------------------
function emailOk( theField ){
 
 if( theField.length > 0 ){
     if( theField.indexOf("@") == -1 || theField.indexOf(".") == -1 ){
            // alert("\n 정확한 이메일 주소인지 확인하십시오.");               
             return false;
     }
 }
 return true;
}

//-----------------------------
//빈공간 체크
//-----------------------------
function delSpace(str) {
	var result = null;
	for (var i=0; i<str.length; i++) {
		if ( str.charAt(i) != ' ') {
			result = str.substring(i, str.length);
			break;
		}
	}
	return result;
}

//-----------------------------
//Null 값 체크
//-----------------------------
function isNull(str) {
	var strResult = delSpace(str);
	var isnull = false;

	if (strResult == null)
		isnull = true;
	else if (strResult.length < 1)
		isnull = true;

	return isnull;
}

//-----------------------------
//한글체크
//-----------------------------
function checkHangul(Str) {
	var RetVal  = true;
	var cha = "";
	
	for (var i=0; i<=Str.length -1; i++) {
		cha = Str.substring(i,i+1);

		if ((cha >= "0" && cha <= "9") || (cha >= "a"  && cha <= "z") || (cha >= "A"  && cha <= "Z")) {
			RetVal = false; 
			break;
		}
	}

	return RetVal;
}

//-----------------------------
//특수문자체크
//-----------------------------
function checkSpecialchar(Str) {
	for (var i=0; i<Str.length; i++) {
		if ((Str.charAt(i) == "~") ||  (Str.charAt(i) == "`") ||(Str.charAt(i) == "!") || (Str.charAt(i) == "@") ||
			(Str.charAt(i) == "#") || (Str.charAt(i) == "$") || (Str.charAt(i) == "%") || (Str.charAt(i) == "^") ||
			(Str.charAt(i) == "&") || (Str.charAt(i) == "*") || (Str.charAt(i) == "(") || (Str.charAt(i) == ")") ||
			(Str.charAt(i) == "+") || (Str.charAt(i) == "=") ||
			(Str.charAt(i) == "\\") || (Str.charAt(i) == "|") || (Str.charAt(i) == "{") || (Str.charAt(i) == "}") ||
			(Str.charAt(i) == "[") || (Str.charAt(i) == "]") || (Str.charAt(i) == ";") || (Str.charAt(i) == ":") ||
			(Str.charAt(i) == "'") || (Str.charAt(i) == "\"") || (Str.charAt(i) == "<") || (Str.charAt(i) == ">") ||
			(Str.charAt(i) == ",") || (Str.charAt(i) == ".") || (Str.charAt(i) == "?") || (Str.charAt(i) == "/") )
			return	true;
	}
	return	false;
}


//-----------------------------
//이메일체크
//-----------------------------
function checkEmailAddress(str) {
	var supported = 0;

	if (window.RegExp) {
		var tempStr = "a";
		var tempReg = new RegExp(tempStr);

		if (tempReg.test(tempStr))
			supported = 1;
	}

	if (!supported)
		return (str.indexOf(".") > 2) && (str.indexOf("@") > 0);

	var r1 = new RegExp("(@.*@)|(\\.\\.)|(@\\.)|(^\\.)");
	var r2 = new RegExp("^.+\\@(\\[?)[a-zA-Z0-9\\-\\.]+\\.([a-zA-Z]{2,3}|[0-9]{1,3})(\\]?)$");

	return (!r1.test(str) && r2.test(str));
}

//공지사항 상세조회
function fnNoticeDetail(postsequence){
	$("#postsequence").val(postsequence);	
	$("#form").attr("action",  "notice_view.do");
	$("#form").submit();	
}

//공지사항 조회
function fnNotice(pageNo){
	$("#currentPageNo").val(pageNo);
	$("#form").attr("action",  "notice.do");
	$("#form").submit();
}

function WinPop(Url, Name, Width, Height, Opt){
	
	if (navigator.appName.indexOf("Internet Explorer")!= -1){
		var badBrowser = navigator.appVersion.indexOf("MSIE 9") == -1;
		var badBrowser2 = navigator.appVersion.indexOf("MSIE 10") == -1;
		if(!badBrowser){
			//
			var OptV = "";
			if (Opt == 1) {
				OptV=',toolbar=no,location=no,directories=no,status=no,menuBar=no,scrollBars=no,resizable=no';
			} //모든조건 no
			else if (Opt == 2){
				OptV=',toolbar=no,location=no,directories=no,status=no,menuBar=no,scrollBars=no,resizable=auto';
			} //사이즈조절만 가능
			else if (Opt == 3){
				OptV=',toolbar=no,location=no,directories=no,status=no,menuBar=no,scrollbars=yes,resizable=no';
			} //스크롤바만 가능
			var Option='width=' + Width + ',height=' + Height + OptV;
			var winup=window.open(Url, "", Option);
			if (!winup) {
				alert('팝업 차단을 허용해 주세요.');
			}
			else {
				winup.focus();
			}
		}else if(!badBrowser2){
			var OptV = "";
			if (Opt == 1) {
				OptV=',toolbar=no,location=no,directories=no,status=no,menuBar=no,scrollBars=no,resizable=no';
			} //모든조건 no
			else if (Opt == 2){
				OptV=',toolbar=no,location=no,directories=no,status=no,menuBar=no,scrollBars=no,resizable=auto';
			} //사이즈조절만 가능
			else if (Opt == 3){
				OptV=',toolbar=no,location=no,directories=no,status=no,menuBar=no,scrollbars=yes,resizable=no';
			} //스크롤바만 가능
			var Option='width=' + Width + ',height=' + Height + OptV;
			var winup=window.open(Url, Name, Option);
			if (!winup) {
				alert('팝업 차단을 허용해 주세요.');
			}
			else {
				winup.focus();
			}
		}
	}else{
		var OptV = "";
		if (Opt == 1) {
			OptV=',toolbar=no,location=no,directories=no,status=no,menuBar=no,scrollBars=no,resizable=no';
		} //모든조건 no
		else if (Opt == 2){
			OptV=',toolbar=no,location=no,directories=no,status=no,menuBar=no,scrollBars=no,resizable=auto';
		} //사이즈조절만 가능
		else if (Opt == 3){
			OptV=',toolbar=no,location=no,directories=no,status=no,menuBar=no,scrollbars=yes,resizable=no';
		} //스크롤바만 가능
		var Option='width=' + Width + ',height=' + Height + OptV;
		var winup=window.open(Url, Name, Option);
		if (!winup) {
			alert('팝업 차단을 허용해 주세요.');
		}
		else {
			winup.focus();
		}
	}
}

function WinNoSizePop(Url, Name, Opt){
	var OptV = "";
	if (Opt == 1) {
		OptV=',toolbar=no,location=no,directories=no,status=no,menuBar=no,scrollBars=no,resizable=no';
	} //모든조건 no
	else if (Opt == 2){
		OptV=',toolbar=no,location=no,directories=no,status=no,menuBar=no,scrollBars=no,resizable=auto';
	} //사이즈조절만 가능
	else if (Opt == 3){
		OptV=',toolbar=no,location=no,directories=no,status=no,menuBar=no,scrollbars=yes,resizable=no';
	} //스크롤바만 가능	
	var winup=window.open(Url, Name, OptV);
	if (!winup) {
		alert('팝업 차단을 허용해 주세요.');
	}
	else {
		winup.focus();
	}
}

function MakeFlashString(source,id,width,height,wmode, otherParam)
{	
	return "<object classid=\"clsid:d27cdb6e-ae6d-11cf-96b8-444553540000\" codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,22,0\" width="+width+" height="+height+" id="+id+"><param name=wmode value="+wmode+" /><param name=movie value="+source+" /><param name=quality value=high />"+otherParam+"<embed src="+source+" quality=high wmode="+wmode+" type=\"application/x-shockwave-flash\" pluginspage=\"http://www.macromedia.com/shockwave/download/index.cgi?p1_prod_version=shockwaveflash\" width="+width+" height="+height+"></embed></object>";
}
function MakeMovingString(source,width,height)
{	
	return "<embed src="+source+" width="+width+" height="+height+" type='application/x-mplayer2'  pluginspage='http://www.microsoft.com/windows/mediaplayer/download/default.asp' AUTOSTART=1 EnableContextMenu=0 invokeURLs=0 SHOWSTATUSBAR=1></embed>"; //컨트롤박스, 상태박스가 보인다.
}

//-----------------------------
//링크 점선 제거 함수
//-----------------------------
function bluring(){ 
if(event.srcElement.tagName=="A"||event.srcElement.tagName=="IMG") document.body.focus(); 
} 
document.onfocusin=bluring;

//-----------------------------
//주민등록번호 검사 
//-----------------------------
function juminchk(jumin_no){
	var residentNum	=	 jumin_no;

	if (residentNum != "9999999999999" )
	{	
		var a = new Array(13);
	
		for (var i=0; i < 13; i++) {
			a[i] = parseInt(residentNum.charAt(i));
		}
	
		var j = a[0]*2 + a[1]*3 + a[2]*4 + a[3]*5 + a[4]*6 + a[5]*7 + a[6]*8 + a[7]*9 + a[8]*2 + a[9]*3 + a[10]*4 + a[11]*5;
		var d = j % 11;
		var k = 11 - d;
	
		if (k > 9) {
				k = k % 10;
		}
	
		if (k != a[12]) {
			return false;
		}
		else {
			return true; 
		}
	}
}

function strToInt(s){
	try{
		s = s.replace(/px/gi, "");
		return parseInt(s);
	}catch(exception){
		console.error("strToInt Error [" + exception.name + "] : "+ exception.message);
	}finally{
		
	}	
}