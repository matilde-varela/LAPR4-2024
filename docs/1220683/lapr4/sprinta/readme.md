# 1220683 - Matilde Varela - Sprint A - Self Assessment

**This section is mandatory for students of LAPR4**

## Engineering Process


| Level            | Description                                                                                                                                                                                                                                                                                                                     |
|------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 0 - Unacceptable | Did not use                                                                                                                                                                                                                                                                                                                     |
| 1 – Attempt	     | The engineering process is very incipient, with almost no evidence of technical documentation or many errors.                                                                                                                                                                                                                   |
| 2 – Many Defects | 	There is already a significant set of engineering artefacts, but the engineering process has evidence of significant errors (e.g. incorrect order of activities, lack of justification when moving between activities/phases of the process) or significant flaws in good practices and techniques that students should apply. |
| 3 – Some Defects | 	There is evidence of a globally correct engineering process, although there may be small flaws in documentation or some activities with errors or missing (e.g. lack of testing) as well as small flaws in the application of good practices and techniques. There may be some inconsistency between code and design.          |
| 4 – Correct      | 	The engineering process is correct. Code and design are completely aligned. Failures in the application of good practices and techniques are almost meaningless.                                                                                                                                                               |
| 5 - Exceptional  | 	The engineering process is correct (order of activities). Code and design are completely aligned. Very good justification of the process followed/applied. Application of good practices and techniques is exemplary.                                                                                                          |

**Selected Level:** 4

**Justification and Evidences:**

> There was an initial commit to correct a few things within the YAML file of the project. After that, the documentation was
elaborated, but not concluded. This happened because, due to the nature of this functionality, it was not possible to plan and provide
test before implementing it, due to the fact that it is only possible to test this User Story after a commit has been done.
>
> Because of these things, it was not possible to follow the usual order of activities. Some elements of certain phases were not
applicable to this functionality, like class diagrams, sequence diagrams and rationales.
>
> The code in the YAML file is align with the design, and any divergence is explained in the user story's **readme** file.
>
> All the evidences are on the readme file and on the commits of the repository, available on GitHub.

## Issues and Version Control

| Level              | Description                                                                                                                                                                        |
|--------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 0 –  Unacceptable	 | Did not use                                                                                                                                                                        |
| 1 – Attempt	       | Very sparse use of the repository.                                                                                                                                                 |
| 2 – Many Defects   | 	Basic use of the repository: messages of little significance and without any connection to issues/tasks.                                                                          |
| 3 – Some Defects	  | Basic use of the repository: mostly significant messages but with little connection to issues/tasks. They use a task/issue management board, but with errors.                      |
| 4 – Correct	       | Frequent use of the repository: messages that are mostly significant but sometimes fail to connect to issues/tasks. Use task/issue management boards correctly.                    |
| 5 - Exceptional    | 	Frequent use of the repository: mostly significant messages and (virtually) no failures in connection to issues/tasks. They use task/issue management boards in an exemplary way. |

**Selected Level:** 3

**Justification and Evidences:**

> The commits are frequent, but their messages could have been more explicit, more significant. However, all the commits
> that are associated with the functionality have its issue number associated.
> 
> The commits also had tags associated with them, regarding the type of change that has been committed.
> 
> The management board was used, although forgotten in the beginning.

![commits_sprintA](commit_messages_sprintA.svg)

