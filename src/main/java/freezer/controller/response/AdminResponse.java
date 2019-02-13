package freezer.controller.response;


import java.util.List;

import freezer.model.Users;

public class AdminResponse extends BaseResponse {
	List<Users> admin;

	public AdminResponse(){	
	}
	
	public AdminResponse(List<Users> admin){
		this.admin = admin;
	}

	public List<Users> getAdmin() {
		return admin;
	}

	public void setAdmin(List<Users> list) {
		this.admin = list;
	}
	
}
