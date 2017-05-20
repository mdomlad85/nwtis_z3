# Naziv: sustav aplikacija s korištenjem web servisa openweathermap.org i Google Maps API
Sustav je sastoji od dva projekta ({LDAP_korisničko_ime}_zadaca_3_1 i {LDAP_korisničko_ime}_zadaca_3_2). Prvi projekt ima 4 dijela:
u korisničkom dijelu potrebno je unositi pojedinačne IoT uređaje za koje će se preuzimati metorološki podaci. Prvo se unese naziv IoT uređaja i adresa, zatim se pokrene akcija koja preuzima njeni geolokacijski podaci putem Google Maps API. Slijedi pokretanje akcije za prikaz geolokacijski podataka. Nakon toga je akcija za spremanje podataka o IoT uređaju u tablicu baze podataka (UREDJAJI). Zadnja akcija je preuzimanje važećih meteoroloških podataka za IoT uređaj na bazi njegovih geolokacijskih podataka te njihov prikaz na ekranu korisnika.
u pozadinskoj dretvi preuzimaju se u pravilnim intervalima meteorološki podaci putem REST web servisa openweathermap.org za izabrani skup IoT uređaja i pohranjuju se u tablicu u bazi podataka (METEO)
pruža SOAP web servis za meteorološke podatke spremljenih IoT uređaja. Operacije se temelje na podacima koje se nalaze u tablici METEO u bazi podataka. Potrebne su sljedeće operacije:
daje popis svih IoT uređaja i njihovih geo lokacija u application/json formatu
dodaj IoT uređaj (šalje se naziv i adresa koja se ne pamti ali služi za preuzimanje geo lokacije)
svi meteo podaci za IoT uređaj u intervalu (šalju se id IoT uređaja, , od i do tipa long za timestamp, vraća se niz objekata klase Meteo)  (id je identifikator IoT u tablici UREDJAJI u bazi podataka) 
zadnji meteo podaci za IoT uređaj (šalje se id IoT uređaja, vraća se objekat klase Meteo)  (id je identifikator IoT u tablici UREDJAJI u bazi podataka) 
min i max važeća temperatura za IoT uređaj u intervalu (šalju se id IoT uređaja, od i do tipa long za timestamp, vraća se niz od dva elementa tipa float)  (id je identifikator IoT u tablici UREDJAJI u bazi podataka) 
min i max vlaga za IoT uređaj u intervalu (šalju se id IoT uređaja, od i do tipa long za timestamp,vraća se niz od dva elementa tipa float)  (id je identifikator IoT u tablici UREDJAJI u bazi podataka) 
min i max tlak za IoT uređaj u intervalu (šalju se id IoT uređaja, od i do tipa long za timestamp,vraća se niz od dva elementa tipa float)  (id je identifikator IoT u tablici UREDJAJI u bazi podataka) 
pruža dva REST web servis za IoT uređaje,  spremljene podatke o IoT uređajima i meteo podacima:
GET metoda - osnovna adresa - daje popis svih IoT uređaja i njihovih geo lokacija u application/json formatu
POST metoda - osvnovna adresa - dodaj IoT uređaj (šalje se naziv i adresa koja se ne pamti ali služi za preuzimanje geo lokacije) Šalju se podaci u application/json formatu.
GET metoda - na bazi putanje {id} za izabran IoT uređaj vraća važeće meteorološke podatke (temp, vlaga, tlak) na bazi poziva REST web servisa openweathermap.org. (id je identifikator IoT u tablici UREDJAJI u bazi podataka) Vraća podatke u application/json formatu.
Drugi projekt sastoji se od korisničkog dijela u kojem se mogu obaviti dvije aktivnosti:
unositi pojedinačne IoT uređaje za koje će se preuzimati metorološki podaci. Unese se naziv IoT uređaja i adresa. Postoje dva gumba "Upiši SOAP" i "Upiši REST". Svaki od njih pokreće akciju koja poziva određenu operaciju web servisa (SOAP i REST) iz prvog projekta.
preuzeti IoT uređaje za koje se prikupljaju meteorološki podaci u prvom projektu te se prikazuju u obliku padajućeg izbornika s mogućim odabirom više elemenata. Postoje elementi za unos od i do intervala.
Ako se odabere samo jedan IoT uređaj tada se može aktivirati gumb "Preuzmi SOAP" kojim se pokreće akcija koja će preuzeti sve pohranjenje meteorološke podatke za odabrani IoT uređaj putem SOAP web servisa iz prvog projekta. Preuzeti meteorološki podaci prikazuju se u obliku tablice.
Ako se odabere minimalno dva IoT uređaja tada se može aktivirati gumb "Preuzmi REST" kojim se pokreće akcija koja će preuzeti putem REST web servisa iz prvog projekta preuzimaju važaći  meteorološki podaci za odabrane IoT uređaje. Preuzeti meteorološki podaci prikazuju se u obliku tablice.
APPID (APIKEY) za openweathermap treba biti spremljen u konfiguracijskoj datoteci.

