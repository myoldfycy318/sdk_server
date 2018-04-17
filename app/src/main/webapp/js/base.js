/*!
 * 基础js
 * author:ryan
 */

//解决bootstrap模态框不垂直居中的问题
$(function(){
    if(is.mac()){
        $("body").addClass("os_mac");
    }

    $('.modal').on('show.bs.modal', function(e) {
        $.winowsChange.init($(this));
    });

    //当浏览器窗口发生变化时
    $(window).on('resize', $.winowsChange.init($('.modal')));

    //出现滚动条处理footer
    $.isScroll.resize();

    //兼容ie8的placeholder
    $("input").each(function(){
        var ph = $(this).attr("palceholder");
        if(ph != ""){
            $(this).placeholder($(this).attr("palceholder"));
        }
    })
});

function showStatusN(n){
	$(".navbar-main").children().eq(n).addClass("active");
}

function fixModalClose(modalDialog){
	$(modalDialog).find(".close").unbind().on("click",function(){
		$(modalDialog).modal("hide");
	});
	$(".modal").on("click",function(){
		$(modalDialog).modal("hide");
	});
	$(modalDialog).find(".modal-dialog").unbind().on("click",function(event){
		event.stopPropagation();
	});
}

function copyToClipboard(txt) {
    if (window.clipboardData) {
      window.clipboardData.clearData();
      clipboardData.setData("Text", txt);
      alert("复制成功！");

    } else if (navigator.userAgent.indexOf("Opera") != -1) {
      window.location = txt;
    } else if (window.netscape) {
      try {
        netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
      } catch (e) {
        alert("被浏览器拒绝！\n请在浏览器地址栏输入'about:config'并回车\n然后将 'signed.applets.codebase_principal_support'设置为'true'");
      }
      var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);
      if (!clip)
        return;
      var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);
      if (!trans)
        return;
      trans.addDataFlavor("text/unicode");
      var str = new Object();
      var len = new Object();
      var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);
      var copytext = txt;
      str.data = copytext;
      trans.setTransferData("text/unicode", str, copytext.length * 2);
      var clipid = Components.interfaces.nsIClipboard;
      if (!clip)
        return false;
      clip.setData(trans, null, clipid.kGlobalClipboard);
      alert("复制成功！");
    }
  }

/*!
 * IE10 viewport hack for Surface/desktop Windows 8 bug
 */
(function () {
    'use strict';
    if (navigator.userAgent.match(/IEMobile\/10\.0/)) {
        var msViewportStyle = document.createElement('style')
        msViewportStyle.appendChild(
            document.createTextNode(
                '@-ms-viewport{width:auto!important}'
            )
        )
        document.querySelector('head').appendChild(msViewportStyle)
    }

})();


