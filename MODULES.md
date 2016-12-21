Modules
===============

Library is also distributed via **separate modules** which may be downloaded as standalone parts of
the library in order to decrease dependencies count in Android projects, so only dependencies really
need in an Android project are included. **However** some modules may depend on another modules from
this library or on modules from other libraries.

Below are listed modules that are available for download also with theirs dependencies.

## Download ##

### Gradle ###

**Interface**

    compile 'com.albedinsky.android:image-loader:0.6.1@aar'

**Base implementation**

    compile 'com.albedinsky.android:image-loader-base:0.6.1@aar'

**[Glide 3.7.0](https://github.com/bumptech/glide)**

    compile 'com.albedinsky.android:image-loader-glide:0.6.1@aar'

_depends on:_
[`com.github.bumptech.glide:glide:3.7.0`](https://github.com/bumptech/glide)

**[Picasso 2.5.2](http://square.github.io/picasso/)**

    compile 'com.albedinsky.android:image-loader-picasso:0.6.1@aar'

_depends on:_
[`com.squareup.picasso:picasso:2.5.2`](http://square.github.io/picasso/)

**[Volley 1.0.0](http://developer.android.com/training/volley/index.html)**

    not avilable

_depends on:_
[`com.mcxiaoke.volley:library-aar:1.0.0`](http://developer.android.com/training/volley/index.html)

**[UniversalImageLoader 1.9.5](https://github.com/nostra13/Android-Universal-Image-Loader)**

    compile 'com.albedinsky.android:image-loader-universal:0.6.1@aar'

_depends on:_
[`com.nostra13.universalimageloader:universal-image-loader:1.9.5`](https://github.com/nostra13/Android-Universal-Image-Loader)

