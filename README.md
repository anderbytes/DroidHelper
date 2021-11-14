# DroidHelper
<b>Swiss Army Knife of Helpers for Android Applications</b>

The sole purpose of this library is to be a extremely useful collection of common methods that can be used across several Android projects, allowing fellow devs to keep their concerns on the core of their apps.

This library tends to increase in quantity of methods, as you are free to suggest changes and improvements, being them justified and accepted, of course ;)

## Helpers

This library, at of today, has in it a team of professionals (<i>they're packages inside Kotlin but let's not tell this to them</i>) that have separated responsabilities:

### Architect
Methods to calculate heights, widhts, DPIs, pixels, and all those  beautiful new concepts that make us crazy every time they create more advanced devices

### Butler
General purpose methods that we all need sometimes

### Chrono
Methods that will ease your messing with time. Because we are all here to bargain...

### Collector
Methods that intend to make it simplier to deal with any type of collection (Lists, Arrays, and more)

### Enigma
We're all using those crypto weaponry sometimes. Here I'm going to consolidate the most common used ones.

### Maestro
Going from here to there? This guy can agilize things

### Nurse
This one can keep an eye on your stats and provide some useful views

### Teacher
Common methods to operate with words and texts

### Zero
Common methods to operate with numbers


## Installation

Gradle dependency:

```gradle
allprojects {
   repositories {
       	maven { url "https://jitpack.io" }
   }
}
```

Go to the build.gradle of your project, and inser the following link in the dependencies:

```gradle
dependencies {
  ...
  implementation 'com.github.anderbytes:DroidHelper:0.96'
}
```

## 📃 Changelog
<li>1.20 - First official release
<li>0.96 - Still in alpha. Will add incremental new methods after 1.00
