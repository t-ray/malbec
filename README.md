malbec
======

malbec is a web front end for ansible. It is written in Kotlin and requires at least jre8 to run.

# Background
malbec started out to fill a very narrow need: to run ansible playbooks from a web interface. The concept is provide a front-end for users to be able to run a given playbook without having to know ansible, or have ansible installed on their systems. 

This functionality obviously overlaps with a professional tool like [Ansible Tower](https://www.ansible.com/tower). However, Tower is an enterprise class product, with tons of features, and many of these features simply do not matter to small teams. 

Another similar tool is [semaphore](https://github.com/ansible-semaphore/semaphore).  semaphore is a capable tool, but some functionality is questionable. 

And lastly, I wanted some project where I could explore Kotlin. I've tinkered with it for a while now, but never for real, live code. 

# Roadmap / Features
The core functionality will include:

* The ability to check out remote repositories:
  * git
      * https
      * ssh
  * svn? (depending on demand)
* define inventory within the UI
* use inventory checked out from repo
* Define ssh keystores
    * Used for repo pulls
    * Used for running ansible
* Auditing
    * Record all runs+results
* Authentication/Authorization
    * Will be implemented later. Needs thorough design.


