package jobs4u.base.usermanagement.application;

import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.application.UserManagementService;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import jobs4u.base.usermanagement.domain.BaseRoles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListUserService {

    private final UserManagementService userSvc = AuthzRegistry.userService();


    /**
     * This method returns the users of the BackOffice Application.
     *
     * @return systemUsers of the backoffice
     */
    public Iterable<SystemUser> backofficeUsers() {
        return filterBackofficeUsers(userSvc.allUsers());


    }

    /**
     * This method aims to filter the list of system users to include only those who are permitted to access the backoffice application.
     *
     * @param systemUsers - all users of the system.
     * @return systemUsers of the backoffice
     */
    private Iterable<SystemUser> filterBackofficeUsers(Iterable<SystemUser> systemUsers) {
        List<SystemUser> backofficeUsers = new ArrayList<>();

        for (SystemUser user : systemUsers) {
            if (user.hasAny(BaseRoles.CUSTOMER_MANAGER, BaseRoles.OPERATOR, BaseRoles.LANGUAGE_ENGINEER)) {
                backofficeUsers.add(user);
            }
        }
        return backofficeUsers;
    }
}
