package freezer.controller;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import freezer.controller.request.CategoryRequest;
import freezer.controller.request.DeleteFrozenItemsRequest;
import freezer.controller.request.FreezersRequest;
import freezer.controller.request.ItemsRequest;
import freezer.controller.request.LoginRequest;
import freezer.controller.request.LogoutRequest;
import freezer.controller.request.ReminderRequest;
import freezer.controller.request.UserRequest;
import freezer.controller.response.BaseResponse;
import freezer.controller.response.CategoryResponse;
import freezer.controller.response.FreezersResponse;
import freezer.controller.response.ItemsResponse;
import freezer.controller.response.LoginResponse;
import freezer.controller.response.ReminderResponse;
import freezer.controller.response.ResponseStatus;
import freezer.controller.response.UserResponse;
import freezer.model.Authentication;
import freezer.model.Reminder;
import freezer.repository.ExceptionEmptyList;
import freezer.service.FreezerService;

@RestController
public class FreezerController extends HttpServlet{
	
	@Autowired
	private FreezerService freezerService;

	@RequestMapping(path = "/addItem", method = RequestMethod.POST)
	public ItemsResponse addItem(@RequestBody ItemsRequest addItemRequest){
	
		return freezerService.addItem(addItemRequest.getNumberBags(),
				addItemRequest.getBagWeight(),
				addItemRequest.getFk_id_freezer(),
				addItemRequest.getFk_id_user(),
				addItemRequest.getFk_id_category()); 
	}
	@RequestMapping(path = "/addReminder", method = RequestMethod.POST)
	public ReminderResponse addReminder(@RequestBody ReminderRequest addReminderRequest){
	
		return freezerService.addReminder(addReminderRequest.getMessage(),
				addReminderRequest.getDate(),
				addReminderRequest.getForUser());
	}
	
	@RequestMapping(path = "/deleteReminder", method = RequestMethod.POST)
	public ReminderResponse deleteReminder(@RequestBody ReminderRequest deleteReminderRequest){
		
		ReminderResponse reminderResponse = new ReminderResponse();

		boolean hasBeenDeleted = freezerService.deleteReminder(deleteReminderRequest.getIdReminder());	
		if(hasBeenDeleted) {
			reminderResponse.setStatus(ResponseStatus.SUCCESS);
			reminderResponse.setMessage("Reminder has been deleted!");
			return reminderResponse;
		}
		else {
			reminderResponse.setStatus(ResponseStatus.FAILURE);
			reminderResponse.setMessage("Unable to delete reminder");
		return reminderResponse;
		}
}
	
	@RequestMapping(path = "/updateReminder", method = RequestMethod.POST)
    public ReminderResponse updateReminder(@RequestBody ReminderRequest reminderRequest ){

    	return freezerService.updateReminder(reminderRequest.getMessage(), reminderRequest.getDate(), reminderRequest.getForUser(), reminderRequest.getIdReminder());
    } 
	
	@RequestMapping(path = "/getAllRemindersForAuser", method = RequestMethod.POST)
	public ReminderResponse getAllRemindersForAuser(@RequestBody ReminderRequest reminderRequest){
		return freezerService.getAllRemindersForAuser(reminderRequest.getForUser());
	}
	
	@RequestMapping(path = "/getAllReminders", method = RequestMethod.GET)
	public ReminderResponse getAllReminders(){
		return freezerService.getAllReminders();
	}
	@RequestMapping(path = "/loginUser", method = RequestMethod.POST)
	public LoginResponse loginUser(@RequestBody LoginRequest loginUser){
		return freezerService.loginUser(loginUser.getUserName(), loginUser.getPassword());
				
	}
	
	@RequestMapping(path = "/logOutFromAllDevices", method = RequestMethod.POST)
	public BaseResponse logOutFromAllDevices(@RequestBody LogoutRequest logoutUser){
		return freezerService.logOutFromAllDevices(logoutUser.getIdUsers());
				
	}
	
