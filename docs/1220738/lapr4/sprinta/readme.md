# 1220738 - José Santos - Sprint A - Self Assessment

**This section is mandatory for students of LAPR4**

## Engineering Process

| Level            | Description                                                                                                                                                                                                                                                                                                                     |
|------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 0 - Unacceptable | Did not use                                                                                                                                                                                                                                                                                                                     |
| 1 – Attempt      | The engineering process is very incipient, with almost no evidence of technical documentation or many errors.                                                                                                                                                                                                                   |
| 2 – Many Defects | 	There is already a significant set of engineering artefacts, but the engineering process has evidence of significant errors (e.g. incorrect order of activities, lack of justification when moving between activities/phases of the process) or significant flaws in good practices and techniques that students should apply. |
| 3 – Some Defects | 	There is evidence of a globally correct engineering process, although there may be small flaws in documentation or some activities with errors or missing (e.g. lack of testing) as well as small flaws in the application of good practices and techniques. There may be some inconsistency between code and design.          |
| 4 – Correct      | 	The engineering process is correct. Code and design are completely aligned. Failures in the application of good practices and techniques are almost meaningless.                                                                                                                                                               |
| 5 - Exceptional  | 	The engineering process is correct (order of activities). Code and design are completely aligned. Very good justification of the process followed/applied. Application of good practices and techniques is exemplary.                                                                                                          |

**Selected Level:** 4

 > The engineering process was followed in the best way possible considering the objective of this user story.
> 
> That being said, some parts of this process were not achievable, again, due to the nature of the user story, for example:
> - Tests
> - Class Diagrams
> - Sequence Diagrams
>
> However, the order of activities was followed as show by the following commits<sup>1</sup>: 
> ![order_of_commits](order_of_commits.png)
 >
> More proof can be found in the related [documentation](docs/us_g003/readme.md) and in the commits sent to the GitHub repository.
> 
> <sup>1</sup> The last commit is not being considered in the process as it updates the auto-evaluation and the read.me file associated with my work in this sprint.

## Issues and Version Control

| Level             | Description                                                                                                                                                                        |
|-------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 0 –  Unacceptable | Did not use                                                                                                                                                                        |
| 1 – Attempt       | Very sparse use of the repository.                                                                                                                                                 |
| 2 – Many Defects  | 	Basic use of the repository: messages of little significance and without any connection to issues/tasks.                                                                          |
| 3 – Some Defects	 | Basic use of the repository: mostly significant messages but with little connection to issues/tasks. They use a task/issue management board, but with errors.                      |
| 4 – Correct       | Frequent use of the repository: messages that are mostly significant but sometimes fail to connect to issues/tasks. Use task/issue management boards correctly.                    |
| 5 - Exceptional   | 	Frequent use of the repository: mostly significant messages and (virtually) no failures in connection to issues/tasks. They use task/issue management boards in an exemplary way. |

**Selected Level:** 3

> The commits are not frequent, as most of the documentation and research was done throughout the first 2-3 weeks of this sprint. The documentation was done slowly but commits were not done with every change since it would only include some paragraphs or updates to existing info in the documentation.
> 
> Not only that but the commit messages and titles were not explicit enough and should've been clearer, however, all commits have their issue number associated and use the correct tags to show what kind of work was done and committed:
> ![usage_of_tags.png](usage_of_tags.png)
> ![usage_of_the_number_of_issue](usage_of_the_number_of_issue.png)

## Team Work

| Level             | Description                                                                                                                                                                                                                                            |
|-------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 0 –  Unacceptable | There is no evidence of any type of teamwork process.                                                                                                                                                                                                  |
| 1 – Attempt       | There is little evidence of teamwork.                                                                                                                                                                                                                  |
| 2 – Many Defects  | 	There is some evidence of work with coordination between teams.                                                                                                                                                                                       |
| 3 – Some Defects	 | There is evidence of a team decision-making process in which students actively participate, although some minor problems may exist, such as the discovery of dependencies only at the end of the process.                                              |
| 4 – Correct       | There is evidence of a team decision-making process in which students actively participate and which results in the assignment of tasks in a clear and timely manner.                                                                                  |
| 5 - Exceptional   | 	There is evidence of a team decision-making process in which students actively participate and which results in the assignment of tasks in a clear and timely manner. There is evidence of group tasks such as integration and deployment activities. |

**Selected Level:** 5

**Justification and Evidences:**
 > Multiple meetings were set up with the team to discuss a large amount of subject regarding this project.
