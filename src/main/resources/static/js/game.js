/*获取容器宽高*/
var screenH = document.body.offsetHeight;
var screenW = document.body.offsetWidth;
var RX = screenW - $('.pk').width();
var RY = screenH - $('.pk').height();
var TIMER = '';
var TIMERCLOCK = '';

var top = screenH / 2 - 60; //中心点定位距上长度
var left = screenW / 2 - 80; //中心点定位距左长度

var bottom = screenH - 200;

/*红包模块*/
function PACKAGE() {
    this.speed = '';
    this.x = '';
    this.y = '';
    this.luck_key = this.randomNum(0, 9);
}

PACKAGE.prototype.init = function () { // 初始化
    this.basic();
    this.bindDom();
}

PACKAGE.prototype.randomNum = function (begin, end) { // 范围随机数生成器
    return Math.floor(Math.random() * (end - begin)) + begin;
}

PACKAGE.prototype.basic = function () {
    this.x = parseInt(this.randomNum(0, RX)); // 红包起始x坐标
    this.y = parseInt(this.randomNum(70, 300)); // 红包起始y坐标
    this.speed = this.randomNum(4000, 6000); // 红包运动总时长
}

PACKAGE.prototype.eases = function () { // 红包运动模式
    var easeNum = parseInt(this.randomNum(0, 9));
    switch (easeNum) {
        case 0:
            return 'easeInQuad';
            break;
        case 1:
            return 'swing';
            break;
        case 2:
            return 'easeInExpo';
            break;
        case 3:
            return 'easeInQuint';
            break;
        case 4:
            return 'easeInOutBounce';
            break;
        case 5:
            return 'easeInQuad';
            break;
        case 6:
            return 'swing';
            break;
        case 7:
            return 'easeInExpo';
            break;
        case 8:
            return 'easeInQuint';
            break;
        case 9:
            return 'easeInOutBounce';
            break;
    }
}

PACKAGE.prototype.theme = function (num) { // 红包皮肤
    var themeNum = num || parseInt(this.randomNum(0, 8));
    switch (themeNum) {
        case 0:
            return 'pk1';
            break;
        case 1:
            return 'pk2';
            break;
        case 2:
            return 'pk3';
            break;
        case 3:
            return 'pk4';
            break;
        case 4:
            return 'pk5';
            break;
        case 5:
            return 'pk6';
            break;
        case 6:
            return 'pk7';
            break;
        case 7:
            return 'pk8';
            break;
        case 8:
            return 'pk9';
            break;
    }
}

PACKAGE.prototype.bindDom = function () {
    var lock = this.randomNum(0, 9);
    var _key = this.luck_key;
    var luck = this.theme();
    /*参数*/
    /* if (_key == lock) {
        console.log('%cthis is package', 'background:#aaa;color:red;font-size:16px;');
        luck = this.theme(3);
    } */
    // console.log('key', _key)
    // console.log('lock', lock)
    // console.log('speed', this.speed)

    var pkDom = $('<div class="pk ' + luck + '" data-rom=' + lock + '></div>');
    pkDom.css({
        'left': this.x + 'px',
        'top': '-' + this.y + 'px'
    });
    // console.log("结果：" + lock % 3);
    if (lock % 3 == 0) {
        pkDom.animate({
            top: RY
        }, this.speed, this.eases(), function () {
            $(this).remove();
        });
    } else if (lock % 3 == 1) {
        pkDom.addClass("spin3DX");
        
    } else if (lock % 3 == 2) {
        pkDom.addClass("spin3DY");
        
    }

    pkDom.on('touchstart', function () {
        /* 中奖操作 */
        /* if ($(this).attr('data-rom') == _key) { */
        /* if(_key == lock){ */
        /* alert("this.luck_key:"+_key+",lock:"+lock); */

        if (_key == lock) {
            $(this).stop();
            $(this).addClass('boom');
            $(this).removeClass('pk');
            document.getElementById('myaudios').pause();
            document.getElementById('myaudio').play();
            //动画停止，移除其他小猪
            clearInterval(TIMER);
            clearInterval(TIMERCLOCK);
            $(this).removeClass('spin3DX');
            $(this).removeClass('spin3DY');
            $('.pk').remove();

            var pt = top + 'px';
            var pl = left + 'px';
            var ptm = bottom + 'px';
            $(".shoupeng").show(500);
            
            $(this).animate({
                'top': pt,
                'left': pl,
                'width': '125px',
                'height': '125px'
            }, 1500, function () {
                $(this).addClass('doing');
                setTimeout(function () {
                	leave('./poster');
                    //window.location.href="";
                }, 1600);
            });
        } else if (_key != lock) {

            $(this).addClass("disappear");
            setTimeout(function () {
                $(".disappear").remove()
            }, 270);
        }


        // setTimeout(function () {
        //     $(this).remove();
        // }.bind(this), 200)

    })
    $('body').append(pkDom);
}

