package org.apache.cordova.wavemark;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.squareup.picasso.Picasso;
import com.yourwellness.ngage.R;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class WavemarkAdapter extends BaseAdapter{

	List<ResponseModal> response = Collections.emptyList();
	private final  Context context;
	private Date CurrentDate;
	private Date StartDate;
	private Date EnDate;

	public WavemarkAdapter(Context context){
		this.context = context;
	}

	public void UpdateResponse(List<ResponseModal> response){
		this.response = response;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return response.size();
	}

	@Override
	public ResponseModal getItem(int position) {
		// TODO Auto-generated method stub
		return response.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		ImageView ivImage;
		TextView tvTitle;
		TextView tvDesc;

		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.wavemark_listrow, parent,false);
			ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
			tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
			tvDesc = (TextView) convertView.findViewById(R.id.tvDesc);
			convertView.setTag(new ViewHolder(ivImage,tvTitle,tvDesc));
		}else{
			ViewHolder holder = (ViewHolder)convertView.getTag();
			ivImage = holder.ivImage;
			tvTitle = holder.tvTitle;
			tvDesc = holder.tvDesc;
		}

		final ResponseModal response = getItem(position);
		
		
		//		ivImage.setImageResource(resId)
		
		SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd");
		String strtdate = response.getStart_Date();
		String enddate = response.getEnd_Date();

		Date now = new Date();
		Date alsoNow = Calendar.getInstance().getTime();
		String current_date = new SimpleDateFormat("yyyy-MM-dd").format(now);
		/*String Startdate= new SimpleDateFormat("yyyy-MM-dd").format(strtdate);
		String Enddate= new SimpleDateFormat("yyyy-MM-dd").format(enddate);*/
		try {
			CurrentDate = formatter.parse(current_date);
			StartDate = formatter.parse(strtdate);
			EnDate = formatter.parse(enddate);
//			boolean iw = StartDate.compareTo(CurrentDate)<0;
//			boolean ie= CurrentDate.compareTo(EnDate)<0;
//			boolean iw2 = StartDate.compareTo(CurrentDate)<0;

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if( StartDate != null && CurrentDate != null && EnDate != null){
			if((StartDate.compareTo(CurrentDate)<0)&&(CurrentDate.compareTo(EnDate)<0)){
			Picasso.with(context).load(response.getImageUrl()).into(ivImage);
			tvTitle.setText(response.getTitle());
			tvDesc.setText(response.getDescription());
			tvDesc.setVisibility(View.VISIBLE);
		}else			
		{
			Picasso.with(context).load(R.drawable.man).into(ivImage);
			tvTitle.setText(response.getExpiry_message());
			tvDesc.setVisibility(View.GONE);
		}
		}
		convertView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if( StartDate == null || CurrentDate == null || EnDate == null)
					return;
				
				
				String url = response.getUrl();
				if(url!=null){
					//if(StartDate.compareTo(CurrentDate) && CurrentDate.compareTo(date))
					boolean iw = StartDate.compareTo(CurrentDate)<0;
					boolean ie= CurrentDate.compareTo(EnDate)<0;
					if((StartDate.compareTo(CurrentDate)<0)&&(CurrentDate.compareTo(EnDate)<0)){
						Intent i = new Intent(Intent.ACTION_VIEW);
						i.setData(Uri.parse(url));
						context.startActivity(i);
					}
				}else
				{

				}

			}

		});

		return convertView;
	}

	public static class ViewHolder{

		public final ImageView ivImage;
		public final TextView tvTitle;
		public final TextView tvDesc;

		public ViewHolder(ImageView ivImage,TextView tvTitle,TextView tvDesc){
			this.ivImage = ivImage;
			this.tvDesc = tvDesc;
			this.tvTitle = tvTitle;			
		}

	}

}
