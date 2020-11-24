/*
    http://openlayers.org/en/latest/examples/wmts-layer-from-capabilities.html?q=WMTSCapabilities
*/     
var VworldBase,VworldSatellite,VworldGray,VworldMidnight,VworldHybrid;
var attr = '© <a href="http://dev.vworld.kr">vworld</a>';
var VworldHybrid = new ol.source.XYZ({
    url: 'http://api.vworld.kr/req/wmts/1.0.0/99F58FC3-5453-37CE-9D85-F4A0F1490DBA/Hybrid/{z}/{y}/{x}.png'
}); //문자 타일 레이어
 
var VworldSatellite = new ol.source.XYZ({
    url: 'http://api.vworld.kr/req/wmts/1.0.0/99F58FC3-5453-37CE-9D85-F4A0F1490DBA/Satellite/{z}/{y}/{x}.jpeg'
    ,attributions : attr
}); //항공사진 레이어 타일
 
    var VworldBase = new ol.source.XYZ({
        url: 'http://api.vworld.kr/req/wmts/1.0.0/99F58FC3-5453-37CE-9D85-F4A0F1490DBA/Base/{z}/{y}/{x}.png'
    ,attributions : attr
}); // 기본지도 타일
 
    var VworldGray =  new ol.source.XYZ({
        url: 'http://api.vworld.kr/req/wmts/1.0.0/99F58FC3-5453-37CE-9D85-F4A0F1490DBA/gray/{z}/{y}/{x}.png'
    ,attributions : attr
}); //회색지도 타일
 
var VworldMidnight =  new ol.source.XYZ({
    url: 'http://api.vworld.kr/req/wmts/1.0.0/99F58FC3-5453-37CE-9D85-F4A0F1490DBA/midnight/{z}/{y}/{x}.png'
    ,attributions : attr
})

var source = new ol.source.Vector({wrapX: false});

var vector = new ol.layer.Vector({
  source: source,
  style: new ol.style.Style({
      fill: new ol.style.Fill({
          color: 'rgba(255,255,255, 0.4)'
      }),
      stroke: new ol.style.Stroke({
          color: 'red',
          width: 2
      }),
      image: new ol.style.Circle({
          radius: 7,
          fill: new ol.style.Fill({
              color: 'red'
          })
      })
  })
});

var drawBox = null;

function addInteraction() {	  
    var geometryFunction, maxPoints;
    geometryFunction = ol.interaction.Draw.createBox();    
    maxPoints = 2;
    
    drawBox = new ol.interaction.Draw({
      source: source,
      type: 'Circle',
      geometryFunction: geometryFunction,
      maxPoints: maxPoints,
      condition: ol.events.condition.platformModifierKeyOnly
    });
   
    drawBox.on('drawstart', function (event) {    	
    	source.clear();
    	
    	/* SelectedselectAccordion
    	 	0 : 시나리오(영역)
    	 	4 : 시나리오(지역)
    	 	1 : 지도(영역)
    	 	2 : 지도(지역)
    	 	3 : 신호(영역)
    	 	5 : 신호(지역)
    	 */
    	switch(SelectedselectAccordion){
	    	case 0 :
	    		$("#ScenarioByCoordinate_form #minX").val("0.0");
	        	$("#ScenarioByCoordinate_form #minY").val("0.0");
	        	$("#ScenarioByCoordinate_form #maxX").val("0.0");
	        	$("#ScenarioByCoordinate_form #maxY").val("0.0");
	    		break;
	    	case 1 :
	    		$("#MapByCoordinate_form #minX").val("0.0");
	        	$("#MapByCoordinate_form #minY").val("0.0");
	        	$("#MapByCoordinate_form #maxX").val("0.0");
	        	$("#MapByCoordinate_form #maxY").val("0.0");
	    		break;
	    	case 3 : 
	    		$("#SignalByCoordinate_form #minX").val("0.0");
	        	$("#SignalByCoordinate_form #minY").val("0.0");
	        	$("#SignalByCoordinate_form #maxX").val("0.0");
	        	$("#SignalByCoordinate_form #maxY").val("0.0");
	    		break;
    	}    	
    	
    });
    
    drawBox.on('drawend', function (event) {    	
        SetAreaCoord(event.feature.getGeometry());
    });
        
    map.addInteraction(drawBox);	
    drawBox.setActive(false);
}

