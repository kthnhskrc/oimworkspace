package idm;

import java.util.ArrayList;
import java.util.List;
import oracle.iam.identity.usermgmt.api.UserManager;
import oracle.iam.identity.usermgmt.api.UserManagerConstants;
import oracle.iam.identity.usermgmt.vo.User;
import oracle.iam.platform.OIMClient;
import oracle.iam.platform.entitymgr.vo.SearchCriteria;

public class FindUser {

	public static void main(String[] args) throws Exception {

		User user = findUserByUserLogin("KTHNHSKRC", "Active");

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
	public static User findUserByUserLogin(String userLogin, String userStatus) {
		// Creates user instance
		User user = null;

		try {
			// Calls OimClient
			OIMClient client = CreateOIMClient.createClient();

			// Initiates userManager
			UserManager userManager = client.getService(UserManager.class);

			// Creates search criteria with userlogin
			SearchCriteria scUserLogin = new SearchCriteria(UserManagerConstants.AttributeName.USER_LOGIN.getId(), userLogin, SearchCriteria.Operator.EQUAL);
			
			// Creates search criteria with status
			SearchCriteria scStatus = new SearchCriteria(UserManagerConstants.AttributeName.STATUS.getId(), userStatus, SearchCriteria.Operator.EQUAL);
			
			// Combines criterias
			SearchCriteria scFinalCriteria = new SearchCriteria(scUserLogin, scStatus, SearchCriteria.Operator.AND);
						
			// Finds and gets user
			List<User> users = userManager.search(scFinalCriteria, null, null);
			user = users.get(0);

		} catch (Exception e) {
			System.out.println("Exception while creating OIMClient: " + e.getMessage());
		}

		return user;
	}
	
}