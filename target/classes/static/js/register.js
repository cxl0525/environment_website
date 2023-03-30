// JavaScript Document
var Username=false;
var Password=false;
var ConfirmPassword=false;
var Phone=false;
var RealName=false;
var Identity=false;
var Email=false;
var QQNum=false;
var Province=false;
function check(){

	//alert("注册成功");
	Username=false;
	Password=false;
	ConfirmPassword=false;
	Phone=false;
	RealName=false;
	Identity=false;
	Email=false;
	QQNum=false;
	Province=false;

	checkUsername();
	checkPassword();
	checkConfirmPassword();
	checkPhone();
	checkRealName();
	checkIdentity();
	checkEmail();
	checkQQ();
	if(Username&&Password&&ConfirmPassword&&Phone&&RealName&&Identity&&Email&&QQNum&&Province){
		return true;
	}
	return false;

}

function checkUsername(){
	var username=document.getElementById("userName").value;
	if(username==null || username==""){
		document.getElementById("usernameSpan").innerHTML="<font color='red'>用户名不能为空</font>";
	}
	else{
		document.getElementById("usernameSpan").innerHTML="";
		Username=true;
	}
}

function checkPassword(){
	var password=document.getElementById("userPassword").value;
	if(password==null || password==""){
		document.getElementById("pwdSpan").innerHTML="<font color='red'>密码不能为空</font>";
	}
	else{
		document.getElementById("pwdSpan").innerHTML="";
		Password=true;
	}
}

function checkConfirmPassword(){
	var confirmPassword=document.getElementById("confirmPassword").value;
	var password=document.getElementById("userPassword").value;
	//alert(password+"........."+confirmPassword);
	if(confirmPassword==null || confirmPassword==""){
		document.getElementById("confirmPwdSpan").innerHTML="<font color='red'>确认密码不能为空</font>";
	}
	else if(password!=confirmPassword){
		document.getElementById("confirmPwdSpan").innerHTML="<font color='red'>两次密码不一致</font>";

	}
	else{
		//document.getElementById("confirmPwdSpan").innerHTML="";
		//alert("ok");
		document.getElementById("confirmPwdSpan").innerHTML="";
		ConfirmPassword=true;
	}

}

function checkPhone(){
	var phone=document.getElementById("userPhone").value;
	var re = /^1\d{10}$/;
	if(phone==null || phone==""){
		document.getElementById("phoneSpan").innerHTML="<font color='red'>电话号码不能为空</font>";
	}
	else if(!re.test(phone)){
		document.getElementById("phoneSpan").innerHTML="<font color='red'>请填写11位手机号码</font>";
	}
	else{
		document.getElementById("phoneSpan").innerHTML="";
		Phone=true;
	}

}
function checkRealName(){
	var realName=document.getElementById("userRealName").value;
	if(realName==null || realName==""){
		document.getElementById("realNameSpan").innerHTML="<font color='red'>真实姓名不能为空</font>";
	}
	else{
		document.getElementById("realNameSpan").innerHTML="";
		RealName=true;
	}
}


function checkIdentity(){
	var identityID=document.getElementById("userIdentity").value;

	if(identityID==null || identityID==""){
		document.getElementById("identitySpan").innerHTML="<font color='red'>身份证号不能为空</font>";
	}

	else if(!(/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(identityID))){


		document.getElementById("identitySpan").innerHTML="<font color='red'>身份证号码格式错误</font>";

	}
	else{
		document.getElementById("identitySpan").innerHTML="";
		Identity=true;
	}

}

function checkEmail(){
	var email=document.getElementById("userEmail").value;
	var province=document.getElementById("province").value;
	var city=document.getElementById("city").value;

	if(province=="请选择省份"){
		document.getElementById("addressSpan").innerHTML="<font color='red'>请选择省份，城市</font>";
		Province=false;
	}
	else{
		Province=true;
	}

	if(email==null || email==""){
		document.getElementById("emailSpan").innerHTML="<font color='red'>邮箱不能为空</font>";
	}
	else if (!/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(email)) {
		document.getElementById("emailSpan").innerHTML="<font color='red'>邮箱格式错误</font>";
	}
	else{
		document.getElementById("emailSpan").innerHTML="";
		Email=true;
	}
}

function checkQQ(){
	var QQ=document.getElementById("userQQId").value;
	var reg = /^[1-9][0-9]{4,9}$/gim;

	if (!reg.test(QQ) && QQ!=null && QQ!="") {
		document.getElementById("qqSpan").innerHTML="<font color='red'>QQ号格式错误</font>";
	}
	else{
		document.getElementById("qqSpan").innerHTML="";
		QQNum=true;
	}
}
			