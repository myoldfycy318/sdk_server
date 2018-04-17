$(function(){
    //加载应用数据
    $.post("/statistic/appList.html",{},function(data){
        console.log(data);
        if(data.code == 1000){
            var $container = $(".selectContainer");
            $container.find(".selectOption").empty();
            $container.find(".selectMenu").empty();
            var dataBase = data.data;
            for(var i = 0;i < dataBase.length;i++){
                var appName = dataBase[i].appName;
                var appNameCopy = appName;
                var appCode = dataBase[i].appCode;
                if(appName.length > 5 ){
                	appName = appName.substring(0,5);
                }
                if( i == 0 ){
                    $container.find(".selectOption").append('<div class="col-xs-6 text-left padding-left5">'+appCode+'</div><div class="col-xs-6 text-right no-padding-left">'+appName+'</div>');
                }
                $container.find(".selectMenu").append('<li><div class="col-xs-6 text-left padding-left5">'+appCode+'</div><div title="'+appNameCopy+'" class="col-xs-6 text-right no-padding-left">'+appName+'</div> </li>');
            }
            $container.find(".selectMenu").append('<li id="app-all"><div class="col-xs-5 text-left padding-left5"></div><div class="col-xs-7 text-left no-padding-left">全部</div> </li>');
            
            if(dataBase.length > 10){
            	$container.find(".selectMenu").css({
            		"max-height":"380px",
            		"overflow-x":"hidden",
            		"overflow-y":"scroll"
            	});
            }
        }else{
            console.error(data.message);
        }

        $(".selectContainer").select();

        initLoadPage();
    },'JSON');
});