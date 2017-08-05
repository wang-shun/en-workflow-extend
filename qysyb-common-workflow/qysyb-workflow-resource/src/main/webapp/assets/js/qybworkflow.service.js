//企业部工作流公共服务类
app.service('qybWorkflowService',function(){
	//从字段的属性配置里面获取布尔值  字符串 true 1 为真
	this.getBooleanFromRemark = function(remark){
		var booleanStr = remark
		var bool = false
		if(booleanStr && (booleanStr == "true" || booleanStr == "1")){
			bool = true
		}	
		return bool
	},
	
	//页面公共参数初始化 formtype 不同页面注入此服务 $scope,$params不同，通过formtype来区分？ 
	this.initC2FormParams = function($scope,$params,formtype){
		this.$scope = $scope
		this.$params = $params
		this.formtype = formtype
	},
	
	//自定义模板页面 广播验证函数到
	this.emitFormCheckFunc = function(func,fieldNo,scope){
		var thescope
		if(scope){
			thescope = scope
		}else if(this.$scope){
			thescope = this.$scope
		}else {
			console.error("广播验证 scope参数未定义")
			return
		}
		var thefieldNo
		if(fieldNo){
			thefieldNo = fieldNo
		}else if(this.$params){
			thefieldNo = this.$params.fieldNo
		}else {
			console.error("广播验证  fieldNo参数未定义")
			return
		}
		
		thescope.$emit("formcheckfunc",{name:thefieldNo,func:func});
	},
	//获取Wrapper的class
	this.getWrapperClass = function(params){
		var theparams
		if(params){
			theparams = params
		}else if(this.$params){
			theparams = this.$params
		}else{
			console.error("params参数未初始化")
		}
		var classStr = "form-group"
		var span  = theparams.field.displaySpan
		var spanClassStr = (span != undefined&&span != "")
			?"col-sm-"+span.toString():"col-sm-4"
		if(spanClassStr){
			classStr = classStr + " " + spanClassStr
		}	
		return classStr	
	},

	//获取当前环节的actions 是一个数组
	this.getProperties = function(params){
		var theparams
		if(params){
			theparams = params
		}else if(this.$params){
			theparams = this.$params
		}else{
			console.error("params参数未初始化")
			return
		}
		return theparams.pParams.pModel.actions
	},

	//获取当前环节的自定义属性。格式很特别。用一个方法封装起来
	this.getCustomProperty = function(p,actions){
		if(!actions){
			actions = this.getProperties()
		}
		var ps = actions[p]
		if(ps && ps.length == 1){
			return ps[0]
		}
	},	
	
	this.getSubject = function(scope){ 
		var thescope
		if(scope){
			thescope = scope
		}else if(this.$scope){
			thescope = this.$scope
		}else {
			console.error("scope参数未定义")
			return
		}		
		return thescope.formentity.global.subject 
	},
	
	this.getFormentity = function(scope){ 
		var thescope
		if(scope){
			thescope = scope
		}else if(this.$scope){
			thescope = this.$scope
		}else {
			console.error("scope参数未定义")
			return
		}		
		return thescope.formentity
	},
	
	this.getGlobal = function(scope){ 
		var thescope
		if(scope){
			thescope = scope
		}else if(this.$scope){
			thescope = this.$scope
		}else {
			console.error(" scope参数未定义")
			return
		}		
		return thescope.formentity.global 
	},
	
	/**
	 * 上面的方法都要废弃掉
	 * 应该要用这个方法
	 */
	this.getContext = function($scope){
		var context = {}
		var $scope = $scope
		var $params = $scope.$params
		//从字段的属性配置里面获取布尔值  字符串 true 1 为真
		context.getBooleanFromRemark = function(remark){
			var booleanStr = remark
			var bool = false
			if(booleanStr && (booleanStr == "true" || booleanStr == "1")){
				bool = true
			}	
			return bool
		};
		
		//自定义模板页面 广播验证函数到
		context.emitFormCheckFunc = function(func){
			var thefieldNo = $params.fieldNo		
			$scope.$emit("formcheckfunc",{name:thefieldNo,func:func});
		};
		//获取Wrapper的class
		context.getWrapperClass = function(){
			var theparams = $params

			var classStr = "form-group"
			var span  = theparams.field.displaySpan
			var spanClassStr = (span != undefined&&span != "")
				?"col-sm-"+span.toString():"col-sm-4"
			if(spanClassStr){
				classStr = classStr + " " + spanClassStr
			}	
			return classStr
		};

		//获取当前环节的actions 是一个数组
		context.getProperties = function(){
			var theparams = $params
			return theparams.pParams.pModel.actions
		};

		//获取当前环节的自定义属性。格式很特别。用一个方法封装起来
		context.getCustomProperty = function(p,actions){
			if(!actions){
				actions = this.getProperties()
			}
			var ps = actions[p]
			if(ps && ps.length == 1){
				return ps[0]
			}
		};
		
		context.getSubject = function(){ 	
			return $scope.formentity.global.subject 
		};
		
		context.getFormentity = function(){ 	
			return $scope.formentity
		};
		
		context.getGlobal = function(){ 	
			return $scope.formentity.global 
		};
		return context
	},
	
	/**
	 * @params mobilecontext 是否是移动端环境
	 * 把流程处理关键函数提取出来
	 */
	this.bindFunctions = function($scope,$http,$compile, Modal, $location,debounce, ModelFactory, Messenger, FormContainer,TabOperator,$q,functions,mobilecontext){
		angular.extend(functions, this.getFunctions($scope,$http,$compile, Modal, $location,debounce, ModelFactory, Messenger, FormContainer,TabOperator,$q,mobilecontext))	
	},
	
	this.getFunctions = function($scope,$http,$compile, Modal, $location,debounce, ModelFactory, Messenger, FormContainer,TabOperator,$q,mobilecontext){
		var $scope = $scope
		var $model = $scope.model
		var $view = $scope.$view
		var $params = $scope.$params
		var $http = $http
		var $compile = $compile
		var Modal = Modal
		var $location = $location
		var debounce = debounce
		var ModelFactory = ModelFactory
		var Messenger = Messenger
		var FormContainer = FormContainer
		var TabOperator = TabOperator
		var $q = $q
		
		var mobilecontext = mobilecontext
		var functions = $scope.functions;
		var _this = this
		functions.autoswitchcommit = function($event){
			if(mobilecontext){				
				var mobile = _this.getCustomProperty("mobile",$model.actions)
				if(mobile == false || mobile == "false"){
					Messenger.post({
						'message': "此环节不支持移动端操作，请前往网页端处理此事项",
			            'type': 'error',
			            'hideAfter':4
			        });		
			        return
				}				
			}
			//func 为业务表单提交前验证函数
			/*	if(!angular.isUndefined($model.func)){
					var a = $model.func();
					if(a==false){
						Messenger.post({
							'message': "必填项为空！",
				            'type': 'error',
				            'hideAfter':4
				        });	
						return;
					}	
				}
			*/
				if($scope.formentity.global.oppositive || $model.actions.return == undefined){ //积极意见或者流程没有退回配置 流转流程

					if(!$scope.formentity.global.oppositive && $model.actions.return == undefined){
						console.warn("本环节没有退回配置，但是是否定意见。默认继续走流程！")
					}
				
					//func 为业务表单提交前验证函数
					if(!angular.isUndefined($model.func)){
						var a = $model.func("submit");
						if(a==false){
							Messenger.post({
								'message': "必填项为空！",
					            'type': 'error',
					            'hideAfter':3
					        });	
							return;
						}	
					}	
					//如果有下一步选人页面弹出就不用 确认页面了？
					var hasSelectPage = !$scope.formentity.global.handleinline 
						&& $model.actions.freechoose != undefined
						&& $model.actions.freechoose != "";
					if(!hasSelectPage){
						Modal.openConfirm({message:'确定提交？'},function(){
							functions.commit($event)
						})
					}else{
						functions.commit($event)
					}
				}else{
					if($model.actions.return == undefined){
						Messenger.post({
							'message': "本环节没有退回配置，请联系系统维护人员！",
				            'type': 'error',
				            'hideAfter':4
				        });			
				        return
					}
					//退回 需不需要必填项验证？
			/*		if(!angular.isUndefined($model.func)){
						var a = $model.func("return");
						if(a==false){
							Messenger.post({
								'message': "必填项为空！",
					            'type': 'error',
					            'hideAfter':4
					        });	
							return;
						}	
					}
			*/		
					//否定意见 退回流程
					Modal.openConfirm({message:'确定提交？'},function(){
						functions.newField_click($event)
					})	
				}
			}
		
		//提交
		functions.commit = function($event){
			console.log("submited $scope.formentity",$scope.formentity);

//			console.log("$model.funcsbeforsubmit",$model.funcsbeforsubmit);
			//必填项等验证通过才执行 字段页面提交前函数
			$model.funcsbeforsubmit();

			if($model.isCountersign){  //会签环节 不选人，下一步处理人用环节外围配置
				functions.newField5_click($event);
				return;		
			}
			//没有集成那个处理模板
			if(!$scope.formentity.global.handleinline 
					&& $model.actions.freechoose != undefined
					&& $model.actions.freechoose != ""){
				var url = $model.actions.freechoose.openUrl;
				Modal.open(
						url,
						{
							actions:$model.actions,
							formentity:$scope.formentity,
							params:$params.params,
							transitions:$model.getOutTransition.result,
							curUserId:$scope.subject.id
						},
						function(data){
							console.log("choosed",data);
							$model.assigneeList = data.assigneeList==""?undefined:data.assigneeList;//避免传空字符进去，影响判断;
							$model.assigneeid = data.assignee==""?undefined:data.assignee;
							$model.nexthandlegroupid = data.candidateGroups==""?undefined:data.candidateGroups;
							$model.nexthandleid = data.candidateUsers==""?undefined:data.candidateUsers;
							$model.ccInform = data.ccInform;
							$model.transition = data.transition;
							$model.dest = $model.transition.dest;
							$model.transitionId = $model.transition.id;
							
							$model.variables = {
								assignee:$model.assigneeid,
								candidateGroups:$model.nexthandlegroupid,
								candidateUsers:$model.nexthandleid,
								assigneeList:$model.assigneeList,
								wfComment:$model.opinion
							}					
							
							functions.newField5_click($event);
						
					});
				return;
			}

			if(!$scope.formentity.global.handleinline){
				$model.variables = {
					assignee:$model.assigneeid,
					candidateGroups:$model.nexthandlegroupid,
					candidateUsers:$model.nexthandleid,
					assigneeList:$model.assigneeList,
					wfComment:$model.opinion
				}	
			}else{  //集成处理页面模板了 一些参数用模板设置到全局中的值
				$model.variables = $scope.formentity.global.handleinline.variables
				$model.transition = $scope.formentity.global.handleinline.transition
				$model.transitionId = $model.transition.id
				$model.dest = $model.transition.dest
			}
			functions.newField5_click($event);
		}
		//提交
		functions.newField5_click = function($event){
			var transitionId = $model.transitionId;

			//return;
			$model.formId = $model.serviceproduct.result.formId;

			if(!angular.isUndefined($scope.func)){
				var a = $scope.func();	
			}	
			
			var userId =  $scope.subject.id;
			var processDefinitionId = $params.params.processDefinitionId;
			var moduleId =  $params.params.moduleId;
			var taskDefKey =  $params.params.taskDefKey;
			$http.post("flow/goanywhere",
				{
					ccInform:$model.ccInform,
					transition:$model.transition,
					transitionId:transitionId,
					formId:$model.formId,
					wfOperator:{
						userId:userId,
						businessData:{
							moduleId:moduleId,
							businessKey:$model.businessKey
						}
				    },
				    businessKey:$params.params.businessKey,
					isStart:false,
					processDefinitionId:$params.params.processDefinitionId,
					moduleId:$params.params.moduleId,
					currenTaskId:$params.params.taskId,
					destTaskDefinitionKey:$model.dest.id,
					useHisAssignee:false,
					variables:$model.variables,
					entity:$scope.formentity,
					proInsId:$params.params.processInstanceId,
					taskDefKey:$params.params.taskDefKey,
					opinion:$model.opinion
				
			}).success(function(data){
				if(data.result == "200"){
					 Messenger.post({
						 'message': "  操作成功！",
						 'type': 'success',
						 'hideAfter':4
					 });
					 if(mobilecontext){
						 functions.jumpSuccess()
					 }else{		
						 if(Modal.instance){
							 Modal.instance.close()
						 }else{
							 TabOperator.closeTab(TabOperator.getCurrentTab());							 
						 }
					 }
					 
				}else{
					 Messenger.post({
						 'message': "  something wrong！",
						 'type': 'error',
						 'hideAfter':4
					 });			
				}
			});		
		}
		//流程监控
		functions.newField2_click = function($event){
			var paramsjson = {
				processInstanceId:$params.params.processInstanceId,
				processDefinitionId:$params.params.processDefinitionId
			}
			Modal.open("f/monitorProcess",paramsjson);			
		}	

		//回退
		functions.newField_click = function($event){

			//提交前函数 各字段可以在这里把自己的数据放入业务js对象
			$model.funcsbeforsubmit();	
			
			var userId =  $scope.subject.id;
			var processDefinitionId = $params.params.processDefinitionId;
			var moduleId =  $params.params.moduleId;
			var taskDefKey =  $params.params.taskDefKey;
			variables = {
			}
			$http.post("flow/reject",
				{
					rejectMessage:"reject",
					
					formId:$model.formId,
					wfOperator:{
						userId:userId,
						businessData:{
							moduleId:moduleId,
							businessKey:$model.businessKey
						}
				    },
				    curActivity:$model.transition.src,
				    businessKey:$params.params.businessKey,
					isStart:false,
					processDefinitionId:$params.params.processDefinitionId,
					moduleId:$params.params.moduleId,
					currenTaskId:$params.params.taskId,
					destTaskDefinitionKey:$model.actions.return.returnTo,
					useHisAssignee:true,
					variables:variables,
					entity:$scope.formentity,
					proInsId:$params.params.processInstanceId,
					taskDefKey:$params.params.taskDefKey,
					opinion:$model.opinion		
			}).success(function(data){
					 Messenger.post({
					    'message': "退回成功",
					    'type': 'success',
					    'hideAfter':4
					 });
					 if(mobilecontext){
						 functions.jumpSuccess()
					 }else{
						 if(Modal.instance){
							 Modal.instance.close()
						 }else{
							 TabOperator.closeTab(TabOperator.getCurrentTab());							 
						 }
					 }					 
				});		
		}	

		//tab页的验证函数
		functions.dotabchecks = function(){
			var a = $model.tabcheckfuncs;
			var result = {"status":true,"msg":"everthing is ok!"};
			for(var aa in a){
				var checkresult = a[aa].func();
				if(checkresult.status==false){
//					console.log(a[aa]);
					return  checkresult;
				}
			}
			return result;	
		},

		functions.dateconvert = function(a){
			var date = new Date(a);
			return date.Format("MM/dd hh:mm");;
		}	

		//生成随机数
		functions.timeGenerator = function(){
		/*	 var day=new Date();
			   var Year=0;
			   var Month=0;
			   var Day=0;
			   var Hour = 0;
			   var Minute = 0;
			   var Second = 0;
			   var CurrentDate="";
			   //初始化时间
			   Year       = day.getYear();
			   Month      = day.getMonth()+1;
			   Day        = day.getDate()
			   Hour       = day.getHours();
			   Minute     = day.getMinutes();
			   Second     = day.getSeconds();
			   CurrentDate = (Year+"")+(""+Month)+(Day+"")+(""+Hour)+(""+Minute)+(Second+"");
		*/
			var a = Math.random()*(10000000000000)
			var ran = Math.floor(a)
			return ran;
				
		}
		
		functions.start_flow = function($event){
			//	func 为业务表单提交前验证函数
			if(!angular.isUndefined($model.func)){
				//提交前验证
				var a = $model.func("submit");
				if(a==false){
					Messenger.post({
						'message': "必填项为空或格式不正确！",
			            'type': 'error',
			            'hideAfter':4
			        });	
					return;
				}	
			}
			if($model.isCommit){
				Messenger.post({
					'message': "流程已提交，请勿重复提交！",
		            'type': 'error',
		            'hideAfter':4
		        });	
		        return;
			}
			//如果有下一步选人页面弹出就不用 确认页面了？
			var hasSelectPage = $model.actions.freechoose != undefined;
			if(!hasSelectPage){
				Modal.openConfirm({message:'确定提交？'},function(){
					functions.start_flowdo($event)
				})		
			}else{
				functions.start_flowdo($event)
			}
		}
		
		functions.start_flowdo = function($event){
		 	//提交前函数 各字段可以在这里把自己的数据放入业务js对象
		 	$model.funcsbeforsubmit();
		 		
			var checkresult = functions.dotabchecks();
			if(checkresult.status==false){
				Messenger.post({
					'message': checkresult.msg,
		            'type': 'error',
		            'hideAfter':4
		        });		
		        return;	
			}
			if($model.actions.freechoose!=undefined){
				var url = $model.actions.freechoose.openUrl;
				console.log('$model.getOutTransition.result ',$model.getOutTransition.result);
				Modal.open(
						url,
						{
							actions:$model.actions,
							formentity:$scope.formentity,
							params:$params.params,
							transitions:$model.getOutTransition.result,
							curUserId:$scope.subject.id
						},
						function(data){
							console.log("choosed",data);
							$model.assigneeList = data.assigneeList==""?undefined:data.assigneeList;//避免传空字符进去，影响判断;
							$model.assigneeid = data.assignee==""?undefined:data.assignee;
							$model.nexthandlegroupid = data.candidateGroups==""?undefined:data.candidateGroups;
							$model.nexthandleid = data.candidateUsers==""?undefined:data.candidateUsers;
							$model.ccInform = data.ccInform;
							$model.transition = data.transition;
							$model.dest = $model.transition.dest;
							$model.transitionId = $model.transition.id;
							functions.submitflow_click($event);		
				});
				return;
			}
			functions.submitflow_click($event);
		}
		//提交流程
		functions.submitflow_click = function($event){
			var processDefinitionId = $params.params.processDefinitionId;
			var moduleId =  $params.params.moduleId;
			var taskDefKey =  $params.params.taskDefKey;
			var userId = $scope.subject.id;

			variablesJson = {
					assignee:$model.assigneeid,
					assigneeList:$model.assigneeList,
					candidateGroups:$model.nexthandlegroupid,
					candidateUsers:$model.nexthandleid,
					"productNo":$location.search().productNo,
					"formId":$model.serviceproduct.result.formId,
					"serviceTypeId":$model.serviceproduct.result.serviceTypeId
					};

			$scope.formentity.businessKey = $model.businessKey;
			$scope.formentity.moduleId = moduleId;
//			console.log("formId",$model.formId);
//			$model.formId = $location.search().formId;
			$http.post("flow/startflow",
					{
								ccInform:$model.ccInform,
								transition:$model.transition,
								moduleId:moduleId,
								formId:$model.formId,
								businessKey:$model.businessKey,
								processDefinitionId:processDefinitionId,
								variables:variablesJson,
								entity:$scope.formentity,
								wfOperator:{
									userId:userId,
									businessData:{
										moduleId:moduleId,
										businessKey:$model.businessKey
									}
								}
							 
					}
					).success(function(data){
							if(data.result=='200'){
								$model.isCommit = true;

								if(mobilecontext){
									functions.jumpSuccess()
								}else{
									Messenger.post({
										'message': "操作成功!",
						                'type': 'success',
						                'hideAfter':4
						            });									
									 if(Modal.instance){
										 Modal.instance.close()
									 }else{
										 TabOperator.closeTab(TabOperator.getCurrentTab());							 
									 }								}									
									
							}else{
									Messenger.post({
										'message': "流程提交失败，请关闭本页面后重试",
						                'type': 'error',
						                'hideAfter':4
						            });				
							}
				  });	
		}	
		
		functions.close_tab = function(){
//			TabOperator.closeTab(TabOperator.getCurrentTab());
		}
		//tab选择
		functions.newGroup17_onDynamicSelected = function(tab){
			var funcs = $model.selectfuncs;
			console.log("$model.selectfuncs",$model.selectfuncs);
			for(var i=0;i<funcs.length;i++){
				funcs[i].func(tab);
			}
		}
		// save draft
		functions.newField4_click = function($event){
			if(!angular.isUndefined($model.func)){
				var a = $model.func("draft");
				if(a==false){
					Messenger.post({
						'message': "验证不通过！",
			            'type': 'error',
			            'hideAfter': 2
			        });	
					return;
				}	
			}	
			var businessKey;
//			地址栏没有 则是新工单
			if(angular.isUndefined($model.formBusinessKey)){
				businessKey = $model.businessKey;
				$scope.formentity.businessKey = $model.businessKey;
			}else{
			//地址栏中已经有businessKey 说明是草稿的第二次保存 businessKey 用原来的
				$scope.formentity.businessKey = $model.formBusinessKey;
				businessKey = $model.formBusinessKey;
			}
			$scope.formentity.status = "draft";
			var form = {};
			form.formId = $model.formId;
			$http.post("ws/updateFormDataWithExternalTable",
			{
				businessKey:businessKey,
				procInsId:$params.params.processInstanceId,
				entityjson:$scope.formentity,
				curActivity:$model.transition.src,
				nextActivity:$model.transition.dest,
				form:form,
				curUserId:$scope.subject.id,
				map:{"moduleId":$params.params.moduleId}
			}).success(function(){
				 if(mobilecontext){
					 functions.jumpSuccess()
				 }else{
					 if(Modal.instance){
						 Modal.instance.close()
					 }else{
						 TabOperator.closeTab(TabOperator.getCurrentTab());							 
					 }					 
					 Messenger.post({
							'message': "保存成功!",
			                'type': 'success',
			                'hideAfter':4
			            });					 
				 }				

			});
		},
		 //结束流程按钮
		functions.button_click = function($event){
		 	Modal.openConfirm({message:"确认删除流程实例？删除后将不可恢复！"},
		 			function(){
		 				$http.post("ws/deleteprocessins",
		 					{					
		 							json:$scope.formentity,
		 							wfOperator:{
		 								userId:$scope.subject.id,
		 								businessData:{
		 									moduleId:$params.params.moduleId,
		 									businessKey:$model.businessKey
		 								}
		 						    },
		 						    deleteReason:"用户删除",
		 						    processInstanceId:$params.params.processInstanceId,
		 						    formId:$model.formId,
		 						    deleteHistory:true				
		 					}).success(function(){
		 						 if(mobilecontext){
		 							 functions.jumpSuccess()
		 						 }else{
		 	 						Messenger.post({
											'message': "删除成功!",
							                'type': 'success',
							                'hideAfter':4
							            });			 							 
		 							 if(Modal.instance){
		 								 Modal.instance.close()
		 							 }else{
		 								 TabOperator.closeTab(TabOperator.getCurrentTab());							 
		 							 }		 						 }		 						
		 						
					
		 					});
		 			});
		 },
		  //挂起流程按钮
		 functions.suspend_click = function($event){
		 	Modal.openConfirm({message:"确认挂起流程实例？挂起后需要释放才能继续处理流程！"},
		 			function(){
		 				$http.post("ws/suspendProcessIns",
		 					{					
		 							json:$scope.formentity,
		 							wfOperator:{
		 								userId:$scope.subject.id,
		 								businessData:{
		 									moduleId:$params.params.moduleId,
		 									businessKey:$model.businessKey
		 								}
		 						    },
		 						    suspendReason:"用户挂起",
		 						    processInstanceId:$params.params.processInstanceId,
		 						    formId:$model.formId,
		 						    updateBusinessForm:true				
		 					}).success(function(){
								 if(Modal.instance){
									 Modal.instance.close()
								 }else{
									 TabOperator.closeTab(TabOperator.getCurrentTab());							 
								 }		 						
								 Messenger.post({
									'message': "挂起成功!",
					                'type': 'success',
					                'hideAfter':4
					            });					
		 					});
		 			});
		 },
		 functions.initGlobalParam = function(){
		 	//全局 用来通信
			$scope.formentity.global = {}
			//默认积极意见 让流程可以流转
			$scope.formentity.global.oppositive = true
			var actions = $model.activityactions.result
			//传阅按钮
			if(actions.sendviewuser){
				if(!$scope.formentity.global.buttons){
					$scope.formentity.global.buttons = new Array()
				}
				var sendbutton = {
						id:"senduser",
						name:"传阅",
						click:function(){
							Modal.open("f/multipleuserchoose4",{choosed:$model.senduserchoosed},function(data){
								$model.senduserchoosed = functions.removeuserorgtreeprefix(data.users);
								functions.generateString();
							});				
							
						}
						}
				$scope.formentity.global.buttons.push(sendbutton)
				
			}
			//退回按钮
			if(actions.rtnbutton && $model.actions.return){
				if(!$scope.formentity.global.buttons){
					$scope.formentity.global.buttons = new Array()
				}
				var button = {
						id:"returnbutton",
						name:"退回",
						click:function($event){
								if($model.actions.return == undefined){
									Messenger.post({
										'message': "本环节没有退回配置，请联系系统维护人员！",
							            'type': 'error',
							            'hideAfter':4
							        });			
							        return
								}
								//退回 需不需要必填项验证？
						/*		if(!angular.isUndefined($model.func)){
									var a = $model.func("return");
									if(a==false){
										Messenger.post({
											'message': "必填项为空！",
								            'type': 'error',
								            'hideAfter':4
								        });	
										return;
									}	
								}
						*/		
								//否定意见 退回流程
								Modal.openConfirm({message:'确定提交？'},function(){
									functions.newField_click($event)
								})							
							}
						}
				$scope.formentity.global.buttons.push(button)	
			}
			//观察者
			var subject = {}
			subject.obs = new Array()
			subject.changed = function(fieldNo,data){
				this.obs.forEach(function(ob){
					ob.func(fieldNo,data)
				})
			}
			subject.regist = function(fieldNo,func){
				var ob = {}
				ob.fieldNo = fieldNo
				ob.func = func
				this.obs.push(ob)
			}
			$scope.formentity.global.subject = subject
		 }
		 //生成以逗号分隔多个id的一个字符串
		 functions.generateString = function(){
			var s = undefined;
			var nameStr = undefined;
			$model.senduserchoosed.forEach(function(e,i){
				if(s==undefined){
					s = e.id;
					nameStr = e.name;
				}else{
					s = s + ","+e.id;
					nameStr = nameStr + "," + e.name;
				}
			});
			$scope.formentity.wfsendUserRead = s;
		}
		functions.removeuserorgtreeprefix = function(users){
			for(var i in users){
				if(users[i].id.indexOf("user") !== -1){
					users[i].id = users[i].id.substring(5)
				}	
			}
			return users;
		}		
		functions.jumpSuccess = function(){
			location.href = "workflow/mobile/success.html"
		}
		
		return functions;
	}
});