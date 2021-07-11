package idm;

import oracle.iam.platform.OIMClient;
import oracle.iam.provisioning.api.ApplicationInstanceService;
import oracle.iam.provisioning.vo.ApplicationInstance;

public class FindApplicationInstance {

	public static void main(String[] args) {
		ApplicationInstance applicationInstance = getApplicationInstance("Active Directory");
		
		// Prints application informations
		if(applicationInstance != null) {
			applicationInstance.getDisplayName();
			applicationInstance.getItResourceKey();
			applicationInstance.getDescription();
		}
	}

	public static ApplicationInstance getApplicationInstance(String applicationInstanceName) {
		// Initializes application instance
		ApplicationInstance appInstance = null;

		// Gets application instance service
		OIMClient oimClient = CreateOIMClient.createClient();
		ApplicationInstanceService appInstService = oimClient.getService(ApplicationInstanceService.class);

		try {
			// Gets application instance information
			appInstance = appInstService.findApplicationInstanceByName(applicationInstanceName);
		} catch (Exception e) {
			System.out.println("Exception while creating OIMClient: " + e.getMessage());
		}

		return appInstance;
	}

}
