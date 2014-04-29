package com.example.what2watch;

public class Channel {
		String name = null;
		TimeTable[] table = null;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public TimeTable[] getTable() {
			return table;
		}
		public void setTable(TimeTable[] table) {
			this.table = table;
		}
}
