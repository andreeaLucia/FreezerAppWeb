package freezer.controller.response;

import java.util.ArrayList;
import java.util.List;

import freezer.model.Items;
import freezer.model.Users;

public class UserResponse extends BaseResponse{
	private Users users;
	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}		
}
