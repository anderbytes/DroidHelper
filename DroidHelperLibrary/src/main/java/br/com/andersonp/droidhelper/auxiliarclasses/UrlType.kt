package br.com.andersonp.droidhelper.auxiliarclasses

/**
 * Url type and it's associated common package name in Android
 *
 * @property androidApp name of the package
 */
enum class UrlType(val androidApp: String) {
    INSTAGRAM("com.instagram.android"),
    LINKEDIN("com.linkedin.android"),
    SPOTIFY("com.spotify.music"),
    TED("com.ted.android"),
    WHATSAPP("com.whatsapp"),
    FACEBOOK("com.facebook.katana"),
    YOUTUBE("com.google.android.youtube"),
    GOOGLEMAPS("com.google.android.gms.maps"),
}