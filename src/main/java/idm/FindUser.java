package idm;

import java.util.List;
import oracle.iam.identity.usermgmt.api.UserManager;
import oracle.iam.identity.usermgmt.api.UserManagerConstants;
import oracle.iam.identity.usermgmt.vo.User;
import oracle.iam.platform.OIMClient;
import oracle.iam.platform.entitymgr.vo.SearchCriteria;

public class FindUser {

	public static void main(String[] args) throws Exception {

		User user = findUserByUserLogin("KTHNHSKRC");

		// Prints user attributes
		if (user != null) {
			System.out.println("FirstName:" + user.getFirstName());
			System.out.println("LastName:" + user.getLastName());
			System.out.println("Email:" + user.getEmail());
		}

	}

	/**
	 * This method gets userLogin and return matched user object
	 * @author kthnhskrc
	 * @param userLogin
	 * @return user
	 */
	public static User findUserByUserLogin(String userLogin) {
		// Creates user instance
		User user = null;

		try {
			// Calls OimClient
			OIMClient client = CreateOIMClient.createClient();

			// Initiate userManager
			UserManager userManager = client.getService(UserManager.class);

			// Create search criteria with userlogin
			SearchCriteria criteria = new SearchCriteria(UserManagerConstants.AttributeName.USER_LOGIN.getId(), userLogin, SearchCriteria.Operator.EQUAL);

			// Finds and gets user
			List<User> users = userManager.search(criteria, null, null);
			user = users.get(0);

		} catch (Exception e) {
			System.out.println("Exception while creating OIMClient: " + e.getMessage());
		}

		return user;
	}
	
}