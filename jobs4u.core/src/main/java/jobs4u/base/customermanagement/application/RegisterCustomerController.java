package jobs4u.base.customermanagement.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.application.UserManagementService;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import jobs4u.base.usermanagement.domain.BaseRoles;

import java.util.Optional;

public class RegisterCustomerController {
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final CustomerManagementService custSvc = new CustomerManagementService();


    public boolean registerNewCustomer(String companyName, String address, String customerCode,
                                       String email) {
        Optional<SystemUser> customerManager = authz.loggedinUserWithPermissions(BaseRoles.ADMIN, BaseRoles.CUSTOMER_MANAGER);

        if(customerManager.isPresent()){
            custSvc.registerNewCustomer(companyName, address, customerCode, customerManager.get(), email);
            return true;
        }
        return false;
    }
}