Klase i metode trebaju biti komentirane u javadoc formatu. Projekt se isključivo treba predati u formatu NetBeans projekta. Prije predavanja projekta potrebno je napraviti Clean na projektu. Zatim cijeli projekt sažeti u .zip (NE .rar) format s nazivom Prije predavanja projekta potrebno je napraviti Clean na projektu. Zatim cijeli projekt sažeti u .zip (NE .rar) format s nazivom {LDAP_korisničko_ime}_zadaca_3.zip i predati u Moodle. Uključiti izvorni kod, primjere datoteka konfiguracijskih podataka i popunjeni obrazac za zadaću pod nazivom {LDAP_korisničko_ime}_zadaca_3.doc (u korijenskom direktoriju projekta).
Naziv projekta: {LDAP_korisničko_ime}_zadaca_3
Kreiranje 11. vježbe/zadaće 3 (direktorij {LDAP_korisničko_ime}_zadaca_3). U nastavku se direktorij za vježbu simbolički označava kao {vježba}).
Kreiranje projekta {LDAP_korisničko_ime}_zadaca_3_1 kao Java Web aplikaciju, server Tomcat 8.*, Java EE verzija: Java EE 7, kontekst {LDAP_korisničko_ime}_zadaca_3_1, (Frameworks: Ne)
Kreirati pakete org.foi.nwtis.{LDAP_korisnik}.ws.klijenti, org.foi.nwtis.{LDAP_korisnik}.ws.serveri, org.foi.nwtis.{LDAP_korisnik}.rest.klijenti, org.foi.nwtis.{LDAP_korisnik}.rest.serveri, org.foi.nwtis.{LDAP_korisnik}.web.podaci, org.foi.nwtis.{LDAP_korisnik}.web.slusaci
Kreirati direktorij lib u projektu
Kopiranje biblioteke dist\vjezba_03_2.jar iz projekta vjezba_03_2 u direktorij lib
Kopiranje biblioteke dist\vjezba_06_1.jar iz projekta vjezba_06_1 u direktorij lib
Dodavanje biblioteke (jar/folder) lib\vjezba_03_2.jar u projekt
Dodavanje biblioteke (jar/folder) lib\vjezba_06_1.jar u projekt
Dodavanje biblioteka Java DB Driver, Java EE7 API Library, JAX-RS, JAX-WS, Jersey  
Kopirati  glassfish-4.1.?\glassfish\modules\javax.json.jar u direktorij lib
Dodavanje  biblioteke (jar/folder) lib\javax.json.jar  u projekt
Preuzeti primjere datoteke konfiguracija za JavaDB (NWTiS.db.config_2.xml) iz vjezba_06_2 te kopirati u direktorij WEB-INF
kreirati web.xml (New file/Web/Standard Deployment Descriptor) i upisati u web.xml inicijalni parametar konteksta konfiguracija
kreirati slušaca konteksta SlusacAplikacije (anotirani bez uključivanja u web.xml) i kopirati kod funkcije iz vjezba_08_1 ili  {LDAP_korisničko_ime}_zadaca_2 Vrijednost ServletContext spremiti u varijablu klase kako bi se jednostavno pristupilo podacima iz konteksta (aplikacije). Dodati getter za varijablu.
izgraditi, isporučiti, izvršiti i testirati aplikaciju
izvršiti poziv REST web servisa Google Maps API http://maps.google.com/maps/api/geocode/json?address=xxx&sensor=false (umjesto xxx upusati adresu po želji) npr. http://maps.google.com/maps/api/geocode/json?address=Croatia,Vara%C5%BEdin,Pavlinska%202&sensor=false
preuzeti geolokacijske podatke (latitude i longitude)
izvršiti poziv REST servisa openweathermap.org za važeće meteorološke podatke http://api.openweathermap.org/data/2.5/weather?lat=aaa.aaa&lon=bbb.bbb&units=metric&lang=hr&APIKEY=nnn  (umjesto aaa.aaa upsati latitude, umjesto bbb.bbb upisati longitude, umjesto nnn upisati APPID)
preuzeti priložene klase za servis (Klase: GMRESTHelper, OWMRESTHelper, MeteoPodaci, Lokacija, Uredjaj)
kreirati novi SOAP web servis GeoMeteoWS (New/Other/Web Services/Web Service)  u paketu org.foi.nwtis.{LDAP_korisnik}.ws.serveri
slijedi prikaz dijaloškog okvira (Slika 1.) u vezi uključivanja GEOMETEO web servisa u projekt. Odabrati Yes.
otvoriti klasu GeoMeteoWS i odabrati Design
kliknuti na operaciju hello i obrisati operaciju hello
promjeniti u Properites za projekt Source/Binary/Format u 1.8 (ako nije)
preuzeti sa Moodla iz zadaće 2. SQL za kreiranje tablice UREDJAJI i izvršiti u JavaDB bazi podataka nwtsi_g{1,2,3}
preuzetu SQL datoteku meteo.sql izvršiti u JavaDB bazi podataka nwtsi_g{1,2,3}
dodati operaciju dajSveUredjaje(), a vraća java.util.List<Uredjaj>
izgraditi i isporučiti aplikaciju
testirati web servis, desni klik na web servis i Test Web Service, otići na adresu http://localhost:xxxx/{LDAP_korisničko_ime}_zadaca_3_1/GeoMeteoWS?WSDL i upamtiti adresu
testirati operaciju web servisa. Kartica Services/Web Services pa desna tipka na mišu Create Group/NWTiS, desna tipka na mišu pa Add Web Service... i kopirati adresu WSDL-a. Otvoriti web servis i odabrati operaciju, desna tipka na mišu i Test Method i Submit
dodati index.jsp  (izgled kao na slici 5)
u web.xml promijeniti da je index.jsp početna stranica (Pages/Welcome Files)
obrisati index.html
dodati u index.jsp obrazac za unos naziva IoT uređaja i njegove adrese i gumba za akcije
kreirati servlet DodajUredjaj (paket org.foi.nwtis.{LDAP_korisnik}.web) koji će primiti podatke i obaviti potrebne akcije
dohvat geo podataka nakon akcije na gumbu
spremanje podataka o uređaju u tablicu baze podataka UREDJAJI
dohvat važećih meteopodataka upisane adrese IoT uredjaja 
kreirati dretvu PreuzmiMeteoPodatke koja će za spremljene IoT uređaji odnosno njihove geo lokacije preuzimati meterološke podatke i spremati u odgovarajuću tablicu pod nazivom METEO. Dretvu treba startati u slušaču aplikacije kod kreiranje konteksta. Interval dretve određen je konfiguracijskim podatkom.
dodati dodatne operacije web servisa GeoMeteoWS:
dajSveUredjaje()  - vraća List<Uredjaj> sa svim uređajima koji se nalaze u tablici UREDJAJI
dodajUredjaj(Uredjaj)  - dodaje novi uređaj u tablicu UREDJAJI
dajSveMeteoPodatkeZaUredjaj(int, long, long) - vraća sve spremljene podatke iz baze podataka za uneseni IoT uređaj i interval, ukoliko nema podataka vraća null
dajZadnjeMeteoPodatkeZaUredjaj(int) - vraća posljednje spremljene meteo podatake iz baze podatka za uneseni IoT uređaj, ukoliko nema podataka vraća null
dajMinMaxTempZaUredjaj(int, long, long) - vraća min i max važeće temperature iz baze podatka za uneseni IoT uređaj i interval, ukoliko nema podataka vraća null
dajMinMaxVlagaZaUredjaj(int, long, long) - vraća min i max vlagu iz baze podatka za uneseni IoT uređaj i interval, ukoliko nema podataka vraća null
dajMinMaxTlakZaUredjaj(int, long, long) - vraća min i max tlak iz baze podatka za uneseni IoT uređaj i interval, ukoliko nema podataka vraća null
testirati kreirane operacije
kreirati RESTful web servis u paketu org.foi.nwtis.{LDAP_korisnik}.rest.serveri (Other/Web Services/RESTful Web Services from Patterns/Container-Item/ (Slika 2) s nazivom resursa MeteoREST,  klasa MeteoRESTResource, klasa za kontejner MeteoRESTResourceContainer, putanja {id} i putanja kontejnera /meteoREST, tip application/json.(Slika 3.) Odabrati Create default Jersey REST servlet adaptor in web.xml (Slika 4.)
u klasi MeteoREST_Container u metodi getJson() staviti da vraća popis svih IoT ueđaja u application/json formatu.  Potrebno je napraviti upit prema bazi podataka.
u klasi MeteoREST u metodi getJson() staviti da vraća na bazi putanje {id} važeće meteorološke podatke izabranog IoT uređaja. Vraća podatke u application/json formatu. 
u klasi MeteoREST u metodi postJson(String) staviti da dodaje IoT uređaj. Prima podatke u application/json formatu.
izgraditi i isporučiti aplikaciju
testirati RESTful web servis (RESTFul Test Services/Test RESTFul Test Services)
izvršiti RESTful http://localhost:xxxx/{LDAP_korisnicko_ime}_zadaca_3_1/{web}resources/meteoREST/
izvršiti RESTful http://localhost:xxxx/{LDAP_korisnicko_ime}_zadaca_3_1/{web}resources/meteoREST/1 i druge id IoT uređaja
Kreiranje projekta {LDAP_korisnicko_ime}_zadaca_3_2 kao Java Web aplikaciju, server Glassfish, Java EE verzija: Java EE 7, kontekst {LDAP_korisnicko_ime}_zadaca_3_2 , (Frameworks: JSF), Libraries/Server Library: JSF 2.*, Configuration/JSF Servlet URL pattern: /faces/* , Prefered Page Language: Facelets
kreirati pakete org.foi.nwtis.{LDAP_korisnik}.ws.klijenti,  org.foi.nwtis.{LDAP_korisnik}.web.zrna
kreirati novog klijenta web servisa (New/Other/Web Services/Web Service Client) za Web servis MeteoWS koji ima adresu WSDL na bazi projekta {LDAP_korisnicko_ime}_zadaca_3_1
kreirati klasu MeteoWSKlijent u paketu org.foi.nwtis.{LDAP_korisnik}.ws.klijenti
napraviti poziv operacije  dajSveAdrese na web servisu GeoMeteoWS (Insert Code/Call Web Service Operation...)
promijeniti za metodu iz private u public
kreirati JSF MenagesBean OdabirUredkaja (razmisliti primjeni session ili request)
dodati varijablu za preuzimanje uređaja List<Uredjaj>, dodati gettere i settere
kreirati JSF odabirUredjaja.xhtml (izgled kao na slici 6)
dodati elemente za unos podataka u obrascu i potrebne gumbe
dodati <h:dataTable... >za prikaz preuzetih podataka
.....
izgraditi i isporučiti aplikaciju
