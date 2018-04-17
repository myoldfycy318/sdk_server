var confirmDiv = "<div id='confirm' style='display:none'><div class='dialog-header'><span>确 认</span><a href='javascript:;' class='dialog-close'></a></div><div class='dialog-message'></div><div class='dialog-buttons'><button class='btn-small-i yes'>确 认</button><button class='btn-small-i no'>取 消</button></div></div>";
var alertDiv = "<div id='alert' style='display:none'><div class='dialog-header'><span>提 醒</span><a href='javascript:;' class='dialog-close'></a></div><div class='dialog-message'></div><div class='dialog-buttons'><button class='btn-small-i'>确 定</button></div></div>";
var dialogDiv = "<div id='dialog' style='display:none'><div class='dialog-header'><span class='title'>这是个个性化对话框</span><a href='javascript:;' class='dialog-close'></a></div><div class='dialog-message'></div><div class='dialog-buttons'><button class='btn-small-i yes'>确 定</button></div></div>";
$(function(){
	$(document.body).append(confirmDiv);
	$(document.body).append(alertDiv);
	$(document.body).append(dialogDiv);
});
function bindState(items){
	items.bind("mouseover",function(){
		$(this).addClass("hover");
	}).bind("mouseout",function(){
		$(this).removeClass("hover");
	}).bind("click",function(){
		items.removeClass("on");
		$(this).addClass("on");
	});
}
function reflesh(){
	window.location.reload(); 
}

function serializeMap(form){
	var arr = $(form).serializeArray();
	var data = {};
	for(var i = 0;i<arr.length;i++){
		data[arr[i].name] = arr[i].value;
	}
	return data;
}
function json2str (obj){   
    var THIS = this;    
    switch(typeof(obj)){   
        case 'string':   
            return '"' + obj.replace(/(["\\])/g, '\\$1') + '"';   
        case 'array':   
            return '[' + obj.map(THIS.json2str).join(',') + ']';   
        case 'object':   
             if(obj instanceof Array){   
                var strArr = [];   
                var len = obj.length;   
                for(var i=0; i<len; i++){   
                    strArr.push(THIS.json2str(obj[i]));   
                }
                return '[' + strArr.join(',') + ']';
            }else if(obj==null){
                return 'null';
            }else{  
            	var string = [];
            	for (var property in obj)
            		string.push(THIS.json2str(property) + ':' + THIS.json2str(obj[property]));
            	return '{' + string.join(',') + '}';   
            }   
        case 'number':   
            return obj;   
        case false:   
            return obj;   
    }   
}

//统一过滤AJAX请求，若被截获，则弹出提示
function filterRequest(data){
	try{
		data = $.parseJSON(data);
	}catch(e){}
	if(data && data.result == -9999){
		$.dialog.tips(data.msg.length > 50?data.msg.substr(0,50):data.msg,2);
		return null;
	}
	return data;
}