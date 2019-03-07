$(function () {
    // 选字
    $('.deng_kuang_boxs').click(function () {
        var asd = $(this).find('.denglong_text').is('.denglong_texts');
        if (asd != true) {
            var all = $(this).find('.denglong_text').html();
            var a = $('.tianzi_box_p1').html();
            var b = $('.tianzi_box_p2').html();
            var c = $('.tianzi_box_p3').html();
            var d = $('.tianzi_box_p4').html();
            if (a == '?') {
                $(this).find('.denglong').addClass('denglongs');
                $(this).find('.denglong1').removeClass('denglongs');
                $(this).find('.denglong_text').addClass('denglong_texts');
                $('.tianzi_box_p1').html(all)
            } else if (b == '?') {
                $(this).find('.denglong').addClass('denglongs');
                $(this).find('.denglong1').removeClass('denglongs');
                $(this).find('.denglong_text').addClass('denglong_texts');
                $('.tianzi_box_p2').html(all)
            } else if (c == '?') {
                $(this).find('.denglong').addClass('denglongs');
                $(this).find('.denglong1').removeClass('denglongs');
                $(this).find('.denglong_text').addClass('denglong_texts');
                $('.tianzi_box_p3').html(all)
            } else if (d == '?') {
                $(this).find('.denglong').addClass('denglongs');
                $(this).find('.denglong1').removeClass('denglongs');
                $(this).find('.denglong_text').addClass('denglong_texts');
                $('.tianzi_box_p4').html(all)
            }
        }
    })


    // 重置成语
    $('.btn_again1').click(function () {
    	//当答对题目大于等于3时，不允许重置
    	if(number-trueNumber>2){
    		alert("呀！闯关失败，再来一次！");
    		return; 
    	}
    	
    	if(number<5){ //答题数目小于5题 
    		var ss = '?';
            $('.denglong').removeClass('denglongs');
            $('.denglong1').addClass('denglongs');
            $('.denglong_text').removeClass('denglong_texts');
            $('.tianzi_box_p').html(ss);
    	}else{
    		alert("本轮答题次数已用完");
    	}
    })
    // 确定
    $('.btn_again2').click(function () {
    //	debugger
    	if(number<5){
    		
    		
    		//点击确定提交，最后一行不为？号
    		var d = $('.tianzi_box_p4').html();
            if (d != "?") {
                
            	number++;
            	
            	//获取每个tianzi_box_p ，拼接字符串
            	var str = '';
            	var aps = document.getElementsByClassName("tianzi_box_p");
        		for(var i=0;i<aps.length;i++){
        			str += aps[i].innerHTML;
        		}
        		
        		//判断成语对错
        		if(str==listArr[number-1].answer){
        			//答对题目，当前状态改为绿色（正确，错误，默认三种图片）
        			$('.duicuos'+number).find('.true').show();
        			//答对题目+1
        			trueNumber++;
        			$(".content_p_span1").html(trueNumber);
        			//当正确题目等于3时，弹出进入抽奖页面链接
    	            if(trueNumber==3){
    	            	
    	            	$('.mg').show();
    	            	//alert("GameNext");
    	            //	return;
    	            	//进入验证session
    	            	 $('.shoupeng1').click(function () {
    	            			$.ajax({
    	    	            		url:'./nextstep',
    	    	            		type:'post',
    	    	            		dataType:'json',
    	    	            		data:{},
    	    	            		async:true,
    	    	            		success:function(res){
    	    	            			if(res.status==1){
    	    	            				leave('../pig/pigRain');
    	    	            			}
    	    	            		}
    	    	            	});
    	            	 })
    	            
    	            	
    	            }
        		}else{
        			//打错题目，当前状态改为红色
        			$('.duicuos'+number).find('.false').show();
        			//当答对题目大于等于3时，游戏结束
        			if(number-trueNumber>=3){ //答3对0，答4对1，答5对2 结束
        				$('.btn_restart').show();
        			    $('.btn_again2').unbind("click"); //移除点击事件
        			    alert('闯关失败，再来一次！');
        	    		return ;
        	    		//返回结束页面
        	    	}
        			
        			
        		}
        	//	debugger
        		/*alert("number"+number);
        		alert("trueNumber"+trueNumber);
        		if(number-trueNumber==2){ //四题对2个，最后一次不加1
        			$(".content_p_span").html(number);
        		}else{
        			
        		}*/
        		
        		if(number==5){
        			$(".content_p_span").html(number);
        		}else{
        			//进入下一轮答题，当前题目+1
            		$(".content_p_span").html(number+1);
        		}
        		if(number<5){
        			flushTitle(); //刷新样式，并且赋值
        		}
            }else{
            	alert("请填完成语")
            	return;
            }
		}else{
			alert("本轮答题次数已用完");
			$('.btn_restart').show();
			//返回结束页面
			
		}
    })
    // 重新挑战
    $('.btn_restart').click(function () {
    	/*var ss = '?';
        $('.content_p_span').html(0);
        $('.content_p_span1').html(0);
        $('.duicuoss').find('img').hide();
        $('.tianzi_box_p').html(ss);
        $('.denglong').removeClass('denglongs');
        $('.denglong1').addClass('denglongs');
        $('.denglong_text').removeClass('denglong_texts');*/
    	location.reload(); //刷新页面
    })
    
    $('.shoupeng3').click(function () {
        $('.mg').hide();
    })

})