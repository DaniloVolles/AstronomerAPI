```MERMAID
classDiagram
class Professor {
UUID id
String fullName
LocalDate birthDate
String cpf
String email
String phone
Degree degree
String researchArea
String institution
Address address
}

    class Address {
        String street
        String complement
        String neighborhood
        String city
        String state
        String country
    }

    class CelestialObject {
        String id
        String name
        CelestialObjectType type
        BigDecimal diameterKm
        LocalDate discoveryDate
        String discoveryMethod
        String discoveryDescription
        UUID professorId
    }

    enum Degree {
        BACHELOR
        MASTER
        DOCTOR
    }

    enum CelestialObjectType {
        STAR
        PLANET
        MOON
        DWARF_PLANET
        ASTEROID
        COMET
        OTHER
    }

    Professor "1" --> "0..*" CelestialObject : discovers
    Professor --> Address
    CelestialObject --> CelestialObjectType
    Professor --> Degree

```
