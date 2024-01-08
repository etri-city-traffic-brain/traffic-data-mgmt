
/************************************************************
 * desc   : 달력구성과 달력 텍스트필드의 초기값 설정
 *		 		파라미터 to_date_id에 null을 넘기면 하나의 달력 세팅 가능
 * syntax : calendar_init(from_date_id,to_date_id)
 * param  : 해당 텍스트필드의 id      
 *************************************************************/
function calendar_init(from_date_id,to_date_id){
	var today = new Date(); 
	var year = today.getUTCFullYear();
	var mon = today.getMonth()+1;
	var date = today.getDate();
	if(mon < 10){
		mon = "0"+mon;
	}
	if(date < 10){
		date = "0"+date;
	}
	
	if(to_date_id==null){
		$("#"+from_date_id).val(year+"-"+mon+"-"+date);
		
		$("#"+from_date_id).datepicker({
			showOn: "button",
			buttonImage: context +"/images/sub/ico_calendar.gif",
			buttonImageOnly: true,
			buttonText: "날짜 선택",
			changeYear: true,
			changeMonth: true,
			nextText: "  ▶   ",
			prevText: "  ◀   ",
			currentText : "오늘날짜",
			monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'],
			dayNamesMin: ['일','월','화','수','목','금','토'],
			monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			dateFormat: "yy-mm-dd"
		});
	}else{
		
		$("#"+to_date_id).val(year+"-"+mon+"-"+date);
		
		var day7 = today - 1000 * 60 * 60 * 24 * 7;
		var before1week = new Date(day7);
		var y = before1week.getUTCFullYear();
		var m = before1week.getMonth()+1;
		var d = before1week.getDate();
		if(m < 10){
			m = "0"+m;
		}
		if(d < 10){
			d = "0"+d;
		}
		$("#"+from_date_id).val(y+"-"+m+"-"+d);
		
		$("#"+from_date_id+",#"+to_date_id).datepicker({
			showOn: "button",
			buttonImage: context +"/images/common/calendar_icon.png",
			buttonImageOnly: true,
			buttonText: "Select date",
			changeYear: true,
			changeMonth: true,
			nextText: "다음달",
			prevText: "이전달",
			monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'],
			dayNamesMin: ['일','월','화','수','목','금','토'],
			monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			dateFormat: "yy-mm-dd"
		});
	}
}

function initCalendar(from_date_id,to_date_id){
	if(to_date_id==null){
		$("#"+from_date_id).datepicker({
			showOn: "button",
			buttonImage: context +"/images/sub/ico_calendar.gif",
			buttonImageOnly: true,
			buttonText: "날짜 선택",
			changeYear: true,
			changeMonth: true,
			nextText: "다음",
			prevText: "이전",
			currentText : "오늘날짜",
			monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'],
			dayNamesMin: ['일','월','화','수','목','금','토'],
			monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			dateFormat: "yy-mm-dd"
		});
	}else{
		$("#"+from_date_id+",#"+to_date_id).datepicker({
			showOn: "button",
			buttonImage: context +"/images/common/calendar_icon.png",
			buttonImageOnly: true,
			buttonText: "Select date",
			changeYear: true,
			changeMonth: true,
			nextText: "다음달",
			prevText: "이전달",
			monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'],
			dayNamesMin: ['일','월','화','수','목','금','토'],
			monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			dateFormat: "yy-mm-dd"
		});
	}
}

/************************************************************
 * desc   : 해당 오브젝트에 시간을 세팅한다(시, 분, 초)
 * syntax : cfTimeCombo('se_01')       
 *param  : objId-입력된 오브젝트ID
 ************************************************************/
function cfTimepicker(timeField){
	$("#"+timeField).datetimepicker({
		language:'en',
		format:"hh:mm:ss",
		pickDate: false
	});
}

/************************************************************
 * desc   : 해당 오브젝트에 시간을 세팅한다(시, 분)
 * syntax : cfTimeCombo2('se_01')       
 *param  : objId-입력된 오브젝트ID
 ************************************************************/
