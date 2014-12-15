package com.gkbhitech.drishti;

import android.app.Activity;
import android.graphics.drawable.StateListDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class HomeAdapter extends BaseAdapter{

	private Activity context;
	private Integer[] homeIcons = {
			R.drawable.icon_gkb,
			R.drawable.icon_inventory,
			R.drawable.icon_order,
			R.drawable.icon_account,
			R.drawable.icon_report,
			R.drawable.icon_geotagging,
			R.drawable.icon_gdo,
			R.drawable.icon_misc,
			R.drawable.icon_setting
	};
	private Integer[] homeIconsClicked = {
			R.drawable.icon_gkb_click,
			R.drawable.icon_inventory_click,
			R.drawable.icon_order_click,
			R.drawable.icon_accounts_click,
			R.drawable.icon_report_click,
			R.drawable.icon_geotagging_click,
			R.drawable.icon_gdo_click,
			R.drawable.icon_misc_click,
			R.drawable.icon_setting_click
	};
	
	public HomeAdapter(Activity context){
		this.context = context;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return homeIcons.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public static class ViewHolder {
		public ImageView imgHomeIcon;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder view;
		LayoutInflater inflator = context.getLayoutInflater();
		
		if (convertView == null) {
			view = new ViewHolder();
			convertView = inflator.inflate(R.layout.adapter_home, null);
			
			view.imgHomeIcon = (ImageView) convertView.findViewById(R.id.img_home_icons);

			convertView.setTag(view);
		} else {
			view = (ViewHolder) convertView.getTag();
		}
		//view.imgHomeIcon.setImageResource(homeIcons[position]);
		StateListDrawable states = new StateListDrawable();
		states.addState(new int[] {android.R.attr.state_pressed},
				context.getResources().getDrawable(homeIconsClicked[position]));
		states.addState(new int[] { },
				context.getResources().getDrawable(homeIcons[position]));

		view.imgHomeIcon.setBackgroundDrawable(states);
		
		return convertView;
	}
}
