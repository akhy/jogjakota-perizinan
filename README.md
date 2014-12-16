jogjakota-perizinan
===================

Simple Android app for TKI assignment

You need Android Studio IDE and Android SDK to properly edit this project.

## Building via commandline

You need at least Android SDK to build APK via command line.

1. Create `local.properties` in project root directory and put this line inside:

	```
	sdk.dir=/Path/to/your/Android/SDK
	```

2. Execute this command to generate APK, replace `./gradlew` with `gradlew.bat` for Windows users.

	```
	./gradlew assemble
	```

3. APK(s) can be found at `{projectRoot}/app/build/outputs/apk/`


