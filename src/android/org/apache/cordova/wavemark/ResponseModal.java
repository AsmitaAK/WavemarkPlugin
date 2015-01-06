package org.apache.cordova.wavemark;

import android.os.Parcel;
import android.os.Parcelable;

public class ResponseModal implements Parcelable{

	long id;
	String title;
	String description;
	String url;
	String imageUrl;
	int errorCode;
	String status;
	String metadata;
	String imageId;
	
	//Added by Lakshmi
	String project_id;
	String start_Date;
	String end_Date;
	String start_time;
	String end_time;
	String expiry_message;


	public ResponseModal() {
		// TODO Auto-generated constructor stub
	}
	
	public String getExpiry_message() {
		return expiry_message;
	}

	public void setExpiry_message(String expiry_message) {
		this.expiry_message = expiry_message;
	}

	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMetadata() {
		return metadata;
	}
	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void setErrorCode(int errorCode){
		this.errorCode = errorCode;
	}

	public int getErrorCode(){
		return this.errorCode;
	}
	
	
	public String getProject_id() {
		return project_id;
	}

	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}

	public String getStart_Date() {
		return start_Date;
	}

	public void setStart_Date(String start_Date) {
		this.start_Date = start_Date;
	}

	public String getEnd_Date() {
		return end_Date;
	}

	public void setEnd_Date(String end_Date) {
		this.end_Date = end_Date;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		// TODO Auto-generated method stub
		//		long id;
		//		String title;
		//		String description;
		//		String url;
		//		String imageUrl;
		//		int errorCode;
		//		String status;
		//		String metadata;
		//		String imageId;

		parcel.writeLong(this.id);
		parcel.writeString(this.title);
		parcel.writeString(this.description);
		parcel.writeString(this.url);
		parcel.writeString(this.imageUrl);
		parcel.writeInt(this.errorCode);
		parcel.writeString(this.status);
		parcel.writeString(this.metadata);
		parcel.writeString(this.imageId);

	}

	public ResponseModal(Parcel parcel) {
		// TODO Auto-generated constructor stub
		this.id = parcel.readLong();
		this.title = parcel.readString();
		this.description = parcel.readString();
		this.url = parcel.readString();
		this.imageUrl = parcel.readString();
		this.errorCode = parcel.readInt();
		this.status = parcel.readString();
		this.metadata = parcel.readString();
		this.imageId = parcel.readString();
	}

	public static final Parcelable.Creator<ResponseModal> CREATOR = new Creator<ResponseModal>(){

		@Override
		public ResponseModal createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new ResponseModal(source);
		}

		@Override
		public ResponseModal[] newArray(int size) {
			// TODO Auto-generated method stub
			return new ResponseModal[size];
		}

	};

}
