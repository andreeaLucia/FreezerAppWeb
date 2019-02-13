package freezer.controller.response;

public class LoginResponse extends BaseResponse{
	private String token;
	private int idUsers;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getIdUsers() {
		return idUsers;
	}

	public void setIdUsers(int idUsers) {
		this.idUsers = idUsers;
	}
	
	
	
}
