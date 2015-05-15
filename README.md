#JavaFlacEncoder
This repository packages the JavaFlacEncoder library (current version v0.2.3) so that gradle can find it when included into android apps.

##How to include in an android App
In the `build.gradle` file for the `app` module, you need to add this repository as a maven repository:
```
repositories {
    maven {
        url "https://raw.githubusercontent.com/g-i-o-/JavaFlacEncoder/master/"
    }
}

```

Then you need to add `javaFlacEncoder` in the dependencies:
```
dependencies {
    compile 'javaFlacEncoder:javaFlacEncoder:0.2.3'
}
```

That should do it.