package cs442.team6.memoirs;

public class Event {
	String eventDate, eventTitle, eventDescription;
	//int eventImg;

	public Event(String eventDate, String eventTitle, String eventDescription) {
		super();
		//this.eventImg = eventImg;
		this.eventDate = eventDate;
		this.eventTitle = eventTitle;
		this.eventDescription = eventDescription;
	}
	
	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}
	
	public String getEventTitle() {
		return eventTitle;
	}

	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}

	public String getEventDescription() {
		return eventDescription;
	}

	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}

	/*public int getEventImg() {
		return eventImg;
	}

	public void setEventImg(int eventImg) {
		this.eventImg = eventImg;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.eventTitle+"\n\nDescription = "+eventDescription+"\n";
	}*/

}