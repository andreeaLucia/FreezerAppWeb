package freezer.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import freezer.controller.response.AdminResponse;
import freezer.controller.response.BaseResponse;
import freezer.controller.response.CategoryResponse;
import freezer.controller.response.FreezersResponse;
import freezer.controller.response.ItemsResponse;
import freezer.controller.response.LoginResponse;
import freezer.controller.response.ReminderResponse;
import freezer.controller.response.ResponseStatus;
import freezer.controller.response.UserResponse;
import freezer.model.Authentication;
import freezer.model.ExceptionName;
import freezer.model.GetItemsAddedByUser;
import freezer.model.Users;
import freezer.repository.ExceptionEmptyList;
import freezer.repository.FreezerRepository;

@Component
public class FreezerService {

	static final String DB_URL = "jdbc:mysql://localhost/freezerdb";
	static final String USER = "root";
	static final String PASS = "";

	@Autowired
	private FreezerRepository freezerRepository;
	
	public UserResponse addUser(String userName, String password, boolean isAdmin){
		System.out.println("before");
		String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt()); 
		System.out.println("after " + passwordHash);
		boolean wasAdded;
		UserResponse userResponse = new UserResponse();

		wasAdded = freezerRepository.addUser(userName, passwordHash, isAdmin);
		if(wasAdded) {
			userResponse.setStatus(ResponseStatus.SUCCESS);

		}
		else {
			userResponse.setStatus(ResponseStatus.FAILURE);
			userResponse.setMessage("Could not add");
		}


