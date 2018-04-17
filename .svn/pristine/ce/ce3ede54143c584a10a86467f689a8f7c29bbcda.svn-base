/**
 * @license
 * lodash lodash.com/license | Underscore.js 1.8.3 underscorejs.org/LICENSE
 */
;(function(){function t(t,n){return t.set(n[0],n[1]),t}function n(t,n){return t.add(n),t}function r(t,n,r){switch(r.length){case 0:return t.call(n);case 1:return t.call(n,r[0]);case 2:return t.call(n,r[0],r[1]);case 3:return t.call(n,r[0],r[1],r[2])}return t.apply(n,r)}function e(t,n,r,e){for(var u=-1,o=t?t.length:0;++u<o;){var i=t[u];n(e,i,r(i),t)}return e}function u(t,n){for(var r=-1,e=t?t.length:0;++r<e&&false!==n(t[r],r,t););return t}function o(t,n){for(var r=t?t.length:0;r--&&false!==n(t[r],r,t););
return t}function i(t,n){for(var r=-1,e=t?t.length:0;++r<e;)if(!n(t[r],r,t))return false;return true}function f(t,n){for(var r=-1,e=t?t.length:0,u=0,o=[];++r<e;){var i=t[r];n(i,r,t)&&(o[u++]=i)}return o}function c(t,n){return!(!t||!t.length)&&-1<d(t,n,0)}function a(t,n,r){for(var e=-1,u=t?t.length:0;++e<u;)if(r(n,t[e]))return true;return false}function l(t,n){for(var r=-1,e=t?t.length:0,u=Array(e);++r<e;)u[r]=n(t[r],r,t);return u}function s(t,n){for(var r=-1,e=n.length,u=t.length;++r<e;)t[u+r]=n[r];return t}function h(t,n,r,e){
var u=-1,o=t?t.length:0;for(e&&o&&(r=t[++u]);++u<o;)r=n(r,t[u],u,t);return r}function p(t,n,r,e){var u=t?t.length:0;for(e&&u&&(r=t[--u]);u--;)r=n(r,t[u],u,t);return r}function _(t,n){for(var r=-1,e=t?t.length:0;++r<e;)if(n(t[r],r,t))return true;return false}function v(t,n,r){var e;return r(t,function(t,r,u){if(n(t,r,u))return e=r,false}),e}function g(t,n,r,e){var u=t.length;for(r+=e?1:-1;e?r--:++r<u;)if(n(t[r],r,t))return r;return-1}function d(t,n,r){if(n!==n)return g(t,b,r);--r;for(var e=t.length;++r<e;)if(t[r]===n)return r;
return-1}function y(t,n,r,e){--r;for(var u=t.length;++r<u;)if(e(t[r],n))return r;return-1}function b(t){return t!==t}function x(t,n){var r=t?t.length:0;return r?O(t,n)/r:Z}function j(t){return function(n){return null==n?N:n[t]}}function m(t){return function(n){return null==t?N:t[n]}}function w(t,n,r,e,u){return u(t,function(t,u,o){r=e?(e=false,t):n(r,t,u,o)}),r}function A(t,n){var r=t.length;for(t.sort(n);r--;)t[r]=t[r].c;return t}function O(t,n){for(var r,e=-1,u=t.length;++e<u;){var o=n(t[e]);o!==N&&(r=r===N?o:r+o);
}return r}function k(t,n){for(var r=-1,e=Array(t);++r<t;)e[r]=n(r);return e}function E(t,n){return l(n,function(n){return[n,t[n]]})}function S(t){return function(n){return t(n)}}function I(t,n){return l(n,function(n){return t[n]})}function R(t,n){return t.has(n)}function W(t,n){for(var r=-1,e=t.length;++r<e&&-1<d(n,t[r],0););return r}function B(t,n){for(var r=t.length;r--&&-1<d(n,t[r],0););return r}function M(t){return"\\"+Ft[t]}function C(t){var n=false;if(null!=t&&typeof t.toString!="function")try{
n=!!(t+"")}catch(t){}return n}function L(t){var n=-1,r=Array(t.size);return t.forEach(function(t,e){r[++n]=[e,t]}),r}function D(t){var n=Object;return function(r){return t(n(r))}}function z(t,n){for(var r=-1,e=t.length,u=0,o=[];++r<e;){var i=t[r];i!==n&&"__lodash_placeholder__"!==i||(t[r]="__lodash_placeholder__",o[u++]=r)}return o}function U(t){var n=-1,r=Array(t.size);return t.forEach(function(t){r[++n]=t}),r}function $(t){var n=-1,r=Array(t.size);return t.forEach(function(t){r[++n]=[t,t]}),r}function F(t){
if(!t||!Ct.test(t))return t.length;for(var n=Bt.lastIndex=0;Bt.test(t);)n++;return n}function T(m){function Et(t){return Gu.call(t)}function St(t){if(ou(t)&&!Fi(t)&&!(t instanceof Ft)){if(t instanceof $t)return t;if(qu.call(t,"__wrapped__"))return Ee(t)}return new $t(t)}function It(){}function $t(t,n){this.__wrapped__=t,this.__actions__=[],this.__chain__=!!n,this.__index__=0,this.__values__=N}function Ft(t){this.__wrapped__=t,this.__actions__=[],this.__dir__=1,this.__filtered__=false,this.__iteratees__=[],
this.__takeCount__=4294967295,this.__views__=[]}function Pt(t){var n=-1,r=t?t.length:0;for(this.clear();++n<r;){var e=t[n];this.set(e[0],e[1])}}function Zt(t){var n=-1,r=t?t.length:0;for(this.clear();++n<r;){var e=t[n];this.set(e[0],e[1])}}function Vt(t){var n=-1,r=t?t.length:0;for(this.clear();++n<r;){var e=t[n];this.set(e[0],e[1])}}function Kt(t){var n=-1,r=t?t.length:0;for(this.__data__=new Vt;++n<r;)this.add(t[n])}function Jt(t){this.__data__=new Zt(t)}function fn(t,n){var r,e=Fi(t)||cu(t)||He(t)?k(t.length,String):[],u=e.length,o=!!u;
for(r in t)!n&&!qu.call(t,r)||o&&("length"==r||ge(r,u))||e.push(r);return e}function cn(t,n,r,e){return t===N||Ye(t,Fu[r])&&!qu.call(e,r)?n:t}function an(t,n,r){(r===N||Ye(t[n],r))&&(typeof n!="number"||r!==N||n in t)||(t[n]=r)}function ln(t,n,r){var e=t[n];qu.call(t,n)&&Ye(e,r)&&(r!==N||n in t)||(t[n]=r)}function sn(t,n){for(var r=t.length;r--;)if(Ye(t[r][0],n))return r;return-1}function hn(t,n,r,e){return No(t,function(t,u,o){n(e,t,r(t),o)}),e}function pn(t,n){return t&&Br(n,bu(n),t)}function _n(t,n){
for(var r=-1,e=null==t,u=n.length,o=Cu(u);++r<u;)o[r]=e?N:du(t,n[r]);return o}function vn(t,n,r){return t===t&&(r!==N&&(t=t<=r?t:r),n!==N&&(t=t>=n?t:n)),t}function gn(t,n,r,e,o,i,f){var c;if(e&&(c=i?e(t,o,i,f):e(t)),c!==N)return c;if(!uu(t))return t;if(o=Fi(t)){if(c=he(t),!n)return Wr(t,c)}else{var a=Et(t),l="[object Function]"==a||"[object GeneratorFunction]"==a;if(Ni(t))return kr(t,n);if("[object Object]"==a||"[object Arguments]"==a||l&&!i){if(C(t))return i?t:{};if(c=pe(l?{}:t),!n)return Mr(t,pn(c,t));
}else{if(!Ut[a])return i?t:{};c=_e(t,a,gn,n)}}if(f||(f=new Jt),i=f.get(t))return i;if(f.set(t,c),!o)var s=r?Rn(t,bu,Yo):bu(t);return u(s||t,function(u,o){s&&(o=u,u=t[o]),ln(c,o,gn(u,n,r,e,o,t,f))}),c}function dn(t){var n=bu(t);return function(r){return yn(r,t,n)}}function yn(t,n,r){var e=r.length;if(null==t)return!e;for(t=Object(t);e--;){var u=r[e],o=n[u],i=t[u];if(i===N&&!(u in t)||!o(i))return false}return true}function bn(t){return uu(t)?ro(t):{}}function xn(t,n,r){if(typeof t!="function")throw new Uu("Expected a function");
return ti(function(){t.apply(N,r)},n)}function jn(t,n,r,e){var u=-1,o=c,i=true,f=t.length,s=[],h=n.length;if(!f)return s;r&&(n=l(n,S(r))),e?(o=a,i=false):200<=n.length&&(o=R,i=false,n=new Kt(n));t:for(;++u<f;){var p=t[u],_=r?r(p):p,p=e||0!==p?p:0;if(i&&_===_){for(var v=h;v--;)if(n[v]===_)continue t;s.push(p)}else o(n,_,e)||s.push(p)}return s}function mn(t,n){var r=true;return No(t,function(t,e,u){return r=!!n(t,e,u)}),r}function wn(t,n,r){for(var e=-1,u=t.length;++e<u;){var o=t[e],i=n(o);if(null!=i&&(f===N?i===i&&!au(i):r(i,f)))var f=i,c=o;
}return c}function An(t,n){var r=[];return No(t,function(t,e,u){n(t,e,u)&&r.push(t)}),r}function On(t,n,r,e,u){var o=-1,i=t.length;for(r||(r=ve),u||(u=[]);++o<i;){var f=t[o];0<n&&r(f)?1<n?On(f,n-1,r,e,u):s(u,f):e||(u[u.length]=f)}return u}function kn(t,n){return t&&Zo(t,n,bu)}function En(t,n){return t&&qo(t,n,bu)}function Sn(t,n){return f(n,function(n){return nu(t[n])})}function In(t,n){n=ye(n,t)?[n]:Ar(n);for(var r=0,e=n.length;null!=t&&r<e;)t=t[Ae(n[r++])];return r&&r==e?t:N}function Rn(t,n,r){
return n=n(t),Fi(t)?n:s(n,r(t))}function Wn(t,n){return t>n}function Bn(t,n){return null!=t&&qu.call(t,n)}function Mn(t,n){return null!=t&&n in Object(t)}function Cn(t,n,r){for(var e=r?a:c,u=t[0].length,o=t.length,i=o,f=Cu(o),s=1/0,h=[];i--;){var p=t[i];i&&n&&(p=l(p,S(n))),s=yo(p.length,s),f[i]=!r&&(n||120<=u&&120<=p.length)?new Kt(i&&p):N}var p=t[0],_=-1,v=f[0];t:for(;++_<u&&h.length<s;){var g=p[_],d=n?n(g):g,g=r||0!==g?g:0;if(v?!R(v,d):!e(h,d,r)){for(i=o;--i;){var y=f[i];if(y?!R(y,d):!e(t[i],d,r))continue t;
}v&&v.push(d),h.push(g)}}return h}function Ln(t,n,r){var e={};return kn(t,function(t,u,o){n(e,r(t),u,o)}),e}function Dn(t,n,e){return ye(n,t)||(n=Ar(n),t=we(t,n),n=We(n)),n=null==t?t:t[Ae(n)],null==n?N:r(n,t,e)}function zn(t){return ou(t)&&"[object ArrayBuffer]"==Gu.call(t)}function Un(t){return ou(t)&&"[object Date]"==Gu.call(t)}function $n(t,n,r,e,u){if(t===n)n=true;else if(null==t||null==n||!uu(t)&&!ou(n))n=t!==t&&n!==n;else t:{var o=Fi(t),i=Fi(n),f="[object Array]",c="[object Array]";o||(f=Et(t),
f="[object Arguments]"==f?"[object Object]":f),i||(c=Et(n),c="[object Arguments]"==c?"[object Object]":c);var a="[object Object]"==f&&!C(t),i="[object Object]"==c&&!C(n);if((c=f==c)&&!a)u||(u=new Jt),n=o||Ki(t)?ee(t,n,$n,r,e,u):ue(t,n,f,$n,r,e,u);else{if(!(2&e)&&(o=a&&qu.call(t,"__wrapped__"),f=i&&qu.call(n,"__wrapped__"),o||f)){t=o?t.value():t,n=f?n.value():n,u||(u=new Jt),n=$n(t,n,r,e,u);break t}if(c)n:if(u||(u=new Jt),o=2&e,f=bu(t),i=f.length,c=bu(n).length,i==c||o){for(a=i;a--;){var l=f[a];if(!(o?l in n:qu.call(n,l))){
n=false;break n}}if((c=u.get(t))&&u.get(n))n=c==n;else{c=true,u.set(t,n),u.set(n,t);for(var s=o;++a<i;){var l=f[a],h=t[l],p=n[l];if(r)var _=o?r(p,h,l,n,t,u):r(h,p,l,t,n,u);if(_===N?h!==p&&!$n(h,p,r,e,u):!_){c=false;break}s||(s="constructor"==l)}c&&!s&&(r=t.constructor,e=n.constructor,r!=e&&"constructor"in t&&"constructor"in n&&!(typeof r=="function"&&r instanceof r&&typeof e=="function"&&e instanceof e)&&(c=false)),u.delete(t),u.delete(n),n=c}}else n=false;else n=false}}return n}function Fn(t){return ou(t)&&"[object Map]"==Et(t);
}function Tn(t,n,r,e){var u=r.length,o=u,i=!e;if(null==t)return!o;for(t=Object(t);u--;){var f=r[u];if(i&&f[2]?f[1]!==t[f[0]]:!(f[0]in t))return false}for(;++u<o;){var f=r[u],c=f[0],a=t[c],l=f[1];if(i&&f[2]){if(a===N&&!(c in t))return false}else{if(f=new Jt,e)var s=e(a,l,c,t,n,f);if(s===N?!$n(l,a,e,3,f):!s)return false}}return true}function Nn(t){return!(!uu(t)||Pu&&Pu in t)&&(nu(t)||C(t)?Yu:jt).test(Oe(t))}function Pn(t){return uu(t)&&"[object RegExp]"==Gu.call(t)}function Zn(t){return ou(t)&&"[object Set]"==Et(t);
}function qn(t){return ou(t)&&eu(t.length)&&!!zt[Gu.call(t)]}function Vn(t){return typeof t=="function"?t:null==t?Eu:typeof t=="object"?Fi(t)?Qn(t[0],t[1]):Hn(t):Wu(t)}function Kn(t){if(!xe(t))return vo(t);var n,r=[];for(n in Object(t))qu.call(t,n)&&"constructor"!=n&&r.push(n);return r}function Gn(t){if(!uu(t)){var n=[];if(null!=t)for(var r in Object(t))n.push(r);return n}r=xe(t);var e=[];for(n in t)("constructor"!=n||!r&&qu.call(t,n))&&e.push(n);return e}function Jn(t,n){return t<n}function Yn(t,n){
var r=-1,e=Qe(t)?Cu(t.length):[];return No(t,function(t,u,o){e[++r]=n(t,u,o)}),e}function Hn(t){var n=ae(t);return 1==n.length&&n[0][2]?je(n[0][0],n[0][1]):function(r){return r===t||Tn(r,t,n)}}function Qn(t,n){return ye(t)&&n===n&&!uu(n)?je(Ae(t),n):function(r){var e=du(r,t);return e===N&&e===n?yu(r,t):$n(n,e,N,3)}}function Xn(t,n,r,e,o){if(t!==n){if(!Fi(n)&&!Ki(n))var i=Gn(n);u(i||n,function(u,f){if(i&&(f=u,u=n[f]),uu(u)){o||(o=new Jt);var c=f,a=o,l=t[c],s=n[c],h=a.get(s);if(h)an(t,c,h);else{var h=e?e(l,s,c+"",t,n,a):N,p=h===N;
p&&(h=s,Fi(s)||Ki(s)?Fi(l)?h=l:Xe(l)?h=Wr(l):(p=false,h=gn(s,true)):fu(s)||He(s)?He(l)?h=vu(l):!uu(l)||r&&nu(l)?(p=false,h=gn(s,true)):h=l:p=false),p&&(a.set(s,h),Xn(h,s,r,e,a),a.delete(s)),an(t,c,h)}}else c=e?e(t[f],u,f+"",t,n,o):N,c===N&&(c=u),an(t,f,c)})}}function tr(t,n){var r=t.length;if(r)return n+=0>n?r:0,ge(n,r)?t[n]:N}function nr(t,n,r){var e=-1;return n=l(n.length?n:[Eu],S(fe())),t=Yn(t,function(t){return{a:l(n,function(n){return n(t)}),b:++e,c:t}}),A(t,function(t,n){var e;t:{e=-1;for(var u=t.a,o=n.a,i=u.length,f=r.length;++e<i;){
var c=Sr(u[e],o[e]);if(c){e=e>=f?c:c*("desc"==r[e]?-1:1);break t}}e=t.b-n.b}return e})}function rr(t,n){return t=Object(t),er(t,n,function(n,r){return r in t})}function er(t,n,r){for(var e=-1,u=n.length,o={};++e<u;){var i=n[e],f=t[i];r(f,i)&&(o[i]=f)}return o}function ur(t){return function(n){return In(n,t)}}function or(t,n,r,e){var u=e?y:d,o=-1,i=n.length,f=t;for(t===n&&(n=Wr(n)),r&&(f=l(t,S(r)));++o<i;)for(var c=0,a=n[o],a=r?r(a):a;-1<(c=u(f,a,c,e));)f!==t&&uo.call(f,c,1),uo.call(t,c,1);return t;
}function ir(t,n){for(var r=t?n.length:0,e=r-1;r--;){var u=n[r];if(r==e||u!==o){var o=u;if(ge(u))uo.call(t,u,1);else if(ye(u,t))delete t[Ae(u)];else{var u=Ar(u),i=we(t,u);null!=i&&delete i[Ae(We(u))]}}}}function fr(t,n){return t+lo(xo()*(n-t+1))}function cr(t,n){var r="";if(!t||1>n||9007199254740991<n)return r;do n%2&&(r+=t),(n=lo(n/2))&&(t+=t);while(n);return r}function ar(t,n){return n=go(n===N?t.length-1:n,0),function(){for(var e=arguments,u=-1,o=go(e.length-n,0),i=Cu(o);++u<o;)i[u]=e[n+u];for(u=-1,
o=Cu(n+1);++u<n;)o[u]=e[u];return o[n]=i,r(t,this,o)}}function lr(t,n,r,e){if(!uu(t))return t;n=ye(n,t)?[n]:Ar(n);for(var u=-1,o=n.length,i=o-1,f=t;null!=f&&++u<o;){var c=Ae(n[u]),a=r;if(u!=i){var l=f[c],a=e?e(l,c,f):N;a===N&&(a=uu(l)?l:ge(n[u+1])?[]:{})}ln(f,c,a),f=f[c]}return t}function sr(t,n,r){var e=-1,u=t.length;for(0>n&&(n=-n>u?0:u+n),r=r>u?u:r,0>r&&(r+=u),u=n>r?0:r-n>>>0,n>>>=0,r=Cu(u);++e<u;)r[e]=t[e+n];return r}function hr(t,n){var r;return No(t,function(t,e,u){return r=n(t,e,u),!r}),!!r;
}function pr(t,n,r){var e=0,u=t?t.length:e;if(typeof n=="number"&&n===n&&2147483647>=u){for(;e<u;){var o=e+u>>>1,i=t[o];null!==i&&!au(i)&&(r?i<=n:i<n)?e=o+1:u=o}return u}return _r(t,n,Eu,r)}function _r(t,n,r,e){n=r(n);for(var u=0,o=t?t.length:0,i=n!==n,f=null===n,c=au(n),a=n===N;u<o;){var l=lo((u+o)/2),s=r(t[l]),h=s!==N,p=null===s,_=s===s,v=au(s);(i?e||_:a?_&&(e||h):f?_&&h&&(e||!p):c?_&&h&&!p&&(e||!v):p||v?0:e?s<=n:s<n)?u=l+1:o=l}return yo(o,4294967294)}function vr(t,n){for(var r=-1,e=t.length,u=0,o=[];++r<e;){
var i=t[r],f=n?n(i):i;if(!r||!Ye(f,c)){var c=f;o[u++]=0===i?0:i}}return o}function gr(t){return typeof t=="number"?t:au(t)?Z:+t}function dr(t){if(typeof t=="string")return t;if(au(t))return To?To.call(t):"";var n=t+"";return"0"==n&&1/t==-P?"-0":n}function yr(t,n,r){var e=-1,u=c,o=t.length,i=true,f=[],l=f;if(r)i=false,u=a;else if(200<=o){if(u=n?null:Go(t))return U(u);i=false,u=R,l=new Kt}else l=n?[]:f;t:for(;++e<o;){var s=t[e],h=n?n(s):s,s=r||0!==s?s:0;if(i&&h===h){for(var p=l.length;p--;)if(l[p]===h)continue t;
n&&l.push(h),f.push(s)}else u(l,h,r)||(l!==f&&l.push(h),f.push(s))}return f}function br(t,n,r,e){for(var u=t.length,o=e?u:-1;(e?o--:++o<u)&&n(t[o],o,t););return r?sr(t,e?0:o,e?o+1:u):sr(t,e?o+1:0,e?u:o)}function xr(t,n){var r=t;return r instanceof Ft&&(r=r.value()),h(n,function(t,n){return n.func.apply(n.thisArg,s([t],n.args))},r)}function jr(t,n,r){for(var e=-1,u=t.length;++e<u;)var o=o?s(jn(o,t[e],n,r),jn(t[e],o,n,r)):t[e];return o&&o.length?yr(o,n,r):[]}function mr(t,n,r){for(var e=-1,u=t.length,o=n.length,i={};++e<u;)r(i,t[e],e<o?n[e]:N);
return i}function wr(t){return Xe(t)?t:[]}function Ar(t){return Fi(t)?t:ri(t)}function Or(t,n,r){var e=t.length;return r=r===N?e:r,!n&&r>=e?t:sr(t,n,r)}function kr(t,n){if(n)return t.slice();var r=new t.constructor(t.length);return t.copy(r),r}function Er(t){var n=new t.constructor(t.byteLength);return new Xu(n).set(new Xu(t)),n}function Sr(t,n){if(t!==n){var r=t!==N,e=null===t,u=t===t,o=au(t),i=n!==N,f=null===n,c=n===n,a=au(n);if(!f&&!a&&!o&&t>n||o&&i&&c&&!f&&!a||e&&i&&c||!r&&c||!u)return 1;if(!e&&!o&&!a&&t<n||a&&r&&u&&!e&&!o||f&&r&&u||!i&&u||!c)return-1;
}return 0}function Ir(t,n,r,e){var u=-1,o=t.length,i=r.length,f=-1,c=n.length,a=go(o-i,0),l=Cu(c+a);for(e=!e;++f<c;)l[f]=n[f];for(;++u<i;)(e||u<o)&&(l[r[u]]=t[u]);for(;a--;)l[f++]=t[u++];return l}function Rr(t,n,r,e){var u=-1,o=t.length,i=-1,f=r.length,c=-1,a=n.length,l=go(o-f,0),s=Cu(l+a);for(e=!e;++u<l;)s[u]=t[u];for(l=u;++c<a;)s[l+c]=n[c];for(;++i<f;)(e||u<o)&&(s[l+r[i]]=t[u++]);return s}function Wr(t,n){var r=-1,e=t.length;for(n||(n=Cu(e));++r<e;)n[r]=t[r];return n}function Br(t,n,r,e){r||(r={});
for(var u=-1,o=n.length;++u<o;){var i=n[u],f=e?e(r[i],t[i],i,r,t):N;ln(r,i,f===N?t[i]:f)}return r}function Mr(t,n){return Br(t,Yo(t),n)}function Cr(t,n){return function(r,u){var o=Fi(r)?e:hn,i=n?n():{};return o(r,t,fe(u,2),i)}}function Lr(t){return ar(function(n,r){var e=-1,u=r.length,o=1<u?r[u-1]:N,i=2<u?r[2]:N,o=3<t.length&&typeof o=="function"?(u--,o):N;for(i&&de(r[0],r[1],i)&&(o=3>u?N:o,u=1),n=Object(n);++e<u;)(i=r[e])&&t(n,i,e,o);return n})}function Dr(t,n){return function(r,e){if(null==r)return r;
if(!Qe(r))return t(r,e);for(var u=r.length,o=n?u:-1,i=Object(r);(n?o--:++o<u)&&false!==e(i[o],o,i););return r}}function zr(t){return function(n,r,e){var u=-1,o=Object(n);e=e(n);for(var i=e.length;i--;){var f=e[t?i:++u];if(false===r(o[f],f,o))break}return n}}function Ur(t,n,r){function e(){return(this&&this!==qt&&this instanceof e?o:t).apply(u?r:this,arguments)}var u=1&n,o=Tr(t);return e}function $r(t){return function(n){n=gu(n);var r=Ct.test(n)?n.match(Bt):N,e=r?r[0]:n.charAt(0);return n=r?Or(r,1).join(""):n.slice(1),
e[t]()+n}}function Fr(t){return function(n){return h(Ou(Au(n).replace(Rt,"")),t,"")}}function Tr(t){return function(){var n=arguments;switch(n.length){case 0:return new t;case 1:return new t(n[0]);case 2:return new t(n[0],n[1]);case 3:return new t(n[0],n[1],n[2]);case 4:return new t(n[0],n[1],n[2],n[3]);case 5:return new t(n[0],n[1],n[2],n[3],n[4]);case 6:return new t(n[0],n[1],n[2],n[3],n[4],n[5]);case 7:return new t(n[0],n[1],n[2],n[3],n[4],n[5],n[6])}var r=bn(t.prototype),n=t.apply(r,n);return uu(n)?n:r;
}}function Nr(t,n,e){function u(){for(var i=arguments.length,f=Cu(i),c=i,a=ie(u);c--;)f[c]=arguments[c];return c=3>i&&f[0]!==a&&f[i-1]!==a?[]:z(f,a),i-=c.length,i<e?Xr(t,n,qr,u.placeholder,N,f,c,N,N,e-i):r(this&&this!==qt&&this instanceof u?o:t,this,f)}var o=Tr(t);return u}function Pr(t){return function(n,r,e){var u=Object(n);if(!Qe(n)){var o=fe(r,3);n=bu(n),r=function(t){return o(u[t],t,u)}}return r=t(n,r,e),-1<r?u[o?n[r]:r]:N}}function Zr(t){return ar(function(n){n=On(n,1);var r=n.length,e=r,u=$t.prototype.thru;
for(t&&n.reverse();e--;){var o=n[e];if(typeof o!="function")throw new Uu("Expected a function");if(u&&!i&&"wrapper"==oe(o))var i=new $t([],(true))}for(e=i?e:r;++e<r;)var o=n[e],u=oe(o),f="wrapper"==u?Jo(o):N,i=f&&be(f[0])&&424==f[1]&&!f[4].length&&1==f[9]?i[oe(f[0])].apply(i,f[3]):1==o.length&&be(o)?i[u]():i.thru(o);return function(){var t=arguments,e=t[0];if(i&&1==t.length&&Fi(e)&&200<=e.length)return i.plant(e).value();for(var u=0,t=r?n[u].apply(this,t):e;++u<r;)t=n[u].call(this,t);return t}})}function qr(t,n,r,e,u,o,i,f,c,a){
function l(){for(var d=arguments.length,y=Cu(d),b=d;b--;)y[b]=arguments[b];if(_){var x,j=ie(l),b=y.length;for(x=0;b--;)y[b]===j&&x++}if(e&&(y=Ir(y,e,u,_)),o&&(y=Rr(y,o,i,_)),d-=x,_&&d<a)return j=z(y,j),Xr(t,n,qr,l.placeholder,r,y,j,f,c,a-d);if(j=h?r:this,b=p?j[t]:t,d=y.length,f){x=y.length;for(var m=yo(f.length,x),w=Wr(y);m--;){var A=f[m];y[m]=ge(A,x)?w[A]:N}}else v&&1<d&&y.reverse();return s&&c<d&&(y.length=c),this&&this!==qt&&this instanceof l&&(b=g||Tr(b)),b.apply(j,y)}var s=128&n,h=1&n,p=2&n,_=24&n,v=512&n,g=p?N:Tr(t);
return l}function Vr(t,n){return function(r,e){return Ln(r,t,n(e))}}function Kr(t,n){return function(r,e){var u;if(r===N&&e===N)return n;if(r!==N&&(u=r),e!==N){if(u===N)return e;typeof r=="string"||typeof e=="string"?(r=dr(r),e=dr(e)):(r=gr(r),e=gr(e)),u=t(r,e)}return u}}function Gr(t){return ar(function(n){return n=1==n.length&&Fi(n[0])?l(n[0],S(fe())):l(On(n,1),S(fe())),ar(function(e){var u=this;return t(n,function(t){return r(t,u,e)})})})}function Jr(t,n){n=n===N?" ":dr(n);var r=n.length;return 2>r?r?cr(n,t):n:(r=cr(n,ao(t/F(n))),
Ct.test(n)?Or(r.match(Bt),0,t).join(""):r.slice(0,t))}function Yr(t,n,e,u){function o(){for(var n=-1,c=arguments.length,a=-1,l=u.length,s=Cu(l+c),h=this&&this!==qt&&this instanceof o?f:t;++a<l;)s[a]=u[a];for(;c--;)s[a++]=arguments[++n];return r(h,i?e:this,s)}var i=1&n,f=Tr(t);return o}function Hr(t){return function(n,r,e){e&&typeof e!="number"&&de(n,r,e)&&(r=e=N),n=su(n),r===N?(r=n,n=0):r=su(r),e=e===N?n<r?1:-1:su(e);var u=-1;r=go(ao((r-n)/(e||1)),0);for(var o=Cu(r);r--;)o[t?r:++u]=n,n+=e;return o;
}}function Qr(t){return function(n,r){return typeof n=="string"&&typeof r=="string"||(n=_u(n),r=_u(r)),t(n,r)}}function Xr(t,n,r,e,u,o,i,f,c,a){var l=8&n,s=l?i:N;i=l?N:i;var h=l?o:N;return o=l?N:o,n=(n|(l?32:64))&~(l?64:32),4&n||(n&=-4),u=[t,n,u,h,s,o,i,f,c,a],r=r.apply(N,u),be(t)&&Xo(r,u),r.placeholder=e,ni(r,t,n)}function te(t){var n=Du[t];return function(t,r){if(t=_u(t),r=yo(hu(r),292)){var e=(gu(t)+"e").split("e"),e=n(e[0]+"e"+(+e[1]+r)),e=(gu(e)+"e").split("e");return+(e[0]+"e"+(+e[1]-r))}return n(t);
}}function ne(t){return function(n){var r=Et(n);return"[object Map]"==r?L(n):"[object Set]"==r?$(n):E(n,t(n))}}function re(t,n,r,e,u,o,i,f){var c=2&n;if(!c&&typeof t!="function")throw new Uu("Expected a function");var a=e?e.length:0;if(a||(n&=-97,e=u=N),i=i===N?i:go(hu(i),0),f=f===N?f:hu(f),a-=u?u.length:0,64&n){var l=e,s=u;e=u=N}var h=c?N:Jo(t);return o=[t,n,r,e,u,l,s,o,i,f],h&&(r=o[1],t=h[1],n=r|t,e=128==t&&8==r||128==t&&256==r&&o[7].length<=h[8]||384==t&&h[7].length<=h[8]&&8==r,131>n||e)&&(1&t&&(o[2]=h[2],
n|=1&r?0:4),(r=h[3])&&(e=o[3],o[3]=e?Ir(e,r,h[4]):r,o[4]=e?z(o[3],"__lodash_placeholder__"):h[4]),(r=h[5])&&(e=o[5],o[5]=e?Rr(e,r,h[6]):r,o[6]=e?z(o[5],"__lodash_placeholder__"):h[6]),(r=h[7])&&(o[7]=r),128&t&&(o[8]=null==o[8]?h[8]:yo(o[8],h[8])),null==o[9]&&(o[9]=h[9]),o[0]=h[0],o[1]=n),t=o[0],n=o[1],r=o[2],e=o[3],u=o[4],f=o[9]=null==o[9]?c?0:t.length:go(o[9]-a,0),!f&&24&n&&(n&=-25),ni((h?Vo:Xo)(n&&1!=n?8==n||16==n?Nr(t,n,f):32!=n&&33!=n||u.length?qr.apply(N,o):Yr(t,n,r,e):Ur(t,n,r),o),t,n)}function ee(t,n,r,e,u,o){
var i=2&u,f=t.length,c=n.length;if(f!=c&&!(i&&c>f))return false;if((c=o.get(t))&&o.get(n))return c==n;var c=-1,a=true,l=1&u?new Kt:N;for(o.set(t,n),o.set(n,t);++c<f;){var s=t[c],h=n[c];if(e)var p=i?e(h,s,c,n,t,o):e(s,h,c,t,n,o);if(p!==N){if(p)continue;a=false;break}if(l){if(!_(n,function(t,n){if(!l.has(n)&&(s===t||r(s,t,e,u,o)))return l.add(n)})){a=false;break}}else if(s!==h&&!r(s,h,e,u,o)){a=false;break}}return o.delete(t),o.delete(n),a}function ue(t,n,r,e,u,o,i){switch(r){case"[object DataView]":if(t.byteLength!=n.byteLength||t.byteOffset!=n.byteOffset)break;
t=t.buffer,n=n.buffer;case"[object ArrayBuffer]":if(t.byteLength!=n.byteLength||!e(new Xu(t),new Xu(n)))break;return true;case"[object Boolean]":case"[object Date]":case"[object Number]":return Ye(+t,+n);case"[object Error]":return t.name==n.name&&t.message==n.message;case"[object RegExp]":case"[object String]":return t==n+"";case"[object Map]":var f=L;case"[object Set]":if(f||(f=U),t.size!=n.size&&!(2&o))break;return(r=i.get(t))?r==n:(o|=1,i.set(t,n),n=ee(f(t),f(n),e,u,o,i),i.delete(t),n);case"[object Symbol]":
if(Fo)return Fo.call(t)==Fo.call(n)}return false}function oe(t){for(var n=t.name+"",r=Mo[n],e=qu.call(Mo,n)?r.length:0;e--;){var u=r[e],o=u.func;if(null==o||o==t)return u.name}return n}function ie(t){return(qu.call(St,"placeholder")?St:t).placeholder}function fe(){var t=St.iteratee||Su,t=t===Su?Vn:t;return arguments.length?t(arguments[0],arguments[1]):t}function ce(t,n){var r=t.__data__,e=typeof n;return("string"==e||"number"==e||"symbol"==e||"boolean"==e?"__proto__"!==n:null===n)?r[typeof n=="string"?"string":"hash"]:r.map;
}function ae(t){for(var n=bu(t),r=n.length;r--;){var e=n[r],u=t[e];n[r]=[e,u,u===u&&!uu(u)]}return n}function le(t,n){var r=null==t?N:t[n];return Nn(r)?r:N}function se(t,n,r){n=ye(n,t)?[n]:Ar(n);for(var e,u=-1,o=n.length;++u<o;){var i=Ae(n[u]);if(!(e=null!=t&&r(t,i)))break;t=t[i]}return e?e:(o=t?t.length:0,!!o&&eu(o)&&ge(i,o)&&(Fi(t)||cu(t)||He(t)))}function he(t){var n=t.length,r=t.constructor(n);return n&&"string"==typeof t[0]&&qu.call(t,"index")&&(r.index=t.index,r.input=t.input),r}function pe(t){
return typeof t.constructor!="function"||xe(t)?{}:bn(to(t))}function _e(r,e,u,o){var i=r.constructor;switch(e){case"[object ArrayBuffer]":return Er(r);case"[object Boolean]":case"[object Date]":return new i((+r));case"[object DataView]":return e=o?Er(r.buffer):r.buffer,new r.constructor(e,r.byteOffset,r.byteLength);case"[object Float32Array]":case"[object Float64Array]":case"[object Int8Array]":case"[object Int16Array]":case"[object Int32Array]":case"[object Uint8Array]":case"[object Uint8ClampedArray]":
case"[object Uint16Array]":case"[object Uint32Array]":return e=o?Er(r.buffer):r.buffer,new r.constructor(e,r.byteOffset,r.length);case"[object Map]":return e=o?u(L(r),true):L(r),h(e,t,new r.constructor);case"[object Number]":case"[object String]":return new i(r);case"[object RegExp]":return e=new r.constructor(r.source,dt.exec(r)),e.lastIndex=r.lastIndex,e;case"[object Set]":return e=o?u(U(r),true):U(r),h(e,n,new r.constructor);case"[object Symbol]":return Fo?Object(Fo.call(r)):{}}}function ve(t){return Fi(t)||He(t)||!!(oo&&t&&t[oo]);
}function ge(t,n){return n=null==n?9007199254740991:n,!!n&&(typeof t=="number"||wt.test(t))&&-1<t&&0==t%1&&t<n}function de(t,n,r){if(!uu(r))return false;var e=typeof n;return!!("number"==e?Qe(r)&&ge(n,r.length):"string"==e&&n in r)&&Ye(r[n],t)}function ye(t,n){if(Fi(t))return false;var r=typeof t;return!("number"!=r&&"symbol"!=r&&"boolean"!=r&&null!=t&&!au(t))||(et.test(t)||!rt.test(t)||null!=n&&t in Object(n))}function be(t){var n=oe(t),r=St[n];return typeof r=="function"&&n in Ft.prototype&&(t===r||(n=Jo(r),
!!n&&t===n[0]))}function xe(t){var n=t&&t.constructor;return t===(typeof n=="function"&&n.prototype||Fu)}function je(t,n){return function(r){return null!=r&&(r[t]===n&&(n!==N||t in Object(r)))}}function me(t,n,r,e,u,o){return uu(t)&&uu(n)&&(o.set(n,t),Xn(t,n,N,me,o),o.delete(n)),t}function we(t,n){return 1==n.length?t:In(t,sr(n,0,-1))}function Ae(t){if(typeof t=="string"||au(t))return t;var n=t+"";return"0"==n&&1/t==-P?"-0":n}function Oe(t){if(null!=t){try{return Zu.call(t)}catch(t){}return t+""}
return""}function ke(t,n){return u(q,function(r){var e="_."+r[0];n&r[1]&&!c(t,e)&&t.push(e)}),t.sort()}function Ee(t){if(t instanceof Ft)return t.clone();var n=new $t(t.__wrapped__,t.__chain__);return n.__actions__=Wr(t.__actions__),n.__index__=t.__index__,n.__values__=t.__values__,n}function Se(t,n,r){var e=t?t.length:0;return e?(r=null==r?0:hu(r),0>r&&(r=go(e+r,0)),g(t,fe(n,3),r)):-1}function Ie(t,n,r){var e=t?t.length:0;if(!e)return-1;var u=e-1;return r!==N&&(u=hu(r),u=0>r?go(e+u,0):yo(u,e-1)),
g(t,fe(n,3),u,true)}function Re(t){return t&&t.length?t[0]:N}function We(t){var n=t?t.length:0;return n?t[n-1]:N}function Be(t,n){return t&&t.length&&n&&n.length?or(t,n):t}function Me(t){return t?mo.call(t):t}function Ce(t){if(!t||!t.length)return[];var n=0;return t=f(t,function(t){if(Xe(t))return n=go(t.length,n),true}),k(n,function(n){return l(t,j(n))})}function Le(t,n){if(!t||!t.length)return[];var e=Ce(t);return null==n?e:l(e,function(t){return r(n,N,t)})}function De(t){return t=St(t),t.__chain__=true,
t}function ze(t,n){return n(t)}function Ue(){return this}function $e(t,n){return(Fi(t)?u:No)(t,fe(n,3))}function Fe(t,n){return(Fi(t)?o:Po)(t,fe(n,3))}function Te(t,n){return(Fi(t)?l:Yn)(t,fe(n,3))}function Ne(t,n,r){var e=-1,u=lu(t),o=u.length,i=o-1;for(n=(r?de(t,n,r):n===N)?1:vn(hu(n),0,o);++e<n;)t=fr(e,i),r=u[t],u[t]=u[e],u[e]=r;return u.length=n,u}function Pe(t,n,r){return n=r?N:n,n=t&&null==n?t.length:n,re(t,128,N,N,N,N,n)}function Ze(t,n){var r;if(typeof n!="function")throw new Uu("Expected a function");
return t=hu(t),function(){return 0<--t&&(r=n.apply(this,arguments)),1>=t&&(n=N),r}}function qe(t,n,r){return n=r?N:n,t=re(t,8,N,N,N,N,N,n),t.placeholder=qe.placeholder,t}function Ve(t,n,r){return n=r?N:n,t=re(t,16,N,N,N,N,N,n),t.placeholder=Ve.placeholder,t}function Ke(t,n,r){function e(n){var r=c,e=a;return c=a=N,_=n,s=t.apply(e,r)}function u(t){var r=t-p;return t-=_,p===N||r>=n||0>r||g&&t>=l}function o(){var t=Ii();if(u(t))return i(t);var r,e=ti;r=t-_,t=n-(t-p),r=g?yo(t,l-r):t,h=e(o,r)}function i(t){
return h=N,d&&c?e(t):(c=a=N,s)}function f(){var t=Ii(),r=u(t);if(c=arguments,a=this,p=t,r){if(h===N)return _=t=p,h=ti(o,n),v?e(t):s;if(g)return h=ti(o,n),e(p)}return h===N&&(h=ti(o,n)),s}var c,a,l,s,h,p,_=0,v=false,g=false,d=true;if(typeof t!="function")throw new Uu("Expected a function");return n=_u(n)||0,uu(r)&&(v=!!r.leading,l=(g="maxWait"in r)?go(_u(r.maxWait)||0,n):l,d="trailing"in r?!!r.trailing:d),f.cancel=function(){h!==N&&Ko(h),_=0,c=p=a=h=N},f.flush=function(){return h===N?s:i(Ii())},f}function Ge(t,n){
function r(){var e=arguments,u=n?n.apply(this,e):e[0],o=r.cache;return o.has(u)?o.get(u):(e=t.apply(this,e),r.cache=o.set(u,e),e)}if(typeof t!="function"||n&&typeof n!="function")throw new Uu("Expected a function");return r.cache=new(Ge.Cache||Vt),r}function Je(t){if(typeof t!="function")throw new Uu("Expected a function");return function(){var n=arguments;switch(n.length){case 0:return!t.call(this);case 1:return!t.call(this,n[0]);case 2:return!t.call(this,n[0],n[1]);case 3:return!t.call(this,n[0],n[1],n[2]);
}return!t.apply(this,n)}}function Ye(t,n){return t===n||t!==t&&n!==n}function He(t){return Xe(t)&&qu.call(t,"callee")&&(!eo.call(t,"callee")||"[object Arguments]"==Gu.call(t))}function Qe(t){return null!=t&&eu(t.length)&&!nu(t)}function Xe(t){return ou(t)&&Qe(t)}function tu(t){return!!ou(t)&&("[object Error]"==Gu.call(t)||typeof t.message=="string"&&typeof t.name=="string")}function nu(t){return t=uu(t)?Gu.call(t):"","[object Function]"==t||"[object GeneratorFunction]"==t}function ru(t){return typeof t=="number"&&t==hu(t);
}function eu(t){return typeof t=="number"&&-1<t&&0==t%1&&9007199254740991>=t}function uu(t){var n=typeof t;return!!t&&("object"==n||"function"==n)}function ou(t){return!!t&&typeof t=="object"}function iu(t){return typeof t=="number"||ou(t)&&"[object Number]"==Gu.call(t)}function fu(t){return!(!ou(t)||"[object Object]"!=Gu.call(t)||C(t))&&(t=to(t),null===t||(t=qu.call(t,"constructor")&&t.constructor,typeof t=="function"&&t instanceof t&&Zu.call(t)==Ku))}function cu(t){return typeof t=="string"||!Fi(t)&&ou(t)&&"[object String]"==Gu.call(t);
}function au(t){return typeof t=="symbol"||ou(t)&&"[object Symbol]"==Gu.call(t)}function lu(t){if(!t)return[];if(Qe(t))return cu(t)?t.match(Bt):Wr(t);if(no&&t[no]){t=t[no]();for(var n,r=[];!(n=t.next()).done;)r.push(n.value);return r}return n=Et(t),("[object Map]"==n?L:"[object Set]"==n?U:mu)(t)}function su(t){return t?(t=_u(t),t===P||t===-P?1.7976931348623157e308*(0>t?-1:1):t===t?t:0):0===t?t:0}function hu(t){t=su(t);var n=t%1;return t===t?n?t-n:t:0}function pu(t){return t?vn(hu(t),0,4294967295):0;
}function _u(t){if(typeof t=="number")return t;if(au(t))return Z;if(uu(t)&&(t=nu(t.valueOf)?t.valueOf():t,t=uu(t)?t+"":t),typeof t!="string")return 0===t?t:+t;t=t.replace(ct,"");var n=xt.test(t);return n||mt.test(t)?Nt(t.slice(2),n?2:8):bt.test(t)?Z:+t}function vu(t){return Br(t,xu(t))}function gu(t){return null==t?"":dr(t)}function du(t,n,r){return t=null==t?N:In(t,n),t===N?r:t}function yu(t,n){return null!=t&&se(t,n,Mn)}function bu(t){return Qe(t)?fn(t):Kn(t)}function xu(t){return Qe(t)?fn(t,true):Gn(t);
}function ju(t,n){return null==t?{}:er(t,Rn(t,xu,Ho),fe(n))}function mu(t){return t?I(t,bu(t)):[]}function wu(t){return xf(gu(t).toLowerCase())}function Au(t){return(t=gu(t))&&t.replace(At,rn).replace(Wt,"")}function Ou(t,n,r){return t=gu(t),n=r?N:n,n===N&&(n=Lt.test(t)?Mt:_t),t.match(n)||[]}function ku(t){return function(){return t}}function Eu(t){return t}function Su(t){return Vn(typeof t=="function"?t:gn(t,true))}function Iu(t,n,r){var e=bu(n),o=Sn(n,e);null!=r||uu(n)&&(o.length||!e.length)||(r=n,
n=t,t=this,o=Sn(n,bu(n)));var i=!(uu(r)&&"chain"in r&&!r.chain),f=nu(t);return u(o,function(r){var e=n[r];t[r]=e,f&&(t.prototype[r]=function(){var n=this.__chain__;if(i||n){var r=t(this.__wrapped__);return(r.__actions__=Wr(this.__actions__)).push({func:e,args:arguments,thisArg:t}),r.__chain__=n,r}return e.apply(t,s([this.value()],arguments))})}),t}function Ru(){}function Wu(t){return ye(t)?j(Ae(t)):ur(t)}function Bu(){return[]}function Mu(){return false}m=m?on.defaults({},m,on.pick(qt,Dt)):qt;var Cu=m.Array,Lu=m.Error,Du=m.Math,zu=m.RegExp,Uu=m.TypeError,$u=m.Array.prototype,Fu=m.Object.prototype,Tu=m.String.prototype,Nu=m["__core-js_shared__"],Pu=function(){
var t=/[^.]+$/.exec(Nu&&Nu.keys&&Nu.keys.IE_PROTO||"");return t?"Symbol(src)_1."+t:""}(),Zu=m.Function.prototype.toString,qu=Fu.hasOwnProperty,Vu=0,Ku=Zu.call(Object),Gu=Fu.toString,Ju=qt._,Yu=zu("^"+Zu.call(qu).replace(it,"\\$&").replace(/hasOwnProperty|(function).*?(?=\\\()| for .+?(?=\\\])/g,"$1.*?")+"$"),Hu=Gt?m.Buffer:N,Qu=m.Symbol,Xu=m.Uint8Array,to=D(Object.getPrototypeOf),no=Qu?Qu.iterator:N,ro=m.Object.create,eo=Fu.propertyIsEnumerable,uo=$u.splice,oo=Qu?Qu.isConcatSpreadable:N,io=m.clearTimeout!==qt.clearTimeout&&m.clearTimeout,fo=m.Date&&m.Date.now!==qt.Date.now&&m.Date.now,co=m.setTimeout!==qt.setTimeout&&m.setTimeout,ao=Du.ceil,lo=Du.floor,so=Object.getOwnPropertySymbols,ho=Hu?Hu.isBuffer:N,po=m.isFinite,_o=$u.join,vo=D(Object.keys),go=Du.max,yo=Du.min,bo=m.parseInt,xo=Du.random,jo=Tu.replace,mo=$u.reverse,wo=Tu.split,Ao=le(m,"DataView"),Oo=le(m,"Map"),ko=le(m,"Promise"),Eo=le(m,"Set"),So=le(m,"WeakMap"),Io=le(m.Object,"create"),Ro=function(){
var t=le(m.Object,"defineProperty"),n=le.name;return n&&2<n.length?t:N}(),Wo=So&&new So,Bo=!eo.call({valueOf:1},"valueOf"),Mo={},Co=Oe(Ao),Lo=Oe(Oo),Do=Oe(ko),zo=Oe(Eo),Uo=Oe(So),$o=Qu?Qu.prototype:N,Fo=$o?$o.valueOf:N,To=$o?$o.toString:N;St.templateSettings={escape:X,evaluate:tt,interpolate:nt,variable:"",imports:{_:St}},St.prototype=It.prototype,St.prototype.constructor=St,$t.prototype=bn(It.prototype),$t.prototype.constructor=$t,Ft.prototype=bn(It.prototype),Ft.prototype.constructor=Ft,Pt.prototype.clear=function(){
this.__data__=Io?Io(null):{}},Pt.prototype.delete=function(t){return this.has(t)&&delete this.__data__[t]},Pt.prototype.get=function(t){var n=this.__data__;return Io?(t=n[t],"__lodash_hash_undefined__"===t?N:t):qu.call(n,t)?n[t]:N},Pt.prototype.has=function(t){var n=this.__data__;return Io?n[t]!==N:qu.call(n,t)},Pt.prototype.set=function(t,n){return this.__data__[t]=Io&&n===N?"__lodash_hash_undefined__":n,this},Zt.prototype.clear=function(){this.__data__=[]},Zt.prototype.delete=function(t){var n=this.__data__;
return t=sn(n,t),!(0>t)&&(t==n.length-1?n.pop():uo.call(n,t,1),true)},Zt.prototype.get=function(t){var n=this.__data__;return t=sn(n,t),0>t?N:n[t][1]},Zt.prototype.has=function(t){return-1<sn(this.__data__,t)},Zt.prototype.set=function(t,n){var r=this.__data__,e=sn(r,t);return 0>e?r.push([t,n]):r[e][1]=n,this},Vt.prototype.clear=function(){this.__data__={hash:new Pt,map:new(Oo||Zt),string:new Pt}},Vt.prototype.delete=function(t){return ce(this,t).delete(t)},Vt.prototype.get=function(t){return ce(this,t).get(t);
},Vt.prototype.has=function(t){return ce(this,t).has(t)},Vt.prototype.set=function(t,n){return ce(this,t).set(t,n),this},Kt.prototype.add=Kt.prototype.push=function(t){return this.__data__.set(t,"__lodash_hash_undefined__"),this},Kt.prototype.has=function(t){return this.__data__.has(t)},Jt.prototype.clear=function(){this.__data__=new Zt},Jt.prototype.delete=function(t){return this.__data__.delete(t)},Jt.prototype.get=function(t){return this.__data__.get(t)},Jt.prototype.has=function(t){return this.__data__.has(t);
},Jt.prototype.set=function(t,n){var r=this.__data__;if(r instanceof Zt){if(r=r.__data__,!Oo||199>r.length)return r.push([t,n]),this;r=this.__data__=new Vt(r)}return r.set(t,n),this};var No=Dr(kn),Po=Dr(En,true),Zo=zr(),qo=zr(true),Vo=Wo?function(t,n){return Wo.set(t,n),t}:Eu,Ko=io||function(t){return qt.clearTimeout(t)},Go=Eo&&1/U(new Eo([,-0]))[1]==P?function(t){return new Eo(t)}:Ru,Jo=Wo?function(t){return Wo.get(t)}:Ru,Yo=so?D(so):Bu,Ho=so?function(t){for(var n=[];t;)s(n,Yo(t)),t=to(t);return n}:Bu;
(Ao&&"[object DataView]"!=Et(new Ao(new ArrayBuffer(1)))||Oo&&"[object Map]"!=Et(new Oo)||ko&&"[object Promise]"!=Et(ko.resolve())||Eo&&"[object Set]"!=Et(new Eo)||So&&"[object WeakMap]"!=Et(new So))&&(Et=function(t){var n=Gu.call(t);if(t=(t="[object Object]"==n?t.constructor:N)?Oe(t):N)switch(t){case Co:return"[object DataView]";case Lo:return"[object Map]";case Do:return"[object Promise]";case zo:return"[object Set]";case Uo:return"[object WeakMap]"}return n});var Qo=Nu?nu:Mu,Xo=function(){var t=0,n=0;
return function(r,e){var u=Ii(),o=16-(u-n);if(n=u,0<o){if(150<=++t)return r}else t=0;return Vo(r,e)}}(),ti=co||function(t,n){return qt.setTimeout(t,n)},ni=Ro?function(t,n,r){n+="";var e;e=(e=n.match(ht))?e[1].split(pt):[],r=ke(e,r),e=r.length;var u=e-1;return r[u]=(1<e?"& ":"")+r[u],r=r.join(2<e?", ":" "),n=n.replace(st,"{\n/* [wrapped with "+r+"] */\n"),Ro(t,"toString",{configurable:true,enumerable:false,value:ku(n)})}:Eu,ri=Ge(function(t){t=gu(t);var n=[];return ut.test(t)&&n.push(""),t.replace(ot,function(t,r,e,u){
n.push(e?u.replace(vt,"$1"):r||t)}),n}),ei=ar(function(t,n){return Xe(t)?jn(t,On(n,1,Xe,true)):[]}),ui=ar(function(t,n){var r=We(n);return Xe(r)&&(r=N),Xe(t)?jn(t,On(n,1,Xe,true),fe(r,2)):[]}),oi=ar(function(t,n){var r=We(n);return Xe(r)&&(r=N),Xe(t)?jn(t,On(n,1,Xe,true),N,r):[]}),ii=ar(function(t){var n=l(t,wr);return n.length&&n[0]===t[0]?Cn(n):[]}),fi=ar(function(t){var n=We(t),r=l(t,wr);return n===We(r)?n=N:r.pop(),r.length&&r[0]===t[0]?Cn(r,fe(n,2)):[]}),ci=ar(function(t){var n=We(t),r=l(t,wr);return n===We(r)?n=N:r.pop(),
r.length&&r[0]===t[0]?Cn(r,N,n):[]}),ai=ar(Be),li=ar(function(t,n){n=On(n,1);var r=t?t.length:0,e=_n(t,n);return ir(t,l(n,function(t){return ge(t,r)?+t:t}).sort(Sr)),e}),si=ar(function(t){return yr(On(t,1,Xe,true))}),hi=ar(function(t){var n=We(t);return Xe(n)&&(n=N),yr(On(t,1,Xe,true),fe(n,2))}),pi=ar(function(t){var n=We(t);return Xe(n)&&(n=N),yr(On(t,1,Xe,true),N,n)}),_i=ar(function(t,n){return Xe(t)?jn(t,n):[]}),vi=ar(function(t){return jr(f(t,Xe))}),gi=ar(function(t){var n=We(t);return Xe(n)&&(n=N),
jr(f(t,Xe),fe(n,2))}),di=ar(function(t){var n=We(t);return Xe(n)&&(n=N),jr(f(t,Xe),N,n)}),yi=ar(Ce),bi=ar(function(t){var n=t.length,n=1<n?t[n-1]:N,n=typeof n=="function"?(t.pop(),n):N;return Le(t,n)}),xi=ar(function(t){function n(n){return _n(n,t)}t=On(t,1);var r=t.length,e=r?t[0]:0,u=this.__wrapped__;return!(1<r||this.__actions__.length)&&u instanceof Ft&&ge(e)?(u=u.slice(e,+e+(r?1:0)),u.__actions__.push({func:ze,args:[n],thisArg:N}),new $t(u,this.__chain__).thru(function(t){return r&&!t.length&&t.push(N),
t})):this.thru(n)}),ji=Cr(function(t,n,r){qu.call(t,r)?++t[r]:t[r]=1}),mi=Pr(Se),wi=Pr(Ie),Ai=Cr(function(t,n,r){qu.call(t,r)?t[r].push(n):t[r]=[n]}),Oi=ar(function(t,n,e){var u=-1,o=typeof n=="function",i=ye(n),f=Qe(t)?Cu(t.length):[];return No(t,function(t){var c=o?n:i&&null!=t?t[n]:N;f[++u]=c?r(c,t,e):Dn(t,n,e)}),f}),ki=Cr(function(t,n,r){t[r]=n}),Ei=Cr(function(t,n,r){t[r?0:1].push(n)},function(){return[[],[]]}),Si=ar(function(t,n){if(null==t)return[];var r=n.length;return 1<r&&de(t,n[0],n[1])?n=[]:2<r&&de(n[0],n[1],n[2])&&(n=[n[0]]),
nr(t,On(n,1),[])}),Ii=fo||function(){return qt.Date.now()},Ri=ar(function(t,n,r){var e=1;if(r.length)var u=z(r,ie(Ri)),e=32|e;return re(t,e,n,r,u)}),Wi=ar(function(t,n,r){var e=3;if(r.length)var u=z(r,ie(Wi)),e=32|e;return re(n,e,t,r,u)}),Bi=ar(function(t,n){return xn(t,1,n)}),Mi=ar(function(t,n,r){return xn(t,_u(n)||0,r)});Ge.Cache=Vt;var Ci=ar(function(t,n){n=1==n.length&&Fi(n[0])?l(n[0],S(fe())):l(On(n,1),S(fe()));var e=n.length;return ar(function(u){for(var o=-1,i=yo(u.length,e);++o<i;)u[o]=n[o].call(this,u[o]);
return r(t,this,u)})}),Li=ar(function(t,n){var r=z(n,ie(Li));return re(t,32,N,n,r)}),Di=ar(function(t,n){var r=z(n,ie(Di));return re(t,64,N,n,r)}),zi=ar(function(t,n){return re(t,256,N,N,N,On(n,1))}),Ui=Qr(Wn),$i=Qr(function(t,n){return t>=n}),Fi=Cu.isArray,Ti=Yt?S(Yt):zn,Ni=ho||Mu,Pi=Ht?S(Ht):Un,Zi=Qt?S(Qt):Fn,qi=Xt?S(Xt):Pn,Vi=tn?S(tn):Zn,Ki=nn?S(nn):qn,Gi=Qr(Jn),Ji=Qr(function(t,n){return t<=n}),Yi=Lr(function(t,n){if(Bo||xe(n)||Qe(n))Br(n,bu(n),t);else for(var r in n)qu.call(n,r)&&ln(t,r,n[r]);
}),Hi=Lr(function(t,n){Br(n,xu(n),t)}),Qi=Lr(function(t,n,r,e){Br(n,xu(n),t,e)}),Xi=Lr(function(t,n,r,e){Br(n,bu(n),t,e)}),tf=ar(function(t,n){return _n(t,On(n,1))}),nf=ar(function(t){return t.push(N,cn),r(Qi,N,t)}),rf=ar(function(t){return t.push(N,me),r(cf,N,t)}),ef=Vr(function(t,n,r){t[n]=r},ku(Eu)),uf=Vr(function(t,n,r){qu.call(t,n)?t[n].push(r):t[n]=[r]},fe),of=ar(Dn),ff=Lr(function(t,n,r){Xn(t,n,r)}),cf=Lr(function(t,n,r,e){Xn(t,n,r,e)}),af=ar(function(t,n){return null==t?{}:(n=l(On(n,1),Ae),
rr(t,jn(Rn(t,xu,Ho),n)))}),lf=ar(function(t,n){return null==t?{}:rr(t,l(On(n,1),Ae))}),sf=ne(bu),hf=ne(xu),pf=Fr(function(t,n,r){return n=n.toLowerCase(),t+(r?wu(n):n)}),_f=Fr(function(t,n,r){return t+(r?"-":"")+n.toLowerCase()}),vf=Fr(function(t,n,r){return t+(r?" ":"")+n.toLowerCase()}),gf=$r("toLowerCase"),df=Fr(function(t,n,r){return t+(r?"_":"")+n.toLowerCase()}),yf=Fr(function(t,n,r){return t+(r?" ":"")+xf(n)}),bf=Fr(function(t,n,r){return t+(r?" ":"")+n.toUpperCase()}),xf=$r("toUpperCase"),jf=ar(function(t,n){
try{return r(t,N,n)}catch(t){return tu(t)?t:new Lu(t)}}),mf=ar(function(t,n){return u(On(n,1),function(n){n=Ae(n),t[n]=Ri(t[n],t)}),t}),wf=Zr(),Af=Zr(true),Of=ar(function(t,n){return function(r){return Dn(r,t,n)}}),kf=ar(function(t,n){return function(r){return Dn(t,r,n)}}),Ef=Gr(l),Sf=Gr(i),If=Gr(_),Rf=Hr(),Wf=Hr(true),Bf=Kr(function(t,n){return t+n},0),Mf=te("ceil"),Cf=Kr(function(t,n){return t/n},1),Lf=te("floor"),Df=Kr(function(t,n){return t*n},1),zf=te("round"),Uf=Kr(function(t,n){return t-n},0);return St.after=function(t,n){
if(typeof n!="function")throw new Uu("Expected a function");return t=hu(t),function(){if(1>--t)return n.apply(this,arguments)}},St.ary=Pe,St.assign=Yi,St.assignIn=Hi,St.assignInWith=Qi,St.assignWith=Xi,St.at=tf,St.before=Ze,St.bind=Ri,St.bindAll=mf,St.bindKey=Wi,St.castArray=function(){if(!arguments.length)return[];var t=arguments[0];return Fi(t)?t:[t]},St.chain=De,St.chunk=function(t,n,r){if(n=(r?de(t,n,r):n===N)?1:go(hu(n),0),r=t?t.length:0,!r||1>n)return[];for(var e=0,u=0,o=Cu(ao(r/n));e<r;)o[u++]=sr(t,e,e+=n);
return o},St.compact=function(t){for(var n=-1,r=t?t.length:0,e=0,u=[];++n<r;){var o=t[n];o&&(u[e++]=o)}return u},St.concat=function(){for(var t=arguments.length,n=Cu(t?t-1:0),r=arguments[0],e=t;e--;)n[e-1]=arguments[e];return t?s(Fi(r)?Wr(r):[r],On(n,1)):[]},St.cond=function(t){var n=t?t.length:0,e=fe();return t=n?l(t,function(t){if("function"!=typeof t[1])throw new Uu("Expected a function");return[e(t[0]),t[1]]}):[],ar(function(e){for(var u=-1;++u<n;){var o=t[u];if(r(o[0],this,e))return r(o[1],this,e);
}})},St.conforms=function(t){return dn(gn(t,true))},St.constant=ku,St.countBy=ji,St.create=function(t,n){var r=bn(t);return n?pn(r,n):r},St.curry=qe,St.curryRight=Ve,St.debounce=Ke,St.defaults=nf,St.defaultsDeep=rf,St.defer=Bi,St.delay=Mi,St.difference=ei,St.differenceBy=ui,St.differenceWith=oi,St.drop=function(t,n,r){var e=t?t.length:0;return e?(n=r||n===N?1:hu(n),sr(t,0>n?0:n,e)):[]},St.dropRight=function(t,n,r){var e=t?t.length:0;return e?(n=r||n===N?1:hu(n),n=e-n,sr(t,0,0>n?0:n)):[]},St.dropRightWhile=function(t,n){
return t&&t.length?br(t,fe(n,3),true,true):[]},St.dropWhile=function(t,n){return t&&t.length?br(t,fe(n,3),true):[]},St.fill=function(t,n,r,e){var u=t?t.length:0;if(!u)return[];for(r&&typeof r!="number"&&de(t,n,r)&&(r=0,e=u),u=t.length,r=hu(r),0>r&&(r=-r>u?0:u+r),e=e===N||e>u?u:hu(e),0>e&&(e+=u),e=r>e?0:pu(e);r<e;)t[r++]=n;return t},St.filter=function(t,n){return(Fi(t)?f:An)(t,fe(n,3))},St.flatMap=function(t,n){return On(Te(t,n),1)},St.flatMapDeep=function(t,n){return On(Te(t,n),P)},St.flatMapDepth=function(t,n,r){
return r=r===N?1:hu(r),On(Te(t,n),r)},St.flatten=function(t){return t&&t.length?On(t,1):[]},St.flattenDeep=function(t){return t&&t.length?On(t,P):[]},St.flattenDepth=function(t,n){return t&&t.length?(n=n===N?1:hu(n),On(t,n)):[]},St.flip=function(t){return re(t,512)},St.flow=wf,St.flowRight=Af,St.fromPairs=function(t){for(var n=-1,r=t?t.length:0,e={};++n<r;){var u=t[n];e[u[0]]=u[1]}return e},St.functions=function(t){return null==t?[]:Sn(t,bu(t))},St.functionsIn=function(t){return null==t?[]:Sn(t,xu(t));
},St.groupBy=Ai,St.initial=function(t){return t&&t.length?sr(t,0,-1):[]},St.intersection=ii,St.intersectionBy=fi,St.intersectionWith=ci,St.invert=ef,St.invertBy=uf,St.invokeMap=Oi,St.iteratee=Su,St.keyBy=ki,St.keys=bu,St.keysIn=xu,St.map=Te,St.mapKeys=function(t,n){var r={};return n=fe(n,3),kn(t,function(t,e,u){r[n(t,e,u)]=t}),r},St.mapValues=function(t,n){var r={};return n=fe(n,3),kn(t,function(t,e,u){r[e]=n(t,e,u)}),r},St.matches=function(t){return Hn(gn(t,true))},St.matchesProperty=function(t,n){
return Qn(t,gn(n,true))},St.memoize=Ge,St.merge=ff,St.mergeWith=cf,St.method=Of,St.methodOf=kf,St.mixin=Iu,St.negate=Je,St.nthArg=function(t){return t=hu(t),ar(function(n){return tr(n,t)})},St.omit=af,St.omitBy=function(t,n){return ju(t,Je(fe(n)))},St.once=function(t){return Ze(2,t)},St.orderBy=function(t,n,r,e){return null==t?[]:(Fi(n)||(n=null==n?[]:[n]),r=e?N:r,Fi(r)||(r=null==r?[]:[r]),nr(t,n,r))},St.over=Ef,St.overArgs=Ci,St.overEvery=Sf,St.overSome=If,St.partial=Li,St.partialRight=Di,St.partition=Ei,
St.pick=lf,St.pickBy=ju,St.property=Wu,St.propertyOf=function(t){return function(n){return null==t?N:In(t,n)}},St.pull=ai,St.pullAll=Be,St.pullAllBy=function(t,n,r){return t&&t.length&&n&&n.length?or(t,n,fe(r,2)):t},St.pullAllWith=function(t,n,r){return t&&t.length&&n&&n.length?or(t,n,N,r):t},St.pullAt=li,St.range=Rf,St.rangeRight=Wf,St.rearg=zi,St.reject=function(t,n){return(Fi(t)?f:An)(t,Je(fe(n,3)))},St.remove=function(t,n){var r=[];if(!t||!t.length)return r;var e=-1,u=[],o=t.length;for(n=fe(n,3);++e<o;){
var i=t[e];n(i,e,t)&&(r.push(i),u.push(e))}return ir(t,u),r},St.rest=function(t,n){if(typeof t!="function")throw new Uu("Expected a function");return n=n===N?n:hu(n),ar(t,n)},St.reverse=Me,St.sampleSize=Ne,St.set=function(t,n,r){return null==t?t:lr(t,n,r)},St.setWith=function(t,n,r,e){return e=typeof e=="function"?e:N,null==t?t:lr(t,n,r,e)},St.shuffle=function(t){return Ne(t,4294967295)},St.slice=function(t,n,r){var e=t?t.length:0;return e?(r&&typeof r!="number"&&de(t,n,r)?(n=0,r=e):(n=null==n?0:hu(n),
r=r===N?e:hu(r)),sr(t,n,r)):[]},St.sortBy=Si,St.sortedUniq=function(t){return t&&t.length?vr(t):[]},St.sortedUniqBy=function(t,n){return t&&t.length?vr(t,fe(n,2)):[]},St.split=function(t,n,r){return r&&typeof r!="number"&&de(t,n,r)&&(n=r=N),r=r===N?4294967295:r>>>0,r?(t=gu(t))&&(typeof n=="string"||null!=n&&!qi(n))&&(n=dr(n),""==n&&Ct.test(t))?Or(t.match(Bt),0,r):wo.call(t,n,r):[]},St.spread=function(t,n){if(typeof t!="function")throw new Uu("Expected a function");return n=n===N?0:go(hu(n),0),ar(function(e){
var u=e[n];return e=Or(e,0,n),u&&s(e,u),r(t,this,e)})},St.tail=function(t){var n=t?t.length:0;return n?sr(t,1,n):[]},St.take=function(t,n,r){return t&&t.length?(n=r||n===N?1:hu(n),sr(t,0,0>n?0:n)):[]},St.takeRight=function(t,n,r){var e=t?t.length:0;return e?(n=r||n===N?1:hu(n),n=e-n,sr(t,0>n?0:n,e)):[]},St.takeRightWhile=function(t,n){return t&&t.length?br(t,fe(n,3),false,true):[]},St.takeWhile=function(t,n){return t&&t.length?br(t,fe(n,3)):[]},St.tap=function(t,n){return n(t),t},St.throttle=function(t,n,r){
var e=true,u=true;if(typeof t!="function")throw new Uu("Expected a function");return uu(r)&&(e="leading"in r?!!r.leading:e,u="trailing"in r?!!r.trailing:u),Ke(t,n,{leading:e,maxWait:n,trailing:u})},St.thru=ze,St.toArray=lu,St.toPairs=sf,St.toPairsIn=hf,St.toPath=function(t){return Fi(t)?l(t,Ae):au(t)?[t]:Wr(ri(t))},St.toPlainObject=vu,St.transform=function(t,n,r){var e=Fi(t)||Ki(t);if(n=fe(n,4),null==r)if(e||uu(t)){var o=t.constructor;r=e?Fi(t)?new o:[]:nu(o)?bn(to(t)):{}}else r={};return(e?u:kn)(t,function(t,e,u){
return n(r,t,e,u)}),r},St.unary=function(t){return Pe(t,1)},St.union=si,St.unionBy=hi,St.unionWith=pi,St.uniq=function(t){return t&&t.length?yr(t):[]},St.uniqBy=function(t,n){return t&&t.length?yr(t,fe(n,2)):[]},St.uniqWith=function(t,n){return t&&t.length?yr(t,N,n):[]},St.unset=function(t,n){var r;if(null==t)r=true;else{r=t;var e=n,e=ye(e,r)?[e]:Ar(e);r=we(r,e),e=Ae(We(e)),r=!(null!=r&&qu.call(r,e))||delete r[e]}return r},St.unzip=Ce,St.unzipWith=Le,St.update=function(t,n,r){return null==t?t:lr(t,n,(typeof r=="function"?r:Eu)(In(t,n)),void 0);
},St.updateWith=function(t,n,r,e){return e=typeof e=="function"?e:N,null!=t&&(t=lr(t,n,(typeof r=="function"?r:Eu)(In(t,n)),e)),t},St.values=mu,St.valuesIn=function(t){return null==t?[]:I(t,xu(t))},St.without=_i,St.words=Ou,St.wrap=function(t,n){return n=null==n?Eu:n,Li(n,t)},St.xor=vi,St.xorBy=gi,St.xorWith=di,St.zip=yi,St.zipObject=function(t,n){return mr(t||[],n||[],ln)},St.zipObjectDeep=function(t,n){return mr(t||[],n||[],lr)},St.zipWith=bi,St.entries=sf,St.entriesIn=hf,St.extend=Hi,St.extendWith=Qi,
Iu(St,St),St.add=Bf,St.attempt=jf,St.camelCase=pf,St.capitalize=wu,St.ceil=Mf,St.clamp=function(t,n,r){return r===N&&(r=n,n=N),r!==N&&(r=_u(r),r=r===r?r:0),n!==N&&(n=_u(n),n=n===n?n:0),vn(_u(t),n,r)},St.clone=function(t){return gn(t,false,true)},St.cloneDeep=function(t){return gn(t,true,true)},St.cloneDeepWith=function(t,n){return gn(t,true,true,n)},St.cloneWith=function(t,n){return gn(t,false,true,n)},St.conformsTo=function(t,n){return null==n||yn(t,n,bu(n))},St.deburr=Au,St.defaultTo=function(t,n){return null==t||t!==t?n:t;
},St.divide=Cf,St.endsWith=function(t,n,r){t=gu(t),n=dr(n);var e=t.length,e=r=r===N?e:vn(hu(r),0,e);return r-=n.length,0<=r&&t.slice(r,e)==n},St.eq=Ye,St.escape=function(t){return(t=gu(t))&&Q.test(t)?t.replace(Y,en):t},St.escapeRegExp=function(t){return(t=gu(t))&&ft.test(t)?t.replace(it,"\\$&"):t},St.every=function(t,n,r){var e=Fi(t)?i:mn;return r&&de(t,n,r)&&(n=N),e(t,fe(n,3))},St.find=mi,St.findIndex=Se,St.findKey=function(t,n){return v(t,fe(n,3),kn)},St.findLast=wi,St.findLastIndex=Ie,St.findLastKey=function(t,n){
return v(t,fe(n,3),En)},St.floor=Lf,St.forEach=$e,St.forEachRight=Fe,St.forIn=function(t,n){return null==t?t:Zo(t,fe(n,3),xu)},St.forInRight=function(t,n){return null==t?t:qo(t,fe(n,3),xu)},St.forOwn=function(t,n){return t&&kn(t,fe(n,3))},St.forOwnRight=function(t,n){return t&&En(t,fe(n,3))},St.get=du,St.gt=Ui,St.gte=$i,St.has=function(t,n){return null!=t&&se(t,n,Bn)},St.hasIn=yu,St.head=Re,St.identity=Eu,St.includes=function(t,n,r,e){return t=Qe(t)?t:mu(t),r=r&&!e?hu(r):0,e=t.length,0>r&&(r=go(e+r,0)),
cu(t)?r<=e&&-1<t.indexOf(n,r):!!e&&-1<d(t,n,r)},St.indexOf=function(t,n,r){var e=t?t.length:0;return e?(r=null==r?0:hu(r),0>r&&(r=go(e+r,0)),d(t,n,r)):-1},St.inRange=function(t,n,r){return n=su(n),r===N?(r=n,n=0):r=su(r),t=_u(t),t>=yo(n,r)&&t<go(n,r)},St.invoke=of,St.isArguments=He,St.isArray=Fi,St.isArrayBuffer=Ti,St.isArrayLike=Qe,St.isArrayLikeObject=Xe,St.isBoolean=function(t){return true===t||false===t||ou(t)&&"[object Boolean]"==Gu.call(t)},St.isBuffer=Ni,St.isDate=Pi,St.isElement=function(t){return!!t&&1===t.nodeType&&ou(t)&&!fu(t);
},St.isEmpty=function(t){if(Qe(t)&&(Fi(t)||cu(t)||nu(t.splice)||He(t)||Ni(t)))return!t.length;if(ou(t)){var n=Et(t);if("[object Map]"==n||"[object Set]"==n)return!t.size}var r,n=xe(t);for(r in t)if(qu.call(t,r)&&(!n||"constructor"!=r))return false;return!(Bo&&vo(t).length)},St.isEqual=function(t,n){return $n(t,n)},St.isEqualWith=function(t,n,r){var e=(r=typeof r=="function"?r:N)?r(t,n):N;return e===N?$n(t,n,r):!!e},St.isError=tu,St.isFinite=function(t){return typeof t=="number"&&po(t)},St.isFunction=nu,
St.isInteger=ru,St.isLength=eu,St.isMap=Zi,St.isMatch=function(t,n){return t===n||Tn(t,n,ae(n))},St.isMatchWith=function(t,n,r){return r=typeof r=="function"?r:N,Tn(t,n,ae(n),r)},St.isNaN=function(t){return iu(t)&&t!=+t},St.isNative=function(t){if(Qo(t))throw new Lu("This method is not supported with core-js. Try https://github.com/es-shims.");return Nn(t)},St.isNil=function(t){return null==t},St.isNull=function(t){return null===t},St.isNumber=iu,St.isObject=uu,St.isObjectLike=ou,St.isPlainObject=fu,
St.isRegExp=qi,St.isSafeInteger=function(t){return ru(t)&&-9007199254740991<=t&&9007199254740991>=t},St.isSet=Vi,St.isString=cu,St.isSymbol=au,St.isTypedArray=Ki,St.isUndefined=function(t){return t===N},St.isWeakMap=function(t){return ou(t)&&"[object WeakMap]"==Et(t)},St.isWeakSet=function(t){return ou(t)&&"[object WeakSet]"==Gu.call(t)},St.join=function(t,n){return t?_o.call(t,n):""},St.kebabCase=_f,St.last=We,St.lastIndexOf=function(t,n,r){var e=t?t.length:0;if(!e)return-1;var u=e;if(r!==N&&(u=hu(r),
u=(0>u?go(e+u,0):yo(u,e-1))+1),n!==n)return g(t,b,u-1,true);for(;u--;)if(t[u]===n)return u;return-1},St.lowerCase=vf,St.lowerFirst=gf,St.lt=Gi,St.lte=Ji,St.max=function(t){return t&&t.length?wn(t,Eu,Wn):N},St.maxBy=function(t,n){return t&&t.length?wn(t,fe(n,2),Wn):N},St.mean=function(t){return x(t,Eu)},St.meanBy=function(t,n){return x(t,fe(n,2))},St.min=function(t){return t&&t.length?wn(t,Eu,Jn):N},St.minBy=function(t,n){return t&&t.length?wn(t,fe(n,2),Jn):N},St.stubArray=Bu,St.stubFalse=Mu,St.stubObject=function(){
return{}},St.stubString=function(){return""},St.stubTrue=function(){return true},St.multiply=Df,St.nth=function(t,n){return t&&t.length?tr(t,hu(n)):N},St.noConflict=function(){return qt._===this&&(qt._=Ju),this},St.noop=Ru,St.now=Ii,St.pad=function(t,n,r){t=gu(t);var e=(n=hu(n))?F(t):0;return!n||e>=n?t:(n=(n-e)/2,Jr(lo(n),r)+t+Jr(ao(n),r))},St.padEnd=function(t,n,r){t=gu(t);var e=(n=hu(n))?F(t):0;return n&&e<n?t+Jr(n-e,r):t},St.padStart=function(t,n,r){t=gu(t);var e=(n=hu(n))?F(t):0;return n&&e<n?Jr(n-e,r)+t:t;
},St.parseInt=function(t,n,r){return r||null==n?n=0:n&&(n=+n),t=gu(t).replace(ct,""),bo(t,n||(yt.test(t)?16:10))},St.random=function(t,n,r){if(r&&typeof r!="boolean"&&de(t,n,r)&&(n=r=N),r===N&&(typeof n=="boolean"?(r=n,n=N):typeof t=="boolean"&&(r=t,t=N)),t===N&&n===N?(t=0,n=1):(t=su(t),n===N?(n=t,t=0):n=su(n)),t>n){var e=t;t=n,n=e}return r||t%1||n%1?(r=xo(),yo(t+r*(n-t+Tt("1e-"+((r+"").length-1))),n)):fr(t,n)},St.reduce=function(t,n,r){var e=Fi(t)?h:w,u=3>arguments.length;return e(t,fe(n,4),r,u,No);
},St.reduceRight=function(t,n,r){var e=Fi(t)?p:w,u=3>arguments.length;return e(t,fe(n,4),r,u,Po)},St.repeat=function(t,n,r){return n=(r?de(t,n,r):n===N)?1:hu(n),cr(gu(t),n)},St.replace=function(){var t=arguments,n=gu(t[0]);return 3>t.length?n:jo.call(n,t[1],t[2])},St.result=function(t,n,r){n=ye(n,t)?[n]:Ar(n);var e=-1,u=n.length;for(u||(t=N,u=1);++e<u;){var o=null==t?N:t[Ae(n[e])];o===N&&(e=u,o=r),t=nu(o)?o.call(t):o}return t},St.round=zf,St.runInContext=T,St.sample=function(t){t=Qe(t)?t:mu(t);var n=t.length;
return 0<n?t[fr(0,n-1)]:N},St.size=function(t){if(null==t)return 0;if(Qe(t)){var n=t.length;return n&&cu(t)?F(t):n}return ou(t)&&(n=Et(t),"[object Map]"==n||"[object Set]"==n)?t.size:Kn(t).length},St.snakeCase=df,St.some=function(t,n,r){var e=Fi(t)?_:hr;return r&&de(t,n,r)&&(n=N),e(t,fe(n,3))},St.sortedIndex=function(t,n){return pr(t,n)},St.sortedIndexBy=function(t,n,r){return _r(t,n,fe(r,2))},St.sortedIndexOf=function(t,n){var r=t?t.length:0;if(r){var e=pr(t,n);if(e<r&&Ye(t[e],n))return e}return-1;
},St.sortedLastIndex=function(t,n){return pr(t,n,true)},St.sortedLastIndexBy=function(t,n,r){return _r(t,n,fe(r,2),true)},St.sortedLastIndexOf=function(t,n){if(t&&t.length){var r=pr(t,n,true)-1;if(Ye(t[r],n))return r}return-1},St.startCase=yf,St.startsWith=function(t,n,r){return t=gu(t),r=vn(hu(r),0,t.length),n=dr(n),t.slice(r,r+n.length)==n},St.subtract=Uf,St.sum=function(t){return t&&t.length?O(t,Eu):0},St.sumBy=function(t,n){return t&&t.length?O(t,fe(n,2)):0},St.template=function(t,n,r){var e=St.templateSettings;
r&&de(t,n,r)&&(n=N),t=gu(t),n=Qi({},n,e,cn),r=Qi({},n.imports,e.imports,cn);var u,o,i=bu(r),f=I(r,i),c=0;r=n.interpolate||Ot;var a="__p+='";r=zu((n.escape||Ot).source+"|"+r.source+"|"+(r===nt?gt:Ot).source+"|"+(n.evaluate||Ot).source+"|$","g");var l="sourceURL"in n?"//# sourceURL="+n.sourceURL+"\n":"";if(t.replace(r,function(n,r,e,i,f,l){return e||(e=i),a+=t.slice(c,l).replace(kt,M),r&&(u=true,a+="'+__e("+r+")+'"),f&&(o=true,a+="';"+f+";\n__p+='"),e&&(a+="'+((__t=("+e+"))==null?'':__t)+'"),c=l+n.length,
n}),a+="';",(n=n.variable)||(a="with(obj){"+a+"}"),a=(o?a.replace(V,""):a).replace(K,"$1").replace(G,"$1;"),a="function("+(n||"obj")+"){"+(n?"":"obj||(obj={});")+"var __t,__p=''"+(u?",__e=_.escape":"")+(o?",__j=Array.prototype.join;function print(){__p+=__j.call(arguments,'')}":";")+a+"return __p}",n=jf(function(){return Function(i,l+"return "+a).apply(N,f)}),n.source=a,tu(n))throw n;return n},St.times=function(t,n){if(t=hu(t),1>t||9007199254740991<t)return[];var r=4294967295,e=yo(t,4294967295);for(n=fe(n),
t-=4294967295,e=k(e,n);++r<t;)n(r);return e},St.toFinite=su,St.toInteger=hu,St.toLength=pu,St.toLower=function(t){return gu(t).toLowerCase()},St.toNumber=_u,St.toSafeInteger=function(t){return vn(hu(t),-9007199254740991,9007199254740991)},St.toString=gu,St.toUpper=function(t){return gu(t).toUpperCase()},St.trim=function(t,n,r){return(t=gu(t))&&(r||n===N)?t.replace(ct,""):t&&(n=dr(n))?(t=t.match(Bt),r=n.match(Bt),n=W(t,r),r=B(t,r)+1,Or(t,n,r).join("")):t},St.trimEnd=function(t,n,r){return(t=gu(t))&&(r||n===N)?t.replace(lt,""):t&&(n=dr(n))?(t=t.match(Bt),
n=B(t,n.match(Bt))+1,Or(t,0,n).join("")):t},St.trimStart=function(t,n,r){return(t=gu(t))&&(r||n===N)?t.replace(at,""):t&&(n=dr(n))?(t=t.match(Bt),n=W(t,n.match(Bt)),Or(t,n).join("")):t},St.truncate=function(t,n){var r=30,e="...";if(uu(n))var u="separator"in n?n.separator:u,r="length"in n?hu(n.length):r,e="omission"in n?dr(n.omission):e;t=gu(t);var o=t.length;if(Ct.test(t))var i=t.match(Bt),o=i.length;if(r>=o)return t;if(o=r-F(e),1>o)return e;if(r=i?Or(i,0,o).join(""):t.slice(0,o),u===N)return r+e;
if(i&&(o+=r.length-o),qi(u)){if(t.slice(o).search(u)){var f=r;for(u.global||(u=zu(u.source,gu(dt.exec(u))+"g")),u.lastIndex=0;i=u.exec(f);)var c=i.index;r=r.slice(0,c===N?o:c)}}else t.indexOf(dr(u),o)!=o&&(u=r.lastIndexOf(u),-1<u&&(r=r.slice(0,u)));return r+e},St.unescape=function(t){return(t=gu(t))&&H.test(t)?t.replace(J,un):t},St.uniqueId=function(t){var n=++Vu;return gu(t)+n},St.upperCase=bf,St.upperFirst=xf,St.each=$e,St.eachRight=Fe,St.first=Re,Iu(St,function(){var t={};return kn(St,function(n,r){
qu.call(St.prototype,r)||(t[r]=n)}),t}(),{chain:false}),St.VERSION="4.14.2",u("bind bindKey curry curryRight partial partialRight".split(" "),function(t){St[t].placeholder=St}),u(["drop","take"],function(t,n){Ft.prototype[t]=function(r){var e=this.__filtered__;if(e&&!n)return new Ft(this);r=r===N?1:go(hu(r),0);var u=this.clone();return e?u.__takeCount__=yo(r,u.__takeCount__):u.__views__.push({size:yo(r,4294967295),type:t+(0>u.__dir__?"Right":"")}),u},Ft.prototype[t+"Right"]=function(n){return this.reverse()[t](n).reverse();
}}),u(["filter","map","takeWhile"],function(t,n){var r=n+1,e=1==r||3==r;Ft.prototype[t]=function(t){var n=this.clone();return n.__iteratees__.push({iteratee:fe(t,3),type:r}),n.__filtered__=n.__filtered__||e,n}}),u(["head","last"],function(t,n){var r="take"+(n?"Right":"");Ft.prototype[t]=function(){return this[r](1).value()[0]}}),u(["initial","tail"],function(t,n){var r="drop"+(n?"":"Right");Ft.prototype[t]=function(){return this.__filtered__?new Ft(this):this[r](1)}}),Ft.prototype.compact=function(){
return this.filter(Eu)},Ft.prototype.find=function(t){return this.filter(t).head()},Ft.prototype.findLast=function(t){return this.reverse().find(t)},Ft.prototype.invokeMap=ar(function(t,n){return typeof t=="function"?new Ft(this):this.map(function(r){return Dn(r,t,n)})}),Ft.prototype.reject=function(t){return this.filter(Je(fe(t)))},Ft.prototype.slice=function(t,n){t=hu(t);var r=this;return r.__filtered__&&(0<t||0>n)?new Ft(r):(0>t?r=r.takeRight(-t):t&&(r=r.drop(t)),n!==N&&(n=hu(n),r=0>n?r.dropRight(-n):r.take(n-t)),
r)},Ft.prototype.takeRightWhile=function(t){return this.reverse().takeWhile(t).reverse()},Ft.prototype.toArray=function(){return this.take(4294967295)},kn(Ft.prototype,function(t,n){var r=/^(?:filter|find|map|reject)|While$/.test(n),e=/^(?:head|last)$/.test(n),u=St[e?"take"+("last"==n?"Right":""):n],o=e||/^find/.test(n);u&&(St.prototype[n]=function(){function n(t){return t=u.apply(St,s([t],f)),e&&h?t[0]:t}var i=this.__wrapped__,f=e?[1]:arguments,c=i instanceof Ft,a=f[0],l=c||Fi(i);l&&r&&typeof a=="function"&&1!=a.length&&(c=l=false);
var h=this.__chain__,p=!!this.__actions__.length,a=o&&!h,c=c&&!p;return!o&&l?(i=c?i:new Ft(this),i=t.apply(i,f),i.__actions__.push({func:ze,args:[n],thisArg:N}),new $t(i,h)):a&&c?t.apply(this,f):(i=this.thru(n),a?e?i.value()[0]:i.value():i)})}),u("pop push shift sort splice unshift".split(" "),function(t){var n=$u[t],r=/^(?:push|sort|unshift)$/.test(t)?"tap":"thru",e=/^(?:pop|shift)$/.test(t);St.prototype[t]=function(){var t=arguments;if(e&&!this.__chain__){var u=this.value();return n.apply(Fi(u)?u:[],t);
}return this[r](function(r){return n.apply(Fi(r)?r:[],t)})}}),kn(Ft.prototype,function(t,n){var r=St[n];if(r){var e=r.name+"";(Mo[e]||(Mo[e]=[])).push({name:n,func:r})}}),Mo[qr(N,2).name]=[{name:"wrapper",func:N}],Ft.prototype.clone=function(){var t=new Ft(this.__wrapped__);return t.__actions__=Wr(this.__actions__),t.__dir__=this.__dir__,t.__filtered__=this.__filtered__,t.__iteratees__=Wr(this.__iteratees__),t.__takeCount__=this.__takeCount__,t.__views__=Wr(this.__views__),t},Ft.prototype.reverse=function(){
if(this.__filtered__){var t=new Ft(this);t.__dir__=-1,t.__filtered__=true}else t=this.clone(),t.__dir__*=-1;return t},Ft.prototype.value=function(){var t,n=this.__wrapped__.value(),r=this.__dir__,e=Fi(n),u=0>r,o=e?n.length:0;t=o;for(var i=this.__views__,f=0,c=-1,a=i.length;++c<a;){var l=i[c],s=l.size;switch(l.type){case"drop":f+=s;break;case"dropRight":t-=s;break;case"take":t=yo(t,f+s);break;case"takeRight":f=go(f,t-s)}}if(t={start:f,end:t},i=t.start,f=t.end,t=f-i,u=u?f:i-1,i=this.__iteratees__,f=i.length,
c=0,a=yo(t,this.__takeCount__),!e||200>o||o==t&&a==t)return xr(n,this.__actions__);e=[];t:for(;t--&&c<a;){for(u+=r,o=-1,l=n[u];++o<f;){var h=i[o],s=h.type,h=(0,h.iteratee)(l);if(2==s)l=h;else if(!h){if(1==s)continue t;break t}}e[c++]=l}return e},St.prototype.at=xi,St.prototype.chain=function(){return De(this)},St.prototype.commit=function(){return new $t(this.value(),this.__chain__)},St.prototype.next=function(){this.__values__===N&&(this.__values__=lu(this.value()));var t=this.__index__>=this.__values__.length,n=t?N:this.__values__[this.__index__++];
return{done:t,value:n}},St.prototype.plant=function(t){for(var n,r=this;r instanceof It;){var e=Ee(r);e.__index__=0,e.__values__=N,n?u.__wrapped__=e:n=e;var u=e,r=r.__wrapped__}return u.__wrapped__=t,n},St.prototype.reverse=function(){var t=this.__wrapped__;return t instanceof Ft?(this.__actions__.length&&(t=new Ft(this)),t=t.reverse(),t.__actions__.push({func:ze,args:[Me],thisArg:N}),new $t(t,this.__chain__)):this.thru(Me)},St.prototype.toJSON=St.prototype.valueOf=St.prototype.value=function(){return xr(this.__wrapped__,this.__actions__);
},St.prototype.first=St.prototype.head,no&&(St.prototype[no]=Ue),St}var N,P=1/0,Z=NaN,q=[["ary",128],["bind",1],["bindKey",2],["curry",8],["curryRight",16],["flip",512],["partial",32],["partialRight",64],["rearg",256]],V=/\b__p\+='';/g,K=/\b(__p\+=)''\+/g,G=/(__e\(.*?\)|\b__t\))\+'';/g,J=/&(?:amp|lt|gt|quot|#39|#96);/g,Y=/[&<>"'`]/g,H=RegExp(J.source),Q=RegExp(Y.source),X=/<%-([\s\S]+?)%>/g,tt=/<%([\s\S]+?)%>/g,nt=/<%=([\s\S]+?)%>/g,rt=/\.|\[(?:[^[\]]*|(["'])(?:(?!\1)[^\\]|\\.)*?\1)\]/,et=/^\w*$/,ut=/^\./,ot=/[^.[\]]+|\[(?:(-?\d+(?:\.\d+)?)|(["'])((?:(?!\2)[^\\]|\\.)*?)\2)\]|(?=(?:\.|\[\])(?:\.|\[\]|$))/g,it=/[\\^$.*+?()[\]{}|]/g,ft=RegExp(it.source),ct=/^\s+|\s+$/g,at=/^\s+/,lt=/\s+$/,st=/\{(?:\n\/\* \[wrapped with .+\] \*\/)?\n?/,ht=/\{\n\/\* \[wrapped with (.+)\] \*/,pt=/,? & /,_t=/[a-zA-Z0-9]+/g,vt=/\\(\\)?/g,gt=/\$\{([^\\}]*(?:\\.[^\\}]*)*)\}/g,dt=/\w*$/,yt=/^0x/i,bt=/^[-+]0x[0-9a-f]+$/i,xt=/^0b[01]+$/i,jt=/^\[object .+?Constructor\]$/,mt=/^0o[0-7]+$/i,wt=/^(?:0|[1-9]\d*)$/,At=/[\xc0-\xd6\xd8-\xde\xdf-\xf6\xf8-\xff]/g,Ot=/($^)/,kt=/['\n\r\u2028\u2029\\]/g,Et="[\\ufe0e\\ufe0f]?(?:[\\u0300-\\u036f\\ufe20-\\ufe23\\u20d0-\\u20f0]|\\ud83c[\\udffb-\\udfff])?(?:\\u200d(?:[^\\ud800-\\udfff]|(?:\\ud83c[\\udde6-\\uddff]){2}|[\\ud800-\\udbff][\\udc00-\\udfff])[\\ufe0e\\ufe0f]?(?:[\\u0300-\\u036f\\ufe20-\\ufe23\\u20d0-\\u20f0]|\\ud83c[\\udffb-\\udfff])?)*",St="(?:[\\u2700-\\u27bf]|(?:\\ud83c[\\udde6-\\uddff]){2}|[\\ud800-\\udbff][\\udc00-\\udfff])"+Et,It="(?:[^\\ud800-\\udfff][\\u0300-\\u036f\\ufe20-\\ufe23\\u20d0-\\u20f0]?|[\\u0300-\\u036f\\ufe20-\\ufe23\\u20d0-\\u20f0]|(?:\\ud83c[\\udde6-\\uddff]){2}|[\\ud800-\\udbff][\\udc00-\\udfff]|[\\ud800-\\udfff])",Rt=RegExp("['\u2019]","g"),Wt=RegExp("[\\u0300-\\u036f\\ufe20-\\ufe23\\u20d0-\\u20f0]","g"),Bt=RegExp("\\ud83c[\\udffb-\\udfff](?=\\ud83c[\\udffb-\\udfff])|"+It+Et,"g"),Mt=RegExp(["[A-Z\\xc0-\\xd6\\xd8-\\xde]?[a-z\\xdf-\\xf6\\xf8-\\xff]+(?:['\u2019](?:d|ll|m|re|s|t|ve))?(?=[\\xac\\xb1\\xd7\\xf7\\x00-\\x2f\\x3a-\\x40\\x5b-\\x60\\x7b-\\xbf\\u2000-\\u206f \\t\\x0b\\f\\xa0\\ufeff\\n\\r\\u2028\\u2029\\u1680\\u180e\\u2000\\u2001\\u2002\\u2003\\u2004\\u2005\\u2006\\u2007\\u2008\\u2009\\u200a\\u202f\\u205f\\u3000]|[A-Z\\xc0-\\xd6\\xd8-\\xde]|$)|(?:[A-Z\\xc0-\\xd6\\xd8-\\xde]|[^\\ud800-\\udfff\\xac\\xb1\\xd7\\xf7\\x00-\\x2f\\x3a-\\x40\\x5b-\\x60\\x7b-\\xbf\\u2000-\\u206f \\t\\x0b\\f\\xa0\\ufeff\\n\\r\\u2028\\u2029\\u1680\\u180e\\u2000\\u2001\\u2002\\u2003\\u2004\\u2005\\u2006\\u2007\\u2008\\u2009\\u200a\\u202f\\u205f\\u3000\\d+\\u2700-\\u27bfa-z\\xdf-\\xf6\\xf8-\\xffA-Z\\xc0-\\xd6\\xd8-\\xde])+(?:['\u2019](?:D|LL|M|RE|S|T|VE))?(?=[\\xac\\xb1\\xd7\\xf7\\x00-\\x2f\\x3a-\\x40\\x5b-\\x60\\x7b-\\xbf\\u2000-\\u206f \\t\\x0b\\f\\xa0\\ufeff\\n\\r\\u2028\\u2029\\u1680\\u180e\\u2000\\u2001\\u2002\\u2003\\u2004\\u2005\\u2006\\u2007\\u2008\\u2009\\u200a\\u202f\\u205f\\u3000]|[A-Z\\xc0-\\xd6\\xd8-\\xde](?:[a-z\\xdf-\\xf6\\xf8-\\xff]|[^\\ud800-\\udfff\\xac\\xb1\\xd7\\xf7\\x00-\\x2f\\x3a-\\x40\\x5b-\\x60\\x7b-\\xbf\\u2000-\\u206f \\t\\x0b\\f\\xa0\\ufeff\\n\\r\\u2028\\u2029\\u1680\\u180e\\u2000\\u2001\\u2002\\u2003\\u2004\\u2005\\u2006\\u2007\\u2008\\u2009\\u200a\\u202f\\u205f\\u3000\\d+\\u2700-\\u27bfa-z\\xdf-\\xf6\\xf8-\\xffA-Z\\xc0-\\xd6\\xd8-\\xde])|$)|[A-Z\\xc0-\\xd6\\xd8-\\xde]?(?:[a-z\\xdf-\\xf6\\xf8-\\xff]|[^\\ud800-\\udfff\\xac\\xb1\\xd7\\xf7\\x00-\\x2f\\x3a-\\x40\\x5b-\\x60\\x7b-\\xbf\\u2000-\\u206f \\t\\x0b\\f\\xa0\\ufeff\\n\\r\\u2028\\u2029\\u1680\\u180e\\u2000\\u2001\\u2002\\u2003\\u2004\\u2005\\u2006\\u2007\\u2008\\u2009\\u200a\\u202f\\u205f\\u3000\\d+\\u2700-\\u27bfa-z\\xdf-\\xf6\\xf8-\\xffA-Z\\xc0-\\xd6\\xd8-\\xde])+(?:['\u2019](?:d|ll|m|re|s|t|ve))?|[A-Z\\xc0-\\xd6\\xd8-\\xde]+(?:['\u2019](?:D|LL|M|RE|S|T|VE))?|\\d+",St].join("|"),"g"),Ct=RegExp("[\\u200d\\ud800-\\udfff\\u0300-\\u036f\\ufe20-\\ufe23\\u20d0-\\u20f0\\ufe0e\\ufe0f]"),Lt=/[a-z][A-Z]|[A-Z]{2,}[a-z]|[0-9][a-zA-Z]|[a-zA-Z][0-9]|[^a-zA-Z0-9 ]/,Dt="Array Buffer DataView Date Error Float32Array Float64Array Function Int8Array Int16Array Int32Array Map Math Object Promise RegExp Set String Symbol TypeError Uint8Array Uint8ClampedArray Uint16Array Uint32Array WeakMap _ clearTimeout isFinite parseInt setTimeout".split(" "),zt={};
zt["[object Float32Array]"]=zt["[object Float64Array]"]=zt["[object Int8Array]"]=zt["[object Int16Array]"]=zt["[object Int32Array]"]=zt["[object Uint8Array]"]=zt["[object Uint8ClampedArray]"]=zt["[object Uint16Array]"]=zt["[object Uint32Array]"]=true,zt["[object Arguments]"]=zt["[object Array]"]=zt["[object ArrayBuffer]"]=zt["[object Boolean]"]=zt["[object DataView]"]=zt["[object Date]"]=zt["[object Error]"]=zt["[object Function]"]=zt["[object Map]"]=zt["[object Number]"]=zt["[object Object]"]=zt["[object RegExp]"]=zt["[object Set]"]=zt["[object String]"]=zt["[object WeakMap]"]=false;
var Ut={};Ut["[object Arguments]"]=Ut["[object Array]"]=Ut["[object ArrayBuffer]"]=Ut["[object DataView]"]=Ut["[object Boolean]"]=Ut["[object Date]"]=Ut["[object Float32Array]"]=Ut["[object Float64Array]"]=Ut["[object Int8Array]"]=Ut["[object Int16Array]"]=Ut["[object Int32Array]"]=Ut["[object Map]"]=Ut["[object Number]"]=Ut["[object Object]"]=Ut["[object RegExp]"]=Ut["[object Set]"]=Ut["[object String]"]=Ut["[object Symbol]"]=Ut["[object Uint8Array]"]=Ut["[object Uint8ClampedArray]"]=Ut["[object Uint16Array]"]=Ut["[object Uint32Array]"]=true,
Ut["[object Error]"]=Ut["[object Function]"]=Ut["[object WeakMap]"]=false;var $t,Ft={"\\":"\\","'":"'","\n":"n","\r":"r","\u2028":"u2028","\u2029":"u2029"},Tt=parseFloat,Nt=parseInt,Pt=typeof global=="object"&&global&&global.Object===Object&&global,Zt=typeof self=="object"&&self&&self.Object===Object&&self,qt=Pt||Zt||Function("return this")(),Vt=typeof exports=="object"&&exports&&!exports.nodeType&&exports,Kt=Vt&&typeof module=="object"&&module&&!module.nodeType&&module,Gt=Kt&&Kt.exports===Vt,Jt=Gt&&Pt.g;
t:{try{$t=Jt&&Jt.f("util");break t}catch(t){}$t=void 0}var Yt=$t&&$t.isArrayBuffer,Ht=$t&&$t.isDate,Qt=$t&&$t.isMap,Xt=$t&&$t.isRegExp,tn=$t&&$t.isSet,nn=$t&&$t.isTypedArray,rn=m({"\xc0":"A","\xc1":"A","\xc2":"A","\xc3":"A","\xc4":"A","\xc5":"A","\xe0":"a","\xe1":"a","\xe2":"a","\xe3":"a","\xe4":"a","\xe5":"a","\xc7":"C","\xe7":"c","\xd0":"D","\xf0":"d","\xc8":"E","\xc9":"E","\xca":"E","\xcb":"E","\xe8":"e","\xe9":"e","\xea":"e","\xeb":"e","\xcc":"I","\xcd":"I","\xce":"I","\xcf":"I","\xec":"i","\xed":"i",
"\xee":"i","\xef":"i","\xd1":"N","\xf1":"n","\xd2":"O","\xd3":"O","\xd4":"O","\xd5":"O","\xd6":"O","\xd8":"O","\xf2":"o","\xf3":"o","\xf4":"o","\xf5":"o","\xf6":"o","\xf8":"o","\xd9":"U","\xda":"U","\xdb":"U","\xdc":"U","\xf9":"u","\xfa":"u","\xfb":"u","\xfc":"u","\xdd":"Y","\xfd":"y","\xff":"y","\xc6":"Ae","\xe6":"ae","\xde":"Th","\xfe":"th","\xdf":"ss"}),en=m({"&":"&amp;","<":"&lt;",">":"&gt;",'"':"&quot;","'":"&#39;","`":"&#96;"}),un=m({"&amp;":"&","&lt;":"<","&gt;":">","&quot;":'"',"&#39;":"'",
"&#96;":"`"}),on=T();typeof define=="function"&&typeof define.amd=="object"&&define.amd?(qt._=on, define('lib/lodash/dist/lodash.min',[],function(){return on})):Kt?((Kt.exports=on)._=on,Vt._=on):qt._=on}).call(this);
/**
 * Created by leilihuang on 16/4/8.
 * util/util
 */
define(
    'entries/util/util',['jquery', 'template', 'lib/lodash/dist/lodash.min'],
    function($, Template, _) {
        var util = {
            $head: $("#headTools"),
            $foot: $("#footTools"),
            cookie:{},
            timeOptions: {
                showButtonPanel: true,
                changeYear: true,
                changeMonth: true,
                beforeShow: function(dom) {
                    $(dom).val('');
                }
            },
            getCookie: function() {
                var cookie = document.cookie.split(";");
                for(var i=0;i<cookie.length;i++){
                    this.cookie[cookie[i].split('=')[0].trim()] = cookie[i].split('=')[1];
                }
                console.log(this.cookie);
            },
            initHead: function(callback) {
                if (this.$head) {
                    this.$head.load('/page/head.jsp');
                }
                if (callback) {
                    callback();
                }
            },
            initFoot: function() {
                if (this.$foot) {
                    this.$foot.load('/page/foot.jsp');
                }
            },
            getUserInfo: function() {
                util.ajaxData('user/getUserInfo', 'GET', {}, function(d, bool) {
                    if (bool) {
                        if (d.data.level != 1) {
                            $('.two-menu-box').hide();
                        }
                        Poss.userName = d.data.user.loginName;
                        $('#userName').text(d.data.user.loginName);
                        $('.headBox').find('.login_user').find('.menusBox').hide();
                        $('.headBox').find('.login_dl').hide();
                        $('.headBox').find('.login_user').show();
                    } else {
                        $('.headBox').find('.login_dl').show();
                        window.location.href = 'login.html?url=' + location.href;
                    }
                }.bind(this));
            },
            /**
             *
             * @param url 接口地址
             * @param type  接口类型
             * @param data  传递参数
             * @param callback 回调函数
             * @param status  存在就显示提示信息，不存在就不提示，判断是否显示提示信息
             */
            ajaxData: function(url, type, data, callback, status) {
                console.log(url);
                this.getCookie();
                var d = url.split('/')[0];
                if (d == 'channel') {
                    url = Poss.getChannelUrl(url);
                } else {
                    url = Poss.getHost(url)
                }
                $.ajax({
                    url: url,
                    type: type,
                    data: data,
                    dataType: 'json',
                    headers:{
                        "domeAccessToken":this.cookie.dome_access_token || ''
                    },
                    success: function(d) {
                        if (d.responseCode == 1000) {
                            callback(d, true);
                        } else if (d.responseCode == 1004) {
                            window.location.href = Poss.host + 'login.html??' + location.href;
                        } else {
                            callback(d, false);
                            if (!status) {
                                util.tipsAlert(null, d.errorMsg, 'tips_text');
                            }
                        }
                    },
                    error: function(e) {
                        util.tipsAlert(null, "系统异常，请刷新重试!", 'tips_error');
                    }
                })
            },
            /**
             * @pageNum 当前页码
             * @pageSize 每页显示总数*/
            pageStart: function(pageNum, pageSize, total) {
                var num = 0;
                if (total == 0) {
                    num = 0;
                } else {
                    num = Number(pageNum - 1) * pageSize + 1
                }
                return num;
            },
            /**
             * @leng 每页返回数组的长度
             * @pageNum 当前页码
             * @pageSize 每页显示总数*/
            pageEnd: function(leng, pageNum, pageSize) {
                return Number(leng) + Number(pageNum - 1) * pageSize;
            },
            /**
             * @total 总共多少条数
             * @pageSize 每页显示总数*/
            pageBind: function(total, pageSize, callBack, pageNum, pageBox) {
                var pageBox = pageBox || 'util_pages';
                $("#" + pageBox).pagination(total, {
                    current_page: pageNum || 0,
                    tems_per_page: 1, //边缘页数
                    num_edge_entries: 1,
                    num_display_entries: 4, //主体页数
                    callback: function(index) {
                        callBack(index);
                    },
                    items_per_page: pageSize, //每页显示1项,
                    prev_text: "<",
                    next_text: ">",
                    ellipse_text: "..."
                });
            },
            selectDiv: function(callback) {
                $(".div_select").on("click", function() {
                    $(this).nextAll(".sel_hideSle").show();
                });
                $(".sel_options").on("click", function() {
                    if (!$(this).hasClass("cur")) {
                        var text = $(this).text();
                        $(this).addClass("cur").siblings(".cur").removeClass("cur");
                        $(this).parent().prevAll(".div_select").text(text);
                        if (callback) {
                            callback($(this));
                        }
                    }
                    $(this).parent().hide();

                });
                //$(".sel_hideSle").blur(function () {
                //    $(this).hide();
                //});
                $(".sel_hideSle").blur(function() {
                    console.log(22);
                });
            },
            iCheck: function() {
                $('input.isCheck').iCheck({
                    checkboxClass: 'icheckbox_square',
                    radioClass: 'iradio_square',
                    increaseArea: '20%' // optional
                });
            },
            openImg: function(url) {
                var obj = {
                    src: url
                };
                var open = function() {
                    var html = Template('util_openImg', obj);
                    $(html).find(".clone").on("click", function() {
                        $("#box_openImg").remove();
                    }).end().appendTo("body");
                };


                if ($("#util_openImg").length > 0) {
                    open();
                } else {
                    if ($("#templateHide").length < 1) {
                        $('body').append('<div id="templateHide"></div>');
                    }
                    $("#templateHide").load('../html/template/util.html', function() {
                        open();
                    });

                }
            },
            /**
             * @tplID
             * title 提示信息
             * html  提示文字
             * tips_success         是成功
             * tips_error           是失败
             * tips_loading         是loding加载提示
             * tips_warning_alert   是回调弹窗
             * tips_text            是文本信息提示
             *  
             * callBack             回调参数
             * */
            tipsAlert: function(title, html, tplId, callback) {
                var obj = {
                    title: title ? title : "提示信息",
                    html: html
                };
                var open = function() {
                    var html = Template(tplId, obj);
                    if ($('.util_tips_box').length > 0) {
                        $('.util_tips_box').remove();
                    }
                    $(html).find(".clone").on("click", function() {
                        $(this).parents(".util_tips_box").remove();
                        if (callback && tplId != 'tips_warning_alert') {
                            callback();
                        }
                    }).end().find('.util-submit').on('click', function() {
                        $(this).parents(".util_tips_box").remove();
                        callback();
                    }).end().find('.util-cencer').on('click', function() {
                        $(this).parents(".util_tips_box").remove();
                    }).end().appendTo("body");
                };
                if ($("#" + tplId).length > 0) {

                    open();
                } else {
                    if ($("#templateHide").length > 0) {
                        $("#templateHide").load('/dist/html/template/alert.html', function() {
                            open();
                        });
                    } else {
                        $('body').append('<div id="templateHide"></div>');
                        $("#templateHide").load('/dist/html/template/alert.html', function() {
                            open();
                        });
                    }

                }
            },
            hideLoding: function() {
                $('.util_tips_box').remove();
            },
            showLoding: function() {
                util.tipsAlert(" ", "加载中……", 'tips_loading');
            },
            hideAlertBox: function() {
                var el = '.alert_box';
                $(el + ' .alert-Box').css('height','');
                $(el).hide();
            },
            requireURL: function(str) {
                //return!!str.match(/(((^https?:(?:\/\/)?)(?:[-;:&=\+\$,\w]+@)?[A-Za-z0-9.-]+|(?:www.|[-;:&=\+\$,\w]+@)[A-Za-z0-9.-]+)((?:\/[\+~%\/.\w-_]*)?\??(?:[-\+=&;%@.\w_]*)#?(?:[\w]*))?)$/g);
                var reg = /^https?:\/\/.*$/;
                return reg.test(str);
            },
            showAgreed: function() {
                $('.xie_yi_box').show();
            },
            hideAgreed: function() {
                $('.xie_yi_box').hide();
            },
            cloneAgreed: function() {
                $('.xie_yi_box').find('.clone').on('click', function() {
                    util.hideAgreed();
                });
            },
            agreedBind: function($btnSub, curClass) {
                if (!curClass) {
                    curClass = 'sub_cur';
                }
                $('.agreed-to').on('click', function() {
                    util.hideAgreed();
                    $('.agreed-util-to').prop('checked', true);
                    $btnSub.addClass(curClass);
                });
            },
            /**
             * 数字验证，css命名统一为   util-digVail
             */
            digVail: function(val) {
                var bool = false,
                    reg = /^0\.\d{0,2}$/g,
                    reg2 = /^[1-9]+(\.[0-9]{0,2})?$/g,
                    reg3 = /^([1-9][\d]{0,20}|0)(\.[\d]{0,2})?$/
                if (reg3.test(val)) {

                } else {
                    val = val.substring(0, val.length - 1);
                }
                return val;
            },
            /**
             * 邮箱验证，css命名统一为   util-emailVail
             */
            emailVail: function(val) {
                var bool = false,
                    reg = /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/g;
                if (reg.test(val)) {
                    bool = true;
                } else {
                    bool = false;
                }
                return bool;
            },
            http: function(val) {
                var result = false,
                    reg = /(http:\/\/|https:\/\/)/
                    ;

                if (reg.test(val)) {
                    result = true;
                }

                return result;
            },
            digit: function(val) {
                var result = false,
                    reg = /^([1-9][\d]{0,20}|0)?$/
                    ;
                
                if (reg.test(val)) {
                    result = true;
                }

                return result;
            },
            generateParams: function(param) {
                var url = '',
                    params = [];

                _.forIn(param, function(value, key) {
                    if (value) {
                        params.push(key + '=' + value);
                    }
                });

                if (params.length > 0) {
                    url += '?' + params.join('&');
                }
                return url;
            },
            getQueryString: function(name) {
                var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)","i");
                var r = window.location.search.substr(1).match(reg);
                if (r!=null) return (r[2]); return null;
            },
            channelInit: function() {
                var _this =this;
                this.ajaxData('channel/query', 'POST', {}, function(d, bool) {
                    if (bool) {
                        if (d.data.status == '0') {
                            window.location.href = '/channel/channelmanage-subapply.html';
                        } else if (d.data.status == '1') {
                            window.location.href = '/channel/channelmanage-reviewing.html';
                        } else if (d.data.status == '2') {
                            window.location.href = '/channel/channelmanage-applygame.html';
                        } else if (d.data.status == '3') {
                            window.location.href = '/channel/channelmanage-notrougth.html';
                        } else {
                            _this.tipsAlert(null, '您的渠道已被暂停，请联系客服处理!', 'tips_error');
                        }
                    }
                });
            },
            config: {
                successDelay: 2000,
                deleteDelay: 2500,
            }
        };
        return util;
    }
);

/**
 * Created by leilihuang on 16/4/11.
 */
define(
    'entries/util/base',['jquery', 'entries/util/util'],
    function($, Util) {
        var base = {
            init: function(callback) {
                this.login();
                this.params = Poss.getUrl;
                this.userNameBind();
                this.manageDropdwon();
                this.exitBind();
                setTimeout(function() {
                    callback();
                    this.channelBind();
                }.bind(this), 200);
            },
            login: function() {
                Util.getUserInfo();
            },
            userNameBind: function() {
                $('#userName').on('click', function() {
                    $(this).next().show();
                    return false;
                });
                $(document).on('click', function(e) {
                    $('#userName').next().hide();
                });
            },
            manageDropdwon: function() {
                $('#manageDropdwon').on('click', function() {
                    $(this).next().show();
                    return false;
                });
                $(document).on('click', function(e) {
                    $('#manageDropdwon').next().hide();
                });
            },
            exitBind: function() {
                $('.user_exit').on('click', function() {
                    Util.ajaxData('user/logout', 'POST', {}, function(d, bool) {
                        if (bool) {
                            window.location.href = '/app/index';
                        }
                    })
                });

            },
            channelBind: function() {
                var _this = this;
                $('#channel-manage-menu').on('click', function() {
                    Util.channelInit();
                });
            }
        };
        return base;
    }
);

/*!
 * Chart.js
 * http://chartjs.org/
 * Version: 2.1.6
 *
 * Copyright 2016 Nick Downie
 * Released under the MIT license
 * https://github.com/chartjs/Chart.js/blob/master/LICENSE.md
 */
!function(t){if("object"==typeof exports&&"undefined"!=typeof module)module.exports=t();else if("function"==typeof define&&define.amd)define('lib/Chart.js/dist/Chart.min',[],t);else{var e;e="undefined"!=typeof window?window:"undefined"!=typeof global?global:"undefined"!=typeof self?self:this,e.Chart=t()}}(function(){return function t(e,a,i){function n(r,l){if(!a[r]){if(!e[r]){var s="function"==typeof require&&require;if(!l&&s)return s(r,!0);if(o)return o(r,!0);var d=new Error("Cannot find module '"+r+"'");throw d.code="MODULE_NOT_FOUND",d}var u=a[r]={exports:{}};e[r][0].call(u.exports,function(t){var a=e[r][1][t];return n(a?a:t)},u,u.exports,t,e,a,i)}return a[r].exports}for(var o="function"==typeof require&&require,r=0;r<i.length;r++)n(i[r]);return n}({1:[function(t,e,a){},{}],2:[function(t,e,a){function i(t){if(t){var e=/^#([a-fA-F0-9]{3})$/,a=/^#([a-fA-F0-9]{6})$/,i=/^rgba?\(\s*([+-]?\d+)\s*,\s*([+-]?\d+)\s*,\s*([+-]?\d+)\s*(?:,\s*([+-]?[\d\.]+)\s*)?\)$/,n=/^rgba?\(\s*([+-]?[\d\.]+)\%\s*,\s*([+-]?[\d\.]+)\%\s*,\s*([+-]?[\d\.]+)\%\s*(?:,\s*([+-]?[\d\.]+)\s*)?\)$/,o=/(\w+)/,r=[0,0,0],l=1,s=t.match(e);if(s){s=s[1];for(var d=0;d<r.length;d++)r[d]=parseInt(s[d]+s[d],16)}else if(s=t.match(a)){s=s[1];for(var d=0;d<r.length;d++)r[d]=parseInt(s.slice(2*d,2*d+2),16)}else if(s=t.match(i)){for(var d=0;d<r.length;d++)r[d]=parseInt(s[d+1]);l=parseFloat(s[4])}else if(s=t.match(n)){for(var d=0;d<r.length;d++)r[d]=Math.round(2.55*parseFloat(s[d+1]));l=parseFloat(s[4])}else if(s=t.match(o)){if("transparent"==s[1])return[0,0,0,0];if(r=y[s[1]],!r)return}for(var d=0;d<r.length;d++)r[d]=v(r[d],0,255);return l=l||0==l?v(l,0,1):1,r[3]=l,r}}function n(t){if(t){var e=/^hsla?\(\s*([+-]?\d+)(?:deg)?\s*,\s*([+-]?[\d\.]+)%\s*,\s*([+-]?[\d\.]+)%\s*(?:,\s*([+-]?[\d\.]+)\s*)?\)/,a=t.match(e);if(a){var i=parseFloat(a[4]),n=v(parseInt(a[1]),0,360),o=v(parseFloat(a[2]),0,100),r=v(parseFloat(a[3]),0,100),l=v(isNaN(i)?1:i,0,1);return[n,o,r,l]}}}function o(t){if(t){var e=/^hwb\(\s*([+-]?\d+)(?:deg)?\s*,\s*([+-]?[\d\.]+)%\s*,\s*([+-]?[\d\.]+)%\s*(?:,\s*([+-]?[\d\.]+)\s*)?\)/,a=t.match(e);if(a){var i=parseFloat(a[4]),n=v(parseInt(a[1]),0,360),o=v(parseFloat(a[2]),0,100),r=v(parseFloat(a[3]),0,100),l=v(isNaN(i)?1:i,0,1);return[n,o,r,l]}}}function r(t){var e=i(t);return e&&e.slice(0,3)}function l(t){var e=n(t);return e&&e.slice(0,3)}function s(t){var e=i(t);return e?e[3]:(e=n(t))?e[3]:(e=o(t))?e[3]:void 0}function d(t){return"#"+x(t[0])+x(t[1])+x(t[2])}function u(t,e){return 1>e||t[3]&&t[3]<1?c(t,e):"rgb("+t[0]+", "+t[1]+", "+t[2]+")"}function c(t,e){return void 0===e&&(e=void 0!==t[3]?t[3]:1),"rgba("+t[0]+", "+t[1]+", "+t[2]+", "+e+")"}function h(t,e){if(1>e||t[3]&&t[3]<1)return f(t,e);var a=Math.round(t[0]/255*100),i=Math.round(t[1]/255*100),n=Math.round(t[2]/255*100);return"rgb("+a+"%, "+i+"%, "+n+"%)"}function f(t,e){var a=Math.round(t[0]/255*100),i=Math.round(t[1]/255*100),n=Math.round(t[2]/255*100);return"rgba("+a+"%, "+i+"%, "+n+"%, "+(e||t[3]||1)+")"}function g(t,e){return 1>e||t[3]&&t[3]<1?p(t,e):"hsl("+t[0]+", "+t[1]+"%, "+t[2]+"%)"}function p(t,e){return void 0===e&&(e=void 0!==t[3]?t[3]:1),"hsla("+t[0]+", "+t[1]+"%, "+t[2]+"%, "+e+")"}function m(t,e){return void 0===e&&(e=void 0!==t[3]?t[3]:1),"hwb("+t[0]+", "+t[1]+"%, "+t[2]+"%"+(void 0!==e&&1!==e?", "+e:"")+")"}function b(t){return k[t.slice(0,3)]}function v(t,e,a){return Math.min(Math.max(e,t),a)}function x(t){var e=t.toString(16).toUpperCase();return e.length<2?"0"+e:e}var y=t(6);e.exports={getRgba:i,getHsla:n,getRgb:r,getHsl:l,getHwb:o,getAlpha:s,hexString:d,rgbString:u,rgbaString:c,percentString:h,percentaString:f,hslString:g,hslaString:p,hwbString:m,keyword:b};var k={};for(var S in y)k[y[S]]=S},{6:6}],3:[function(t,e,a){var i=t(5),n=t(2),o=function(t){if(t instanceof o)return t;if(!(this instanceof o))return new o(t);this.values={rgb:[0,0,0],hsl:[0,0,0],hsv:[0,0,0],hwb:[0,0,0],cmyk:[0,0,0,0],alpha:1};var e;if("string"==typeof t)if(e=n.getRgba(t))this.setValues("rgb",e);else if(e=n.getHsla(t))this.setValues("hsl",e);else{if(!(e=n.getHwb(t)))throw new Error('Unable to parse color from string "'+t+'"');this.setValues("hwb",e)}else if("object"==typeof t)if(e=t,void 0!==e.r||void 0!==e.red)this.setValues("rgb",e);else if(void 0!==e.l||void 0!==e.lightness)this.setValues("hsl",e);else if(void 0!==e.v||void 0!==e.value)this.setValues("hsv",e);else if(void 0!==e.w||void 0!==e.whiteness)this.setValues("hwb",e);else{if(void 0===e.c&&void 0===e.cyan)throw new Error("Unable to parse color from object "+JSON.stringify(t));this.setValues("cmyk",e)}};o.prototype={rgb:function(){return this.setSpace("rgb",arguments)},hsl:function(){return this.setSpace("hsl",arguments)},hsv:function(){return this.setSpace("hsv",arguments)},hwb:function(){return this.setSpace("hwb",arguments)},cmyk:function(){return this.setSpace("cmyk",arguments)},rgbArray:function(){return this.values.rgb},hslArray:function(){return this.values.hsl},hsvArray:function(){return this.values.hsv},hwbArray:function(){var t=this.values;return 1!==t.alpha?t.hwb.concat([t.alpha]):t.hwb},cmykArray:function(){return this.values.cmyk},rgbaArray:function(){var t=this.values;return t.rgb.concat([t.alpha])},hslaArray:function(){var t=this.values;return t.hsl.concat([t.alpha])},alpha:function(t){return void 0===t?this.values.alpha:(this.setValues("alpha",t),this)},red:function(t){return this.setChannel("rgb",0,t)},green:function(t){return this.setChannel("rgb",1,t)},blue:function(t){return this.setChannel("rgb",2,t)},hue:function(t){return t&&(t%=360,t=0>t?360+t:t),this.setChannel("hsl",0,t)},saturation:function(t){return this.setChannel("hsl",1,t)},lightness:function(t){return this.setChannel("hsl",2,t)},saturationv:function(t){return this.setChannel("hsv",1,t)},whiteness:function(t){return this.setChannel("hwb",1,t)},blackness:function(t){return this.setChannel("hwb",2,t)},value:function(t){return this.setChannel("hsv",2,t)},cyan:function(t){return this.setChannel("cmyk",0,t)},magenta:function(t){return this.setChannel("cmyk",1,t)},yellow:function(t){return this.setChannel("cmyk",2,t)},black:function(t){return this.setChannel("cmyk",3,t)},hexString:function(){return n.hexString(this.values.rgb)},rgbString:function(){return n.rgbString(this.values.rgb,this.values.alpha)},rgbaString:function(){return n.rgbaString(this.values.rgb,this.values.alpha)},percentString:function(){return n.percentString(this.values.rgb,this.values.alpha)},hslString:function(){return n.hslString(this.values.hsl,this.values.alpha)},hslaString:function(){return n.hslaString(this.values.hsl,this.values.alpha)},hwbString:function(){return n.hwbString(this.values.hwb,this.values.alpha)},keyword:function(){return n.keyword(this.values.rgb,this.values.alpha)},rgbNumber:function(){var t=this.values.rgb;return t[0]<<16|t[1]<<8|t[2]},luminosity:function(){for(var t=this.values.rgb,e=[],a=0;a<t.length;a++){var i=t[a]/255;e[a]=.03928>=i?i/12.92:Math.pow((i+.055)/1.055,2.4)}return.2126*e[0]+.7152*e[1]+.0722*e[2]},contrast:function(t){var e=this.luminosity(),a=t.luminosity();return e>a?(e+.05)/(a+.05):(a+.05)/(e+.05)},level:function(t){var e=this.contrast(t);return e>=7.1?"AAA":e>=4.5?"AA":""},dark:function(){var t=this.values.rgb,e=(299*t[0]+587*t[1]+114*t[2])/1e3;return 128>e},light:function(){return!this.dark()},negate:function(){for(var t=[],e=0;3>e;e++)t[e]=255-this.values.rgb[e];return this.setValues("rgb",t),this},lighten:function(t){var e=this.values.hsl;return e[2]+=e[2]*t,this.setValues("hsl",e),this},darken:function(t){var e=this.values.hsl;return e[2]-=e[2]*t,this.setValues("hsl",e),this},saturate:function(t){var e=this.values.hsl;return e[1]+=e[1]*t,this.setValues("hsl",e),this},desaturate:function(t){var e=this.values.hsl;return e[1]-=e[1]*t,this.setValues("hsl",e),this},whiten:function(t){var e=this.values.hwb;return e[1]+=e[1]*t,this.setValues("hwb",e),this},blacken:function(t){var e=this.values.hwb;return e[2]+=e[2]*t,this.setValues("hwb",e),this},greyscale:function(){var t=this.values.rgb,e=.3*t[0]+.59*t[1]+.11*t[2];return this.setValues("rgb",[e,e,e]),this},clearer:function(t){var e=this.values.alpha;return this.setValues("alpha",e-e*t),this},opaquer:function(t){var e=this.values.alpha;return this.setValues("alpha",e+e*t),this},rotate:function(t){var e=this.values.hsl,a=(e[0]+t)%360;return e[0]=0>a?360+a:a,this.setValues("hsl",e),this},mix:function(t,e){var a=this,i=t,n=void 0===e?.5:e,o=2*n-1,r=a.alpha()-i.alpha(),l=((o*r===-1?o:(o+r)/(1+o*r))+1)/2,s=1-l;return this.rgb(l*a.red()+s*i.red(),l*a.green()+s*i.green(),l*a.blue()+s*i.blue()).alpha(a.alpha()*n+i.alpha()*(1-n))},toJSON:function(){return this.rgb()},clone:function(){var t,e,a=new o,i=this.values,n=a.values;for(var r in i)i.hasOwnProperty(r)&&(t=i[r],e={}.toString.call(t),"[object Array]"===e?n[r]=t.slice(0):"[object Number]"===e?n[r]=t:console.error("unexpected color value:",t));return a}},o.prototype.spaces={rgb:["red","green","blue"],hsl:["hue","saturation","lightness"],hsv:["hue","saturation","value"],hwb:["hue","whiteness","blackness"],cmyk:["cyan","magenta","yellow","black"]},o.prototype.maxes={rgb:[255,255,255],hsl:[360,100,100],hsv:[360,100,100],hwb:[360,100,100],cmyk:[100,100,100,100]},o.prototype.getValues=function(t){for(var e=this.values,a={},i=0;i<t.length;i++)a[t.charAt(i)]=e[t][i];return 1!==e.alpha&&(a.a=e.alpha),a},o.prototype.setValues=function(t,e){var a,n=this.values,o=this.spaces,r=this.maxes,l=1;if("alpha"===t)l=e;else if(e.length)n[t]=e.slice(0,t.length),l=e[t.length];else if(void 0!==e[t.charAt(0)]){for(a=0;a<t.length;a++)n[t][a]=e[t.charAt(a)];l=e.a}else if(void 0!==e[o[t][0]]){var s=o[t];for(a=0;a<t.length;a++)n[t][a]=e[s[a]];l=e.alpha}if(n.alpha=Math.max(0,Math.min(1,void 0===l?n.alpha:l)),"alpha"===t)return!1;var d;for(a=0;a<t.length;a++)d=Math.max(0,Math.min(r[t][a],n[t][a])),n[t][a]=Math.round(d);for(var u in o)u!==t&&(n[u]=i[t][u](n[t]));return!0},o.prototype.setSpace=function(t,e){var a=e[0];return void 0===a?this.getValues(t):("number"==typeof a&&(a=Array.prototype.slice.call(e)),this.setValues(t,a),this)},o.prototype.setChannel=function(t,e,a){var i=this.values[t];return void 0===a?i[e]:a===i[e]?this:(i[e]=a,this.setValues(t,i),this)},"undefined"!=typeof window&&(window.Color=o),e.exports=o},{2:2,5:5}],4:[function(t,e,a){function i(t){var e,a,i,n=t[0]/255,o=t[1]/255,r=t[2]/255,l=Math.min(n,o,r),s=Math.max(n,o,r),d=s-l;return s==l?e=0:n==s?e=(o-r)/d:o==s?e=2+(r-n)/d:r==s&&(e=4+(n-o)/d),e=Math.min(60*e,360),0>e&&(e+=360),i=(l+s)/2,a=s==l?0:.5>=i?d/(s+l):d/(2-s-l),[e,100*a,100*i]}function n(t){var e,a,i,n=t[0],o=t[1],r=t[2],l=Math.min(n,o,r),s=Math.max(n,o,r),d=s-l;return a=0==s?0:d/s*1e3/10,s==l?e=0:n==s?e=(o-r)/d:o==s?e=2+(r-n)/d:r==s&&(e=4+(n-o)/d),e=Math.min(60*e,360),0>e&&(e+=360),i=s/255*1e3/10,[e,a,i]}function o(t){var e=t[0],a=t[1],n=t[2],o=i(t)[0],r=1/255*Math.min(e,Math.min(a,n)),n=1-1/255*Math.max(e,Math.max(a,n));return[o,100*r,100*n]}function l(t){var e,a,i,n,o=t[0]/255,r=t[1]/255,l=t[2]/255;return n=Math.min(1-o,1-r,1-l),e=(1-o-n)/(1-n)||0,a=(1-r-n)/(1-n)||0,i=(1-l-n)/(1-n)||0,[100*e,100*a,100*i,100*n]}function s(t){return Q[JSON.stringify(t)]}function d(t){var e=t[0]/255,a=t[1]/255,i=t[2]/255;e=e>.04045?Math.pow((e+.055)/1.055,2.4):e/12.92,a=a>.04045?Math.pow((a+.055)/1.055,2.4):a/12.92,i=i>.04045?Math.pow((i+.055)/1.055,2.4):i/12.92;var n=.4124*e+.3576*a+.1805*i,o=.2126*e+.7152*a+.0722*i,r=.0193*e+.1192*a+.9505*i;return[100*n,100*o,100*r]}function u(t){var e,a,i,n=d(t),o=n[0],r=n[1],l=n[2];return o/=95.047,r/=100,l/=108.883,o=o>.008856?Math.pow(o,1/3):7.787*o+16/116,r=r>.008856?Math.pow(r,1/3):7.787*r+16/116,l=l>.008856?Math.pow(l,1/3):7.787*l+16/116,e=116*r-16,a=500*(o-r),i=200*(r-l),[e,a,i]}function c(t){return W(u(t))}function h(t){var e,a,i,n,o,r=t[0]/360,l=t[1]/100,s=t[2]/100;if(0==l)return o=255*s,[o,o,o];a=.5>s?s*(1+l):s+l-s*l,e=2*s-a,n=[0,0,0];for(var d=0;3>d;d++)i=r+1/3*-(d-1),0>i&&i++,i>1&&i--,o=1>6*i?e+6*(a-e)*i:1>2*i?a:2>3*i?e+(a-e)*(2/3-i)*6:e,n[d]=255*o;return n}function f(t){var e,a,i=t[0],n=t[1]/100,o=t[2]/100;return 0===o?[0,0,0]:(o*=2,n*=1>=o?o:2-o,a=(o+n)/2,e=2*n/(o+n),[i,100*e,100*a])}function p(t){return o(h(t))}function m(t){return l(h(t))}function v(t){return s(h(t))}function x(t){var e=t[0]/60,a=t[1]/100,i=t[2]/100,n=Math.floor(e)%6,o=e-Math.floor(e),r=255*i*(1-a),l=255*i*(1-a*o),s=255*i*(1-a*(1-o)),i=255*i;switch(n){case 0:return[i,s,r];case 1:return[l,i,r];case 2:return[r,i,s];case 3:return[r,l,i];case 4:return[s,r,i];case 5:return[i,r,l]}}function y(t){var e,a,i=t[0],n=t[1]/100,o=t[2]/100;return a=(2-n)*o,e=n*o,e/=1>=a?a:2-a,e=e||0,a/=2,[i,100*e,100*a]}function k(t){return o(x(t))}function S(t){return l(x(t))}function w(t){return s(x(t))}function C(t){var e,a,i,n,o=t[0]/360,l=t[1]/100,s=t[2]/100,d=l+s;switch(d>1&&(l/=d,s/=d),e=Math.floor(6*o),a=1-s,i=6*o-e,0!=(1&e)&&(i=1-i),n=l+i*(a-l),e){default:case 6:case 0:r=a,g=n,b=l;break;case 1:r=n,g=a,b=l;break;case 2:r=l,g=a,b=n;break;case 3:r=l,g=n,b=a;break;case 4:r=n,g=l,b=a;break;case 5:r=a,g=l,b=n}return[255*r,255*g,255*b]}function M(t){return i(C(t))}function D(t){return n(C(t))}function A(t){return l(C(t))}function I(t){return s(C(t))}function F(t){var e,a,i,n=t[0]/100,o=t[1]/100,r=t[2]/100,l=t[3]/100;return e=1-Math.min(1,n*(1-l)+l),a=1-Math.min(1,o*(1-l)+l),i=1-Math.min(1,r*(1-l)+l),[255*e,255*a,255*i]}function T(t){return i(F(t))}function P(t){return n(F(t))}function _(t){return o(F(t))}function R(t){return s(F(t))}function V(t){var e,a,i,n=t[0]/100,o=t[1]/100,r=t[2]/100;return e=3.2406*n+-1.5372*o+r*-.4986,a=n*-.9689+1.8758*o+.0415*r,i=.0557*n+o*-.204+1.057*r,e=e>.0031308?1.055*Math.pow(e,1/2.4)-.055:e=12.92*e,a=a>.0031308?1.055*Math.pow(a,1/2.4)-.055:a=12.92*a,i=i>.0031308?1.055*Math.pow(i,1/2.4)-.055:i=12.92*i,e=Math.min(Math.max(0,e),1),a=Math.min(Math.max(0,a),1),i=Math.min(Math.max(0,i),1),[255*e,255*a,255*i]}function O(t){var e,a,i,n=t[0],o=t[1],r=t[2];return n/=95.047,o/=100,r/=108.883,n=n>.008856?Math.pow(n,1/3):7.787*n+16/116,o=o>.008856?Math.pow(o,1/3):7.787*o+16/116,r=r>.008856?Math.pow(r,1/3):7.787*r+16/116,e=116*o-16,a=500*(n-o),i=200*(o-r),[e,a,i]}function L(t){return W(O(t))}function B(t){var e,a,i,n,o=t[0],r=t[1],l=t[2];return 8>=o?(a=100*o/903.3,n=7.787*(a/100)+16/116):(a=100*Math.pow((o+16)/116,3),n=Math.pow(a/100,1/3)),e=.008856>=e/95.047?e=95.047*(r/500+n-16/116)/7.787:95.047*Math.pow(r/500+n,3),i=.008859>=i/108.883?i=108.883*(n-l/200-16/116)/7.787:108.883*Math.pow(n-l/200,3),[e,a,i]}function W(t){var e,a,i,n=t[0],o=t[1],r=t[2];return e=Math.atan2(r,o),a=360*e/2/Math.PI,0>a&&(a+=360),i=Math.sqrt(o*o+r*r),[n,i,a]}function z(t){return V(B(t))}function H(t){var e,a,i,n=t[0],o=t[1],r=t[2];return i=r/360*2*Math.PI,e=o*Math.cos(i),a=o*Math.sin(i),[n,e,a]}function N(t){return B(H(t))}function E(t){return z(H(t))}function U(t){return G[t]}function q(t){return i(U(t))}function j(t){return n(U(t))}function Y(t){return o(U(t))}function J(t){return l(U(t))}function X(t){return u(U(t))}function Z(t){return d(U(t))}e.exports={rgb2hsl:i,rgb2hsv:n,rgb2hwb:o,rgb2cmyk:l,rgb2keyword:s,rgb2xyz:d,rgb2lab:u,rgb2lch:c,hsl2rgb:h,hsl2hsv:f,hsl2hwb:p,hsl2cmyk:m,hsl2keyword:v,hsv2rgb:x,hsv2hsl:y,hsv2hwb:k,hsv2cmyk:S,hsv2keyword:w,hwb2rgb:C,hwb2hsl:M,hwb2hsv:D,hwb2cmyk:A,hwb2keyword:I,cmyk2rgb:F,cmyk2hsl:T,cmyk2hsv:P,cmyk2hwb:_,cmyk2keyword:R,keyword2rgb:U,keyword2hsl:q,keyword2hsv:j,keyword2hwb:Y,keyword2cmyk:J,keyword2lab:X,keyword2xyz:Z,xyz2rgb:V,xyz2lab:O,xyz2lch:L,lab2xyz:B,lab2rgb:z,lab2lch:W,lch2lab:H,lch2xyz:N,lch2rgb:E};var G={aliceblue:[240,248,255],antiquewhite:[250,235,215],aqua:[0,255,255],aquamarine:[127,255,212],azure:[240,255,255],beige:[245,245,220],bisque:[255,228,196],black:[0,0,0],blanchedalmond:[255,235,205],blue:[0,0,255],blueviolet:[138,43,226],brown:[165,42,42],burlywood:[222,184,135],cadetblue:[95,158,160],chartreuse:[127,255,0],chocolate:[210,105,30],coral:[255,127,80],cornflowerblue:[100,149,237],cornsilk:[255,248,220],crimson:[220,20,60],cyan:[0,255,255],darkblue:[0,0,139],darkcyan:[0,139,139],darkgoldenrod:[184,134,11],darkgray:[169,169,169],darkgreen:[0,100,0],darkgrey:[169,169,169],darkkhaki:[189,183,107],darkmagenta:[139,0,139],darkolivegreen:[85,107,47],darkorange:[255,140,0],darkorchid:[153,50,204],darkred:[139,0,0],darksalmon:[233,150,122],darkseagreen:[143,188,143],darkslateblue:[72,61,139],darkslategray:[47,79,79],darkslategrey:[47,79,79],darkturquoise:[0,206,209],darkviolet:[148,0,211],deeppink:[255,20,147],deepskyblue:[0,191,255],dimgray:[105,105,105],dimgrey:[105,105,105],dodgerblue:[30,144,255],firebrick:[178,34,34],floralwhite:[255,250,240],forestgreen:[34,139,34],fuchsia:[255,0,255],gainsboro:[220,220,220],ghostwhite:[248,248,255],gold:[255,215,0],goldenrod:[218,165,32],gray:[128,128,128],green:[0,128,0],greenyellow:[173,255,47],grey:[128,128,128],honeydew:[240,255,240],hotpink:[255,105,180],indianred:[205,92,92],indigo:[75,0,130],ivory:[255,255,240],khaki:[240,230,140],lavender:[230,230,250],lavenderblush:[255,240,245],lawngreen:[124,252,0],lemonchiffon:[255,250,205],lightblue:[173,216,230],lightcoral:[240,128,128],lightcyan:[224,255,255],lightgoldenrodyellow:[250,250,210],lightgray:[211,211,211],lightgreen:[144,238,144],lightgrey:[211,211,211],lightpink:[255,182,193],lightsalmon:[255,160,122],lightseagreen:[32,178,170],lightskyblue:[135,206,250],lightslategray:[119,136,153],lightslategrey:[119,136,153],lightsteelblue:[176,196,222],lightyellow:[255,255,224],lime:[0,255,0],limegreen:[50,205,50],linen:[250,240,230],magenta:[255,0,255],maroon:[128,0,0],mediumaquamarine:[102,205,170],mediumblue:[0,0,205],mediumorchid:[186,85,211],mediumpurple:[147,112,219],mediumseagreen:[60,179,113],mediumslateblue:[123,104,238],mediumspringgreen:[0,250,154],mediumturquoise:[72,209,204],mediumvioletred:[199,21,133],midnightblue:[25,25,112],mintcream:[245,255,250],mistyrose:[255,228,225],moccasin:[255,228,181],navajowhite:[255,222,173],navy:[0,0,128],oldlace:[253,245,230],olive:[128,128,0],olivedrab:[107,142,35],orange:[255,165,0],orangered:[255,69,0],orchid:[218,112,214],palegoldenrod:[238,232,170],palegreen:[152,251,152],paleturquoise:[175,238,238],palevioletred:[219,112,147],papayawhip:[255,239,213],peachpuff:[255,218,185],peru:[205,133,63],pink:[255,192,203],plum:[221,160,221],powderblue:[176,224,230],purple:[128,0,128],rebeccapurple:[102,51,153],red:[255,0,0],rosybrown:[188,143,143],royalblue:[65,105,225],saddlebrown:[139,69,19],salmon:[250,128,114],sandybrown:[244,164,96],seagreen:[46,139,87],seashell:[255,245,238],sienna:[160,82,45],silver:[192,192,192],skyblue:[135,206,235],slateblue:[106,90,205],slategray:[112,128,144],slategrey:[112,128,144],snow:[255,250,250],springgreen:[0,255,127],steelblue:[70,130,180],tan:[210,180,140],teal:[0,128,128],thistle:[216,191,216],tomato:[255,99,71],turquoise:[64,224,208],violet:[238,130,238],wheat:[245,222,179],white:[255,255,255],whitesmoke:[245,245,245],yellow:[255,255,0],yellowgreen:[154,205,50]},Q={};for(var $ in G)Q[JSON.stringify(G[$])]=$},{}],5:[function(t,e,a){var i=t(4),n=function(){return new d};for(var o in i){n[o+"Raw"]=function(t){return function(e){return"number"==typeof e&&(e=Array.prototype.slice.call(arguments)),i[t](e)}}(o);var r=/(\w+)2(\w+)/.exec(o),l=r[1],s=r[2];n[l]=n[l]||{},n[l][s]=n[o]=function(t){return function(e){"number"==typeof e&&(e=Array.prototype.slice.call(arguments));var a=i[t](e);if("string"==typeof a||void 0===a)return a;for(var n=0;n<a.length;n++)a[n]=Math.round(a[n]);return a}}(o)}var d=function(){this.convs={}};d.prototype.routeSpace=function(t,e){var a=e[0];return void 0===a?this.getValues(t):("number"==typeof a&&(a=Array.prototype.slice.call(e)),this.setValues(t,a))},d.prototype.setValues=function(t,e){return this.space=t,this.convs={},this.convs[t]=e,this},d.prototype.getValues=function(t){var e=this.convs[t];if(!e){var a=this.space,i=this.convs[a];e=n[a][t](i),this.convs[t]=e}return e},["rgb","hsl","hsv","cmyk","keyword"].forEach(function(t){d.prototype[t]=function(e){return this.routeSpace(t,arguments)}}),e.exports=n},{4:4}],6:[function(t,e,a){e.exports={aliceblue:[240,248,255],antiquewhite:[250,235,215],aqua:[0,255,255],aquamarine:[127,255,212],azure:[240,255,255],beige:[245,245,220],bisque:[255,228,196],black:[0,0,0],blanchedalmond:[255,235,205],blue:[0,0,255],blueviolet:[138,43,226],brown:[165,42,42],burlywood:[222,184,135],cadetblue:[95,158,160],chartreuse:[127,255,0],chocolate:[210,105,30],coral:[255,127,80],cornflowerblue:[100,149,237],cornsilk:[255,248,220],crimson:[220,20,60],cyan:[0,255,255],darkblue:[0,0,139],darkcyan:[0,139,139],darkgoldenrod:[184,134,11],darkgray:[169,169,169],darkgreen:[0,100,0],darkgrey:[169,169,169],darkkhaki:[189,183,107],darkmagenta:[139,0,139],darkolivegreen:[85,107,47],darkorange:[255,140,0],darkorchid:[153,50,204],darkred:[139,0,0],darksalmon:[233,150,122],darkseagreen:[143,188,143],darkslateblue:[72,61,139],darkslategray:[47,79,79],darkslategrey:[47,79,79],darkturquoise:[0,206,209],darkviolet:[148,0,211],deeppink:[255,20,147],deepskyblue:[0,191,255],dimgray:[105,105,105],dimgrey:[105,105,105],dodgerblue:[30,144,255],firebrick:[178,34,34],floralwhite:[255,250,240],forestgreen:[34,139,34],fuchsia:[255,0,255],gainsboro:[220,220,220],ghostwhite:[248,248,255],gold:[255,215,0],goldenrod:[218,165,32],gray:[128,128,128],green:[0,128,0],greenyellow:[173,255,47],grey:[128,128,128],honeydew:[240,255,240],hotpink:[255,105,180],indianred:[205,92,92],indigo:[75,0,130],ivory:[255,255,240],khaki:[240,230,140],lavender:[230,230,250],lavenderblush:[255,240,245],lawngreen:[124,252,0],lemonchiffon:[255,250,205],lightblue:[173,216,230],lightcoral:[240,128,128],lightcyan:[224,255,255],lightgoldenrodyellow:[250,250,210],lightgray:[211,211,211],lightgreen:[144,238,144],lightgrey:[211,211,211],lightpink:[255,182,193],lightsalmon:[255,160,122],lightseagreen:[32,178,170],lightskyblue:[135,206,250],lightslategray:[119,136,153],lightslategrey:[119,136,153],lightsteelblue:[176,196,222],lightyellow:[255,255,224],lime:[0,255,0],limegreen:[50,205,50],linen:[250,240,230],magenta:[255,0,255],maroon:[128,0,0],mediumaquamarine:[102,205,170],mediumblue:[0,0,205],mediumorchid:[186,85,211],mediumpurple:[147,112,219],mediumseagreen:[60,179,113],mediumslateblue:[123,104,238],mediumspringgreen:[0,250,154],mediumturquoise:[72,209,204],mediumvioletred:[199,21,133],midnightblue:[25,25,112],mintcream:[245,255,250],mistyrose:[255,228,225],moccasin:[255,228,181],navajowhite:[255,222,173],navy:[0,0,128],oldlace:[253,245,230],olive:[128,128,0],olivedrab:[107,142,35],orange:[255,165,0],orangered:[255,69,0],orchid:[218,112,214],palegoldenrod:[238,232,170],palegreen:[152,251,152],paleturquoise:[175,238,238],palevioletred:[219,112,147],papayawhip:[255,239,213],peachpuff:[255,218,185],peru:[205,133,63],pink:[255,192,203],plum:[221,160,221],powderblue:[176,224,230],purple:[128,0,128],rebeccapurple:[102,51,153],red:[255,0,0],rosybrown:[188,143,143],royalblue:[65,105,225],saddlebrown:[139,69,19],salmon:[250,128,114],sandybrown:[244,164,96],seagreen:[46,139,87],seashell:[255,245,238],sienna:[160,82,45],silver:[192,192,192],skyblue:[135,206,235],slateblue:[106,90,205],slategray:[112,128,144],slategrey:[112,128,144],snow:[255,250,250],springgreen:[0,255,127],steelblue:[70,130,180],tan:[210,180,140],teal:[0,128,128],thistle:[216,191,216],tomato:[255,99,71],turquoise:[64,224,208],violet:[238,130,238],wheat:[245,222,179],white:[255,255,255],whitesmoke:[245,245,245],yellow:[255,255,0],yellowgreen:[154,205,50]}},{}],7:[function(t,e,a){var i=t(26)();t(25)(i),t(24)(i),t(21)(i),t(22)(i),t(23)(i),t(27)(i),t(31)(i),t(29)(i),t(30)(i),t(32)(i),t(28)(i),t(33)(i),t(34)(i),t(35)(i),t(36)(i),t(37)(i),t(40)(i),t(38)(i),t(39)(i),t(41)(i),t(42)(i),t(43)(i),t(15)(i),t(16)(i),t(17)(i),t(18)(i),t(19)(i),t(20)(i),t(8)(i),t(9)(i),t(10)(i),t(11)(i),t(12)(i),t(13)(i),t(14)(i),window.Chart=e.exports=i},{10:10,11:11,12:12,13:13,14:14,15:15,16:16,17:17,18:18,19:19,20:20,21:21,22:22,23:23,24:24,25:25,26:26,27:27,28:28,29:29,30:30,31:31,32:32,33:33,34:34,35:35,36:36,37:37,38:38,39:39,40:40,41:41,42:42,43:43,8:8,9:9}],8:[function(t,e,a){"use strict";e.exports=function(t){t.Bar=function(e,a){return a.type="bar",new t(e,a)}}},{}],9:[function(t,e,a){"use strict";e.exports=function(t){t.Bubble=function(e,a){return a.type="bubble",new t(e,a)}}},{}],10:[function(t,e,a){"use strict";e.exports=function(t){t.Doughnut=function(e,a){return a.type="doughnut",new t(e,a)}}},{}],11:[function(t,e,a){"use strict";e.exports=function(t){t.Line=function(e,a){return a.type="line",new t(e,a)}}},{}],12:[function(t,e,a){"use strict";e.exports=function(t){t.PolarArea=function(e,a){return a.type="polarArea",new t(e,a)}}},{}],13:[function(t,e,a){"use strict";e.exports=function(t){t.Radar=function(e,a){return a.options=t.helpers.configMerge({aspectRatio:1},a.options),a.type="radar",new t(e,a)}}},{}],14:[function(t,e,a){"use strict";e.exports=function(t){var e={hover:{mode:"single"},scales:{xAxes:[{type:"linear",position:"bottom",id:"x-axis-1"}],yAxes:[{type:"linear",position:"left",id:"y-axis-1"}]},tooltips:{callbacks:{title:function(t,e){return""},label:function(t,e){return"("+t.xLabel+", "+t.yLabel+")"}}}};t.defaults.scatter=e,t.controllers.scatter=t.controllers.line,t.Scatter=function(e,a){return a.type="scatter",new t(e,a)}}},{}],15:[function(t,e,a){"use strict";e.exports=function(t){var e=t.helpers;t.defaults.bar={hover:{mode:"label"},scales:{xAxes:[{type:"category",categoryPercentage:.8,barPercentage:.9,gridLines:{offsetGridLines:!0}}],yAxes:[{type:"linear"}]}},t.controllers.bar=t.DatasetController.extend({dataElementType:t.elements.Rectangle,initialize:function(e,a){t.DatasetController.prototype.initialize.call(this,e,a),this.getMeta().bar=!0},getBarCount:function(){var t=this,a=0;return e.each(t.chart.data.datasets,function(e,i){var n=t.chart.getDatasetMeta(i);n.bar&&t.chart.isDatasetVisible(i)&&++a},t),a},update:function(t){var a=this;e.each(a.getMeta().data,function(e,i){a.updateElement(e,i,t)},a)},updateElement:function(t,a,i){var n=this,o=n.getMeta(),r=n.getScaleForId(o.xAxisID),l=n.getScaleForId(o.yAxisID),s=l.getBasePixel(),d=n.chart.options.elements.rectangle,u=t.custom||{},c=n.getDataset();e.extend(t,{_xScale:r,_yScale:l,_datasetIndex:n.index,_index:a,_model:{x:n.calculateBarX(a,n.index),y:i?s:n.calculateBarY(a,n.index),label:n.chart.data.labels[a],datasetLabel:c.label,base:i?s:n.calculateBarBase(n.index,a),width:n.calculateBarWidth(a),backgroundColor:u.backgroundColor?u.backgroundColor:e.getValueAtIndexOrDefault(c.backgroundColor,a,d.backgroundColor),borderSkipped:u.borderSkipped?u.borderSkipped:d.borderSkipped,borderColor:u.borderColor?u.borderColor:e.getValueAtIndexOrDefault(c.borderColor,a,d.borderColor),borderWidth:u.borderWidth?u.borderWidth:e.getValueAtIndexOrDefault(c.borderWidth,a,d.borderWidth)}}),t.pivot()},calculateBarBase:function(t,e){var a=this,i=a.getMeta(),n=a.getScaleForId(i.yAxisID),o=0;if(n.options.stacked){var r=a.chart,l=r.data.datasets,s=l[t].data[e];if(0>s)for(var d=0;t>d;d++){var u=l[d],c=r.getDatasetMeta(d);c.bar&&c.yAxisID===n.id&&r.isDatasetVisible(d)&&(o+=u.data[e]<0?u.data[e]:0)}else for(var h=0;t>h;h++){var f=l[h],g=r.getDatasetMeta(h);g.bar&&g.yAxisID===n.id&&r.isDatasetVisible(h)&&(o+=f.data[e]>0?f.data[e]:0)}return n.getPixelForValue(o)}return n.getBasePixel()},getRuler:function(t){var e,a=this,i=a.getMeta(),n=a.getScaleForId(i.xAxisID),o=a.getBarCount();e="category"===n.options.type?n.getPixelForTick(t+1)-n.getPixelForTick(t):n.width/n.ticks.length;var r=e*n.options.categoryPercentage,l=(e-e*n.options.categoryPercentage)/2,s=r/o;if(n.ticks.length!==a.chart.data.labels.length){var d=n.ticks.length/a.chart.data.labels.length;s*=d}var u=s*n.options.barPercentage,c=s-s*n.options.barPercentage;return{datasetCount:o,tickWidth:e,categoryWidth:r,categorySpacing:l,fullBarWidth:s,barWidth:u,barSpacing:c}},calculateBarWidth:function(t){var e=this.getScaleForId(this.getMeta().xAxisID),a=this.getRuler(t);return e.options.stacked?a.categoryWidth:a.barWidth},getBarIndex:function(t){var e,a,i=0;for(a=0;t>a;++a)e=this.chart.getDatasetMeta(a),e.bar&&this.chart.isDatasetVisible(a)&&++i;return i},calculateBarX:function(t,e){var a=this,i=a.getMeta(),n=a.getScaleForId(i.xAxisID),o=a.getBarIndex(e),r=a.getRuler(t),l=n.getPixelForValue(null,t,e,a.chart.isCombo);return l-=a.chart.isCombo?r.tickWidth/2:0,n.options.stacked?l+r.categoryWidth/2+r.categorySpacing:l+r.barWidth/2+r.categorySpacing+r.barWidth*o+r.barSpacing/2+r.barSpacing*o},calculateBarY:function(t,e){var a=this,i=a.getMeta(),n=a.getScaleForId(i.yAxisID),o=a.getDataset().data[t];if(n.options.stacked){for(var r=0,l=0,s=0;e>s;s++){var d=a.chart.data.datasets[s],u=a.chart.getDatasetMeta(s);u.bar&&u.yAxisID===n.id&&a.chart.isDatasetVisible(s)&&(d.data[t]<0?l+=d.data[t]||0:r+=d.data[t]||0)}return 0>o?n.getPixelForValue(l+o):n.getPixelForValue(r+o)}return n.getPixelForValue(o)},draw:function(t){var a=this,i=t||1;e.each(a.getMeta().data,function(t,e){var n=a.getDataset().data[e];null===n||void 0===n||isNaN(n)||t.transition(i).draw()},a)},setHoverStyle:function(t){var a=this.chart.data.datasets[t._datasetIndex],i=t._index,n=t.custom||{},o=t._model;o.backgroundColor=n.hoverBackgroundColor?n.hoverBackgroundColor:e.getValueAtIndexOrDefault(a.hoverBackgroundColor,i,e.getHoverColor(o.backgroundColor)),o.borderColor=n.hoverBorderColor?n.hoverBorderColor:e.getValueAtIndexOrDefault(a.hoverBorderColor,i,e.getHoverColor(o.borderColor)),o.borderWidth=n.hoverBorderWidth?n.hoverBorderWidth:e.getValueAtIndexOrDefault(a.hoverBorderWidth,i,o.borderWidth)},removeHoverStyle:function(t){var a=this.chart.data.datasets[t._datasetIndex],i=t._index,n=t.custom||{},o=t._model,r=this.chart.options.elements.rectangle;o.backgroundColor=n.backgroundColor?n.backgroundColor:e.getValueAtIndexOrDefault(a.backgroundColor,i,r.backgroundColor),o.borderColor=n.borderColor?n.borderColor:e.getValueAtIndexOrDefault(a.borderColor,i,r.borderColor),o.borderWidth=n.borderWidth?n.borderWidth:e.getValueAtIndexOrDefault(a.borderWidth,i,r.borderWidth)}}),t.defaults.horizontalBar={hover:{mode:"label"},scales:{xAxes:[{type:"linear",position:"bottom"}],yAxes:[{position:"left",type:"category",categoryPercentage:.8,barPercentage:.9,gridLines:{offsetGridLines:!0}}]},elements:{rectangle:{borderSkipped:"left"}},tooltips:{callbacks:{title:function(t,e){var a="";return t.length>0&&(t[0].yLabel?a=t[0].yLabel:e.labels.length>0&&t[0].index<e.labels.length&&(a=e.labels[t[0].index])),a},label:function(t,e){var a=e.datasets[t.datasetIndex].label||"";return a+": "+t.xLabel}}}},t.controllers.horizontalBar=t.controllers.bar.extend({updateElement:function(t,a,i,n){var o=this,r=o.getMeta(),l=o.getScaleForId(r.xAxisID),s=o.getScaleForId(r.yAxisID),d=l.getBasePixel(),u=t.custom||{},c=o.getDataset(),h=o.chart.options.elements.rectangle;e.extend(t,{_xScale:l,_yScale:s,_datasetIndex:o.index,_index:a,_model:{x:i?d:o.calculateBarX(a,o.index),y:o.calculateBarY(a,o.index),label:o.chart.data.labels[a],datasetLabel:c.label,base:i?d:o.calculateBarBase(o.index,a),height:o.calculateBarHeight(a),backgroundColor:u.backgroundColor?u.backgroundColor:e.getValueAtIndexOrDefault(c.backgroundColor,a,h.backgroundColor),borderSkipped:u.borderSkipped?u.borderSkipped:h.borderSkipped,borderColor:u.borderColor?u.borderColor:e.getValueAtIndexOrDefault(c.borderColor,a,h.borderColor),borderWidth:u.borderWidth?u.borderWidth:e.getValueAtIndexOrDefault(c.borderWidth,a,h.borderWidth)},draw:function(){function t(t){return s[(u+t)%4]}var e=this._chart.ctx,a=this._view,i=a.height/2,n=a.y-i,o=a.y+i,r=a.base-(a.base-a.x),l=a.borderWidth/2;a.borderWidth&&(n+=l,o-=l,r+=l),e.beginPath(),e.fillStyle=a.backgroundColor,e.strokeStyle=a.borderColor,e.lineWidth=a.borderWidth;var s=[[a.base,o],[a.base,n],[r,n],[r,o]],d=["bottom","left","top","right"],u=d.indexOf(a.borderSkipped,0);-1===u&&(u=0),e.moveTo.apply(e,t(0));for(var c=1;4>c;c++)e.lineTo.apply(e,t(c));e.fill(),a.borderWidth&&e.stroke()},inRange:function(t,e){var a=this._view,i=!1;return a&&(i=a.x<a.base?e>=a.y-a.height/2&&e<=a.y+a.height/2&&t>=a.x&&t<=a.base:e>=a.y-a.height/2&&e<=a.y+a.height/2&&t>=a.base&&t<=a.x),i}}),t.pivot()},calculateBarBase:function(t,e){
var a=this,i=a.getMeta(),n=a.getScaleForId(i.xAxisID),o=0;if(n.options.stacked){var r=a.chart.data.datasets[t].data[e];if(0>r)for(var l=0;t>l;l++){var s=a.chart.data.datasets[l],d=a.chart.getDatasetMeta(l);d.bar&&d.xAxisID===n.id&&a.chart.isDatasetVisible(l)&&(o+=s.data[e]<0?s.data[e]:0)}else for(var u=0;t>u;u++){var c=a.chart.data.datasets[u],h=a.chart.getDatasetMeta(u);h.bar&&h.xAxisID===n.id&&a.chart.isDatasetVisible(u)&&(o+=c.data[e]>0?c.data[e]:0)}return n.getPixelForValue(o)}return n.getBasePixel()},getRuler:function(t){var e,a=this,i=a.getMeta(),n=a.getScaleForId(i.yAxisID),o=a.getBarCount();e="category"===n.options.type?n.getPixelForTick(t+1)-n.getPixelForTick(t):n.width/n.ticks.length;var r=e*n.options.categoryPercentage,l=(e-e*n.options.categoryPercentage)/2,s=r/o;if(n.ticks.length!==a.chart.data.labels.length){var d=n.ticks.length/a.chart.data.labels.length;s*=d}var u=s*n.options.barPercentage,c=s-s*n.options.barPercentage;return{datasetCount:o,tickHeight:e,categoryHeight:r,categorySpacing:l,fullBarHeight:s,barHeight:u,barSpacing:c}},calculateBarHeight:function(t){var e=this,a=e.getScaleForId(e.getMeta().yAxisID),i=e.getRuler(t);return a.options.stacked?i.categoryHeight:i.barHeight},calculateBarX:function(t,e){var a=this,i=a.getMeta(),n=a.getScaleForId(i.xAxisID),o=a.getDataset().data[t];if(n.options.stacked){for(var r=0,l=0,s=0;e>s;s++){var d=a.chart.data.datasets[s],u=a.chart.getDatasetMeta(s);u.bar&&u.xAxisID===n.id&&a.chart.isDatasetVisible(s)&&(d.data[t]<0?l+=d.data[t]||0:r+=d.data[t]||0)}return 0>o?n.getPixelForValue(l+o):n.getPixelForValue(r+o)}return n.getPixelForValue(o)},calculateBarY:function(t,e){var a=this,i=a.getMeta(),n=a.getScaleForId(i.yAxisID),o=a.getBarIndex(e),r=a.getRuler(t),l=n.getPixelForValue(null,t,e,a.chart.isCombo);return l-=a.chart.isCombo?r.tickHeight/2:0,n.options.stacked?l+r.categoryHeight/2+r.categorySpacing:l+r.barHeight/2+r.categorySpacing+r.barHeight*o+r.barSpacing/2+r.barSpacing*o}})}},{}],16:[function(t,e,a){"use strict";e.exports=function(t){var e=t.helpers;t.defaults.bubble={hover:{mode:"single"},scales:{xAxes:[{type:"linear",position:"bottom",id:"x-axis-0"}],yAxes:[{type:"linear",position:"left",id:"y-axis-0"}]},tooltips:{callbacks:{title:function(t,e){return""},label:function(t,e){var a=e.datasets[t.datasetIndex].label||"",i=e.datasets[t.datasetIndex].data[t.index];return a+": ("+i.x+", "+i.y+", "+i.r+")"}}}},t.controllers.bubble=t.DatasetController.extend({dataElementType:t.elements.Point,update:function(t){var a=this,i=a.getMeta(),n=i.data;e.each(n,function(e,i){a.updateElement(e,i,t)})},updateElement:function(a,i,n){var o=this,r=o.getMeta(),l=o.getScaleForId(r.xAxisID),s=o.getScaleForId(r.yAxisID),d=a.custom||{},u=o.getDataset(),c=u.data[i],h=o.chart.options.elements.point,f=o.index;e.extend(a,{_xScale:l,_yScale:s,_datasetIndex:f,_index:i,_model:{x:n?l.getPixelForDecimal(.5):l.getPixelForValue(c,i,f,o.chart.isCombo),y:n?s.getBasePixel():s.getPixelForValue(c,i,f),radius:n?0:d.radius?d.radius:o.getRadius(c),hitRadius:d.hitRadius?d.hitRadius:e.getValueAtIndexOrDefault(u.hitRadius,i,h.hitRadius)}}),t.DatasetController.prototype.removeHoverStyle.call(o,a,h);var g=a._model;g.skip=d.skip?d.skip:isNaN(g.x)||isNaN(g.y),a.pivot()},getRadius:function(t){return t.r||this.chart.options.elements.point.radius},setHoverStyle:function(a){var i=this;t.DatasetController.prototype.setHoverStyle.call(i,a);var n=i.chart.data.datasets[a._datasetIndex],o=a._index,r=a.custom||{},l=a._model;l.radius=r.hoverRadius?r.hoverRadius:e.getValueAtIndexOrDefault(n.hoverRadius,o,i.chart.options.elements.point.hoverRadius)+i.getRadius(n.data[o])},removeHoverStyle:function(e){var a=this;t.DatasetController.prototype.removeHoverStyle.call(a,e,a.chart.options.elements.point);var i=a.chart.data.datasets[e._datasetIndex].data[e._index],n=e.custom||{},o=e._model;o.radius=n.radius?n.radius:a.getRadius(i)}})}},{}],17:[function(t,e,a){"use strict";e.exports=function(t){var e=t.helpers,a=t.defaults;a.doughnut={animation:{animateRotate:!0,animateScale:!1},aspectRatio:1,hover:{mode:"single"},legendCallback:function(t){var e=[];e.push('<ul class="'+t.id+'-legend">');var a=t.data,i=a.datasets,n=a.labels;if(i.length)for(var o=0;o<i[0].data.length;++o)e.push('<li><span style="background-color:'+i[0].backgroundColor[o]+'"></span>'),n[o]&&e.push(n[o]),e.push("</li>");return e.push("</ul>"),e.join("")},legend:{labels:{generateLabels:function(t){var a=t.data;return a.labels.length&&a.datasets.length?a.labels.map(function(i,n){var o=t.getDatasetMeta(0),r=a.datasets[0],l=o.data[n],s=l.custom||{},d=e.getValueAtIndexOrDefault,u=t.options.elements.arc,c=s.backgroundColor?s.backgroundColor:d(r.backgroundColor,n,u.backgroundColor),h=s.borderColor?s.borderColor:d(r.borderColor,n,u.borderColor),f=s.borderWidth?s.borderWidth:d(r.borderWidth,n,u.borderWidth);return{text:i,fillStyle:c,strokeStyle:h,lineWidth:f,hidden:isNaN(r.data[n])||o.data[n].hidden,index:n}}):[]}},onClick:function(t,e){var a,i,n,o=e.index,r=this.chart;for(a=0,i=(r.data.datasets||[]).length;i>a;++a)n=r.getDatasetMeta(a),n.data[o].hidden=!n.data[o].hidden;r.update()}},cutoutPercentage:50,rotation:Math.PI*-.5,circumference:2*Math.PI,tooltips:{callbacks:{title:function(){return""},label:function(t,e){return e.labels[t.index]+": "+e.datasets[t.datasetIndex].data[t.index]}}}},a.pie=e.clone(a.doughnut),e.extend(a.pie,{cutoutPercentage:0}),t.controllers.doughnut=t.controllers.pie=t.DatasetController.extend({dataElementType:t.elements.Arc,linkScales:e.noop,getRingIndex:function(t){for(var e=0,a=0;t>a;++a)this.chart.isDatasetVisible(a)&&++e;return e},update:function(t){var a=this,i=a.chart,n=i.chartArea,o=i.options,r=o.elements.arc,l=n.right-n.left-r.borderWidth,s=n.bottom-n.top-r.borderWidth,d=Math.min(l,s),u={x:0,y:0},c=a.getMeta(),h=o.cutoutPercentage,f=o.circumference;if(f<2*Math.PI){var g=o.rotation%(2*Math.PI);g+=2*Math.PI*(g>=Math.PI?-1:g<-Math.PI?1:0);var p=g+f,m={x:Math.cos(g),y:Math.sin(g)},b={x:Math.cos(p),y:Math.sin(p)},v=0>=g&&p>=0||g<=2*Math.PI&&2*Math.PI<=p,x=g<=.5*Math.PI&&.5*Math.PI<=p||g<=2.5*Math.PI&&2.5*Math.PI<=p,y=g<=-Math.PI&&-Math.PI<=p||g<=Math.PI&&Math.PI<=p,k=g<=.5*-Math.PI&&.5*-Math.PI<=p||g<=1.5*Math.PI&&1.5*Math.PI<=p,S=h/100,w={x:y?-1:Math.min(m.x*(m.x<0?1:S),b.x*(b.x<0?1:S)),y:k?-1:Math.min(m.y*(m.y<0?1:S),b.y*(b.y<0?1:S))},C={x:v?1:Math.max(m.x*(m.x>0?1:S),b.x*(b.x>0?1:S)),y:x?1:Math.max(m.y*(m.y>0?1:S),b.y*(b.y>0?1:S))},M={width:.5*(C.x-w.x),height:.5*(C.y-w.y)};d=Math.min(l/M.width,s/M.height),u={x:(C.x+w.x)*-.5,y:(C.y+w.y)*-.5}}i.outerRadius=Math.max(d/2,0),i.innerRadius=Math.max(h?i.outerRadius/100*h:1,0),i.radiusLength=(i.outerRadius-i.innerRadius)/i.getVisibleDatasetCount(),i.offsetX=u.x*i.outerRadius,i.offsetY=u.y*i.outerRadius,c.total=a.calculateTotal(),a.outerRadius=i.outerRadius-i.radiusLength*a.getRingIndex(a.index),a.innerRadius=a.outerRadius-i.radiusLength,e.each(c.data,function(e,i){a.updateElement(e,i,t)})},updateElement:function(t,a,i){var n=this,o=n.chart,r=o.chartArea,l=o.options,s=l.animation,d=(l.elements.arc,(r.left+r.right)/2),u=(r.top+r.bottom)/2,c=l.rotation,h=l.rotation,f=n.getDataset(),g=i&&s.animateRotate?0:t.hidden?0:n.calculateCircumference(f.data[a])*(l.circumference/(2*Math.PI)),p=i&&s.animateScale?0:n.innerRadius,m=i&&s.animateScale?0:n.outerRadius,b=(t.custom||{},e.getValueAtIndexOrDefault);e.extend(t,{_datasetIndex:n.index,_index:a,_model:{x:d+o.offsetX,y:u+o.offsetY,startAngle:c,endAngle:h,circumference:g,outerRadius:m,innerRadius:p,label:b(f.label,a,o.data.labels[a])}});var v=t._model;this.removeHoverStyle(t),i&&s.animateRotate||(0===a?v.startAngle=l.rotation:v.startAngle=n.getMeta().data[a-1]._model.endAngle,v.endAngle=v.startAngle+v.circumference),t.pivot()},removeHoverStyle:function(e){t.DatasetController.prototype.removeHoverStyle.call(this,e,this.chart.options.elements.arc)},calculateTotal:function(){var t,a=this.getDataset(),i=this.getMeta(),n=0;return e.each(i.data,function(e,i){t=a.data[i],isNaN(t)||e.hidden||(n+=Math.abs(t))}),n},calculateCircumference:function(t){var e=this.getMeta().total;return e>0&&!isNaN(t)?2*Math.PI*(t/e):0}})}},{}],18:[function(t,e,a){"use strict";e.exports=function(t){function e(t,e){return a.getValueOrDefault(t.showLine,e.showLines)}var a=t.helpers;t.defaults.line={showLines:!0,hover:{mode:"label"},scales:{xAxes:[{type:"category",id:"x-axis-0"}],yAxes:[{type:"linear",id:"y-axis-0"}]}},t.controllers.line=t.DatasetController.extend({datasetElementType:t.elements.Line,dataElementType:t.elements.Point,addElementAndReset:function(a){var i=this,n=i.chart.options,o=i.getMeta();t.DatasetController.prototype.addElementAndReset.call(i,a),e(i.getDataset(),n)&&0!==o.dataset._model.tension&&i.updateBezierControlPoints()},update:function(t){var i,n,o,r=this,l=r.getMeta(),s=l.dataset,d=l.data||[],u=r.chart.options,c=u.elements.line,h=r.getScaleForId(l.yAxisID),f=r.getDataset(),g=e(f,u);for(g&&(o=s.custom||{},void 0!==f.tension&&void 0===f.lineTension&&(f.lineTension=f.tension),s._scale=h,s._datasetIndex=r.index,s._children=d,s._model={spanGaps:f.spanGaps?f.spanGaps:!1,tension:o.tension?o.tension:a.getValueOrDefault(f.lineTension,c.tension),backgroundColor:o.backgroundColor?o.backgroundColor:f.backgroundColor||c.backgroundColor,borderWidth:o.borderWidth?o.borderWidth:f.borderWidth||c.borderWidth,borderColor:o.borderColor?o.borderColor:f.borderColor||c.borderColor,borderCapStyle:o.borderCapStyle?o.borderCapStyle:f.borderCapStyle||c.borderCapStyle,borderDash:o.borderDash?o.borderDash:f.borderDash||c.borderDash,borderDashOffset:o.borderDashOffset?o.borderDashOffset:f.borderDashOffset||c.borderDashOffset,borderJoinStyle:o.borderJoinStyle?o.borderJoinStyle:f.borderJoinStyle||c.borderJoinStyle,fill:o.fill?o.fill:void 0!==f.fill?f.fill:c.fill,scaleTop:h.top,scaleBottom:h.bottom,scaleZero:h.getBasePixel()},s.pivot()),i=0,n=d.length;n>i;++i)r.updateElement(d[i],i,t);for(g&&0!==s._model.tension&&r.updateBezierControlPoints(),i=0,n=d.length;n>i;++i)d[i].pivot()},getPointBackgroundColor:function(t,e){var i=this.chart.options.elements.point.backgroundColor,n=this.getDataset(),o=t.custom||{};return o.backgroundColor?i=o.backgroundColor:n.pointBackgroundColor?i=a.getValueAtIndexOrDefault(n.pointBackgroundColor,e,i):n.backgroundColor&&(i=n.backgroundColor),i},getPointBorderColor:function(t,e){var i=this.chart.options.elements.point.borderColor,n=this.getDataset(),o=t.custom||{};return o.borderColor?i=o.borderColor:n.pointBorderColor?i=a.getValueAtIndexOrDefault(n.pointBorderColor,e,i):n.borderColor&&(i=n.borderColor),i},getPointBorderWidth:function(t,e){var i=this.chart.options.elements.point.borderWidth,n=this.getDataset(),o=t.custom||{};return o.borderWidth?i=o.borderWidth:n.pointBorderWidth?i=a.getValueAtIndexOrDefault(n.pointBorderWidth,e,i):n.borderWidth&&(i=n.borderWidth),i},updateElement:function(t,e,i){var n,o,r=this,l=r.getMeta(),s=t.custom||{},d=r.getDataset(),u=r.index,c=d.data[e],h=r.getScaleForId(l.yAxisID),f=r.getScaleForId(l.xAxisID),g=r.chart.options.elements.point;void 0!==d.radius&&void 0===d.pointRadius&&(d.pointRadius=d.radius),void 0!==d.hitRadius&&void 0===d.pointHitRadius&&(d.pointHitRadius=d.hitRadius),n=f.getPixelForValue(c,e,u,r.chart.isCombo),o=i?h.getBasePixel():r.calculatePointY(c,e,u,r.chart.isCombo),t._xScale=f,t._yScale=h,t._datasetIndex=u,t._index=e,t._model={x:n,y:o,skip:s.skip||isNaN(n)||isNaN(o),radius:s.radius||a.getValueAtIndexOrDefault(d.pointRadius,e,g.radius),pointStyle:s.pointStyle||a.getValueAtIndexOrDefault(d.pointStyle,e,g.pointStyle),backgroundColor:r.getPointBackgroundColor(t,e),borderColor:r.getPointBorderColor(t,e),borderWidth:r.getPointBorderWidth(t,e),tension:l.dataset._model?l.dataset._model.tension:0,hitRadius:s.hitRadius||a.getValueAtIndexOrDefault(d.pointHitRadius,e,g.hitRadius)}},calculatePointY:function(t,e,a,i){var n,o,r,l=this,s=l.chart,d=l.getMeta(),u=l.getScaleForId(d.yAxisID),c=0,h=0;if(u.options.stacked){for(n=0;a>n;n++)o=s.data.datasets[n],r=s.getDatasetMeta(n),"line"===r.type&&s.isDatasetVisible(n)&&(o.data[e]<0?h+=o.data[e]||0:c+=o.data[e]||0);return 0>t?u.getPixelForValue(h+t):u.getPixelForValue(c+t)}return u.getPixelForValue(t)},updateBezierControlPoints:function(){var t,e,i,n,o,r=this.getMeta(),l=(this.chart.chartArea,r.data||[]);for(t=0,e=l.length;e>t;++t)i=l[t],n=i._model,o=a.splineCurve(a.previousItem(l,t)._model,n,a.nextItem(l,t)._model,r.dataset._model.tension),n.controlPointPreviousX=o.previous.x,n.controlPointPreviousY=o.previous.y,n.controlPointNextX=o.next.x,n.controlPointNextY=o.next.y},draw:function(t){var a,i,n=this,o=n.getMeta(),r=o.data||[],l=t||1;for(a=0,i=r.length;i>a;++a)r[a].transition(l);for(e(n.getDataset(),n.chart.options)&&o.dataset.transition(l).draw(),a=0,i=r.length;i>a;++a)r[a].draw()},setHoverStyle:function(t){var e=this.chart.data.datasets[t._datasetIndex],i=t._index,n=t.custom||{},o=t._model;o.radius=n.hoverRadius||a.getValueAtIndexOrDefault(e.pointHoverRadius,i,this.chart.options.elements.point.hoverRadius),o.backgroundColor=n.hoverBackgroundColor||a.getValueAtIndexOrDefault(e.pointHoverBackgroundColor,i,a.getHoverColor(o.backgroundColor)),o.borderColor=n.hoverBorderColor||a.getValueAtIndexOrDefault(e.pointHoverBorderColor,i,a.getHoverColor(o.borderColor)),o.borderWidth=n.hoverBorderWidth||a.getValueAtIndexOrDefault(e.pointHoverBorderWidth,i,o.borderWidth)},removeHoverStyle:function(t){var e=this,i=e.chart.data.datasets[t._datasetIndex],n=t._index,o=t.custom||{},r=t._model;void 0!==i.radius&&void 0===i.pointRadius&&(i.pointRadius=i.radius),r.radius=o.radius||a.getValueAtIndexOrDefault(i.pointRadius,n,e.chart.options.elements.point.radius),r.backgroundColor=e.getPointBackgroundColor(t,n),r.borderColor=e.getPointBorderColor(t,n),r.borderWidth=e.getPointBorderWidth(t,n)}})}},{}],19:[function(t,e,a){"use strict";e.exports=function(t){var e=t.helpers;t.defaults.polarArea={scale:{type:"radialLinear",lineArc:!0},animation:{animateRotate:!0,animateScale:!0},aspectRatio:1,legendCallback:function(t){var e=[];e.push('<ul class="'+t.id+'-legend">');var a=t.data,i=a.datasets,n=a.labels;if(i.length)for(var o=0;o<i[0].data.length;++o)e.push('<li><span style="background-color:'+i[0].backgroundColor[o]+'">'),n[o]&&e.push(n[o]),e.push("</span></li>");return e.push("</ul>"),e.join("")},legend:{labels:{generateLabels:function(t){var a=t.data;return a.labels.length&&a.datasets.length?a.labels.map(function(i,n){var o=t.getDatasetMeta(0),r=a.datasets[0],l=o.data[n],s=l.custom||{},d=e.getValueAtIndexOrDefault,u=t.options.elements.arc,c=s.backgroundColor?s.backgroundColor:d(r.backgroundColor,n,u.backgroundColor),h=s.borderColor?s.borderColor:d(r.borderColor,n,u.borderColor),f=s.borderWidth?s.borderWidth:d(r.borderWidth,n,u.borderWidth);return{text:i,fillStyle:c,strokeStyle:h,lineWidth:f,hidden:isNaN(r.data[n])||o.data[n].hidden,index:n}}):[]}},onClick:function(t,e){var a,i,n,o=e.index,r=this.chart;for(a=0,i=(r.data.datasets||[]).length;i>a;++a)n=r.getDatasetMeta(a),n.data[o].hidden=!n.data[o].hidden;r.update()}},tooltips:{callbacks:{title:function(){return""},label:function(t,e){return e.labels[t.index]+": "+t.yLabel}}}},t.controllers.polarArea=t.DatasetController.extend({dataElementType:t.elements.Arc,linkScales:e.noop,update:function(t){var a=this,i=a.chart,n=i.chartArea,o=a.getMeta(),r=i.options,l=r.elements.arc,s=Math.min(n.right-n.left,n.bottom-n.top);i.outerRadius=Math.max((s-l.borderWidth/2)/2,0),i.innerRadius=Math.max(r.cutoutPercentage?i.outerRadius/100*r.cutoutPercentage:1,0),i.radiusLength=(i.outerRadius-i.innerRadius)/i.getVisibleDatasetCount(),a.outerRadius=i.outerRadius-i.radiusLength*a.index,a.innerRadius=a.outerRadius-i.radiusLength,o.count=a.countVisibleElements(),e.each(o.data,function(e,i){a.updateElement(e,i,t)})},updateElement:function(t,a,i){for(var n=this,o=n.chart,r=o.chartArea,l=n.getDataset(),s=o.options,d=s.animation,u=(s.elements.arc,t.custom||{},o.scale),c=e.getValueAtIndexOrDefault,h=o.data.labels,f=n.calculateCircumference(l.data[a]),g=(r.left+r.right)/2,p=(r.top+r.bottom)/2,m=0,b=n.getMeta(),v=0;a>v;++v)isNaN(l.data[v])||b.data[v].hidden||++m;var x=-.5*Math.PI,y=t.hidden?0:u.getDistanceFromCenterForValue(l.data[a]),k=x+f*m,S=k+(t.hidden?0:f),w=d.animateScale?0:u.getDistanceFromCenterForValue(l.data[a]);e.extend(t,{_datasetIndex:n.index,_index:a,_scale:u,_model:{x:g,y:p,innerRadius:0,outerRadius:i?w:y,startAngle:i&&d.animateRotate?x:k,endAngle:i&&d.animateRotate?x:S,label:c(h,a,h[a])}}),n.removeHoverStyle(t),t.pivot()},removeHoverStyle:function(e){t.DatasetController.prototype.removeHoverStyle.call(this,e,this.chart.options.elements.arc)},countVisibleElements:function(){var t=this.getDataset(),a=this.getMeta(),i=0;return e.each(a.data,function(e,a){isNaN(t.data[a])||e.hidden||i++}),i},calculateCircumference:function(t){var e=this.getMeta().count;return e>0&&!isNaN(t)?2*Math.PI/e:0}})}},{}],20:[function(t,e,a){"use strict";e.exports=function(t){var e=t.helpers;t.defaults.radar={scale:{type:"radialLinear"},elements:{line:{tension:0}}},t.controllers.radar=t.DatasetController.extend({datasetElementType:t.elements.Line,dataElementType:t.elements.Point,linkScales:e.noop,addElementAndReset:function(e){t.DatasetController.prototype.addElementAndReset.call(this,e),this.updateBezierControlPoints()},update:function(t){var a=this,i=a.getMeta(),n=i.dataset,o=i.data,r=n.custom||{},l=a.getDataset(),s=a.chart.options.elements.line,d=a.chart.scale;void 0!==l.tension&&void 0===l.lineTension&&(l.lineTension=l.tension),e.extend(i.dataset,{_datasetIndex:a.index,_children:o,_loop:!0,_model:{tension:r.tension?r.tension:e.getValueOrDefault(l.lineTension,s.tension),backgroundColor:r.backgroundColor?r.backgroundColor:l.backgroundColor||s.backgroundColor,borderWidth:r.borderWidth?r.borderWidth:l.borderWidth||s.borderWidth,borderColor:r.borderColor?r.borderColor:l.borderColor||s.borderColor,fill:r.fill?r.fill:void 0!==l.fill?l.fill:s.fill,borderCapStyle:r.borderCapStyle?r.borderCapStyle:l.borderCapStyle||s.borderCapStyle,borderDash:r.borderDash?r.borderDash:l.borderDash||s.borderDash,borderDashOffset:r.borderDashOffset?r.borderDashOffset:l.borderDashOffset||s.borderDashOffset,borderJoinStyle:r.borderJoinStyle?r.borderJoinStyle:l.borderJoinStyle||s.borderJoinStyle,scaleTop:d.top,scaleBottom:d.bottom,scaleZero:d.getBasePosition()}}),i.dataset.pivot(),e.each(o,function(e,i){a.updateElement(e,i,t)},a),a.updateBezierControlPoints()},updateElement:function(t,a,i){var n=this,o=t.custom||{},r=n.getDataset(),l=n.chart.scale,s=n.chart.options.elements.point,d=l.getPointPositionForValue(a,r.data[a]);e.extend(t,{_datasetIndex:n.index,_index:a,_scale:l,_model:{x:i?l.xCenter:d.x,y:i?l.yCenter:d.y,tension:o.tension?o.tension:e.getValueOrDefault(r.tension,n.chart.options.elements.line.tension),radius:o.radius?o.radius:e.getValueAtIndexOrDefault(r.pointRadius,a,s.radius),backgroundColor:o.backgroundColor?o.backgroundColor:e.getValueAtIndexOrDefault(r.pointBackgroundColor,a,s.backgroundColor),borderColor:o.borderColor?o.borderColor:e.getValueAtIndexOrDefault(r.pointBorderColor,a,s.borderColor),borderWidth:o.borderWidth?o.borderWidth:e.getValueAtIndexOrDefault(r.pointBorderWidth,a,s.borderWidth),pointStyle:o.pointStyle?o.pointStyle:e.getValueAtIndexOrDefault(r.pointStyle,a,s.pointStyle),hitRadius:o.hitRadius?o.hitRadius:e.getValueAtIndexOrDefault(r.hitRadius,a,s.hitRadius)}}),t._model.skip=o.skip?o.skip:isNaN(t._model.x)||isNaN(t._model.y)},updateBezierControlPoints:function(){var t=this.chart.chartArea,a=this.getMeta();e.each(a.data,function(i,n){var o=i._model,r=e.splineCurve(e.previousItem(a.data,n,!0)._model,o,e.nextItem(a.data,n,!0)._model,o.tension);o.controlPointPreviousX=Math.max(Math.min(r.previous.x,t.right),t.left),o.controlPointPreviousY=Math.max(Math.min(r.previous.y,t.bottom),t.top),o.controlPointNextX=Math.max(Math.min(r.next.x,t.right),t.left),o.controlPointNextY=Math.max(Math.min(r.next.y,t.bottom),t.top),i.pivot()})},draw:function(t){var a=this.getMeta(),i=t||1;e.each(a.data,function(t,e){t.transition(i)}),a.dataset.transition(i).draw(),e.each(a.data,function(t){t.draw()})},setHoverStyle:function(t){var a=this.chart.data.datasets[t._datasetIndex],i=t.custom||{},n=t._index,o=t._model;o.radius=i.hoverRadius?i.hoverRadius:e.getValueAtIndexOrDefault(a.pointHoverRadius,n,this.chart.options.elements.point.hoverRadius),o.backgroundColor=i.hoverBackgroundColor?i.hoverBackgroundColor:e.getValueAtIndexOrDefault(a.pointHoverBackgroundColor,n,e.getHoverColor(o.backgroundColor)),o.borderColor=i.hoverBorderColor?i.hoverBorderColor:e.getValueAtIndexOrDefault(a.pointHoverBorderColor,n,e.getHoverColor(o.borderColor)),o.borderWidth=i.hoverBorderWidth?i.hoverBorderWidth:e.getValueAtIndexOrDefault(a.pointHoverBorderWidth,n,o.borderWidth)},removeHoverStyle:function(t){var a=this.chart.data.datasets[t._datasetIndex],i=t.custom||{},n=t._index,o=t._model,r=this.chart.options.elements.point;o.radius=i.radius?i.radius:e.getValueAtIndexOrDefault(a.radius,n,r.radius),o.backgroundColor=i.backgroundColor?i.backgroundColor:e.getValueAtIndexOrDefault(a.pointBackgroundColor,n,r.backgroundColor),o.borderColor=i.borderColor?i.borderColor:e.getValueAtIndexOrDefault(a.pointBorderColor,n,r.borderColor),o.borderWidth=i.borderWidth?i.borderWidth:e.getValueAtIndexOrDefault(a.pointBorderWidth,n,r.borderWidth)}})}},{}],21:[function(t,e,a){"use strict";e.exports=function(t){var e=t.helpers;t.defaults.global.animation={duration:1e3,easing:"easeOutQuart",onProgress:e.noop,onComplete:e.noop},t.Animation=t.Element.extend({currentStep:null,numSteps:60,easing:"",render:null,onAnimationProgress:null,onAnimationComplete:null}),t.animationService={frameDuration:17,animations:[],dropFrames:0,request:null,addAnimation:function(t,e,a,i){var n=this;i||(t.animating=!0);for(var o=0;o<n.animations.length;++o)if(n.animations[o].chartInstance===t)return void(n.animations[o].animationObject=e);n.animations.push({chartInstance:t,animationObject:e}),1===n.animations.length&&n.requestAnimationFrame()},cancelAnimation:function(t){var a=e.findIndex(this.animations,function(e){return e.chartInstance===t});-1!==a&&(this.animations.splice(a,1),t.animating=!1)},requestAnimationFrame:function(){var t=this;null===t.request&&(t.request=e.requestAnimFrame.call(window,function(){t.request=null,t.startDigest()}))},startDigest:function(){var t=this,e=Date.now(),a=0;t.dropFrames>1&&(a=Math.floor(t.dropFrames),t.dropFrames=t.dropFrames%1);for(var i=0;i<t.animations.length;)null===t.animations[i].animationObject.currentStep&&(t.animations[i].animationObject.currentStep=0),t.animations[i].animationObject.currentStep+=1+a,t.animations[i].animationObject.currentStep>t.animations[i].animationObject.numSteps&&(t.animations[i].animationObject.currentStep=t.animations[i].animationObject.numSteps),t.animations[i].animationObject.render(t.animations[i].chartInstance,t.animations[i].animationObject),t.animations[i].animationObject.onAnimationProgress&&t.animations[i].animationObject.onAnimationProgress.call&&t.animations[i].animationObject.onAnimationProgress.call(t.animations[i].chartInstance,t.animations[i]),t.animations[i].animationObject.currentStep===t.animations[i].animationObject.numSteps?(t.animations[i].animationObject.onAnimationComplete&&t.animations[i].animationObject.onAnimationComplete.call&&t.animations[i].animationObject.onAnimationComplete.call(t.animations[i].chartInstance,t.animations[i]),t.animations[i].chartInstance.animating=!1,t.animations.splice(i,1)):++i;var n=Date.now(),o=(n-e)/t.frameDuration;t.dropFrames+=o,t.animations.length>0&&t.requestAnimationFrame()}}}},{}],22:[function(t,e,a){"use strict";e.exports=function(t){var e=t.helpers;t.types={},t.instances={},t.controllers={},t.Controller=function(a){return this.chart=a,this.config=a.config,this.options=this.config.options=e.configMerge(t.defaults.global,t.defaults[this.config.type],this.config.options||{}),this.id=e.uid(),Object.defineProperty(this,"data",{get:function(){return this.config.data}}),t.instances[this.id]=this,this.options.responsive&&this.resize(!0),this.initialize(),this},e.extend(t.Controller.prototype,{initialize:function(){var e=this;return t.plugins.notify("beforeInit",[e]),e.bindEvents(),e.ensureScalesHaveIDs(),e.buildOrUpdateControllers(),e.buildScales(),e.updateLayout(),e.resetElements(),e.initToolTip(),e.update(),t.plugins.notify("afterInit",[e]),e},clear:function(){return e.clear(this.chart),this},stop:function(){return t.animationService.cancelAnimation(this),this},resize:function(a){var i=this,n=i.chart,o=n.canvas,r=e.getMaximumWidth(o),l=n.aspectRatio,s=i.options.maintainAspectRatio&&isNaN(l)===!1&&isFinite(l)&&0!==l?r/l:e.getMaximumHeight(o),d=n.width!==r||n.height!==s;if(!d)return i;o.width=n.width=r,o.height=n.height=s,e.retinaScale(n);var u={width:r,height:s};return t.plugins.notify("resize",[i,u]),i.options.onResize&&i.options.onResize(i,u),a||(i.stop(),i.update(i.options.responsiveAnimationDuration)),i},ensureScalesHaveIDs:function(){var t=this.options,a=t.scales||{},i=t.scale;e.each(a.xAxes,function(t,e){t.id=t.id||"x-axis-"+e}),e.each(a.yAxes,function(t,e){t.id=t.id||"y-axis-"+e}),i&&(i.id=i.id||"scale")},buildScales:function(){var a=this,i=a.options,n=a.scales={},o=[];i.scales&&(o=o.concat((i.scales.xAxes||[]).map(function(t){return{options:t,dtype:"category"}}),(i.scales.yAxes||[]).map(function(t){return{options:t,dtype:"linear"}}))),i.scale&&o.push({options:i.scale,dtype:"radialLinear",isDefault:!0}),e.each(o,function(i,o){var r=i.options,l=e.getValueOrDefault(r.type,i.dtype),s=t.scaleService.getScaleConstructor(l);if(s){var d=new s({id:r.id,options:r,ctx:a.chart.ctx,chart:a});n[d.id]=d,i.isDefault&&(a.scale=d)}}),t.scaleService.addScalesToLayout(this)},updateLayout:function(){t.layoutService.update(this,this.chart.width,this.chart.height)},buildOrUpdateControllers:function(){var a=this,i=[],n=[];if(e.each(a.data.datasets,function(e,o){var r=a.getDatasetMeta(o);r.type||(r.type=e.type||a.config.type),i.push(r.type),r.controller?r.controller.updateIndex(o):(r.controller=new t.controllers[r.type](a,o),n.push(r.controller))},a),i.length>1)for(var o=1;o<i.length;o++)if(i[o]!==i[o-1]){a.isCombo=!0;break}return n},resetElements:function(){var t=this;e.each(t.data.datasets,function(e,a){t.getDatasetMeta(a).controller.reset()},t)},update:function(a,i){var n=this;t.plugins.notify("beforeUpdate",[n]),n.tooltip._data=n.data;var o=n.buildOrUpdateControllers();e.each(n.data.datasets,function(t,e){n.getDatasetMeta(e).controller.buildOrUpdateElements()},n),t.layoutService.update(n,n.chart.width,n.chart.height),t.plugins.notify("afterScaleUpdate",[n]),e.each(o,function(t){t.reset()}),n.updateDatasets(),t.plugins.notify("afterUpdate",[n]),n.render(a,i)},updateDatasets:function(){var e,a,i=this;if(t.plugins.notify("beforeDatasetsUpdate",[i])){for(e=0,a=i.data.datasets.length;a>e;++e)i.getDatasetMeta(e).controller.update();t.plugins.notify("afterDatasetsUpdate",[i])}},render:function(a,i){var n=this;t.plugins.notify("beforeRender",[n]);var o=n.options.animation;if(o&&("undefined"!=typeof a&&0!==a||"undefined"==typeof a&&0!==o.duration)){var r=new t.Animation;r.numSteps=(a||o.duration)/16.66,r.easing=o.easing,r.render=function(t,a){var i=e.easingEffects[a.easing],n=a.currentStep/a.numSteps,o=i(n);t.draw(o,n,a.currentStep)},r.onAnimationProgress=o.onProgress,r.onAnimationComplete=o.onComplete,t.animationService.addAnimation(n,r,a,i)}else n.draw(),o&&o.onComplete&&o.onComplete.call&&o.onComplete.call(n);return n},draw:function(a){var i=this,n=a||1;i.clear(),t.plugins.notify("beforeDraw",[i,n]),e.each(i.boxes,function(t){t.draw(i.chartArea)},i),i.scale&&i.scale.draw(),t.plugins.notify("beforeDatasetsDraw",[i,n]),e.each(i.data.datasets,function(t,e){i.isDatasetVisible(e)&&i.getDatasetMeta(e).controller.draw(a)},i,!0),t.plugins.notify("afterDatasetsDraw",[i,n]),i.tooltip.transition(n).draw(),t.plugins.notify("afterDraw",[i,n])},getElementAtEvent:function(t){var a=this,i=e.getRelativePosition(t,a.chart),n=[];return e.each(a.data.datasets,function(t,o){if(a.isDatasetVisible(o)){var r=a.getDatasetMeta(o);e.each(r.data,function(t,e){return t.inRange(i.x,i.y)?(n.push(t),n):void 0})}}),n},getElementsAtEvent:function(t){var a=this,i=e.getRelativePosition(t,a.chart),n=[],o=function(){if(a.data.datasets)for(var t=0;t<a.data.datasets.length;t++){var e=a.getDatasetMeta(t);if(a.isDatasetVisible(t))for(var n=0;n<e.data.length;n++)if(e.data[n].inRange(i.x,i.y))return e.data[n]}}.call(a);return o?(e.each(a.data.datasets,function(t,e){if(a.isDatasetVisible(e)){var i=a.getDatasetMeta(e);n.push(i.data[o._index])}},a),n):n},getElementsAtEventForMode:function(t,e){var a=this;switch(e){case"single":return a.getElementAtEvent(t);case"label":return a.getElementsAtEvent(t);case"dataset":return a.getDatasetAtEvent(t);default:return t}},getDatasetAtEvent:function(t){var e=this.getElementAtEvent(t);return e.length>0&&(e=this.getDatasetMeta(e[0]._datasetIndex).data),e},getDatasetMeta:function(t){var e=this,a=e.data.datasets[t];a._meta||(a._meta={});var i=a._meta[e.id];return i||(i=a._meta[e.id]={type:null,data:[],dataset:null,controller:null,hidden:null,xAxisID:null,yAxisID:null}),i},getVisibleDatasetCount:function(){for(var t=0,e=0,a=this.data.datasets.length;a>e;++e)this.isDatasetVisible(e)&&t++;return t},isDatasetVisible:function(t){var e=this.getDatasetMeta(t);return"boolean"==typeof e.hidden?!e.hidden:!this.data.datasets[t].hidden},generateLegend:function(){return this.options.legendCallback(this)},destroy:function(){var a=this;a.stop(),a.clear(),e.unbindEvents(a,a.events),e.removeResizeListener(a.chart.canvas.parentNode);var i=a.chart.canvas;i.width=a.chart.width,i.height=a.chart.height,void 0!==a.chart.originalDevicePixelRatio&&a.chart.ctx.scale(1/a.chart.originalDevicePixelRatio,1/a.chart.originalDevicePixelRatio),i.style.width=a.chart.originalCanvasStyleWidth,i.style.height=a.chart.originalCanvasStyleHeight,t.plugins.notify("destroy",[a]),delete t.instances[a.id]},toBase64Image:function(){return this.chart.canvas.toDataURL.apply(this.chart.canvas,arguments)},initToolTip:function(){var e=this;e.tooltip=new t.Tooltip({_chart:e.chart,_chartInstance:e,_data:e.data,_options:e.options.tooltips},e)},bindEvents:function(){var t=this;e.bindEvents(t,t.options.events,function(e){t.eventHandler(e)})},updateHoverStyle:function(t,e,a){var i,n,o,r=a?"setHoverStyle":"removeHoverStyle";switch(e){case"single":t=[t[0]];break;case"label":case"dataset":break;default:return}for(n=0,o=t.length;o>n;++n)i=t[n],i&&this.getDatasetMeta(i._datasetIndex).controller[r](i)},eventHandler:function(t){var a=this,i=a.tooltip,n=a.options||{},o=n.hover,r=n.tooltips;return a.lastActive=a.lastActive||[],a.lastTooltipActive=a.lastTooltipActive||[],"mouseout"===t.type?(a.active=[],a.tooltipActive=[]):(a.active=a.getElementsAtEventForMode(t,o.mode),a.tooltipActive=a.getElementsAtEventForMode(t,r.mode)),o.onHover&&o.onHover.call(a,a.active),("mouseup"===t.type||"click"===t.type)&&(n.onClick&&n.onClick.call(a,t,a.active),a.legend&&a.legend.handleEvent&&a.legend.handleEvent(t)),a.lastActive.length&&a.updateHoverStyle(a.lastActive,o.mode,!1),a.active.length&&o.mode&&a.updateHoverStyle(a.active,o.mode,!0),(r.enabled||r.custom)&&(i.initialize(),i._active=a.tooltipActive,i.update(!0)),i.pivot(),a.animating||e.arrayEquals(a.active,a.lastActive)&&e.arrayEquals(a.tooltipActive,a.lastTooltipActive)||(a.stop(),(r.enabled||r.custom)&&i.update(!0),a.render(o.animationDuration,!0)),a.lastActive=a.active,a.lastTooltipActive=a.tooltipActive,a}})}},{}],23:[function(t,e,a){"use strict";e.exports=function(t){var e=t.helpers,a=e.noop;t.DatasetController=function(t,e){this.initialize.call(this,t,e)},e.extend(t.DatasetController.prototype,{datasetElementType:null,dataElementType:null,initialize:function(t,e){var a=this;a.chart=t,a.index=e,a.linkScales(),a.addElements()},updateIndex:function(t){this.index=t},linkScales:function(){var t=this,e=t.getMeta(),a=t.getDataset();null===e.xAxisID&&(e.xAxisID=a.xAxisID||t.chart.options.scales.xAxes[0].id),null===e.yAxisID&&(e.yAxisID=a.yAxisID||t.chart.options.scales.yAxes[0].id);
},getDataset:function(){return this.chart.data.datasets[this.index]},getMeta:function(){return this.chart.getDatasetMeta(this.index)},getScaleForId:function(t){return this.chart.scales[t]},reset:function(){this.update(!0)},createMetaDataset:function(){var t=this,e=t.datasetElementType;return e&&new e({_chart:t.chart.chart,_datasetIndex:t.index})},createMetaData:function(t){var e=this,a=e.dataElementType;return a&&new a({_chart:e.chart.chart,_datasetIndex:e.index,_index:t})},addElements:function(){var t,e,a=this,i=a.getMeta(),n=a.getDataset().data||[],o=i.data;for(t=0,e=n.length;e>t;++t)o[t]=o[t]||a.createMetaData(i,t);i.dataset=i.dataset||a.createMetaDataset()},addElementAndReset:function(t){var e=this,a=e.createMetaData(t);e.getMeta().data.splice(t,0,a),e.updateElement(a,t,!0)},buildOrUpdateElements:function(){var t=this.getMeta(),e=t.data,a=this.getDataset().data.length,i=e.length;if(i>a)e.splice(a,i-a);else if(a>i)for(var n=i;a>n;++n)this.addElementAndReset(n)},update:a,draw:function(t){var a=t||1;e.each(this.getMeta().data,function(t,e){t.transition(a).draw()})},removeHoverStyle:function(t,a){var i=this.chart.data.datasets[t._datasetIndex],n=t._index,o=t.custom||{},r=e.getValueAtIndexOrDefault,l=(e.color,t._model);l.backgroundColor=o.backgroundColor?o.backgroundColor:r(i.backgroundColor,n,a.backgroundColor),l.borderColor=o.borderColor?o.borderColor:r(i.borderColor,n,a.borderColor),l.borderWidth=o.borderWidth?o.borderWidth:r(i.borderWidth,n,a.borderWidth)},setHoverStyle:function(t){var a=this.chart.data.datasets[t._datasetIndex],i=t._index,n=t.custom||{},o=e.getValueAtIndexOrDefault,r=(e.color,e.getHoverColor),l=t._model;l.backgroundColor=n.hoverBackgroundColor?n.hoverBackgroundColor:o(a.hoverBackgroundColor,i,r(l.backgroundColor)),l.borderColor=n.hoverBorderColor?n.hoverBorderColor:o(a.hoverBorderColor,i,r(l.borderColor)),l.borderWidth=n.hoverBorderWidth?n.hoverBorderWidth:o(a.hoverBorderWidth,i,l.borderWidth)}}),t.DatasetController.extend=e.inherits}},{}],24:[function(t,e,a){"use strict";e.exports=function(t){var e=t.helpers;t.elements={},t.Element=function(t){e.extend(this,t),this.initialize.apply(this,arguments)},e.extend(t.Element.prototype,{initialize:function(){this.hidden=!1},pivot:function(){var t=this;return t._view||(t._view=e.clone(t._model)),t._start=e.clone(t._view),t},transition:function(t){var a=this;return a._view||(a._view=e.clone(a._model)),1===t?(a._view=a._model,a._start=null,a):(a._start||a.pivot(),e.each(a._model,function(i,n){if("_"===n[0]);else if(a._view.hasOwnProperty(n))if(i===a._view[n]);else if("string"==typeof i)try{var o=e.color(a._model[n]).mix(e.color(a._start[n]),t);a._view[n]=o.rgbString()}catch(r){a._view[n]=i}else if("number"==typeof i){var l=void 0!==a._start[n]&&isNaN(a._start[n])===!1?a._start[n]:0;a._view[n]=(a._model[n]-l)*t+l}else a._view[n]=i;else"number"!=typeof i||isNaN(a._view[n])?a._view[n]=i:a._view[n]=i*t},a),a)},tooltipPosition:function(){return{x:this._model.x,y:this._model.y}},hasValue:function(){return e.isNumber(this._model.x)&&e.isNumber(this._model.y)}}),t.Element.extend=e.inherits}},{}],25:[function(t,e,a){"use strict";var i=t(3);e.exports=function(t){function e(t,e,a){var i;return"string"==typeof t?(i=parseInt(t,10),-1!=t.indexOf("%")&&(i=i/100*e.parentNode[a])):i=t,i}function a(t){return void 0!==t&&null!==t&&"none"!==t}function n(t,i,n){var o=document.defaultView,r=t.parentNode,l=o.getComputedStyle(t)[i],s=o.getComputedStyle(r)[i],d=a(l),u=a(s),c=Number.POSITIVE_INFINITY;return d||u?Math.min(d?e(l,t,n):c,u?e(s,r,n):c):"none"}var o=t.helpers={};o.each=function(t,e,a,i){var n,r;if(o.isArray(t))if(r=t.length,i)for(n=r-1;n>=0;n--)e.call(a,t[n],n);else for(n=0;r>n;n++)e.call(a,t[n],n);else if("object"==typeof t){var l=Object.keys(t);for(r=l.length,n=0;r>n;n++)e.call(a,t[l[n]],l[n])}},o.clone=function(t){var e={};return o.each(t,function(t,a){o.isArray(t)?e[a]=t.slice(0):"object"==typeof t&&null!==t?e[a]=o.clone(t):e[a]=t}),e},o.extend=function(t){for(var e=function(e,a){t[a]=e},a=1,i=arguments.length;i>a;a++)o.each(arguments[a],e);return t},o.configMerge=function(e){var a=o.clone(e);return o.each(Array.prototype.slice.call(arguments,1),function(e){o.each(e,function(e,i){if("scales"===i)a[i]=o.scaleMerge(a.hasOwnProperty(i)?a[i]:{},e);else if("scale"===i)a[i]=o.configMerge(a.hasOwnProperty(i)?a[i]:{},t.scaleService.getScaleDefaults(e.type),e);else if(a.hasOwnProperty(i)&&o.isArray(a[i])&&o.isArray(e)){var n=a[i];o.each(e,function(t,e){e<n.length?"object"==typeof n[e]&&null!==n[e]&&"object"==typeof t&&null!==t?n[e]=o.configMerge(n[e],t):n[e]=t:n.push(t)})}else a.hasOwnProperty(i)&&"object"==typeof a[i]&&null!==a[i]&&"object"==typeof e?a[i]=o.configMerge(a[i],e):a[i]=e})}),a},o.scaleMerge=function(e,a){var i=o.clone(e);return o.each(a,function(e,a){"xAxes"===a||"yAxes"===a?i.hasOwnProperty(a)?o.each(e,function(e,n){var r=o.getValueOrDefault(e.type,"xAxes"===a?"category":"linear"),l=t.scaleService.getScaleDefaults(r);n>=i[a].length||!i[a][n].type?i[a].push(o.configMerge(l,e)):e.type&&e.type!==i[a][n].type?i[a][n]=o.configMerge(i[a][n],l,e):i[a][n]=o.configMerge(i[a][n],e)}):(i[a]=[],o.each(e,function(e){var n=o.getValueOrDefault(e.type,"xAxes"===a?"category":"linear");i[a].push(o.configMerge(t.scaleService.getScaleDefaults(n),e))})):i.hasOwnProperty(a)&&"object"==typeof i[a]&&null!==i[a]&&"object"==typeof e?i[a]=o.configMerge(i[a],e):i[a]=e}),i},o.getValueAtIndexOrDefault=function(t,e,a){return void 0===t||null===t?a:o.isArray(t)?e<t.length?t[e]:a:t},o.getValueOrDefault=function(t,e){return void 0===t?e:t},o.indexOf=Array.prototype.indexOf?function(t,e){return t.indexOf(e)}:function(t,e){for(var a=0,i=t.length;i>a;++a)if(t[a]===e)return a;return-1},o.where=function(t,e){if(o.isArray(t)&&Array.prototype.filter)return t.filter(e);var a=[];return o.each(t,function(t){e(t)&&a.push(t)}),a},o.findIndex=Array.prototype.findIndex?function(t,e,a){return t.findIndex(e,a)}:function(t,e,a){a=void 0===a?t:a;for(var i=0,n=t.length;n>i;++i)if(e.call(a,t[i],i,t))return i;return-1},o.findNextWhere=function(t,e,a){(void 0===a||null===a)&&(a=-1);for(var i=a+1;i<t.length;i++){var n=t[i];if(e(n))return n}},o.findPreviousWhere=function(t,e,a){(void 0===a||null===a)&&(a=t.length);for(var i=a-1;i>=0;i--){var n=t[i];if(e(n))return n}},o.inherits=function(t){var e=this,a=t&&t.hasOwnProperty("constructor")?t.constructor:function(){return e.apply(this,arguments)},i=function(){this.constructor=a};return i.prototype=e.prototype,a.prototype=new i,a.extend=o.inherits,t&&o.extend(a.prototype,t),a.__super__=e.prototype,a},o.noop=function(){},o.uid=function(){var t=0;return function(){return t++}}(),o.isNumber=function(t){return!isNaN(parseFloat(t))&&isFinite(t)},o.almostEquals=function(t,e,a){return Math.abs(t-e)<a},o.max=function(t){return t.reduce(function(t,e){return isNaN(e)?t:Math.max(t,e)},Number.NEGATIVE_INFINITY)},o.min=function(t){return t.reduce(function(t,e){return isNaN(e)?t:Math.min(t,e)},Number.POSITIVE_INFINITY)},o.sign=Math.sign?function(t){return Math.sign(t)}:function(t){return t=+t,0===t||isNaN(t)?t:t>0?1:-1},o.log10=Math.log10?function(t){return Math.log10(t)}:function(t){return Math.log(t)/Math.LN10},o.toRadians=function(t){return t*(Math.PI/180)},o.toDegrees=function(t){return t*(180/Math.PI)},o.getAngleFromPoint=function(t,e){var a=e.x-t.x,i=e.y-t.y,n=Math.sqrt(a*a+i*i),o=Math.atan2(i,a);return o<-.5*Math.PI&&(o+=2*Math.PI),{angle:o,distance:n}},o.aliasPixel=function(t){return t%2===0?0:.5},o.splineCurve=function(t,e,a,i){var n=t.skip?e:t,o=e,r=a.skip?e:a,l=Math.sqrt(Math.pow(o.x-n.x,2)+Math.pow(o.y-n.y,2)),s=Math.sqrt(Math.pow(r.x-o.x,2)+Math.pow(r.y-o.y,2)),d=l/(l+s),u=s/(l+s);d=isNaN(d)?0:d,u=isNaN(u)?0:u;var c=i*d,h=i*u;return{previous:{x:o.x-c*(r.x-n.x),y:o.y-c*(r.y-n.y)},next:{x:o.x+h*(r.x-n.x),y:o.y+h*(r.y-n.y)}}},o.nextItem=function(t,e,a){return a?e>=t.length-1?t[0]:t[e+1]:e>=t.length-1?t[t.length-1]:t[e+1]},o.previousItem=function(t,e,a){return a?0>=e?t[t.length-1]:t[e-1]:0>=e?t[0]:t[e-1]},o.niceNum=function(t,e){var a,i=Math.floor(o.log10(t)),n=t/Math.pow(10,i);return a=e?1.5>n?1:3>n?2:7>n?5:10:1>=n?1:2>=n?2:5>=n?5:10,a*Math.pow(10,i)};var r=o.easingEffects={linear:function(t){return t},easeInQuad:function(t){return t*t},easeOutQuad:function(t){return-1*t*(t-2)},easeInOutQuad:function(t){return(t/=.5)<1?.5*t*t:-0.5*(--t*(t-2)-1)},easeInCubic:function(t){return t*t*t},easeOutCubic:function(t){return 1*((t=t/1-1)*t*t+1)},easeInOutCubic:function(t){return(t/=.5)<1?.5*t*t*t:.5*((t-=2)*t*t+2)},easeInQuart:function(t){return t*t*t*t},easeOutQuart:function(t){return-1*((t=t/1-1)*t*t*t-1)},easeInOutQuart:function(t){return(t/=.5)<1?.5*t*t*t*t:-0.5*((t-=2)*t*t*t-2)},easeInQuint:function(t){return 1*(t/=1)*t*t*t*t},easeOutQuint:function(t){return 1*((t=t/1-1)*t*t*t*t+1)},easeInOutQuint:function(t){return(t/=.5)<1?.5*t*t*t*t*t:.5*((t-=2)*t*t*t*t+2)},easeInSine:function(t){return-1*Math.cos(t/1*(Math.PI/2))+1},easeOutSine:function(t){return 1*Math.sin(t/1*(Math.PI/2))},easeInOutSine:function(t){return-0.5*(Math.cos(Math.PI*t/1)-1)},easeInExpo:function(t){return 0===t?1:1*Math.pow(2,10*(t/1-1))},easeOutExpo:function(t){return 1===t?1:1*(-Math.pow(2,-10*t/1)+1)},easeInOutExpo:function(t){return 0===t?0:1===t?1:(t/=.5)<1?.5*Math.pow(2,10*(t-1)):.5*(-Math.pow(2,-10*--t)+2)},easeInCirc:function(t){return t>=1?t:-1*(Math.sqrt(1-(t/=1)*t)-1)},easeOutCirc:function(t){return 1*Math.sqrt(1-(t=t/1-1)*t)},easeInOutCirc:function(t){return(t/=.5)<1?-0.5*(Math.sqrt(1-t*t)-1):.5*(Math.sqrt(1-(t-=2)*t)+1)},easeInElastic:function(t){var e=1.70158,a=0,i=1;return 0===t?0:1===(t/=1)?1:(a||(a=.3),i<Math.abs(1)?(i=1,e=a/4):e=a/(2*Math.PI)*Math.asin(1/i),-(i*Math.pow(2,10*(t-=1))*Math.sin((1*t-e)*(2*Math.PI)/a)))},easeOutElastic:function(t){var e=1.70158,a=0,i=1;return 0===t?0:1===(t/=1)?1:(a||(a=.3),i<Math.abs(1)?(i=1,e=a/4):e=a/(2*Math.PI)*Math.asin(1/i),i*Math.pow(2,-10*t)*Math.sin((1*t-e)*(2*Math.PI)/a)+1)},easeInOutElastic:function(t){var e=1.70158,a=0,i=1;return 0===t?0:2===(t/=.5)?1:(a||(a=1*(.3*1.5)),i<Math.abs(1)?(i=1,e=a/4):e=a/(2*Math.PI)*Math.asin(1/i),1>t?-.5*(i*Math.pow(2,10*(t-=1))*Math.sin((1*t-e)*(2*Math.PI)/a)):i*Math.pow(2,-10*(t-=1))*Math.sin((1*t-e)*(2*Math.PI)/a)*.5+1)},easeInBack:function(t){var e=1.70158;return 1*(t/=1)*t*((e+1)*t-e)},easeOutBack:function(t){var e=1.70158;return 1*((t=t/1-1)*t*((e+1)*t+e)+1)},easeInOutBack:function(t){var e=1.70158;return(t/=.5)<1?.5*(t*t*(((e*=1.525)+1)*t-e)):.5*((t-=2)*t*(((e*=1.525)+1)*t+e)+2)},easeInBounce:function(t){return 1-r.easeOutBounce(1-t)},easeOutBounce:function(t){return(t/=1)<1/2.75?1*(7.5625*t*t):2/2.75>t?1*(7.5625*(t-=1.5/2.75)*t+.75):2.5/2.75>t?1*(7.5625*(t-=2.25/2.75)*t+.9375):1*(7.5625*(t-=2.625/2.75)*t+.984375)},easeInOutBounce:function(t){return.5>t?.5*r.easeInBounce(2*t):.5*r.easeOutBounce(2*t-1)+.5}};o.requestAnimFrame=function(){return window.requestAnimationFrame||window.webkitRequestAnimationFrame||window.mozRequestAnimationFrame||window.oRequestAnimationFrame||window.msRequestAnimationFrame||function(t){return window.setTimeout(t,1e3/60)}}(),o.cancelAnimFrame=function(){return window.cancelAnimationFrame||window.webkitCancelAnimationFrame||window.mozCancelAnimationFrame||window.oCancelAnimationFrame||window.msCancelAnimationFrame||function(t){return window.clearTimeout(t,1e3/60)}}(),o.getRelativePosition=function(t,e){var a,i,n=t.originalEvent||t,r=t.currentTarget||t.srcElement,l=r.getBoundingClientRect(),s=n.touches;s&&s.length>0?(a=s[0].clientX,i=s[0].clientY):(a=n.clientX,i=n.clientY);var d=parseFloat(o.getStyle(r,"padding-left")),u=parseFloat(o.getStyle(r,"padding-top")),c=parseFloat(o.getStyle(r,"padding-right")),h=parseFloat(o.getStyle(r,"padding-bottom")),f=l.right-l.left-d-c,g=l.bottom-l.top-u-h;return a=Math.round((a-l.left-d)/f*r.width/e.currentDevicePixelRatio),i=Math.round((i-l.top-u)/g*r.height/e.currentDevicePixelRatio),{x:a,y:i}},o.addEvent=function(t,e,a){t.addEventListener?t.addEventListener(e,a):t.attachEvent?t.attachEvent("on"+e,a):t["on"+e]=a},o.removeEvent=function(t,e,a){t.removeEventListener?t.removeEventListener(e,a,!1):t.detachEvent?t.detachEvent("on"+e,a):t["on"+e]=o.noop},o.bindEvents=function(t,e,a){var i=t.events=t.events||{};o.each(e,function(e){i[e]=function(){a.apply(t,arguments)},o.addEvent(t.chart.canvas,e,i[e])})},o.unbindEvents=function(t,e){var a=t.chart.canvas;o.each(e,function(t,e){o.removeEvent(a,e,t)})},o.getConstraintWidth=function(t){return n(t,"max-width","clientWidth")},o.getConstraintHeight=function(t){return n(t,"max-height","clientHeight")},o.getMaximumWidth=function(t){var e=t.parentNode,a=parseInt(o.getStyle(e,"padding-left"))+parseInt(o.getStyle(e,"padding-right")),i=e.clientWidth-a,n=o.getConstraintWidth(t);return isNaN(n)?i:Math.min(i,n)},o.getMaximumHeight=function(t){var e=t.parentNode,a=parseInt(o.getStyle(e,"padding-top"))+parseInt(o.getStyle(e,"padding-bottom")),i=e.clientHeight-a,n=o.getConstraintHeight(t);return isNaN(n)?i:Math.min(i,n)},o.getStyle=function(t,e){return t.currentStyle?t.currentStyle[e]:document.defaultView.getComputedStyle(t,null).getPropertyValue(e)},o.retinaScale=function(t){var e=t.ctx,a=t.canvas,i=a.width,n=a.height,o=t.currentDevicePixelRatio=window.devicePixelRatio||1;1!==o&&(a.height=n*o,a.width=i*o,e.scale(o,o),t.originalDevicePixelRatio=t.originalDevicePixelRatio||o),a.style.width=i+"px",a.style.height=n+"px"},o.clear=function(t){t.ctx.clearRect(0,0,t.width,t.height)},o.fontString=function(t,e,a){return e+" "+t+"px "+a},o.longestText=function(t,e,a,i){i=i||{};var n=i.data=i.data||{},r=i.garbageCollect=i.garbageCollect||[];i.font!==e&&(n=i.data={},r=i.garbageCollect=[],i.font=e),t.font=e;var l=0;o.each(a,function(e){void 0!==e&&null!==e&&o.isArray(e)!==!0?l=o.measureText(t,n,r,l,e):o.isArray(e)&&o.each(e,function(e){void 0===e||null===e||o.isArray(e)||(l=o.measureText(t,n,r,l,e))})});var s=r.length/2;if(s>a.length){for(var d=0;s>d;d++)delete n[r[d]];r.splice(0,s)}return l},o.measureText=function(t,e,a,i,n){var o=e[n];return o||(o=e[n]=t.measureText(n).width,a.push(n)),o>i&&(i=o),i},o.numberOfLabelLines=function(t){var e=1;return o.each(t,function(t){o.isArray(t)&&t.length>e&&(e=t.length)}),e},o.drawRoundedRectangle=function(t,e,a,i,n,o){t.beginPath(),t.moveTo(e+o,a),t.lineTo(e+i-o,a),t.quadraticCurveTo(e+i,a,e+i,a+o),t.lineTo(e+i,a+n-o),t.quadraticCurveTo(e+i,a+n,e+i-o,a+n),t.lineTo(e+o,a+n),t.quadraticCurveTo(e,a+n,e,a+n-o),t.lineTo(e,a+o),t.quadraticCurveTo(e,a,e+o,a),t.closePath()},o.color=function(e){return i?i(e instanceof CanvasGradient?t.defaults.global.defaultColor:e):(console.log("Color.js not found!"),e)},o.addResizeListener=function(t,e){var a=document.createElement("iframe"),i="chartjs-hidden-iframe";a.classlist?a.classlist.add(i):a.setAttribute("class",i);var n=a.style;n.width="100%",n.display="block",n.border=0,n.height=0,n.margin=0,n.position="absolute",n.left=0,n.right=0,n.top=0,n.bottom=0,t.insertBefore(a,t.firstChild),(a.contentWindow||a).onresize=function(){e&&e()}},o.removeResizeListener=function(t){var e=t.querySelector(".chartjs-hidden-iframe");e&&e.parentNode.removeChild(e)},o.isArray=Array.isArray?function(t){return Array.isArray(t)}:function(t){return"[object Array]"===Object.prototype.toString.call(t)},o.arrayEquals=function(t,e){var a,i,n,r;if(!t||!e||t.length!=e.length)return!1;for(a=0,i=t.length;i>a;++a)if(n=t[a],r=e[a],n instanceof Array&&r instanceof Array){if(!o.arrayEquals(n,r))return!1}else if(n!=r)return!1;return!0},o.callCallback=function(t,e,a){t&&"function"==typeof t.call&&t.apply(a,e)},o.getHoverColor=function(t){return t instanceof CanvasPattern?t:o.color(t).saturate(.5).darken(.1).rgbString()}}},{3:3}],26:[function(t,e,a){"use strict";e.exports=function(){var t=function(e,a){var i=this,n=t.helpers;return i.config=a,e.length&&e[0].getContext&&(e=e[0]),e.getContext&&(e=e.getContext("2d")),i.ctx=e,i.canvas=e.canvas,e.canvas.style.display=e.canvas.style.display||"block",i.width=e.canvas.width||parseInt(n.getStyle(e.canvas,"width"),10)||n.getMaximumWidth(e.canvas),i.height=e.canvas.height||parseInt(n.getStyle(e.canvas,"height"),10)||n.getMaximumHeight(e.canvas),i.aspectRatio=i.width/i.height,(isNaN(i.aspectRatio)||isFinite(i.aspectRatio)===!1)&&(i.aspectRatio=void 0!==a.aspectRatio?a.aspectRatio:2),i.originalCanvasStyleWidth=e.canvas.style.width,i.originalCanvasStyleHeight=e.canvas.style.height,n.retinaScale(i),a&&(i.controller=new t.Controller(i)),n.addResizeListener(e.canvas.parentNode,function(){i.controller&&i.controller.config.options.responsive&&i.controller.resize()}),i.controller?i.controller:i};return t.defaults={global:{responsive:!0,responsiveAnimationDuration:0,maintainAspectRatio:!0,events:["mousemove","mouseout","click","touchstart","touchmove"],hover:{onHover:null,mode:"single",animationDuration:400},onClick:null,defaultColor:"rgba(0,0,0,0.1)",defaultFontColor:"#666",defaultFontFamily:"'Helvetica Neue', 'Helvetica', 'Arial', sans-serif",defaultFontSize:12,defaultFontStyle:"normal",showLines:!0,elements:{},legendCallback:function(t){var e=[];e.push('<ul class="'+t.id+'-legend">');for(var a=0;a<t.data.datasets.length;a++)e.push('<li><span style="background-color:'+t.data.datasets[a].backgroundColor+'"></span>'),t.data.datasets[a].label&&e.push(t.data.datasets[a].label),e.push("</li>");return e.push("</ul>"),e.join("")}}},t.Chart=t,t}},{}],27:[function(t,e,a){"use strict";e.exports=function(t){var e=t.helpers;t.layoutService={defaults:{},addBox:function(t,e){t.boxes||(t.boxes=[]),t.boxes.push(e)},removeBox:function(t,e){t.boxes&&t.boxes.splice(t.boxes.indexOf(e),1)},update:function(t,a,i){function n(t){var e,a=t.isHorizontal();a?(e=t.update(t.options.fullWidth?p:k,y),S-=e.height):(e=t.update(x,v),k-=e.width),w.push({horizontal:a,minSize:e,box:t})}function o(t){var a=e.findNextWhere(w,function(e){return e.box===t});if(a)if(t.isHorizontal()){var i={left:C,right:M,top:0,bottom:0};t.update(t.options.fullWidth?p:k,m/2,i)}else t.update(a.minSize.width,S)}function r(t){var a=e.findNextWhere(w,function(e){return e.box===t}),i={left:0,right:0,top:D,bottom:A};a&&t.update(a.minSize.width,S,i)}function l(t){t.isHorizontal()?(t.left=t.options.fullWidth?s:C,t.right=t.options.fullWidth?a-s:C+k,t.top=P,t.bottom=P+t.height,P=t.bottom):(t.left=T,t.right=T+t.width,t.top=D,t.bottom=D+S,T=t.right)}if(t){var s=0,d=0,u=e.where(t.boxes,function(t){return"left"===t.options.position}),c=e.where(t.boxes,function(t){return"right"===t.options.position}),h=e.where(t.boxes,function(t){return"top"===t.options.position}),f=e.where(t.boxes,function(t){return"bottom"===t.options.position}),g=e.where(t.boxes,function(t){return"chartArea"===t.options.position});h.sort(function(t,e){return(e.options.fullWidth?1:0)-(t.options.fullWidth?1:0)}),f.sort(function(t,e){return(t.options.fullWidth?1:0)-(e.options.fullWidth?1:0)});var p=a-2*s,m=i-2*d,b=p/2,v=m/2,x=(a-b)/(u.length+c.length),y=(i-v)/(h.length+f.length),k=p,S=m,w=[];e.each(u.concat(c,h,f),n);var C=s,M=s,D=d,A=d;e.each(u.concat(c),o),e.each(u,function(t){C+=t.width}),e.each(c,function(t){M+=t.width}),e.each(h.concat(f),o),e.each(h,function(t){D+=t.height}),e.each(f,function(t){A+=t.height}),e.each(u.concat(c),r),C=s,M=s,D=d,A=d,e.each(u,function(t){C+=t.width}),e.each(c,function(t){M+=t.width}),e.each(h,function(t){D+=t.height}),e.each(f,function(t){A+=t.height});var I=i-D-A,F=a-C-M;(F!==k||I!==S)&&(e.each(u,function(t){t.height=I}),e.each(c,function(t){t.height=I}),e.each(h,function(t){t.options.fullWidth||(t.width=F)}),e.each(f,function(t){t.options.fullWidth||(t.width=F)}),S=I,k=F);var T=s,P=d;e.each(u.concat(h),l),T+=k,P+=S,e.each(c,l),e.each(f,l),t.chartArea={left:C,top:D,right:C+k,bottom:D+S},e.each(g,function(e){e.left=t.chartArea.left,e.top=t.chartArea.top,e.right=t.chartArea.right,e.bottom=t.chartArea.bottom,e.update(k,S)})}}}}},{}],28:[function(t,e,a){"use strict";e.exports=function(t){var e=t.helpers,a=e.noop;t.defaults.global.legend={display:!0,position:"top",fullWidth:!0,reverse:!1,onClick:function(t,e){var a=e.datasetIndex,i=this.chart,n=i.getDatasetMeta(a);n.hidden=null===n.hidden?!i.data.datasets[a].hidden:null,i.update()},labels:{boxWidth:40,padding:10,generateLabels:function(t){var a=t.data;return e.isArray(a.datasets)?a.datasets.map(function(a,i){return{text:a.label,fillStyle:e.isArray(a.backgroundColor)?a.backgroundColor[0]:a.backgroundColor,hidden:!t.isDatasetVisible(i),lineCap:a.borderCapStyle,lineDash:a.borderDash,lineDashOffset:a.borderDashOffset,lineJoin:a.borderJoinStyle,lineWidth:a.borderWidth,strokeStyle:a.borderColor,datasetIndex:i}},this):[]}}},t.Legend=t.Element.extend({initialize:function(t){e.extend(this,t),this.legendHitBoxes=[],this.doughnutMode=!1},beforeUpdate:a,update:function(t,e,a){var i=this;return i.beforeUpdate(),i.maxWidth=t,i.maxHeight=e,i.margins=a,i.beforeSetDimensions(),i.setDimensions(),i.afterSetDimensions(),i.beforeBuildLabels(),i.buildLabels(),i.afterBuildLabels(),i.beforeFit(),i.fit(),i.afterFit(),i.afterUpdate(),i.minSize},afterUpdate:a,beforeSetDimensions:a,setDimensions:function(){var t=this;t.isHorizontal()?(t.width=t.maxWidth,t.left=0,t.right=t.width):(t.height=t.maxHeight,t.top=0,t.bottom=t.height),t.paddingLeft=0,t.paddingTop=0,t.paddingRight=0,t.paddingBottom=0,t.minSize={width:0,height:0}},afterSetDimensions:a,beforeBuildLabels:a,buildLabels:function(){var t=this;t.legendItems=t.options.labels.generateLabels.call(t,t.chart),t.options.reverse&&t.legendItems.reverse()},afterBuildLabels:a,beforeFit:a,fit:function(){var a=this,i=a.options,n=i.labels,o=i.display,r=a.ctx,l=t.defaults.global,s=e.getValueOrDefault,d=s(n.fontSize,l.defaultFontSize),u=s(n.fontStyle,l.defaultFontStyle),c=s(n.fontFamily,l.defaultFontFamily),h=e.fontString(d,u,c),f=a.legendHitBoxes=[],g=a.minSize,p=a.isHorizontal();if(p?(g.width=a.maxWidth,g.height=o?10:0):(g.width=o?10:0,g.height=a.maxHeight),o)if(r.font=h,p){var m=a.lineWidths=[0],b=a.legendItems.length?d+n.padding:0;r.textAlign="left",r.textBaseline="top",e.each(a.legendItems,function(t,e){var i=n.boxWidth+d/2+r.measureText(t.text).width;m[m.length-1]+i+n.padding>=a.width&&(b+=d+n.padding,m[m.length]=a.left),f[e]={left:0,top:0,width:i,height:d},m[m.length-1]+=i+n.padding}),g.height+=b}else{var v=n.padding,x=a.columnWidths=[],y=n.padding,k=0,S=0,w=d+v;e.each(a.legendItems,function(t,e){var a=n.boxWidth+d/2+r.measureText(t.text).width;S+w>g.height&&(y+=k+n.padding,x.push(k),k=0,S=0),k=Math.max(k,a),S+=w,f[e]={left:0,top:0,width:a,height:d}}),y+=k,x.push(k),g.width+=y}a.width=g.width,a.height=g.height},afterFit:a,isHorizontal:function(){return"top"===this.options.position||"bottom"===this.options.position},draw:function(){var a=this,i=a.options,n=i.labels,o=t.defaults.global,r=o.elements.line,l=a.width,s=(a.height,a.lineWidths);if(i.display){var d,u=a.ctx,c=e.getValueOrDefault,h=c(n.fontColor,o.defaultFontColor),f=c(n.fontSize,o.defaultFontSize),g=c(n.fontStyle,o.defaultFontStyle),p=c(n.fontFamily,o.defaultFontFamily),m=e.fontString(f,g,p);u.textAlign="left",u.textBaseline="top",u.lineWidth=.5,u.strokeStyle=h,u.fillStyle=h,u.font=m;var b=n.boxWidth,v=a.legendHitBoxes,x=function(t,e,a){u.save(),u.fillStyle=c(a.fillStyle,o.defaultColor),u.lineCap=c(a.lineCap,r.borderCapStyle),u.lineDashOffset=c(a.lineDashOffset,r.borderDashOffset),u.lineJoin=c(a.lineJoin,r.borderJoinStyle),u.lineWidth=c(a.lineWidth,r.borderWidth),u.strokeStyle=c(a.strokeStyle,o.defaultColor),u.setLineDash&&u.setLineDash(c(a.lineDash,r.borderDash)),u.strokeRect(t,e,b,f),u.fillRect(t,e,b,f),u.restore()},y=function(t,e,a,i){u.fillText(a.text,b+f/2+t,e),a.hidden&&(u.beginPath(),u.lineWidth=2,u.moveTo(b+f/2+t,e+f/2),u.lineTo(b+f/2+t+i,e+f/2),u.stroke())},k=a.isHorizontal();d=k?{x:a.left+(l-s[0])/2,y:a.top+n.padding,line:0}:{x:a.left+n.padding,y:a.top,line:0};var S=f+n.padding;e.each(a.legendItems,function(t,e){var i=u.measureText(t.text).width,o=b+f/2+i,r=d.x,c=d.y;k?r+o>=l&&(c=d.y+=f+n.padding,d.line++,r=d.x=a.left+(l-s[d.line])/2):c+S>a.bottom&&(r=d.x=r+a.columnWidths[d.line]+n.padding,c=d.y=a.top,d.line++),x(r,c,t),v[e].left=r,v[e].top=c,y(r,c,t,i),k?d.x+=o+n.padding:d.y+=S})}},handleEvent:function(t){var a=this,i=e.getRelativePosition(t,a.chart.chart),n=i.x,o=i.y,r=a.options;if(n>=a.left&&n<=a.right&&o>=a.top&&o<=a.bottom)for(var l=a.legendHitBoxes,s=0;s<l.length;++s){var d=l[s];if(n>=d.left&&n<=d.left+d.width&&o>=d.top&&o<=d.top+d.height){r.onClick&&r.onClick.call(a,t,a.legendItems[s]);break}}}}),t.plugins.register({beforeInit:function(e){var a=e.options,i=a.legend;i&&(e.legend=new t.Legend({ctx:e.chart.ctx,options:i,chart:e}),t.layoutService.addBox(e,e.legend))}})}},{}],29:[function(t,e,a){"use strict";e.exports=function(t){var e=t.helpers.noop;t.plugins={_plugins:[],register:function(t){var e=this._plugins;[].concat(t).forEach(function(t){-1===e.indexOf(t)&&e.push(t)})},unregister:function(t){var e=this._plugins;[].concat(t).forEach(function(t){var a=e.indexOf(t);-1!==a&&e.splice(a,1)})},clear:function(){this._plugins=[]},count:function(){return this._plugins.length},getAll:function(){return this._plugins},notify:function(t,e){var a,i,n=this._plugins,o=n.length;for(a=0;o>a;++a)if(i=n[a],"function"==typeof i[t]&&i[t].apply(i,e||[])===!1)return!1;return!0}},t.PluginBase=t.Element.extend({beforeInit:e,afterInit:e,beforeUpdate:e,afterUpdate:e,beforeDraw:e,afterDraw:e,destroy:e}),t.pluginService=t.plugins}},{}],30:[function(t,e,a){"use strict";e.exports=function(t){var e=t.helpers;t.defaults.scale={display:!0,position:"left",gridLines:{display:!0,color:"rgba(0, 0, 0, 0.1)",lineWidth:1,drawBorder:!0,drawOnChartArea:!0,drawTicks:!0,tickMarkLength:10,zeroLineWidth:1,zeroLineColor:"rgba(0,0,0,0.25)",offsetGridLines:!1},scaleLabel:{labelString:"",display:!1},ticks:{beginAtZero:!1,minRotation:0,maxRotation:50,mirror:!1,padding:10,reverse:!1,display:!0,autoSkip:!0,autoSkipPadding:0,labelOffset:0,callback:function(t){return e.isArray(t)?t:""+t}}},t.Scale=t.Element.extend({beforeUpdate:function(){e.callCallback(this.options.beforeUpdate,[this])},update:function(t,a,i){var n=this;return n.beforeUpdate(),n.maxWidth=t,n.maxHeight=a,n.margins=e.extend({left:0,right:0,top:0,bottom:0},i),n.beforeSetDimensions(),n.setDimensions(),n.afterSetDimensions(),n.beforeDataLimits(),n.determineDataLimits(),n.afterDataLimits(),n.beforeBuildTicks(),n.buildTicks(),n.afterBuildTicks(),n.beforeTickToLabelConversion(),n.convertTicksToLabels(),n.afterTickToLabelConversion(),n.beforeCalculateTickRotation(),n.calculateTickRotation(),n.afterCalculateTickRotation(),n.beforeFit(),n.fit(),n.afterFit(),n.afterUpdate(),n.minSize},afterUpdate:function(){e.callCallback(this.options.afterUpdate,[this])},beforeSetDimensions:function(){e.callCallback(this.options.beforeSetDimensions,[this])},setDimensions:function(){var t=this;t.isHorizontal()?(t.width=t.maxWidth,t.left=0,t.right=t.width):(t.height=t.maxHeight,t.top=0,t.bottom=t.height),t.paddingLeft=0,t.paddingTop=0,t.paddingRight=0,t.paddingBottom=0},afterSetDimensions:function(){e.callCallback(this.options.afterSetDimensions,[this])},beforeDataLimits:function(){e.callCallback(this.options.beforeDataLimits,[this])},determineDataLimits:e.noop,afterDataLimits:function(){e.callCallback(this.options.afterDataLimits,[this])},beforeBuildTicks:function(){e.callCallback(this.options.beforeBuildTicks,[this])},buildTicks:e.noop,afterBuildTicks:function(){e.callCallback(this.options.afterBuildTicks,[this])},beforeTickToLabelConversion:function(){e.callCallback(this.options.beforeTickToLabelConversion,[this])},convertTicksToLabels:function(){var t=this;t.ticks=t.ticks.map(function(e,a,i){return t.options.ticks.userCallback?t.options.ticks.userCallback(e,a,i):t.options.ticks.callback(e,a,i)},t)},afterTickToLabelConversion:function(){e.callCallback(this.options.afterTickToLabelConversion,[this])},beforeCalculateTickRotation:function(){e.callCallback(this.options.beforeCalculateTickRotation,[this])},calculateTickRotation:function(){var a=this,i=a.ctx,n=t.defaults.global,o=a.options.ticks,r=e.getValueOrDefault(o.fontSize,n.defaultFontSize),l=e.getValueOrDefault(o.fontStyle,n.defaultFontStyle),s=e.getValueOrDefault(o.fontFamily,n.defaultFontFamily),d=e.fontString(r,l,s);i.font=d;var u,c=i.measureText(a.ticks[0]).width,h=i.measureText(a.ticks[a.ticks.length-1]).width;if(a.labelRotation=o.minRotation||0,a.paddingRight=0,a.paddingLeft=0,a.options.display&&a.isHorizontal()){a.paddingRight=h/2+3,a.paddingLeft=c/2+3,a.longestTextCache||(a.longestTextCache={});for(var f,g,p=e.longestText(i,d,a.ticks,a.longestTextCache),m=p,b=a.getPixelForTick(1)-a.getPixelForTick(0)-6;m>b&&a.labelRotation<o.maxRotation;){if(f=Math.cos(e.toRadians(a.labelRotation)),g=Math.sin(e.toRadians(a.labelRotation)),u=f*c,u+r/2>a.yLabelWidth&&(a.paddingLeft=u+r/2),a.paddingRight=r/2,g*p>a.maxHeight){a.labelRotation--;break}a.labelRotation++,m=f*p}}a.margins&&(a.paddingLeft=Math.max(a.paddingLeft-a.margins.left,0),a.paddingRight=Math.max(a.paddingRight-a.margins.right,0))},afterCalculateTickRotation:function(){e.callCallback(this.options.afterCalculateTickRotation,[this])},beforeFit:function(){e.callCallback(this.options.beforeFit,[this])},fit:function(){var a=this,i=a.minSize={width:0,height:0},n=a.options,o=t.defaults.global,r=n.ticks,l=n.scaleLabel,s=n.display,d=a.isHorizontal(),u=e.getValueOrDefault(r.fontSize,o.defaultFontSize),c=e.getValueOrDefault(r.fontStyle,o.defaultFontStyle),h=e.getValueOrDefault(r.fontFamily,o.defaultFontFamily),f=e.fontString(u,c,h),g=e.getValueOrDefault(l.fontSize,o.defaultFontSize),p=e.getValueOrDefault(l.fontStyle,o.defaultFontStyle),m=e.getValueOrDefault(l.fontFamily,o.defaultFontFamily),b=(e.fontString(g,p,m),n.gridLines.tickMarkLength);if(d?i.width=a.isFullWidth()?a.maxWidth-a.margins.left-a.margins.right:a.maxWidth:i.width=s?b:0,d?i.height=s?b:0:i.height=a.maxHeight,l.display&&s&&(d?i.height+=1.5*g:i.width+=1.5*g),r.display&&s){a.longestTextCache||(a.longestTextCache={});var v=e.longestText(a.ctx,f,a.ticks,a.longestTextCache),x=e.numberOfLabelLines(a.ticks),y=.5*u;if(d){a.longestLabelWidth=v;var k=Math.sin(e.toRadians(a.labelRotation))*a.longestLabelWidth+u*x+y*x;i.height=Math.min(a.maxHeight,i.height+k),a.ctx.font=f;var S=a.ctx.measureText(a.ticks[0]).width,w=a.ctx.measureText(a.ticks[a.ticks.length-1]).width,C=Math.cos(e.toRadians(a.labelRotation)),M=Math.sin(e.toRadians(a.labelRotation));a.paddingLeft=0!==a.labelRotation?C*S+3:S/2+3,a.paddingRight=0!==a.labelRotation?M*(u/2)+3:w/2+3}else{var D=a.maxWidth-i.width,A=r.mirror;A?v=0:v+=a.options.ticks.padding,D>v?i.width+=v:i.width=a.maxWidth,a.paddingTop=u/2,a.paddingBottom=u/2}}a.margins&&(a.paddingLeft=Math.max(a.paddingLeft-a.margins.left,0),a.paddingTop=Math.max(a.paddingTop-a.margins.top,0),a.paddingRight=Math.max(a.paddingRight-a.margins.right,0),a.paddingBottom=Math.max(a.paddingBottom-a.margins.bottom,0)),a.width=i.width,a.height=i.height},afterFit:function(){e.callCallback(this.options.afterFit,[this])},isHorizontal:function(){return"top"===this.options.position||"bottom"===this.options.position},isFullWidth:function(){return this.options.fullWidth},getRightValue:function a(t){return null===t||"undefined"==typeof t?NaN:"number"==typeof t&&isNaN(t)?NaN:"object"==typeof t?t instanceof Date||t.isValid?t:a(this.isHorizontal()?t.x:t.y):t},getLabelForIndex:e.noop,getPixelForValue:e.noop,getValueForPixel:e.noop,getPixelForTick:function(t,e){var a=this;if(a.isHorizontal()){var i=a.width-(a.paddingLeft+a.paddingRight),n=i/Math.max(a.ticks.length-(a.options.gridLines.offsetGridLines?0:1),1),o=n*t+a.paddingLeft;e&&(o+=n/2);var r=a.left+Math.round(o);return r+=a.isFullWidth()?a.margins.left:0}var l=a.height-(a.paddingTop+a.paddingBottom);return a.top+t*(l/(a.ticks.length-1))},getPixelForDecimal:function(t){var e=this;if(e.isHorizontal()){var a=e.width-(e.paddingLeft+e.paddingRight),i=a*t+e.paddingLeft,n=e.left+Math.round(i);return n+=e.isFullWidth()?e.margins.left:0}return e.top+t*e.height},getBasePixel:function(){var t=this,e=t.min,a=t.max;return t.getPixelForValue(t.beginAtZero?0:0>e&&0>a?a:e>0&&a>0?e:0)},draw:function(a){var i=this,n=i.options;if(n.display){
var o,r,l=i.ctx,s=t.defaults.global,d=n.ticks,u=n.gridLines,c=n.scaleLabel,h=0!==i.labelRotation,f=d.autoSkip,g=i.isHorizontal();d.maxTicksLimit&&(r=d.maxTicksLimit);var p=e.getValueOrDefault(d.fontColor,s.defaultFontColor),m=e.getValueOrDefault(d.fontSize,s.defaultFontSize),b=e.getValueOrDefault(d.fontStyle,s.defaultFontStyle),v=e.getValueOrDefault(d.fontFamily,s.defaultFontFamily),x=e.fontString(m,b,v),y=u.tickMarkLength,k=e.getValueOrDefault(c.fontColor,s.defaultFontColor),S=e.getValueOrDefault(c.fontSize,s.defaultFontSize),w=e.getValueOrDefault(c.fontStyle,s.defaultFontStyle),C=e.getValueOrDefault(c.fontFamily,s.defaultFontFamily),M=e.fontString(S,w,C),D=e.toRadians(i.labelRotation),A=Math.cos(D),I=(Math.sin(D),i.longestLabelWidth*A);l.fillStyle=p;var F=[];if(g){if(o=!1,h&&(I/=2),(I+d.autoSkipPadding)*i.ticks.length>i.width-(i.paddingLeft+i.paddingRight)&&(o=1+Math.floor((I+d.autoSkipPadding)*i.ticks.length/(i.width-(i.paddingLeft+i.paddingRight)))),r&&i.ticks.length>r)for(;!o||i.ticks.length/(o||1)>r;)o||(o=1),o+=1;f||(o=!1)}var T="right"===n.position?i.left:i.right-y,P="right"===n.position?i.left+y:i.right,_="bottom"===n.position?i.top:i.bottom-y,R="bottom"===n.position?i.top+y:i.bottom;if(e.each(i.ticks,function(t,r){if(void 0!==t&&null!==t){var l=i.ticks.length===r+1,s=o>1&&r%o>0||r%o===0&&r+o>=i.ticks.length;if((!s||l)&&void 0!==t&&null!==t){var c,f;r===("undefined"!=typeof i.zeroLineIndex?i.zeroLineIndex:0)?(c=u.zeroLineWidth,f=u.zeroLineColor):(c=e.getValueAtIndexOrDefault(u.lineWidth,r),f=e.getValueAtIndexOrDefault(u.color,r));var p,m,b,v,x,k,S,w,C,M,A,I="middle";if(g){h||(I="top"===n.position?"bottom":"top"),A=h?"right":"center";var V=i.getPixelForTick(r)+e.aliasPixel(c);C=i.getPixelForTick(r,u.offsetGridLines)+d.labelOffset,M=h?i.top+12:"top"===n.position?i.bottom-y:i.top+y,p=b=x=S=V,m=_,v=R,k=a.top,w=a.bottom}else{"left"===n.position?d.mirror?(C=i.right+d.padding,A="left"):(C=i.right-d.padding,A="right"):d.mirror?(C=i.left-d.padding,A="right"):(C=i.left+d.padding,A="left");var O=i.getPixelForTick(r);O+=e.aliasPixel(c),M=i.getPixelForTick(r,u.offsetGridLines),p=T,b=P,x=a.left,S=a.right,m=v=k=w=O}F.push({tx1:p,ty1:m,tx2:b,ty2:v,x1:x,y1:k,x2:S,y2:w,labelX:C,labelY:M,glWidth:c,glColor:f,rotation:-1*D,label:t,textBaseline:I,textAlign:A})}}}),e.each(F,function(t){if(u.display&&(l.lineWidth=t.glWidth,l.strokeStyle=t.glColor,l.beginPath(),u.drawTicks&&(l.moveTo(t.tx1,t.ty1),l.lineTo(t.tx2,t.ty2)),u.drawOnChartArea&&(l.moveTo(t.x1,t.y1),l.lineTo(t.x2,t.y2)),l.stroke()),d.display){l.save(),l.translate(t.labelX,t.labelY),l.rotate(t.rotation),l.font=x,l.textBaseline=t.textBaseline,l.textAlign=t.textAlign;var a=t.label;if(e.isArray(a))for(var i=0,n=0;i<a.length;++i)l.fillText(""+a[i],0,n),n+=1.5*m;else l.fillText(a,0,0);l.restore()}}),c.display){var V,O,L=0;if(g)V=i.left+(i.right-i.left)/2,O="bottom"===n.position?i.bottom-S/2:i.top+S/2;else{var B="left"===n.position;V=B?i.left+S/2:i.right-S/2,O=i.top+(i.bottom-i.top)/2,L=B?-.5*Math.PI:.5*Math.PI}l.save(),l.translate(V,O),l.rotate(L),l.textAlign="center",l.textBaseline="middle",l.fillStyle=k,l.font=M,l.fillText(c.labelString,0,0),l.restore()}if(u.drawBorder){l.lineWidth=e.getValueAtIndexOrDefault(u.lineWidth,0),l.strokeStyle=e.getValueAtIndexOrDefault(u.color,0);var W=i.left,z=i.right,H=i.top,N=i.bottom,E=e.aliasPixel(l.lineWidth);g?(H=N="top"===n.position?i.bottom:i.top,H+=E,N+=E):(W=z="left"===n.position?i.right:i.left,W+=E,z+=E),l.beginPath(),l.moveTo(W,H),l.lineTo(z,N),l.stroke()}}}})}},{}],31:[function(t,e,a){"use strict";e.exports=function(t){var e=t.helpers;t.scaleService={constructors:{},defaults:{},registerScaleType:function(t,a,i){this.constructors[t]=a,this.defaults[t]=e.clone(i)},getScaleConstructor:function(t){return this.constructors.hasOwnProperty(t)?this.constructors[t]:void 0},getScaleDefaults:function(a){return this.defaults.hasOwnProperty(a)?e.scaleMerge(t.defaults.scale,this.defaults[a]):{}},updateScaleDefaults:function(t,a){var i=this.defaults;i.hasOwnProperty(t)&&(i[t]=e.extend(i[t],a))},addScalesToLayout:function(a){e.each(a.scales,function(e){t.layoutService.addBox(a,e)})}}}},{}],32:[function(t,e,a){"use strict";e.exports=function(t){var e=t.helpers;t.defaults.global.title={display:!1,position:"top",fullWidth:!0,fontStyle:"bold",padding:10,text:""};var a=e.noop;t.Title=t.Element.extend({initialize:function(a){var i=this;e.extend(i,a),i.options=e.configMerge(t.defaults.global.title,a.options),i.legendHitBoxes=[]},beforeUpdate:function(){var a=this.chart.options;a&&a.title&&(this.options=e.configMerge(t.defaults.global.title,a.title))},update:function(t,e,a){var i=this;return i.beforeUpdate(),i.maxWidth=t,i.maxHeight=e,i.margins=a,i.beforeSetDimensions(),i.setDimensions(),i.afterSetDimensions(),i.beforeBuildLabels(),i.buildLabels(),i.afterBuildLabels(),i.beforeFit(),i.fit(),i.afterFit(),i.afterUpdate(),i.minSize},afterUpdate:a,beforeSetDimensions:a,setDimensions:function(){var t=this;t.isHorizontal()?(t.width=t.maxWidth,t.left=0,t.right=t.width):(t.height=t.maxHeight,t.top=0,t.bottom=t.height),t.paddingLeft=0,t.paddingTop=0,t.paddingRight=0,t.paddingBottom=0,t.minSize={width:0,height:0}},afterSetDimensions:a,beforeBuildLabels:a,buildLabels:a,afterBuildLabels:a,beforeFit:a,fit:function(){var a=this,i=(a.ctx,e.getValueOrDefault),n=a.options,o=t.defaults.global,r=n.display,l=i(n.fontSize,o.defaultFontSize),s=a.minSize;a.isHorizontal()?(s.width=a.maxWidth,s.height=r?l+2*n.padding:0):(s.width=r?l+2*n.padding:0,s.height=a.maxHeight),a.width=s.width,a.height=s.height},afterFit:a,isHorizontal:function(){var t=this.options.position;return"top"===t||"bottom"===t},draw:function(){var a=this,i=a.ctx,n=e.getValueOrDefault,o=a.options,r=t.defaults.global;if(o.display){var l,s,d=n(o.fontSize,r.defaultFontSize),u=n(o.fontStyle,r.defaultFontStyle),c=n(o.fontFamily,r.defaultFontFamily),h=e.fontString(d,u,c),f=0,g=a.top,p=a.left,m=a.bottom,b=a.right;i.fillStyle=n(o.fontColor,r.defaultFontColor),i.font=h,a.isHorizontal()?(l=p+(b-p)/2,s=g+(m-g)/2):(l="left"===o.position?p+d/2:b-d/2,s=g+(m-g)/2,f=Math.PI*("left"===o.position?-.5:.5)),i.save(),i.translate(l,s),i.rotate(f),i.textAlign="center",i.textBaseline="middle",i.fillText(o.text,0,0),i.restore()}}}),t.plugins.register({beforeInit:function(e){var a=e.options,i=a.title;i&&(e.titleBlock=new t.Title({ctx:e.chart.ctx,options:i,chart:e}),t.layoutService.addBox(e,e.titleBlock))}})}},{}],33:[function(t,e,a){"use strict";e.exports=function(t){function e(t,e){return e&&(n.isArray(e)?Array.prototype.push.apply(t,e):t.push(e)),t}function a(t){if(!t.length)return!1;var e,a,i=[],n=[];for(e=0,a=t.length;a>e;++e){var o=t[e];if(o&&o.hasValue()){var r=o.tooltipPosition();i.push(r.x),n.push(r.y)}}var l=0,s=0;for(e=0,a-i.length;a>e;++e)l+=i[e],s+=n[e];return{x:Math.round(l/i.length),y:Math.round(s/i.length)}}function i(t){var e=t._xScale,a=t._yScale||t._scale,i=t._index,n=t._datasetIndex;return{xLabel:e?e.getLabelForIndex(i,n):"",yLabel:a?a.getLabelForIndex(i,n):"",index:i,datasetIndex:n}}var n=t.helpers;t.defaults.global.tooltips={enabled:!0,custom:null,mode:"single",backgroundColor:"rgba(0,0,0,0.8)",titleFontStyle:"bold",titleSpacing:2,titleMarginBottom:6,titleFontColor:"#fff",titleAlign:"left",bodySpacing:2,bodyFontColor:"#fff",bodyAlign:"left",footerFontStyle:"bold",footerSpacing:2,footerMarginTop:6,footerFontColor:"#fff",footerAlign:"left",yPadding:6,xPadding:6,yAlign:"center",xAlign:"center",caretSize:5,cornerRadius:6,multiKeyBackground:"#fff",callbacks:{beforeTitle:n.noop,title:function(t,e){var a="",i=e.labels,n=i?i.length:0;if(t.length>0){var o=t[0];o.xLabel?a=o.xLabel:n>0&&o.index<n&&(a=i[o.index])}return a},afterTitle:n.noop,beforeBody:n.noop,beforeLabel:n.noop,label:function(t,e){var a=e.datasets[t.datasetIndex].label||"";return a+": "+t.yLabel},labelColor:function(t,e){var a=e.getDatasetMeta(t.datasetIndex),i=a.data[t.index],n=i._view;return{borderColor:n.borderColor,backgroundColor:n.backgroundColor}},afterLabel:n.noop,afterBody:n.noop,beforeFooter:n.noop,footer:n.noop,afterFooter:n.noop}},t.Tooltip=t.Element.extend({initialize:function(){var e=this,a=t.defaults.global,i=e._options,o=n.getValueOrDefault;n.extend(e,{_model:{xPadding:i.xPadding,yPadding:i.yPadding,xAlign:i.yAlign,yAlign:i.xAlign,bodyFontColor:i.bodyFontColor,_bodyFontFamily:o(i.bodyFontFamily,a.defaultFontFamily),_bodyFontStyle:o(i.bodyFontStyle,a.defaultFontStyle),_bodyAlign:i.bodyAlign,bodyFontSize:o(i.bodyFontSize,a.defaultFontSize),bodySpacing:i.bodySpacing,titleFontColor:i.titleFontColor,_titleFontFamily:o(i.titleFontFamily,a.defaultFontFamily),_titleFontStyle:o(i.titleFontStyle,a.defaultFontStyle),titleFontSize:o(i.titleFontSize,a.defaultFontSize),_titleAlign:i.titleAlign,titleSpacing:i.titleSpacing,titleMarginBottom:i.titleMarginBottom,footerFontColor:i.footerFontColor,_footerFontFamily:o(i.footerFontFamily,a.defaultFontFamily),_footerFontStyle:o(i.footerFontStyle,a.defaultFontStyle),footerFontSize:o(i.footerFontSize,a.defaultFontSize),_footerAlign:i.footerAlign,footerSpacing:i.footerSpacing,footerMarginTop:i.footerMarginTop,caretSize:i.caretSize,cornerRadius:i.cornerRadius,backgroundColor:i.backgroundColor,opacity:0,legendColorBackground:i.multiKeyBackground}})},getTitle:function(){var t=this,a=t._options,i=a.callbacks,n=i.beforeTitle.apply(t,arguments),o=i.title.apply(t,arguments),r=i.afterTitle.apply(t,arguments),l=[];return l=e(l,n),l=e(l,o),l=e(l,r)},getBeforeBody:function(){var t=this._options.callbacks.beforeBody.apply(this,arguments);return n.isArray(t)?t:void 0!==t?[t]:[]},getBody:function(t,a){var i=this,o=i._options.callbacks,r=[];return n.each(t,function(t){var n={before:[],lines:[],after:[]};e(n.before,o.beforeLabel.call(i,t,a)),e(n.lines,o.label.call(i,t,a)),e(n.after,o.afterLabel.call(i,t,a)),r.push(n)}),r},getAfterBody:function(){var t=this._options.callbacks.afterBody.apply(this,arguments);return n.isArray(t)?t:void 0!==t?[t]:[]},getFooter:function(){var t=this,a=t._options.callbacks,i=a.beforeFooter.apply(t,arguments),n=a.footer.apply(t,arguments),o=a.afterFooter.apply(t,arguments),r=[];return r=e(r,i),r=e(r,n),r=e(r,o)},update:function(t){var e,o,r=this,l=r._options,s=r._model,d=r._active,u=r._data,c=r._chartInstance;if(d.length){s.opacity=1;var h=[],f=a(d),g=[];for(e=0,o=d.length;o>e;++e)g.push(i(d[e]));l.itemSort&&(g=g.sort(l.itemSort)),d.length>1&&n.each(g,function(t){h.push(l.callbacks.labelColor.call(r,t,c))}),n.extend(s,{title:r.getTitle(g,u),beforeBody:r.getBeforeBody(g,u),body:r.getBody(g,u),afterBody:r.getAfterBody(g,u),footer:r.getFooter(g,u),x:Math.round(f.x),y:Math.round(f.y),caretPadding:n.getValueOrDefault(f.padding,2),labelColors:h});var p=r.getTooltipSize(s);r.determineAlignment(p),n.extend(s,r.getBackgroundPoint(s,p))}else r._model.opacity=0;return t&&l.custom&&l.custom.call(r,s),r},getTooltipSize:function(t){var e=this._chart.ctx,a={height:2*t.yPadding,width:0},i=t.body,o=i.reduce(function(t,e){return t+e.before.length+e.lines.length+e.after.length},0);o+=t.beforeBody.length+t.afterBody.length;var r=t.title.length,l=t.footer.length,s=t.titleFontSize,d=t.bodyFontSize,u=t.footerFontSize;a.height+=r*s,a.height+=(r-1)*t.titleSpacing,a.height+=r?t.titleMarginBottom:0,a.height+=o*d,a.height+=o?(o-1)*t.bodySpacing:0,a.height+=l?t.footerMarginTop:0,a.height+=l*u,a.height+=l?(l-1)*t.footerSpacing:0;var c=0,h=function(t){a.width=Math.max(a.width,e.measureText(t).width+c)};return e.font=n.fontString(s,t._titleFontStyle,t._titleFontFamily),n.each(t.title,h),e.font=n.fontString(d,t._bodyFontStyle,t._bodyFontFamily),n.each(t.beforeBody.concat(t.afterBody),h),c=i.length>1?d+2:0,n.each(i,function(t){n.each(t.before,h),n.each(t.lines,h),n.each(t.after,h)}),c=0,e.font=n.fontString(u,t._footerFontStyle,t._footerFontFamily),n.each(t.footer,h),a.width+=2*t.xPadding,a},determineAlignment:function(t){var e=this,a=e._model,i=e._chart,n=e._chartInstance.chartArea;a.y<t.height?a.yAlign="top":a.y>i.height-t.height&&(a.yAlign="bottom");var o,r,l,s,d,u=(n.left+n.right)/2,c=(n.top+n.bottom)/2;"center"===a.yAlign?(o=function(t){return u>=t},r=function(t){return t>u}):(o=function(e){return e<=t.width/2},r=function(e){return e>=i.width-t.width/2}),l=function(e){return e+t.width>i.width},s=function(e){return e-t.width<0},d=function(t){return c>=t?"top":"bottom"},o(a.x)?(a.xAlign="left",l(a.x)&&(a.xAlign="center",a.yAlign=d(a.y))):r(a.x)&&(a.xAlign="right",s(a.x)&&(a.xAlign="center",a.yAlign=d(a.y)))},getBackgroundPoint:function(t,e){var a={x:t.x,y:t.y},i=t.caretSize,n=t.caretPadding,o=t.cornerRadius,r=t.xAlign,l=t.yAlign,s=i+n,d=o+n;return"right"===r?a.x-=e.width:"center"===r&&(a.x-=e.width/2),"top"===l?a.y+=s:"bottom"===l?a.y-=e.height+s:a.y-=e.height/2,"center"===l?"left"===r?a.x+=s:"right"===r&&(a.x-=s):"left"===r?a.x-=d:"right"===r&&(a.x+=d),a},drawCaret:function(t,e,a,i){var o,r,l,s,d,u,c=this._view,h=this._chart.ctx,f=c.caretSize,g=c.cornerRadius,p=c.xAlign,m=c.yAlign,b=t.x,v=t.y,x=e.width,y=e.height;"center"===m?("left"===p?(o=b,r=o-f,l=o):(o=b+x,r=o+f,l=o),d=v+y/2,s=d-f,u=d+f):("left"===p?(o=b+g,r=o+f,l=r+f):"right"===p?(o=b+x-g,r=o-f,l=r-f):(r=b+x/2,o=r-f,l=r+f),"top"===m?(s=v,d=s-f,u=s):(s=v+y,d=s+f,u=s));var k=n.color(c.backgroundColor);h.fillStyle=k.alpha(a*k.alpha()).rgbString(),h.beginPath(),h.moveTo(o,s),h.lineTo(r,d),h.lineTo(l,u),h.closePath(),h.fill()},drawTitle:function(t,e,a,i){var o=e.title;if(o.length){a.textAlign=e._titleAlign,a.textBaseline="top";var r=e.titleFontSize,l=e.titleSpacing,s=n.color(e.titleFontColor);a.fillStyle=s.alpha(i*s.alpha()).rgbString(),a.font=n.fontString(r,e._titleFontStyle,e._titleFontFamily);var d,u;for(d=0,u=o.length;u>d;++d)a.fillText(o[d],t.x,t.y),t.y+=r+l,d+1===o.length&&(t.y+=e.titleMarginBottom-l)}},drawBody:function(t,e,a,i){var o=e.bodyFontSize,r=e.bodySpacing,l=e.body;a.textAlign=e._bodyAlign,a.textBaseline="top";var s=n.color(e.bodyFontColor),d=s.alpha(i*s.alpha()).rgbString();a.fillStyle=d,a.font=n.fontString(o,e._bodyFontStyle,e._bodyFontFamily);var u=0,c=function(e){a.fillText(e,t.x+u,t.y),t.y+=o+r};n.each(e.beforeBody,c);var h=l.length>1;u=h?o+2:0,n.each(l,function(r,l){n.each(r.before,c),n.each(r.lines,function(r){h&&(a.fillStyle=n.color(e.legendColorBackground).alpha(i).rgbaString(),a.fillRect(t.x,t.y,o,o),a.strokeStyle=n.color(e.labelColors[l].borderColor).alpha(i).rgbaString(),a.strokeRect(t.x,t.y,o,o),a.fillStyle=n.color(e.labelColors[l].backgroundColor).alpha(i).rgbaString(),a.fillRect(t.x+1,t.y+1,o-2,o-2),a.fillStyle=d),c(r)}),n.each(r.after,c)}),u=0,n.each(e.afterBody,c),t.y-=r},drawFooter:function(t,e,a,i){var o=e.footer;if(o.length){t.y+=e.footerMarginTop,a.textAlign=e._footerAlign,a.textBaseline="top";var r=n.color(e.footerFontColor);a.fillStyle=r.alpha(i*r.alpha()).rgbString(),a.font=n.fontString(e.footerFontSize,e._footerFontStyle,e._footerFontFamily),n.each(o,function(i){a.fillText(i,t.x,t.y),t.y+=e.footerFontSize+e.footerSpacing})}},draw:function(){var t=this._chart.ctx,e=this._view;if(0!==e.opacity){var a=this.getTooltipSize(e),i={x:e.x,y:e.y},o=Math.abs(e.opacity<.001)?0:e.opacity;if(this._options.enabled){var r=n.color(e.backgroundColor);t.fillStyle=r.alpha(o*r.alpha()).rgbString(),n.drawRoundedRectangle(t,i.x,i.y,a.width,a.height,e.cornerRadius),t.fill(),this.drawCaret(i,a,o,e.caretPadding),i.x+=e.xPadding,i.y+=e.yPadding,this.drawTitle(i,e,t,o),this.drawBody(i,e,t,o),this.drawFooter(i,e,t,o)}}}})}},{}],34:[function(t,e,a){"use strict";e.exports=function(t,e){var a=t.helpers,i=t.defaults.global;i.elements.arc={backgroundColor:i.defaultColor,borderColor:"#fff",borderWidth:2},t.elements.Arc=t.Element.extend({inLabelRange:function(t){var e=this._view;return e?Math.pow(t-e.x,2)<Math.pow(e.radius+e.hoverRadius,2):!1},inRange:function(t,e){var i=this._view;if(i){for(var n=a.getAngleFromPoint(i,{x:t,y:e}),o=n.angle,r=n.distance,l=i.startAngle,s=i.endAngle;l>s;)s+=2*Math.PI;for(;o>s;)o-=2*Math.PI;for(;l>o;)o+=2*Math.PI;var d=o>=l&&s>=o,u=r>=i.innerRadius&&r<=i.outerRadius;return d&&u}return!1},tooltipPosition:function(){var t=this._view,e=t.startAngle+(t.endAngle-t.startAngle)/2,a=(t.outerRadius-t.innerRadius)/2+t.innerRadius;return{x:t.x+Math.cos(e)*a,y:t.y+Math.sin(e)*a}},draw:function(){var t=this._chart.ctx,e=this._view,a=e.startAngle,i=e.endAngle;t.beginPath(),t.arc(e.x,e.y,e.outerRadius,a,i),t.arc(e.x,e.y,e.innerRadius,i,a,!0),t.closePath(),t.strokeStyle=e.borderColor,t.lineWidth=e.borderWidth,t.fillStyle=e.backgroundColor,t.fill(),t.lineJoin="bevel",e.borderWidth&&t.stroke()}})}},{}],35:[function(t,e,a){"use strict";e.exports=function(t){var e=t.helpers,a=t.defaults.global;t.defaults.global.elements.line={tension:.4,backgroundColor:a.defaultColor,borderWidth:3,borderColor:a.defaultColor,borderCapStyle:"butt",borderDash:[],borderDashOffset:0,borderJoinStyle:"miter",fill:!0},t.elements.Line=t.Element.extend({lineToNextPoint:function(t,e,a,i,n){var o=this,r=o._chart.ctx,l=o._view?o._view.spanGaps:!1;e._view.skip&&!l?i.call(o,t,e,a):t._view.skip&&!l?n.call(o,t,e,a):0===e._view.tension?r.lineTo(e._view.x,e._view.y):r.bezierCurveTo(t._view.controlPointNextX,t._view.controlPointNextY,e._view.controlPointPreviousX,e._view.controlPointPreviousY,e._view.x,e._view.y)},draw:function(){function t(t){r._view.skip||l._view.skip?t&&o.lineTo(i._view.scaleZero.x,i._view.scaleZero.y):o.bezierCurveTo(l._view.controlPointNextX,l._view.controlPointNextY,r._view.controlPointPreviousX,r._view.controlPointPreviousY,r._view.x,r._view.y)}var i=this,n=i._view,o=i._chart.ctx,r=i._children[0],l=i._children[i._children.length-1];o.save(),i._children.length>0&&n.fill&&(o.beginPath(),e.each(i._children,function(t,a){var r=e.previousItem(i._children,a),l=e.nextItem(i._children,a);0===a?(i._loop?o.moveTo(n.scaleZero.x,n.scaleZero.y):o.moveTo(t._view.x,n.scaleZero),t._view.skip?i._loop||o.moveTo(l._view.x,i._view.scaleZero):o.lineTo(t._view.x,t._view.y)):i.lineToNextPoint(r,t,l,function(t,e,a){i._loop?o.lineTo(i._view.scaleZero.x,i._view.scaleZero.y):(o.lineTo(t._view.x,i._view.scaleZero),o.moveTo(a._view.x,i._view.scaleZero))},function(t,e){o.lineTo(e._view.x,e._view.y)})},i),i._loop?t(!0):(o.lineTo(i._children[i._children.length-1]._view.x,n.scaleZero),o.lineTo(i._children[0]._view.x,n.scaleZero)),o.fillStyle=n.backgroundColor||a.defaultColor,o.closePath(),o.fill());var s=a.elements.line;o.lineCap=n.borderCapStyle||s.borderCapStyle,o.setLineDash&&o.setLineDash(n.borderDash||s.borderDash),o.lineDashOffset=n.borderDashOffset||s.borderDashOffset,o.lineJoin=n.borderJoinStyle||s.borderJoinStyle,o.lineWidth=n.borderWidth||s.borderWidth,o.strokeStyle=n.borderColor||a.defaultColor,o.beginPath(),e.each(i._children,function(t,a){var n=e.previousItem(i._children,a),r=e.nextItem(i._children,a);0===a?o.moveTo(t._view.x,t._view.y):i.lineToNextPoint(n,t,r,function(t,e,a){o.moveTo(a._view.x,a._view.y)},function(t,e){o.moveTo(e._view.x,e._view.y)})},i),i._loop&&i._children.length>0&&t(),o.stroke(),o.restore()}})}},{}],36:[function(t,e,a){"use strict";e.exports=function(t){var e=t.helpers,a=t.defaults.global,i=a.defaultColor;a.elements.point={radius:3,pointStyle:"circle",backgroundColor:i,borderWidth:1,borderColor:i,hitRadius:1,hoverRadius:4,hoverBorderWidth:1},t.elements.Point=t.Element.extend({inRange:function(t,e){var a=this._view;return a?Math.pow(t-a.x,2)+Math.pow(e-a.y,2)<Math.pow(a.hitRadius+a.radius,2):!1},inLabelRange:function(t){var e=this._view;return e?Math.pow(t-e.x,2)<Math.pow(e.radius+e.hitRadius,2):!1},tooltipPosition:function(){var t=this._view;return{x:t.x,y:t.y,padding:t.radius+t.borderWidth}},draw:function(){var t,n,o,r,l,s,d=this._view,u=this._chart.ctx,c=d.pointStyle,h=d.radius,f=d.x,g=d.y;if(!d.skip){if("object"==typeof c&&(t=c.toString(),"[object HTMLImageElement]"===t||"[object HTMLCanvasElement]"===t))return void u.drawImage(c,f-c.width/2,g-c.height/2);if(!(isNaN(h)||0>=h)){switch(u.strokeStyle=d.borderColor||i,u.lineWidth=e.getValueOrDefault(d.borderWidth,a.elements.point.borderWidth),u.fillStyle=d.backgroundColor||i,c){default:u.beginPath(),u.arc(f,g,h,0,2*Math.PI),u.closePath(),u.fill();break;case"triangle":u.beginPath(),n=3*h/Math.sqrt(3),l=n*Math.sqrt(3)/2,u.moveTo(f-n/2,g+l/3),u.lineTo(f+n/2,g+l/3),u.lineTo(f,g-2*l/3),u.closePath(),u.fill();break;case"rect":s=1/Math.SQRT2*h,u.fillRect(f-s,g-s,2*s,2*s),u.strokeRect(f-s,g-s,2*s,2*s);break;case"rectRot":s=1/Math.SQRT2*h,u.beginPath(),u.moveTo(f-s,g),u.lineTo(f,g+s),u.lineTo(f+s,g),u.lineTo(f,g-s),u.closePath(),u.fill();break;case"cross":u.beginPath(),u.moveTo(f,g+h),u.lineTo(f,g-h),u.moveTo(f-h,g),u.lineTo(f+h,g),u.closePath();break;case"crossRot":u.beginPath(),o=Math.cos(Math.PI/4)*h,r=Math.sin(Math.PI/4)*h,u.moveTo(f-o,g-r),u.lineTo(f+o,g+r),u.moveTo(f-o,g+r),u.lineTo(f+o,g-r),u.closePath();break;case"star":u.beginPath(),u.moveTo(f,g+h),u.lineTo(f,g-h),u.moveTo(f-h,g),u.lineTo(f+h,g),o=Math.cos(Math.PI/4)*h,r=Math.sin(Math.PI/4)*h,u.moveTo(f-o,g-r),u.lineTo(f+o,g+r),u.moveTo(f-o,g+r),u.lineTo(f+o,g-r),u.closePath();break;case"line":u.beginPath(),u.moveTo(f-h,g),u.lineTo(f+h,g),u.closePath();break;case"dash":u.beginPath(),u.moveTo(f,g),u.lineTo(f+h,g),u.closePath()}u.stroke()}}}})}},{}],37:[function(t,e,a){"use strict";e.exports=function(t){var e=(t.helpers,t.defaults.global);e.elements.rectangle={backgroundColor:e.defaultColor,borderWidth:0,borderColor:e.defaultColor,borderSkipped:"bottom"},t.elements.Rectangle=t.Element.extend({draw:function(){function t(t){return s[(u+t)%4]}var e=this._chart.ctx,a=this._view,i=a.width/2,n=a.x-i,o=a.x+i,r=a.base-(a.base-a.y),l=a.borderWidth/2;a.borderWidth&&(n+=l,o-=l,r+=l),e.beginPath(),e.fillStyle=a.backgroundColor,e.strokeStyle=a.borderColor,e.lineWidth=a.borderWidth;var s=[[n,a.base],[n,r],[o,r],[o,a.base]],d=["bottom","left","top","right"],u=d.indexOf(a.borderSkipped,0);-1===u&&(u=0),e.moveTo.apply(e,t(0));for(var c=1;4>c;c++)e.lineTo.apply(e,t(c));e.fill(),a.borderWidth&&e.stroke()},height:function(){var t=this._view;return t.base-t.y},inRange:function(t,e){var a=this._view;return a?a.y<a.base?t>=a.x-a.width/2&&t<=a.x+a.width/2&&e>=a.y&&e<=a.base:t>=a.x-a.width/2&&t<=a.x+a.width/2&&e>=a.base&&e<=a.y:!1},inLabelRange:function(t){var e=this._view;return e?t>=e.x-e.width/2&&t<=e.x+e.width/2:!1},tooltipPosition:function(){var t=this._view;return{x:t.x,y:t.y}}})}},{}],38:[function(t,e,a){"use strict";e.exports=function(t){var e=t.helpers,a={position:"bottom"},i=t.Scale.extend({determineDataLimits:function(){var t=this;t.minIndex=0,t.maxIndex=t.chart.data.labels.length-1;var a;void 0!==t.options.ticks.min&&(a=e.indexOf(t.chart.data.labels,t.options.ticks.min),t.minIndex=-1!==a?a:t.minIndex),void 0!==t.options.ticks.max&&(a=e.indexOf(t.chart.data.labels,t.options.ticks.max),t.maxIndex=-1!==a?a:t.maxIndex),t.min=t.chart.data.labels[t.minIndex],t.max=t.chart.data.labels[t.maxIndex]},buildTicks:function(t){var e=this;e.ticks=0===e.minIndex&&e.maxIndex===e.chart.data.labels.length-1?e.chart.data.labels:e.chart.data.labels.slice(e.minIndex,e.maxIndex+1)},getLabelForIndex:function(t,e){return this.ticks[t]},getPixelForValue:function(t,e,a,i){var n=this,o=Math.max(n.maxIndex+1-n.minIndex-(n.options.gridLines.offsetGridLines?0:1),1);if(n.isHorizontal()){var r=n.width-(n.paddingLeft+n.paddingRight),l=r/o,s=l*(e-n.minIndex)+n.paddingLeft;return n.options.gridLines.offsetGridLines&&i&&(s+=l/2),n.left+Math.round(s)}var d=n.height-(n.paddingTop+n.paddingBottom),u=d/o,c=u*(e-n.minIndex)+n.paddingTop;return n.options.gridLines.offsetGridLines&&i&&(c+=u/2),n.top+Math.round(c)},getPixelForTick:function(t,e){return this.getPixelForValue(this.ticks[t],t+this.minIndex,null,e)},getValueForPixel:function(t){var e,a=this,i=Math.max(a.ticks.length-(a.options.gridLines.offsetGridLines?0:1),1),n=a.isHorizontal(),o=n?a.width-(a.paddingLeft+a.paddingRight):a.height-(a.paddingTop+a.paddingBottom),r=o/i;return a.options.gridLines.offsetGridLines&&(t-=r/2),t-=n?a.paddingLeft:a.paddingTop,e=0>=t?0:Math.round(t/r)}});t.scaleService.registerScaleType("category",i,a)}},{}],39:[function(t,e,a){"use strict";e.exports=function(t){var e=t.helpers,a={position:"left",ticks:{callback:function(t,a,i){var n=i.length>3?i[2]-i[1]:i[1]-i[0];Math.abs(n)>1&&t!==Math.floor(t)&&(n=t-Math.floor(t));var o=e.log10(Math.abs(n)),r="";if(0!==t){var l=-1*Math.floor(o);l=Math.max(Math.min(l,20),0),r=t.toFixed(l)}else r="0";return r}}},i=t.LinearScaleBase.extend({determineDataLimits:function(){function t(t){return l?t.xAxisID===a.id:t.yAxisID===a.id}var a=this,i=a.options,n=(i.ticks,a.chart),o=n.data,r=o.datasets,l=a.isHorizontal();if(a.min=null,a.max=null,i.stacked){var s={},d=!1,u=!1;e.each(r,function(o,r){var l=n.getDatasetMeta(r);void 0===s[l.type]&&(s[l.type]={positiveValues:[],negativeValues:[]});var c=s[l.type].positiveValues,h=s[l.type].negativeValues;n.isDatasetVisible(r)&&t(l)&&e.each(o.data,function(t,e){var n=+a.getRightValue(t);isNaN(n)||l.data[e].hidden||(c[e]=c[e]||0,h[e]=h[e]||0,i.relativePoints?c[e]=100:0>n?(u=!0,h[e]+=n):(d=!0,c[e]+=n))})}),e.each(s,function(t){var i=t.positiveValues.concat(t.negativeValues),n=e.min(i),o=e.max(i);a.min=null===a.min?n:Math.min(a.min,n),a.max=null===a.max?o:Math.max(a.max,o)})}else e.each(r,function(i,o){var r=n.getDatasetMeta(o);n.isDatasetVisible(o)&&t(r)&&e.each(i.data,function(t,e){var i=+a.getRightValue(t);isNaN(i)||r.data[e].hidden||(null===a.min?a.min=i:i<a.min&&(a.min=i),null===a.max?a.max=i:i>a.max&&(a.max=i))})});this.handleTickRangeOptions()},getTickLimit:function(){var a,i=this,n=i.options.ticks;if(i.isHorizontal())a=Math.min(n.maxTicksLimit?n.maxTicksLimit:11,Math.ceil(i.width/50));else{var o=e.getValueOrDefault(n.fontSize,t.defaults.global.defaultFontSize);a=Math.min(n.maxTicksLimit?n.maxTicksLimit:11,Math.ceil(i.height/(2*o)))}return a},handleDirectionalChanges:function(){this.isHorizontal()||this.ticks.reverse()},getLabelForIndex:function(t,e){return+this.getRightValue(this.chart.data.datasets[e].data[t])},getPixelForValue:function(t,e,a,i){var n,o,r=this,l=r.paddingLeft,s=r.paddingBottom,d=r.start,u=+r.getRightValue(t),c=r.end-d;return r.isHorizontal()?(o=r.width-(l+r.paddingRight),n=r.left+o/c*(u-d),Math.round(n+l)):(o=r.height-(r.paddingTop+s),n=r.bottom-s-o/c*(u-d),Math.round(n))},getValueForPixel:function(t){var e=this,a=e.isHorizontal(),i=e.paddingLeft,n=e.paddingBottom,o=a?e.width-(i+e.paddingRight):e.height-(e.paddingTop+n),r=(a?t-e.left-i:e.bottom-n-t)/o;return e.start+(e.end-e.start)*r},getPixelForTick:function(t,e){return this.getPixelForValue(this.ticksAsNumbers[t],null,null,e)}});t.scaleService.registerScaleType("linear",i,a)}},{}],40:[function(t,e,a){"use strict";e.exports=function(t){var e=t.helpers,a=e.noop;t.LinearScaleBase=t.Scale.extend({handleTickRangeOptions:function(){var t=this,a=t.options,i=a.ticks;if(i.beginAtZero){var n=e.sign(t.min),o=e.sign(t.max);0>n&&0>o?t.max=0:n>0&&o>0&&(t.min=0)}void 0!==i.min?t.min=i.min:void 0!==i.suggestedMin&&(t.min=Math.min(t.min,i.suggestedMin)),void 0!==i.max?t.max=i.max:void 0!==i.suggestedMax&&(t.max=Math.max(t.max,i.suggestedMax)),t.min===t.max&&(t.max++,i.beginAtZero||t.min--)},getTickLimit:a,handleDirectionalChanges:a,buildTicks:function(){var t=this,a=t.options,i=a.ticks,n=e.getValueOrDefault,o=(t.isHorizontal(),t.ticks=[]),r=t.getTickLimit();r=Math.max(2,r);var l,s=i.fixedStepSize&&i.fixedStepSize>0||i.stepSize&&i.stepSize>0;if(s)l=n(i.fixedStepSize,i.stepSize);else{var d=e.niceNum(t.max-t.min,!1);l=e.niceNum(d/(r-1),!0)}var u=Math.floor(t.min/l)*l,c=Math.ceil(t.max/l)*l,h=(c-u)/l;h=e.almostEquals(h,Math.round(h),l/1e3)?Math.round(h):Math.ceil(h),o.push(void 0!==i.min?i.min:u);for(var f=1;h>f;++f)o.push(u+f*l);o.push(void 0!==i.max?i.max:c),t.handleDirectionalChanges(),t.max=e.max(o),t.min=e.min(o),i.reverse?(o.reverse(),t.start=t.max,t.end=t.min):(t.start=t.min,t.end=t.max)},convertTicksToLabels:function(){var e=this;e.ticksAsNumbers=e.ticks.slice(),e.zeroLineIndex=e.ticks.indexOf(0),t.Scale.prototype.convertTicksToLabels.call(e)}})}},{}],41:[function(t,e,a){"use strict";e.exports=function(t){var e=t.helpers,a={position:"left",ticks:{callback:function(t,a,i){var n=t/Math.pow(10,Math.floor(e.log10(t)));return 1===n||2===n||5===n||0===a||a===i.length-1?t.toExponential():""}}},i=t.Scale.extend({determineDataLimits:function(){function t(t){return d?t.xAxisID===a.id:t.yAxisID===a.id}var a=this,i=a.options,n=i.ticks,o=a.chart,r=o.data,l=r.datasets,s=e.getValueOrDefault,d=a.isHorizontal();if(a.min=null,a.max=null,i.stacked){var u={};e.each(l,function(n,r){var l=o.getDatasetMeta(r);o.isDatasetVisible(r)&&t(l)&&(void 0===u[l.type]&&(u[l.type]=[]),e.each(n.data,function(t,e){var n=u[l.type],o=+a.getRightValue(t);isNaN(o)||l.data[e].hidden||(n[e]=n[e]||0,i.relativePoints?n[e]=100:n[e]+=o)}))}),e.each(u,function(t){var i=e.min(t),n=e.max(t);a.min=null===a.min?i:Math.min(a.min,i),a.max=null===a.max?n:Math.max(a.max,n)})}else e.each(l,function(i,n){var r=o.getDatasetMeta(n);o.isDatasetVisible(n)&&t(r)&&e.each(i.data,function(t,e){var i=+a.getRightValue(t);isNaN(i)||r.data[e].hidden||(null===a.min?a.min=i:i<a.min&&(a.min=i),null===a.max?a.max=i:i>a.max&&(a.max=i))})});a.min=s(n.min,a.min),a.max=s(n.max,a.max),a.min===a.max&&(0!==a.min&&null!==a.min?(a.min=Math.pow(10,Math.floor(e.log10(a.min))-1),a.max=Math.pow(10,Math.floor(e.log10(a.max))+1)):(a.min=1,a.max=10))},buildTicks:function(){for(var t=this,a=t.options,i=a.ticks,n=e.getValueOrDefault,o=t.ticks=[],r=n(i.min,Math.pow(10,Math.floor(e.log10(t.min))));r<t.max;){o.push(r);var l=Math.floor(e.log10(r)),s=Math.floor(r/Math.pow(10,l))+1;10===s&&(s=1,++l),r=s*Math.pow(10,l)}var d=n(i.max,r);o.push(d),t.isHorizontal()||o.reverse(),t.max=e.max(o),t.min=e.min(o),i.reverse?(o.reverse(),t.start=t.max,t.end=t.min):(t.start=t.min,t.end=t.max)},convertTicksToLabels:function(){this.tickValues=this.ticks.slice(),t.Scale.prototype.convertTicksToLabels.call(this)},getLabelForIndex:function(t,e){return+this.getRightValue(this.chart.data.datasets[e].data[t])},getPixelForTick:function(t,e){return this.getPixelForValue(this.tickValues[t],null,null,e)},getPixelForValue:function(t,a,i,n){var o,r,l=this,s=l.start,d=+l.getRightValue(t),u=e.log10(l.end)-e.log10(s),c=l.paddingTop,h=l.paddingBottom,f=l.paddingLeft;return l.isHorizontal()?0===d?r=l.left+f:(o=l.width-(f+l.paddingRight),r=l.left+o/u*(e.log10(d)-e.log10(s)),r+=f):0===d?r=l.top+c:(o=l.height-(c+h),r=l.bottom-h-o/u*(e.log10(d)-e.log10(s))),r},getValueForPixel:function(t){var a,i,n=this,o=e.log10(n.end)-e.log10(n.start);return n.isHorizontal()?(i=n.width-(n.paddingLeft+n.paddingRight),a=n.start*Math.pow(10,(t-n.left-n.paddingLeft)*o/i)):(i=n.height-(n.paddingTop+n.paddingBottom),a=Math.pow(10,(n.bottom-n.paddingBottom-t)*o/i)/n.start),a}});t.scaleService.registerScaleType("logarithmic",i,a)}},{}],42:[function(t,e,a){"use strict";e.exports=function(t){var e=t.helpers,a=t.defaults.global,i={display:!0,animate:!0,lineArc:!1,position:"chartArea",angleLines:{display:!0,color:"rgba(0, 0, 0, 0.1)",lineWidth:1},ticks:{showLabelBackdrop:!0,backdropColor:"rgba(255,255,255,0.75)",backdropPaddingY:2,backdropPaddingX:2},pointLabels:{fontSize:10,callback:function(t){return t}}},n=t.LinearScaleBase.extend({getValueCount:function(){return this.chart.data.labels.length},setDimensions:function(){var t=this,i=t.options,n=i.ticks;t.width=t.maxWidth,t.height=t.maxHeight,t.xCenter=Math.round(t.width/2),t.yCenter=Math.round(t.height/2);var o=e.min([t.height,t.width]),r=e.getValueOrDefault(n.fontSize,a.defaultFontSize);t.drawingArea=i.display?o/2-(r/2+n.backdropPaddingY):o/2},determineDataLimits:function(){var t=this,a=t.chart;t.min=null,t.max=null,e.each(a.data.datasets,function(i,n){if(a.isDatasetVisible(n)){var o=a.getDatasetMeta(n);e.each(i.data,function(e,a){var i=+t.getRightValue(e);isNaN(i)||o.data[a].hidden||(null===t.min?t.min=i:i<t.min&&(t.min=i),null===t.max?t.max=i:i>t.max&&(t.max=i))})}}),t.handleTickRangeOptions()},getTickLimit:function(){var t=this.options.ticks,i=e.getValueOrDefault(t.fontSize,a.defaultFontSize);return Math.min(t.maxTicksLimit?t.maxTicksLimit:11,Math.ceil(this.drawingArea/(1.5*i)))},convertTicksToLabels:function(){
var e=this;t.LinearScaleBase.prototype.convertTicksToLabels.call(e),e.pointLabels=e.chart.data.labels.map(e.options.pointLabels.callback,e)},getLabelForIndex:function(t,e){return+this.getRightValue(this.chart.data.datasets[e].data[t])},fit:function(){var t,i,n,o,r,l,s,d,u,c,h,f,g=this.options.pointLabels,p=e.getValueOrDefault(g.fontSize,a.defaultFontSize),m=e.getValueOrDefault(g.fontStyle,a.defaultFontStyle),b=e.getValueOrDefault(g.fontFamily,a.defaultFontFamily),v=e.fontString(p,m,b),x=e.min([this.height/2-p-5,this.width/2]),y=this.width,k=0;for(this.ctx.font=v,i=0;i<this.getValueCount();i++)t=this.getPointPosition(i,x),n=this.ctx.measureText(this.pointLabels[i]?this.pointLabels[i]:"").width+5,0===i||i===this.getValueCount()/2?(o=n/2,t.x+o>y&&(y=t.x+o,r=i),t.x-o<k&&(k=t.x-o,s=i)):i<this.getValueCount()/2?t.x+n>y&&(y=t.x+n,r=i):i>this.getValueCount()/2&&t.x-n<k&&(k=t.x-n,s=i);u=k,c=Math.ceil(y-this.width),l=this.getIndexAngle(r),d=this.getIndexAngle(s),h=c/Math.sin(l+Math.PI/2),f=u/Math.sin(d+Math.PI/2),h=e.isNumber(h)?h:0,f=e.isNumber(f)?f:0,this.drawingArea=Math.round(x-(f+h)/2),this.setCenterPoint(f,h)},setCenterPoint:function(t,e){var a=this,i=a.width-e-a.drawingArea,n=t+a.drawingArea;a.xCenter=Math.round((n+i)/2+a.left),a.yCenter=Math.round(a.height/2+a.top)},getIndexAngle:function(t){var e=2*Math.PI/this.getValueCount();return t*e-Math.PI/2},getDistanceFromCenterForValue:function(t){var e=this;if(null===t)return 0;var a=e.drawingArea/(e.max-e.min);return e.options.reverse?(e.max-t)*a:(t-e.min)*a},getPointPosition:function(t,e){var a=this,i=a.getIndexAngle(t);return{x:Math.round(Math.cos(i)*e)+a.xCenter,y:Math.round(Math.sin(i)*e)+a.yCenter}},getPointPositionForValue:function(t,e){return this.getPointPosition(t,this.getDistanceFromCenterForValue(e))},getBasePosition:function(){var t=this,e=t.min,a=t.max;return t.getPointPositionForValue(0,t.beginAtZero?0:0>e&&0>a?a:e>0&&a>0?e:0)},draw:function(){var t=this,i=t.options,n=i.gridLines,o=i.ticks,r=i.angleLines,l=i.pointLabels,s=e.getValueOrDefault;if(i.display){var d=t.ctx,u=s(o.fontSize,a.defaultFontSize),c=s(o.fontStyle,a.defaultFontStyle),h=s(o.fontFamily,a.defaultFontFamily),f=e.fontString(u,c,h);if(e.each(t.ticks,function(r,l){if(l>0||i.reverse){var c=t.getDistanceFromCenterForValue(t.ticksAsNumbers[l]),h=t.yCenter-c;if(n.display&&0!==l)if(d.strokeStyle=e.getValueAtIndexOrDefault(n.color,l-1),d.lineWidth=e.getValueAtIndexOrDefault(n.lineWidth,l-1),i.lineArc)d.beginPath(),d.arc(t.xCenter,t.yCenter,c,0,2*Math.PI),d.closePath(),d.stroke();else{d.beginPath();for(var g=0;g<t.getValueCount();g++){var p=t.getPointPosition(g,c);0===g?d.moveTo(p.x,p.y):d.lineTo(p.x,p.y)}d.closePath(),d.stroke()}if(o.display){var m=s(o.fontColor,a.defaultFontColor);if(d.font=f,o.showLabelBackdrop){var b=d.measureText(r).width;d.fillStyle=o.backdropColor,d.fillRect(t.xCenter-b/2-o.backdropPaddingX,h-u/2-o.backdropPaddingY,b+2*o.backdropPaddingX,u+2*o.backdropPaddingY)}d.textAlign="center",d.textBaseline="middle",d.fillStyle=m,d.fillText(r,t.xCenter,h)}}}),!i.lineArc){d.lineWidth=r.lineWidth,d.strokeStyle=r.color;for(var g=t.getDistanceFromCenterForValue(i.reverse?t.min:t.max),p=s(l.fontSize,a.defaultFontSize),m=s(l.fontStyle,a.defaultFontStyle),b=s(l.fontFamily,a.defaultFontFamily),v=e.fontString(p,m,b),x=t.getValueCount()-1;x>=0;x--){if(r.display){var y=t.getPointPosition(x,g);d.beginPath(),d.moveTo(t.xCenter,t.yCenter),d.lineTo(y.x,y.y),d.stroke(),d.closePath()}var k=t.getPointPosition(x,g+5),S=s(l.fontColor,a.defaultFontColor);d.font=v,d.fillStyle=S;var w=t.pointLabels,C=w.length,M=w.length/2,D=M/2,A=D>x||x>C-D,I=x===D||x===C-D;0===x?d.textAlign="center":x===M?d.textAlign="center":M>x?d.textAlign="left":d.textAlign="right",I?d.textBaseline="middle":A?d.textBaseline="bottom":d.textBaseline="top",d.fillText(w[x]?w[x]:"",k.x,k.y)}}}}});t.scaleService.registerScaleType("radialLinear",n,i)}},{}],43:[function(t,e,a){"use strict";var i=t(1);i="function"==typeof i?i:window.moment,e.exports=function(t){var e=t.helpers,a={units:[{name:"millisecond",steps:[1,2,5,10,20,50,100,250,500]},{name:"second",steps:[1,2,5,10,30]},{name:"minute",steps:[1,2,5,10,30]},{name:"hour",steps:[1,2,3,6,12]},{name:"day",steps:[1,2,5]},{name:"week",maxStep:4},{name:"month",maxStep:3},{name:"quarter",maxStep:4},{name:"year",maxStep:!1}]},n={position:"bottom",time:{parser:!1,format:!1,unit:!1,round:!1,displayFormat:!1,isoWeekday:!1,displayFormats:{millisecond:"h:mm:ss.SSS a",second:"h:mm:ss a",minute:"h:mm:ss a",hour:"MMM D, hA",day:"ll",week:"ll",month:"MMM YYYY",quarter:"[Q]Q - YYYY",year:"YYYY"}},ticks:{autoSkip:!1}},o=t.Scale.extend({initialize:function(){if(!i)throw new Error("Chart.js - Moment.js could not be found! You must include it before Chart.js to use the time scale. Download at https://momentjs.com");t.Scale.prototype.initialize.call(this)},getLabelMoment:function(t,e){return this.labelMoments[t][e]},getMomentStartOf:function(t){var e=this;return"week"===e.options.time.unit&&e.options.time.isoWeekday!==!1?t.clone().startOf("isoWeek").isoWeekday(e.options.time.isoWeekday):t.clone().startOf(e.tickUnit)},determineDataLimits:function(){var t=this;t.labelMoments=[];var a=[];t.chart.data.labels&&t.chart.data.labels.length>0?(e.each(t.chart.data.labels,function(e,i){var n=t.parseTime(e);n.isValid()&&(t.options.time.round&&n.startOf(t.options.time.round),a.push(n))},t),t.firstTick=i.min.call(t,a),t.lastTick=i.max.call(t,a)):(t.firstTick=null,t.lastTick=null),e.each(t.chart.data.datasets,function(n,o){var r=[],l=t.chart.isDatasetVisible(o);"object"==typeof n.data[0]&&null!==n.data[0]?e.each(n.data,function(e,a){var n=t.parseTime(t.getRightValue(e));n.isValid()&&(t.options.time.round&&n.startOf(t.options.time.round),r.push(n),l&&(t.firstTick=null!==t.firstTick?i.min(t.firstTick,n):n,t.lastTick=null!==t.lastTick?i.max(t.lastTick,n):n))},t):r=a,t.labelMoments.push(r)},t),t.options.time.min&&(t.firstTick=t.parseTime(t.options.time.min)),t.options.time.max&&(t.lastTick=t.parseTime(t.options.time.max)),t.firstTick=(t.firstTick||i()).clone(),t.lastTick=(t.lastTick||i()).clone()},buildTicks:function(i){var n=this;n.ctx.save();var o=e.getValueOrDefault(n.options.ticks.fontSize,t.defaults.global.defaultFontSize),r=e.getValueOrDefault(n.options.ticks.fontStyle,t.defaults.global.defaultFontStyle),l=e.getValueOrDefault(n.options.ticks.fontFamily,t.defaults.global.defaultFontFamily),s=e.fontString(o,r,l);if(n.ctx.font=s,n.ticks=[],n.unitScale=1,n.scaleSizeInUnits=0,n.options.time.unit)n.tickUnit=n.options.time.unit||"day",n.displayFormat=n.options.time.displayFormats[n.tickUnit],n.scaleSizeInUnits=n.lastTick.diff(n.firstTick,n.tickUnit,!0),n.unitScale=e.getValueOrDefault(n.options.time.unitStepSize,1);else{var d=n.isHorizontal()?n.width-(n.paddingLeft+n.paddingRight):n.height-(n.paddingTop+n.paddingBottom),u=n.tickFormatFunction(n.firstTick,0,[]),c=n.ctx.measureText(u).width,h=Math.cos(e.toRadians(n.options.ticks.maxRotation)),f=Math.sin(e.toRadians(n.options.ticks.maxRotation));c=c*h+o*f;var g=d/c;n.tickUnit="millisecond",n.scaleSizeInUnits=n.lastTick.diff(n.firstTick,n.tickUnit,!0),n.displayFormat=n.options.time.displayFormats[n.tickUnit];for(var p=0,m=a.units[p];p<a.units.length;){if(n.unitScale=1,e.isArray(m.steps)&&Math.ceil(n.scaleSizeInUnits/g)<e.max(m.steps)){for(var b=0;b<m.steps.length;++b)if(m.steps[b]>=Math.ceil(n.scaleSizeInUnits/g)){n.unitScale=e.getValueOrDefault(n.options.time.unitStepSize,m.steps[b]);break}break}if(m.maxStep===!1||Math.ceil(n.scaleSizeInUnits/g)<m.maxStep){n.unitScale=e.getValueOrDefault(n.options.time.unitStepSize,Math.ceil(n.scaleSizeInUnits/g));break}++p,m=a.units[p],n.tickUnit=m.name;var v=n.firstTick.diff(n.getMomentStartOf(n.firstTick),n.tickUnit,!0),x=n.getMomentStartOf(n.lastTick.clone().add(1,n.tickUnit)).diff(n.lastTick,n.tickUnit,!0);n.scaleSizeInUnits=n.lastTick.diff(n.firstTick,n.tickUnit,!0)+v+x,n.displayFormat=n.options.time.displayFormats[m.name]}}var y;if(n.options.time.min?y=n.getMomentStartOf(n.firstTick):(n.firstTick=n.getMomentStartOf(n.firstTick),y=n.firstTick),!n.options.time.max){var k=n.getMomentStartOf(n.lastTick);0!==k.diff(n.lastTick,n.tickUnit,!0)&&(n.lastTick=n.getMomentStartOf(n.lastTick.add(1,n.tickUnit)))}n.smallestLabelSeparation=n.width,e.each(n.chart.data.datasets,function(t,e){for(var a=1;a<n.labelMoments[e].length;a++)n.smallestLabelSeparation=Math.min(n.smallestLabelSeparation,n.labelMoments[e][a].diff(n.labelMoments[e][a-1],n.tickUnit,!0))},n),n.options.time.displayFormat&&(n.displayFormat=n.options.time.displayFormat),n.ticks.push(n.firstTick.clone());for(var S=1;S<=n.scaleSizeInUnits;++S){var w=y.clone().add(S,n.tickUnit);if(n.options.time.max&&w.diff(n.lastTick,n.tickUnit,!0)>=0)break;S%n.unitScale===0&&n.ticks.push(w)}var C=n.ticks[n.ticks.length-1].diff(n.lastTick,n.tickUnit);(0!==C||0===n.scaleSizeInUnits)&&(n.options.time.max?(n.ticks.push(n.lastTick.clone()),n.scaleSizeInUnits=n.lastTick.diff(n.ticks[0],n.tickUnit,!0)):(n.ticks.push(n.lastTick.clone()),n.scaleSizeInUnits=n.lastTick.diff(n.firstTick,n.tickUnit,!0))),n.ctx.restore()},getLabelForIndex:function(t,e){var a=this,i=a.chart.data.labels&&t<a.chart.data.labels.length?a.chart.data.labels[t]:"";return"object"==typeof a.chart.data.datasets[e].data[0]&&(i=a.getRightValue(a.chart.data.datasets[e].data[t])),a.options.time.tooltipFormat&&(i=a.parseTime(i).format(a.options.time.tooltipFormat)),i},tickFormatFunction:function(t,a,i){var n=t.format(this.displayFormat),o=this.options.ticks,r=e.getValueOrDefault(o.callback,o.userCallback);return r?r(n,a,i):n},convertTicksToLabels:function(){var t=this;t.tickMoments=t.ticks,t.ticks=t.ticks.map(t.tickFormatFunction,t)},getPixelForValue:function(t,e,a,i){var n=this,o=t&&t.isValid&&t.isValid()?t:n.getLabelMoment(a,e);if(o){var r=o.diff(n.firstTick,n.tickUnit,!0),l=r/n.scaleSizeInUnits;if(n.isHorizontal()){var s=n.width-(n.paddingLeft+n.paddingRight),d=(s/Math.max(n.ticks.length-1,1),s*l+n.paddingLeft);return n.left+Math.round(d)}var u=n.height-(n.paddingTop+n.paddingBottom),c=(u/Math.max(n.ticks.length-1,1),u*l+n.paddingTop);return n.top+Math.round(c)}},getPixelForTick:function(t,e){return this.getPixelForValue(this.tickMoments[t],null,null,e)},getValueForPixel:function(t){var e=this,a=e.isHorizontal()?e.width-(e.paddingLeft+e.paddingRight):e.height-(e.paddingTop+e.paddingBottom),n=(t-(e.isHorizontal()?e.left+e.paddingLeft:e.top+e.paddingTop))/a;return n*=e.scaleSizeInUnits,e.firstTick.clone().add(i.duration(n,e.tickUnit).asSeconds(),"seconds")},parseTime:function(t){var e=this;return"string"==typeof e.options.time.parser?i(t,e.options.time.parser):"function"==typeof e.options.time.parser?e.options.time.parser(t):"function"==typeof t.getMonth||"number"==typeof t?i(t):t.isValid&&t.isValid()?t:"string"!=typeof e.options.time.format&&e.options.time.format.call?(console.warn("options.time.format is deprecated and replaced by options.time.parser. See http://nnnick.github.io/Chart.js/docs-v2/#scales-time-scale"),e.options.time.format(t)):i(t,e.options.time.format)}});t.scaleService.registerScaleType("time",o,n)}},{1:1}]},{},[7])(7)});
// randomColor by David Merfield under the MIT license
// https://github.com/davidmerfield/randomColor/
;(function(root, factory) {

  // Support AMD
  if (typeof define === 'function' && define.amd) {
    define('lib/randomcolor/randomColor',[], factory);

  // Support CommonJS
  } else if (typeof exports === 'object') {
    var randomColor = factory();

    // Support NodeJS & Component, which allow module.exports to be a function
    if (typeof module === 'object' && module && module.exports) {
      exports = module.exports = randomColor;
    }

    // Support CommonJS 1.1.1 spec
    exports.randomColor = randomColor;

  // Support vanilla script loading
  } else {
    root.randomColor = factory();
  }

}(this, function() {

  // Seed to get repeatable colors
  var seed = null;

  // Shared color dictionary
  var colorDictionary = {};

  // Populate the color dictionary
  loadColorBounds();

  var randomColor = function (options) {

    options = options || {};

    // Check if there is a seed and ensure it's an
    // integer. Otherwise, reset the seed value.
    if (options.seed && options.seed === parseInt(options.seed, 10)) {
      seed = options.seed;

    // A string was passed as a seed
    } else if (typeof options.seed === 'string') {
      seed = stringToInteger(options.seed);

    // Something was passed as a seed but it wasn't an integer or string
    } else if (options.seed !== undefined && options.seed !== null) {
      throw new TypeError('The seed value must be an integer');

    // No seed, reset the value outside.
    } else {
      seed = null;
    }

    var H,S,B;

    // Check if we need to generate multiple colors
    if (options.count !== null && options.count !== undefined) {

      var totalColors = options.count,
          colors = [];

      options.count = null;

      while (totalColors > colors.length) {

        // Since we're generating multiple colors,
        // incremement the seed. Otherwise we'd just
        // generate the same color each time...
        if (seed && options.seed) options.seed += 1;

        colors.push(randomColor(options));
      }

      options.count = totalColors;

      return colors;
    }

    // First we pick a hue (H)
    H = pickHue(options);

    // Then use H to determine saturation (S)
    S = pickSaturation(H, options);

    // Then use S and H to determine brightness (B).
    B = pickBrightness(H, S, options);

    // Then we return the HSB color in the desired format
    return setFormat([H,S,B], options);
  };

  function pickHue (options) {

    var hueRange = getHueRange(options.hue),
        hue = randomWithin(hueRange);

    // Instead of storing red as two seperate ranges,
    // we group them, using negative numbers
    if (hue < 0) {hue = 360 + hue;}

    return hue;

  }

  function pickSaturation (hue, options) {

    if (options.luminosity === 'random') {
      return randomWithin([0,100]);
    }

    if (options.hue === 'monochrome') {
      return 0;
    }

    var saturationRange = getSaturationRange(hue);

    var sMin = saturationRange[0],
        sMax = saturationRange[1];

    switch (options.luminosity) {

      case 'bright':
        sMin = 55;
        break;

      case 'dark':
        sMin = sMax - 10;
        break;

      case 'light':
        sMax = 55;
        break;
   }

    return randomWithin([sMin, sMax]);

  }

  function pickBrightness (H, S, options) {

    var bMin = getMinimumBrightness(H, S),
        bMax = 100;

    switch (options.luminosity) {

      case 'dark':
        bMax = bMin + 20;
        break;

      case 'light':
        bMin = (bMax + bMin)/2;
        break;

      case 'random':
        bMin = 0;
        bMax = 100;
        break;
    }

    return randomWithin([bMin, bMax]);
  }

  function setFormat (hsv, options) {

    switch (options.format) {

      case 'hsvArray':
        return hsv;

      case 'hslArray':
        return HSVtoHSL(hsv);

      case 'hsl':
        var hsl = HSVtoHSL(hsv);
        return 'hsl('+hsl[0]+', '+hsl[1]+'%, '+hsl[2]+'%)';

      case 'hsla':
        var hslColor = HSVtoHSL(hsv);
        return 'hsla('+hslColor[0]+', '+hslColor[1]+'%, '+hslColor[2]+'%, ' + Math.random() + ')';

      case 'rgbArray':
        return HSVtoRGB(hsv);

      case 'rgb':
        var rgb = HSVtoRGB(hsv);
        return 'rgb(' + rgb.join(', ') + ')';

      case 'rgba':
        var rgbColor = HSVtoRGB(hsv);
        return 'rgba(' + rgbColor.join(', ') + ', ' + Math.random() + ')';

      default:
        return HSVtoHex(hsv);
    }

  }

  function getMinimumBrightness(H, S) {

    var lowerBounds = getColorInfo(H).lowerBounds;

    for (var i = 0; i < lowerBounds.length - 1; i++) {

      var s1 = lowerBounds[i][0],
          v1 = lowerBounds[i][1];

      var s2 = lowerBounds[i+1][0],
          v2 = lowerBounds[i+1][1];

      if (S >= s1 && S <= s2) {

         var m = (v2 - v1)/(s2 - s1),
             b = v1 - m*s1;

         return m*S + b;
      }

    }

    return 0;
  }

  function getHueRange (colorInput) {

    if (typeof parseInt(colorInput) === 'number') {

      var number = parseInt(colorInput);

      if (number < 360 && number > 0) {
        return [number, number];
      }

    }

    if (typeof colorInput === 'string') {

      if (colorDictionary[colorInput]) {
        var color = colorDictionary[colorInput];
        if (color.hueRange) {return color.hueRange;}
      }
    }

    return [0,360];

  }

  function getSaturationRange (hue) {
    return getColorInfo(hue).saturationRange;
  }

  function getColorInfo (hue) {

    // Maps red colors to make picking hue easier
    if (hue >= 334 && hue <= 360) {
      hue-= 360;
    }

    for (var colorName in colorDictionary) {
       var color = colorDictionary[colorName];
       if (color.hueRange &&
           hue >= color.hueRange[0] &&
           hue <= color.hueRange[1]) {
          return colorDictionary[colorName];
       }
    } return 'Color not found';
  }

  function randomWithin (range) {
    if (seed === null) {
      return Math.floor(range[0] + Math.random()*(range[1] + 1 - range[0]));
    } else {
      //Seeded random algorithm from http://indiegamr.com/generate-repeatable-random-numbers-in-js/
      var max = range[1] || 1;
      var min = range[0] || 0;
      seed = (seed * 9301 + 49297) % 233280;
      var rnd = seed / 233280.0;
      return Math.floor(min + rnd * (max - min));
    }
  }

  function HSVtoHex (hsv){

    var rgb = HSVtoRGB(hsv);

    function componentToHex(c) {
        var hex = c.toString(16);
        return hex.length == 1 ? '0' + hex : hex;
    }

    var hex = '#' + componentToHex(rgb[0]) + componentToHex(rgb[1]) + componentToHex(rgb[2]);

    return hex;

  }

  function defineColor (name, hueRange, lowerBounds) {

    var sMin = lowerBounds[0][0],
        sMax = lowerBounds[lowerBounds.length - 1][0],

        bMin = lowerBounds[lowerBounds.length - 1][1],
        bMax = lowerBounds[0][1];

    colorDictionary[name] = {
      hueRange: hueRange,
      lowerBounds: lowerBounds,
      saturationRange: [sMin, sMax],
      brightnessRange: [bMin, bMax]
    };

  }

  function loadColorBounds () {

    defineColor(
      'monochrome',
      null,
      [[0,0],[100,0]]
    );

    defineColor(
      'red',
      [-26,18],
      [[20,100],[30,92],[40,89],[50,85],[60,78],[70,70],[80,60],[90,55],[100,50]]
    );

    defineColor(
      'orange',
      [19,46],
      [[20,100],[30,93],[40,88],[50,86],[60,85],[70,70],[100,70]]
    );

    defineColor(
      'yellow',
      [47,62],
      [[25,100],[40,94],[50,89],[60,86],[70,84],[80,82],[90,80],[100,75]]
    );

    defineColor(
      'green',
      [63,178],
      [[30,100],[40,90],[50,85],[60,81],[70,74],[80,64],[90,50],[100,40]]
    );

    defineColor(
      'blue',
      [179, 257],
      [[20,100],[30,86],[40,80],[50,74],[60,60],[70,52],[80,44],[90,39],[100,35]]
    );

    defineColor(
      'purple',
      [258, 282],
      [[20,100],[30,87],[40,79],[50,70],[60,65],[70,59],[80,52],[90,45],[100,42]]
    );

    defineColor(
      'pink',
      [283, 334],
      [[20,100],[30,90],[40,86],[60,84],[80,80],[90,75],[100,73]]
    );

  }

  function HSVtoRGB (hsv) {

    // this doesn't work for the values of 0 and 360
    // here's the hacky fix
    var h = hsv[0];
    if (h === 0) {h = 1;}
    if (h === 360) {h = 359;}

    // Rebase the h,s,v values
    h = h/360;
    var s = hsv[1]/100,
        v = hsv[2]/100;

    var h_i = Math.floor(h*6),
      f = h * 6 - h_i,
      p = v * (1 - s),
      q = v * (1 - f*s),
      t = v * (1 - (1 - f)*s),
      r = 256,
      g = 256,
      b = 256;

    switch(h_i) {
      case 0: r = v; g = t; b = p;  break;
      case 1: r = q; g = v; b = p;  break;
      case 2: r = p; g = v; b = t;  break;
      case 3: r = p; g = q; b = v;  break;
      case 4: r = t; g = p; b = v;  break;
      case 5: r = v; g = p; b = q;  break;
    }

    var result = [Math.floor(r*255), Math.floor(g*255), Math.floor(b*255)];
    return result;
  }

  function HSVtoHSL (hsv) {
    var h = hsv[0],
      s = hsv[1]/100,
      v = hsv[2]/100,
      k = (2-s)*v;

    return [
      h,
      Math.round(s*v / (k<1 ? k : 2-k) * 10000) / 100,
      k/2 * 100
    ];
  }

  function stringToInteger (string) {
    var total = 0
    for (var i = 0; i !== string.length; i++) {
      if (total >= Number.MAX_SAFE_INTEGER) break;
      total += string.charCodeAt(i)
    }
    return total
  }

  return randomColor;
}));
define(
    'entries/service/payStatistic',['jquery', 'Class', 'entries/util/util', 
      'template','lib/Chart.js/dist/Chart.min', 
      'datetimepicker', 'lib/lodash/dist/lodash.min', 
      'lib/randomcolor/randomColor', 'pages'],
    function ($, Class, Util, template, Chart, datetimepicker, _, randomcolor, daterangepicker) {
      var app = Class.create({
          setOptions: function (opts) {
            var options = {
              pageNum: 1,
              pageSize: 10,
              total: 0,
              status: 0,
              appCode: '',
              randomcolor: [],
              myChart: {}
            };
            $.extend(true, this, options, opts);
          }
        }, {
        init: function (opts) {
            //payStatistic Init
            this.setOptions(opts);
            this.bindEvent();
            this.getTabelData();
            this.getChartData();
            this.getAppData({ });
        },
        setRandomColorCache: function(size) {
          var colorSize = this.randomcolor.length;
          var i = 0;
          if (colorSize < size) {
            for (i=0; i< size - colorSize; i++) {
              this.randomcolor.push(randomcolor({format: 'rgbArray'}).join(','));
            }
          }
        },
        setLineColor: function(item, index) {
          var color = this.randomcolor[index];
          item.fill = false,
          item.backgroundColor = 'rgba(' + color + ',0.2)',
          item.borderColor = 'rgba(' + color + ',1)',
          item.pointBorderColor = 'rgba(' + color + ',1)',
          item.pointBackgroundColor = "#fff",
          item.pointHoverBackgroundColor = 'rgba(' + color + ',1)',
          item.pointHoverBorderColor = 'rgba(' + color + ')'

          return item;
        },
        bindEvent: function() {
          this.dateBind();
          this.searchBind();
          this.exportAllBind();
        },
        cloneBind: function () {
          $('.clone').on('click', function () {
              $('.alert_box').hide();
          });
        },
        searchBind: function() {
          $('#search_btn').on('click', function(e) {
            var params = this.formVal(e.target.parentNode);
            this.getTabelData(params);
            this.getChartData(params);
          }.bind(this));
        },
        exportAllBind: function() {
          $('#export_all_btn').on('click', function(e) {
            var params = this.formVal(e.target.parentNode);
            var url = Util.generateParams(params);

            Util.ajaxData('payStatistic/isCanExport', 'GET', {
              startTime: params.startTime,
              endTime: params.endTime
            }, function(data, bool) {
              if (bool) {
                window.location.href = Poss.host + 'payStatistic/exportTotalWatter' + url;
              }
            });
          }.bind(this));
        },
        exportAppBind: function() {
          $('#export-app').on('click', function(e) {
            var params = this.formVal($('#form1'));
            var appCode = $(e.target).attr('data-appCode');
            var url = Util.generateParams(_.assign(params, {appCode: appCode}));

            Util.ajaxData('payStatistic/isCanExport', 'GET', {
              startTime: params.startTime,
              endTime: params.endTime
            }, function(data, bool) {
              if (bool) {
                window.location.href = Poss.host + 'payStatistic/exportAppWatter' + url;
              }
            });
          }.bind(this));
        },
        dateBind: function () {
          $('.datetimepicker2').datetimepicker({
              lang: 'ch',
              timepicker: false,
              yearStart: 1970,     //设置最小年份
              yearEnd: 2020,        //设置最大年份
              format: "Y-m-d"
          });
        },
        getTabelData: function (params) {
          if (!params) {
            params = {};
          }
          Util.ajaxData('payStatistic/allWaterList', 'GET', params, function (d, bool) {
            if (bool) {
              var html = template('tpl_table', d.data);

              this.render(html);
            }
          }.bind(this));
        },
        getChartData: function(params) {
          if (!params) {
            params = {};
          }
          Util.ajaxData('payStatistic/queryPayChart', 'GET', params, function(data, bool) {
            if (bool) {
              var chartData = data.data,
                  scales = { 
                    yAxes: [{
                      ticks: {
                        beginAtZero: true,
                        callback: function(value, index, values) {
                          return value.toFixed(2) + '元';
                        }
                      }
                    }]
                  };

              this.setRandomColorCache(chartData.datasets.length);
              chartData.datasets = _.map(chartData.datasets, this.setLineColor.bind(this));

              var ctx = document.getElementById("chart");

              if (this.myChart.destroy) {
                this.myChart.destroy();
              }

              this.myChart = new Chart(ctx, {
                type: 'line',
                data: chartData,
                options: {
                  tooltips: {
                    callbacks: {
                      label: function(tooltipItem, data) {
                        var label = data.datasets[tooltipItem.datasetIndex].label;
                        return label + ': ' + tooltipItem.yLabel + '元';
                      }
                    }
                  },
                  legend: {
                    display: true,
                    position: 'bottom'
                  },
                  title: {
                    display: true,
                    text: '流水折线图'
                  },
                  scales: scales
                }
              });

            }
          }.bind(this));
        },
        getAppData: function(params) {
          Util.ajaxData('payStatistic/queryAppCode', 'GET', params, function (d, bool) {
            if (bool) {
              var html = template('tpl_apps', { list: d.data.list });

              this.renderApps(html);
            }
          }.bind(this));
        },
        getDetailData: function(status) {
          var that = this;
          var params = this.formVal($('#form1'));
          var data = {
            appCode: this.appCode,
            pageNo: this.pageNum,
            pageSize: this.pageSize
          };


          if (this.appCode) {
            Util.ajaxData('payStatistic/list', 'GET', _.assign(params, data), function (result, bool) {
              if (bool) {               
                this.total = result.data.totalCount;
                result.data.pageNum = Util.pageStart(this.pageNum, this.pageSize, this.total);
                result.data.sizeTotal = Util.pageEnd(Number(result.data.list.length), Number(this.pageNum), this.pageSize);

                var html = template('tpl_detail', {
                  list: result.data.list,
                  params: data,
                  pagination: {
                    total: this.total,
                    pageNum: result.data.pageNum,
                    sizeTotal: result.data.sizeTotal
                  }
                });
                that.renderDetail(html);
                $('#detailPanel').show();
              }
            }.bind(this));
          }
        },
        render: function (html) {
          $('#tbody').empty().append(html);
          this.detail();
        },
        renderDetail: function(html) {
          $('#detail').empty().append(html);
          this.cloneBind();
          this.exportAppBind();
          this.pageBind();    
        },
        renderApps: function(html) {
          $('.appCode_sel').empty().append(html);
        },
        detail: function () {
          $('.detail').on('click', function (e) {
            this.status = 0;
            var appCode = $(e.target).attr('appCode');
            this.appCode = appCode;
            this.getDetailData();
          }.bind(this));
        },
        formVal: function (El) {
          var form = $(El), obj = {};
          form.find('input:text').each(function () {
              if ($(this).val()) {
                  obj[$(this).attr('data-name')] = $(this).val();
              }
          });
          obj[form.find('select').attr('data-name')] = form.find('select').val();
          return obj;
        },
        pageBind: function () {
          var status = 0;
          Util.pageBind(this.total, this.pageSize, function (i) {
            this.pageNum = i + 1;
            if (status == 0) {
                status = 1;
            } else {
                this.getDetailData();
            }
          }.bind(this), this.pageNum - 1);
        }
      });

      return app;
    }
);
/**
 * Created by leilihuang on 16/4/11.
 */
require(
    ['jquery','entries/util/base','entries/service/payStatistic'],
    function ($,Base, PayStatistic) {
        $(function () {
            Base.init(function () {
                new PayStatistic();
            })
        });
    }
);
define("entries/action/payStatistic.js", function(){});

/**
 * Created by leilihuang on 16/4/8.
 */
(function () {
    var obj = document.getElementById('requirejs'),
        baseUrl = obj && obj.getAttribute('data-url') ? obj.getAttribute('data-url') : './',
        isDebug = 0,
        bool=true;

    function getBaseUrl(url) {
        return baseUrl + url;
    }

    if(typeof(window.platform)!='object'){
        window.platform={};
    }

    Poss={
        // host:'http://localhost:8001/',
        host:'http://open.domestore.cn/',
        channelUrl:'http://channel.domestore.cn/',
        baseLog:'',       
        getLog: function (url) {
            return this.baseLog+url;
        },
        getHost: function (url) {
            return this.host+url;
        },
        getChannelUrl:function(url){
            return this.channelUrl+url;
        },
        /**打印日志
         * @text 内容
         * @status 0 在控制台打印  1 alert弹出层打印
         * */
        isDeBug: function (text,status) {
            if(!status){
                status=0;
            }
            if(bool){
                if(status==0){
                    console.log(text);
                }else{
                    alert(text);
                }
            }
        },
        callbackUrl: function (url) {
            console.log(111);
            if(!url){
                url=location.search.split('??')[1];
            }else{
                url=url.split('??')[1];
            }
            return url;
        },
        getUrl: function (url) {
            if(!url){
                url=location.search;
            }else{
                url=url.split('?')[1];
            }
            var theRequest = {},strs;
            if (url.indexOf("?") != -1) {
                var str = url.substr(1);
                strs = str.split("&&");
                for(var i = 0; i < strs.length; i ++) {
                    theRequest[strs[i].split("=")[0]]=decodeURI(strs[i].split("=")[1]);
                }
            }
            return theRequest;
        },
        setEncodeURI: function (str) {
            return encodeURI(str);
        },
        getDecodeURI: function (str) {
            return decodeURI(str);
        }
    };

    var config = {
        paths: {
            jquery: [
                getBaseUrl('jquery.min')
            ],
            template: [
                getBaseUrl('template')
            ],
            pages:[
                getBaseUrl('jquery.pagination')
            ],
            iCheck:[
                getBaseUrl('icheck.min')
            ],
            Class:[
                getBaseUrl('class')
            ],
            datetimepicker:[
                getBaseUrl('jquery.datetimepicker')
            ],
            fileUpload:[
                getBaseUrl('webuploader')
            ],
            lodash: [
                getBaseUrl('lodash/dist/lodash.min')
            ],
            Mock:[
                getBaseUrl('mockjs/dist/mock-min')
            ]
        },
        shim: {
            jquery: {
                deps: [],
                exports: '$'
            },
            template: {
                deps: ['jquery'],
                exports: 'template'
            },
            pages:{
                deps:['jquery'],
                exports:'pages'
            },
            iCheck:{
                deps:['jquery'],
                exports:'iCheck'
            },
            Class:{
                deps:[],
                exports:'Class'
            },
            datetimepicker:{
                deps:['jquery'],
                exports:'datetimepicker'
            },
            fileUpload:{
                deps:['jquery'],
                exports:'fileUpload'
            },
            lodash: {
                deps: [],
                exports: '_'
            },
            Mock:{
                deps:['jquery'],
                exports:'Mock'
            }
        }
    };
    require.config(config);
})();
define("entries/main.js", function(){});

