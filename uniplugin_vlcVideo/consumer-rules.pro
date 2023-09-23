#module扩展的混淆
-keep public class * extends com.taobao.weex.common.WXModule{*;}
#component扩展混淆
-keep public class * extends com.taobao.weex.ui.component.WXComponent{*;}