PACKAGE.prototype.start = function (alltime, amount) {
    
    var time = 0;
    alltime ? alltime = alltime : alltime = 600000;
    amount ? amount = amount : amount = 50;
    var intervalTime = parseInt(alltime / amount);
    TIMER = setInterval(function () {
        pks.init();
        time++;
        if (time >= amount / 2) { // 前半数红包不进行"钥匙"生成
            this.luck_key = this.randomNum(0, 20);
        }
        if (time > amount) {
            pks.stop();
        }
    }.bind(this), intervalTime);
}
PACKAGE.prototype.stop = function () {
    clearInterval(TIMER);
    clearInterval(TIMERCLOCK);
    $('.pk').remove();
}

var $clock = $('.clock');

function clock(time) {
    var times = time / 1000;
    $clock.html(times)
    TIMERCLOCK = setInterval(function () {
        times--;
        resetTime(times);
        if (times <= 0) {
        	$(".alert_bg").show();
            pks.stop();
        }
    }, 1000);
}


//单纯分钟和秒倒计时
function resetTime(time){
  var timer=null;
  var t=time;
  var m=0;
  var s=0;
  m=Math.floor(t/60%60);
  m<10&&(m='0'+m);
  s=Math.floor(t%60);
  function countDown(){
   s--;
   s<10&&(s='0'+s);
   if(s.length>=3){
    s=59;
    m="0"+(Number(m)-1);
   }
   if(m.length>=3){
    m='00';
    s='00';
    clearInterval(timer);
   }
   $clock.html(m+":"+s);
  }
  timer=setInterval(countDown,1000);
}


//带天数的倒计时
function countDown(times){
  var timer=null;
  timer=setInterval(function(){
    var day=0,
      hour=0,
      minute=0,
      second=0;//时间默认值
    if(times > 0){
      day = Math.floor(times / (60 * 60 * 24));
      hour = Math.floor(times / (60 * 60)) - (day * 24);
      minute = Math.floor(times / 60) - (day * 24 * 60) - (hour * 60);
      second = Math.floor(times) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (minute * 60);
    }
    if (day <= 9) day = '0' + day;
    if (hour <= 9) hour = '0' + hour;
    if (minute <= 9) minute = '0' + minute;
    if (second <= 9) second = '0' + second;
    //
	/* console.log(minute+":"+second);
    console.log(day+"天:"+hour+"小时："+minute+"分钟："+second+"秒"); */
	
    times--;
  },1000);
  if(times<=0){
    clearInterval(timer);
  }
}

var pks = new PACKAGE();

// 游戏生成器
/*$(document).ready(function () {
    clock(30000);
    pks.start(30000, 50);//总时间，猪总数
})


document.addEventListener('DOMContentLoaded', function () {
    function audioAutoPlay() {
        var audio = document.getElementById('myaudios');
        audio.play();
        document.addEventListener("WeixinJSBridgeReady", function () {
            audio.play();
        }, false);
    }
    audioAutoPlay();
});*/

function startGame(){
	var audio = document.getElementById('myaudios');
	audio.play(); 
	document.addEventListener("WeixinJSBridgeReady", function () {
		audio.play();
	}, false);
	$(".tip_bg").hide();
	clock(30000);
	pks.start(30000, 50);
}
function restart(){
	var audio = document.getElementById('myaudios');
	audio.play();
	document.addEventListener("WeixinJSBridgeReady", function () {
		audio.play();
	}, false);
	$(".alert_bg").hide();
	clock(30000);
	pks.start(30000, 50);
}