# Architecture

![architecture_fullstack_java](https://github.com/user-attachments/assets/27206729-68eb-436f-bcaf-4975940f8bba)

## Synchronone communicatie
De directe communicatie tussen microservices gebeurt synchroon met Spring Cloud OpenFeign. Dit type communicatie wordt gebruikt wanneer de aanroepende microservice direct een antwoord nodig heeft van de aangeroepen microservice.

## Asynchrone communicatie
De indirecte communicatie tussen microservices verloopt asynchroon via RabbitMQ. Dit type communicatie wordt toegepast wanneer de aanroepende microservice niet meteen een antwoord nodig heeft en het bericht op een later moment mag worden verwerkt. Concreet betekent dit dat de microservices onafhankelijk van elkaar kunnen opereren tijdens deze communicatie.