function checkInput(input){
	input = $(input);
	var msg = input.parents(".form-group").find(".msg");
	if(input.attr("required") && is.empty(input.val())){
		msg.show().addClass("error-msg");
		return false;
	}
	var regex = input.attr("regex");
	if(regex && !(new RegExp(regex)).test(input.val())){
		msg.show().addClass("error-msg");
		return false;
	}
	if(input.attr("isUrl")){
		var urls = input.val().split(';');
		for(var i = 0;i<urls.length;i++){
			if(!is.empty(urls[i]) && !is.url(urls[i])){
				msg.show().addClass("error-msg");
				return false;
			}
		}
	}
	if(input.attr("maxlength") && input.val().length > parseInt(input.attr("maxlength"))){
		msg.show().addClass("error-msg");
		return false;
	}
	msg.removeClass("error-msg");
	return true;
}
function bindUpload(appIcon){
	var uploadBtn = $('#uploadBtn');
	var innerHtml = '<div class="img-cover"><div class="img-cover-bg"></div><div class="img-cover-text">重新上传</div></div><img src="../img/upload-init.png" /><input type="hidden" class="img-url" name="img-url" value="" />';
	if(uploadBtn.is(".webuploader-container")){
		uploadBtn.removeClass("webuploader-container");
		uploadBtn.html(innerHtml);
		if(appIcon){
			uploadBtn.find("input[name='img-url']").val(appIcon);
			uploadBtn.find("img").attr("src",appIcon);
		}
	}

	var uploader = WebUploader.create({
		// swf文件路径
		swf: "../js/webuploader-dist-0.1.5/Uploader.swf",
		// 文件接收服务端。
		server: '/merchantapp/uploadfile.html',
		pick: $('#uploadBtn'),
		// 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
		resize: false,
		auto: true,
		fileVal:"pic",
		accept: {
			extensions: 'jpg,jpeg,bmp,png',
			mimeTypes: 'image/*',
			title: 'Images'
		} 
	});
	//修正uploader的内部BUG
	function makeTop(){
		var len = $('#uploadBtn').children().length;
		console.log("make top:" + len);
		if(len >= 2){
			$('#uploadBtn').children(":last").css("z-index","20000");
		}else{
			setTimeout(function(){
				makeTop();
			},100);
		}
		
	}
	makeTop();
	
	uploader.on('startUpload', function(file,response) {
		uploadBtn.attr("state","uploading");
	});
	uploader.on('uploadSuccess', function(file,response) {
		uploader.removeFile(file);
		if(response.success){
        	$.mDialog.tips("图片上传成功!");
        	uploadBtn.find("img").attr("src", response.imgUrl);
        	uploadBtn.find(".imgUrl").val(response.imgUrl);
		uploadBtn.attr("state","idle");
        }else{
            $.mDialog.tips("上传图片异常:" + response.msg);
        }
		
	});
	uploader.on('uploadError', function(file) {
		uploader.removeFile(file);
		$.mDialog.tips("没有上传成功，请重试");
		uploadBtn.attr("state","idle");
	});
	var imgUrl = uploadBtn.find("img").attr("src");
	if(imgUrl.indexOf("upload-init.png") > -1){
		uploadBtn.find(".img-cover").hide();
	}else{
		uploadBtn.find(".img-cover").show();
	}
	uploadBtn.on("mouseover",function(){
		var imgUrl = uploadBtn.find("img").attr("src");
		if(imgUrl.indexOf("upload-init.png") < 0){
			uploadBtn.find(".img-cover").show();
		}
	}).on("mouseout",function(){
		uploadBtn.find(".img-cover").hide();
	});
	uploadBtn.find(".img-cover").hide()
}
/**
 * $.mDialog author:hhk version: 0.1
 *
 * @requires jQuery,bootstrap
 */
