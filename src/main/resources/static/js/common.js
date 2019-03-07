/*window.alert = function(name){
    var iframe = document.createElement("IFRAME");
    iframe.style.display="none";
    iframe.setAttribute("src", 'data:text/plain,');
    document.documentElement.appendChild(iframe);
    window.frames[0].window.alert(name);
    iframe.parentNode.removeChild(iframe);
};*/

window.alert = function(name){
    //创建一个大盒子
    var box = document.createElement("div");
    var back = document.createElement("div");
    //创建一个关闭按钮
    back.id = "backg";
    document.body.appendChild(back);
    var button = document.createElement("div");
    //定义一个对象保存样式
    box.id="alertbox";
    //把创建的元素添加到body中
    document.body.appendChild(box);
    //把alert传入的内容添加到box中
    if(arguments[0]){
        box.innerHTML = arguments[0];
    }
    button.innerHTML = "确定";
    //定义按钮样式
    button.id="alertbutton";
    //把按钮添加到box中
    box.appendChild(button);
    //给按钮添加单击事件
    button.addEventListener("click",function(){
        document.body.removeChild(box);
        document.body.removeChild(back);//每次点击需要移除子元素，不然呵呵哒
    })
};

window.confirm = function (message) {
    var iframe = document.createElement("IFRAME");
    iframe.style.display = "none";
    iframe.setAttribute("src", 'data:text/plain,');
    document.documentElement.appendChild(iframe);
    var alertFrame = window.frames[0];
    var result = alertFrame.window.confirm(message);
    iframe.parentNode.removeChild(iframe);
    return result;
};

Date.prototype.Format = function (fmt) { // author: meizz
    var o = {
        "M+": this.getMonth() + 1, // 月份
        "d+": this.getDate(), // 日
        "H+": this.getHours(), // 小时
        "m+": this.getMinutes(), // 分
        "s+": this.getSeconds(), // 秒
        "q+": Math.floor((this.getMonth() + 3) / 3), // 季度
        "S": this.getMilliseconds() // 毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            return fmt;
}

$.ajaxSetup({
	cache : false,
	error:function(data){
		alert(data.responseText)
		window.location.href="http://fbclient/tool";
	}
});