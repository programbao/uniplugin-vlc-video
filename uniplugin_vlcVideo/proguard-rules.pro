# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
#module扩展的混淆
-keep public class * extends com.taobao.weex.common.WXModule{*;}
#component扩展混淆
-keep public class * extends com.taobao.weex.ui.component.WXComponent{*;}

-keep public class * extends io.dcloud.weex.AppHookProxy{*;}
-keep public class * extends io.dcloud.feature.uniapp.UniAppHookProxy{*;}
-keep public class * extends io.dcloud.feature.uniapp.common.UniModule{*;}
-keep public class * extends io.dcloud.feature.uniapp.ui.component.UniComponent{*;}

-keep class org.videolan.** { *; }