	@RequestMapping(path = "/logOut", method = RequestMethod.POST)
	public BaseResponse logOut(@RequestBody LogoutRequest logoutUser){
		return freezerService.logOut(logoutUser.getToken());
				
	}
	
	
	@RequestMapping(path = "/deleteItem", method = RequestMethod.POST)
	public ItemsResponse deleteFrozenItem(@RequestBody DeleteFrozenItemsRequest deleteFrozenItemsRequest){
			
		return freezerService.deleteItem(deleteFrozenItemsRequest.getIdItemName());
	
	}
	
    @RequestMapping(path = "/getAllFreezers", method = RequestMethod.GET)
	public FreezersResponse getAllFreezers() throws IOException {
		return freezerService.getAllFreezers();
	}
    
    @RequestMapping(path = "/getFreezerCapacity", method = RequestMethod.GET)
	public FreezersResponse getFreezerCapacity(@RequestParam (value="c") int capacity) throws IOException {
		return freezerService.getFreezerCapacity(capacity);
	}
	
    @RequestMapping(path = "/addUser", method = RequestMethod.POST)
	public UserResponse addUser(@RequestBody UserRequest userRequest){
	
		return freezerService.addUser(userRequest.getUserName(), userRequest.getPassword(), userRequest.getIsAdmin());
	}
    
    @RequestMapping(path = "/deleteUser", method = RequestMethod.POST)
   	public UserResponse deleteUser(@RequestBody UserRequest deleteUserRequest){
   			
   		return freezerService.deleteUser(deleteUserRequest.getIdUsers());
   	
   	}
    //@RequestBody smart object serialization deserialization
    @RequestMapping(path = "/updateUser", method = RequestMethod.POST)
    public UserResponse updateUser(@RequestBody UserRequest userRequest ){

    	return freezerService.updateUser(userRequest.getUserName(), userRequest.getPasswordHash(), userRequest.getIdUsers());
    }      
    @RequestMapping(path = "/addFreezer", method = RequestMethod.POST)
	public FreezersResponse addFreezer(@RequestBody FreezersRequest freezerRequest){
	
		return freezerService.addFreezer(freezerRequest.getFreezerName(),freezerRequest.getCapacity());
	}
    
    @RequestMapping(path = "/addCategory", method = RequestMethod.POST)
	public CategoryResponse addCategory(@RequestBody @Valid CategoryRequest categoryRequest){
	
		return freezerService.addCategory(categoryRequest.getCategoryName());
	}
   
    @RequestMapping(path = "/updateCategory", method = RequestMethod.POST)
	public CategoryResponse updateCategory(@RequestBody @Valid CategoryRequest categoryRequest ){
			
		return freezerService.updateCategory(categoryRequest.getIdCategory(),categoryRequest.getCategoryName());
	}
    
    @RequestMapping(path = "/updateFreezer", method = RequestMethod.POST)
	public FreezersResponse updateFreezer(@RequestBody @Valid FreezersRequest freezerRequest ){
			
		return freezerService.updateFreezer(freezerRequest.getIdFreezer(),freezerRequest.getFreezerName(), freezerRequest.getCapacity());
	}
    
    @RequestMapping(path = "/deleteFreezer", method = RequestMethod.POST)
	public FreezersResponse deleteFreezer(@RequestBody FreezersRequest deleteFreezerRequest){
			
		return freezerService.deleteFreezer(deleteFreezerRequest.getIdFreezer());
	
	}
	@RequestMapping(path = "/updateWeightBagAfterIdItem", method = RequestMethod.POST)
	public ItemsResponse updateWeightBagAfterIdItem(@RequestBody ItemsRequest updateWeightBagAfterIdItem){
	
			return freezerService.updateWeightBagAfterIdItem(updateWeightBagAfterIdItem.getIdItem(),
					updateWeightBagAfterIdItem.getBagWeight());
				
	}

}
