package com.example.what2watch;

import android.os.Parcel;
import android.os.Parcelable;

public class Cinema implements Parcelable{
	String name = null;
	String longitude = null;
	String latitude= null;
	TimeTable[] table = null;
		public Cinema(String name, String longitude, String latitude) {
		super();
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
	}
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getLongitude() {
			return longitude;
		}
		public void setLongitude(String longitude) {
			this.longitude = longitude;
		}
		public String getLatitude() {
			return latitude;
		}
		public void setLatitude(String latitude) {
			this.latitude = latitude;
		}
		public TimeTable[] getTable() {
			return table;
		}
		public void setTable(TimeTable[] table) {
			this.table = table;
		}
		@Override
		public int describeContents()
		{
			return 0;
		}

		@Override
		public void writeToParcel(Parcel dest, int flags)
		{
			dest.writeString(name);
			dest.writeString(latitude);
			dest.writeString(longitude);
		}
		public static final Parcelable.Creator<Cinema> CREATOR = new Parcelable.Creator<Cinema>()
				{
				    @Override
				    public Cinema createFromParcel(Parcel source)
				    {
				        return new Cinema(source);
				    }

				    @Override
				    public Cinema[] newArray(int size)
				    {
					return new Cinema[size];
				    }
				};

		public Cinema(Parcel in) {
					this.name = in.readString();
					this.latitude = in.readString();
					this.longitude = in.readString();
				}
		
}
