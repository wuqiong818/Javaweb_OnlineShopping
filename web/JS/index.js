// 轮播图
/*总结：以结构高于一切，只有结构搭建好了，才会保证自己所写的代码不容易出错 
比如：在实现轮播图无缝切换那里，正常的思路是只需要复制一张图片，不管是左箭头
切换还是右箭头切换，都是用最后一张图片进行切换掩盖的，而我最开始的思路是左右
都复制一张图片，结果导致代码的异常复杂，虽然可以实现，现实思路太乱了
轮播图制作的主要思路：
1、搭建好H5,c3；注意轮播图所需要的图片，要全部放在一个大盒子里面
2、获取各个需要的节点，获取父节点之后，其子节点可以用fatherNode.children[i]直接进行操作
3、然后逐步添加各种事件，轮播图的主要部分有左右箭头、底部小圆圈、自动播放
代码书写的时候不分先后顺序，但我们一般先写小圆圈、左右箭头、自动播放（手动调用事件节课，、
语法：事件的元素.事件名）；
4.小圆圈我们一般采用动态的方法进行添加，通过创建元素，添加元素来完成，var=document.createElement(添加的元素'),
fatherNode.appendChild（所创建的元素名，不带引号）,在添加好小圆点后，我们使用排他思想，来实现点击那个小圆点，
那个小圆点显示特殊的样式，至于小圆点的移动，我们则需要使用自定义属性index来实现，setAttribute(定义的属性名，定义属性值)，
通过F12我们可以在网页窗口进行显示，我们定义的样式，属于内联样式；通过公式：小圆点的index*图片的大小，即可完成点击小圆点实现小圆点的移动。
5.左右按键切换轮播图的主要难点在于实现无缝切换，这个我一开始的思路走远了，基本上是错误的，
利用Element.cloneElement(ture)深拷贝元素，并添加元素。
JS算法的部分主要是先加加再移动，当移动到拷贝的图片上是，再++前条件语句，满足条件时我们直接让其left=XXpx；
实现瞬间的移动，
；
*/
window.addEventListener('load', function () {
    // 首页轮播图形制作
    var slideshow_container = document.querySelector('.slideshow_container');
    var lis = document.querySelector('.slideshow_container').querySelectorAll('li');
    var img = document.querySelector('.slideshow_container').querySelector('li').querySelector('img');
    var imgWidth = img.offsetWidth;//1226获取图片的宽度
    // 自动轮播图动画
    var timer = setInterval(function () {
        btR.click();
    }, 3000);

    // 利用小圆点实现轮播图的切换
    /*要求：1.根据图片的数量，自动插入小圆点
    【创建变量 在插入变量即可】
            2.当前图片的小圆点，显示特别的样式
            【重新声明一个当前类，点击哪一个小圆点，就让该圆点特别显示
            主要就是用排他思想来实现 消灭所有人 成全我自己
            利用className设置类名的时候 注意权重的问题】
            如何对父变量中的子节点进行操作，element.children[i]
            3.我们可以使用小圆点进行图片的切换
            【核心算法：索引号加上图片的宽度
            通过自定义属性，来设置索引好，在元素生成的时候就直接进行设置即可
            注意，setAttribute是设置，getAttribute是获取】
    默认第一个用特殊的样式显示，没实现 直接写在for循环创建小李的后面就可以了，具体的原因我也不清楚
    好像是写到for循环中会对循环造成干扰
            */
    var circle = document.querySelector('.circle')
    var index = 0;
    for (var i = 0; i < lis.length; i++) {
        // 向ol中添加li,利用for循环进行
        var li = document.createElement('li');
        circle.appendChild(li);
        circle.children[i].setAttribute('index', i);
        li.style.left = 1080 + i * 13 + 'px';//1080是小圆圈的初始位置

        li.addEventListener('click', function () {
            for (var i = 0; i < circle.children.length; i++) {
                circle.children[i].className = '';
            }
            this.className = 'current';
            index = this.getAttribute('index');
            //通过点击小圆圈就让轮播图动起来，就需要将之前的自动播放，给封装成函数；
            // animation(obj,target,callback);
            animation(slideshow_container, -index * imgWidth);
        });

    }
    // 使第一个小圆圈默认显示特殊的样式
    circle.children[0].className = 'current';
    // 轮播图按钮
    var btL = document.querySelector('.btL');
    var btR = document.querySelector('.btR');
    var flag = true;
    /* // 复制最后一个图片结点，将其插入到第一个位置；
    var last = slideshow_container.children[slideshow_container.children.length - 1].cloneNode(true);
    slideshow_container.insertBefore(last, slideshow_container.children[0]); */
    // 复制第一个图片结点，将其插入到第一个位置；
    var first = slideshow_container.children[0].cloneNode(true);
    slideshow_container.appendChild(first);
    btL.addEventListener('mouseenter', function () {
        btL.style.backgroundPosition = -1 + 'px' + ' ' + 0 + 'px';
        clearInterval(timer);
        timer = null;
    });
    btL.addEventListener('mouseleave', function () {
        btL.style.backgroundPosition = -84 + 'px' + ' ' + 0 + 'px';
        timer = setInterval(function () {
            btR.click();
        }, 3000);
    });
    btL.addEventListener('click', function () {
        if (flag) {
            flag = false;
            if (index == 0) {
                index = slideshow_container.children.length - 1;
                slideshow_container.style.left = -index * imgWidth + 'px';
            }
            index--;
            for (var i = 0; i < circle.children.length; i++) {
                circle.children[i].className = '';
            }
            if (index < 0) {
                circle.children[index - 1].className = 'current';
            }
            circle.children[index].className = 'current';
            animation(slideshow_container, -index * imgWidth, function () {
                flag = true;
            });
        }

    })

    btR.addEventListener('mouseenter', function () {
        btR.style.backgroundPosition = -42 + 'px' + ' ' + 0 + 'px';
        clearInterval(timer);
        timer = null;
    });
    btR.addEventListener('mouseleave', function () {
        btR.style.backgroundPosition = -123 + 'px' + ' ' + 0 + 'px';
        btL.style.backgroundPosition = -84 + 'px' + ' ' + 0 + 'px';
        timer = setInterval(function () {
            btR.click();
        }, 3000);
    });
    
    btR.addEventListener('click', function () {
        /* 无缝切换的关键，首先是深拷贝克隆元素，父节点.children[要克隆的第几个节点].cloneNode(true);
            将其添加至末尾，父节点.appendChild(名 无引号)；
            最后利用条件判断，另其直接变为0
        */
        if (flag) {
            flag = false;
            if (index == slideshow_container.children.length - 1) {
                index = 0;
                // 想当于将图片进行瞬移
                slideshow_container.style.left = -index * imgWidth + 'px';
            }
            index++;
            animation(slideshow_container, -index * imgWidth, function () {
                flag = true;
            })

            for (var i = 0; i < circle.children.length; i++) {
                circle.children[i].className = '';
            }
            if (index == slideshow_container.children.length - 1) {
                circle.children[0].className = 'current';
            }
            circle.children[index].className = 'current';
        }


    })
    // 返回顶部动画
    var menuItems = document.querySelector('.menu_items');
    var menuItemsTop=menuItems.offsetTop-200;
    var goBack=document.querySelector('.goBack');
    var exhibition=document.querySelector('.exhibition');
    var gobackTop=exhibition.offsetTop+exhibition.offsetHeight/2
    console.log(gobackTop);
    document.addEventListener('scroll', function () {
        if (window.pageYOffset > 200) {
            menuItems.style.position = 'fixed';
            menuItems.style.top = menuItemsTop+'px';
            
        }else{
            menuItems.style.position = 'absolute';
            menuItems.style.top = '200px';
        }

        if (window.pageYOffset >=gobackTop ) {
            goBack.style.display='block';
        }
        else{
            goBack.style.display='none';
        }
    })
    console.log(window.pageYOffset);
    goBack.addEventListener('click',function(){
        goBackanimation(window,0);

        
    });
    // 返回顶部的动画函数
    function goBackanimation(obj, target) {
        clearInterval(obj.timer);
        obj.timer = setInterval(function () {
            var step=(target-window.pageYOffset)/10;
            step = step > 0 ? Math.ceil(step) : Math.floor(step);
            if (window.pageYOffset == target) {
                // 只要没有达到这个条件，计时器就不会清除，步长就会一直累加
                clearInterval(obj.timer);
                //回调函数写到定时器结束后，一旦定时器结束，就回调函数
               callback&&callback();
            }
            window.scroll(0,window.pageYOffset+step);
        }, 5);
    }

//    这里进行tab切换的编写  tab_title
    var tab_switch1 =document.querySelector('#tab_switch1');
    var tab_switch2 =document.querySelector('#tab_switch2');
    var tab_container = document.querySelectorAll('.tab_container');
    var tab_container1 = tab_container[0];
    var tab_container2 = tab_container[1];
    tab_switch1.addEventListener('click',function (){
        tab_container1.style.display='block';
        tab_switch1.style.color='#bfb828';
        tab_switch2.style.color='#000000';
        tab_container2.style.display='none';

    });
    tab_switch2.addEventListener('click',function (){
        tab_container2.style.display='block';
        tab_switch2.style.color='#bfb828';
        tab_switch1.style.color='#000000';
        tab_container1.style.display='none';

    });
});


