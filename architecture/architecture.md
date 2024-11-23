# Architecture

![architecture_fullstack_java](https://github.com/user-attachments/assets/27206729-68eb-436f-bcaf-4975940f8bba)

## Synchrone communicatie
De directe communicatie tussen microservices gebeurt synchroon met Spring Cloud OpenFeign. Dit type communicatie wordt 
gebruikt wanneer de aanroepende microservice direct een antwoord nodig heeft van de aangeroepen microservice.

### User stories met synchrone communicatie:
- US-01: Als redacteur wil ik nieuwe posts kunnen aanmaken, zodat ik nieuws en updates kan delen met de organisatie
- US-02: Als redacteur wil ik posts kunnen opslaan als concept, zodat ik er later aan kan verderwerken of kan wachten op goedkeuring
- US-03: Als redacteur wil ik de inhoud van een post kunnen bewerken, zodat ik fouten kan corrigeren en inhoud kan bijwerken
- US-04: Als gebruiker wil ik een overzicht van gepubliceerde posts kunnen zien, zodat ik op de hoogte blijf van het laatste nieuws
- US-05: Als gebruiker wil ik posts kunnen filteren op basis van inhoud, auteur, datum
- US-06: Als redacteur wil ik ingediende posts kunnen bekijken en goedkeuren of afwijzen, zodat alleen goedgekeurde content wordt gepubliceerd
- US-08: Als redacteur wil ik opmerkingen kunnen toevoegen bij afwijzing van een post, zodat de redacteur weet welke wijzigingen er nodig zijn
- US-09: Als gebruiker wil ik een reactie kunnen plaatsen op een post, zodat ik mijn mening kan delen of vragen kan stellen
- US-10: Als gebruiker wil ik reacties van andere collega's kunnen lezen, zodat ik een indruk krijg van hun mening of vragen over het artikel
- US-11: Als gebruiker wil ik mijn eigen reacties kunnen bewerken of verwijderen, zodat ik altijd mijn eigen bijdragen kan corrigeren of verwijderen

## Asynchrone communicatie
De indirecte communicatie tussen microservices verloopt asynchroon via RabbitMQ. Dit type communicatie wordt toegepast 
wanneer de aanroepende microservice niet meteen een antwoord nodig heeft en het bericht op een later moment mag worden 
verwerkt. Concreet betekent dit dat de microservices onafhankelijk van elkaar kunnen opereren tijdens deze communicatie.

### User stories met asynchrone communicatie:
- US-07: Als redacteur wil ik een melding ontvangen wanneer een post goedgekeurd of afgewezen is, zodat ik weet of het gepubliceerd kan worden of moet worden herzien