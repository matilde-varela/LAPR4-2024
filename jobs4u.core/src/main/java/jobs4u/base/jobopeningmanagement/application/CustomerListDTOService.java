package jobs4u.base.jobopeningmanagement.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.validations.Preconditions;
import jobs4u.base.customermanagement.domain.Customer;
import jobs4u.base.customermanagement.dto.CustomerDTO;
import jobs4u.base.usermanagement.domain.BaseRoles;

import java.util.ArrayList;
import java.util.List;

public class CustomerListDTOService {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    public List<CustomerDTO> convertToDTO(List<Customer> listToDisplay){
        Preconditions.noneNull(listToDisplay);
        Preconditions.nonEmpty(listToDisplay);

        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.CUSTOMER_MANAGER);

        List<CustomerDTO> dtoList = new ArrayList<>();
        for (Customer customer : listToDisplay){
            dtoList.add(customer.toDTO());
        }
        return dtoList;
    }
}
