package com.nasmedia.admixer.sample;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nasmedia.admixerssp.ads.AdEvent;
import com.nasmedia.admixerssp.ads.AdInfo;
import com.nasmedia.admixerssp.ads.AdListener;
import com.nasmedia.admixerssp.ads.NativeAdView;
import com.nasmedia.admixerssp.common.AdMixer;
import com.nasmedia.admixerssp.common.nativeads.NativeAdViewBinder;

import java.util.HashMap;
import java.util.Map;

public class NativeActivity extends AppCompatActivity {

    private NativeAdView nativeAdView;
    private LinearLayout container;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native);
        container = findViewById(R.id.container_native);

        int nativeLayout = R.layout.item_320x480;

        // Native 광고를 노출할, Layout 을 정의한 xml 의 ID 정보를 SDK 에 전달합니다.
        // (icon, title, advertiser, description, main(image,video View), cta ...)
        // 아래 binding 해야 할 asset 중 title or iconImage or mainView 중 1개는 반드시 설정해야 하는 필수값 입니다.
        NativeAdViewBinder viewBinder = new NativeAdViewBinder.Builder(nativeLayout)
                .setIconImageId(R.id.iv_icon) // 아이콘 ID
                .setTitleId(R.id.tv_title)    // 제목(타이틀) ID
                .setAdvertiserId(R.id.tv_adv) // 광고주 ID
                .setDescriptionId(R.id.tv_desc) // 설명 ID
                .setMainViewId(R.id.iv_main)    // 메인 이미지 또는 동영상 ID
                .setCtaId(R.id.btn_cta) // Call to Action 버튼 (ex 더보기) ID
                .build();


        nativeAdView = new NativeAdView(getApplicationContext());

        nativeAdView.setViewBinder(viewBinder); // 네이티브 광고 레이아웃 필수 전달
        nativeAdView.setAdViewListener(new AdListener() {
            @Override
            public void onReceivedAd(String s, Object o) {
                // 광고 수신 성공
                Toast.makeText(NativeActivity.this, "onReceivedAd" + s, Toast.LENGTH_SHORT).show();
                container.removeAllViews();
                container.addView(nativeAdView); // 레이아웃에 네이티브 광고를 추가
                container.setVisibility(View.VISIBLE);

            }

            @Override
            public void onFailedToReceiveAd(Object o, String s, int i, String s1) {
                // 광고 수신 실패
                Toast.makeText(NativeActivity.this, "onFailedToReceiveAd :" + s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onEventAd(Object o, AdEvent adEvent) {
                // 기타 이벤트
                Toast.makeText(NativeActivity.this, "onEventAd :" + adEvent.toString(), Toast.LENGTH_SHORT).show();
                switch (adEvent) {
                    case CLICK: // 클릭 시
                    case DISPLAYED: // 노출 시
                        break;
                }
            }

        });
        Map<String, Integer> adViewIds = new HashMap<>();
        adViewIds.put("nativeLayout", R.layout.item_320x480);
        adViewIds.put("iv_icon", R.id.iv_icon);
        adViewIds.put("tv_title", R.id.tv_title);
        adViewIds.put("tv_adv", R.id.tv_adv);
        adViewIds.put("tv_desc", R.id.tv_desc);
        adViewIds.put("iv_main", R.id.iv_main);
        adViewIds.put("btn_cta", R.id.btn_cta);
        adViewIds.put(AdMixer.ADAPTER_MOBWITH_IMAGE,R.id.imageView_ad);

        AdInfo.Builder builder = new AdInfo.Builder(Application.ADUNIT_ID_NATIVE) // AdMixer 플랫폼에서 발급받은 배너 ADUNIT_ID
                .isRetry(false);


        builder.setViewIds(AdMixer.ADAPTER_ADMANAGER, adViewIds);
        builder.setViewIds(AdMixer.ADAPTER_MOBWITH, adViewIds);
        builder.setViewIds(AdMixer.ADAPTER_ADFIT, adViewIds);
        builder.setViewIds(AdMixer.ADAPTER_PANGLE, adViewIds);

        builder.setIsUseMediation(true);// 미디에이션 사용 여부 (true - 기본값, false - 미사용)
        AdInfo adInfo = builder.build();
        // 이 때 설정하신 Native 의 부모 activity 는 원활한 광고 제공을 위해 hardwareAccelerated 가 true 설정되오니 참고 부탁드립니다.
        nativeAdView.setAdInfo(adInfo);
        // 네이티브 광고 미리 로드
        nativeAdView.loadNativeAd();
    }

    // 생명주기에 따라 아래 설정이 반드시 필요합니다.
    @Override
    protected void onResume() {
        if (nativeAdView != null) {
            nativeAdView.onResume();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (nativeAdView != null) {
            nativeAdView.onPause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (nativeAdView != null) {
            nativeAdView.onDestroy();
            nativeAdView = null;
        }
        super.onDestroy();
    }
}
