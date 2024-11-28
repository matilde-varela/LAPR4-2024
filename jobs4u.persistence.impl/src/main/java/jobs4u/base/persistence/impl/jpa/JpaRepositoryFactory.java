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
package jobs4u.base.persistence.impl.jpa;

import jobs4u.base.Application;
import jobs4u.base.applicationmanagement.repositories.ApplicationRepository;
import jobs4u.base.candidatemanagement.repository.CandidateRepository;
import jobs4u.base.criteriamanagement.repository.CriteriaRepository;
import jobs4u.base.customermanagement.repository.CustomerRepository;
import jobs4u.base.infrastructure.persistence.RepositoryFactory;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.infrastructure.authz.repositories.impl.jpa.JpaAutoTxUserRepository;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
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
 * Created by nuno on 21/03/16.
 */
public class JpaRepositoryFactory implements RepositoryFactory {

    @Override
    public UserRepository users(final TransactionalContext autoTx) {
        return new JpaAutoTxUserRepository(autoTx);
    }

    @Override
    public UserRepository users() {
        return new JpaAutoTxUserRepository(Application.settings().getPersistenceUnitName(),
                Application.settings().getExtendedPersistenceProperties());
    }

    @Override
    public CustomerRepository customers(final TransactionalContext autoTx) {
        return new JpaCustomerRepository(autoTx);
    }

    @Override
    public CustomerRepository customers() {
        return new JpaCustomerRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public ContractTypeRepository contractTypes(final TransactionalContext autoTx) {
        return new JpaContractTypeRepository(autoTx);
    }

    @Override
    public ContractTypeRepository contractTypes() {
        return new JpaContractTypeRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public WorkModeRepository workModes(final TransactionalContext autoTx) {
        return new JpaWorkModeRepository(autoTx);
    }

    @Override
    public WorkModeRepository workModes() {
        return new JpaWorkModeRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public RequirementSpecificationRepository requirementSpecifications(final TransactionalContext autoTx) {
        return new JpaRequirementSpecificationRepository(autoTx);
    }

    @Override
    public RequirementSpecificationRepository requirementSpecifications() {
        return new JpaRequirementSpecificationRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public JobOpeningRepository jobOpenings(final TransactionalContext autoTx) {
        return new JpaJobOpeningRepository(autoTx);
    }

    @Override
    public JobOpeningRepository jobOpenings() {
        return new JpaJobOpeningRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public ApplicationRepository applications(final TransactionalContext autoTx) {
        return new JpaApplicationRepository(autoTx);
    }

    @Override
    public InterviewModelRepository interviewModels(final TransactionalContext autoTx) {
        return new JpaInterviewModelRepository(autoTx);
    }

    @Override
    public InterviewModelRepository interviewModels() {
        return new JpaInterviewModelRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public ApplicationRepository applications() {
        return new JpaApplicationRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public CandidateRepository candidates(final TransactionalContext tx) {
        return new JpaCandidateRepository(tx);
    }

    @Override
    public CandidateRepository candidates() {
        return new JpaCandidateRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public CriteriaRepository criteria(final TransactionalContext autoTx) {
        return new JpaCriteriaRepository(autoTx);
    }

    @Override
    public CriteriaRepository criteria() {
        return new JpaCriteriaRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public RecruitmentProcessRepository recruitmentProcesses(final TransactionalContext autoTx) {
        return new JpaRecruitmentProcessRepository(autoTx);
    }

    @Override
    public RecruitmentProcessRepository recruitmentProcesses() {
        return new JpaRecruitmentProcessRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public RankRepository ranks(TransactionalContext autoTx) {
        return new JpaRankRepository(autoTx);
    }

    @Override
    public RankRepository ranks() {
        return new JpaRankRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public NotificationRepository notifications(TransactionalContext autoTx) {
        return new JpaNotificationRepository(autoTx);
    }

    @Override
    public NotificationRepository notifications() {
        return new JpaNotificationRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public TransactionalContext newTransactionalContext() {
        return JpaAutoTxRepository.buildTransactionalContext(Application.settings().getPersistenceUnitName(),
                Application.settings().getExtendedPersistenceProperties());
    }

}
