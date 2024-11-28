# US 1020

## 1. Context

This is the first time this user story is being requested.

## 2. Requirements

**US 1020** As {Customer Manager}, I want to publish the results of the selection of candidates for a job opening, so
that candidates and customer are notified by email of the result.

**Acceptance Criteria:**

- 1020.1. The number of selected applications/candidates should not surpass the number of vacancies for the job opening.

- 1020.2. The system is required to notify both the customer linked to the job opening and the candidate about the
  selected application.

- 1020.3. The first N applications, where N is the number of vacancies, are the one to be selected.

- 1020.4. The application status of all the job openings in the rank must be updated.

**Dependencies/References:**

We understand that this requirement pertains to reference NFR11, which specifies that the solution should be deployed
across multiple network nodes. Specifically, it is expected that the relational database server and the Follow Up Server
be deployed on nodes separate from localhost, preferably in the cloud. Additionally, the Follow Up Server must handle
e-mail notification tasks in the background.

**Client Clarifications:**

> **Question:** Regarding the sending of email notifications, is it necessary to keep a record of this?
>
>
> **Answer:** The document does not specifically address this matter. However, from Jobs4u's perspective, it seems
> appropriate to have this information recorded.

> **Question:** What is the format for this publication?
>
>
> **Answer:** The publication refers to informing candidates and the client, via email. Candidates who are selected must
> receive an email indicating that they have been selected for their application for the job opening and will be
> contacted
> by the company. As far as the company is concerned, it must receive an email with the list of selected candidates,
> which
> must include the candidate's name and contact details.


> **Question:** Should the email be in English or Portuguese?
>
>
> **Answer:** It can be in Portuguese or English.

> **Question:** Should we assume that the first N candidates in the ranking (where N is the number of job vacancies) are
> chosen, or should we allow the customer manager to select the N candidates?
>
>
> **Answer:** The first option (using the results from US1013).

## 3. Analysis

It is understood that for this functionality, the customer manager must select a job opening to publish the results.
Additionally, the first N candidates, where N is the number of job vacancies, are chosen to fill the positions for
that
job opening.

Furthermore, candidates can only be selected for job openings whose recruitment process is in the
Result phase.

System-wise, after selection, emails will be sent to both the chosen candidates and the customer. The email to the
candidate will inform them that their application was selected and that they will be contacted by the company. For the
company/customer, the email must contain the list of selected candidates, including their names and contact details.

### Domain Model

![Domain Model - US 1020](analysis-diagram/domain-model.svg)

## 4. Design

### 4.1. Realization

![Sequence Diagram](design-diagrams/sequence-diagram.svg)

#### 4.1.1. Getting job opening in the result phase

![Sequence Diagram](design-diagrams/sequence-diagram-job-opening-in-result.svg)

#### 4.1.2. Notifying candidates of the application status change

![Sequence Diagram](design-diagrams/sequence-diagram-application-status-changed.svg)

### 4.2. Class Diagram

![Class Diagram](design-diagrams/class-diagram.svg)

### 4.3. Applied Patterns

* **DTO**
* **Repository**
* **Service**
* **MVC**
* **Observer**

> **MVC**
>
> **Justification:**
>
> The MVC pattern was employed to divide the system into three distinct parts: model, view, and controller, each
> responsible for a specific aspect of the system’s functionality. This separation of concerns enhances maintainability
> and extensibility, as changes to one part do not require changes to the others.

> **Repository Pattern**
> * JobOpeningRepository
> * ApplicationRepository
> * CustomerRepository
> * NotificationRepository
> * RankRepository
>
> **Justification:**
>
> The repositories were utilized to retrieve persisted instances based on specific criteria and to save new instances
> after modifications were made.

> **DTO**
> * JobOpeningDto
>
> **Justification:**
>
> We opted for DTOs due to the significant amount of domain information required for this functionality. Recognizing the
> benefits of encapsulation and layer decoupling offered by DTOs, we concluded that applying this pattern was
> helpful
> in this context.

> **Service Pattern**
> * JobOpeningManagementService
> * JobOpeningDtoService
> * AuthorizationService
> * NotificationManagementService
> * FollowUpConnectionService
>
> **Justification:**
>
> The services were used to gather job openings to display them to the user, essentially
> listing them. Recognizing the potential for this functionality to be used in various use cases, we opted to
> develop a service with the primary responsibility of: obtaining the persisted instances using their repository and
> using
> the DtoService to transform these instances into DTOs.
> The authorization service was used to verify the roles of the logged-in user.

> **Observer**
> * ApplicationStatusChangedEvent
> * ApplicationStatusChangedWatchDog
> * NotifyCandidateOnApplicationStatusChangedController
>
> **Justification:**
>
>
> All the mentioned objects are components of the implemented observer pattern. This pattern was employed to ensure that
> when results are published, emails are sent to candidates and customers, and notifications are created for changes in
> application status, allowing candidates to stay informed about new updates.

