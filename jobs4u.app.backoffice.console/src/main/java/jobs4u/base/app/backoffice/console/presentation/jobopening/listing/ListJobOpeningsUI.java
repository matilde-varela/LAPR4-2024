package jobs4u.base.app.backoffice.console.presentation.jobopening.listing;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractListUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.time.domain.model.DateInterval;
import eapli.framework.visitor.Visitor;
import jobs4u.base.criteriamanagement.dto.CriteriaDTO;
import jobs4u.base.customermanagement.dto.CustomerDTO;
import jobs4u.base.jobopeningmanagement.application.ListJobOpeningsController;
import jobs4u.base.jobopeningmanagement.dto.JobOpeningDTO;

import java.util.*;

public class ListJobOpeningsUI extends AbstractListUI<JobOpeningDTO> {

    private final ListJobOpeningsController controller = new ListJobOpeningsController();

    private CriteriaDTO criteriaDTO;
    private CustomerDTO customerDTO;
    private DateInterval dateInterval;

    @Override
    public boolean show() {
        criteriaDTO = new CriteriaDTO("no criteria");
        try {
            super.show();
            String answer;
            do {
                answer = Console.readLine("Would you like to select filtering criteria? [y/n]").trim().toLowerCase();
                if (!answer.equals("y") && !answer.equals("n")) {
                    throw new InputMismatchException("Invalid input. Please enter 'y' or 'n'.");
                }
                if (answer.equals("y")) {
                    criteriaDTO = showAndSelectCriteria();
                    switch (criteriaDTO.getDenomination()) {
                        case "Status [STARTED]":
                            super.show();
                            break;
                        case "Company Name":
                            customerDTO = showAndSelectCustomer("Company Name");
                            super.show();
                            break;
                        case "Customer Code":
                            customerDTO = showAndSelectCustomer("Customer Code");
                            super.show();
                            break;
                        case "Time Interval":
                            dateInterval = buildNewDateInterval();
                            super.show();
                            break;
                        default:
                            break;
                    }
                }else {
                    super.show();
                }
                answer = Console.readLine("Finish listing? [y/n]").trim().toLowerCase();
                if (!answer.equals("y") && !answer.equals("n")) {
                    throw new InputMismatchException("Invalid input. Please enter 'y' or 'n'.");
                }
            } while (!answer.equals("y"));
            return false;
        } catch (NoSuchElementException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private DateInterval buildNewDateInterval() {
        Calendar start = Console.readCalendar("Provide the start date [DD-MM-YYYY]:");
        Calendar end = Console.readCalendar("Provide the end date [DD-MM-YYYY]:");
        return new DateInterval(start, end);
    }

    private CriteriaDTO showAndSelectCriteria() {
        List<CriteriaDTO> criteria = this.controller.getCriteria();
        SelectWidget<CriteriaDTO> criteriaSelector = new SelectWidget<>("Select a criteria to filter the list of job openings:",
                criteria);

        criteriaSelector.show();
        return criteriaSelector.selectedElement();
    }

    @Override
    public String headline() {
        return "List Job Openings";
    }

    @Override
    protected String emptyMessage() {
        return "No data found.";
    }

    @Override
    protected Iterable<JobOpeningDTO> elements() {
        switch (criteriaDTO.getDenomination()){
            case "Status [STARTED]":
                return controller.filterJobOpeningList("STARTED");
            case "Company Name":
                return controller.filterJobOpeningList(customerDTO);
            case "Customer Code":
                return controller.filterJobOpeningList(customerDTO.customerCode());
            case "Time Interval":
                return controller.filterJobOpeningList(dateInterval);
            case "no criteria":
                return controller.backofficeJobOpenings();
            default:
                throw new NoSuchElementException("There is no criteria with defined designation");
        }
    }

    @Override
    protected Visitor<JobOpeningDTO> elementPrinter() {
        return new JobOpeningPrinter();
    }

    @Override
    protected String elementName() {
        return "Job Opening";
    }

    @Override
    protected String listHeader() {
        return "Listing Job Openings";
    }

    private CustomerDTO showAndSelectCustomer(String distinction) {
        List<CustomerDTO> customerDTOS = this.controller.getCustomersList();
        if (customerDTOS.isEmpty()){
            throw new NoSuchElementException("There are no customers to be associated with a job opening.");
        }
        List<String> customerInfo = new ArrayList<>();
        if (distinction.equals("Company Name")){
            for (CustomerDTO dto : customerDTOS){
                customerInfo.add(dto.companyName());
            }
        }else {
            for (CustomerDTO dto : customerDTOS){
                customerInfo.add(dto.customerCode());
            }
        }

        SelectWidget<String> costumerSelector = new SelectWidget<>("Select a customers that was assigned to you:",
                customerInfo);
        costumerSelector.show();
        String answer = costumerSelector.selectedElement();

        if (distinction.equals("Company Name")){
            for (CustomerDTO dto : customerDTOS){
                if (dto.companyName().equals(answer)){
                    return dto;
                }
            }
        }else {
            for (CustomerDTO dto : customerDTOS){
                if (dto.customerCode().equals(answer)){
                    return dto;
                }
            }
        }
        throw new NoSuchElementException("No customer selected.");
    }
}
