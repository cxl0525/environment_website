// JavaScript Document
	function showDiv(obj,picId){
		var imgSrc=obj.src;
		document.getElementById('popDiv').style.backgroundImage='url('+imgSrc+')';
		document.getElementById('popDiv').style.display='block';
		document.getElementById('bg').style.display='block';


		$.ajax({
			type: "POST",
			url: "/updatePicClicks",
			contentType: 'application/json',
			data: JSON.stringify({
				"picId": picId

			}),
			success: function (result) {
				console.log(result);
				 // $('#picClicksNum').text(parseInt($('#picClicksNum').text())+1);

			},
			error: function (result) {
				alert("错误："+result.toString())
			}
		})

	}

 

	function closeDiv(){
		document.getElementById('popDiv').style.display='none';
		document.getElementById('bg').style.display='none';
		window.location.reload();
	}

	function showLaunchImageDiv(){
		document.getElementById('launch_image_div').style.display='block';
		document.getElementById('launch_image_bg').style.display='block';

	}

 

	function closeLaunchImageDiv(){

		document.getElementById('launch_image_div').style.display='none';

		document.getElementById('launch_image_bg').style.display='none';

	}


	
	
	
	function submitImg(){
		var picType=document.getElementById("picType").value;
		var errorSpan=document.getElementById("errorSpan");
		if(picType=="请选择类型"){
			errorSpan.innerHTML="<font>请选择图片类别</font>";
			return false;
		}
		else{
			return true;
			closeLaunchImageDiv();
			
		}
		
	}