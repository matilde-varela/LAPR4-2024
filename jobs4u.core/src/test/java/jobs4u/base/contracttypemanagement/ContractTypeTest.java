package jobs4u.base.contracttypemanagement;

import jobs4u.base.contracttypemanagement.domain.ContractType;
import org.junit.Test;

import static org.junit.Assert.assertThrows;

public class ContractTypeTest {

    @Test
    public void ensureContractTypeNullIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new ContractType(null));
    }

    @Test
    public void ensureContractTypeEmptyIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new ContractType(""));
    }

}