!function(t){var e={};function r(n){if(e[n])return e[n].exports;var o=e[n]={i:n,l:!1,exports:{}};return t[n].call(o.exports,o,o.exports,r),o.l=!0,o.exports}r.m=t,r.c=e,r.d=function(t,e,n){r.o(t,e)||Object.defineProperty(t,e,{enumerable:!0,get:n})},r.r=function(t){"undefined"!=typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(t,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(t,"__esModule",{value:!0})},r.t=function(t,e){if(1&e&&(t=r(t)),8&e)return t;if(4&e&&"object"==typeof t&&t&&t.__esModule)return t;var n=Object.create(null);if(r.r(n),Object.defineProperty(n,"default",{enumerable:!0,value:t}),2&e&&"string"!=typeof t)for(var o in t)r.d(n,o,function(e){return t[e]}.bind(null,o));return n},r.n=function(t){var e=t&&t.__esModule?function(){return t.default}:function(){return t};return r.d(e,"a",e),e},r.o=function(t,e){return Object.prototype.hasOwnProperty.call(t,e)},r.p="",r(r.s=9)}([function(t,e,r){"use strict";var n=r(5),o=r(2),i=r(8);var a=Object(i.a)(o.default,n.b,n.c,!1,null,"7807aad8","7d2541a0",!1,n.a,void 0);(function(t){this.options.style||(this.options.style={}),Vue.prototype.__merge_style&&Vue.prototype.__$appStyle__&&Vue.prototype.__merge_style(Vue.prototype.__$appStyle__,this.options.style),Vue.prototype.__merge_style?Vue.prototype.__merge_style(r(7).default,this.options.style):Object.assign(this.options.style,r(7).default)}).call(a),e.default=a.exports},function(t,e){t.exports={"@VERSION":2}},function(t,e,r){"use strict";var n=r(3),o=r.n(n);e.default=o.a},function(t,e,r){"use strict";(function(t){var n=r(14);Object.defineProperty(e,"__esModule",{value:!0}),e.default=void 0;var o=n(r(15)),i={data:function(){var t;return t={msg:"",isFull:!1,duration:36e5,fullControlsWidth:"200",current:0,leftTime:"00:00:00",rightTime:"00:00:00",statusBarHeight:"20px",getStatusBarHeight:"20px",headerHeight:"44px",palyerHeight:"500rpx",tipTop:"250rpx",tip:"\u52a0\u8f7d\u4e2d...",isShowTip:!0,isShowTitle:!0,hideTitleTime:6e3},(0,o.default)(t,"isFull",!1),(0,o.default)(t,"playOrPauseText","\u64ad\u653e"),(0,o.default)(t,"videoNowSatus",!1),(0,o.default)(t,"touchHandleType",!1),(0,o.default)(t,"handleDynamicX",0),(0,o.default)(t,"handleDynamicY",0),(0,o.default)(t,"videoNowLight",0),(0,o.default)(t,"videoNowVoice",0),(0,o.default)(t,"onceClickTimer",null),(0,o.default)(t,"changing",!1),t},onLoad:function(){},methods:{play:function(){this.$refs.vlc.toPlay()},pause:function(){t("log","pause",this.$refs.vlc,"this.$refs.vlcthis.$refs.vlc"," at pages/index/index.nvue:74"),this.$refs.vlc.pause()},stop:function(){this.$refs.vlc.stop()},full:function(){this.isFull?plus.screen.lockOrientation("portrait-primary"):plus.screen.lockOrientation("landscape-primary"),this.isFull=!this.isFull},onTimeChanged:function(e){t("log","onTimeChanged:"+JSON.stringify(e)," at pages/index/index.nvue:91"),0==this.changing&&(this.current=e.detail.time)},onTotalTime:function(t){this.duration=t.detail.time},init:function(){this.tip="\u7b49\u5f85\u64ad\u653e",this.isShowTip=!0,this.$refs.vlc.init({urlType:"network",url:"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=164016&resourceType=video&editionType=high&source=aliyun&playUrlType=url_oss",options:["--rtsp-tcp","--audio-time-stretch","-vvv"],mediaOptions:[":network-caching=3000",":clock-jitter=0",":clock-synchro=0",":fullscreen"],HWDecoder:{enabled:!1,force:!1}}),this.playOrPauseText="\u64ad\u653e",this.duration=36e5,this.current=0}}};e.default=i}).call(this,r(13).default)},function(t,e){t.exports={".title":{".video-box ":{fontSize:["20",0,1,0],marginTop:["20",0,1,0],marginBottom:["20",0,1,0],fontWeight:["700",0,1,0],textAlign:["center",0,1,0]}},".picture":{".video-box ":{flexDirection:["row",0,1,1]}},".img":{".video-box .picture ":{width:["200",0,2,2],height:["200",0,2,2]}},".picture2":{".video-box ":{justifyContent:["center",0,1,3],alignItems:["center",0,1,3]}},"@VERSION":2}},function(t,e,r){"use strict";r.d(e,"b",(function(){return n})),r.d(e,"c",(function(){return o})),r.d(e,"a",(function(){}));var n=function(){var t=this,e=t.$createElement,r=t._self._c||e;return r("scroll-view",{staticStyle:{flexDirection:"column"},attrs:{scrollY:!0,showScrollbar:!0,enableBackToTop:!0,bubble:"true"}},[r("div",{staticClass:["video-box"]},[r("u-text",{staticClass:["title"],appendAsTree:!0,attrs:{append:"tree"}},[t._v("vlc\u4e07\u80fd\u64ad\u653e\u5668")]),r("programbao-vlc-video",{ref:"vlc",staticStyle:{height:"200"},attrs:{play:"http://10.10.47.244/movie/D/movie/%E9%80%9F%E5%BA%A6%E4%B8%8E%E6%BF%80%E6%83%85S10/FAST.10.2023.1080p.H264.%E5%86%85%E5%B5%8C%E4%B8%AD%E5%AD%97.mp4"},on:{onStateChanged:t.onStateChanged,onTimeChanged:t.onTimeChanged,onTotalTime:t.onTotalTime}}),r("u-text",{appendAsTree:!0,attrs:{append:"tree"}},[t._v(t._s(t.current))]),r("u-text",{appendAsTree:!0,attrs:{append:"tree"}},[t._v(t._s(t.duration))]),r("view",{staticClass:["btns"]},[r("button",{attrs:{type:"primary"},on:{click:t.init}},[t._v("\u521d\u59cb\u5316")]),r("button",{attrs:{type:"primary"},on:{click:t.play}},[t._v("\u64ad\u653e")]),r("button",{attrs:{type:"primary"},on:{click:t.pause}},[t._v("\u6682\u505c")]),r("button",{attrs:{type:"primary"},on:{click:t.stop}},[t._v("\u505c\u6b62")]),r("button",{attrs:{type:"primary"},on:{click:t.full}},[t._v("\u5168\u5c4f")])],1),r("div",{staticClass:["picture","picture2"]},[r("u-image",{staticClass:["img"],attrs:{src:"../../static/video.png",mode:"aspectFit"}})],1),r("div",{staticClass:["picture"]},[r("u-image",{staticClass:["img"],attrs:{src:"../../static/subtitles.png",mode:"aspectFit"}}),r("u-image",{staticClass:["img"],attrs:{src:"../../static/audio.png",mode:"aspectFit"}})],1)],1)])},o=[]},function(t,e){function r(e){return t.exports=r="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(t){return typeof t}:function(t){return t&&"function"==typeof Symbol&&t.constructor===Symbol&&t!==Symbol.prototype?"symbol":typeof t},t.exports.__esModule=!0,t.exports.default=t.exports,r(e)}t.exports=r,t.exports.__esModule=!0,t.exports.default=t.exports},function(t,e,r){"use strict";r.r(e);var n=r(4),o=r.n(n);for(var i in n)["default"].indexOf(i)<0&&function(t){r.d(e,t,(function(){return n[t]}))}(i);e.default=o.a},function(t,e,r){"use strict";function n(t,e,r,n,o,i,a,s,u,l){var c,p="function"==typeof t?t.options:t;if(u){p.components||(p.components={});var f=Object.prototype.hasOwnProperty;for(var d in u)f.call(u,d)&&!f.call(p.components,d)&&(p.components[d]=u[d])}if(l&&("function"==typeof l.beforeCreate&&(l.beforeCreate=[l.beforeCreate]),(l.beforeCreate||(l.beforeCreate=[])).unshift((function(){this[l.__module]=this})),(p.mixins||(p.mixins=[])).push(l)),e&&(p.render=e,p.staticRenderFns=r,p._compiled=!0),n&&(p.functional=!0),i&&(p._scopeId="data-v-"+i),a?(c=function(t){(t=t||this.$vnode&&this.$vnode.ssrContext||this.parent&&this.parent.$vnode&&this.parent.$vnode.ssrContext)||"undefined"==typeof __VUE_SSR_CONTEXT__||(t=__VUE_SSR_CONTEXT__),o&&o.call(this,t),t&&t._registeredComponents&&t._registeredComponents.add(a)},p._ssrRegister=c):o&&(c=s?function(){o.call(this,this.$root.$options.shadowRoot)}:o),c)if(p.functional){p._injectStyles=c;var y=p.render;p.render=function(t,e){return c.call(e),y(t,e)}}else{var v=p.beforeCreate;p.beforeCreate=v?[].concat(v,c):[c]}return{exports:t,options:p}}r.d(e,"a",(function(){return n}))},function(t,e,r){"use strict";r.r(e);r(10),r(12);var n=r(0);n.default.mpType="page",n.default.route="pages/index/index",n.default.el="#root",new Vue(n.default)},function(t,e,r){Vue.prototype.__$appStyle__={},Vue.prototype.__merge_style&&Vue.prototype.__merge_style(r(11).default,Vue.prototype.__$appStyle__)},function(t,e,r){"use strict";r.r(e);var n=r(1),o=r.n(n);for(var i in n)["default"].indexOf(i)<0&&function(t){r.d(e,t,(function(){return n[t]}))}(i);e.default=o.a},function(t,e){if("undefined"==typeof Promise||Promise.prototype.finally||(Promise.prototype.finally=function(t){var e=this.constructor;return this.then((function(r){return e.resolve(t()).then((function(){return r}))}),(function(r){return e.resolve(t()).then((function(){throw r}))}))}),"undefined"!=typeof uni&&uni&&uni.requireGlobal){var r=uni.requireGlobal();ArrayBuffer=r.ArrayBuffer,Int8Array=r.Int8Array,Uint8Array=r.Uint8Array,Uint8ClampedArray=r.Uint8ClampedArray,Int16Array=r.Int16Array,Uint16Array=r.Uint16Array,Int32Array=r.Int32Array,Uint32Array=r.Uint32Array,Float32Array=r.Float32Array,Float64Array=r.Float64Array,BigInt64Array=r.BigInt64Array,BigUint64Array=r.BigUint64Array}},function(t,e,r){"use strict";function n(t){var e=Object.prototype.toString.call(t);return e.substring(8,e.length-1)}function o(){return"string"==typeof __channelId__&&__channelId__}function i(t,e){switch(n(e)){case"Function":return"function() { [native code] }";default:return e}}Object.defineProperty(e,"__esModule",{value:!0}),e.default=function(){for(var t=arguments.length,e=new Array(t),r=0;r<t;r++)e[r]=arguments[r];var a=e.shift();if(o())return e.push(e.pop().replace("at ","uni-app:///")),console[a].apply(console,e);var s=e.map((function(t){var e=Object.prototype.toString.call(t).toLowerCase();if("[object object]"===e||"[object array]"===e)try{t="---BEGIN:JSON---"+JSON.stringify(t,i)+"---END:JSON---"}catch(r){t=e}else if(null===t)t="---NULL---";else if(void 0===t)t="---UNDEFINED---";else{var r=n(t).toUpperCase();t="NUMBER"===r||"BOOLEAN"===r?"---BEGIN:"+r+"---"+t+"---END:"+r+"---":String(t)}return t})),u="";if(s.length>1){var l=s.pop();u=s.join("---COMMA---"),0===l.indexOf(" at ")?u+=l:u+="---COMMA---"+l}else u=s[0];console[a](u)},e.log=function(t){for(var e=arguments.length,r=new Array(e>1?e-1:0),n=1;n<e;n++)r[n-1]=arguments[n];console[t].apply(console,r)}},function(t,e){t.exports=function(t){return t&&t.__esModule?t:{default:t}},t.exports.__esModule=!0,t.exports.default=t.exports},function(t,e,r){var n=r(16);t.exports=function(t,e,r){return(e=n(e))in t?Object.defineProperty(t,e,{value:r,enumerable:!0,configurable:!0,writable:!0}):t[e]=r,t},t.exports.__esModule=!0,t.exports.default=t.exports},function(t,e,r){var n=r(6).default,o=r(17);t.exports=function(t){var e=o(t,"string");return"symbol"===n(e)?e:String(e)},t.exports.__esModule=!0,t.exports.default=t.exports},function(t,e,r){var n=r(6).default;t.exports=function(t,e){if("object"!==n(t)||null===t)return t;var r=t[Symbol.toPrimitive];if(void 0!==r){var o=r.call(t,e||"default");if("object"!==n(o))return o;throw new TypeError("@@toPrimitive must return a primitive value.")}return("string"===e?String:Number)(t)},t.exports.__esModule=!0,t.exports.default=t.exports}]);