(function($) {
    var _allDialog = {};
    if(is.desktop()){
        _allDialog = {
            defaultDialog:'<div class="mwmDialog modal fade"><div class="modal-dialog" style="display:block;max-width:350px;">'
            +	'<div class="modal-content">'
            +		'<div class="modal-body" style="line-height:150%;font-size:18px;text-align:center;word-wrap: break-word;">'
            +			'<p></p>'
            +		'</div>'
            +		'<div class="modal-footer" style="text-align:center;border:0px;">'
            +			'<button type="button" class="btn btn-primary yes-btn btn-lg" style="width:48%">{YES_BTN_TITLE}</button>'
            +			'<button type="button" class="btn btn-default no-btn btn-lg" style="width:48%" data-dismiss="modal">{NO_BTN_TITLE}</button>'
            +		'</div>'
            +	'</div>'
            +'</div></div>'

        };
    }else{
        _allDialog = {
            defaultDialog:'<div class="mwmDialog modal fade"><div class="modal-dialog" style="display:block;">'
            +	'<div class="modal-content">'
            +		'<div class="modal-body" style="line-height:150%;font-size:18px;text-align:center;word-wrap: break-word;">'
            +			'<p></p>'
            +		'</div>'
            +		'<div class="modal-footer" style="text-align:center;border:0px;">'
            +			'<button type="button" class="btn btn-default no-btn btn-lg btn-block" style="data-dismiss="modal">{NO_BTN_TITLE}</button>'
            +			'<button type="button" class="btn btn-primary yes-btn btn-lg btn-block" style="">{YES_BTN_TITLE}</button>'
            +		'</div>'
            +	'</div>'
            +'</div></div>'
        };
    }

    var _target = null;
    var _yesBtnTitle = "确定";
    var _noBtnTitle = "取消";
    var _tipsSleep = 2500;
    var _command = null;
    $.mDialog = {
        init:function(){
            _target = $(".mwmDialog");
            if(_target.length == 0){
                $(_allDialog.defaultDialog).appendTo("body");
                _target = $(".mwmDialog");
            }
            if(!$.browser.versions.mobile){
                _target.find(".modal-dialog").css("width","350px");
                _target.find(".modal-footer button").removeClass("btn-lg").addClass("btn-md");
            }

        },
        centerModal:function() {
            _target.css('display', 'block');
            var $dialog = _target.find(".modal-dialog");
            var offset = ($(window).height() - $dialog.height()) / 2;
            // Center modal vertically in window
            $dialog.css("margin-top", offset).css("margin-left", "auto").css("margin-right", "auto");
        },
        reset:function(){
            this.init();
            _target.modal('hide');
            _target.find("button").show().unbind('click');
            _target.find(".modal-body").html("");
            _target.find(".modal-footer").show();
        },
        touchBody:function(msg){
            _target.find(".modal-body").html($("<p>" + msg + "</p>"));
        },
        alert:function(msg, fn,yesBtnTitle){
            _command = 'alert';
            this.reset();
            this.touchBody(msg);
            _target.find(".yes-btn").text(yesBtnTitle || _yesBtnTitle);
            _target.find(".yes-btn").bind("click",function(){
                if($.isFunction(fn)){
                    fn();
                }
                $('.mwmDialog').modal('hide');
            });
            _target.find(".no-btn").hide();
            _target.modal('show');

            this.centerModal();
        },
        confirm:function(msg,yesfn,nofn,yesBtnTitle,noBtnTitle){
            _command = 'confrim';
            this.reset();

            this.touchBody(msg);
            _target.find(".yes-btn").text(yesBtnTitle || _yesBtnTitle);
            _target.find(".no-btn").text(noBtnTitle || _noBtnTitle);
            _target.find(".yes-btn").bind("click",function(){
            	var $tar = $('.mwmDialog');
                if($.isFunction(yesfn)){
                    yesfn.apply($tar);
                }
                $tar.modal('hide');
            });
            _target.find(".no-btn").bind("click",function(){
                if($.isFunction(nofn)){
                    nofn();
                }
                _target.modal('hide');
            });

            _target.modal('show');
            this.centerModal();
        },
        tips:function(msg,tipType,fn){
            _command = 'tips';
            this.reset();
            _target = $(".mwmDialog")
            _target.find(".modal-footer").hide();

            this.touchBody(msg);
            _target.find(".modal-body").css('text-align','center');
            _target.find(".modal-body").children().css("margin","0px");
            _target.modal('show');
            this.centerModal();
            setTimeout(function(){
                if(_command == 'tips'){
                    _target.modal('hide');
                    if($.isFunction(fn)){
                        fn();
                    }
                }
            },_tipsSleep);
        }
    };

    $.browser = {
        versions:function(){
            var u = navigator.userAgent, app = navigator.appVersion;
            var r = {
                // 移动终端浏览器版本信息
                trident: u.indexOf('Trident') > -1, // IE内核
                presto: u.indexOf('Presto') > -1, // opera内核
                webKit: u.indexOf('AppleWebKit') > -1, // 苹果、谷歌内核
                gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, // 火狐内核
                ios: !!u.match(/ios/), // ios终端
                android: u.indexOf('Android') > -1, // android终端或者uc浏览器
                iPhone: u.indexOf('iPhone') > -1, // 是否为iPhone或者QQHD浏览器
                iPod: u.indexOf('iPod') > -1, // 是否为iPhone或者QQHD浏览器
                iPad: u.indexOf('iPad') > -1, // 是否iPad
                webApp: u.indexOf('Safari') == -1, // 是否web应该程序，没有头部与底部
                wx:u.indexOf('MicroMessenger') > -1
            };
            r.mobile = r.android || r.iPhone || r.iPad || r.wx || r.iPod;
            return r;
        }(),
        language:(navigator.browserLanguage || navigator.language).toLowerCase(),

        isWx:function(){
            var ua = navigator.userAgent.toLowerCase();
            if(ua.match(/MicroMessenger/i)=="micromessenger") {
                return true;
            } else {
                return false;
            }
        }()
    };

    /*是否出现滚动条*/
    $.isScroll = {
        scrollY:function(){
            if (document.documentElement.scrollHeight > document.documentElement.clientHeight){
                return true;
            }else{
                return false;
            }
        },
        resize:function(){
            if(this.scrollY()){
                $(".footer").removeClass("navbar-fixed-bottom");
            }
        }
    };

    /*窗口发生改变的时候*/
    $.winowsChange = {
        init:function(element){
            this.centerModals(element);
            $.isScroll.resize();
        },
        centerModals:function ($element) {
            if($element == undefined){
                return;
            }
            var $modals;
            if ($element.length) {
                $modals = $element;
            } else {
                $modals = $('.modal:visible');
            }
            $modals.each( function(i) {
                var $clone = $(this).clone().css('display', 'block').appendTo('body');
                var top = Math.round(($clone.height() - $clone.find('.modal-content').height()) / 2);
                top = top > 0 ? top : 0;
                $clone.remove();
                $(this).find('.modal-content').css("margin-top", top);
            });
        }
    }
}($));

