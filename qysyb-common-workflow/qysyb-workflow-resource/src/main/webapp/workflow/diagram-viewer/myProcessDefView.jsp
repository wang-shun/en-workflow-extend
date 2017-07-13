<%@ page contentType="text/html; charset=utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://"
		+ request.getServerName() + ":" + request.getServerPort()
		+ path + "/";
String processDefinitionId = request
		.getParameter("processDefinitionId");
%>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  
  <link rel="stylesheet" href="style.css" type="text/css" media="screen">
  <script src="js/jstools.js" type="text/javascript" charset="utf-8"></script>
  <script src="js/raphael.js" type="text/javascript" charset="utf-8"></script>
  
  <script src="js/jquery/jquery.js" type="text/javascript" charset="utf-8"></script>
  <script src="js/jquery/jquery.progressbar.js" type="text/javascript" charset="utf-8"></script>
  <script src="js/jquery/jquery.asyncqueue.js" type="text/javascript" charset="utf-8"></script>
  
  <script src="js/Color.js" type="text/javascript" charset="utf-8"></script>
  <script src="js/Polyline.js" type="text/javascript" charset="utf-8"></script>
  <script src="js/ActivityImpl.js" type="text/javascript" charset="utf-8"></script>
  <script src="js/ActivitiRest.js" type="text/javascript" charset="utf-8"></script>
  <script src="js/LineBreakMeasurer.js" type="text/javascript" charset="utf-8"></script>
  <script src="js/ProcessDiagramGenerator.js" type="text/javascript" charset="utf-8"></script>
  <script src="js/ProcessDiagramCanvas.js" type="text/javascript" charset="utf-8"></script>
  
  <style type="text/css" media="screen">
    
  </style>
</head>
<body>
<div class="wrapper">
  <div id="pb1" style="display: none"></div>
  <div id="overlayBox" >
    <div id="diagramBreadCrumbs" class="diagramBreadCrumbs" onmousedown="return false" onselectstart="return false" style="display: none"></div>
    <div id="diagramHolder" class="diagramHolder"></div>
    <div class="diagram-info" id="diagramInfo"  style="display: none"></div>
  </div>
</div>

