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

Aplikacija je namijenjena za rezervaciju hotelskih soba jednog hotela. U aplikaciji postojati će dvije vrste korisnika: gost i vlasnik hotela.
Nakon prijave u sustav, gostu će se prikazati početni ekran aplikacije na kojem će biti prikazane osnovne informacije o hotelu, s kojeg će moći pristupiti popisu soba. 
Aplikacija će gostu pružiti opcije rezeviranja sobe te promjenu i otkazivanje rezervacija. 
Vlasnik će imati mogućnosti pregleda svih trenutnih i prošlih rezervacija te ručnog dodavanja nove rezervacije.

## Specifikacija projekta

Oznaka | Naziv | Kratki opis | Odgovorni član tima
------ | ----- | ----------- | -------------------
F01 | Registracija | Kako bi korisnik mogao pristupiti aplikaciji, prvo se mora registrirati. Podaci koji se unose pri registraciji su ime, prezime, mail, broj telefona, lozinka i datum rođenja. | Mladen Kajić
F02 | Login | Za pristup funkcionalnostima aplikacije, korisnik će se trebati prijaviti u sustav. Aplikacija će provjeravati vrstu prijavljenog korisnika (vlasnik i gost) na temelju unesenih podataka (mail i lozinka). | Jan Ivančić
F03 | Oporavak lozinke | U slučaju zaboravljanja lozinke, gost može zahtijevati oporavak lozinke te će mu tada na mail doći kontrolni kod koji će unijeti u aplikaciju sa novom lozinkom. | Luka Galjer
F04 | Prikaz i filtriranje soba | Nakon što gost navigira na pregled soba, aplikacija će iz baze podataka prikazati popis svih soba, koje će gost moći filtrirati prema broju kreveta koje ta soba ima i cijeni sobe po noćenju. | Luka Galjer
F05 | Pregled odabrane sobe | Nakon što gost odabere sobu sa popisa, prikazuju mu se sve informacije o sobi, kao što su cijena, slika sobe te sadržaj sobe (kuhalo, internet, broj kreveta, tv, itd.). | Jan Ivančić
F06 | Rezervacije | Aplikacija će gostu omogućiti rezerviranje sobe u željenom terminu i otkazivanje rezervacija. Ukoliko željeni termin nije dostupan, aplikacija će onemogućiti odabir tog termina. Podaci o informacijama će se spremati u bazu podataka. Sustav automatski odobrava rezervaciju. | Jan Ivančić
F07 | Potvrda o rezervaciji putem maila | Kada gost rezervira sobu ili obriše rezervaciju, sustav automatski šalje obavijest o tome na mail gosta. | Mladen Kajić
F08 | Pregled svih rezervacija | Vlasnik hotela će imati mogućnost pregleda svih rezervacija, koje će se podijeliti na trenute i prošle na temelju datuma te rezervacije i trenutnog datuma. | Mladen Kajić
F09 | Ručno upravljanje rezervacijama | Vlasnik hotela će moći ručno dodati novu rezervaciju određene sobe, u slučaju da je soba rezervirana kontaktno umjesto kroz aplikaciju. Također će moći i ručno izbrisati rezervaciju. | Luka Galjer


## Tehnologije i oprema

Za implementaciju aplikacije koristiti ćemo Android Studio te Kotlin programski jezik. Za verzioniranje softvera koristiti ćemo Git i GitHub. Za pisanje dokumentacije projekta koristiti ćemo GitHub Wiki. Za pomoć u izradi projekta koristiti ćemo se dostupnim materijalima sa kolegija Razvoj aplikacija za mobilne i pametne uređaje. 

## Baza podataka i web server

Trebamo bazu podataka i pristup serveru za PHP skripte.

## .gitignore
Uzmite u obzir da je u mapi Software .gitignore konfiguriran za nekoliko tehnologija, ali samo ako će projekti biti smješteni direktno u mapu Software ali ne i u neku pod mapu. Nakon odabira konačne tehnologije i projekta obavezno dopunite/premjestite gitignore kako bi vaš projekt zadovoljavao kriterije koji su opisani u ReadMe.md dokumentu dostupnom u mapi Software.
