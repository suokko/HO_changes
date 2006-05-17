HO
---
Directory information :

- src/java
de.hattrickorganizer.credtis   -> 2D Credit Engine
de.hattrickorganizer.database  -> db Wrapper conatining jdbc Adapter and DB Functions
de.hattrickorganizer.gui       -> All and everything regarding gui
de.hattrickorganizer.hoplugins -> plugin dev dir
de.hattrickorganizer.logik     -> logical functions
de.hattrickorganizer.model     -> datastructures
de.hattrickorganizer.net       -> http and hofriendly network functions
de.hattrickorganizer.plugins   -> Plugin-API, Interfaces for every exported API
de.hattrickorganizer.tools     -> global Helper funcs

- src/conf
flags     		       -> Image dir with ht Flags being named using their ht number
sprache 		       -> Language Files
config			       -> Original files for epv and ratings 
script			       -> Place where generation scripts are

- dist 			       -> contains files needed for deployment of HO
- test/java		       -> test classes

needed additional library for building :
hsqldb.jar
jl1.0.jar

----
Jar Building
---
Use Manifest.mf as Manifest for jar
These packages belong into jar
de.hattrickorganizer.credits (obf)
de.hattrickorganizer.database obf)
de.hattrickorganizer.gui (obf)
gui (UserParameter needs to be kept public, gui.bilder needs path to be kept when obfuscating)
de.hattrickorganizer.logik (obf)
de.hattrickorganizer.model (obf)
de.hattrickorganizer.net (obf)
plugins ( need to be kept all public when obfuscating)
de.hattrickorganizer.tools (obf) 
de.hattrickorganizer.HO.class ( mainclass need to be kept public when obfuscating )

--
deployment
--
ship obfuscated hocoded.jar
with 
sprache,
flags,
ho.bat
HO.sh
lizenz.txt
Loglo.ico
version.txt
defaults.xml
jl1.0.jar
and hsqldb.jar, hsqldb_lic.txt
HOLauncher.class
epv.dat
ratings.dat

Thats all for now.
Thx for supporting HO :)
