package hn.news.app

import android.Manifest
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import dagger.hilt.android.AndroidEntryPoint
import hn.news.app.ui.home.TopBarHome
import hn.news.app.ui.mainapp.MainScreen
import hn.news.app.ui.theme.BakaBakaComposeAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {
        const val SHORTCUT_EXPLORER = "SHORTCUT_EXPLORER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Đọc intent để xem có phải mở từ Shortcut không
        val openExplorer = intent?.getBooleanExtra(SHORTCUT_EXPLORER, false) ?: false
        setContent {
            BakaBakaComposeAppTheme {
                MainScreen(openExplorer = openExplorer)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                100
            )
        }
        if (!Settings.canDrawOverlays(this)) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                "package:${this.packageName}".toUri()
            )
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            this.startActivity(intent)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            val shortcutManager = getSystemService(ShortcutManager::class.java)
            val intent = Intent(this, MainActivity::class.java).apply {
                action = Intent.ACTION_VIEW
                putExtra(SHORTCUT_EXPLORER, true)
            }

            val shortcut = ShortcutInfo.Builder(this, "favorite_shortcut")
                .setShortLabel(getString(R.string.explorer))
                .setLongLabel(getString(R.string.explorer))
                .setIcon(Icon.createWithResource(this, R.drawable.ic_launcher_background))
                .setIntent(intent)
                .build()

            shortcutManager.dynamicShortcuts = listOf(shortcut)
        }
    }

    // Nếu app đã chạy, shortcut sẽ gọi lại onNewIntent
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        // Bạn cần cập nhật lại tab (dùng ViewModel hoặc MutableStateFlow để truyền vào Compose)
        //val openExplorer = intent.getBooleanExtra(SHORTCUT_EXPLORER, false)
        // TODO: Gọi tới ViewModel hoặc gửi event để chuyển tab
    }
}

@Preview(showBackground = true, heightDp = 800, widthDp = 400)
@Composable
fun NewsAppPreview() {
    BakaBakaComposeAppTheme {
        MainScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    BakaBakaComposeAppTheme {
        TopBarHome()
    }
}