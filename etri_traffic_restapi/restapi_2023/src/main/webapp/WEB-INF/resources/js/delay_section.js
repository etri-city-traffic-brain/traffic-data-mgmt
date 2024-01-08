var format = "image/png";
var delayLayer =  new OpenLayers.Layer.WMS(
        "DELAY_TRAFFIC", server,
        {
            LAYERS: 'BigData:DELAY_TRAFFIC',
            STYLES: '',
            format: format,
            transparent: true,
            format_options: 'antialias: Full'
        },
        {
            buffer: 0,
            displayOutsideMaxExtent: true,
            isBaseLayer: false,
            yx : {'EPSG:4326' : true}
        } 
    );

var markerLayer = new OpenLayers.Layer.Vector("Select-Marker", {
	styleMap: new OpenLayers.StyleMap({'default' : new OpenLayers.Style({
		"graphicWidth" : 32, 
		"graphicHeight" : 32,
		"graphicOpacity" : 1.0,
		"graphicName" : "Select-Marker", 
		"externalGraphic": context + "images/icon/ic_pin_01.png"
	})})
});	

var SelectStyle = new OpenLayers.Style(template, {context: colorContext});

var SelectLayer = new OpenLayers.Layer.Vector("Select-Traffic", {
	styleMap: new OpenLayers.StyleMap({'default' :SelectStyle}),
	strategies: [ new OpenLayers.Strategy.BBOX() ],
	protocol: new OpenLayers.Protocol.WFS({
		readFormat: new OpenLayers.Format.GeoJSON(),
		formatOptions: {
	        outputFormat: "JSON"
	    },				  
        version: "1.0.0",
        srsName: "EPSG:4326",
        url:  "./getWfsTraffic.do",  
        params: { routeid: $("#routeid").val() },
        featureType: "type",
        featurePrefix: "",
        featureNS: "",
        geometryName: "geom"
    }),
    Visibility : false
});

$(document).ready(function(){
	ReSizeMap();
	
	ServiceMap = new RxNaver("map_canvas");	
	ServiceMap.addLayer(SelectLayer);
	ServiceMap.addLayer(delayLayer);
	ServiceMap.addLayer(markerLayer); 
	ServiceMap.setCenter(126.705461, 37.455882);	
	ServiceMap.zoom(6);

	$('#searchWrd').keyup(function(event){
		fnXXSS(this);
	});
	
	$('#btnSearch').click(function(event){
		fnSearch(1);
	});	
	
	ServiceMap.map.events.on({
	    moveend: function(evt) {		    	
	    	RefreshMap();
	    }       
	});
	
	ServiceMap.map.events.register('click', delayLayer, delayLayerClick);
	
});

function delayLayerClick(event){

	 var params = {
             REQUEST: "GetFeatureInfo",
             EXCEPTIONS: "application/vnd.ogc.se_xml",
             BBOX: ServiceMap.map.getExtent().toBBOX(),
             SERVICE: "WMS",
             INFO_FORMAT: 'application/json',
             QUERY_LAYERS: delayLayer.params.LAYERS,
             FEATURE_COUNT: 50,
             Layers: 'BigData:DELAY_TRAFFIC',
             WIDTH: ServiceMap.map.size.w,
             HEIGHT: ServiceMap.map.size.h,
             format: format,
             styles: delayLayer.params.STYLES,
             srs: delayLayer.params.SRS};
	// handle the wms 1.3 vs wms 1.1 madness
     if(ServiceMap.map.layers[0].params.VERSION == "1.3.0") {
         params.version = "1.3.0";
         params.j = parseInt(event.xy.x);
         params.i = parseInt(event.xy.y);
     } else {
         params.version = "1.1.1";
         params.x = parseInt(event.xy.x);
         params.y = parseInt(event.xy.y);
     }
     
     clickX = event.xy.x;
     clickY = event.xy.y;
     clickLngLat =  ServiceMap.pixelToLatLng(event.xy);
 	 AjaxToJson("getCrossData.do", params,  fnLinkSelect, fnLinkSelectError);
 	  
     OpenLayers.Event.stop(event);	
}

function initSelect(){
	$('#roadList > tbody > tr').each(function() {
		$(this).attr("class","yui-dt-odd");
	});
}

function fnLinkSelect(data, st, xhr){
	
	var index = 0;
	if( data.features.length == 0){
		ServiceMap.HidePopup();
		return;
	}
	
	if( data.features[index].properties.AVRG_SPEED == null){
		index = 1;
		if( data.features[index].properties.AVRG_SPEED  == null){
			ServiceMap.HidePopup();
			return;
		}
	}
	
	var sStr = String.format("<div class='content'><header class='tooltip-header' >{0}" + 
			 "<small onmousedown='fPopupClose(this)' class='clPopMark-p'>X</small></header>" + 
	       	 "<table><tbody>" +						        	
	       	 "<tr><td>{1} &rarr; {2}</td></tr>" +
	       	 //"<tr><td colspan='2'>{3} Km/h</td></tr>" +
	       	 "<tr><td class='bold3' style='background-color:#FA1313;' colspan='2'>{3} Km/h</td></td></tr>" + 
	       	 "</tbody></table></div>",
	       	data.features[index].properties.ROADNAME,
	       	data.features[index].properties.STNODE_NM, data.features[index].properties.EDNODE_NM,
	       	Math.round(data.features[index].properties.AVRG_SPEED));

	ServiceMap.ShowPopup(clickLngLat, sStr);	
};

