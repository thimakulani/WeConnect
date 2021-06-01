package com.st.weconnect.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.st.weconnect.R;


public class SplashScreen extends AppCompatActivity {

    private ShimmerFrameLayout mShimmerViewContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_layout);

        TextView appName = (TextView)findViewById(R.id.txtAppName);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        final TextView developed = (TextView)findViewById(R.id.txtDevelopedBy);
        final ImageView ImageAppLogo = (ImageView)findViewById(R.id.ImageAppLogo);
        developed.setVisibility(View.GONE);
        ImageAppLogo.setVisibility(View.GONE);


        mShimmerViewContainer.startShimmerAnimation();


        Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.float_up);
        final Animation slideUp2 = AnimationUtils.loadAnimation(this, R.anim.float_up);
        final Animation slideUp3 = AnimationUtils.loadAnimation(this, R.anim.float_up);
        appName.startAnimation(slideUp);
        slideUp.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                developed.setVisibility(View.VISIBLE);
                ImageAppLogo.setVisibility(View.VISIBLE);
                developed.startAnimation(slideUp3);
                ImageAppLogo.startAnimation(slideUp2);
                slideUp3.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {




                        new Handler().postDelayed(() -> {

                          Intent  intent = new Intent(getApplicationContext(), LoginSignupActivity.class);
                          startActivity(intent);
                          overridePendingTransition(R.anim.slide_up, R.anim.slide_right);
                          finish();
                            mShimmerViewContainer.stopShimmerAnimation();
                        }, 3000);

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();


    }
}