function cfTimepicker2(timeField){
	$("#"+timeField).datetimepicker({
		language:'en',
		format:"hh:mm", 
		pickDate: false,
		pickSeconds: false
	});
}

/************************************************************
 * desc   : 서버에 날짜 정보를 전송하기 전 포괄적인 유효성 검사함수 (날짜 + 시간)
 * syntax : cfValidDateTime('from_date', 'from_time', 'to_date', 'to_time')       
 * param  : html tag ID
 * return : boolean 이상무 - true, 이상 - false
 * msg	  : 검색기간 유효성체크 (날짜 + 시간) 각 서브페이지에서는 이 함수 하나만 호출 
 ************************************************************/
function cfValidDateTime(from_date, from_time, to_date, to_time){
	
	var startDate = cfDateFormat(from_date, from_time);
	var endDate = cfDateFormat(to_date, to_time);
	//필드 유효성
	if(!cfCheckDateTime(startDate, from_date, from_time, "S")) return;
	if(!cfCheckDateTime(endDate, to_date, to_time, "E")) return;
	//기간 유효성   시작기간 < 종료기간
	if(!cfCheckPeriodDateTime(startDate, endDate, from_date, from_time)){
		return false;
	}else{
		return true;
	}
}

/************************************************************
 * desc   : 서버에 날짜와 시간 정보를 전송하기 위한 문자열 변경 함수
 * syntax : cfDateFormat('date','time')       
 * param  : html tag ID
 * return : "YYYYMMDDHH24MISS"형식의 문자열
 * msg	  :  
 ************************************************************/
function cfDateFormat(date, time){
	var dateFormat = "";
	var timeFormat = "";
	if(date != null && date != ""){
		dateFormat = $("#" + date).val().replace(/\-/g, "");
	}
	if(time != null && time != ""){
		timeFormat = $("#" + time).val().replace(/\:/g, "");
	}
	return dateFormat + timeFormat;
}

/************************************************************
 * desc   : 날짜와 시간 폼컴포넌트의 value값에 대한 유효성 체크
 * syntax : cfCheckDateTime('formDate', 'sDate', 'sTime', 'stx')       
 * param  : formDate - 실제전송할 날짜형식(함수적용), sDate, sTime - html tag ID, stx - 'S' = 시작기간 'E' = 종료기간 'A' = 단일
 * return : boolean 이상무 - true, 이상 - false
 * msg	  :  
 ************************************************************/
function cfCheckDateTime(formDate, sDate, sTime, stx){
	
	var year = "";
	var month = "";
	var day = "";
	var time = "";
	var minutes = "";
	var second = "";
	var maxDaysInMonth = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
	var maxDay = "";
	
	var alertMsg = "";
	if(stx == "S"){
		alertMsg = "시작기간 ";
	}else if(stx == "E"){
		alertMsg = "종료기간 ";
	}
	
	try{
		//형식 길이 체크
		if(formDate.length != 14){
			alert(alertMsg + "날짜 형식을 맞추어 주세요 \n 예: 2014-12-26, 00:00:00");
			return false;
		}
		if(isNaN(formDate)){
			alert(alertMsg + "날짜 형식을 맞추어 주세요 \n 예: 2014-12-26, 00:00:00");
			return false;
		}
		year = Number(formDate.substring(0, 4));
		month = Number(formDate.substring(4, 6));
		if( month<1 || month>12 ) {
            alert(alertMsg + "달을 정확히 입력해주세요");
            $("#" + sDate).focus();
			return false;
        }
		day = Number(formDate.substring(6, 8));
		maxDay = maxDaysInMonth[month-1];
        // 윤년 체크
        if( month==2 && ( year%4==0 && year%100!=0 || year%400==0 ) ) {
            maxDay = 29;
        }
		if( day<=0 || day>maxDay ) {
            alert(alertMsg + "일을 정확히 입력해주세요");
            $("#" + sDate).focus();
			return false;
        }
		time = Number(formDate.substring(8, 10));
		if( time<0 || time>23 ) {
            alert(alertMsg + "시간을 정확히 입력해주세요");
            $("#" + sTime).focus();
			return false;
        }
		minutes = Number(formDate.substring(10, 12));
		if( minutes<0 || minutes>59 ) {
            alert(alertMsg + "분을 정확히 입력해주세요");
            $("#" + sTime).focus();
			return false;
        }
		second = Number(formDate.substring(12, 14));
		if( second<0 || second>59 ) {
            alert(alertMsg + "초를 정확히 입력해주세요");
            $("#" + sTime).focus();
			return false;
        }
		return true;
			
	}catch (err){
		console.log(err);
		return false;
	}
}