function fnLinkSelectError(xhr,st,err){
	if( xhr.responseText ==	"" ){
		console.log("검색 결과가 없습니다.");
	}
	console.error("LinkSelect Error >>> Type: "+st+"; Response: "+ xhr.status + " "+xhr.statusText + " , " + err);	
}


function fnSearch(pageNo) {
	$("#form").attr("action",  "delaySection.do");
	$("#form").submit();
}

/************************************************************
 * Resize Event
 ************************************************************/	
$(window).resize(function() {
	ReSizeMap();
});
/************************************************************
 * 화면 종료
 ************************************************************/
$( window ).unload(function() {
	ServiceMap = null;
});
/************************************************************
 * Resize Map div
 ************************************************************/	
function ReSizeMap(){
	
	var maskheight = 0;
	var maskwidth = 0;
	var contentwidth = 0;
	
	try{
		contentwidth = $('#contents-wrap').width() -  $('#left_list').width() - 6;
		maskheight = document.documentElement.clientHeight - 250;
		
		if( maskheight < 500 ){
			maskheight = 500;
		}
		
		$('#map_box').css('height', maskheight );
		$('#map_box').css('width', contentwidth );

		$('#left_list').css('height', maskheight ); 
		
		$('#legend').css('top', ($('#map_canvas').height()) - 50 );
		
		maskwidth = contentwidth / 2 - 170;
		$('#map_h').css('left', maskwidth );
		
	}catch(exception){
		console.log(exception);
	}finally{
		contentwidth = null;
	}

//	원본소스유지
//	var maskheight = 0;
//	var contentwidth = 0;
//	try{
//		contentwidth = $('#contents-wrap').width() -  $('#left_list').width() - 6;
//		maskheight = document.documentElement.clientHeight - 350;
//		
//		if( maskheight < 450 ){
//			maskheight = 450;
//		}
//		
//		$('#map_box').css('height', maskheight );
//		$('#map_box').css('width', contentwidth );
//
//		$('#left_list').css('height', maskheight ); 
//		
//		$('#legend').css('top', ($('#map_canvas').height()) - 50 );
//		
//	}catch(exception){
//		console.log(exception);
//	}finally{
//		contentwidth = null;
//	}
}

function RefreshMap(){
	
	ServiceMap.HidePopup();	
	if( $("#routeid").val() == ""){		
		SelectLayer.setVisibility(false);
	}
}

function fnShowSelectRoad(routeid, direction, arterytype){  
	$('#routeid').val(routeid);
	$('#direction').val(direction);
	
	markerLayer.removeAllFeatures();
	SelectLayer.setVisibility(false);	
	SelectLayer.refresh({ force: true, params: {routeid: routeid} });	
	
	AjaxToJson("getRouteDetail.do",  "routeid=" + routeid + "&arterytype=" + arterytype , fnShowSelectRoadSuccess, fnShowSelectRoadError);
}

function fnShowSelectRoadSuccess(data, st, xhr){
	$("#routeid").val(data.ROUTEID);
	SelectLayer.setVisibility(true);
	
	var latlng1 = null; 
	var latlng2 = null; 
	var bounds = new OpenLayers.Bounds();
    
	if( data.ARTERYTYPE == '04' || data.ARTERYTYPE == '05'){
		latlng1 = ServiceMap.xyToLatLng(data.MINX, data.MINY);
		latlng2 = ServiceMap.xyToLatLng(data.MAXX, data.MAXY);
		bounds.extend(latlng1);
	    bounds.extend(latlng2);	    
		ServiceMap.zoomToExtent(bounds);
		SelectLayer.refresh({ force: true, params: {routeid: data.ROUTEID} });
		return;
	}
	
	latlng1 = ServiceMap.xyToLatLng(data.FROMNODEX, data.FROMNODEY);
	latlng2 = ServiceMap.xyToLatLng(data.TONODEX, data.TONODEY);
	bounds.extend(latlng1);
    bounds.extend(latlng2);	    

    //화살표 아이콘 표출을 위해 주석 제거 박황서 151221
	var point1 = new OpenLayers.Geometry.Point(latlng1.lon, latlng1.lat);
    var point2 = new OpenLayers.Geometry.Point(latlng2.lon, latlng2.lat);

    var marker1 = new OpenLayers.Feature.Vector(point1, null, {
    	externalGraphic: context + '/images/icon/ic_pin_01.png',
        graphicWidth: 34,
        graphicHeight: 49,
        fillOpacity: 1
    });

    var marker2 = new OpenLayers.Feature.Vector(point2, null, {
    	externalGraphic: context + '/images/icon/ic_pin_01.png',
        graphicWidth: 34,
        graphicHeight: 49,
        fillOpacity: 1
    }); 
    
    markerLayer.addFeatures([marker1, marker2]);
    ServiceMap.zoomToExtent(bounds);    
    SelectLayer.refresh({ force: true, params: {routeid: data.ROUTEID} });
}

function fnShowSelectRoadError(xhr,st,err){
	console.error("fnShowSelectRoad Error >>> Type: "+st+"; Response: "+ xhr.status + " "+xhr.statusText + " , " + err);	
}
 

function fnDrawTrafficGrade(grade, color){
	var canvas = document.getElementById(grade);
    var ctx = canvas.getContext('2d');
    var centerX = canvas.width / 2;
    var centerY = canvas.height / 2;
    var radius = 10;

    ctx.beginPath();
    ctx.arc(centerX, centerY, radius, 0, 2 * Math.PI, false);
    ctx.fillStyle = color;
    ctx.fill();
    ctx.lineWidth = 1;
    ctx.strokeStyle = '#003300';
    ctx.stroke();
}

function fPopupClose(elmnt){
	elmnt.parentNode.parentNode.style.display = 'none';
}
