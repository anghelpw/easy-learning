package ro.ase.ism.login.db;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

public class UserFunctions {

	private JSONParser jsonParser;

	// URL of the PHP API
	private static String loginURL = "http://practica.gdm.ro/danut/android_razvan/Functions.php";
	private static String registerURL = "http://practica.gdm.ro/danut/android_razvan/Functions.php";
	private static String forpassURL = "http://practica.gdm.ro/danut/android_razvan/Functions.php";
	private static String chgpassURL = "http://practica.gdm.ro/danut/android_razvan/Functions.php";

	private static String login_tag = "login";
	private static String register_tag = "register";
	private static String forpass_tag = "forpass";
	private static String chgpass_tag = "chgpass";

	// Constructor
	public UserFunctions() {
		jsonParser = new JSONParser();
	}

	/**
	 * Function to Login
	 **/

	public JSONObject loginUser(String email, String password) {

		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", login_tag));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
		JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
		return json;
	}

	/**
	 * Function to change the password
	 **/

	public JSONObject chgPass(String newPass, String email) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", chgpass_tag));

		params.add(new BasicNameValuePair("newpas", newPass));
		params.add(new BasicNameValuePair("email", email));
		JSONObject json = jsonParser.getJSONFromUrl(chgpassURL, params);
		return json;
	}

	/**
	 * Function to reset the password
	 **/

	public JSONObject forPass(String forgotPassword) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", forpass_tag));
		params.add(new BasicNameValuePair("forgotpassword", forgotPassword));
		JSONObject json = jsonParser.getJSONFromUrl(forpassURL, params);
		return json;
	}

	/**
	 * Function to Create Account
	 **/

	public JSONObject registerUser(String email, String username,
			String password) {

		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", register_tag));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("password", password));
		JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
		return json;
	}

}