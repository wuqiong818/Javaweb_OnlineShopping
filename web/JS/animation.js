// 封装轮播图函数
function animation(obj, target, callback) {
    //先清除以前的定时器，永远都有一个定时器正在启动
    clearInterval(obj.timer);
    obj.timer = setInterval(function () {
        //动画的核心，移动的公式【obj.style.left = slideshow_container.offsetLeft - imgWidth + 'px';】
        //此函数设置为步长，步长要为整数，不要出现小数的情况；
        // var step=(target-obj.offsetLeft)/10;
        var step=(target-obj.offsetLeft)/10;
        step = step > 0 ? Math.ceil(step) : Math.floor(step);
        if (obj.offsetLeft == target) {
            // 只要没有达到这个条件，计时器就不会清除，步长就会一直累加
            clearInterval(obj.timer);
            //回调函数写到定时器结束后，一旦定时器结束，就回调函数
            if (callback) {
                callback();
            }
        }
        obj.style.left=obj.offsetLeft+step+'px';
    }, 15);
} 