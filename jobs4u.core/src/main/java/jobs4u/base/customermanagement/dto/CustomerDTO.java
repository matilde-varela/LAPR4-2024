package jobs4u.base.customermanagement.dto;

public class CustomerDTO {
    private final String custumerCode;
    private final String companyName;

    public CustomerDTO(String custumerCode, String companyName) {
        this.custumerCode = custumerCode;
        this.companyName = companyName;
    }

    public String companyName(){
        return this.companyName;
    }

    public String customerCode(){
        return this.custumerCode;
    }

    @Override
    public String toString() {
        return String.format("%s | %s", companyName, custumerCode);
    }
}
