!function(e,t){"object"==typeof exports&&"undefined"!=typeof module?module.exports=t():"function"==typeof define&&define.amd?define(t):e.Sweetalert2=t()}(this,function(){"use strict";var e="swal2-",t=function(t){var n={};for(var o in t)n[t[o]]=e+t[o];return n},n=t(["container","in","iosfix","modal","overlay","fade","show","hide","noanimation","close","title","content","spacer","confirm","cancel","icon","image","input","file","range","select","radio","checkbox","textarea","inputerror","validationerror","progresssteps","activeprogressstep","progresscircle","progressline","loading","styled"]),o=t(["success","warning","info","question","error"]),r={title:"",text:"",html:"",type:null,customClass:"",animation:!0,allowOutsideClick:!0,allowEscapeKey:!0,showConfirmButton:!0,showCancelButton:!1,preConfirm:null,confirmButtonText:"确定",confirmButtonColor:"#3085d6",confirmButtonClass:null,cancelButtonText:"Cancel",cancelButtonColor:"#aaa",cancelButtonClass:null,buttonsStyling:!0,reverseButtons:!1,focusCancel:!1,showCloseButton:!1,showLoaderOnConfirm:!1,imageUrl:null,imageWidth:null,imageHeight:null,imageClass:null,timer:null,width:500,padding:20,background:"#fff",input:null,inputPlaceholder:"",inputValue:"",inputOptions:{},inputAutoTrim:!0,inputClass:null,inputAttributes:{},inputValidator:null,progressSteps:[],currentProgressStep:null,progressStepsDistance:"40px",onOpen:null,onClose:null},i=('\n  <div class="'+n.modal+'" style="display: none" tabIndex="-1">\n    <ul class="'+n.progresssteps+'"></ul>\n    <div class="'+n.icon+" "+o.error+'">\n      <span class="x-mark"><span class="line left"></span><span class="line right"></span></span>\n    </div>\n    <div class="'+n.icon+" "+o.question+'">?</div>\n    <div class="'+n.icon+" "+o.warning+'">!</div>\n    <div class="'+n.icon+" "+o.info+'">i</div>\n    <div class="'+n.icon+" "+o.success+'">\n      <span class="line tip"></span> <span class="line long"></span>\n      <div class="placeholder"></div> <div class="fix"></div>\n    </div>\n    <img class="'+n.image+'">\n    <h2 class="'+n.title+'"></h2>\n    <div class="'+n.content+'"></div>\n    <input class="'+n.input+'">\n    <input type="file" class="'+n.file+'">\n    <div class="'+n.range+'">\n      <output></output>\n      <input type="range">\n    </div>\n    <select class="'+n.select+'"></select>\n    <div class="'+n.radio+'"></div>\n    <label for="'+n.checkbox+'" class="'+n.checkbox+'">\n      <input type="checkbox">\n    </label>\n    <textarea class="'+n.textarea+'"></textarea>\n    <div class="'+n.validationerror+'"></div>\n    <hr class="'+n.spacer+'">\n    <button type="button" class="'+n.confirm+'">OK</button>\n    <button type="button" class="'+n.cancel+'">Cancel</button>\n    <span class="'+n.close+'">&times;</span>\n  </div>\n').replace(/(^|\n)\s*/g,""),a=void 0,l=document.getElementsByClassName(n.container);l.length?a=l[0]:(a=document.createElement("div"),a.className=n.container,a.innerHTML=i);var s=function(e,t){e=String(e).replace(/[^0-9a-f]/gi,""),e.length<6&&(e=e[0]+e[0]+e[1]+e[1]+e[2]+e[2]),t=t||0;for(var n="#",o=0;o<3;o++){var r=parseInt(e.substr(2*o,2),16);r=Math.round(Math.min(Math.max(0,r+r*t),255)).toString(16),n+=("00"+r).substr(r.length)}return n},c={previousWindowKeyDown:null,previousActiveElement:null,previousBodyPadding:null},u=function(){if("undefined"==typeof document)return void console.error("SweetAlert2 requires document to initialize");if(!document.getElementsByClassName(n.container).length){document.body.appendChild(a);var e=p(),t=P(e,n.input),o=P(e,n.file),r=e.querySelector("."+n.range+" input"),i=e.querySelector("."+n.range+" output"),l=P(e,n.select),s=e.querySelector("."+n.checkbox+" input"),c=P(e,n.textarea);return t.oninput=function(){F.resetValidationError()},t.onkeydown=function(e){setTimeout(function(){13===e.keyCode&&(e.stopPropagation(),F.clickConfirm())},0)},o.onchange=function(){F.resetValidationError()},r.oninput=function(){F.resetValidationError(),i.value=r.value},r.onchange=function(){F.resetValidationError(),r.previousSibling.value=r.value},l.onchange=function(){F.resetValidationError()},s.onchange=function(){F.resetValidationError()},c.oninput=function(){F.resetValidationError()},e}},d=function(e){return a.querySelector("."+e)},p=function(){return document.body.querySelector("."+n.modal)||u()},f=function(){var e=p();return e.querySelectorAll("."+n.icon)},m=function(){return d(n.title)},v=function(){return d(n.content)},h=function(){return d(n.image)},y=function(){return d(n.spacer)},g=function(){return d(n.progresssteps)},b=function(){return d(n.validationerror)},w=function(){return d(n.confirm)},C=function(){return d(n.cancel)},k=function(){return d(n.close)},S=function(t){var n=[w(),C()];return t&&n.reverse(),n.concat(Array.prototype.slice.call(p().querySelectorAll("button:not([class^="+e+"]), input:not([type=hidden]), textarea, select")))},x=function(e,t){return!!e.classList&&e.classList.contains(t)},E=function(e){if(e.focus(),"file"!==e.type){var t=e.value;e.value="",e.value=t}},A=function(e,t){if(e&&t){var n=t.split(/\s+/).filter(Boolean);n.forEach(function(t){e.classList.add(t)})}},B=function(e,t){if(e&&t){var n=t.split(/\s+/).filter(Boolean);n.forEach(function(t){e.classList.remove(t)})}},P=function(e,t){for(var n=0;n<e.childNodes.length;n++)if(x(e.childNodes[n],t))return e.childNodes[n]},L=function(e,t){t||(t="block"),e.style.opacity="",e.style.display=t},q=function(e){e.style.opacity="",e.style.display="none"},M=function(e){for(;e.firstChild;)e.removeChild(e.firstChild)},T=function(e){return e.offsetWidth||e.offsetHeight||e.getClientRects().length},V=function(e,t){e.style.removeProperty?e.style.removeProperty(t):e.style.removeAttribute(t)},H=function(e){if("function"==typeof MouseEvent){var t=new MouseEvent("click",{view:window,bubbles:!1,cancelable:!0});e.dispatchEvent(t)}else if(document.createEvent){var n=document.createEvent("MouseEvents");n.initEvent("click",!1,!1),e.dispatchEvent(n)}else document.createEventObject?e.fireEvent("onclick"):"function"==typeof e.onclick&&e.onclick()},O=function(){var e=document.createElement("div"),t={WebkitAnimation:"webkitAnimationEnd",OAnimation:"oAnimationEnd oanimationend",msAnimation:"MSAnimationEnd",animation:"animationend"};for(var n in t)if(t.hasOwnProperty(n)&&void 0!==e.style[n])return t[n];return!1}(),N=function(){var e=p();window.onkeydown=c.previousWindowKeyDown,c.previousActiveElement&&c.previousActiveElement.focus&&c.previousActiveElement.focus(),clearTimeout(e.timeout)},j=function(){var e=document.createElement("div");e.style.width="50px",e.style.height="50px",e.style.overflow="scroll",document.body.appendChild(e);var t=e.offsetWidth-e.clientWidth;return document.body.removeChild(e),t},I=function(e,t){var n=void 0;return function(){var o=function(){n=null,e()};clearTimeout(n),n=setTimeout(o,t)}},D="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"==typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e},W=(function(){function e(e){this.value=e}function t(t){function n(e,t){return new Promise(function(n,r){var l={key:e,arg:t,resolve:n,reject:r,next:null};a?a=a.next=l:(i=a=l,o(e,t))})}function o(n,i){try{var a=t[n](i),l=a.value;l instanceof e?Promise.resolve(l.value).then(function(e){o("next",e)},function(e){o("throw",e)}):r(a.done?"return":"normal",a.value)}catch(e){r("throw",e)}}function r(e,t){switch(e){case"return":i.resolve({value:t,done:!0});break;case"throw":i.reject(t);break;default:i.resolve({value:t,done:!1})}i=i.next,i?o(i.key,i.arg):a=null}var i,a;this._invoke=n,"function"!=typeof t.return&&(this.return=void 0)}return"function"==typeof Symbol&&Symbol.asyncIterator&&(t.prototype[Symbol.asyncIterator]=function(){return this}),t.prototype.next=function(e){return this._invoke("next",e)},t.prototype.throw=function(e){return this._invoke("throw",e)},t.prototype.return=function(e){return this._invoke("return",e)},{wrap:function(e){return function(){return new t(e.apply(this,arguments))}},await:function(t){return new e(t)}}}(),Object.assign||function(e){for(var t=1;t<arguments.length;t++){var n=arguments[t];for(var o in n)Object.prototype.hasOwnProperty.call(n,o)&&(e[o]=n[o])}return e}),U=W({},r),K=[],R=void 0,_=function(e){var t=p();for(var i in e)r.hasOwnProperty(i)||"extraParams"===i||console.warn('SweetAlert2: Unknown parameter "'+i+'"');t.style.width="number"==typeof e.width?e.width+"px":e.width,t.style.padding=e.padding+"px",t.style.background=e.background;var a=m(),l=v(),s=w(),c=C(),u=k();if(a.innerHTML=e.title.split("\n").join("<br>"),e.text||e.html){if("object"===D(e.html))if(l.innerHTML="",0 in e.html)for(var d=0;d in e.html;d++)l.appendChild(e.html[d].cloneNode(!0));else l.appendChild(e.html.cloneNode(!0));else e.html?l.innerHTML=e.html:e.text&&(l.innerHTML=(""+e.text).split("\n").join("<br>"));L(l)}else q(l);e.showCloseButton?L(u):q(u),t.className=n.modal,e.customClass&&A(t,e.customClass);var b=g(),S=parseInt(null===e.currentProgressStep?F.getQueueStep():e.currentProgressStep,10);e.progressSteps.length?(L(b),M(b),S>=e.progressSteps.length&&console.warn("SweetAlert2: Invalid currentProgressStep parameter, it should be less than progressSteps.length (currentProgressStep like JS arrays starts from 0)"),e.progressSteps.forEach(function(t,o){var r=document.createElement("li");if(A(r,n.progresscircle),r.innerHTML=t,o===S&&A(r,n.activeprogressstep),b.appendChild(r),o!==e.progressSteps.length-1){var i=document.createElement("li");A(i,n.progressline),i.style.width=e.progressStepsDistance,b.appendChild(i)}})):q(b);for(var x=f(),E=0;E<x.length;E++)q(x[E]);if(e.type){var P=!1;for(var T in o)if(e.type===T){P=!0;break}if(!P)return console.error("SweetAlert2: Unknown alert type: "+e.type),!1;var H=t.querySelector("."+n.icon+"."+o[e.type]);switch(L(H),e.type){case"success":A(H,"animate"),A(H.querySelector(".tip"),"animate-success-tip"),A(H.querySelector(".long"),"animate-success-long");break;case"error":A(H,"animate-error-icon"),A(H.querySelector(".x-mark"),"animate-x-mark");break;case"warning":A(H,"pulse-warning")}}var O=h();e.imageUrl?(O.setAttribute("src",e.imageUrl),L(O),e.imageWidth?O.setAttribute("width",e.imageWidth):O.removeAttribute("width"),e.imageHeight?O.setAttribute("height",e.imageHeight):O.removeAttribute("height"),O.className=n.image,e.imageClass&&A(O,e.imageClass)):q(O),e.showCancelButton?c.style.display="inline-block":q(c),e.showConfirmButton?V(s,"display"):q(s);var N=y();e.showConfirmButton||e.showCancelButton?L(N):q(N),s.innerHTML=e.confirmButtonText,c.innerHTML=e.cancelButtonText,e.buttonsStyling&&(s.style.backgroundColor=e.confirmButtonColor,c.style.backgroundColor=e.cancelButtonColor),s.className=n.confirm,A(s,e.confirmButtonClass),c.className=n.cancel,A(c,e.cancelButtonClass),e.buttonsStyling?(A(s,n.styled),A(c,n.styled)):(B(s,n.styled),B(c,n.styled),s.style.backgroundColor=s.style.borderLeftColor=s.style.borderRightColor="",c.style.backgroundColor=c.style.borderLeftColor=c.style.borderRightColor=""),e.animation===!0?B(t,n.noanimation):A(t,n.noanimation)},z=function(e,t){var o=p();e?(A(o,n.show),A(a,n.fade),B(o,n.hide)):B(o,n.fade),L(o),a.style.overflowY="hidden",O&&!x(o,n.noanimation)?o.addEventListener(O,function e(){o.removeEventListener(O,e),a.style.overflowY="auto"}):a.style.overflowY="auto",A(a,n.in),A(document.body,n.in),Q(),Z(),c.previousActiveElement=document.activeElement,null!==t&&"function"==typeof t&&t(void 0,o)},Q=function(){null===c.previousBodyPadding&&document.body.scrollHeight>window.innerHeight&&(c.previousBodyPadding=document.body.style.paddingRight,document.body.style.paddingRight=j()+"px")},Y=function(){null!==c.previousBodyPadding&&(document.body.style.paddingRight=c.previousBodyPadding,c.previousBodyPadding=null)},Z=function(){var e=/iPad|iPhone|iPod/.test(navigator.userAgent)&&!window.MSStream;if(e&&!x(document.body,n.iosfix)){var t=document.body.scrollTop;document.body.style.top=t*-1+"px",A(document.body,n.iosfix)}},J=function(){if(x(document.body,n.iosfix)){var e=parseInt(document.body.style.top,10);B(document.body,n.iosfix),document.body.scrollTop=e*-1}},$=function(){for(var e=arguments.length,t=Array(e),o=0;o<e;o++)t[o]=arguments[o];if(void 0===t[0])return console.error("SweetAlert2 expects at least 1 attribute!"),!1;var r=W({},U);switch(D(t[0])){case"string":r.title=t[0],r.text=t[1],r.type=t[2];break;case"object":W(r,t[0]),r.extraParams=t[0].extraParams,"email"===r.input&&null===r.inputValidator&&(r.inputValidator=function(e){return new Promise(function(t,n){var o=/^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;o.test(e)?t():n("Invalid email address")})});break;default:return console.error('SweetAlert2: Unexpected type of argument! Expected "string" or "object", got '+D(t[0])),!1}_(r);var i=p();return new Promise(function(e,t){r.timer&&(i.timeout=setTimeout(function(){F.closeModal(r.onClose),t("timer")},r.timer));var o=function(e){switch(e=e||r.input){case"select":case"textarea":case"file":return P(i,n[e]);case"checkbox":return i.querySelector("."+n.checkbox+" input");case"radio":return i.querySelector("."+n.radio+" input:checked")||i.querySelector("."+n.radio+" input:first-child");case"range":return i.querySelector("."+n.range+" input");default:return P(i,n.input)}},l=function(){var e=o();if(!e)return null;switch(r.input){case"checkbox":return e.checked?1:0;case"radio":return e.checked?e.value:null;case"file":return e.files.length?e.files[0]:null;default:return r.inputAutoTrim?e.value.trim():e.value}};r.input&&setTimeout(function(){var e=o();e&&E(e)},0);for(var u=function(t){r.showLoaderOnConfirm&&F.showLoading(),r.preConfirm?r.preConfirm(t,r.extraParams).then(function(n){F.closeModal(r.onClose),e(n||t)},function(e){F.hideLoading(),e&&F.showValidationError(e)}):(F.closeModal(r.onClose),e(t))},d=function(e){var n=e||window.event,o=n.target||n.srcElement,i=w(),a=C(),c=i===o||i.contains(o),d=a===o||a.contains(o);switch(n.type){case"mouseover":case"mouseup":r.buttonsStyling&&(c?i.style.backgroundColor=s(r.confirmButtonColor,-.1):d&&(a.style.backgroundColor=s(r.cancelButtonColor,-.1)));break;case"mouseout":r.buttonsStyling&&(c?i.style.backgroundColor=r.confirmButtonColor:d&&(a.style.backgroundColor=r.cancelButtonColor));break;case"mousedown":r.buttonsStyling&&(c?i.style.backgroundColor=s(r.confirmButtonColor,-.2):d&&(a.style.backgroundColor=s(r.cancelButtonColor,-.2)));break;case"click":c&&F.isVisible()?r.input?!function(){var e=l();r.inputValidator?(F.disableInput(),r.inputValidator(e,r.extraParams).then(function(){F.enableInput(),u(e)},function(e){F.enableInput(),e&&F.showValidationError(e)})):u(e)}():u(!0):d&&F.isVisible()&&(F.closeModal(r.onClose),t("cancel"))}},f=i.querySelectorAll("button"),m=0;m<f.length;m++)f[m].onclick=d,f[m].onmouseover=d,f[m].onmouseout=d,f[m].onmousedown=d;k().onclick=function(){F.closeModal(r.onClose),t("close")},a.onclick=function(e){e.target===a&&r.allowOutsideClick&&(F.closeModal(r.onClose),t("overlay"))};var v=w(),h=C();r.reverseButtons?v.parentNode.insertBefore(h,v):v.parentNode.insertBefore(v,h);var x=function(e,t){for(var n=S(r.focusCancel),o=0;o<n.length;o++){e+=t,e===n.length?e=0:e===-1&&(e=n.length-1);var i=n[e];if(T(i))return i.focus()}},M=function(e){var n=e||window.event,o=n.keyCode||n.which;if([9,13,32,27].indexOf(o)!==-1){for(var i=n.target||n.srcElement,a=S(r.focusCancel),l=-1,s=0;s<a.length;s++)if(i===a[s]){l=s;break}9===o?(n.shiftKey?x(l,-1):x(l,1),n.stopPropagation(),n.preventDefault()):13===o||32===o?l===-1&&(r.focusCancel?H(h,n):H(v,n)):27===o&&r.allowEscapeKey===!0&&(F.closeModal(r.onClose),t("esc"))}};c.previousWindowKeyDown=window.onkeydown,window.onkeydown=M,r.buttonsStyling&&(v.style.borderLeftColor=r.confirmButtonColor,v.style.borderRightColor=r.confirmButtonColor),F.showLoading=F.enableLoading=function(){L(y()),L(v,"inline-block"),A(v,n.loading),A(i,n.loading),v.disabled=!0,h.disabled=!0},F.hideLoading=F.disableLoading=function(){r.showConfirmButton||(q(v),r.showCancelButton||q(y())),B(v,n.loading),B(i,n.loading),v.disabled=!1,h.disabled=!1},F.enableButtons=function(){v.disabled=!1,h.disabled=!1},F.disableButtons=function(){v.disabled=!0,h.disabled=!0},F.enableConfirmButton=function(){v.disabled=!1},F.disableConfirmButton=function(){v.disabled=!0},F.enableInput=function(){var e=o();if(!e)return!1;if("radio"===e.type)for(var t=e.parentNode.parentNode,n=t.querySelectorAll("input"),r=0;r<n.length;r++)n[r].disabled=!1;else e.disabled=!1},F.disableInput=function(){var e=o();if(!e)return!1;if(e&&"radio"===e.type)for(var t=e.parentNode.parentNode,n=t.querySelectorAll("input"),r=0;r<n.length;r++)n[r].disabled=!0;else e.disabled=!0},F.recalculateHeight=I(function(){var e=p(),t=e.style.display;e.style.minHeight="",L(e),e.style.minHeight=e.scrollHeight+1+"px",e.style.display=t},50),F.showValidationError=function(e){var t=b();t.innerHTML=e,L(t);var r=o();E(r),A(r,n.inputerror)},F.resetValidationError=function(){var e=b();q(e),F.recalculateHeight();var t=o();t&&B(t,n.inputerror)},F.getProgressSteps=function(){return r.progressSteps},F.setProgressSteps=function(e){r.progressSteps=e,_(r)},F.showProgressSteps=function(){L(g())},F.hideProgressSteps=function(){q(g())},F.enableButtons(),F.hideLoading(),F.resetValidationError();for(var V=["input","file","range","select","radio","checkbox","textarea"],O=void 0,N=0;N<V.length;N++){var j=n[V[N]],W=P(i,j);if(O=o(V[N])){for(var U in O.attributes)if(O.attributes.hasOwnProperty(U)){var K=O.attributes[U].name;"type"!==K&&"value"!==K&&O.removeAttribute(K)}for(var Q in r.inputAttributes)O.setAttribute(Q,r.inputAttributes[Q])}W.className=j,r.inputClass&&A(W,r.inputClass),q(W)}var Y=void 0;!function(){switch(r.input){case"text":case"email":case"password":case"number":case"tel":O=P(i,n.input),O.value=r.inputValue,O.placeholder=r.inputPlaceholder,O.type=r.input,L(O);break;case"file":O=P(i,n.file),O.placeholder=r.inputPlaceholder,O.type=r.input,L(O);break;case"range":var e=P(i,n.range),t=e.querySelector("input"),a=e.querySelector("output");t.value=r.inputValue,t.type=r.input,a.value=r.inputValue,L(e);break;case"select":var l=P(i,n.select);if(l.innerHTML="",r.inputPlaceholder){var s=document.createElement("option");s.innerHTML=r.inputPlaceholder,s.value="",s.disabled=!0,s.selected=!0,l.appendChild(s)}Y=function(e){for(var t in e){var n=document.createElement("option");n.value=t,n.innerHTML=e[t],r.inputValue===t&&(n.selected=!0),l.appendChild(n)}L(l),l.focus()};break;case"radio":var c=P(i,n.radio);c.innerHTML="",Y=function(e){for(var t in e){var o=1,i=document.createElement("input"),a=document.createElement("label"),l=document.createElement("span");i.type="radio",i.name=n.radio,i.value=t,i.id=n.radio+"-"+o++,r.inputValue===t&&(i.checked=!0),l.innerHTML=e[t],a.appendChild(i),a.appendChild(l),a.for=i.id,c.appendChild(a)}L(c);var s=c.querySelectorAll("input");s.length&&s[0].focus()};break;case"checkbox":var u=P(i,n.checkbox),d=o("checkbox");d.type="checkbox",d.value=1,d.id=n.checkbox,d.checked=Boolean(r.inputValue);var p=u.getElementsByTagName("span");p.length&&u.removeChild(p[0]),p=document.createElement("span"),p.innerHTML=r.inputPlaceholder,u.appendChild(p),L(u);break;case"textarea":var f=P(i,n.textarea);f.value=r.inputValue,f.placeholder=r.inputPlaceholder,L(f);break;case null:break;default:console.error('SweetAlert2: Unexpected type of input! Expected "text", "email", "password", "select", "checkbox", "textarea" or "file", got "'+r.input+'"')}}(),"select"!==r.input&&"radio"!==r.input||(r.inputOptions instanceof Promise?(F.showLoading(),r.inputOptions.then(function(e){F.hideLoading(),Y(e)})):"object"===D(r.inputOptions)?Y(r.inputOptions):console.error("SweetAlert2: Unexpected type of inputOptions! Expected object or Promise, got "+D(r.inputOptions))),z(r.animation,r.onOpen),x(-1,1),a.scrollTop=0,"undefined"==typeof MutationObserver||R||(R=new MutationObserver(F.recalculateHeight),R.observe(i,{childList:!0,characterData:!0,subtree:!0}))})},F=function e(){for(var t=arguments.length,n=Array(t),o=0;o<t;o++)n[o]=arguments[o];return e.isVisible()&&e.close(),$.apply(void 0,n)};return F.isVisible=function(){var e=p();return T(e)},F.queue=function(e){K=e;var t=p(),n=function(){K=[],t.removeAttribute("data-queue-step")},o=[];return new Promise(function(e,r){!function i(a,l){a<K.length?(t.setAttribute("data-queue-step",a),F(K[a]).then(function(e){o.push(e),i(a+1,l)},function(e){n(),r(e)})):(n(),e(o))}(0)})},F.getQueueStep=function(){return p().getAttribute("data-queue-step")},F.insertQueueStep=function(e,t){return t&&t<K.length?K.splice(t,0,e):K.push(e)},F.deleteQueueStep=function(e){"undefined"!=typeof K[e]&&K.splice(e,1)},F.close=F.closeModal=function(e){var t=p();B(t,n.show),A(t,n.hide);var r=t.querySelector("."+n.icon+"."+o.success);B(r,"animate"),B(r.querySelector(".tip"),"animate-success-tip"),B(r.querySelector(".long"),"animate-success-long");var i=t.querySelector("."+n.icon+"."+o.error);B(i,"animate-error-icon"),B(i.querySelector(".x-mark"),"animate-x-mark");var l=t.querySelector("."+n.icon+"."+o.warning);B(l,"pulse-warning"),N();var s=function(){q(t),t.style.minHeight="",B(a,n.in),B(document.body,n.in),Y(),J()};O&&!x(t,n.noanimation)?t.addEventListener(O,function e(){t.removeEventListener(O,e),x(t,n.hide)&&s()}):s(),null!==e&&"function"==typeof e&&e(void 0,t)},F.clickConfirm=function(){return w().click()},F.clickCancel=function(){return C().click()},F.setDefaults=function(e){if(!e||"object"!==("undefined"==typeof e?"undefined":D(e)))return console.error("SweetAlert2: the argument for setDefaults() is required and has to be a object");for(var t in e)r.hasOwnProperty(t)||"extraParams"===t||(console.warn('SweetAlert2: Unknown parameter "'+t+'"'),delete e[t]);W(U,e)},F.resetDefaults=function(){U=W({},r)},F.noop=function(){},F.version="6.1.0",F}),window.Sweetalert2&&(window.sweetAlert=window.swal=window.Sweetalert2);