Modules
===============

Library is also distributed via **separate modules** which may be downloaded as standalone parts of
the library in order to decrease dependencies count in Android projects, so only dependencies really
needed in an Android project are included. **However** some modules may depend on another modules
from this library or on modules from other libraries.

Below are listed modules that are available for download also with theirs dependencies.

## Download ##

### Gradle ###

For **successful resolving** of artifacts for separate modules via **Gradle** add the following snippet
into **build.gradle** script of your desired Android project and use `compile '...'` declaration
as usually.

    repositories {
        maven {
            url  "http://dl.bintray.com/universum-studios/android"
        }
    }

## Available modules ##
> Following modules are available in the [latest](https://github.com/universum-studios/android_image_loader/releases "Latest Releases page") release.

**[Interface](https://github.com/universum-studios/android_image_loader/tree/master/library/src/main)**

    compile 'universum.studios.android:image-loader:0.6.1@aar'

**[Base](https://github.com/universum-studios/android_image_loader/tree/master/library/src/base)**

    compile 'universum.studios.android:image-loader-base:0.6.1@aar'

**[Glide](https://github.com/universum-studios/android_image_loader/tree/master/library/src/glide)**

    compile 'universum.studios.android:image-loader-glide:0.6.1@aar'

_depends on:_
[`com.github.bumptech.glide:glide:3.7.0`](https://github.com/bumptech/glide)

**[Picasso](https://github.com/universum-studios/android_image_loader/tree/master/library/src/picasso)**

    compile 'universum.studios.android:image-loader-picasso:0.6.1@aar'

_depends on:_
[`com.squareup.picasso:picasso:2.5.2`](http://square.github.io/picasso/)

**[UniversalImageLoader](https://github.com/universum-studios/android_image_loader/tree/master/library/src/universal)**

    compile 'universum.studios.android:image-loader-universal:0.6.1@aar'

_depends on:_
[`com.nostra13.universalimageloader:universal-image-loader:1.9.5`](https://github.com/nostra13/Android-Universal-Image-Loader)

**[Volley](https://github.com/universum-studios/android_image_loader/tree/master/library/src/volley)**

    not avilable

_depends on:_
[`com.mcxiaoke.volley:library-aar:1.0.0`](http://developer.android.com/training/volley/index.html)

