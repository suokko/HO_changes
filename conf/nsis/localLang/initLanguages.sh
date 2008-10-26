#!/bin/bash
echo "Warning, this will overwrite all non-english language files!"
echo
cat languages
echo
echo "Press ENTER to start or CTRL-C to abort"
echo
read
TEMPFILE=`mktemp`
for curLang in `cat languages`; do
	echo "Creating language template for ${curLang}"
	cat Skeleton.txt | recode utf16le..utf8 | sed -e s#Skeleton#${curLang}## | recode utf8..utf16le > ${curLang}.txt
done
