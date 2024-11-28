# Project Jobs4U

## 1. Description of the Project

Jobs4U specializes in talent acquisition, offering recruitment services to its clients for various job positions. This project aims to explore the development of a solution to automate the company's primary activities. Jobs4U's clients consist of other businesses or entities in need of human resource recruitment assistance.

In response to client requests, Jobs4U undertakes all necessary activities to select a pool of candidates for job offers. Upon completion of the process, Jobs4U provides its clients with a ranked list of candidates for each job offer. The final decision regarding recruitment rests with the client.

The project will include the following applications:
* Backoffice:
  * Manage Customers and its Managers
  * Deploy and Configure Plugin
  * Register Application
  * Manage Job Openings
* Customer App:
  * Follow Job Openings
* Candidate App:
  * Follow Applications

The platform will manage:
* Customers: register
* Job Openings:register, list, edit
* Applications: register, list
* Interviews: schedule
* Candidates: register, list, rank, enable/disable
* Recruiment Process: setup, open/close phases

## 2. Planning and Technical Documentation

[Planning and Technical Documentation](docs/readme.md)

## 3. How to Build

Before building this project, it is necessary to make sure Maven is installed and defined on the PATH

* build

> build-all.bat\
> or\
> build-all.sh

* rebuild

> rebuild-all.bat\
> or\
> rebuild-all.sh

## 4. How to Execute Tests

To execute the tests, it's needed to execute the following command in the command line:

> nvm test


## 5. How to Run

Before running the application, is necessary to build the project. For more information go to section [3. How to Execute Tests](#3-how-to-build)

* backoffice app 

> run-backoffice-app.bat\
> or\
> run-backoffice-app.sh

* candidate app

> run-candidate-app.bat\
> or\
> run-candidate-app.sh

* customer app

> run-customer-app.bat\
> or\
> run-customer-app.sh


* follow-up server

> run-follow-up-server.bat\
> or\
> run-follow-up-server.sh

## 7. How to Generate PlantUML Diagrams

To generate plantuml diagrams for documentation execute the script (for the moment, only for linux/unix/macos):

    ./generate-plantuml-diagrams.sh