function SetAreaCoord(geometry){
	//좌측상단
	var fcoords = ol.proj.transform(geometry.getFirstCoordinate(), 'EPSG:900913', 'EPSG:4326');
	//우측하단
    var lcoords = ol.proj.transform(geometry.A.slice(4, geometry.a + 4 ), 'EPSG:900913', 'EPSG:4326'); 

    /* SelectedselectAccordion
	 	0 : 시나리오(영역)
	 	4 : 시나리오(지역)
	 	1 : 지도(영역)
	 	2 : 지도(지역)
	 	3 : 신호(영역)
	 	5 : 신호(지역)
	 */
    switch(SelectedselectAccordion){
	case 0 :
		$("#ScenarioByCoordinate_form #minX").val(fcoords[0].toFixed(6));
    	$("#ScenarioByCoordinate_form #minY").val(fcoords[1].toFixed(6));
    	$("#ScenarioByCoordinate_form #maxX").val(lcoords[0].toFixed(6));
    	$("#ScenarioByCoordinate_form #maxY").val(lcoords[1].toFixed(6));
		break;
	case 1 : 
		$("#MapByCoordinate_form #minX").val(fcoords[0].toFixed(6));
    	$("#MapByCoordinate_form #minY").val(fcoords[1].toFixed(6));
    	$("#MapByCoordinate_form #maxX").val(lcoords[0].toFixed(6));
    	$("#MapByCoordinate_form #maxY").val(lcoords[1].toFixed(6));
		break;
	case 3 : 
		$("#SignalByCoordinate_form #minX").val(fcoords[0].toFixed(6));
    	$("#SignalByCoordinate_form #minY").val(fcoords[1].toFixed(6));
    	$("#SignalByCoordinate_form #maxX").val(lcoords[0].toFixed(6));
    	$("#SignalByCoordinate_form #maxY").val(lcoords[1].toFixed(6));
		break;
    }  
    
	//$("#minX").val(fcoords[0].toFixed(6));
	//$("#minY").val(fcoords[1].toFixed(6));
    //$("#maxX").val(lcoords[0].toFixed(6));
	//$("#maxY").val(lcoords[1].toFixed(6));
}

function InitializeMap(mapDiv, mapName){
	map = new ol.Map({
        controls: ol.control.defaults().extend([
        	new ol.control.OverviewMap(),
        	new ol.control.ZoomSlider(),
            new ol.control.ScaleLine()
        		]),
        layers: [
           new ol.layer.Tile({
                source: VworldBase,
                name: mapName
            })
           , vector
        ],
        target: mapDiv, 
        view: new ol.View({ 
            //center: ol.proj.transform([127.1253262,37.5440378], 'EPSG:4326', 'EPSG:900913'),		// 서울 강동구
            center: ol.proj.transform([127.341543,36.353629], 'EPSG:4326', 'EPSG:900913'), 			// 대전 유성구
            zoom: 13,
            minZoom : 0,
            maxZoom : 21
        })
    });
	
	addInteraction();
	
	//Enable interaction by holding shift
    this.addEventListener('keydown', function(event) {
      if (event.keyCode == 17) {
    	  drawBox.setActive(true);
      }
    });
    
    this.addEventListener('keyup', function(event) {
      if (event.keyCode == 17) {
    	  drawBox.setActive(false);
      }
    });
}

/*
control 설정
 
var base_button = document.createElement('button'); 	 base_button.innerHTML = 'B';
var gray_button = document.createElement('button');		 gray_button.innerHTML = 'G';
var midnight_button = document.createElement('button');	 midnight_button.innerHTML = 'M';
var hybrid_button = document.createElement('button');	 hybrid_button.innerHTML = 'H';		hybrid_button.className='on';
var sate_button = document.createElement('button');		 sate_button.innerHTML = 'S';
var element = document.createElement('div');
element.className = 'rotate-north ol-unselectable ol-control ol-mycontrol';

base_button.onclick=function(){
map.getLayers().forEach(function(layer){
    if(layer.get("name")=="vworld"){
        layer.setSource(VworldBase)
    }
})
}

gray_button.onclick=function(){
map.getLayers().forEach(function(layer){
    if(layer.get("name")=="vworld"){
        layer.setSource(VworldGray)
    }
})
}

midnight_button.onclick=function(){
map.getLayers().forEach(function(layer){
    if(layer.get("name")=="vworld"){
        layer.setSource(VworldMidnight)
    }
})
}

sate_button.onclick=function(){
map.getLayers().forEach(function(layer){
    if(layer.get("name")=="vworld"){
        layer.setSource(VworldSatellite)
    }
})
}

hybrid_button.onclick=function(){
var _this = this;
map.getLayers().forEach(function(layer){
    if(layer.get("name")=="hybrid"){
        if(_this.className == "on"){
            layer.setSource(null)
            _this.className ="";
        }else{
            _this.className ="on";
             
            layer.setSource(VworldHybrid)
        }
    }
})
}
  
element.appendChild(base_button);
element.appendChild(gray_button);
element.appendChild(midnight_button);
element.appendChild(sate_button);
element.appendChild(hybrid_button);


var layerControl = new ol.control.Control({element: element});
*/   