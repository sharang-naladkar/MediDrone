diff --git a/app/build.gradle.kts b/app/build.gradle.kts
index 0000000..0000000 100644
--- a/app/build.gradle.kts
+++ b/app/build.gradle.kts
@@
     // Hilt
     implementation("com.google.dagger:hilt-android:2.47")
     kapt("com.google.dagger:hilt-compiler:2.47")
+    // Hilt navigation for Compose
+    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
@@
     // Firebase BOM (FCM)
     implementation(platform("com.google.firebase:firebase-bom:32.0.0"))
     implementation("com.google.firebase:firebase-messaging")
