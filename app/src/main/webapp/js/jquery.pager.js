/*
* jQuery pager plugin
* Version 1.0 (12/22/2008)
* @requires jQuery v1.2.6 or later
*
* Example at: http://jonpauldavies.github.com/JQuery/Pager/PagerDemo.html
*
* Copyright (c) 2008-2009 Jon Paul Davies
* Dual licensed under the MIT and GPL licenses:
* http://www.opensource.org/licenses/mit-license.php
* http://www.gnu.org/licenses/gpl.html
* 
* Read the related blog post and contact the author at http://www.j-dee.com/2008/12/22/jquery-pager-plugin/
*
* This version is far from perfect and doesn't manage it's own state, therefore contributions are more than welcome!
*
* Usage: .pager({ pagenumber: 1, pagecount: 15, buttonClickCallback: PagerClickTest });
*
* Where pagenumber is the visible page number
*       pagecount is the total number of pages to display
*       buttonClickCallback is the method to fire when a pager button is clicked.
*
* buttonClickCallback signiture is PagerClickTest = function(pageclickednumber) 
* Where pageclickednumber is the number of the page clicked in the control.
*
* The included Pager.CSS file is a dependancy but can obviously tweaked to your wishes
* Tested in IE6 IE7 Firefox & Safari. Any browser strangeness, please report.
*/
(function($) {

    $.fn.pager = function(options) {
		
        var opts = $.extend({}, $.fn.pager.defaults, options);
        return this.each(function() {
        	$(options.tips).text("");
			if(options.totalCount != null && options.limit != null){
				//重新计算pagecount
				options.totalCount = parseInt(options.totalCount);
				options.limit = parseInt(options.limit);
				if(options.totalCount <= options.limit){
					options.pagecount = 1;
				}else if(options.totalCount % options.limit == 0){
					options.pagecount = options.totalCount / options.limit;
				}else{
					options.pagecount = options.totalCount / options.limit + 1;
				}
				//当前显示1 到10 条记录,总共16条
				if(options.tips && options.totalCount > 0){
					var start = ((options.pagenumber - 1) * options.limit + 1);
					var end =  (options.pagenumber * options.limit)>options.totalCount?options.totalCount:(options.pagenumber * options.limit);
					$(options.tips).show();
					$(options.tips).text("当前显示" + start + "到" + end  + "条记录,总共" + options.totalCount +"条");
					
				}else{
					$(options.tips).hide();
				}
			}
        // empty out the destination element and then render out the pager with the supplied options
            $(this).empty().append(renderpager(parseInt(options.pagenumber), parseInt(options.pagecount), options.buttonClickCallback));
            
            // specify correct cursor activity
            $('.pages li').mouseover(function() { document.body.style.cursor = "pointer"; }).mouseout(function() { document.body.style.cursor = "auto"; });
        });
    };

    // render and return the pager with the supplied options
    function renderpager(pagenumber, pagecount, buttonClickCallback) {
    	if(pagecount <= 1){
    		return;
    	}
        // setup $pager to hold render
    	var $nav = $('<nav></nav>');
        var $pager = $('<ul class="pages pagination"></ul>');

        // add in the previous and next buttons
        $pager.append(renderButton('<img class="leftArrow" src="../img/pager-left.png"/>', pagenumber, pagecount, buttonClickCallback));

        // pager currently only handles 10 viewable pages ( could be easily parameterized, maybe in next version ) so handle edge cases
        var startPoint = 1;
        var endPoint = 9;

        if (pagenumber > 4) {
            startPoint = pagenumber - 4;
            endPoint = pagenumber + 4;
        }

        if (endPoint > pagecount) {
            startPoint = pagecount - 8;
            endPoint = pagecount;
        }

        if (startPoint < 1) {
            startPoint = 1;
        }

        // loop thru visible pages and render buttons
        for (var page = startPoint; page <= endPoint; page++) {

            var currentButton = $('<li class="page-number"><a>' + (page) + '</a></li>');

            page == pagenumber ? currentButton.addClass('active') : currentButton.find("a").click(function() { buttonClickCallback(this.firstChild.data); });
            currentButton.appendTo($pager);
        }

        // render in the next and last buttons before returning the whole rendered control back.
        $pager.append(renderButton('<img class="rightArrow" src="../img/pager-right.png"/>', pagenumber, pagecount, buttonClickCallback));
        $pager.appendTo($nav);
        return $nav;
    }

    // renders and returns a 'specialized' button, ie 'next', 'previous' etc. rather than a page number button
    function renderButton(buttonLabel, pagenumber, pagecount, buttonClickCallback) {

        var $Button = $('<li class="pgNext"><a>' + buttonLabel + '</a></li>');

        var destPage = 1;

        // work out destination page for required button type
        switch (buttonLabel) {
            case "first":
                destPage = 1;
                break;
            case "上一页":
                destPage = pagenumber - 1;
                break;
            case "下一页":
                destPage = pagenumber + 1;
                break;
            case "last":
                destPage = pagecount;
                break;
        }
		if(buttonLabel.indexOf("leftArrow") > -1){
			destPage = pagenumber - 1;
		}else if(buttonLabel.indexOf("rightArrow") > -1){
			destPage = pagenumber + 1;
		}
		if(destPage < 0){
			destPage = 0;
		}else if(destPage >= pagecount){
			destPage = pagecount;
		}

        // disable and 'grey' out buttons if not needed.
        if (buttonLabel.indexOf("leftArrow") > -1) {
            pagenumber <= 1 ? $Button.addClass('pgEmpty') : $Button.find("a").click(function() { buttonClickCallback(destPage); });
        }
        else {
            pagenumber >= pagecount ? $Button.addClass('pgEmpty') : $Button.find("a").click(function() { buttonClickCallback(destPage); });
        }

        return $Button;
    }

    // pager defaults. hardly worth bothering with in this case but used as placeholder for expansion in the next version
    $.fn.pager.defaults = {
        pagenumber: 1,
        pagecount: 1
    };

})(jQuery);





