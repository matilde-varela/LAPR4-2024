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
package jobs4u.base.infrastructure.persistence;

import jobs4u.base.applicationmanagement.repositories.ApplicationRepository;
import jobs4u.base.candidatemanagement.repository.CandidateRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import jobs4u.base.criteriamanagement.repository.CriteriaRepository;
import jobs4u.base.customermanagement.repository.CustomerRepository;
import jobs4u.base.contracttypemanagement.repository.ContractTypeRepository;
import jobs4u.base.jobopeningmanagement.repositories.JobOpeningRepository;
import jobs4u.base.notificationmanagement.repositories.NotificationRepository;
import jobs4u.base.rankmanagement.persistence.RankRepository;
import jobs4u.base.workmodemanagement.repository.WorkModeRepository;
import jobs4u.base.recruitmentprocessmanagement.repository.RecruitmentProcessRepository;
import jobs4u.base.interviewmodelmanagement.repositories.InterviewModelRepository;
import jobs4u.base.requirementsmanagement.repositories.RequirementSpecificationRepository;

/**
 * @author Paulo Gandra Sousa
 *
 */
public interface RepositoryFactory {

    /**
     * factory method to create a transactional context to use in the repositories
     *
     * @return
     */
    TransactionalContext newTransactionalContext();

    /**
     *
     * @param autoTx
     *            the transactional context to enrol
     * @return
     */
    UserRepository users(TransactionalContext autoTx);

    /**
     * repository will be created in auto transaction mode
     *
     * @return
     */
    UserRepository users();

    /**
     *
     * @param autoTx
     *            the transactional context to enroll
     * @return
     */
    CustomerRepository customers(TransactionalContext autoTx);

    /**
     * repository will be created in auto transaction mode
     *
     * @return
     */
    CustomerRepository customers();

    /**
     *
     * @param autoTx
     *            the transactional context to enroll
     * @return
     */
    ContractTypeRepository contractTypes(TransactionalContext autoTx);

    /**
     * repository will be created in auto transaction mode
     *
     * @return
     */
    ContractTypeRepository contractTypes();


    /**
     *
     * @param autoTx
     *            the transactional context to enroll
     * @return
     */
    WorkModeRepository workModes(TransactionalContext autoTx);

    /**
     * repository will be created in auto transaction mode
     *
     * @return
     */
    WorkModeRepository workModes();


    /**
     *
     * @param autoTx
     *            the transactional context to enroll
     * @return
     */
    RequirementSpecificationRepository requirementSpecifications(TransactionalContext autoTx);

    /**
     * repository will be created in auto transaction mode
     *
     * @return
     */
    RequirementSpecificationRepository requirementSpecifications();


    /**
     *
     * @param autoTx
     *            the transactional context to enroll
     * @return
     */
    JobOpeningRepository jobOpenings(TransactionalContext autoTx);

    /**
     * repository will be created in auto transaction mode
     *
     * @return
     */
    JobOpeningRepository jobOpenings();


    /**
     *
     * @param autoTx
     *            the transactional context to enroll
     * @return
     */
    InterviewModelRepository interviewModels(TransactionalContext autoTx);

    /**
     * repository will be created in auto transaction mode
     *
     * @return
     */
    InterviewModelRepository interviewModels();

    /**
     *
     * @param autoTx
     *            the transactional context to enroll
     * @return
     */
    ApplicationRepository applications(TransactionalContext autoTx);

    /**
     * repository will be created in auto transaction mode
     *
     * @return
     */
    ApplicationRepository applications();


    /**
     *
     * @param tx
     *            the transactional context to enroll
     * @return
     */
    CandidateRepository candidates(TransactionalContext tx);

    /**
     * repository will be created in auto transaction mode
     *
     * @return
     */
    CandidateRepository candidates();



    /**
     *
     * @param autoTx
     *            the transactional context to enroll
     * @return
     */
    CriteriaRepository criteria(TransactionalContext autoTx);

    /**
     * repository will be created in auto transaction mode
     *
     * @return
     */
    CriteriaRepository criteria();

    /**
     *
     * @param autoTx
     *            the transactional context to enroll
     * @return
     */
    RecruitmentProcessRepository recruitmentProcesses(TransactionalContext autoTx);

    /**
     * repository will be created in auto transaction mode
     *
     * @return
     */
    RecruitmentProcessRepository recruitmentProcesses();

    /**
     *
     * @param autoTx
     *            the transactional context to enroll
     * @return
     */
    NotificationRepository notifications(TransactionalContext autoTx);

    /**
     * repository will be created in auto transaction mode
     *
     * @return
     */
    NotificationRepository notifications();


    /**
     *
     * @param autoTx
     *            the transactional context to enroll
     * @return
     */
    RankRepository ranks(TransactionalContext autoTx);

    /**
     * repository will be created in auto transaction mode
     *
     * @return
     */
    RankRepository ranks();


}
