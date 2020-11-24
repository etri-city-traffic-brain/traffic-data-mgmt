var regEmail = /([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
var SelectedselectAccordion = 0;


function CheckEmail(){
	if (!regEmail.test($("#txtEmail").val())){
		return false;
	}
	return true;
}


function InitializeDatetimepicker(){	
	$('[role="date"]').datetimepicker({
		  locale: 'ko',
	      format: 'YYYY-MM-DD'
	}).on('dp.change', function(e){
		//$("#" + $(this).data("target")).val(e.date.format("YYYYMMDD"));
	});
	
	$('[role="time"]').datetimepicker({
		  locale: 'ko',
	      format: 'HH:mm:ss'
	}).on('dp.change', function(e){ 
		//$("#" + $(this).data("target")).val(e.date.format("HHmmss"));
	});
	
	$('[role="time"]').css("margin-top", "4px");
}

function InitializButtonCheckbox(){
	$('.button-checkbox').each(function () {
        // Settings
        var $widget = $(this),
            $button = $widget.find('button'),
            $checkbox = $widget.find('input:checkbox'),
            color = $button.data('color'),
            settings = {
                on: {
                    icon: 'glyphicon glyphicon-check'
                },
                off: {
                    icon: 'glyphicon glyphicon-unchecked'
                }
            };
        
        // Event Handlers
        $button.on('click', function () {
            $checkbox.prop('checked', !$checkbox.is(':checked'));
            $checkbox.triggerHandler('change');
            updateDisplay();
        });
        
        $checkbox.on('change', function () {
            updateDisplay();
        });
        
        // Actions
        function updateDisplay() {
            var isChecked = $checkbox.is(':checked');

            // Set the button's state
            $button.data('state', (isChecked) ? "on" : "off");
            $checkbox.val((isChecked) ? "1" : "0");
            
            // Set the button's icon
            $button.find('.state-icon')
                .removeClass()
                .addClass('state-icon ' + settings[$button.data('state')].icon);

            // Update the button's color
            if (isChecked) {
                $button
                    .removeClass('btn-default')
                    .addClass('btn-' + color + ' active');
            }
            else {
                $button
                    .removeClass('btn-' + color + ' active')
                    .addClass('btn-default');
            }
        }
        
        // Initialization
        function init() {

            updateDisplay();

            // Inject the icon if applicable
            if ($button.find('.state-icon').length == 0) {
                $button.prepend('<i class="state-icon ' + settings[$button.data('state')].icon + '"></i> ');
            }
        }
        init();
    });
}

function InitializButtonUpload(){
	$('[role="upload-file"]').on('change', function(){  // 값이 변경되면
		if(window.FileReader){  // modern browser
			var filename = $(this)[0].files[0].name;
		} else {  // old IE
			var filename = $(this).val().split('/').pop().split('\\').pop();  // 파일명만 추출
		}		
		
		// 추출한 파일명 삽입
		$("#" + $(this).data("target")).val(filename);
	});	
}

function InitializSelectpicker(){	  
	$('[role="region-select"]').on('change', function (e) {		
		var select = $(this).data("sub");
		var part  = $(this).data("part");
		
		if(this.value == "0" || this.value == ""){
			$('[role=' + select + ']').empty();
			$('[role=' + select + ']').append('<option value="0" selected="selected" data-tokens="전체">전체</option>');
						
			$('[role=' + part + ']').empty();
			$('[role=' + part + ']').append('<option value="1" selected="selected">1</option>');
			
			return;
		}		 
		
		$.ajax({ 
		  type: "POST",
		  url: "getDong",
		  dataType : 'json',
		  data: {"sigu_cd" : this.value},			  
		  success: function(responseData){				  
			  $("#ajax").remove();
			  $('[role=' + select + ']').empty();
			  $('[role=' + select + ']').append('<option value="0" selected="selected" data-tokens="전체">전체</option>');				  
			  $.each(responseData, function(index, item){
				  var option;
				  option = '<option value="' + item.dong_cd + '" data-tokens="' + item.dong_nm + '">'+ item.dong_nm +'</option>';
				  $('[role=' + select + ']').append(option);
			  });
		  }
		});
		
		var regionValue = this.value;
		
		$.ajax({ 
			  type: "POST",
			  url: "getPartitionCnt",
			  dataType : 'json',
			  data: {"sigu_cd" : this.value},			  
			  success: function(responseData){				  
				  $("#ajax").remove();
				  $('[role=' + part + ']').empty();		
				  $('[role=' + part + ']').data("region", regionValue);
		
				  $.each(responseData, function(index, item){
					  var option;
					  if(index == 0){
						  option = '<option value="' + item + '" selected="selected">'+ item +'</option>';
					  }else{
						  option = '<option value="' + item + '" >'+ item + '</option>';
					  }
					  $('[role=' + part + ']').append(option);
				  });
			  }
			});
	});	
}

function RequestXml(btn){		
	if(!ValidateParam(btn)){
		return;
	}		
	
	$("#include").val($(btn).data("include")); 
	$("#search_form").attr("action", $(btn).data("url"));
	$("#search_form").submit();
}

function InitializeDisplay(){	
	$(".ol-control.ol-overviewmap").css("left", $( "#menu" ).css("width"));
	InitializeDatetimepicker();
	InitializButtonCheckbox();
	InitializButtonUpload();
	InitializSelectpicker();
} 

$(document).ready(function(){
	InitializeMap('map', "vworld");
	InitializeDisplay();	
	
});

function UploadXml(btn){	
	if(!CheckUploadFile($("#" + $(btn).data("target"))[0].files[0]))
		return;
	
	var formData = new FormData();
	formData.append("uploadFile", $("#" + $(btn).data("target"))[0].files[0]);
	$.ajax({
	  type: "POST",
	  url: $(btn).data("url"),
	  data: formData,
	  processData: false, 
	  contentType: false,
	  success: function(responseData){
		  $("#ajax").remove();
		  alert(responseData); 
	  }
	});
}

function UploadXml2(btn){	
	if(!CheckUploadFile($("#" + $(btn).data("target"))[0].files[0]))
		return;
	
	var formData = new FormData();
	formData.append("uploadFile", $("#" + $(btn).data("target"))[0].files[0]);
	$.ajax({
	  type: "POST",
	  url: $(btn).data("url"),
	  data: formData,
	  processData: false, 
	  contentType: false
	});
}

function CheckUploadFile(file){
	if( file == null ){
		alert("Select a file to upload");
		return false;
	}
	
	if(window.File && window.FileReader && window.FileList && window.Blob){
		fsize = file.size;
		if( fsize == 0){
			alert("Upload file size is 0");
			return false;
		}

		ftype = file.type;							
		if( ftype != "text/xml" && ftype != "application/vnd.ms-excel" && ftype != "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"){
			alert("The allowed file is not in the format.");
			return false;
		}		
	}
	
	return true;
}

function UploadJson(btn){
	if(!CheckJsonUploadFile($("#" + $(btn).data("target"))[0].files[0]))
		return;
	
	var formData = new FormData();
	formData.append("uploadFile", $("#" + $(btn).data("target"))[0].files[0]);
	$.ajax({
	  type: "POST",
	  url: $(btn).data("url"),
	  data: formData,
	  processData: false, 
	  contentType: false,
	  success: function(responseData){
		  $("#ajax").remove();
		  alert(responseData); 
	  }
	});	 
}

function CheckJsonUploadFile(file){
	if( file == null ){
		alert("Select a file to upload");
		return false;
	}
	
	if(window.File && window.FileReader && window.FileList && window.Blob){
		fsize = file.size;
		if( fsize == 0){
			alert("Upload file size is 0");
			return false;
		}

		ftype = file.type;							
		if( ftype != "application/json" ){
			alert("The allowed file is not in the format.");
			return false;
		}		
	}
	
	return true;
}

function ValidateParam(btn){
	if($('#reqDate').val() == ""){
		alert("조회일자를 선택하십시오.");
		return false;
	}
	
	if($('#fromTime').val() == ""){
		alert("시작시간을 선택하십시오.");
		return false;
	}
	
	if($('#toTime').val() == ""){
		alert("종료시간을 선택하십시오.");
		return false;
	}
	
	if($('#fromTime').val() >= $('#toTime').val()){
		alert("선택시간이 올바르지 않습니다.");
		return false;
	}
	
	if($(btn).data("url") == "requestScenearioByCoordinate"){
		if($("#minX").val() == "" || $("#minY").val() == "" ||
				$("#maxX").val() == "" ||$("#maxY").val() == ""){
			alert("영역을 선택하십시오.");
			return false;
		}
	}else if($(btn).data("url") == "requestPartitionedScenarioByMaster"){
		if($("#partitions").val() == ""){
			alert("파티션수를 입력하십시오.");
			return false;	
		}
	}else if($(btn).data("url") == "requestPartitionedScenarioByWorker"){
		if($("#partitions").val() == ""){
			alert("파티션수를 입력하십시오.");
			return false;	
		}
		if($("#partitionNo").val() == ""){
			alert("파티션번호를 입력하십시오.");
			return false;	
		}		
	}
	
	return true;
}

//팝업 Close 기능
function close_pop(flag) {
     $('#uploadModal').hide();
     $('#downloadModal').hide();
     $('#uploadJsonModal').hide();
};

function selectAccordion(inclue){
	SelectedselectAccordion = inclue;
}

function RequestScenarioByRegion(){
	if($('#ScenarioByRegion_form #fromDate').val() == ""){
		alert("시작날짜를 선택하십시오.");
		return false;
	}
	
	if($('#ScenarioByRegion_form #toDate').val() == ""){
		alert("종료날짜를 선택하십시오.");
		return false;
	}
		
	if($('#ScenarioByRegion_form #fromTime').val() == ""){
		alert("시작시간을 선택하십시오.");
		return false;
	}
	
	if($('#ScenarioByRegion_form #toTime').val() == ""){
		alert("종료시간을 선택하십시오.");
		return false;
	}

	
	if($("#ScenarioByRegion_form #partitions").val() != "1" && $("#ScenarioByRegion_form #region").val() == "0" ){
		alert("파티션기능은 구를 선택하셔야 합니다.");
		return false;	
	}
	
	$("#ScenarioByRegion_form #fromDate").val( $("#ScenarioByRegion_form #fromDate").val().replace(/-/g, '')  );
	$("#ScenarioByRegion_form #toDate").val( $("#ScenarioByRegion_form #toDate").val().replace(/-/g, '')  );
	$("#ScenarioByRegion_form #fromTime").val( $("#ScenarioByRegion_form #fromTime").val().replace(/:/g, '')  );
	$("#ScenarioByRegion_form #toTime").val( $("#ScenarioByRegion_form #toTime").val().replace(/:/g, '')  );
	
	$('#ScenarioByRegion_form').submit();
}

function RequestScenarioByRegion2(){
	if($('#ScenarioByRegion2_form #fromDate').val() == ""){
		alert("시작날짜를 선택하십시오.");
		return false;
	}
	
	if($('#ScenarioByRegion2_form #toDate').val() == ""){
		alert("종료날짜를 선택하십시오.");
		return false;
	}
		
	if($('#ScenarioByRegion2_form #fromTime').val() == ""){
		alert("시작시간을 선택하십시오.");
		return false;
	}
	
	if($('#ScenarioByRegion2_form #toTime').val() == ""){
		alert("종료시간을 선택하십시오.");
		return false;
	}

	
	if($("#ScenarioByRegion2_form #partitions").val() != "1" && $("#ScenarioByRegion2_form #region").val() == "0" ){
		alert("파티션기능은 구를 선택하셔야 합니다.");
		return false;	
	}
	
	$("#ScenarioByRegion2_form #fromDate").val( $("#ScenarioByRegion2_form #fromDate").val().replace(/-/g, '')  );
	$("#ScenarioByRegion2_form #toDate").val( $("#ScenarioByRegion2_form #toDate").val().replace(/-/g, '')  );
	$("#ScenarioByRegion2_form #fromTime").val( $("#ScenarioByRegion2_form #fromTime").val().replace(/:/g, '')  );
	$("#ScenarioByRegion2_form #toTime").val( $("#ScenarioByRegion2_form #toTime").val().replace(/:/g, '')  );
	
	$('#ScenarioByRegion2_form').submit();
}

function RequestScenarioByCoordinate(){
	if($('#ScenarioByCoordinate_form #fromDate').val() == ""){
		alert("시작날짜를 선택하십시오.");
		return false;
	}
	
	if($('#ScenarioByCoordinate_form #toDate').val() == ""){
		alert("종료날짜를 선택하십시오.");
		return false;
	}
		
	if($('#ScenarioByCoordinate_form #fromTime').val() == ""){
		alert("시작시간을 선택하십시오.");
		return false;
	}
	
	if($('#ScenarioByCoordinate_form #toTime').val() == ""){
		alert("종료시간을 선택하십시오.");
		return false;
	}	
	
	if($("#ScenarioByCoordinate_form #minX").val() == "" || $("#ScenarioByCoordinate_form #minY").val() == "" ||
			$("#ScenarioByCoordinate_form #maxX").val() == "" ||$("#ScenarioByCoordinate_form #maxY").val() == ""){
		alert("영역을 선택하십시오.");
		return false;
	}
	
	$("#ScenarioByCoordinate_form #fromDate").val( $("#ScenarioByCoordinate_form #fromDate").val().replace(/-/g, '')  );
	$("#ScenarioByCoordinate_form #toDate").val( $("#ScenarioByCoordinate_form #toDate").val().replace(/-/g, '')  );
	$("#ScenarioByCoordinate_form #fromTime").val( $("#ScenarioByCoordinate_form #fromTime").val().replace(/:/g, '')  );
	$("#ScenarioByCoordinate_form #toTime").val( $("#ScenarioByCoordinate_form #toTime").val().replace(/:/g, '')  );
	
	$('#ScenarioByCoordinate_form').submit();
}

function RequestMapByRegion(){
	$('#MapByRegion_form').submit();
}


function RequestMapByRegion2(){
	$('#MapByRegion2_form').submit();
}

function RequestMapByCoordinate(){
	if($("#MapByCoordinate_form #minX").val() == "" || $("#MapByCoordinate_form #minY").val() == "" ||
			$("#MapByCoordinate_form #maxX").val() == "" ||$("#MapByCoordinate_form #maxY").val() == ""){
		alert("영역을 선택하십시오.");
		return false;
	}
	
	$('#MapByCoordinate_form').submit();
}

function RequestSignalByRegion(){
	if($('#SignalByRegion_form #fromDate').val() == ""){
		alert("시작날짜를 선택하십시오.");
		return false;
	}
	
	if($('#SignalByRegion_form #toDate').val() == ""){
		alert("종료날짜를 선택하십시오.");
		return false;
	}
		
	if($('#SignalByRegion_form #fromTime').val() == ""){
		alert("시작시간을 선택하십시오.");
		return false;
	}
	
	if($('#SignalByRegion_form #toTime').val() == ""){
		alert("종료시간을 선택하십시오.");
		return false;
	}
	
	$("#SignalByRegion_form #fromDate").val( $("#SignalByRegion_form #fromDate").val().replace(/-/g, '')  );
	$("#SignalByRegion_form #toDate").val( $("#SignalByRegion_form #toDate").val().replace(/-/g, '')  );
	$("#SignalByRegion_form #fromTime").val( $("#SignalByRegion_form #fromTime").val().replace(/:/g, '')  );
	$("#SignalByRegion_form #toTime").val( $("#SignalByRegion_form #toTime").val().replace(/:/g, '')  );
	
	$('#SignalByRegion_form').submit();
}


function RequestSignalByRegion2(){
	if($('#SignalByRegion2_form #fromDate').val() == ""){
		alert("시작날짜를 선택하십시오.");
		return false;
	}
	
	if($('#SignalByRegion2_form #toDate').val() == ""){
		alert("종료날짜를 선택하십시오.");
		return false;
	}
		
	if($('#SignalByRegion2_form #fromTime').val() == ""){
		alert("시작시간을 선택하십시오.");
		return false;
	}
	
	if($('#SignalByRegion2_form #toTime').val() == ""){
		alert("종료시간을 선택하십시오.");
		return false;
	}
	
	$("#SignalByRegion2_form #fromDate").val( $("#SignalByRegion2_form #fromDate").val().replace(/-/g, '')  );
	$("#SignalByRegion2_form #toDate").val( $("#SignalByRegion2_form #toDate").val().replace(/-/g, '')  );
	$("#SignalByRegion2_form #fromTime").val( $("#SignalByRegion2_form #fromTime").val().replace(/:/g, '')  );
	$("#SignalByRegion2_form #toTime").val( $("#SignalByRegion2_form #toTime").val().replace(/:/g, '')  );
	
	$('#SignalByRegion2_form').submit();
}


function RequestSignalByCoordinate(){
	if($('#SignalByCoordinate_form #fromDate').val() == ""){
		alert("시작날짜를 선택하십시오.");
		return false;
	}
	
	if($('#SignalByCoordinate_form #toDate').val() == ""){
		alert("종료날짜를 선택하십시오.");
		return false;
	}
		
	if($('#SignalByCoordinate_form #fromTime').val() == ""){
		alert("시작시간을 선택하십시오.");
		return false;
	}
	
	if($('#SignalByCoordinate_form #toTime').val() == ""){
		alert("종료시간을 선택하십시오.");
		return false;
	}	
	
	if($("#SignalByCoordinate_form #minX").val() == "" || $("#SignalByCoordinate_form #minY").val() == "" ||
			$("#SignalByCoordinate_form #maxX").val() == "" ||$("#SignalByCoordinate_form #maxY").val() == ""){
		alert("영역을 선택하십시오.");
		return false;
	}
	
	$("#SignalByCoordinate_form #fromDate").val( $("#SignalByCoordinate_form #fromDate").val().replace(/-/g, '')  );
	$("#SignalByCoordinate_form #toDate").val( $("#SignalByCoordinate_form #toDate").val().replace(/-/g, '')  );
	$("#SignalByCoordinate_form #fromTime").val( $("#SignalByCoordinate_form #fromTime").val().replace(/:/g, '')  );
	$("#SignalByCoordinate_form #toTime").val( $("#SignalByCoordinate_form #toTime").val().replace(/:/g, '')  );
	
	$('#SignalByCoordinate_form').submit();
}
