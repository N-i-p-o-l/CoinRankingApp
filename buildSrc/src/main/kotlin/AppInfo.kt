object AppInfo {
    const val appName = "CoinRanking"
    const val applicationId = "com.android.coinranking"

    private const val versionMajor = 1
    private const val versionMinor = 0
    private const val versionPatch = 0

    val versionCode: Int = versionMajor * 10000 + versionMinor * 100 + versionPatch
    val versionName: String = "${AppInfo.versionMajor}.${AppInfo.versionMinor}.${AppInfo.versionPatch}"
}