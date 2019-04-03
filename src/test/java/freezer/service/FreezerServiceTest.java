package freezer.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import freezer.controller.response.AdminResponse;
import freezer.controller.response.FreezersResponse;
import freezer.controller.response.ResponseStatus;
import freezer.model.ExceptionName;
import freezer.model.Users;
import freezer.repository.FreezerRepository;
import org.junit.Assert;
import org.junit.Before;


@RunWith(MockitoJUnitRunner.class) 
public class FreezerServiceTest {
	
	@Mock
	FreezerRepository freezerRepoMock ;
	
	@InjectMocks
	FreezerService freezerService;
	
	@Test
	@Before
	public void methodRuns() {
		System.out.println("Asta ruleaza inainte de fiecare test case!");
	}
	@Test
	public void getAllUsersSuccesTest() throws ExceptionName {
		List<Users> users = new ArrayList<>();
		users.add(new Users(5,"ana","gfhgf56","abcd","2018-11-12 21:04:22","2018-11-13 10:17:17",true));
		users.add(new Users(5,"ana","gfhgf56","abcd","2018-11-12 21:04:22","2018-11-13 10:17:17",true));
		users.add(new Users(5,"ana","gfhgf56","abcd","2018-11-12 21:04:22","2018-11-13 10:17:17",true));
		
		when(freezerRepoMock.getAllUsers()).thenReturn(users);
	
		AdminResponse  result = freezerService.getAllUsers();
		
		Assert.assertEquals(ResponseStatus.SUCCESS, result.getStatus());
		Assert.assertEquals(users.size(), result.getUsersList().size());
		
		
	}
	@Test
	public void getAllUsersFailureTest() throws ExceptionName {
		try {
			when(freezerRepoMock.getAllUsers()).thenThrow(new ExceptionName("An exception ocurred!"));
		} catch (ExceptionName e) {
			throw new ExceptionName("An exception ocurred!");
			
		}
		
		AdminResponse  result = freezerService.getAllUsers();
		
		Assert.assertEquals(ResponseStatus.FAILURE, result.getStatus());
		
	}
	
	@Test
	public void testAddFreezer() throws ExceptionName {
		String freezerName = "freezerOne";
		int capacity = 3500;
		Boolean value = true;
		when(freezerRepoMock.addFreezer(freezerName, capacity)).thenReturn(true);
		FreezersResponse result = freezerService.addFreezer(freezerName, capacity);
		Assert.assertEquals(ResponseStatus.SUCCESS, result.getStatus());
	}
	
	@Test
	public void failureTestAddFreezer() throws ExceptionName {
		String freezerName = "freezerOne";
		int capacity = 3500;
		Boolean value = false;
		when(freezerRepoMock.addFreezer(freezerName, capacity)).thenReturn(false);
		FreezersResponse result = freezerService.addFreezer(freezerName, capacity);
		Assert.assertEquals(ResponseStatus.FAILURE, result.getStatus());
	}

	
	@Test
	public void testUpdateFreezerSucces() {
		int idFreezers = 123;
		String freezerName = "freezerManastur";
		int capacity = 20;
		when(freezerRepoMock.updateFreezer(idFreezers, freezerName, capacity)).thenReturn(true);
		FreezersResponse result = freezerService.updateFreezer(idFreezers, freezerName, capacity);
		Assert.assertEquals(ResponseStatus.SUCCESS, result.getStatus());
	}
	
	@Test
	public void testUpdateFreezerFailure() {
		
	}
//
//	@Test
//	public void testDeleteFreezer() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetFreezerCapacity() {
//		fail("Not yet implemented");
//	}

}
