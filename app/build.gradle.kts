diff --git a/app/build.gradle.kts b/app/build.gradle.kts
index e69de29..0000000 100644
--- a/app/build.gradle.kts
+++ b/app/build.gradle.kts
@@
     // MQTT (placeholder)
     implementation("org.eclipse.paho:org.eclipse.paho.client.mqttv3:1.2.5")
+    // Security crypto for EncryptedSharedPreferences
+    implementation("androidx.security:security-crypto:1.1.0-alpha03")
+    // OkHttp logging
+    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
*** End Patch
