package com.gkbhitech.drishti.order;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gkbhitech.drishti.R;
import com.gkbhitech.drishti.model.Order;

public class OrderSummaryAdapter extends BaseAdapter{

	private Activity context;
	private Order[] orders;
	
	public OrderSummaryAdapter(Activity context, Order[] orders){
		this.context = context;
		this.orders = orders;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return orders.length;
	}

	@Override
	public Order getItem(int position) {
		// TODO Auto-generated method stub
		return orders[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public static class ViewHolder {
		public TextView txtOrderNo;
		public TextView txtOrderRefNo;
		public TextView txtDate;
		public TextView txtStatus;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder view;
		LayoutInflater inflator = ((Activity)context).getLayoutInflater();
		
		if (convertView == null) {
			view = new ViewHolder();
			convertView = inflator.inflate(R.layout.adapter_order_summary, null);
			view.txtOrderNo = (TextView) convertView.findViewById(R.id.txt_order_no);
			view.txtOrderRefNo = (TextView) convertView.findViewById(R.id.txt_order_ref_no);
			view.txtDate = (TextView) convertView.findViewById(R.id.txt_date);
			view.txtStatus = (TextView) convertView.findViewById(R.id.txt_status);

			convertView.setTag(view);
		} else {
			view = (ViewHolder) convertView.getTag();
		}
		
		Order order = orders[position];
		view.txtOrderNo.setText(order.getOrder_no());
		view.txtOrderRefNo.setText(order.getCust_refno());
		view.txtDate.setText(""+order.getOrder_date());
		view.txtStatus.setText(""+order.getStatus_code());
		
		return convertView;
	}

}
