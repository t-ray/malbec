# Technical Docs
The goal of this project is to provide a functional, and above all, useful we frontend for ansible. However, it is also a techincal exercise so that I can explore different technologies and practices to which I am not typically exposed. This document will explain why some techincal decisions have been made, and ask broader questions about the future of the project.

## Container Support?
Malbec is a full stack application which requires access to a database and local installations of ansible. Technically it would be possible to run in a container, but requiring access to local ansible installations complicates things a bit. So to expedite things, the first release will provide a fat jar and require a jre, but future releases may include a Dockerfile.

## Kotlin vs *Other*
I've chosen to implement Malbec in Kotlin because I want to understand it deeply enough that I can recommend its adoption at my work, which is a java shop.

I did consider Go, but frankly I'm just not interested in go. I understand the runtime requirements are much larger due to the jvm, but there are plenty of other successful jvm based backend projects. 

### Java vs Kotlin frameworks
I would prefer to use Kotlin focused frameworks/libraries, but Kotlin, as an ecosystem, still has a lot of catching up to do with java. Therefore, the codebase will make liberal use of java frameworks due to their maturity. 

### Spring Boot
I've chosen to go with Spring Boot to bootstrap the codebase. It may make sense in the future to move away from Spring Boot to pure Kotlin frameworks like [Exposed](https://github.com/JetBrains/Exposed), [ktor](https://github.com/Kotlin/ktor), and [Kara](http://karaframework.com/), but right now Spring Boot makes sense because it provides so much out of the box already. 

### Database
The schema and the setup for the database will be managed by liquibase, so the intent is to support many different database backends (Postgres, Mysql, etc). But the first version will only include dependencies for mysql, and sql statements will only be tested against mysql.

### Data Access Patterns
I fundamentally disgree with the concept of ORM's, so data access will be implemented either using spring jdbc, or with Exposed. I think for periods of initial development, it is likely to have both spring jdbc and Exposed code used at the same time, and see which evolves better. 

If I stick with spring jdbc, then I will have to thoroughly test the sql literals against multiple database backends. If I use Exposed, then theoretically I don't have to worry about it.

### Model Objects
Right now the model objects are all in the package `malbec.core.domain`
I generally prefer to have three distinct layers of model objects - one for the data tier, one for the business domain, and the last for the api/user interface/view model. However, this project should be simple enough that a single model will suffice. I will break them out into separate models layer if required. Therefore, for now at least, the model objects will be sibject to data, UI, and marshalling annotations. 

## Domain Model
The program will function in a similar manner to the semaphore project. The highest level unit of functionality is the **Project**. Pretty much everything will belong to a project. 

Within a project, there are several functional notions:

* Repositories
* Key Stores
* Inventory
* Tasks 

#### Repositories
A Repository is a collection of ansible tasks or playbooks that is stored remotely. Initially, only git repos will be supported. A project may have multiple repositories defined.

#### Keys
Each project defines zero or more keys to be used when fetching from remote repos, or for when executing playbooks.

#### Inventory
The user can define custom inventory directly within a given project. Alternatively, inventory stored within the repo may be used. A project may have multiple inventory defined.

#### Tasks
The tasks represent a single unit that the user will execute. A user will define a task that execute a playbook from a repo, using a specified ssh key, and specified inventory. A project may have multiple tasks.

#### Installations
An Installation is a physcal reference to an instance of ansible on the host system. Typically a system may only have a single installation, but some systems may have multiple, concurrent installations present with different versions. Malbec will support either such configuration. The system provides a singleton object named the DefaultInstallation. This object represents an Installation that exists on the system's path. 

Installations are global objects and are not owned by a particular project.

## Front-end
Who knows? I'm not a front end developer. Really want some help here.
