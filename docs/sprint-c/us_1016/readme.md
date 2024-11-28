# US 1016

## 1. Context

*This is the first time this user story is being requested.*

## 2. Requirements

**US 1016:** As Customer Manager, I want the system to notify candidates, by email, of the result of the verification process.

**Acceptance Criteria:**

- 1016.1. The user must be logged with customer manager role;

- 1016.2. If an error happens in the notification, doesn't invalidate the result of the verification process 

**Client Clarifications**

> **Question:** I would like to know the format of the following Message to Send to the client, could be Algo do Genero: "Dear [Candidate’s Name], hope this email finds you well. As a Customer Manager, I wanted to inform you about the outcome of the verification process for the position you applied for. After careful consideration of your application and qualifications, I am pleased to inform you that you have successfully passed the verification process. Congratulations! Your application met our initial criteria and we are impressed with your qualifications and experience. We will proceed to the next phase of the selection process, which may include additional interviews or evaluations. We will contact you shortly with more details about the next steps. Thank you for your interest in our company and for taking the time to apply for the position. We appreciate your patience throughout the process. If you have any questions or need further assistance, please feel free to contact us. [Your Name] Customer Manager [Your Company Name]" I would like to know the most important information when Notify the candidate, should appear the name of the Customer manager, the job Reference, the name of the candidate. And whether the email should be in English or Portuguese.
>
> **Answer:** It can be as it presents. It can be in Portuguese or English.

> **Question:** I want to know when the client says "verification process" is the same about the screening phase.
>
> **Answer:** Yes.

> **Question:** This user story has a functional dependency with 1015. I would like to know if an error occurs, do I need to delete what happened in US 1015, as if it were a transaction?
>
> **Answer:** The process of notification (US1016) must be done after the verification (US1015) but an error in the notification does not invalidate the “results” of the verification process.

> **Question:** what is the process through which this notification is generated? After evaluation of the Requirement Specification module, it generates an "Approved" or "Rejected" result. This result automatically triggers a notification to the candidate or it is the Customer Manager who has the responsibility to inform the candidate through the system of the verification result (e.g. after a negative result is generated, the Customer Manager will in the system reject the candidate to be sent the email)?
> 
> **Answer:** This is the second option that presents. The US1015 allows the Customer Manager to invoke the requirements verification process. After that all applications must be accepted or refused. It is then possible for the Customer Manager to invoke the notification through US1016.

## 3. Analysis

*This functionality is for the Customer Manager, so the user needs to be authenticated first to be able to send an email to the candidate.*
The system should send the email, once the verification is completed.

**System Sequence Diagram:**

![SSD-US-1016](ssd/ssd-us-1016.svg)



## 4. Design
The principal function is to send a notification to candidates who doesn't have been notified, the input for the Customer Manager consists of:
* Select send the email

After successfully submitting this information, the system should send the notification and update the state of application notification.
### 4.1.1 Domain Model
![Domain model](dm/domain-model-us-1016.svg)

### 4.1. Realization

![Sequence diagram](sd/sd-us-1016.svg)

### 4.2. Class Diagram

![a class diagram](cd/cd-us-1016.svg)


### 4.3. Applied Patterns
* **DTO**
* **Repository**
* **Service**

> **Repository Pattern**
> * ApplicationRepository
> * JobOpeningRepository
>
> **Justifications**
>
>The repositories were employed to persist applications and job openings, as well as to reconstruct objects from the
persistence.
> 

> **Service Pattern**
> * AuthorizationService
> * JobOpeningManagementService
> * JobOpeningDTOService
> * FollowUpConnectionService
>
> **Justifications**
>
> The AuthorizationService, pre-existing service within the Eapli.Framework were used here
> to retrieve the logged-in user with Customer Manager roles.
>
> The JobOpeningManagementService is employed to register job openings and get all the job openings.
>
> The FollowUpConnectionService is employed to establish the connection with the email server and send the email
> 
> The mentioned services were developed because the functionalities they offer will be utilized across multiple use
> cases.

> **DTO**
> * JobOpeningDTO
>
> **Justifications**
>
> We choose DTOs because we have a big amount of domain data required for this functionality. 
> Recognizing the benefits of encapsulation and layer decoupling offered by DTOs, we decided 
> applying this pattern to our project.
> 

### 4.4. Tests

**Domain tests were implemented in other user stories**

## 5. Implementation

### SendNotificationEmailController

```
    public Iterable<JobOpeningDTO> getJobOpeningsList() {
        authorizationService.ensureAuthenticatedUserHasAnyOf(BaseRoles.CUSTOMER_MANAGER, BaseRoles.ADMIN);

        return jobOpeningManagementService.activeJobOpenings();
    }
    private Pair<Boolean, String> establishConnection(Username username, String password) {
        return followUpConnectionService.establishConnection(username, password);
    }
    public boolean sendEmail(JobOpeningDTO jobOpeningDTO, String userPassword) {
        authorizationService.ensureAuthenticatedUserHasAnyOf(BaseRoles.CUSTOMER_MANAGER, BaseRoles.ADMIN);
        try {
            //get job opening and applications list
            JobOpening jobOpening= jobOpeningManagementService.getJobOpening(jobOpeningDTO);
            Iterable<Application> applications = jobOpening.getApplications();
            //get customer manager
            Optional<SystemUser> customerManager = authorizationService.loggedinUserWithPermissions(BaseRoles.CUSTOMER_MANAGER);

            String customerManagerEmail ="";
            if (customerManager.isPresent()){
                customerManagerEmail = customerManager.get().email().toString();
            }
            //Conection to server
            Username user = customerManager.get().username();
            Pair<Boolean, String> connection = establishConnection(user, userPassword);

            if (!connection.getKey()) {
                throw new IllegalArgumentException("Error: Could not establish connection" + connection.getValue());
            }
            for (Application application : applications){
                if (application.requirementResult()!= null){
                    followUpConnectionService.sendEmail(customerManagerEmail,application.candidate().email().toString(),"Verification Process Result","Hello, this email is intended to inform you that the result of your verification process was: "+application.requirementResult()+". Best Regards.");
                }
            }
        } catch (NoSuchElementException | IllegalArgumentException e) {
            System.out.println("Error");
        }
        return true;
    }
```
### JobOpeningManagementService

```
 public Iterable<JobOpeningDTO> activeJobOpenings() {
        return dtoSvc.convertToDTO(jobOpeningRepository.findAllJobOpeningsNotStarted());
    }
```
### FollowUpConnectionService

```
    public boolean sendEmail(String senderEmail, String receiverEmail, String topic, String info) {
        //send email request with dataDTO
        try {
            DataDTO dataDTO = new DataDTO(FollowUpRequestCodes.EMAIL.getCode());
            List<String> params = new ArrayList<>();
            params.add(senderEmail);
            params.add(receiverEmail);
            params.add(topic);
            params.add(info);

            for (String parameter : params){
                byte[] serialized = SerializationUtil.serialize(parameter);
                dataDTO.addDataBlock(serialized.length, serialized);
            }

            byte[] message = dataDTO.toByteArray();
            sOut.writeInt(message.length);
            sOut.write(message);
            sOut.flush();
            return receiveEmptyResponse();

        } catch (IOException e) {
            throw new RuntimeException(e + "\n Unable to send email request.\n");
        }
    }
```
## 6. Integration/Demonstration
To execute this functionality it is necessary to run the script named `run-backoffice-app` and log in with Customer Manager permissions
after it, must select the menu `Job Opening` followed by `Send Notification Email with Requirement Specification result`.

