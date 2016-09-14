/* Start change the area width*/
var content_left_width = $('#content_left').outerWidth();
var content_middle_width = $('#content_middle').outerWidth();

$('#container').width(document.body.clientWidth+"px");
$('#content_right').width((document.body.clientWidth-content_left_width-content_middle_width)+"px");
 /* End change the area width*/


// alert("浏览器当前窗口可视区域高度 ="+ $(window).height()); //浏览器当前窗口可视区域高度
// alert("浏览器当前窗口文档的高度"+$(document).height()); //浏览器当前窗口文档的高度
// alert("浏览器当前窗口文档body的高度"+$(document.body).height());//浏览器当前窗口文档body的高度
// alert("浏览器当前窗口文档body的总高度"+$(document.body).outerHeight(true));//浏览器当前窗口文档body的总高度 包括border padding margin
// alert("浏览器当前窗口可视区域宽度"+$(window).width()); //浏览器当前窗口可视区域宽度
// alert("浏览器当前窗口文档对象宽度"+$(document).width());//浏览器当前窗口文档对象宽度
// alert("浏览器当前窗口文档body的高度"+$(document.body).width());//浏览器当前窗口文档body的高度
// alert("浏览器当前窗口文档body的总宽度"+$(document.body).outerWidth(true));//浏览器当前窗口文档body的总宽度 包括border padding margin

// 获取页面的高度、宽度
function getPageSize() {
    var xScroll, yScroll;
    if (window.innerHeight && window.scrollMaxY) {
        xScroll = window.innerWidth + window.scrollMaxX;
        yScroll = window.innerHeight + window.scrollMaxY;
    } else {
        if (document.body.scrollHeight > document.body.offsetHeight) { // all but Explorer Mac
            xScroll = document.body.scrollWidth;
            yScroll = document.body.scrollHeight;
        } else { // Explorer Mac...would also work in Explorer 6 Strict, Mozilla and Safari
            xScroll = document.body.offsetWidth;
            yScroll = document.body.offsetHeight;
        }
    }
    var windowWidth, windowHeight;
    if (self.innerHeight) { // all except Explorer
        if (document.documentElement.clientWidth) {
            windowWidth = document.documentElement.clientWidth;
        } else {
            windowWidth = self.innerWidth;
        }
        windowHeight = self.innerHeight;
    } else {
        if (document.documentElement && document.documentElement.clientHeight) { // Explorer 6 Strict Mode
            windowWidth = document.documentElement.clientWidth;
            windowHeight = document.documentElement.clientHeight;
        } else {
            if (document.body) { // other Explorers
                windowWidth = document.body.clientWidth;
                windowHeight = document.body.clientHeight;
            }
        }
    }
    // for small pages with total height less then height of the viewport
    if (yScroll < windowHeight) {
        pageHeight = windowHeight;
    } else {
        pageHeight = yScroll;
    }
    // for small pages with total width less then width of the viewport
    if (xScroll < windowWidth) {
        pageWidth = xScroll;
    } else {
        pageWidth = windowWidth;
    }
    arrayPageSize = new Array(pageWidth, pageHeight, windowWidth, windowHeight);
    return arrayPageSize;
}



$(document).ready(function(){
    var submitIcon = $('.searchbox-icon');
    var inputBox = $('.searchbox-input');
    var searchBox = $('.searchbox');
    var isOpen = false;
    submitIcon.click(function(){
        if(isOpen == false){
            searchBox.addClass('searchbox-open');
            inputBox.focus();
            isOpen = true;
        } else {
            searchBox.removeClass('searchbox-open');
            inputBox.focusout();
            isOpen = false;
        }
    });
    submitIcon.mouseup(function(){
        return false;
    });
    searchBox.mouseup(function(){
        return false;
    });
    $(document).mouseup(function(){
        if(isOpen == true){
            $('.searchbox-icon').css('display','block');
            submitIcon.click();
        }
    });

    /**
     * 二三级导航点击收缩效果
     */
    $('.submenu > a').click(function(e)
    {
        e.preventDefault();
        var submenu = $(this).siblings('ul');
        var li = $(this).parents('li');
        var submenus = $('#sidebar li.submenu ul');
        var submenus_parents = $('#sidebar li.submenu');
        if(li.hasClass('open'))
        {
            if(($(window).width() > 768) || ($(window).width() < 479)) {
                submenu.slideUp();
            } else {
                submenu.fadeOut(250);
            }
            li.removeClass('open');
        } else
        {
            if(($(window).width() > 768) || ($(window).width() < 479)) {
                submenus.slideUp();
                submenu.slideDown();
            } else {
                submenus.fadeOut(250);
                submenu.fadeIn(250);
            }
            submenus_parents.removeClass('open');
            li.addClass('open');
        }
    });

    var ul = $('#sidebar > ul');

    $('#sidebar > a').click(function(e)
    {
        e.preventDefault();
        var sidebar = $('#sidebar');
        if(sidebar.hasClass('open'))
        {
            sidebar.removeClass('open');
            ul.slideUp(250);
        } else
        {
            sidebar.addClass('open');
            ul.slideDown(250);
        }
    });
    var sidebar = $('#sidebar');
    if(sidebar.hasClass('open'))
    {
        sidebar.removeClass('open');
        ul.slideUp(250);
    }


});


/**
 * 设置当前目录导航，现在分为三级
 * @param firstSliderId 一级菜单id
 * @param twoSliderId 二级菜单id
 * @param thridSiderId 三级菜单id
 */
function setCurrentPageNavigation(firstSliderId, twoSliderId,thridSiderId) {
    $("#content_left > ul > li").each(function(){
        $(this).removeClass("active");
    });
    $("#" + firstSliderId).addClass("active");
    $("#sidebar ul > li").each(function() {
        $(this).removeClass("active");
    });
    var li="<i class='icon-caret-right icon-has-li'></i>";
    $("#" + twoSliderId).addClass("active");

    if (thridSiderId != null && thridSiderId != '' && thridSiderId != undefined) {
        $("#sidebar ul > li > ul > li").each(function() {
            $(this).removeClass("active");
        });
        $("#" + thridSiderId).addClass("active");
        $("#" + thridSiderId +">a").append(li);
        if( $("#sidebar").hasClass('open')){
            $(this).removeClass("open");
        };
        $("#" + thridSiderId).parents("li").addClass("open");
    }
}

function buttonUp(){
    var inputVal = $('.searchbox-input').val();
    inputVal = $.trim(inputVal).length;
    if( inputVal !== 0){
        $('.searchbox-icon').css('display','none');
    } else {
        $('.searchbox-input').val('');
        $('.searchbox-icon').css('display','block');
    }
}