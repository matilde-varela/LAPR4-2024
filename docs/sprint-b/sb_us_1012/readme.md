# US 1012

## 1. Context

It is the first time this task was assigned.

## 2. Requirements

**US 1012:** As Customer Manager, I want to generate and export a template text file to help collect the candidate 
answers during thew interviews.


**Acceptance Criteria:**

- **1012.1.** The file should have the questions to be asked in the interview and the possible answers for each question.

- **1012.2.** The exported file should be in .txt format.

- **1012.3.** It is necessary the use of ANTLR.


**Dependencies/References:**

This functionality has a dependency on [_US G007_](../us_g007) that pertains to the authentication and authorization for
all users and functionalities.
In addition, this functionality has a dependency on [_US G003_](../us_g003) that consists in initial configuration of 
the project structure, where was made the setup for the ANTLR.

_Reference 1012.1:_ Requirement Specifications and Interview Models The support for this functionality must follow 
specific technical requirements, specified in LPROG. The ANTLR tool should be used (https://www.antlr.org/).


**Client Clarifications:**

> **Question:** Is it possible to clarify whether the questions to be asked for the Interview Models and the Requirement
> Specifications are the ones provided in the documentation example or if there is any group of questions that you would 
> like us to use?
> 
> 
> **Answer:** The specification document provides, as mentioned, examples. They are just indicative examples. You can use
> the examples as test cases and as a start point to define others. However, the solution should support more than just
> the examples provided in the document. In any of the plugins, the type of questions that should be supported is what is
> presented on the page 8 of the document. As the product owner, I would like a functional demonstration of the system
> to include at least 2 plugins of each type, so that we can demonstrate, at least minimally, support for more than one 
> plugin used (of each type) simultaneously.



## 3. Analysis

The objective of this user story is to create a template file, that will be used as a model for the job interviews done 
in the future. 

To be able to reach that objective, the file should contain the questions and examples of answers for
them.

There are some type of questions that should be supported, such as:
* True/False
* Short Text Answer
* Choice, with Single-Answer
* Choice, with Multiple-Answer
* Integer Number
* Decimal Number
* Date
* Time
* Numeric Scale

For each question chosen, the Operator will define the possible answers, to be used as comparison with the answers
given by the candidates.


### 3.1 System Sequence Diagram

![system sequence diagram](US1012_SSD.svg)

### 3.2 Domain Model Related

![domain model related](domain-model-excerpt.svg)

### 3.3 Grammar

```
grammar Plugin;

start: (group)* EOF;

group: (question NEWLINE?)+;

question: type '"' PHRASE ('.' | '?') '"' choices answer;

type: TYPES '-' NUM ' ';
choices: choice*;

choice: NUM ') '  PHRASE ;

// Answers for the questions
answer: START_ANSWER answer_option ;

answer_option: true_false_a | text |  date_a | time_a | whole_n_a |  scale_a | decimal_a  ;

text:  PHRASE ;
whole_n_a:   NUM+;
true_false_a:  BOOLEAN ;
scale_a:   NUM+ '-'  NUM+;
decimal_a:  NUM+ '.' NUM+;
date_a:  NUM NUM '/' NUM NUM '/' NUM NUM NUM NUM;
time_a:  NUM NUM ':' NUM NUM;

BOOLEAN: 'True' | 'False' | 'true' | 'false';
START_ANSWER: 'A:. ';
PHRASE: (WORD ' ')* WORD;
WORD : ([a-zA-Z]|[-]|[/])+;
NEWLINE: '\r'? '\n'->skip;
NUM: [0-9];
TYPES: '[SC]'
    | '[MC]'
    | '[WN]'
    | '[TF]'
    | '[NS]'
    | '[DN]'
    | '[SA]'
    | '[D]'
    | '[T]'
    ;
```

```
grammar SymbolTable;

start : (line NEWLINE?)+;

line : type question choices? evals;

evals : answer_eval*;

answer_eval: '/' (PHRASE ';'?)* ':' numbers
           | '/' answer_numbers ':' numbers
           | '/' condition
           | '/' date ':' numbers
           | '/' BOOLEAN ':' numbers
           | '/' scale ':' numbers
           | '/' decimal ':' numbers
           | '/' time ':' numbers
           ;

date:  NUM NUM '-' NUM NUM '-' NUM NUM NUM NUM;

scale:   NUM+ '-'  NUM+;
decimal:  NUM+ '.' NUM+;
time:  NUM NUM ':' NUM NUM;

condition : SIGNALS+;

answer_numbers : numbers;

choices : choice+;

choice : '/' (PHRASE ';'?)*;

type : TYPES'-' numbers;
question: '/' PHRASE ('.' | '?');
numbers : NUM+;


BOOLEAN: 'True' | 'False' | 'true' | 'false';
START_ANSWER: 'A:. ';
PHRASE: (WORD ' ')* WORD;
WORD : ([a-zA-Z]|[-,])+;
NEWLINE: '\r'? '\n'->skip;
SIGNALS: ([<]|[=]|[>])+;
NUM: [0-9];
TYPES: '[SC]'
    | '[MC]'
    | '[WN]'
    | '[TF]'
    | '[NS]'
    | '[DN]'
    | '[SA]'
    | '[D]'
    | '[T]'
    ;

```

## 4. Design
To address this functionality, we are going to adopt a four-layered approach based on DDD (Domain-Driven Design)
architecture: Presentation, Application, Domain and Persistence.

The main goal of this functionality is for generate a interview model template and to generate is needed a file name,
the questions and the correspondent answers.

The question types are defined in the documentation, but also can be added more if wanted.

To be able to promote encapsulation between layers, it will be used DTOs.

### 4.1. Realization

**US1012 Sequence diagram**

![US1012 Sequence diagram](us_1012_SD.svg)

### 4.2. Class Diagram

![US2003 Class Diagram](us1012_class_diagram.svg)

### 4.3. Applied Patterns

This topic presents the classes with the patterns applied to them along with justifications.


>**Service Pattern**
> * InterviewTemplateManagerService
> * JobOpeningManagementService
>
> **Justifications**
> 
>  The manager service will manage a set of responsibilities that don't belong to any class.
>  It will manage the creation of the interview template file.
>
>   JobOpeningManagementService is used in more than one functionality, and its in charge of managing request regarding
>   jobOpenings.
>

>**Repository Pattern**
> * InterviewModelRepository
> * JobOpeningRepository
>
> **Justifications**
>
> Many plugins can exist, so they must be stored and persisted in a repository. It is from here that the plugins available
> to generate an interview model template file are.
>
> The JobOpeningRepository has stored all the jobOpening instances created in all sessions in its database, it's where
> the instances can be rebuilt.



### 4.4. Tests


**Test 1:** Verifies that the file is not empty

**Refers to Acceptance Criteria:** 1012.1

````
@Test
public void ensureFileNotEmpty() {
...
}
````

**Test 2:** Verifies file format

**Refers to Acceptance Criteria:** 1012.2

````
@Test
public void ensureFileFormat() {
...
}
````

**Test 3:** Verifies grammar is valid.

**Refers to Acceptance Criteria:** 1012.3

````
@Test
public void ensureCorrectFormatIsValid() {
...
}
````

**Test 4:** Verifies grammar is invalid.

**Refers to Acceptance Criteria:** 1012.3

````
@Test
public void ensureIncorrectFormatIsInvalid() {
...
}
````


## 5. Implementation


### GenerateInterviewModelTemplateFileController

````
public List<JobOpeningDTO> getJobOpeningList() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.CUSTOMER_MANAGER);
        List<JobOpening> jobOpenings = new ArrayList<>();
        for (JobOpening job : jobOpeningManagementService.getUNFINISHEDJobOpenings()){
            jobOpenings.add(job);
        }
        return jobOpeningListDTOService.convertToDTO(jobOpenings);
    }
    
