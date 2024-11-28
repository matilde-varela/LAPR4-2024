/*
 * Copyright (c) 2013-2024 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package jobs4u.base.persistence.impl.inmemory;

import jobs4u.base.applicationmanagement.repositories.ApplicationRepository;
import jobs4u.base.candidatemanagement.repository.CandidateRepository;
import jobs4u.base.criteriamanagement.repository.CriteriaRepository;
import jobs4u.base.customermanagement.repository.CustomerRepository;
import jobs4u.base.infrastructure.bootstrapers.BaseBootstrapper;
import jobs4u.base.infrastructure.persistence.RepositoryFactory;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.infrastructure.authz.repositories.impl.inmemory.InMemoryUserRepository;
import jobs4u.base.contracttypemanagement.repository.ContractTypeRepository;
import jobs4u.base.jobopeningmanagement.repositories.JobOpeningRepository;
import jobs4u.base.rankmanagement.persistence.RankRepository;
import jobs4u.base.notificationmanagement.repositories.NotificationRepository;
import jobs4u.base.workmodemanagement.repository.WorkModeRepository;
import jobs4u.base.recruitmentprocessmanagement.repository.RecruitmentProcessRepository;
import jobs4u.base.interviewmodelmanagement.repositories.InterviewModelRepository;
import jobs4u.base.requirementsmanagement.repositories.RequirementSpecificationRepository;

/**
 *
 * Created by nuno on 20/03/16.
 */
public class InMemoryRepositoryFactory implements RepositoryFactory {

    static {
        // only needed because of the in memory persistence
        new BaseBootstrapper().execute();
    }

    @Override
    public UserRepository users(final TransactionalContext tx) {
        return new InMemoryUserRepository();
    }

    @Override
    public UserRepository users() {
        return users(null);
    }

    @Override
    public CustomerRepository customers(TransactionalContext tx) {
        return new InMemoryCustomerRepository();
    }

    @Override
    public CustomerRepository customers() {
        return customers(null);
    }

    @Override
    public ContractTypeRepository contractTypes(TransactionalContext tx) {
        return new InMemoryContractTypeRepository();
    }

    @Override
    public ContractTypeRepository contractTypes() {
        return contractTypes(null);
    }

    @Override
    public WorkModeRepository workModes(TransactionalContext tx) {
        return new InMemoryWorkModeRepository();
    }

    @Override
    public WorkModeRepository workModes() {
        return workModes(null);
    }

    @Override
    public RequirementSpecificationRepository requirementSpecifications(TransactionalContext tx) {
        return new InMemoryRequirementSpecificationRepository();
    }

    @Override
    public RequirementSpecificationRepository requirementSpecifications() {
        return requirementSpecifications(null);
    }

    @Override
    public JobOpeningRepository jobOpenings(TransactionalContext tx) {
        return new InMemoryJobOpeningRepository();
    }

    @Override
    public JobOpeningRepository jobOpenings() {
        return jobOpenings(null);
    }

    @Override
    public CriteriaRepository criteria(TransactionalContext tx) {
        return new InMemoryCriteriaRepository();
    }

    @Override
    public CriteriaRepository criteria() {
        return criteria(null);
    }

    @Override
    public RecruitmentProcessRepository recruitmentProcesses(TransactionalContext autoTx) {
        return new InMemoryRecruitmentProcessRepository();
    }

    @Override
    public RecruitmentProcessRepository recruitmentProcesses() {
        return recruitmentProcesses(null);
    }

    @Override
    public NotificationRepository notifications(TransactionalContext autoTx) {
        return new InMemoryNotificationRepository();
    }

    @Override
    public NotificationRepository notifications() {
        return notifications(null);
    }

    @Override
    public InterviewModelRepository interviewModels(TransactionalContext autoTx) {
        return new InMemoryInterviewModelRepository();
    }

    @Override
    public InterviewModelRepository interviewModels() { return interviewModels(null); }

    @Override
    public ApplicationRepository applications(TransactionalContext autoTx) {
        return new InMemoryApplicationRepository();
    }

    @Override
    public ApplicationRepository applications() {
        return applications(null);
    }

    @Override
    public CandidateRepository candidates(TransactionalContext tx) {return new InMemoryCandidateUserRepository();}

    @Override
    public CandidateRepository candidates(){ return  candidates(null);}

    @Override
    public RankRepository ranks(TransactionalContext tx) {return new InMemoryRankRepository();}

    @Override
    public RankRepository ranks(){ return  ranks(null);}

    @Override
    public TransactionalContext newTransactionalContext() {
        // in memory does not support transactions...
        return null;
    }

}
