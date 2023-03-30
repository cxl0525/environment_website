// JavaScript Document
var flag=true;
function checkAll(){
	checkActName();
	if(flag==false) return flag;
	checkActTag();
	if(flag==false) return flag;
	checkActStartDate();
	if(flag==false) return flag;
	checkActDeadLine();
	if(flag==false) return flag;
	checkActRegDate();
	if(flag==false) return flag;
	checkActRegDeadline();
	if(flag==false) return flag;
	checkActPlace();
	if(flag==false) return flag;
	checkActSlogan();
	
	return flag;
}
function checkActName(){
			var actName=document.getElementById("actName").value;
			var actNameSpan=document.getElementById("actNameSpan");
			if(actName=="" || actName==null){
				actNameSpan.innerHTML="<font>活动名称不能为空</font>";
				flag=false;
			}
			else{
				actNameSpan.innerHTML="";
				flag=true;
			}
	
		}
		
		function checkActTag(){
			var actTag=document.getElementById("actTag").value;
			var actTagSpan=document.getElementById("actTagSpan");
			if(actTag=="请选择类别" || actTag==null){
				actTagSpan.innerHTML="<font>请选择活动类别</font>";
				flag=false;
			}
			else{
				actTagSpan.innerHTML="";
				flag=true;
			}
		}
		
		function checkActStartDate(){
			var actStartDate=document.getElementById("actStartDate").value;
			actStartDate = actStartDate.replace(/-/g,"/");
			var actStartDateSpan=document.getElementById("actStartDateSpan");
			var actualDate=new Date();
			var actualStartDate=new Date(Date.parse(actStartDate));
		
			if(actStartDate=="" || actStartDate==null){
				actStartDateSpan.innerHTML="<font>请选择活动开始日期</font>";
				flag=false;
			}
			else if(actualStartDate<=actualDate){
				actStartDateSpan.innerHTML="<font>活动开始时间应晚于当前日期</font>";
				flag=false;
			}
			else{
				actStartDateSpan.innerHTML="";
				flag=true;
			}
		}
		
		function checkActStartTime(){
			var artStartTime=document.getElementById("actStartTime").value;
			var actStartDateSpan=document.getElementById("actStartDateSpan");
			var times=artStartTime.split(":");
			var hour=parseInt(times[0]);
			if(artStartTime==null || artStartTime==""){
				actStartDateSpan.innerHTML="<font>请选择活动开始时间</font>";
			}
			else if(hour<8){
				actStartDateSpan.innerHTML="<font>活动开始时间不得早于八点</font>";
			}
			else{
				actStartDateSpan.innerHTML="";
			}
			
			
		}
		
		function checkActDeadLine(){
			var actDeadLine=document.getElementById("actDeadLine").value;
			var actDeadLineSpan=document.getElementById("actDeadLineSpan");
			var actStartDate=document.getElementById("actStartDate").value;
			if(actDeadLine=="" || actDeadLine==null){
				actDeadLineSpan.innerHTML="<font>请选择活动结束时间</font>";
				flag=false;
			}
			else if(actDeadLine<actStartDate){
				actDeadLineSpan.innerHTML="<font>活动结束时间应晚于开始日期</font>";
				flag=false;
			}
			else{
				actDeadLineSpan.innerHTML="";
				flag=true;
			}
			
		}
		
		function checkActRegDate(){
			var actRegDate=document.getElementById("actRegDate").value;
			var actRegDateSpan=document.getElementById("actRegDateSpan");
			var actStartDate=document.getElementById("actStartDate").value;
			if(actRegDate=="" || actRegDate==null){
				actRegDateSpan.innerHTML="<font>请选择报名开始时间</font>";
				flag=false;
			}
			else if(actRegDate>=actStartDate){
				actRegDateSpan.innerHTML="<font>报名开始时间应早于活动开始日期</font>";
				flag=false;
			}
			else{
				actRegDateSpan.innerHTML="";
				flag=true;
			}
		}
		
		function checkActRegDeadline(){
			var actRegDeadline=document.getElementById("actRegDeadline").value;
			actRegDeadline = actRegDeadline.replace(/-/g,"/");
			var actualRegDeadline=new Date(Date.parse(actRegDeadline));
			
			var actRegDeadlineSpan=document.getElementById("actRegDeadlineSpan");
			
			//活动开始时间
			var actStartDate=document.getElementById("actStartDate").value;
			actStartDate = actStartDate.replace(/-/g,"/");
			var actualStartDate=new Date(Date.parse(actStartDate));
			
			//报名开始时间
			var actRegDate=document.getElementById("actRegDate").value;
			
			var idays=dateDiff(actualRegDeadline, actualStartDate);
			if(actRegDeadline=="" || actRegDeadline==null){
				actRegDeadlineSpan.innerHTML="<font>请选择报名截止时间</font>";
				flag=false;
			}
			
			else if(idays<2){
				actRegDeadlineSpan.innerHTML="<font style='font-size:14px;'>报名截止时间应早于活动开始日期至少两天</font>";
				flag=false;
			}
			else if(actRegDeadline<=actRegDate){
				actRegDeadlineSpan.innerHTML="<font>报名截止时间应晚于报名开始日期</font>";
				flag=false;
			}
			else{
				actRegDeadlineSpan.innerHTML="";
				flag=true;
			}
		}
		
		function checkActPlace(){
			var province=document.getElementById("province").value;
			var city=document.getElementById("city").value;
			
			if(province=="请选择省份"){
				document.getElementById("actPlaceSpan").innerHTML="<font color='red'>请选择省份，城市</font>";
				flag=false;
			}
			else{
				document.getElementById("actPlaceSpan").innerHTML="";
				flag=true;
			}
		}
			
		function checkActSlogan(){
			var actSlogan=document.getElementById("actSlogan").value;
			var actSloganSpan=document.getElementById("actSloganSpan");
			
			if(actSlogan=="" || actSlogan==null){
				actSloganSpan.innerHTML="<font>宣传口号不能为空</font>";
				flag=false;
			}
			else if(actSlogan.length>16){
				actSloganSpan.innerHTML="<font>限16个字</font>";
				flag=false;
			}
			else{
				actSloganSpan.innerHTML="";
				flag=true;
			}
		}
		
		//计算两个天数的差值
		function dateDiff(sDate1,  sDate2){ 
				var iDays;
    			var times1=sDate1.getTime();
				var times2=sDate2.getTime();
    			iDays  = Math.floor(Math.abs(times1 - times2)  /  1000  /  60  /  60 /24 ) ;
    			return  iDays;
		}