````
````
 public boolean exportTemplateFile(JobOpeningDTO jobOpeningDTO, String directoryPath) {
        return interviewTemplateManagerService.generateNewTemplate(interviewTemplateManagerService.getInterviewFromJobOpening(jobOpeningDTO), directoryPath);
    }
````


### JobOpeningManagementService

````
public Iterable<JobOpening> getUNFINISHEDJobOpenings(){
        return jobOpeningRepository.getUNFINISHEDJobOpeningList();
    }
````

### JobOpeningListDTOService

````
    public List<JobOpeningDTO> convertToDTO(List<JobOpening> listToDisplay){
        Preconditions.noneNull(listToDisplay);
        Preconditions.nonEmpty(listToDisplay);

        List<JobOpeningDTO> dtoList = new ArrayList<>();
        for (JobOpening jobOpening : listToDisplay){
            dtoList.add(jobOpening.toDTO());
        }
        return dtoList;
    }
````

### InterviewTemplateManagerService

````
public boolean generateNewTemplate(InterviewModel interviewModel, String outputDirectory) {
        try {
            String[] command = {"java", "-jar", interviewModel.pluginJarFile().pluginName(), "-template", (outputDirectory + "\\")};

            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.start();

            int exitCode = process.waitFor();
            System.out.println("Template command executed. Process exited with code: " + exitCode);
            if (exitCode == 0){
                return true;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
````



## 6. Integration/Demonstration

To activate this feature, you'll need to run the script named `run-backoffice-app` and log in with Customer Manager
permissions. Then, navigate to the "Plugins" menu and select option 1 - `Generate and export Interview Model Template` - to access this
feature.


