$(function () {
    var heights = $('.bg_1').height();
    $('.content').height(heights);

    $(document).ready(function () {
        //循环执行，每隔1秒钟执行一次 1000 
        var t1 = window.setInterval(refreshCount, 2000);

        function refreshCount() {

        }
        //去掉定时器的方法  
        window.clearInterval(t1);
    })
})