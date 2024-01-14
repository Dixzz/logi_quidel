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

-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

-keep class org.json.*
-keepclassmembers,includedescriptorclasses class org.json.* { *; }

-keepattributes JavascriptInterface
-keepattributes *Annotation*

-optimizations !method/inlining/*

-keepclasseswithmembers class * {
  public void onPayment*(...);
}

-keep public class *

#Specifies not to ignore non-public library classes.
-dontskipnonpubliclibraryclasses

#Specifies not to ignore package visible library class members
-dontskipnonpubliclibraryclassmembers

-optimizationpasses 5
#Specifies that the access modifiers of classes and class members may have become broad during processing. This can improve the results of the optimization step.
-allowaccessmodification
#Specifies that interfaces may be merged, even if their implementing classes don't implement all interface methods. This can reduce the size of the output by reducing the total number of classes.
-mergeinterfacesaggressively

#Specifies to apply aggressive overloading while obfuscating. Multiple fields and methods can then get the same names, This option can make the processed code even smaller
#-overloadaggressively

#Specifies to repackage all packages that are renamed, by moving them into the single given parent package
-flattenpackagehierarchy

#Specifies to repackage all class files that are renamed, by moving them into the single given package. Without argument or with an empty string (''), the package is removed completely.
-repackageclasses

#For example, if your code contains a large number of hard-coded strings that refer to classes, and you prefer not to keep their names, you may want to use this option
-adaptclassstrings
#Specifies the resource files to be renamed, all resource files that correspond to class files are renamed
-adaptresourcefilenames

#Specifies the resource files whose contents are to be updated. Any class names mentioned in the resource files are renamed
-adaptresourcefilecontents

#Specifies not to verify the processed class files.
#-dontpreverify

-verbose

#Specifies to print any warnings about unresolved references and other important problems, but to continue processing in any case.
-ignorewarnings

# ADDED
#-dontobfuscate
#-useuniqueclassmembernames

# For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
    public static *** w(...);
    public static *** e(...);
}

-keepnames class * implements java.io.Serializable

-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keepclasseswithmembers,allowobfuscation,includedescriptorclasses class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

-keepclassmembers enum * {
    @com.google.gson.annotations.SerializedName <fields>;
}

##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-dontwarn sun.misc.**
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.* { <fields>; }

# Prevent proguard from stripping interface information from TypeAdapter, TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Prevent R8 from leaving Data object members always null
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

-keep class com.lc.loginext.quidel.bmtc.models.** { *; }
-keep class com.lc.loginext.quidel.bmtc.models.* { *; }
 -keep class com.lc.loginext.quidel.bmtc.models**
 -keep class com.lc.loginext.quidel.bmtc.models.**
 -keepclassmembers class com.lc.loginext.quidel.bmtc.models** {*;}
 -keepclassmembers class com.lc.loginext.quidel.bmtc.models.** {*;}

##---------------End: proguard configuration for Gson  ----------
-keep class androidx.appcompat.widget.** { *; }

## Branch SDK related
-keep class com.google.android.gms.** { *; }
-keep class com.facebook.applinks.** { *; }
-keepclassmembers class com.facebook.applinks.** { *; }
-keep class com.facebook.FacebookSdk { *; }

# support for Huawei devices without Google Mobile Services
-keep class com.huawei.hms.ads.** { *; }
-keep interface com.huawei.hms.ads.** { *; }
##

######## Start: proguard rules for Map My India -------------------------------
# Retrofit 2
# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on RoboVM on iOS. Will not be used at runtime.
-dontnote retrofit2.Platform
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes *Annotation*,Signature
# Retain declared checked exceptions for use by a Proxy instance.
 -keepattributes Exceptions
# For using GSON @Expose annotation
-keepattributes *Annotation*
# Gson specific classes
-dontwarn sun.misc.**
-dontwarn okio.**
-dontwarn okhttp3.**
-keep class retrofit.**
-keep class retrofit.** { *; }
-keepclasseswithmembers class * {
 @retrofit.http.* <methods>;
}

-keep class androidx.lifecycle.** {*;}
-dontoptimize -dontobfuscate -keepattributes SourceFile,LineNumberTable -keep class com.google.mlkit.** { *; }