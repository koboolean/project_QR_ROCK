package vo;

public class User {
	private String user_Device_ID;
	private String user_QR_Code;
	private String user_Phone;
	private int user_Password;
	private int user_admin;
	
	public User() {
		super();
	}
	
	public String getUser_Device_ID() {
		return user_Device_ID;
	}
	public void setUser_Device_ID(String user_Device_ID) {
		this.user_Device_ID = user_Device_ID;
	}
	public String getUser_QR_Code() {
		return user_QR_Code;
	}
	public void setUser_QR_Code(String user_QR_Code) {
		this.user_QR_Code = user_QR_Code;
	}
	public String getUser_Phone() {
		return user_Phone;
	}
	public void setUser_Phone(String user_Phone) {
		this.user_Phone = user_Phone;
	}
	public int getUser_Password() {
		return user_Password;
	}
	public void setUser_Password(int user_Password) {
		this.user_Password = user_Password;
	}
	public int getUser_admin() {
		return user_admin;
	}
	public void setUser_admin(int user_admin) {
		this.user_admin = user_admin;
	}
	
	
	
}