> In some commits, the messages are not very significant, but all have connection to the issue in hand [#5].

![issue_usg004_sprintA](usg004_issue_number.svg)

## Team Work

| Level              | Description                                                                                                                                                                                                                                            |
|--------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 0 –  Unacceptable	 | There is no evidence of any type of teamwork process.                                                                                                                                                                                                  |
| 1 – Attempt	       | There is little evidence of teamwork.                                                                                                                                                                                                                  |
| 2 – Many Defects   | 	There is some evidence of work with coordination between teams.                                                                                                                                                                                       |
| 3 – Some Defects	  | There is evidence of a team decision-making process in which students actively participate, although some minor problems may exist, such as the discovery of dependencies only at the end of the process.                                              |
| 4 – Correct	       | There is evidence of a team decision-making process in which students actively participate and which results in the assignment of tasks in a clear and timely manner.                                                                                  |
| 5 - Exceptional    | 	There is evidence of a team decision-making process in which students actively participate and which results in the assignment of tasks in a clear and timely manner. There is evidence of group tasks such as integration and deployment activities. |

**Selected Level:** 5

**Justification and Evidences:**

> The team used GitHub to officially divide tasks and timely plan the whole software development process. In the images
> below is the task division and the proof that all has been done in a timely manner.

![table_issues](table_issues.svg)
![early_planning](early_planning.svg)

## Deployment

| Level             | Description                                                                                                                                                                                                                                                                                   |
|-------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 0 –  Unacceptable | 	The group is unable to demonstrate the system developed outside the IDE.                                                                                                                                                                                                                     |
| 1 – Attempt	      | The system runs outside the IDE, on the local machine without any type of virtualization. The group was able to demonstrate the system but with many failures (e.g. need to restart parts of the system). The demo is based on the version marked in the repository and obtained from Moodle. |
| 2 – Many Defects  | 	The system runs outside the IDE, on the local machine without any type of virtualization. The group managed to demonstrate the application albeit with some minor glitches during the demonstration. The correct version was used (marked in the repository and sent to Moodle).             |
| 3 – Some Defects  | 	The system runs outside the IDE, in a single local virtual environment (machine or container). The group was able to demonstrate the system without any execution failures during the demonstration. The correct version was used.                                                           |
| 4 – Correct	      | The system runs outside the IDE, distributed across two or more local virtual environments (machines or containers). The group was able to demonstrate the system without any execution failures during the demonstration. The correct version was used.                                      |
| 5 - Exceptional   | 	The system runs outside the IDE, distributed across two or more remote virtual environments (machines or containers) (e.g. in the cloud). The group was able to demonstrate the system without any execution failures during the demonstration. The correct version was used.                |

**Selected Level:** 4

**Justification and Evidences:**

> The program runs in the IDE and on the local environment with Java and Maven within the environment variables of the system
> and the connection and server of H2 database active.

![app_running](app_running.svg)

> First image is the Window's Terminal and the second one is the IDE's terminal.


## Integration

| Level              | Description                                                                                                                                                   |
|--------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 0 –  Unacceptable	 | There is no integration.                                                                                                                                      |
| 1 – Attempt	       |                                                                                                                                                               |
| 2 – Many Defects   | 	There is some integration, but it is very rudimentary (for example, they define and test a grammar in the IDE but this is not integrated into the solution). |
| 3 – Some Defects	  | There is integration between most components/functionality, presenting some inconsistencies and/or unnecessary coupling.                                      |
| 4 – Correct	       | There is integration between most components/functionalities without any type of incoherence and/or unnecessary coupling.                                     |
| 5 - Exceptional	   | The previous item is true and this integration is perfectly documented.                                                                                       |

**Selected Level:** 5

**Justification and Evidences:**

> The CI Server is integral to all the present functionalities, guarantying the quality of the code.
> If something is wrong with the code, the commit will not go through.

![workflow_integration](workflow_integration.svg)

> The documentation is complete and very explicit about all aspects of the functionality - available on the **readme** 
> file.

## Req. Satisfaction

| Level              | Description                                                                                                                                                                                                                                                                                        |
|--------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 0 –  Unacceptable	 | No solution was developed for the requirements.                                                                                                                                                                                                                                                    | 
| 1 – Attempt	       | Most of the requirements were not minimally fulfilled due to interpretation flaws that were not adequately justified.                                                                                                                                                                              |
| 2 – Many Defects 	 | Most of the requirements were minimally met, although there were some flaws in interpretation that were not correctly justified and reveal insufficient knowledge of the problem domain. They do not have evidence of defining acceptance criteria for each user story/functionality.              |
| 3 – Some Defects	  | All requirements were met almost in full and any options related to the interpretation/analysis of the problem are correctly justified. Incomplete definition of acceptance criteria. Some acceptance criteria reflected in tests.                                                                 |
| 4 – Correct	       | All requirements have been met in full, and any options related to the interpretation/analysis of the problem are correctly justified and are evidence of your understanding of the problem domain. Very complete definition of acceptance criteria. Most criteria are reflected in tests.         |
| 5 - Exceptional	   | The above is true and alternatives are also discussed that are supported by a rich understanding of the problem domain. These alternatives may be related to the problem domain or the high-level architecture of the solution. Exemplary acceptance criteria and excellent connection to testing. |

**Selected Level:** 4

**Justification and Evidences:**

> USG004 requirements were to use GitHub Actions/Workflow and that all workflows processes checking the commits must run under the 2 minutes mark.
> Both were met.
> 
> The **readme** file of the functionality is explicit about the usages of GitHub Actions, mainly in the Tests topic and the artifacts generated. Every part of the user story's development is thoroughly explained and justified.
> The analysis topic has a clear explanation of the concepts of Continuous Integration, YAML and Workflows, essentials for the understanding of the problem.
>
> For evidence, check USG004's **readme** file.
> 
> No alternatives were discussed.