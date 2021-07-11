package idm;

import java.util.Map;
import Thor.API.Operations.tcFormInstanceOperationsIntf;
import oracle.iam.platform.OIMClient;

public class FormUtils {

	public static void updateProcessFormData(long pik, Map<String, Object> map) {
		// Creates oim client
		OIMClient oimClient = CreateOIMClient.createClient();
		tcFormInstanceOperationsIntf tcFormInstanceOperationsService = oimClient
				.getService(tcFormInstanceOperationsIntf.class);

		try {
			// Sets process form data
			tcFormInstanceOperationsService.setProcessFormData(pik, map);
		} catch (Exception e) {
			System.out.println("Exception while setting process form data: " + e.getMessage());
		}
	}

}
