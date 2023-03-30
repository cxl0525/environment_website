// JavaScript Document
var flag1;
var flag2;
function isSamePwd(){
	var newPwd=document.getElementById("newPwd").value;
	var newPwdAgain=document.getElementById("newPwdAgain").value;
	if(newPwd==newPwdAgain){
		document.getElementById("pwd_info").innerHTML="";
		flag1=true;
	}
	else{
		document.getElementById("pwd_info").innerHTML="<font>两次新密码不一致</font>";
		flag1=false;
	}
}

function checkPwd(){
	return flag1;
}

function checkPhone(){
			var phone=document.getElementById("userPhone").value;
			var re = /^1\d{10}$/;
			
			if(!re.test(phone)){
				document.getElementById("phoneSpan").innerHTML="<font color='red'>请填写11位手机号码</font>";
				flag2=false;
			}
			else{
				document.getElementById("phoneSpan").innerHTML="";
				flag2=true;
			}
			
		}
		
		function checkIdentity(){
			var identityID=document.getElementById("userIdentity").value;
			
			
			if(!(/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(identityID))){

                
                document.getElementById("identitySpan").innerHTML="<font color='red'>身份证号码格式错误</font>";
				flag2=false;

            }
			else{
				document.getElementById("identitySpan").innerHTML="";
				flag2=true;
			}

        }
		
		function checkEmail(){
			var email=document.getElementById("userEmail").value;
			
			if (!/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(email)) { 
				document.getElementById("emailSpan").innerHTML="<font color='red'>邮箱格式错误</font>"; 
				flag2=false;
			}
			else{
				document.getElementById("emailSpan").innerHTML="";
				flag2=true;
			}
		}
		
		function checkQQ(){
			var QQ=document.getElementById("userQQId").value;
			var reg = /^[1-9][0-9]{4,9}$/gim;
			
			if (!reg.test(QQ) && QQ!=null && QQ!="") {
           		document.getElementById("qqSpan").innerHTML="<font color='red'>QQ号格式错误</font>";
				flag2=false;
        	} 
			else{
				document.getElementById("qqSpan").innerHTML="";
				flag2=true;
			}
		}
			
function checkAll(){
	return flag2;
}