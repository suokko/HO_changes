#!/bin/bash
echo "Warning, this will overwrite all non-english language files!"
echo
cat languages
echo
echo "Press ENTER to start or CTRL-C to abort"
echo
read
cat languages | grep -v English | xargs -n1 -IXXXX sed -e s#English#XXXX## English.nsh -e wXXXX.nsh > /dev/null
