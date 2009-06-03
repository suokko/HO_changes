Informations about the new HO! Feedback Plugin (since HO 1.410)
============================================================

HO! introduces in this release a plugin called Feedback.
It sends information about rating prediction, training duration,
and transfers prices to the HO! staff.
These data will help a lot, to improve our formulas!

You can disable this feature in Options/Feedback

Below, you'll find an exact list of all uploaded data.

Of course, we will use the uploaded informations for formula 
optimization only and keep them strictly confidential.


Structure of a data set:
========================

Ratings
~~~~~~~
For each usefull match (i.e. no red cards, no injuries...) this data will be uploaded:

- Contributor ID (i.e. your team ID)
- Match date
- Location (home/away/awayderby)
- Trainer Type
- Team spirit
- Team confidence
- Attitude (PIC/MOTS)
- Tactic type (AOW...)
- Tactic skill
- The "defend lead" minute
- the real HT ratings for each area (midfield, left defense, central defense, ...)
- For each player in lineup:
  - form
  - xp
  - stamina
  - his skills (playmaking, passing...)
  - specialty
  - behaviour (off/def)
  - stars
- HO Feedback Plugin version


Training
~~~~~~~~
For each usefull skillup, this data will be uploaded:

- Contributor ID (i.e. your team ID)
- HT season
- HT week
- Player ID
- Skill type (playmaking, passing, ...)
- new value of skill
- training length
- age
- avg number of assistants
- avg trainer type
- number and kind of trainings he received (short passes, ...)
- HO Feedback Plugin version


Transfers
~~~~~~~~~
For each usefull transfer, this data will be uploaded:

- Contributor ID (i.e. your team ID)
- Transfer ID
- age
- form
- injury level
- xp
- leadership
- tsi
- stamina
- his skills (playmaking, passing, ...)
- specialty
- aggressivity
- honesty
- popularity
- transfer price
- HT season
- HT week
- transfer date
- country
- your league level
- HO Feedback Plugin version

