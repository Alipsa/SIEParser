# Release Notes for SieParser

## Version 2.0.1, in progress
- Modify description to mention that the library can also write SIE files, not just read them.
- Dev enhancement: Add configuration cache and parallell execution.

## Version 2.0, 2026-02-27
- Ported all upstream bug fixes and features from jsisie (2017-2026)
- Java 17 minimum, `java.time.LocalDate` throughout, `java.util.function.Consumer` callbacks
- `#UNDERDIM` support, `#KSUMMA` writing, stream I/O, SIE type filtering
- Exception hierarchy (`SieException` base class), proper field encapsulation
- Expanded automated test coverage across SIE 1-4 and SIE 5
- SIE 5 (XML) read/write support via JAXB (`alipsa.sieparser.sie5` package)
- New dependencies
  - jakarta.xml.bind:jakarta.xml.bind-api [4.0.5]
  - org.glassfish.jaxb:jaxb-runtime [4.0.6]
- Upgraded dependencies
  - org.slf4j:slf4j-api [2.0.16 -> 2.0.17]

## Version 1.0, 2016-12-10
- Initial working port of the jsisie parser to Java