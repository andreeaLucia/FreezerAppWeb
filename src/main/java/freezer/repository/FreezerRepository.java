package freezer.repository;

import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import freezer.controller.response.ReminderResponse;
import freezer.model.Authentication;
import freezer.model.Category;
import freezer.model.ExceptionName;
import freezer.model.Freezers;
import freezer.model.GetItemsAddedByUser;
import freezer.model.GetItemsFromFreezer;
import freezer.model.Items;
import freezer.model.Reminder;
import freezer.model.Users;
@Component
public class FreezerRepository {

	static final String DB_URL = "jdbc:mysql://localhost/freezerappdb";
	static final String USER = "root";
	static final String PASS = "";
	
	
	public Users loginUser(String usersName, String passwordHash){
		Users user = new Users();
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				PreparedStatement st = conn.prepareStatement("Select userName, passwordHash from Users where userName=? and passwordHash=?");){
			st.setString(1, usersName);
			st.setString(2, passwordHash);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				user.setUserName(rs.getString("userName"));
				user.setPasswordHash(rs.getString("passwordHash"));
			}
		} catch (SQLException se) {
			//Handle errors for JDBC
			se.printStackTrace();
		}
		return user;
	}

	public Users getUserByUsername(String userName) {
		Users user = new Users();
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				PreparedStatement st = conn.prepareStatement("Select * from Users where userName=?");){
			st.setString(1, userName);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				user.setIdUsers(rs.getInt("idUsers"));
				user.setUserName(rs.getString("userName"));
				user.setPasswordHash(rs.getString("passwordHash"));
				user.setCreatedDate(rs.getString("createdDate"));
				user.setLastModifiedDate(rs.getString("lastModifiedDate"));
			}
		} catch (SQLException se) {
			//Handle errors for JDBC
			se.printStackTrace();
		}
		return user;
	}	
	public int getUserIdByName(String userName) {
		int idUser = 0;
		Users user = new Users();
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				PreparedStatement st = conn.prepareStatement("Select users.idUsers from users where userName = 'userName'");){
			st.setString(1, userName);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				user.setIdUsers(rs.getInt("idUsers"));	
			}
		} catch (SQLException se) {
			//Handle errors for JDBC
			se.printStackTrace();
		}
		
		return idUser = user.getIdUsers();
	}
	
	public boolean logOutFromAllDevices(int idUserFk) {
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				PreparedStatement st = conn.prepareStatement("delete from authentication where idUserFk=?")) {
			st.setInt(1, idUserFk);
			int result = st.executeUpdate();
		    if(result == 0) {
		    	return false;
		    }
		    else {
		    	return true;
		    }
		} catch (SQLException se) {
			se.printStackTrace();
			return false;
		}	
	}
	
	public boolean deleteToken(String token) {
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				PreparedStatement st = conn.prepareStatement("delete from authentication where token=?")) {
			st.setString(1, token);
			int result = st.executeUpdate();
		    if(result == 0) {
		    	return false;
		    }
		    else {
		    	return true;
		    }
		} catch (SQLException se) {
			se.printStackTrace();
			return false;
		}	
	}
	public boolean logOut(String token) {
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				PreparedStatement st = conn.prepareStatement("delete from authentication where token=?")) {
			st.setString(1, token);
			int result = st.executeUpdate();
		    if(result == 0) {
		    	return false;
		    }
		    else {
		    	return true;
		    }
		} catch (SQLException se) {
			se.printStackTrace();
			return false;
		}	
	}

	public Authentication verifyTokenJava(String token) {
		Authentication aut = new Authentication();
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				PreparedStatement st = conn.prepareStatement("SELECT * FROM authentication WHERE token=?;")) {
			st.setString(1, token);
			ResultSet  rs = st.executeQuery();
			while (rs.next()) {
				aut.setIdAuthentication(rs.getInt("idAuthentication")); 
				aut.setToken(rs.getString("token"));
				aut.setIdUserFk(rs.getInt("idUserFk"));
				aut.setCreatedDate(rs.getTimestamp("createdDate").toLocalDateTime());
				aut.setModifiedDate(rs.getDate("lastModifiedDate"));
			}
		} catch (SQLException se) {
			se.printStackTrace();
		}
		return aut;
	}
	
	public boolean saveToken(int idUserFk, String token) {
	
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				PreparedStatement st = conn.prepareStatement("Insert into Authentication(idUserFk, token, createdDate, lastModifiedDate) values (?, ?, ?, ?);");){
			st.setInt(1, idUserFk);
			st.setString(2, token);
			st.setString(3, getCurrentDate());
			st.setString(4, getCurrentDate());
			int rs = st.executeUpdate();
			if(rs == 0) {
				return false;
			}
		} catch (SQLException se) {
			//Handle errors for JDBC
			se.printStackTrace();
			return false;
		}
		
		return true;
	}
	public  boolean addUser(String userName, String passwordHash, boolean isAdmin){

		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				PreparedStatement stmt = conn.prepareStatement("Insert into USERS (userName, passwordHash, createdDate, lastModifiedDate, isAdmin) values(?, ?, ?, ?, ?)");){
			stmt.setString(1, userName);
			stmt.setString(2, passwordHash);
			stmt.setString(3, getCurrentDate());
			stmt.setString(4, getCurrentDate());
			stmt.setBoolean(5, isAdmin);
			int result = stmt.executeUpdate();
			if (result == 0) {
				return false;
			}
		}catch(SQLException se){
			se.getStackTrace();
		}
		return true;
	}
	
	public boolean addReminder(String message, Date date, int forUser) {
		try(Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
				PreparedStatement stmt = conn.prepareStatement("Insert into Reminder(message, date, addedDate, modifiedDate, forUser) values (?, ?, ?, ?, ?)");){
			stmt.setString(1, message);
			stmt.setDate(2, (java.sql.Date) date);
			stmt.setString(3, getCurrentDate());
			stmt.setString(4, getCurrentDate());
			stmt.setInt(5, forUser);
			int result = stmt.executeUpdate();
			if (result == 0) {
				return false;
			}
		}catch(SQLException se){
			se.getStackTrace();
			return false;
		}
		return true;

}
	public boolean deleteReminder(int  idReminder) {
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				PreparedStatement st = conn.prepareStatement("Delete from reminder where idReminder=?");){
			st.setInt(1, idReminder);
			int result = st.executeUpdate();
		    if(result == 0) {
		    	return false;
		    }
		} catch (SQLException se) {
			//Handle errors for JDBC
			se.printStackTrace();
			return false;
		}
		return true;
	}	
	public boolean updateReminder(String message, Date date, int forUser, int idReminder) {
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				PreparedStatement st = conn.prepareStatement("Update reminder set message=?, date=?, forUser=?, addedDate=now(), modifiedDate=now() where idReminder=?");){
			st.setString(1, message);
			st.setDate(2, (java.sql.Date) date);
			st.setInt(3, forUser);
			st.setInt(4, idReminder);
			st.executeUpdate();
		} catch (SQLException se) {
			//Handle errors for JDBC
			se.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean deleteUser(int idUsers) {
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				PreparedStatement st = conn.prepareStatement("Delete from users where idUsers=?");){
			st.setInt(1, idUsers);
			st.executeUpdate();
		
		} catch (SQLException se) {
			//Handle errors for JDBC
			se.printStackTrace();
			return false;
		}
		return true;
	}	
	public boolean updateUser(String userName, String password, int idUsers) {
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				PreparedStatement st = conn.prepareStatement("Update users set userName=?, passwordH=?, lastModifiedDate=now() where idUsers=?");){
			st.setString(1, userName);
			st.setString(2, password);
			st.setInt(3, idUsers);
			st.executeUpdate();
		} catch (SQLException se) {
			//Handle errors for JDBC
			se.printStackTrace();
			return false;
		}
		return true;
	}
	public List<Reminder> getAllRemindersForAuser(int forUser) throws ExceptionName {
	    
		List<Reminder> list = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				PreparedStatement st = conn.prepareStatement("select idReminder, message, date, addedDate, modifiedDate, forUser from reminder where forUser=?")) {
			st.setInt(1, forUser);
			ResultSet  rs = st.executeQuery();
			while (rs.next()) {
				Reminder reminder = new Reminder();
				reminder.setIdReminder(rs.getInt("idReminder")); 
				reminder.setMessage(rs.getString("message"));
				reminder.setDate(rs.getString("date")); 
				reminder.setAddedDate(rs.getString("addedDate")); 
				reminder.setModifiedDate(rs.getString("modifiedDate")); 
				reminder.setForUser(rs.getInt("forUser")); 
				list.add(reminder);
			}
		} catch (SQLIntegrityConstraintViolationException se) {
			throw new ExceptionName("Give another message!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
   public List<Reminder> getAllReminders() throws ExceptionName {
		List<Reminder> list = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				PreparedStatement st = conn.prepareStatement("select idReminder, message, date, addedDate, modifiedDate, forUser from reminder")) {
			ResultSet  rs = st.executeQuery();
			while (rs.next()) {
				Reminder reminder = new Reminder();
				reminder.setIdReminder(rs.getInt("idReminder")); 
				reminder.setMessage(rs.getString("message"));
				reminder.setDate(rs.getString("date")); 
				reminder.setAddedDate(rs.getString("addedDate")); 
				reminder.setModifiedDate(rs.getString("modifiedDate")); 
				reminder.setForUser(rs.getInt("forUser")); 
				list.add(reminder);
			}
		} catch (SQLIntegrityConstraintViolationException se) {
			throw new ExceptionName("Give another message!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
   }
	public Users getUserById(int idUsers) {

		Users user = new Users();
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				PreparedStatement st = conn.prepareStatement("select usersName from users where idUsers=?")) {
			st.setInt(1, idUsers);
			ResultSet  rs = st.executeQuery();
			while (rs.next()) {
				user.setUserName(rs.getString("userName"));
				user.setIdUsers(rs.getInt("idUsers"));
			}
		} catch (SQLException se) {
			se.printStackTrace();
		}
		return user;
	}
	public List<Users> getAllUsers() throws ExceptionName {
		List<Users> list = new ArrayList<>();
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				PreparedStatement st = conn.prepareStatement("select *  from users")){
			ResultSet  rs = st.executeQuery();
			while(rs.next()){
				Users user = new Users();
				user.setIdUsers(rs.getInt("idUsers"));
				user.setUserName(rs.getString("userName"));
				user.setPassword(rs.getString("password"));
				user.setPasswordHash(rs.getString("passwordHash"));
				user.setCreatedDate(rs.getString("createdDate"));
				user.setLastModifiedDate(rs.getString("lastModifiedDate"));
				list.add(user);
			}
		}catch(SQLException se){
			throw new ExceptionName("An exception ocurred!");
		}
		return list;
	}
	
	public boolean isAdmin(String token) {
		boolean isAdmin = false;
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				PreparedStatement st = conn.prepareStatement("select isAdmin from users inner join authentication on authentication.idUserfk = users.idUsers where token = ?;");){
			st.setString(1, token);
			ResultSet  rs = st.executeQuery();
			while(rs.next()){
				isAdmin = rs.getBoolean("isAdmin");
			}
		}catch(SQLException se){
			se.printStackTrace();
		}
		return isAdmin;
	}
	
	public List<Users> getAllUsersByName(String userName){
		List<Users> list = new ArrayList<>();
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				PreparedStatement st = conn.prepareStatement("select userName from users")){
			ResultSet  rs = st.executeQuery();
			while(rs.next()){
				Users user = new Users();
				user.setIdUsers(rs.getInt("idUsers"));
				user.setUserName(rs.getString("usersName"));
				user.setPasswordHash(rs.getString("passwordHash"));
				user.setCreatedDate(rs.getString("createdDate"));
				user.setLastModifiedDate(rs.getString("lastModifiedDate"));
				list.add(user);
			}
			
		}catch(SQLException se){
			se.printStackTrace();
		}
		return list;
	}
	public boolean addFreezer(String freezerName, int capacity) throws ExceptionName {

		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				PreparedStatement stmt = conn.prepareStatement(
						"Insert into Freezers(freezerName, capacity, createdDate, lastModifiedDate) values(?,?,now(),now())");){
			stmt.setString(1, freezerName);
			stmt.setInt(2, capacity);
			stmt.executeUpdate();
		}catch(SQLIntegrityConstraintViolationException e){
			throw new ExceptionName(e.getMessage());
		}catch(SQLException se){
			se.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean updateFreezer(int idFreezers, String freezerName, int capacity) {
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				PreparedStatement st = conn.prepareStatement("Update freezers set freezerName=?, capacity=?, lastModifiedDate=now() where idFreezer=?");){
			st.setString(1, freezerName);
			st.setInt(2, capacity);
			st.setInt(3, idFreezers);
			st.executeUpdate();
			if (st.executeUpdate() == 0) {
				return false;
			}
		} catch (SQLException se) {
			//Handle errors for JDBC
			se.printStackTrace();
		}
		return true;
	}
	public List<Freezers> getAllFreezers(){
		List<Freezers> freezerList = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				PreparedStatement st = conn.prepareStatement("select * from freezers");){
			ResultSet  rs = st.executeQuery();
			while (rs.next()) {
				Freezers freezer = new Freezers();
				freezer.setIdFreezer(rs.getInt("idFreezer"));
				freezer.setFreezerName(rs.getString("freezerName"));
				freezer.setCapacity(rs.getInt("capacity"));
				freezer.setCreatedDate(rs.getString("createdDate"));
				freezer.setLastModifiedDate(rs.getString("lastModifiedDate"));
				freezerList.add(freezer);
			}
		} catch (SQLException se) {
			se.printStackTrace();	
		}
		return freezerList;
	}
	public int getFreezerCapacity(int idFreezer){

		Freezers freezers = new Freezers();
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				PreparedStatement st = conn.prepareStatement("Select * from freezers where idFreezer=?");){
			st.setInt(1, idFreezer);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				freezers.setIdFreezer(rs.getInt("idFreezer"));
				freezers.setFreezerName(rs.getString("freezerName"));
				freezers.setCapacity(rs.getInt("capacity"));
				freezers.setCreatedDate(rs.getString("createdDate"));
				freezers.setLastModifiedDate(rs.getString("lastModifiedDate"));
			}
		}
		catch (SQLException se) {
			//Handle errors for JDBC
			se.printStackTrace();
		}
		
		return freezers.getCapacity();
	}
	
	private String getCurrentDate() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String strDate = sdfDate.format(now);
		return strDate;
	}
	
	public boolean deleteFreezer(int idFrezeer) {
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				PreparedStatement st = conn.prepareStatement("Delete from freezers where idFreezer=?");){
			st.setInt(1, idFrezeer);
			st.executeUpdate();	
			if(st.executeUpdate() == 0) {
				
				return true;
			}
		} catch (SQLException se) {
			//Handle errors for JDBC
			se.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean addItem(int numberBags, int bagWeight, int fk_id_freezer, int fk_id_user, int fk_id_category) {
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				PreparedStatement stmt = conn.prepareStatement(
						"Insert into items (numberBags, bagWeight, "
						+ "fk_id_freezer, fk_id_user, fk_id_category, createdDate, lastModifiedDate) "
						+ "values (?, ?, ?, ?, ?, now(), now())");){
			stmt.setInt(1, numberBags);
			stmt.setInt(2, bagWeight);
			stmt.setInt(3, fk_id_freezer);
			stmt.setInt(4, fk_id_user);
			stmt.setInt(5, fk_id_category);
			stmt.executeUpdate();
		} catch (SQLException se) {
			//Handle errors for JDBC
			se.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean deleteItem (int idItem) {
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				PreparedStatement st = conn.prepareStatement("Delete from items where idItem=?");){
			st.setInt(1, idItem);
			int response = st.executeUpdate();
			if (response == 0) {
				return false;
			}
		} catch (SQLException se) {
			//Handle errors for JDBC
			se.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean updateItem(int idItem, int bagWeight, int numberBags) {
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				PreparedStatement st = conn.prepareStatement("Update items set bagWeight=?, numberBags=?,lastModifiedDate=now() where idItem=?");){
			st.setInt(1, bagWeight);
			st.setInt(2, numberBags);
			st.setInt(3, idItem);
			st.executeUpdate();
			if(st.executeUpdate() == 0) {
				return false;
			}
		} catch (SQLException se) {
			//Handle errors for JDBC
			se.printStackTrace();
		}
		return true;
	}
	public boolean updateCategory(int idCategory, String categoryName) {
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				PreparedStatement st = conn.prepareStatement("Update category set categoryName=?,lastModifiedDate=now() where idCategory=?");){
			st.setString(1, categoryName);
			st.setInt(2, idCategory);
			st.executeUpdate();
			if(st.executeUpdate() == 0) {
				return false;
			}
		} catch (SQLException se) {
			//Handle errors for JDBC
			se.printStackTrace();
		}
		return true;
	}
	
	public List<Items> getItems(int fk_id_freezer) {	
		List<Items>listOfItems = new ArrayList<>();
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				PreparedStatement st = conn.prepareStatement("Select * from items where fk_id_freezer=?");){
			st.setInt(1, fk_id_freezer);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Items items = new Items();
				items.setIdItem(rs.getInt("idItem"));
				items.setFk_id_user(rs.getInt("fk_id_users"));
				items.setFk_id_freezer(rs.getInt("fk_id_freezer"));
				items.setNumberBags(rs.getInt("numberBags"));
				items.setBagWeight(rs.getInt("BagWeight"));	
				listOfItems.add(items);
			}			
		}
		catch (SQLException se) {
			//Handle errors for JDBC
			se.printStackTrace();
		}
		return listOfItems;
	}
	
	public List<Category>getCategory(int categoryName) {

		List<Category> categoryList = new ArrayList<>();
		
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				PreparedStatement st = conn.prepareStatement("Select * from category where categoryName=?");){
			st.setInt(1, categoryName);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Category category = new Category();
				category.setIdCategory(rs.getInt("idCategory"));
				category.setCategoryName(rs.getString("categoryName"));
				category.setCreatedDate(rs.getString("createdDate"));
				category.setLastModifiedDate(rs.getString("lastModifiedDate"));
				categoryList.add(category);
			}
		}
		catch (SQLException se) {
			//Handle errors for JDBC
			se.printStackTrace();
		}
		return categoryList;
	}
	
	public List<Category>getAllCategories() {
		
		List<Category> categoryList = new ArrayList<>();
		
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				PreparedStatement st = conn.prepareStatement("select * from category");){
			ResultSet  rs = st.executeQuery();
			while (rs.next()) {
				Category categories = new Category();
				categories.setIdCategory(rs.getInt("idCategory"));
				categories.setCategoryName(rs.getString("categoryName"));
				categories.setCreatedDate(rs.getString("createdDate"));
				categories.setLastModifiedDate(rs.getString("lastModifiedDate"));
				categoryList.add(categories);
			}
		} catch (SQLException se) {
			//Handle errors for JDBC
			se.printStackTrace();
		}
		return categoryList;
	}
	
	public List<GetItemsFromFreezer> getItemsFromFreezer(String frezeerName) {
		List<GetItemsFromFreezer> list = new ArrayList<>();
		
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				PreparedStatement st = conn.prepareStatement("Select items.idItem, items.numberBags, items.bagWeight, users.userName, category.categoryName " + 
						"from items " + 
						"inner join users on users.idUsers = items.fk_id_user " + 
						"inner join category on category.idCategory = items.fk_id_category " + 
						"inner join freezers on freezers.idFreezers = items.fk_id_freezer " + 
						"where freezers.freezerName = ?;");){
			st.setString(1, frezeerName); 
			ResultSet rs = st.executeQuery(); 
			while (rs.next()) {
				GetItemsFromFreezer items = new GetItemsFromFreezer();
				items.setIdItem(rs.getInt("idItem"));
				items.setUserName(rs.getString("userName"));
				items.setItemName(rs.getString("itemName"));
				items.setNumberBags(rs.getInt("numberBags"));
				items.setBagWeight(rs.getInt("bagWeight"));
				list.add(items);
			}
		} catch (SQLException se) {
			//Handle errors for JDBC
			se.printStackTrace();
		}
		return list;
	}

	public List<GetItemsAddedByUser> getItemsAddedByUser(String userName) {
		
		List<GetItemsAddedByUser> list = new ArrayList<>();
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				PreparedStatement st = conn.prepareStatement("Select items.idItem, items.numberBags, items.bagWeight, freezers.freezerName, category.categoryName" + 
						"from items " + 
						"inner join freezers on freezers.idFreezers = items.fk_id_freezer " + 
						"inner join category on category.idCategory = items.id_category " + 
						"inner join users on users.idUsers = items.fk_id_user " + 
						"where users.userName = ?");){
			st.setString(1, userName);
			ResultSet  rs = st.executeQuery();
			while (rs.next()) {
				GetItemsAddedByUser items = new GetItemsAddedByUser();
				items.setIdItem(rs.getInt("idItem"));
				items.setFreezerName(rs.getString("freezerName"));
				items.setCategoryName(rs.getString("categoryName"));
				items.setNumberBags(rs.getInt("numberBags"));
				items.setBagWeight(rs.getInt("bagWeight"));
				list.add(items);
			}
		} catch (SQLException se) {
			//Handle errors for JDBC
			se.printStackTrace();
		}
		return list;
	}
	
	public Category getCategoryById(int idCategory) {
		Category category = new Category();
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				PreparedStatement st = conn.prepareStatement("select categoryName from category where idCategory=?");){
			st.setInt(1, idCategory);
			ResultSet  rs = st.executeQuery();
			while (rs.next()) {
				category.setIdCategory(rs.getInt("idCategory"));
				category.setCategoryName(rs.getString("categoryName"));
				category.setCreatedDate(rs.getString("createdDate"));
				category.setLastModifiedDate(rs.getString("lastModifiedDate"));
			}
		} catch (SQLException se) {
			se.printStackTrace();
		}
		return category;
	}	
	public List<Category> getAllCategorys() throws ExceptionEmptyList {

		List<Category> categoryList = new ArrayList<>();
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				PreparedStatement st = conn.prepareStatement("select * from category");){
			ResultSet  rs = st.executeQuery();
			while (rs.next()) {
				Category category = new Category();
				//TODO add addedDate
				category.setIdCategory(rs.getInt("idCategory"));
				category.setCategoryName(rs.getString("categoryName"));
				category.setCreatedDate(rs.getString("createdDate"));
				category.setLastModifiedDate(rs.getString("lastModifiedDate"));
				categoryList.add(category);
			}
			if(categoryList.isEmpty()) {
				throw new ExceptionEmptyList("Lista este goala!");
			}
		}catch (SQLException se) {
			se.printStackTrace();
		}

		return categoryList;
	}

	public boolean addCategory(String categoryName) throws ExceptionName {

		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				PreparedStatement st = conn.prepareStatement("insert into category (categoryName,createdDate,lastModifiedDate) values(?,now(),now())");){
			st.setString(1, categoryName);
			if (st.executeUpdate() == 0) {
				return false;
			}
		} catch (SQLIntegrityConstraintViolationException se) {
			throw new ExceptionName("The name of the category is not good!");
			
		} catch (SQLException se) {
			se.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean updateWeightBagAfterIdItem(int idItem, int bagWeight) throws ExceptionEmptyList {
		List<Items> itemsList = new ArrayList<>();
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				PreparedStatement st = conn.prepareStatement("update items inner join freezers on freezers.idFreezer = items.fk_id_freezer set bagWeight=? where idItem=?");){
			st.setInt(1, bagWeight);
			st.setInt(2, idItem);
			st.executeUpdate();
			if(st.executeUpdate() == 0) {
				return false;
			}
			if(itemsList.isEmpty()) {
				throw new ExceptionEmptyList("Lista este goala!");
			}
		}catch (SQLException se) {
			se.printStackTrace();
		}
		return true;
	}

}







