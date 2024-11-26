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
# Bảo vệ Retrofit và các lớp liên quan
-keep class com.example.duan1_customer.model.** { *; }
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes RuntimeVisibleAnnotations
-keepattributes *Annotation*
-keepattributes Annotation
# Bảo vệ Gson và các lớp phản ánh (reflection)
-keepclassmembers class com.google.gson.** {
    *;
}
-keepattributes EnclosingMethod
-keepattributes InnerClasses

# Giữ nguyên các trường được gán annotation của Gson
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}
-keep class com.bumptech.glide.** { *; }
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class com.bumptech.glide.** { *; }
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}
-dontwarn io.reactivex.**
-keep class io.reactivex.** { *; }
-keep class com.google.android.gms.** { *; }
-dontwarn com.google.android.gms.**