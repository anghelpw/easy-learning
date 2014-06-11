package ro.ase.ism;

import ro.ase.ism.view.ViewContentEasy;
import ro.ase.ism.view.ViewContentHard;
import ro.ase.ism.view.ViewContentMedium;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridLayout;
import android.widget.ImageButton;

public class CustomPagerAdapter extends PagerAdapter {

	public Object instantiateItem(View collection, int position) {

		View v = new View(collection.getContext());

		LayoutInflater inflater = (LayoutInflater) collection.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		switch (position) {
		case 0: {
			v = inflater.inflate(R.layout.view_easy, null, false);
			GridLayout gl = (GridLayout) v.findViewById(R.id.layoutViewEasy);
			ImageButton lesson1 = (ImageButton) gl
					.findViewById(R.id.btnViewEasy);
			final Context context = collection.getContext();
			lesson1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					context.startActivity(new Intent(context,
							ViewContentEasy.class));
				}
			});
			break;
		}
		case 1: {
			v = inflater.inflate(R.layout.view_medium, null, false);
			GridLayout gl = (GridLayout) v.findViewById(R.id.layoutViewMedium);
			ImageButton lesson1 = (ImageButton) gl
					.findViewById(R.id.btnViewMedium);
			final Context context = collection.getContext();
			lesson1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					context.startActivity(new Intent(context,
							ViewContentMedium.class));
				}
			});
			break;
		}
		case 2: {
			v = inflater.inflate(R.layout.view_hard, null, false);
			GridLayout gl = (GridLayout) v.findViewById(R.id.layoutViewHard);
			ImageButton lesson1 = (ImageButton) gl
					.findViewById(R.id.btnViewHard);
			final Context context = collection.getContext();
			lesson1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					context.startActivity(new Intent(context,
							ViewContentHard.class));
				}
			});
			break;
		}
		}
		((ViewPager) collection).addView(v, 0);
		return v;
	}

	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewPager) arg0).removeView((View) arg2);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == ((View) arg1);
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public int getCount() {
		return 3;
	}
}