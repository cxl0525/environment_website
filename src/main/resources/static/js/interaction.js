// // JavaScript Document
// 	function clickLike(obj,comId){
// 	//alert(comId)
// 		var span_array=obj.getElementsByTagName("span");
// 		var number_span=span_array[0];
// 		var number=parseInt(number_span.innerHTML);
//
// 		var img_array=obj.getElementsByTagName("img");
// 		var number_img=img_array[0];
//
// 		if(number_img.src.endsWith("like.PNG")){
//
// 			$.ajax({
// 				type: "POST",
// 				url: "/commentuserlike",
// 				contentType: 'application/json',
// 				data: JSON.stringify({
// 					"comId": parseInt(comId),
// 					"isLike": 1
//
// 				}),
// 				success: function (result) {
// 					//alert(result.toString())
// 					console.log(result);
// 					number=number+1;
// 					number_span.innerHTML=number;
// 					number_img.src="/images/like_red.PNG";
// 					number_span.style.color="red";
// 				},
// 				error: function (result) {
// 					alert("错误："+result.toString())
// 				}
// 			})
//
// 		}
// 		else if(number_img.src.endsWith("like_red.PNG")){
//
//
// 			$.ajax({
// 				type: "POST",
// 				url: "/commentuserlike",
// 				contentType: 'application/json',
// 				data: JSON.stringify({
// 					"comId": parseInt(comId),
// 					"isLike": -1
//
// 				}),
// 				success: function (result) {
// 					//alert(result.toString())
// 					console.log(result);
// 					number=number-1;
// 					number_span.innerHTML=number;
// 					number_img.src="/images/like.PNG";
// 					number_span.style.color="#B9B5B5";
// 				},
// 				error: function (result) {
// 					alert("错误："+result.toString())
// 				}
// 			})
// 		}
//
//
// 	}
//
// 		function showLaunchInteractionDiv(){
//
// 			document.getElementById('launch_interaction_div').style.display='block';
//
// 			document.getElementById('launch_interaction_bg').style.display='block';
//
// 		}
//
//
//
// 		function closeLaunchInteractionDiv(){
//
// 			document.getElementById('launch_interaction_div').style.display='none';
//
// 			document.getElementById('launch_interaction_bg').style.display='none';
//
// 		}
//
// 		function submitInteraction(comId){
//
// 			var interaction_comment=document.getElementById("comContent").value;
//
// 			//alert(interaction_comment);
//
// 			document.getElementById('launch_interaction_div').style.display='none';
//
// 			document.getElementById('launch_interaction_bg').style.display='none';
// 			if(interaction_comment==""||null){
// 				alert("评论内容不能为空！")
// 			}else {//ajax提交信息（敏感字处理）
// 				//alert(interaction_comment)
// 				postData(parseInt(comId), interaction_comment);
// 			}
//
//
// 		}
// function postData(comId, comContent) {
// 	alert(comId+comContent)
// 	$("#comment-container").load(/*[[@{/commentAndTopic}]]*/"",{
// 		"comParentComment.comId": comId,
// 		"comContent": comContent
// 	},function (responseTxt, statusTxt, xhr) {
// //        $(window).scrollTo($('#comment-container'),500);
// 		clearContent();//不生效
//
// 	});
//
// }
//
// function clearContent() {
// 	$("[id='comContent']").val('');
//
// }