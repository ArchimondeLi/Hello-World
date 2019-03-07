$(function() {
	// < 遮罩层结束
	/* 立即抽奖按钮点击事件 */
	$(".anniu").click(function() {
		$(".alert_bg").hide();
		$.ajax({
			type : "POST",// 方法类型
			dataType : "json",// 预期服务器返回的数据类型
			url : "./draw",// url
			success : function(res) {
				if(res.code==1){
					var rest = res.data;
					if(res.data.awardCode==null){
						alert("很遗憾，没有抽中")
						var num = Number($("#num").text())-1;
						if(num>=0){
							$("#num").html(num);
						}
					}else{
						var num = Number($("#num").text())-1;
						if(num>=0){
							$("#num").html(num);
						}
						
						$(".aname").html(rest.awardName);
						$(".alert_prize").attr("src",rest.awardImage);
						if(num>=1){
							$(".alert_bg").show();
						}else{
							$(".mg").show();
						}
					}
				}else if(res.code==0){
					alert(res.msg)
				}else{
					alert(res.msg)
				}
			}
		});
	});
	
	/* 弹出框按钮点击事件 */ 
	$(".goTo").click(function() {
		$(".alert_bg").hide();
		window.location.href = "./prize";
	});
	
	/* 弹出框关闭按钮 */
	$(".alert_close").click(function() {
		$(".alert_bg").hide();
	});
	
	/* 弹出框关闭按钮 */
	$(".guanbi").click(function() {
		$(".mg").hide();
	});

})