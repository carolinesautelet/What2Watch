package com.example.what2watch;

public class Cinema {
		String name = null;
		String longitude = null;
		String latitude= null;
		TimeTable[] table = null;
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
		
}