/************************************************************
 * desc   : 기간 유효성 체크함수   시작기간 < 종료기간 (날짜 + 시간)
 * syntax : cfCheckPeriodDateTime('startDate', 'endDate', 'from_date', 'from_time'))       
 * param  : startDate, endDate - 실제전송할 날짜형식(함수적용), from_date, from_time - html tag ID
 * return : boolean 이상무 - true, 이상 - false
 * msg	  :  
 ************************************************************/
function cfCheckPeriodDateTime(startDate, endDate, from_date, from_time){ 
	if(endDate - startDate < 0){ 
		alert("시작기간이 종료기간보다 앞서야 합니다.");
		
		//날짜가 틀렸는지 시간이 틀렸는지 확인하여 포커스
		if(endDate.substring(0, 8) - startDate.substring(0, 8) < 0){
			$("#" + from_date).focus(); 
		}else{
			$("#" + from_time).focus();
		}
		return false;
	}
	return true;
}

/************************************************************
 * desc   : 서버에 날짜 정보를 전송하기 전 포괄적인 유효성 검사함수 (날짜만)
 * syntax : cfValidOnlyDate('from_date', 'to_date')       
 * param  : html tag ID
 * return : boolean 이상무 - true, 이상 - false
 * msg	  : 시간없이 달력기간만 전송하는 화면에서 호출(시작기간 ~ 종료기간)
 ************************************************************/
function cfValidOnlyDate(from_date, to_date){
	var startDate = cfDateType(from_date);
	var endDate = cfDateType(to_date);
	//필드 유효성
	if(!cfCheckOnlyDate(startDate, from_date, "S")) return;
	if(!cfCheckOnlyDate(endDate, to_date, "E")) return;
	//기간 유효성   시작기간 < 종료기간
	if(!cfCheckPeriodOnlyDate(startDate, endDate, from_date)){
		return false;
	}else{
		return true;
	}
}

/************************************************************
 * desc   : 서버에 날짜 정보를 전송하기 위한 문자열 변경 함수
 * syntax : cfDateType('date')       
 * param  : date - html tag ID
 * return : "YYYYMMDD"형식의 문자열
 * msg	  :  
 ************************************************************/
function cfDateType(date){
	var dateFormat = "";
	if(date != null && date != ""){
		dateFormat = $("#" + date).val().replace(/\-/g, "");
	}
	return dateFormat;
}

/************************************************************
 * desc   : 날짜와 시간 폼컴포넌트의 value값에 대한 유효성 체크 (날짜만 전송)
 * syntax : cfCheckOnlyDate('formDate', 'sDate', 'stx')       
 * param  : formDate - 실제전송할 날짜형식(함수적용된 변수), sDate - html tag ID, 
 * 			stx - 'S' = 시작기간 'E' 종료기간 'A' 특정날짜
 * return : boolean 이상무 - true, 이상 - false
 * msg	  : 기간이 아닌 단순 특정날짜를 전송해야하는 화면에서도 사용가능
 ************************************************************/