> 
> Also, the images below show the division of work and tasks using GitHub in a timely manner:
> ![timely_division_of_tasks1.png](timely_division_of_tasks1.png)
 > ![timely_division_of_tasks2.png](timely_division_of_tasks2.png)
 
## Deployment

| Level             | Description                                                                                                                                                                                                                                                                                   |
|-------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 0 –  Unacceptable | 	The group is unable to demonstrate the system developed outside the IDE.                                                                                                                                                                                                                     |
| 1 – Attempt       | The system runs outside the IDE, on the local machine without any type of virtualization. The group was able to demonstrate the system but with many failures (e.g. need to restart parts of the system). The demo is based on the version marked in the repository and obtained from Moodle. |
| 2 – Many Defects  | 	The system runs outside the IDE, on the local machine without any type of virtualization. The group managed to demonstrate the application albeit with some minor glitches during the demonstration. The correct version was used (marked in the repository and sent to Moodle).             |
| 3 – Some Defects  | 	The system runs outside the IDE, in a single local virtual environment (machine or container). The group was able to demonstrate the system without any execution failures during the demonstration. The correct version was used.                                                           |
| 4 – Correct       | The system runs outside the IDE, distributed across two or more local virtual environments (machines or containers). The group was able to demonstrate the system without any execution failures during the demonstration. The correct version was used.                                      |
| 5 - Exceptional   | 	The system runs outside the IDE, distributed across two or more remote virtual environments (machines or containers) (e.g. in the cloud). The group was able to demonstrate the system without any execution failures during the demonstration. The correct version was used.                |

**Selected Level:** 5

**Justification and Evidences:**

> The images below show that the app being used in the IDE and the command line:
> ![IDE_program.png](IDE_program.png)
> ![Command_line_program.png](Command_line_program.png)
> 
> The only reason the backoffice app only shows the title is because we dont have data on logins, so it was agreed that the code that used login data would be commented to avoid disruption, while everything else was not.

## Integration

| Level             | Description                                                                                                                                                   |
|-------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 0 –  Unacceptable | There is no integration.                                                                                                                                      |
| 1 – Attempt       |                                                                                                                                                               |
| 2 – Many Defects  | 	There is some integration, but it is very rudimentary (for example, they define and test a grammar in the IDE but this is not integrated into the solution). |
| 3 – Some Defects	 | There is integration between most components/functionality, presenting some inconsistencies and/or unnecessary coupling.                                      |
| 4 – Correct       | There is integration between most components/functionalities without any type of incoherence and/or unnecessary coupling.                                     |
| 5 - Exceptional   | The previous item is true and this integration is perfectly documented.                                                                                       |

**Selected Level:** 5

**Justification and Evidences:**
> The integration was successful and no problems were created:
> ![H2_database.png](../../../us_g003/H2_database.png)
> ![ANTLR_dependency.png](../../../us_g003/ANTLR_dependency.png)
> The documentation describes well the user story and is complete.

## Req. Satisfaction

| Level             | Description                                                                                                                                                                                                                                                                                        |
|-------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 0 –  Unacceptable | No solution was developed for the requirements.                                                                                                                                                                                                                                                    | 
| 1 – Attempt       | Most of the requirements were not minimally fulfilled due to interpretation flaws that were not adequately justified.                                                                                                                                                                              |
| 2 – Many Defects  | Most of the requirements were minimally met, although there were some flaws in interpretation that were not correctly justified and reveal insufficient knowledge of the problem domain. They do not have evidence of defining acceptance criteria for each user story/functionality.              |
| 3 – Some Defects	 | All requirements were met almost in full and any options related to the interpretation/analysis of the problem are correctly justified. Incomplete definition of acceptance criteria. Some acceptance criteria reflected in tests.                                                                 |
| 4 – Correct       | All requirements have been met in full, and any options related to the interpretation/analysis of the problem are correctly justified and are evidence of your understanding of the problem domain. Very complete definition of acceptance criteria. Most criteria are reflected in tests.         |
| 5 - Exceptional   | The above is true and alternatives are also discussed that are supported by a rich understanding of the problem domain. These alternatives may be related to the problem domain or the high-level architecture of the solution. Exemplary acceptance criteria and excellent connection to testing. |

**Selected Level:** 4

**Justification and Evidences:**
> The user story asked that the necessary setup is to be done so the project can handle future applications such as ANTLR and also support the envisioned architecture that is shown in chapter 4.
> Both were met.
> 
> The read.me file describes in a concussive way how this user story was thought out and implemented.
> 
> No other alternatives were discussed.