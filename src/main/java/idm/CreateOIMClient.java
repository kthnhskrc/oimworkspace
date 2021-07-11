package idm;

import java.util.Hashtable;
import oracle.iam.platform.OIMClient;

public class CreateOIMClient {
	public static void main(String[] args) {
		System.out.println("Client will be created");
		OIMClient oimClient = createClient();
		if (oimClient != null) {
			System.out.println("Client created");
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	/**
	 * This method creates oim client
	 * @author kthnhskrc
	 * @return oimclient
	 */
	public static OIMClient createClient() {
		// Creates OIM client object
		OIMClient oimClient = new OIMClient();

		// Sets server URL
		String serverURL = "t3://localhost:14000";

		// Sets username and password
		String username = "xelsysadm";
		String password = "********";

		try {
			// Sets environment variables
			Hashtable env = new Hashtable();
			env.put(OIMClient.JAVA_NAMING_FACTORY_INITIAL, "weblogic.jndi.WLInitialContextFactory");
			env.put(OIMClient.JAVA_NAMING_PROVIDER_URL, serverURL);

			System.setProperty("java.security.auth.login.config", "/authwl.conf");
			System.setProperty("APPSERVER_TYPE", "wls");

			// Initiates client with oim environment variables
			oimClient = new OIMClient(env);

			// Logins oim
			oimClient.login(username, password.toCharArray());

		} catch (Exception e) {
			oimClient = null;
			System.out.println("Exception while creating OIMClient: " + e.getMessage());
		}

		return oimClient;

	}

}