function cfCheckOnlyDate(formDate, sDate, stx){
	
	var year = "";
	var month = "";
	var day = "";
	var maxDaysInMonth = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
	var maxDay = "";
	
	var alertMsg = "";
	if(stx == "S"){
		alertMsg = "시작기간 ";
	}else if(stx == "E"){
		alertMsg = "종료기간 ";
	}else if(stx == "A"){
		alertMsg = "";
	}
	
	try{
		if(formDate.length != 8){
			alert(alertMsg + "날짜 형식을 맞추어 주세요 \n 예: 2014-12-26");
			$("#" + sDate).focus();
			return false;
		}
		if(isNaN(formDate)){
			alert(alertMsg + "날짜 형식을 맞추어 주세요 \n 예: 2014-12-26"); 
			$("#" + sDate).focus();
			return false;
		}
		year = Number(formDate.substring(0, 4));
		month = Number(formDate.substring(4, 6));
		if( month<1 || month>12 ) {
            alert(alertMsg + "달을 정확히 입력해주세요");
            $("#" + sDate).focus();
			return false;
        }
		day = Number(formDate.substring(6, 8));
		maxDay = maxDaysInMonth[month-1];
        // 윤년 체크
        if( month==2 && ( year%4==0 && year%100!=0 || year%400==0 ) ) {
            maxDay = 29;
        }
		if( day<=0 || day>maxDay ) {
            alert(alertMsg + "일을 정확히 입력해주세요");
            $("#" + sDate).focus();
			return false;
		}
		return true;
	}catch (err){
		console.log(err);
		return false;
	}
}

/************************************************************
 * desc   : 기간 유효성 체크함수   시작기간 < 종료기간 (날짜만)
 * syntax : cfCheckPeriodOnlyDate('startDate', 'endDate', 'from_date')       
 * param  : startDate, endDate - 실제전송할 날짜형식(함수적용), from_date - html tag ID
 * return : boolean 이상무 - true, 이상 - false
 * msg	  :  
 ************************************************************/
function cfCheckPeriodOnlyDate(startDate, endDate, from_date){ 
	if(endDate - startDate < 0){ 
		alert("시작기간이 종료기간보다 앞서야 합니다.");
		
		//날짜가 틀렸는지 시간이 틀렸는지 확인하여 포커스
		$("#" + from_date).focus(); 
		return false;
	}
	return true;
} 

/************************************************************
 * desc   : 해당 오브젝트에 Spinner 를 삽입한다
 * syntax : cfSpinFormat('date', 'step')       
 * param  : date - html tag ID, step - 증가값
 * msg	  : 
 ************************************************************/
function cfSpinFormat(spinFeild, step){
	$("#"+spinFeild).spinner({
		step	: 	step,
		max		:	255, 
		min		:	0
	});
	$('#'+spinFeild).val('0');
}

/************************************************************
desc   : 오늘 날짜를 구하는 함수
syntax : cfGetToday(objID)
param  : objID = textField ID
return : date
*************************************************************/
function cfGetToday(textFieldId) {
	var now = new Date();

    var year= now.getFullYear();
    var mon = (now.getMonth()+1)>9 ? ''+(now.getMonth()+1) : '0'+(now.getMonth()+1);
    var day = now.getDate()>9 ? ''+now.getDate() : '0'+now.getDate();
            
    var chan_val = year + '-' + mon + '-' + day;
    if(textFieldId == "snr_id"){
    	chan_val = year + mon + day;
    }
    $("#"+textFieldId).val(chan_val);
}

/************************************************************
desc   : 현재 시간을 구하는 함수
syntax : cfGetTime(to_time)
param  : objID = textField ID
return : time
*************************************************************/
function cfGetTime(textFieldId){
	var now = new Date();
	
	var hh = now.getHours()>9 ? ''+now.getHours() : '0'+now.getHours();
	var mm = now.getMinutes()>9 ? ''+now.getMinutes() : '0'+now.getMinutes();
	var ss = now.getSeconds()>9 ? ''+now.getSeconds() : '0'+now.getSeconds();
	
	var chan_val = hh + ':' + mm + ':' + ss;
    $("#"+textFieldId).val(chan_val);
}

/************************************************************
desc   : 3분전 시간을 구하는 함수
syntax : cfGetTimeBf3m(from_time)
param  : objID = textField ID
return : time
*************************************************************/
function cfGetTimeBf3m(textFieldId){
	var now = new Date();
	var before3m = now - 1000 * 60 * 3;
	var day = new Date(before3m);
	
	var hh = day.getHours()>9 ? ''+day.getHours() : '0'+day.getHours();
	var mm = day.getMinutes()>9 ? ''+day.getMinutes() : '0'+day.getMinutes();
	var ss = day.getSeconds()>9 ? ''+day.getSeconds() : '0'+day.getSeconds(); 
	
	var chan_val = hh + ':' + mm + ':' + ss;
    $("#"+textFieldId).val(chan_val);
    
}

