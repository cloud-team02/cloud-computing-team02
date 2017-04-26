package cloud.clouddb.cloud_app;

public class App{
	private String App_name;
	private int App_id;
	private int Developer_id;
	private int use_times;
	private double price;
	private String Url;
	
	public String getApp_name() {
		return App_name;
	}
	public void setApp_name(String app_name) {
		this.App_name = app_name;
	}
	public int getApp_id() {
		return App_id;
	}
	public void setApp_id(int app_id) {
		this.App_id = app_id;
	}
	public int getDeveloper_id() {
		return Developer_id;
	}
	public void setDeveloper_id(int developer_id) {
		this.Developer_id = developer_id;
	}
	public int getUse_times() {
		return use_times;
	}
	public void setUse_times(int use_times) {
		this.use_times = use_times;
	}
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		this.Url = url;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

	
	@Override
	public String toString()
	{
		String str=this.App_id +" - "+ this.App_name;
		return str;
	}
}
