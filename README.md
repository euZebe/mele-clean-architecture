# mele-clean-architecture

## Overview
This project is about applying [clean architecture](https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html) to a simple application, i.e. isolating core domain from every infrastructure "detail" (meaning database, frameworks...).

## Domain context
Based on the idea to generate draws (assigning "randomly" a person to each participant, considering constraints), this application provides a few REST web services.
Example: 
* 4 participants: 
  * Niobé
  * Titouan
  * Eusèbe
  * Julia
* 2 constraints:
  * Niobé cannot be assigned to Julia
  * Niobé cannot be assigned to Titouan

[meLe application](http://mele-eusebe.rhcloud.com) is an implementation of this concept.


## Code architecture
- The domain implementation is located in the core module
- entrypoints exposed by the application (i.e. REST web services) are located in the entrypoints module. Depends on the core module.
- The persistence feature is located in the infrastructure module. Depends on the core module.
- Configuration module contains the SpringBoot configuration. Depends on all others modules.

