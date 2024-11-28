package jobs4u.base.candidatemanagement.application;

import eapli.framework.domain.events.DomainEvent;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.application.UserManagementService;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.pubsub.EventPublisher;
import eapli.framework.infrastructure.pubsub.impl.inprocess.service.InProcessPubSub;
import eapli.framework.time.util.CurrentTimeCalendars;
import jobs4u.base.applicationmanagement.domain.Application;
import jobs4u.base.applicationmanagement.dto.ApplicationDTO;
import jobs4u.base.candidatemanagement.domain.Candidate;
import jobs4u.base.candidatemanagement.domain.PhoneNumber;
import jobs4u.base.candidatemanagement.domain.events.NewCandidateUserRegisteredEvent;
import jobs4u.base.candidatemanagement.dto.CandidateDTO;
import jobs4u.base.candidatemanagement.repository.CandidateRepository;
import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.jobopeningmanagement.domain.JobOpening;
import jobs4u.base.usermanagement.application.GeneratePasswordService;
import jobs4u.base.usermanagement.domain.BaseRoles;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CandidateManagementService {
    private final GeneratePasswordService passwordService = new GeneratePasswordService();
    private final CandidateRepository candidateRepository= PersistenceContext.repositories().candidates();
    private final UserManagementService userManagementService = AuthzRegistry.userService();
    private final ListCandidatesDTOService listCandidatesDTOService = new ListCandidatesDTOService();
    private final CandidateDTOService candidateDTOService = new CandidateDTOService();
    private final EventPublisher dispatcher = InProcessPubSub.publisher();

    public void registerCandidate(String name, String email, PhoneNumber phoneNumber) {
        String password = passwordService.generatePassword();

        final Set<Role> roles = new HashSet<>();
        roles.add(BaseRoles.CANDIDATE_USER);

        SystemUser sysUser = userManagementService.registerNewUser(email, password, name,"Candidate",email, roles);

        final DomainEvent event = new NewCandidateUserRegisteredEvent(sysUser,phoneNumber);
        dispatcher.publish(event);
    }

    public List<Candidate> getCandidatesList() {
        Iterable<Candidate> candidatesList = candidateRepository.findAll();
        //Transformar Iterable em List
        List<Candidate> candidatesListOrdered = StreamSupport.stream(candidatesList.spliterator(),false).collect(Collectors.toList());

        candidatesListOrdered.sort(Comparator.comparing(Candidate::email));
        return candidatesListOrdered;
    }

    public Iterable<CandidateDTO> getCandidatesListDTO() {
        Iterable<Candidate> candidatesList = candidateRepository.findAll();
        return candidateDTOService.convertToDTO(candidatesList);
    }

    public boolean alreadyExits(String phoneNumber){
        return candidateRepository.checksIfExits(new PhoneNumber("+351", phoneNumber));
    }

    public Optional<Candidate> getCandidateByPhoneNumber(String phoneNumber){
        return candidateRepository.findByPhoneNumber(new PhoneNumber("+351", phoneNumber));
    }
    public Iterable<CandidateDTO> activeCandidates() {
        Iterable<Candidate> candidates = this.candidateRepository.findByActive(true);
        return candidateDTOService.convertToDTO(candidates);
    }

    public Iterable<CandidateDTO> deactivatedCandidates() {
        Iterable<Candidate> candidates = this.candidateRepository.findByActive(false);
        return candidateDTOService.convertToDTO(candidates);
    }

    public Iterable<CandidateDTO> activeCandidatesDTO() {
        return this.candidateDTOService.convertToDTO(this.candidateRepository.findByActive(true));
    }

    public List<CandidateDTO> getCandidatesFromApplications(Set<Application> applicationList) {
        List <Candidate> candidateList = new ArrayList<>();

        for (Application a : applicationList) {
            candidateList.add(a.candidate());
        }

        return listCandidatesDTOService.toDTO(candidateList);
    }

}