<!-- <div id=Layer1 style="position:absolute; width:4px; height:4px; z-index:3; visibility: hidden"> 
<select style="width:4"></select>  -->
</div> 
<!-- <div id="PopMenu" style="background-color:rgb(255, 255, 255);position:absolute; width:100px; z-index:1; visibility: hidden">
<div> 
<table border=2 width=100 > 
<TH align="center" color="sliver" onclick=""> 
唯鱼的菜单 
</tH> 
<tr onclick="formsetting()"> 
<td> 
表单设置
</td> 
</tr> 
<tr> 
<td> 
节点设置
</td> 
</tr> 
</table> 
</div>
</div> -->
<script language='javascript'>
var DiagramGenerator = {};
var pb1;
var dataUrl="<%=basePath%>";
$(document).ready(function(){

  var query_string = {};
  var query = window.location.search.substring(1);
  var vars = query.split("&");
  for (var i=0;i<vars.length;i++) {
    var pair = vars[i].split("=");
    query_string[pair[0]] = pair[1];
  } 
  
  var processDefinitionId = query_string["processDefinitionId"];
  var processInstanceId = query_string["processInstanceId"];
  console.log("Initialize progress bar");
  
  pb1 = new $.ProgressBar({
    boundingBox: '#pb1',
    label: 'Progressbar!',
    on: {
      complete: function() {
        console.log("Progress Bar COMPLETE");
        this.set('label', 'complete!');
        if (processInstanceId) {
          ProcessDiagramGenerator.drawHighLights(processInstanceId);
        }
      },
      valueChange: function(e) {
        this.set('label', e.newVal + '%');
      }
    },
    value: 0
  });
  console.log("Progress bar inited");
  
  ProcessDiagramGenerator.options = {
    diagramBreadCrumbsId: "diagramBreadCrumbs",
    diagramHolderId: "diagramHolder",
    diagramInfoId: "diagramInfo",
    on: {
      click: function(canvas, element, contextObject){
        var mouseEvent = this;
        console.log("[CLICK] mouseEvent: %o, canvas: %o, clicked element: %o, contextObject: %o", mouseEvent, canvas, element, contextObject);

        if (contextObject.getProperty("type") == "callActivity") {
          var processDefinitonKey = contextObject.getProperty("processDefinitonKey");
          var processDefinitons = contextObject.getProperty("processDefinitons");
          var processDefiniton = processDefinitons[0];
          console.log("Load callActivity '" + processDefiniton.processDefinitionKey + "', contextObject: ", contextObject);

          	// Load processDefinition
        	ProcessDiagramGenerator.drawDiagram(processDefiniton.processDefinitionId);
          	
          	
        }
        
      //yyc add begine
      	// 任务类型
  		var taskType = contextObject.getProperty("type");
      	if("userTask"==taskType){
      		//任务定义ID
      		var activityId = contextObject.id;
      		//流程定义ID
      		var processDefId = processDefinitionId;
      		if(activityId && processDefId){
      			//开始进行外围配置的实现
      			var parentPage = $(parent.document);
      			var moduleId = parentPage.find("#moduleIdHiddenField").val();
      			console.log("开始对流程定义ID:"+processDefId+",任务定义ID:"+activityId+",模块事项ID:"+moduleId+" 进行外围配置。。。");
      			parentPage.find("#activity_name").val(contextObject.getProperty("name"));
      			parentPage.find("#activity_alias").removeAttr("disabled");
      			parentPage.find("#activity_duration").removeAttr("disabled");
	      		parentPage.find("#activity_durationUnit").removeAttr("disabled");
      			// 查询点击任务定义的外围配置
      			 $.post(dataUrl+"workflow/config/findProcessConfigProperty?processDefinitionId="+processDefinitionId+"&taskDefKey="+activityId+"&moduleId="+moduleId, {},
      				   function(data){
							if(data && data.result && data.result=="success"){
								parentPage.find("#activity_alias").val(data.wfProcessConfigProperty.alias);
	      		      			//parentPage.find("#activity_performer").val(data.wfProcessConfigProperty.performer);
	      		      			
	      		      			if(data.wfProcessConfigProperty.performer){
	      		      				parentPage.find("#activity_performer_hidden_group").val('');
	      		      				parentPage.find("#activity_performer_hidden").val(data.wfProcessConfigProperty.performer);
	      		      				parentPage.find("#activity_performer").val("用户【"+data.userNames+"】");
	      		      			}else if(data.wfProcessConfigProperty.groupPerformer){
	      		      				parentPage.find("#activity_performer_hidden").val('');
	      		      				parentPage.find("#activity_performer_hidden_group").val(data.wfProcessConfigProperty.groupPerformer);
	      		      				parentPage.find("#activity_performer").val("组【"+data.groupNames+"】");
	      		      			}else{
		      		      			parentPage.find("#activity_performer_hidden_group").val('');
	      		      				parentPage.find("#activity_performer_hidden").val('');
	      		      				parentPage.find("#activity_performer").val('');
	      		      			}
	      		      			
	      		      			parentPage.find("#activity_duration").val(data.wfProcessConfigProperty.duration);
	      		      			var unit = data.wfProcessConfigProperty.durationUnit;
	      		      			if(unit){
	      		      				parentPage.find("#activity_durationUnit").val(data.wfProcessConfigProperty.durationUnit);
	      		      			}else{
	      		      				parentPage.find("#activity_durationUnit").val(undefined);
	      		      			}
	      		      			parentPage.find("#activity_bindForm").val(data.wfProcessConfigProperty.bindForm);
	      		      			//parentPage.find("#activity_otherAttr").val("");
							}else{
								parentPage.find("#activity_alias").val("");
	      		      			parentPage.find("#activity_performer").val("");
	      		      			parentPage.find("#activity_duration").val("");
	      		      			parentPage.find("#activity_durationUnit").val(undefined);
	      		      			parentPage.find("#activity_bindForm").val("");
	      		      			parentPage.find("#activity_performer_hidden_group").val("");
	      		      			parentPage.find("#activity_performer_hidden").val("");
	      		      			//parentPage.find("#activity_otherAttr").val("");
							}
							
							window.parent.angular.element("#newGroupId").scope().functions.show_table_activiti_setting(processDefId) ;
      		      			
      			}, "json"); 
      			parentPage.find("#activity_activityId").val(activityId);
      			
      		}
      		
      	}else if("startEvent"==taskType){//对开始环节进行外围配置的绑定
      		//对不需要配置的元素进行置空和隐藏
      		//任务定义ID
      		var startId = contextObject.id;
      		//流程定义ID
      		var processDefId = processDefinitionId;
      		if(startId && processDefId){
      		//开始进行外围配置的实现
      			var parentPage = $(parent.document);
      			var moduleId = parentPage.find("#moduleIdHiddenField").val();
      			parentPage.find("#activity_name").val("");
      			parentPage.find("#activity_name").attr("disabled","disabled");
				parentPage.find("#activity_alias").attr("disabled","disabled");
		      	parentPage.find("#activity_durationUnit").attr("disabled","disabled");
		      	parentPage.find("#activity_duration").attr("disabled","disabled");
      			
      			 $.post(dataUrl+"workflow/config/findProcessConfigProperty?processDefinitionId="+processDefinitionId+"&taskDefKey="+startId+"&moduleId="+moduleId, {},
        				   function(data){
        				    //console.log(data);
  							if(data && data.result && data.result=="success"){
  								parentPage.find("#activity_alias").val(data.wfProcessConfigProperty.alias);
  								
  	      		      			//parentPage.find("#activity_performer").val(data.wfProcessConfigProperty.performer);
  	      		      			
  	      		      			if(data.wfProcessConfigProperty.performer){
  	      		      				parentPage.find("#activity_performer_hidden_group").val('');
  	      		      				parentPage.find("#activity_performer_hidden").val(data.wfProcessConfigProperty.performer);
  	      		      				parentPage.find("#activity_performer").val("用户【"+data.userNames+"】");
  	      		      			}else if(data.wfProcessConfigProperty.groupPerformer){
  	      		      				parentPage.find("#activity_performer_hidden").val('');
  	      		      				parentPage.find("#activity_performer_hidden_group").val(data.wfProcessConfigProperty.groupPerformer);
  	      		      				parentPage.find("#activity_performer").val("组【"+data.groupNames+"】");
  	      		      			}else{
  		      		      			parentPage.find("#activity_performer_hidden_group").val('');
  	      		      				parentPage.find("#activity_performer_hidden").val('');
  	      		      				parentPage.find("#activity_performer").val('');
  	      		      			}
  	      		      		    
  	      		      			parentPage.find("#activity_duration").val(data.wfProcessConfigProperty.duration);
  	      		      			var unit = data.wfProcessConfigProperty.durationUnit;
  	      		      			if(unit){
  	      		      				parentPage.find("#activity_durationUnit").val(data.wfProcessConfigProperty.durationUnit);
  	      		      			}else{
  	      		      				parentPage.find("#activity_durationUnit").val(undefined);
  	      		      			}
  	      		      			parentPage.find("#activity_bindForm").val(data.wfProcessConfigProperty.bindForm);
  	      		      			//parentPage.find("#activity_otherAttr").val("");
  							}else{
  								parentPage.find("#activity_name").val("");
  								parentPage.find("#activity_alias").val("");
  	      		      			parentPage.find("#activity_performer").val("");
  	      		      			parentPage.find("#activity_duration").val("");
  	      		      			parentPage.find("#activity_durationUnit").val(undefined);
  	      		      			parentPage.find("#activity_bindForm").val("");
  	      		      			parentPage.find("#activity_performer_hidden_group").val("");
  	      		      			parentPage.find("#activity_performer_hidden").val("");
  	      		      			//parentPage.find("#activity_otherAttr").val("");
  							}
        		      			
        			}, "json"); 
      			parentPage.find("#activity_activityId").val(startId);
      		}
      	}
      	
      	//yyc add end
      },
      rightClick: function(canvas, element, contextObject){
    	  return;
        var mouseEvent = this;
        x = mouseEvent.clientX; 
        y = mouseEvent.clientY; 
        var parentPage = $(parent.document);
        activity_activityId = contextObject.id;
//        console.log("contextob",contextObject);
        parentPage.find("#activity_activityId").val(activity_activityId);
        parentPage.find("#processInsId").val(processInstanceId);
        parentPage.find("#processDefId").val(processDefinitionId);
        parentPage.find("#activity_name").val(contextObject.getProperty("name"));
        window.setTimeout("showMenu();", 100); 
        console.log();
//        console.log("[RIGHTCLICK] mouseEvent: %o, canvas: %o, clicked element: %o, contextObject: %o", mouseEvent, canvas, element, contextObject);
      },
      over: function(canvas, element, contextObject){
        var mouseEvent = this;
        //console.log("[OVER] mouseEvent: %o, canvas: %o, clicked element: %o, contextObject: %o", mouseEvent, canvas, element, contextObject);

        // TODO: show tooltip-window with contextObject info
        ProcessDiagramGenerator.showActivityInfo(contextObject);
      },
      out: function(canvas, element, contextObject){
        var mouseEvent = this;
 //       console.log("[OUT] mouseEvent: %o, canvas: %o, clicked element: %o, contextObject: %o", mouseEvent, canvas, element, contextObject);

        ProcessDiagramGenerator.hideInfo();
      }
    }
  };
  
  var baseUrl = "<%=basePath %>";
  
  ActivitiRest.options = {
    processInstanceHighLightsUrl: baseUrl + "workflow/service/process-instance/{processInstanceId}/highlights?callback=?",
    processDefinitionUrl: baseUrl + "workflow/service/process-definition/{processDefinitionId}/diagram-layout?callback=?",
    processDefinitionByKeyUrl: baseUrl + "workflow/service/process-definition/{processDefinitionKey}/diagram-layout?callback=?"
  };
  
  if (processDefinitionId) {
    ProcessDiagramGenerator.drawDiagram(processDefinitionId);
    
  } else {
    alert("processDefinitionId parameter is required");
  }
  
  $("body").bind("click",function(){ 
	   // PopMenu.attr("style","visibility:hidden");
  }); 
  document.oncontextmenu=norightclick;
});
var x;
var y;
var activity_activityId;
var PopMenu;
function norightclick(e){
	return;
	console.log(e);
 	if(e){
		console.log(e);
		if(e.which==2||e.which==3){
			return false;
		}	
	}else if(event.button==2||event.button==3){
//		alert(1);
 		event.cancelBubble =true;
		event.returnValue = false;
		event.preventDefault(); 
		return false;
		
	}
}

function formsetting(){
	var parentPage = $(parent.document);
	var formId = parentPage.find("#formId").val();
	var params = "formId="+formId+"?activityId="+activity_activityId;
	window.location.href = "#/f/servicemanagefieldpermissionconfig?"+params;
	alert(1);
}

function showMenu(){
	return;
	var parentPage = $(parent.document);	
	var a = parentPage.scrollTop();
	PopMenu = parentPage.find("#PopMenu");
	var c2flowdefinitioner_ = parentPage.find("#c2flowdefinitioner_");
	var poffsettop = c2flowdefinitioner_.offset().top;
	var poffsetleft = c2flowdefinitioner_.offset().left;
	var xx = x-2+poffsetleft;
	var yy = y-2-a+poffsettop;
	PopMenu.attr("style","background-color:rgb(255, 255, 255);position:fixed; width:60px; z-index:1; visibility:hidden;left:"+xx+"px;top:"+yy+"px;visibility:visible");
	HiddenPop(); 
}
function HiddenPop(){ 
//	Layer1.style.visibility="hidden"; 
} 
</script>

</body>
</html>
