package idm;

import Thor.API.Operations.tcFormInstanceOperationsIntf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.iam.identity.usermgmt.vo.User;
import oracle.iam.platform.OIMClient;
import oracle.iam.platform.Platform;
import oracle.iam.platform.entitymgr.vo.SearchCriteria;
import oracle.iam.provisioning.api.ApplicationInstanceService;
import oracle.iam.provisioning.api.ProvisioningConstants;
import oracle.iam.provisioning.api.ProvisioningService;
import oracle.iam.provisioning.vo.Account;
import oracle.iam.provisioning.vo.ApplicationInstance;

public class AccountUtils {


    //Initialize oimClient and services in the constructor, in order to be available for all methods in the class
    OIMClient oimClient;

    public AccountUtils() {
        oimClient = CreateOIMClient.createClient();
        getServices(oimClient);
    }


    public static void main(String[] args) {
        // Gets active directory account of active KTHNHSKRC user
        Account account = getAccountApplication("KTHNHSRC", "Active", "Active Directory");

        if (account != null) {
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
      
        try {
            // Find active KTHNHSKRC user
            User user = FindUser.findUserByUserLogin(userLogin, status);

            //Find application instance object
            ApplicationInstance appInstance = appInstanceService.findApplicationInstanceByName(applicationName);

            // Find matched user
            //This way it searches all Application Instances from the same Resource Object, in case there are more than one
            SearchCriteria sc =
                new SearchCriteria(ProvisioningConstants.AccountSearchAttribute
                                                                        .OBJ_NAME
                                                                        .getId(), appInstance.getObjectName(),
                                   SearchCriteria.Operator.EQUAL);
            List<Account> matchedAccounts =
                provisioningService.getAccountsProvisionedToUser(user.getEntityId(), sc, null, false);
            Account account = null;

            //Gets only Provisioned or Enabled accounts
            //If there is more than one Provisioned or Enabled account, gets the primary
            for (Account acc : matchedAccounts) {
                if (acc.getAppInstance()
                       .getApplicationInstanceName()
                       .equalsIgnoreCase(appInstance.getApplicationInstanceName())) {
                    if (!ProvisioningConstants.ObjectStatus
                                              .REVOKED
                                              .getId()
                                              .equalsIgnoreCase(acc.getAccountStatus())) {
                        if (acc.getAccountType().equals(Account.ACCOUNT_TYPE.Primary)) {
                            account = acc;
                            break;
                        }
                    }
                }
            }


        } catch (Exception e) {
            System.out.println("Exception while getting account application: " + e.getMessage());
        }

        return null;
    }

    private static ProvisioningService provisioningService;
    private static ApplicationInstanceService appInstanceService;

    private static void getServices(OIMClient client) {
        if (appInstanceService == null) {
            appInstanceService =
                client == null ? Platform.getService(ApplicationInstanceService.class) :
                client.getService(ApplicationInstanceService.class);
        }
        if (provisioningService == null) {
            provisioningService =
                client == null ? Platform.getService(ProvisioningService.class) :
                client.getService(ProvisioningService.class);
        }
    }


    public static void changeAccountInformation(String key, Object value, long instanceKey) {
        // Maps key and value
        Map<String, Object> keyValuePair = new HashMap<String, Object>();
        keyValuePair.put(key, value);

        // Updates form process form data
        FormUtils.updateProcessFormData(instanceKey, keyValuePair);
    }
}
