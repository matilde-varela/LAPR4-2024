package jobs4u.base.usermanagement.application;

import eapli.framework.infrastructure.authz.application.PasswordPolicy;
import jobs4u.base.usermanagement.domain.BasePasswordPolicy;
import jobs4u.base.usermanagement.domain.RandomPassword;

public class GeneratePasswordService {

    private final PasswordPolicy policy;

    public GeneratePasswordService() {
        this.policy = new BasePasswordPolicy();
    }

    public String generatePassword() {
        return new RandomPassword().toString();
    }

}
