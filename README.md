# Inicijalne upute za prijavu projekta iz Razvoja aplikacija za mobilne i pametne uređaje

Poštovane kolegice i kolege, 

čestitamo vam jer ste uspješno prijavili svoj projektni tim na kolegiju Razvoj aplikacija za mobilne i pametne uređaje, te je za vas automatski kreiran repozitorij koji ćete koristiti za verzioniranje vašega koda i za jednostavno dokumentiranje istoga.

Ovaj dokument (README.md) predstavlja **osobnu iskaznicu vašeg projekta**. Vaš prvi zadatak je **prijaviti vlastiti projektni prijedlog** na način da ćete prijavu vašeg projekta, sukladno uputama danim u ovom tekstu, napisati upravo u ovaj dokument, umjesto ovoga teksta.

Za upute o sintaksi koju možete koristiti u ovom dokumentu i kod pisanje vaše projektne dokumentacije pogledajte [ovaj link](https://guides.github.com/features/mastering-markdown/).
Sav programski kod potrebno je verzionirati u glavnoj **master** grani i **obvezno** smjestiti u mapu Software. Sve artefakte (npr. slike) koje ćete koristiti u vašoj dokumentaciju obvezno verzionirati u posebnoj grani koja je već kreirana i koja se naziva **master-docs** i smjestiti u mapu Documentation.

Nakon vaše prijave bit će vam dodijeljen mentor s kojim ćete tijekom semestra raditi na ovom projektu. Mentor će vam slati povratne informacije kroz sekciju Discussions također dostupnu na GitHubu vašeg projekta. A sada, vrijeme je da prijavite vaš projekt. Za prijavu vašeg projektnog prijedloga molimo vas koristite **predložak** koji je naveden u nastavku, a započnite tako da kliknete na *olovku* u desnom gornjem kutu ovoga dokumenta :) 

# Aplikacija za rezervaciju soba hotela

## Projektni tim

Ime i prezime | E-mail adresa (FOI) | JMBAG | Github korisničko ime | Seminarska grupa
------------  | ------------------- | ----- | --------------------- | ----------------
Mladen Kajić | mkajic20@student.foi.hr | 0016147999 | mkajic20 | IPS3-S-G1.1
Luka Galjer | lgaljer20@student.foi.hr | 0016148489 | lgaljer20 | IPS3-S-G1.1
Jan Ivančić | jivancic20@student.foi.hr | 0016150494 | jivancic20 | IPS3-S-G1.1

## Opis domene

Aplikacija je namijenjena za rezervaciju hotelske sobe. Aplikacija će pružiti osnovne informaijce o hotelu i sobama, nuditi će opcije rezeviranja sobe te promjenu i otkazivanje rezervacija. Korisnik će također moći ostaviti recenziju hotela.



## Specifikacija projekta
Umjesto ovih uputa opišite zahtjeve za funkcionalnošću mobilne aplikacije ili aplikacije za pametne uređaje. Pobrojite osnovne funkcionalnosti i za svaku naznačite ime odgovornog člana tima. Opišite osnovnu buduću arhitekturu programskog proizvoda. Obratite pozornost da mobilne aplikacije često zahtijevaju pozadinske servise. Također uzmite u obzir da bi svaki član tima trebao biti odgovoran za otprilike 3 funkcionalnosti, te da bi opterećenje članova tima trebalo biti ujednačeno. Priložite odgovarajuće dijagrame i skice gdje je to prikladno. Funkcionalnosti sustava bobrojite u tablici ispod koristeći predložak koji slijedi:

Oznaka | Naziv | Kratki opis | Odgovorni član tima
------ | ----- | ----------- | -------------------
F01 | Registracija | Kako bi korisnik mogao pristupiti aplikaciji, prvo se mora registrirati | -
F02 | Login | Za pristup funkcionalnostima aplikacije, korisnik će se trebati prijaviti u sustav | -
F03 | Oporavak lozinke | U slučaju zaboravljanja lozinke, korisnik može zahtijevati oporavak lozinke te će mu tada na mail doći kontrolni kod koji će unijeti u aplikaciju sa novom lozinkom | -
F04 | Filtriranje soba | Aplikacija će korisniku omogućiti filtriranje soba | -
F05 | Pregled sobe | Aplikacija će korisniku omogućiti da pregleda informacije o sobi | -
F06 | Rezervacije | Aplikacija će korisniku omogućiti rezerviranje sobe u željenom terminu, promjenu te otkazivanje rezervacija | -
F07 | Potvrda rezervacije | Slanje obavijesti na mail o rezerviranju sobe, promjeni rezervacije i otkazivanju rezervacije | -
F08 | Recenzije | Korisnik će moći ostaviti recenziju hotela, te vidjeti ostale recenzije | -
F09 | Promjena izgleda aplikacije | Korisnik će moći mijenjati temu aplikacije (svijetla i tamna tema) | -

## Tehnologije i oprema

Za implementaciju aplikacije koristiti ćemo Android Studio te Kotlin programski jezik. Za verzioniranje softvera koristiti ćemo Git i GitHub. Za pisanje dokumentacije projekta koristiti ćemo GitHub Wiki. Za pomoć u izradi projekta koristiti ćemo se dostupnim materijalima sa kolegija Razvoj aplikacija za mobilne i pametne uređaje. 

## Baza podataka i web server

Trebamo bazu podataka i pristup serveru za PHP skripte.

## .gitignore
Uzmite u obzir da je u mapi Software .gitignore konfiguriran za nekoliko tehnologija, ali samo ako će projekti biti smješteni direktno u mapu Software ali ne i u neku pod mapu. Nakon odabira konačne tehnologije i projekta obavezno dopunite/premjestite gitignore kako bi vaš projekt zadovoljavao kriterije koji su opisani u ReadMe.md dokumentu dostupnom u mapi Software.