## 5. Implementation

### PublishResultJobOpeningController

````
public void publishResultForJobOpening(JobOpeningDTO jobOpeningDTO, String userPassword) {
    Optional<SystemUser> customerManagerOpt = authz.loggedinUserWithPermissions(BaseRoles.CUSTOMER_MANAGER);

    if (customerManagerOpt.isPresent()) {
        publishResults(jobOpeningDTO, customerManagerOpt.get().email(), userPassword);
    } else {
        throw new IllegalArgumentException("Couldn't retrieve logged in user.");
    }
}

private void publishResults(JobOpeningDTO jobOpeningDTO, EmailAddress customerManager, String userPassword) {
    Optional<Rank> r = rankRepository.ofIdentity(new JobReference(jobOpeningDTO.getJobReference()));

    if (r.isEmpty()) {
        throw new IllegalArgumentException("Coudln't obtain the rank for the selected job opening.");
    }

    Rank rank = r.get();
    final List<Candidate> acceptedCandidateList = new ArrayList<>();
    String newStatus;

    for (RankOrder entry : rank.rankOrder()) {
        if (entry.numberRanked() <= jobOpeningDTO.getNumVacancies()) {
            Application app = entry.acceptApplication();
            newStatus = app.applicationStatus().getStatusDescription();
            acceptedCandidateList.add(app.candidate());
            appRepo.save(app);
            sendEmail(customerManager, entry.candidateEmail(), rank.identity(), userPassword);
        } else {
            Application app = entry.rejectApplication();
            newStatus = app.applicationStatus().getStatusDescription();
            appRepo.save(app);
        }
        dispatcher.publish(new ApplicationStatusChangedEvent(entry.candidateEmail(), rank.identity(), newStatus));
    }
    Optional<Customer> customer = customerRepository.getCustomerByCustomerCode(jobOpeningDTO.getCustomerCode());
    sendEmail(customerManager, customer.get().customerUser().email(), acceptedCandidateList, rank.identity(), userPassword);
}
````

````
private void sendEmail(EmailAddress senderEmail, EmailAddress receiverEmail, JobReference jo, String userPassword) {
    try {
        Username user = getSessionCredentials();
        Pair<Boolean, String> connection = establishConnection(user, userPassword);
        if (!connection.getKey()) {
            throw new IllegalArgumentException("Error: Could not establish connection" + connection.getValue());
        }
        String text = "Hello, this is Jobs4u, we came to tell you that your application has been accepted for the job opening: " + jo.toString();
        connectionService.sendEmail(senderEmail.toString(), receiverEmail.toString(), "JobOpening Result", text);
        FollowUpConnectionService.closeConnection();
    } catch (NoSuchElementException | IllegalArgumentException e) {
        LOGGER.error(e.getMessage());
    }
}

private void sendEmail(EmailAddress senderEmail, EmailAddress receiverEmail, List<Candidate> cand, JobReference jo, String userPassword) {
    try {
        Username user = getSessionCredentials();
        Pair<Boolean, String> connection = establishConnection(user, userPassword);
        if (!connection.getKey()) {
            throw new IllegalArgumentException("Error: Could not establish connection" + connection.getValue());
        }
        connectionService.sendEmail(senderEmail.toString(), receiverEmail.toString(), "JobOpening Result", emailInfo(jo, cand));
        FollowUpConnectionService.closeConnection();
    } catch (NoSuchElementException | IllegalArgumentException e) {
        LOGGER.error(e.getMessage());
    }
}
````

## 6. Integration/Demonstration

To use this feature, you'll need to run the script named `run-backoffice-app` and log in with Customer Manager
permissions.

Then, navigate to the _Job Opening_ menu and select option 11 - `Publish results of job opening` - to
access this feature.

Additionally, the server needs to be initiated. Since it uses the DEI local SMTP server (frodo.dei.isep.ipp.pt), it is
essential that the email addresses of the customer manager, candidates, and customers who will send and receive emails
are ISEP mailboxes (…@isep.ipp.pt).

````
+= Publish results of job opening =============================================+

Please enter your password: 
Password-1
Select a job opening to publish the results of
1. 
»» Job Reference: AVIP-1
 » Function: Back End Senior Developer
 » Contract Type: full-time
 » Work Mode: remote
 » Address: Third Street, Third, Third Town, Third District, 4510-910
 » Description: Night Guard.
 » Number of Vacancies: 6
 » Company: AVIP

0. Exit
Select an option: 
1
The results have been successfully published.
+==============================================================================+
````

## 7. Observations

This user story underwent many changes due to server incompatibilities. Initially, the plan was to use threads, but
despite implementing thread synchronization, the functionality was not working as required. Numerous modifications would
have been necessary to make it work. 

Therefore, with the deadline approaching, we decided that altering the planned
implementation would be more beneficial.

