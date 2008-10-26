#!/bin/bash
if [ "x$2" == "x" ]; then
	echo "Usage: addString <STRINGNAME> <DEFAULTTEXT>"
	exit 1
fi
fitStringLength=25
TMPFILE=`mktemp`
for curLang in `cat languages`; do
	if [ -e ${curLang}.txt ]; then
		echo "Adding string to ${curLang}"
		stringLength=${#1}
		addSpaces=`expr ${fitStringLength} - ${stringLength}`
		cat ${curLang}.txt | recode utf16le..utf8 > ${TMPFILE}
		echo -e -n '!'"insertmacro LANG_STRING" >> ${TMPFILE}
		echo -n " $1 " >> ${TMPFILE}
		while [ ${addSpaces} -gt 0 ]; do
			echo -n " " >> ${TMPFILE}
			addSpaces=`expr ${addSpaces} - 1`
		done
		echo -e "\"$2\"\r"  >> ${TMPFILE}
		recode utf8..utf16le ${TMPFILE}
		mv ${TMPFILE} ${curLang}.txt
	else
		echo "${curLang}.txt not found, skipping"
	fi
done
