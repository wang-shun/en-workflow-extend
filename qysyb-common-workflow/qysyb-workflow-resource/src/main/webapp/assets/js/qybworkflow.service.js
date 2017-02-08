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
	}	
});