package com.nasmedia.admixerkotlin

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.nasmedia.admixerssp.ads.AdEvent
import com.nasmedia.admixerssp.ads.AdInfo
import com.nasmedia.admixerssp.ads.AdListener
import com.nasmedia.admixerssp.ads.AdView

class BannerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScriptPage()
        }
    }
}

@Composable
fun ScriptPage() {
    val context = LocalContext.current
    val scrollState = rememberLazyListState()

    Box(modifier = Modifier.fillMaxSize()) {

        // 스크롤 콘텐츠
        LazyColumn(
            state = scrollState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = 80.dp)
        ) {
            items(50) { index ->
                Text(
                    text = "스크립트 아이템 $index",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }

        // 광고 위치 계산
        val yOffset by remember {
            derivedStateOf {
                if (scrollState.firstVisibleItemIndex == 0) {
                    -scrollState.firstVisibleItemScrollOffset
                } else {
                    -80
                }
            }
        }

        // 광고 View (AndroidView에서 직접 생성)
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = yOffset.dp)
                .background(Color.LightGray)
                .padding(horizontal = 20.dp, vertical = 8.dp),
            factory = {
                it.createAdView("100268")
            }
        )
    }
}

// AdView 초기화 함수
fun Context.createAdView(
    adUnitId: String,
    onDisplayed: () -> Unit = {},
    onClick: () -> Unit = {}
): AdView = AdView(this).apply {
    layoutParams = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )

    setAdInfo(
        AdInfo.Builder(adUnitId)
            .setIsUseMediation(true)
            .build()
    )

    setAlwaysShowAdView(true)

    setAdViewListener(object : AdListener {
        override fun onReceivedAd(p0: String?, p1: Any?) {
            Log.i("ScriptPage", "onReceivedAd")
        }

        override fun onFailedToReceiveAd(p0: Any?, p1: String?, p2: Int, p3: String?) {
            Log.e("ScriptPage", "onFailedToReceiveAd: code=$p2, message=$p3")
            // SDK 내부에서 null pointer 발생할 수 있는 부분 → SDK가 null 체크 강화해야 함
        }

        override fun onEventAd(p0: Any?, p1: AdEvent?) {
            when (p1) {
                AdEvent.CLICK -> {
                    Log.i("ScriptPage", "Ad Clicked")
                    onClick()
                }

                AdEvent.DISPLAYED -> {
                    Log.i("ScriptPage", "Ad Displayed")
                    onDisplayed()
                }

                else -> {}
            }
        }
    })
}
