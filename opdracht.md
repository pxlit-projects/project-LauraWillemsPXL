# Contentbeheer & publicatiesysteen niewsartikelen / blogposts
## Omschrijving
Applicatie voor het beheren en publiceren van interne nieuwsartikelen binnen een organisatie. Dit systeem stelt 
redacteuren in staat om artikelen te schrijven, goed te keuren en te publiceren, zodat medewerkers op de hoogte blijven
van het laatste nieuws. De applicatie bestaat uit 3 microservices:
- PostService
- ReviewService
- CommentService

## Omschrijving microservices
1. PostService: laat redacteurs toe om posts aan te maken en aan te passen
2. ReviewService: laat redacteurs toe om posts goed te keuren of af te wijzen
3. CommentService: laat gebruikers toe om reacties te plaatsen op posts

## User Stories (PostService)
### US-01: Als redacteur wil ik nieuwe posts kunnen aanmaken, zodat ik nieuws en updates kan delen met de organisatie
### US-02: Als redacteur wil ik posts kunnen opslaan als concept, zodat ik er later aan kan verderwerken of kan wachten op goedkeuring
### US-03: Als redacteur wil ik de inhoud van een post kunnen bewerken, zodat ik fouten kan corrigeren en inhoud kan bijwerken
### US-04: Als gebruiker wil ik een overzicht van gepubliceerde posts kunnen zien, zodat ik op de hoogte blijf van het laatste nieuws
### US-05: Als gebruiker wil ik posts kunnen filteren op basis van inhoud, auteur, datum

## User Stories (ReviewService)
### US-06: Als redacteur wil ik ingediende posts kunnen bekijken en goedkeuren of afwijzen, zodat alleen goedgekeurde content wordt gepubliceerd
### US-07: Als redacteur wil ik een melding ontvangen wanneer een post goedgekeurd of afgewezen is, zodat ik weet of het gepubliceerd kan worden of moet worden herzien
### US-08: Als redacteur wil ik opmerkingen kunnen toevoegen bij afwijzing van een post, zodat de redacteur weet welke wijzigingen er nodig zijn

## User Stories (CommentService)
### US-09: Als gebruiker wil ik een reactie kunnen plaatsen op een post, zodat ik mijn mening kan delen of vragen kan stellen
### US-10: Als gebruiker wil ik reacties van andere collega's kunnen lezen, zodat ik een indruk krijg van hun mening of vragen over het artikel
### US-11: Als gebruiker wil ik mijn eigen reacties kunnen bewerken of verwijderen, zodat ik altijd mijn eigen bijdragen kan corrigeren of verwijderen

## Authenticatie
- Uitwerken van een authenticatiesysteen is **NIET** nodig
- Frontend
  - AuthService die rol / user bijhoudt
  - Wijzigen rol / user via login scherm, geen communicatie met backend nodig
- Backend
  - Leest rol / user uit header en gebruikt deze in de logica

## Communicatie microservices
1. Open-Feign (synchroon)
2. Message bus - RabbitMq (asynchroon)

## Test Coverage
- Frontend: 50%
- Backend: 70%

## Logback
- Toegepast in elke microservice
- Log naar een file
