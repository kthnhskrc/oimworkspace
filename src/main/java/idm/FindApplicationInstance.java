package idm;

import oracle.iam.platform.OIMClient;
import oracle.iam.provisioning.api.ApplicationInstanceService;
import oracle.iam.provisioning.vo.ApplicationInstance;

public class FindApplicationInstance {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationInstance applicationInstance = getApplicationInstance("Active Directory");
		
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
			appInstance = appInstService.findApplicationInstanceByName(applicationInstanceName);
		} catch (Exception e) {
			System.out.println("Exception while creating OIMClient: " + e.getMessage());
		}

		return appInstance;
	}

}
