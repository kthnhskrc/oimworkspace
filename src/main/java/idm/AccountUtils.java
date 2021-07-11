package idm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import oracle.iam.identity.usermgmt.vo.User;
import oracle.iam.platform.OIMClient;
import oracle.iam.provisioning.api.ProvisioningService;
import oracle.iam.provisioning.vo.Account;
import oracle.iam.provisioning.vo.ApplicationInstance;

public class AccountUtils {

	public static void main(String[] args) {
		// Gets active directory account of active KTHNHSKRC user
		Account account = getAccountApplication("KTHNHSRC", "Active", "Active Directory");
		
		if(account != null) {
			// Gets app instance key of active directory account
			long appInstanceKey = Long.parseLong(account.getProcessInstanceKey());
			
			// Change other telephone information of active directory account
			changeAccountInformation("Other Telephone", "12345123", appInstanceKey);
		}
	}

	/**
	 * This method gets account application informations
	 * @author kthnhskrc
	 * @param userLogin
	 * @param status
	 * @param applicationName
	 * @return
	 */
	public static Account getAccountApplication(String userLogin, String status, String applicationName) {
		// Creates oim client
		OIMClient oimClient = CreateOIMClient.createClient();
		ProvisioningService provisioningService = oimClient.getService(ProvisioningService.class);

		try {
			// Find active KTHNHSKRC user
			User user = FindUser.findUserByUserLogin(userLogin, status);

			// Find matched user
			List<Account> matchedAccounts = provisioningService.getAccountsProvisionedToUser(user.getEntityId());
			for (Account account : matchedAccounts) {
				// Gets account app instance
				ApplicationInstance appInstance = account.getAppInstance();
				if (appInstance.getApplicationInstanceName().equals(applicationName)) {
					return account;
				}
			}
		} catch (Exception e) {
			System.out.println("Exception while getting account application: " + e.getMessage());
		}

		return null;
	}

	public static void changeAccountInformation(String key, Object value, long instanceKey) {
		// Maps key and value
		Map<String, Object> keyValuePair = new HashMap<String, Object>();
		keyValuePair.put(key, value);

		// Updates form process form data
		FormUtils.updateProcessFormData(instanceKey, keyValuePair);
	}
}
