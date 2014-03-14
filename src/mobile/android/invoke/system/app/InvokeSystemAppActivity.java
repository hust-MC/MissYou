package mobile.android.invoke.system.app;

import mobile.android.*;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.util.Log;
import android.view.View.*;
import android.view.animation.*;
import android.view.animation.Animation.AnimationListener;
import android.view.*;

public class InvokeSystemAppActivity extends Activity implements
		OnClickListener, AnimationListener
{
	Button sms;
	Button call;
	Animation scale_small;
	Animation scale_big;
	Move move = new Move();
	static boolean big_flag = true; // 标志是否完成放大动画
	static boolean sms_flag = false; // 标志是否按下短信键

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Log.d("2", "create");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		sms = (Button) findViewById(R.id.sms);
		call = (Button) findViewById(R.id.call);
		sms.setOnClickListener(this);
		call.setOnClickListener(this);

		scale_big = AnimationUtils.loadAnimation(this, R.anim.scale_big);
		scale_small = AnimationUtils.loadAnimation(this, R.anim.scale_small);
		scale_big.setAnimationListener(this);
		scale_small.setAnimationListener(this);
		// 装载属性动画资源
		AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(this,
				R.animator.anim);
		// 设置要控制的对象
		set.setTarget(move);
		// 开始动画
		set.start();
		// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
	}

	public void sms()
	{
		Intent sms = new Intent(Intent.ACTION_SENDTO,
				Uri.parse("sms:800912"));
		startActivity(sms);
		finish();
		System.exit(0);
	}

	public void call()
	{
		Intent callIntent = new Intent(Intent.ACTION_CALL,
				Uri.parse("tel:800912"));
		startActivity(callIntent);
		finish();
		System.exit(0);
	}

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
		case R.id.sms:
			sms.startAnimation(scale_big);
			sms_flag = true;
			break;
		case R.id.call:
			call.startAnimation(scale_big);
			sms_flag = false;
			break;
		}
	}

	public void onAnimationEnd(Animation anim)
	{
		if (big_flag)
		{
			if (sms_flag)
				sms.startAnimation(scale_small);

			else
				call.startAnimation(scale_small);

			big_flag = (big_flag == false);
		}
		else
		{
			if(sms_flag)
				sms();
			else
				call();
		}
	}

	@Override
	public void onAnimationStart(Animation anim)
	{

	}

	@Override
	public void onAnimationRepeat(Animation anim)
	{

	}

	class Move
	{
		private int x;

		public int getMCzY()
		{
			return x;
		}

		public void setMCzY(int x)
		{
			this.x = x;
			sms.getBackground().setAlpha(x);
			call.getBackground().setAlpha(x);
			// button.layout(x, button.getTop(), x + button.getMeasuredWidth(),
			// button.getBottom());
		}
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		Log.d("1", "destroy");
	}
}