/************************************************************
desc   : 10분 후 시간을 구하는 함수
syntax : cfGetTimeAf10m(objID)
param  : objID = textField ID
return : time
*************************************************************/
function cfGetTimeAf10m(textFieldId){
	var now = new Date();
	//var after10m = now - 1000 * 60 * 3;
	//var day = new Date(after10m);
	var day = new Date(Date.parse(now) + 1000 * 60 * 10); 
	
	var hh = day.getHours()>9 ? ''+day.getHours() : '0'+day.getHours();
	var mm = day.getMinutes()>9 ? ''+day.getMinutes() : '0'+day.getMinutes();
	var ss = day.getSeconds()>9 ? ''+day.getSeconds() : '0'+day.getSeconds(); 
	
	var chan_val = hh + ':' + mm + ':' + ss;
    $("#"+textFieldId).val(chan_val);
    
}

/**********************************************************************************************
desc   : 입력된 키코드로 숫자여부 리턴
syntax : cfOnylInputNum(0)       
param  : 포함여부(2:'.', 1:'-', 0:미포함)
return : boolean
***********************************************************************************************/
function cfOnylInputNum(arg) { 
	var keyList = "";	//숫자키 리스트
	
	//ctrl+c인 경우 true
	if ( event.ctrlKey && event.keyCode == 67 ) {
		return true;
	}
	
	//ctrl+x인 경우 true
	if ( event.ctrlKey && event.keyCode == 88 ) {
		return true;
	}
	
	//ctrl+v인 경우 true
	if ( event.ctrlKey && event.keyCode == 86 ) {
		return true;
	}
	
	if (arg == "2") {
		keyList = [48, 49, 50, 51, 52, 53, 54, 55, 56, 57,  
	               8, 35, 36, 37, 38, 39, 40, 45, 46, 190,
	               9, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 110];
	}
	else if ( arg == "1" ) {
		keyList = [48, 49, 50, 51, 52, 53, 54, 55, 56, 57,  
	               8, 35, 36, 37, 38, 39, 40, 45, 46, 189,
	               9, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105];
	} else if ( arg == "0" ){
		keyList = [48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 
	               8, 9];
	}

	//tab, Backspace , End, Home, left, up, right, down,  insert, delete, dash, number pad
	for(var i = 0; i < keyList.length; i++){		
		if( event.keyCode == keyList[i])
			return true;
	}
	return false;
} 

/************************************************************
desc   : lcd_ontime, lcd_offtime 유효성검사하는 함수
syntax : cfCheckHM(inputId)
param  : inputId = lcd_ontime, lcd_offtime 2350형식의 4자리 숫자 형태의 텍스트필드 id
return : boolean
*************************************************************/
function cfCheckHM(inputId){
	var timeFormat = $("#" + inputId).val();
	var timeId = $("#" + inputId).attr("id");
	var alertMsg = (timeId == "lcd_ontime") ? "ON TIME " : "OFF TIME ";
	
	if(timeFormat.length < 4){
		alert(alertMsg + "날짜 형식을 맞추어 주세요. 예: 0500(시간분)");
		$("#" + inputId).focus();
		return false;
	}
	if(isNaN(timeFormat)){
		alert(alertMsg + "날짜 형식을 맞추어 주세요. 예: 0500(시간분)");
		$("#" + inputId).focus();
		return false;
	}
	var time = Number(timeFormat.substring(0, 2));
	if( time<0 || time>23 ) {
        alert(alertMsg + "시간을 정확히 입력해주세요");
        $("#" + inputId).focus();
		return false;
    }
	var minutes = Number(timeFormat.substring(2, 4));
	if( minutes<0 || minutes>59 ) {
        alert(alertMsg + "분을 정확히 입력해주세요");
        $("#" + inputId).focus();
		return false;
    }
	return true;
}