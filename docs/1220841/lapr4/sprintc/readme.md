# 1220841 - Rita Barbosa - Sprint B - Self Assessment

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

**Selected Level:** 5

**Justification and Evidences:**

The engineering process followed the proper sequence of activities for all user stories, as evidenced in various
documentation files. Additionally, the order of activities was confirmed through the commits made, with user story 1004
serving as an illustrative example.

![Commits for US1004](img.png)
![Commits for US1004](img_1.png)
![Commits for US1004](img_2.png)
![Commits for US1004](img_3.png)

Furthermore, all steps and decisions concerning the user stories were accurately documented along with thorough
explanations.

## Issues and Version Control

| Level              | Description                                                                                                                                                                        |
|--------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 0 –  Unacceptable	 | Did not use                                                                                                                                                                        |
| 1 – Attempt	       | Very sparse use of the repository.                                                                                                                                                 |
| 2 – Many Defects   | 	Basic use of the repository: messages of little significance and without any connection to issues/tasks.                                                                          |
| 3 – Some Defects	  | Basic use of the repository: mostly significant messages but with little connection to issues/tasks. They use a task/issue management board, but with errors.                      |
| 4 – Correct	       | Frequent use of the repository: messages that are mostly significant but sometimes fail to connect to issues/tasks. Use task/issue management boards correctly.                    |
| 5 - Exceptional    | 	Frequent use of the repository: mostly significant messages and (virtually) no failures in connection to issues/tasks. They use task/issue management boards in an exemplary way. |

**Selected Level:** 5

**Justification and Evidences:**

The repository was frequently used, with all commit messages being meaningful, following the team's established
format, and linked to their respective issues. Additionally, the task management board was appropriately utilized.
This is corroborated by:

![commits format](commits-format.png)
![commit frequency](commit-frequency.png)

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

Throughout this sprint, we held multiple meetings to address specific topics, which were documented both on the task
management board and in the [readme](../../../team-decisions/team-decisions.md) file.

As for the task management board, the meetins we recorded on the daily logs, for example:
![img_4.png](img_4.png)
Additionally, some decisions made during these meetings are detailed in the readme file mentioned earlier.

Finally, there is the sprint planning issue, which details all decisions made during
the [sprint planning](https://github.com/orgs/Departamento-de-Engenharia-Informatica/projects/184/views/2?filterQuery=assignee%3A%40me+sprint&pane=issue&itemId=62698181)
meeting and
outlines the task allocation.

Additionally, we have decided to use daily logs for this sprint to document the progress of our planned tasks throughout
the sprint. For example:

![img_5.png](img_5.png)
![img_6.png](img_6.png)

## Deployment

| Level             | Description                                                                                                                                                                                                                                                                                   |
|-------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 0 –  Unacceptable | 	The group is unable to demonstrate the system developed outside the IDE.                                                                                                                                                                                                                     |
| 1 – Attempt	      | The system runs outside the IDE, on the local machine without any type of virtualization. The group was able to demonstrate the system but with many failures (e.g. need to restart parts of the system). The demo is based on the version marked in the repository and obtained from Moodle. |
| 2 – Many Defects  | 	The system runs outside the IDE, on the local machine without any type of virtualization. The group managed to demonstrate the application albeit with some minor glitches during the demonstration. The correct version was used (marked in the repository and sent to Moodle).             |
| 3 – Some Defects  | 	The system runs outside the IDE, in a single local virtual environment (machine or container). The group was able to demonstrate the system without any execution failures during the demonstration. The correct version was used.                                                           |
| 4 – Correct	      | The system runs outside the IDE, distributed across two or more local virtual environments (machines or containers). The group was able to demonstrate the system without any execution failures during the demonstration. The correct version was used.                                      |
| 5 - Exceptional   | 	The system runs outside the IDE, distributed across two or more remote virtual environments (machines or containers) (e.g. in the cloud). The group was able to demonstrate the system without any execution failures during the demonstration. The correct version was used.                |

**Selected Level:** 5

**Justification and Evidences:**

The program can operate independently of the IDE, functioning effectively across different environments. For instance,
it has been tested and proven compatible with both Windows and Linux platforms.

* Linux
  ![system running in linux virtual machine](system-run-vm-linux.png)

* Windows
  ![system running in windows](system-run-windows.png)

Aditionally, as seen above the system doesn't have any system execution failures.

## Integration

| Level              | Description                                                                                                                                                  |
|--------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 0 –  Unacceptable	 | There is no integration.                                                                                                                                     |
| 1 – Attempt	       |                                                                                                                                                              |
| 2 – Many Defects   | There is some integration, but it is very rudimentary (for example, they define and test a grammar in the IDE but this is not integrated into the solution). |
| 3 – Some Defects	  | There is integration between most components/functionality, presenting some inconsistencies and/or unnecessary coupling.                                     |
| 4 – Correct	       | There is integration between most components/functionalities without any type of incoherence and/or unnecessary coupling.                                    |
| 5 - Exceptional	   | The previous item is true and this integration is perfectly documented.                                                                                      |

**Selected Level:** 4

**Justification and Evidences:**

Integration within our current project is evident through the seamless interaction between most components and
functionalities. This is reflected in the absence of conflicts or inconsistencies and the minimal presence of
unnecessary dependencies.

## Req. Satisfaction

| Level              | Description                                                                                                                                                                                                                                                                                        |
|--------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 0 –  Unacceptable	 | No solution was developed for the requirements.                                                                                                                                                                                                                                                    | 
| 1 – Attempt	       | Most of the requirements were not minimally fulfilled due to interpretation flaws that were not adequately justified.                                                                                                                                                                              |
| 2 – Many Defects 	 | Most of the requirements were minimally met, although there were some flaws in interpretation that were not correctly justified and reveal insufficient knowledge of the problem domain. They do not have evidence of defining acceptance criteria for each user story/functionality.              |
| 3 – Some Defects	  | All requirements were met almost in full and any options related to the interpretation/analysis of the problem are correctly justified. Incomplete definition of acceptance criteria. Some acceptance criteria reflected in tests.                                                                 |
| 4 – Correct	       | All requirements have been met in full, and any options related to the interpretation/analysis of the problem are correctly justified and are evidence of your understanding of the problem domain. Very complete definition of acceptance criteria. Most criteria are reflected in tests.         |
| 5 - Exceptional	   | The above is true and alternatives are also discussed that are supported by a rich understanding of the problem domain. These alternatives may be related to the problem domain or the high-level architecture of the solution. Exemplary acceptance criteria and excellent connection to testing. |

**Selected Level:** 5

**Justification and Evidences:**

The user stories' requirements have been fulfilled, as evidenced in their respective
readme ([us_1004](../../../sprint-c/us_1004/readme.md); [us_1015](../../../sprint-c/us_1015/readme.md); [us_1020](../../../sprint-c/us_1020/readme.md); [us_2001b](../../../sprint-c/us_2001b/readme.md); [us_4000](../../../sprint-c/us_4000/readme.md))
files, where the acceptance
criteria are also provided. In terms of testing, it is clear that the tests were designed based on the acceptance
criteria, with each test referencing the corresponding acceptance criteria.