/*新增的内容*/

+function ($) {
    $.mfn = {
        init:function(){
            doSomething();
        },
        doSomething:function(){

        }
    },
    $.fn.select = function(options){
        return this.each(function(){
            var $this = $(this);
            var $shows = $this.find(".shows");
            var $selectOption = $this.find(".selectOption");
            var $el = $this.find("ul > li");

            $this.click(function(e){
                $(this).toggleClass("zIndex");
                $(this).children("ul").toggleClass("dis");
                e.stopPropagation();
            });

            $el.bind("click",function(){
                var $this_ = $(this);
                $this_.parent().parent().find(".selectOption").html($this_.html());
            });

            $("body").bind("click",function(){
                $this.removeClass("zIndex");
                $this.find("ul").removeClass("dis");
            })
        });
    }
}(jQuery);

var baseUrl = "/statistic/";

$(function(){
    $(".count .panel-heading").each(function(){
        $(this).click(function(){
            $(".count .panel-heading").removeClass("open");
            $(".count .panel-heading").removeClass("active");
            $(this).addClass("active");
        });
        $(this).hover(function(){
            $(".count .panel-heading").removeClass("hover");
            $(this).addClass("hover");
        },function(){
            $(this).removeClass("hover");
        });
    });

    $(".list-group .list-group-item").each(function(){
        $(this).click(function(){
            $(".count .panel-heading.active").addClass("open");
            $(".list-group .list-group-item").removeClass("active");
            $(this).addClass("active");
        });
        $(this).hover(function(){
            $(".list-group .list-group-item").removeClass("hover");
            $(this).addClass("hover");
        },function(){
            $(this).removeClass("hover");
        });
    });


    $(".collapse").each(function(index){

        $(this).on('show.bs.collapse', function () {
            var _obj  = $(this).parent().find(".panel-heading:eq("+index+")").find(".arrow-bg");
            _obj.removeClass("rorate0");
            _obj.addClass("rorate90");
        });

        $(this).on('hide.bs.collapse', function () {
            var _obj  = $(this).parent().find(".panel-heading:eq("+index+")").find(".arrow-bg");
            _obj.removeClass("rorate90");
            _obj.addClass("rorate0");
        });
    });

})

//时间选择器时间格式化
function getFormatDate(DateformatStr,n){
    var date = new Date();
    date.setTime(new Date().getTime()-1000*60*60*24*n);
    return date.Format(DateformatStr);
}

function getEndMonth(DateformatStr){
	var date = new Date();
	var month  = date.getMonth();  
	var day = date.getDate();
	if(day != 1){
		date.setMonth(month+1);
	}
	return date.Format(DateformatStr);
}

function getRealEndMonth(DateformatStr){
	var date = new Date();
	var month  = date.getMonth();  
	var day = date.getDate();
	console.log(day);
	if(day == 1){
		date.setMonth(month-2); 
	}else{
		date.setMonth(month-1); 
	}
	console.log(date.Format(DateformatStr));
	return date.Format(DateformatStr);     
}

function getRealEndDay(DateformatStr){
	var date = new Date();
	var month  = date.getMonth();  
    date.setTime(date.getTime()-1000*60*60*24*2);
    console.log(date.Format(DateformatStr));
    return date.Format(DateformatStr);
}

Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

