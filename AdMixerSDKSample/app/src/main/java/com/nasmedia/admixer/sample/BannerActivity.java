package com.nasmedia.admixer.sample;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nasmedia.admixerssp.ads.AdEvent;
import com.nasmedia.admixerssp.ads.AdInfo;
import com.nasmedia.admixerssp.ads.AdListener;
import com.nasmedia.admixerssp.ads.AdView;


public class BannerActivity extends AppCompatActivity {

    private RelativeLayout container;
    private AdView banner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        container = findViewById(R.id.container_banner);

        AdInfo adInfo = new AdInfo.Builder(Application.ADUNIT_ID_BANNER) // AdMixer 플랫폼에서 발급받은 배너 ADUNIT_ID;
                .setIsUseMediation(true) // 미디에이션 사용 여부 (true - 기본값, false - 미사용)
                .build();

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        banner = new AdView(this);
        banner.setLayoutParams(params);
        // 이 때 설정하신 Banner 의 부모 activity 는 원활한 광고 제공을 위해 hardwareAccelerated 가 true 설정되오니 참고 부탁드립니다.
        banner.setAdInfo(adInfo);
        banner.setAlwaysShowAdView(false);   // 광고 로딩 전에도 영역을 차지할 것인지 설정(false – 기본값)
        banner.setAdViewListener(new AdListener() {
            @Override
            public void onReceivedAd(String s, Object o) {
                // 광고 수신 성공
                Toast.makeText(BannerActivity.this, "onReceivedAd", Toast.LENGTH_SHORT).show();
                // [2번 방법] 광고를 받은 뒤, 원하는 시점에 노출
                // if (banner.hasAd) {  // 광고를 성공적으로 받았는지 판단
                //    container.removeView(banner);
                //    container.addView(banner); // 레이아웃에 배너 광고 뷰를 추가
                //    banner.showAd(); // showAd() 를 이용하여, 원하는 시점에 노출 가능
                // }
            }

            @Override
            public void onFailedToReceiveAd(Object o, String s, int i, String s1) {
                // 광고 수신 실패
                Toast.makeText(BannerActivity.this, ""+s, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onEventAd(Object o, AdEvent adEvent) {
                // 기타 이벤트
                Toast.makeText(BannerActivity.this, adEvent.toString(), Toast.LENGTH_SHORT).show();
                switch (adEvent) {
                    case CLICK:
                    case DISPLAYED:
                        break;
                }

            }

        });

        // [1번 방법] 광고 요청 후 받은 즉시 노출
        container.addView(banner);

        // [2번 방법] 광고 요청 후 광고를 받은 뒤, 원하는 시점에 노출
        // banner.loadAd(); // 광고를 미리 로드한다
    }

    // 생명주기에 따라 아래 설정이 반드시 필요합니다.
    @Override
    protected void onResume() {
        if (banner != null) {
            banner.onResume();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (banner != null) {
            banner.onPause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (banner != null) {
            banner.onDestroy();
        }
        super.onDestroy();
    }
}
