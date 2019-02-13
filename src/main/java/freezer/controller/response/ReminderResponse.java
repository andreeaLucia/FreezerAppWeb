package freezer.controller.response;

import java.util.ArrayList;
import java.util.List;

import freezer.model.Freezers;
import freezer.model.Reminder;

public class ReminderResponse extends BaseResponse{
	
    List<Reminder> reminders = new ArrayList<>();
	
	public ReminderResponse(){	
	}
	
	public ReminderResponse(List<Reminder> reminders){
		this.reminders = reminders;
	}

	public List<Reminder> getReminders() {
		return reminders;
	}

	public void setReminders(List<Reminder> list) {
		this.reminders = list;
	}
	
    
}