		return userResponse;
	}

	public LoginResponse loginUser(String userName, String password){
		LoginResponse loginResponse = new LoginResponse();
		Users user = freezerRepository.getUserByUsername(userName);
		if (user.getUserName() == null) {
			loginResponse.setStatus(ResponseStatus.FAILURE);
			loginResponse.setMessage("Username or password invalid!");
			return loginResponse;
		}

		if(BCrypt.checkpw(password, user.getPasswordHash())){
			String token = generateToken();
			int idUser = user.getIdUsers();
			boolean response = freezerRepository.saveToken(idUser, token);
			if(response) {
				loginResponse.setToken(token);
				loginResponse.setStatus(ResponseStatus.SUCCESS);
			} else {
				loginResponse.setStatus(ResponseStatus.FAILURE);
				loginResponse.setMessage("Token has not been introduced to db");
			}
		} else {
			loginResponse.setStatus(ResponseStatus.FAILURE);
			loginResponse.setMessage("Username or password inavalid!");
		}

		return loginResponse;	
	}
	public UserResponse deleteUser(int idUsers) {

		UserResponse userResponse = new UserResponse();

		boolean wasDeleted = freezerRepository.deleteUser(idUsers);
		if (wasDeleted) {
			userResponse.setStatus(ResponseStatus.SUCCESS);
		} else {
			userResponse.setStatus(ResponseStatus.FAILURE);
			userResponse.setMessage("Could not delete");
		}	
		return userResponse;
	}

	public boolean isAdmin(String token) {
		
		return freezerRepository.isAdmin(token);
		
}
	public AdminResponse getAllUsers()  {
		AdminResponse adminResponse = new AdminResponse();
		try {
			adminResponse.setUsersList(freezerRepository.getAllUsers());
			adminResponse.setStatus(ResponseStatus.SUCCESS);
		} catch (ExceptionName e) {
			e.printStackTrace();
			adminResponse.setStatus(ResponseStatus.FAILURE);
		}
		
		return adminResponse;
	}
	
	public UserResponse updateUser(String userName, String passwordHash, int idUsers) {
		UserResponse userResponse = new UserResponse();
		boolean wasUpdated = freezerRepository.updateUser(userName, passwordHash, idUsers);
		if(wasUpdated) {
			userResponse.setStatus(ResponseStatus.SUCCESS);

		}
		else {
			userResponse.setStatus(ResponseStatus.FAILURE);
		}
		return userResponse;
	}   
	public BaseResponse logOutFromAllDevices(int idUsers) {
		BaseResponse logoutResponse = new BaseResponse();
		boolean isLogOut = freezerRepository.logOutFromAllDevices(idUsers);
		if(isLogOut) {
			logoutResponse.setStatus(ResponseStatus.SUCCESS);
		}
		else {
			logoutResponse.setStatus(ResponseStatus.FAILURE);
		}
		return logoutResponse;
	}
	
	public BaseResponse logOut(String token) {
		BaseResponse logoutResponse = new BaseResponse();
		boolean isLogOut = freezerRepository.logOut(token);
		if(isLogOut) {
			logoutResponse.setStatus(ResponseStatus.SUCCESS);
			logoutResponse.setMessage("Go to login!");
		}
		else {
			logoutResponse.setStatus(ResponseStatus.FAILURE);
		}
		return logoutResponse;
	}
	private String generateToken() {
		int leftLimit = 97; // letter 'a'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 10;
	    Random random = new Random();
	    StringBuilder buffer = new StringBuilder(targetStringLength);
	    for (int i = 0; i < targetStringLength; i++) {
	        int randomLimitedInt = leftLimit + (int) 
	          (random.nextFloat() * (rightLimit - leftLimit + 1));
	        buffer.append((char) randomLimitedInt);
	    }
	    String token = buffer.toString();
	 
	 
	    return token;
	}
	public boolean verifyTokenJava(String token) {
		Authentication aut = freezerRepository.verifyTokenJava(token);
		if(aut.getToken() == null) {
			return false;
		}
		else{
			LocalDateTime validTime = LocalDateTime.now();
			LocalDateTime createdDate = aut.getCreatedDate().plusMinutes(3);
			if(createdDate.isAfter(validTime)) {
				return true;
			}
			else {
				freezerRepository.deleteToken(token);
				return false;
			}	
		}
	}

	public ReminderResponse addReminder(String message, Date date, int forUser) {
		ReminderResponse reminderResponse = new ReminderResponse();
		boolean wasAdded = freezerRepository.addReminder(message, date, forUser);
		if (wasAdded){
			reminderResponse.setStatus(ResponseStatus.SUCCESS);
		}
		else {
			reminderResponse.setStatus(ResponseStatus.FAILURE);
		}
		return reminderResponse;
	}
	
	
	
	public boolean deleteReminder(int idReminder) {
		return freezerRepository.deleteReminder(idReminder);
   }
	   

	public ReminderResponse updateReminder(String message, Date date, int forUser, int idReminder) {

		boolean wasUpdateed = freezerRepository.updateReminder(message, date, forUser, idReminder);
		ReminderResponse reminderResponse = new ReminderResponse();
		if(wasUpdateed) {
			reminderResponse.setStatus(ResponseStatus.SUCCESS);
		}
		else {
		    reminderResponse.setStatus(ResponseStatus.FAILURE);
		}
		return reminderResponse;
	}
	
	public ReminderResponse getAllRemindersForAuser(int forUser)  {
		ReminderResponse reminderResponse = new ReminderResponse();
		try {
			reminderResponse.setReminders(freezerRepository.getAllRemindersForAuser(forUser));
			reminderResponse.setStatus(ResponseStatus.SUCCESS);
		} catch (ExceptionName e) {
			reminderResponse.setStatus(ResponseStatus.FAILURE);
			e.getMessage();
		}

		return reminderResponse;
	}
	public ReminderResponse getAllReminders()  {
		ReminderResponse reminderResponse = new ReminderResponse();
		try {
			reminderResponse.setReminders(freezerRepository.getAllReminders());
			reminderResponse.setStatus(ResponseStatus.SUCCESS);
		} catch (ExceptionName e) {
			reminderResponse.setStatus(ResponseStatus.FAILURE);
			e.getMessage();
		}
		return reminderResponse;
	}
	   
	public Users getUserByUsername(String userName) { 
		return freezerRepository.getUserByUsername(userName);
	}
	
	public CategoryResponse addCategory(String categoryName){
		CategoryResponse categoryResponse = new CategoryResponse();
	    
		boolean wasAdded;
		try {
			wasAdded = freezerRepository.addCategory(categoryName);
			if (wasAdded) {
				categoryResponse.setStatus(ResponseStatus.SUCCESS);
			
			} else {
				categoryResponse.setStatus(ResponseStatus.FAILURE);
				categoryResponse.setMessage("Could not add");
			}
		} catch (ExceptionName e) {
			e.getMessage();
		}
		
		return categoryResponse;
	}
	
	public FreezersResponse getAllFreezers() {
		 FreezersResponse freezersResponse = new FreezersResponse();
		 freezersResponse.setFreezers(freezerRepository.getAllFreezers());
		 freezersResponse.setStatus(ResponseStatus.SUCCESS);
		return freezersResponse;
	}


	public FreezersResponse addFreezer(String freezerName, int capacity){
		FreezersResponse freezerResponse = new FreezersResponse();
		boolean wasAdded;
		try {
			wasAdded = freezerRepository.addFreezer(freezerName, capacity);
			if (wasAdded){
				freezerResponse.setStatus(ResponseStatus.SUCCESS);
			}
			else {
				freezerResponse.setStatus(ResponseStatus.FAILURE);
			}
			
		} catch (ExceptionName e) {
			e.getMessage();
			freezerResponse.setStatus(ResponseStatus.FAILURE);
		}
		return freezerResponse;
}
	public FreezersResponse updateFreezer(int idFreezer, String freezerName, int capacity) {
		
		boolean wasUpdateed = freezerRepository.updateFreezer(idFreezer, freezerName, capacity);
		FreezersResponse freezerResponse = new FreezersResponse();
		if(wasUpdateed) {
			freezerResponse.setStatus(ResponseStatus.SUCCESS);
		}
		else {
			freezerResponse.setStatus(ResponseStatus.FAILURE);
		}
		return freezerResponse;
	}
    public CategoryResponse updateCategory(int idCategory, String categoryName) {
    	CategoryResponse categoryResponse = new CategoryResponse();
		boolean wasUpdateed = freezerRepository.updateCategory(idCategory, categoryName);
		
		if(wasUpdateed) {
			categoryResponse.setStatus(ResponseStatus.SUCCESS);
		}
		else {
			categoryResponse.setStatus(ResponseStatus.FAILURE);
		}
		return categoryResponse;
	}
	public ItemsResponse addItem(int numberBags, int bagWeight, int fk_id_freezer, int fk_id_user, int fk_id_category){
		ItemsResponse itemsResponse = new ItemsResponse();
		boolean wasAdded = freezerRepository.addItem(numberBags, bagWeight, fk_id_freezer, fk_id_user, fk_id_category);
		if (wasAdded) {
			itemsResponse.setStatus(ResponseStatus.SUCCESS);
		}
		else {
			itemsResponse.setStatus(ResponseStatus.FAILURE);
		}
		return itemsResponse;
	}

	public FreezersResponse deleteFreezer(int idFreezer) {
		
		FreezersResponse freezerResponse = new FreezersResponse();
		
		boolean wasDeleted = freezerRepository.deleteFreezer(idFreezer);
		if (wasDeleted) {
			freezerResponse.setStatus(ResponseStatus.SUCCESS);
		} else {
			freezerResponse.setStatus(ResponseStatus.FAILURE);
			freezerResponse.setMessage("Could not delete");
		}	
		return freezerResponse;
	}
	
	public ItemsResponse deleteItem(int idItem) {
		ItemsResponse frozenItemsResponse = new ItemsResponse();

		boolean wasDeleted = freezerRepository.deleteItem(idItem);
		if (wasDeleted) {
			frozenItemsResponse.setStatus(ResponseStatus.SUCCESS);
		} else {
			frozenItemsResponse.setStatus(ResponseStatus.FAILURE);
			frozenItemsResponse.setMessage("Could not delete");
		}	
		return frozenItemsResponse;
	}


	public ItemsResponse updateItem(int idItem, int bagWeight, int numberBags) {
		ItemsResponse frozenItemsResponse = new ItemsResponse();
		
		boolean wasUpdated = freezerRepository.updateItem(idItem, bagWeight, numberBags);
		
		if(wasUpdated) {
			frozenItemsResponse.setStatus(ResponseStatus.SUCCESS);
			
		}
		else {
			frozenItemsResponse.setStatus(ResponseStatus.FAILURE);
			frozenItemsResponse.setMessage("Could not update");
		}
		
		return frozenItemsResponse;
	}
	
	public FreezersResponse getFreezerCapacity(int idFreezer) {
		FreezersResponse freezersResponse = new FreezersResponse();
		
		int capacity = freezerRepository.getFreezerCapacity(idFreezer);
		if (capacity == 0) {
			freezersResponse.setStatus(ResponseStatus.SUCCESS);
			freezersResponse.setMessage("Capacity is empty!");
		}
		else{
			freezersResponse.setStatus(ResponseStatus.SUCCESS);
			freezersResponse.setMessage("Capacity is " + capacity);
		}
		
		return freezersResponse;
	}

	public List<GetItemsAddedByUser> getItemsAddedByUser(String userName) {
		return freezerRepository.getItemsAddedByUser(userName);
	}

	public void setFreezerRepository(FreezerRepository freezerRepository) {
		this.freezerRepository = freezerRepository;	
	}

	public ItemsResponse updateWeightBagAfterIdItem(int idItem, int bagWeight)  {
		ItemsResponse iR = new ItemsResponse();
		
	    try {
			boolean response = freezerRepository.updateWeightBagAfterIdItem(idItem, bagWeight);
			if (response == true) {
				iR.setStatus(ResponseStatus.SUCCESS);
				iR.setMessage("Weight of bag update with success!");
			}
			else {
				iR.setStatus(ResponseStatus.FAILURE);
				iR.setMessage("Weight NOT update with success!");
			}
		} catch (ExceptionEmptyList e) {
			e.printStackTrace();
		}
		return iR;
	}

	
}
