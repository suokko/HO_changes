#!/bin/sh
#
# Start script for Hattrick Organizer v0.331
# Originally created by patta, RAGtime and others
# Last Change (2008-06-16) by Flattermann (flattermannHO@gmail.com)
# 
# List of changes: 
#
#	0.33    - don't copy rating.dat anymore (not needed for HO>=1.400)
#	0.32    - optional separate configuration file
#	0.31    - configurable java memory (-m or $MAX_MEMORY)
#	0.3	- Introduce the bourne shell as standard shell
#		- Make HO! multi-user capable
#		- Several layout changes to the script
#		- check database before doing backup
#		- quit if java version is not returned (NO Sun java?)    
#

########################################################################
#                                                                      #
#               User editable settings                                 #
#                                                                      #
#  To use HO! with multiple users on one system:                       #
#  - create a new directory, e.g. /usr/local/share/hattrickorganizer   #
#     or /opt/ho and unpack the HO archive there!                      #
#                                                                      #
#  - copy HO.sh to a directory in users $PATH, e.g.                    #
#     cp HO.sh /usr/local/bin/ho                                       #
#                                                                      #
#  - optionally you may create a HO.config file there and edit         #
#    this instead of HO.sh                                             #
#                                                                      #
#  - edit the HODIR variable to this new directory (without the ``)    #
#     and HOHOME to any user writable directory!                       #
#                                                                      #
#  - start HO! out of a terminal via e.g. 'ho'		               #
#                                                                      #
#  - manage your team :)                                               #
#                                                                      #
#                                                                      #
#  Alternatively, as single user just start HO! from the HO directory  #
#  via ./HO.sh without editing something!                              # 
#                                                                      #
########################################################################
# OPTIONAL user configuration file which may overwrite some
# of the following variables later if it exists in HO.sh-directory.
HOCONF=HO.config

#
### BEGIN of default user configuration! ###
#

# Enter HO!'s directory. Default is the current directory.
# In multi user mode this can be any directory (full path!).
#
#HODIR=`pwd`

HODIR=`pwd`

# Next comes HO!'s directory to store its user data.
# This directory must be writable by the user,
# default is install directory (single user mode).
#
# SINGLE USER:
#
#HOHOME=$HODIR
#
# MULTI USER:
# 
#HOHOME=~/.hattrickorganizer

HOHOME=${HODIR}

# Where can I find java?
# Default is just looking at $PATH
# (remember option -j)
#JAVA=`which java`

JAVA=`which java`

# Enter the maximum amount of backups you do want to store.
# Default is 5
#
#MAX_BACKUPS=5

MAX_BACKUPS=5

# Enter the maximum amount of memory available to the java VM.
# Default is 256 MegaBytes!
#
#MAX_MEMORY=256m

MAX_MEMORY=256m

#
### END of default user configuration! ###
#

# Read additional user configuration variables from config file and
# overwrite the defaults above if it is found in HO.sh-directory !!!
HOSHDIR="$(dirname "$0")"
if [ -f "$HOSHDIR/$HOCONF" ]
then
# THIS FILE IS NOT PART OF HO PACKAGE AND WON'T BE OVERWRITTEN! 
# SO YOU HAVE TO CREATE IT BY YOUR OWN BY COPYING THE LINES ABOVE 
# BETWEEN "### BEGIN" AND "### END" THAT YOU WANT TO CHANGE!!!
   echo "Reading HO configuration from user file $HOSHDIR/$HOCONF!"
   source "$HOSHDIR/$HOCONF"
fi

########################################################################
#        DO NOT EDIT ANY MORE UNTIL YOU KNOW WHAT YOU'RE DOING!        #
########################################################################

# How do you call me?
#

HONAME=$0

# Enter the directory where the database is stored
#        
#DATABASEDIR=$HOHOME/db

DATABASEDIR=$HOHOME/db

# Enter the default backup-directory. It will be created
# in $HOHOME/db if it doesn't exist. Default is 'backup'.
#           
#BACKUPDIR=$HOHOME/db/backup 

BACKUPDIR=$HOHOME/db/backup

# Enter the directory where the HO! plugins reside
#
#PLUGINSDIR=$HOHOME/hoplugins

PLUGINSDIR=$HOHOME/hoplugins

# Enter the directory where the language files are
#
#SPRACHDIR=$HOHOME/sprache

SPRACHDIR=$HOHOME/sprache

            
# required java version

JAVAVERREQ=1.4.1

# Which database files and name of backup file?

BACKUPLIST="database.data database.script database.backup database.properties"
PREFIX=database

# We need a date for the backup-file

DATE=`date "+%Y-%m-%d"`

# Check for javaversion by default?

CHECK=true

# Perform NO backup by default (because HO does this now internally!
# For old behavoir edit or start with option --backup/-b)

BACKUP=false

# Restore by default? 
# IMPORTANT! This is just for initialisation!
# If you set this to 'true' strange things will occure!

RESTORE=false

# check at least top install directory

if [ ! -d "${HODIR}" ]
then
  echo "INSTALL DIRECTORY NOT FOUND: ${HODIR}" >&2
  exit 4
fi

# Which version of HO! is this?

HOVERSION=`cat "${HODIR}"/version.txt`

# Output the help

help(){
	cat <<-EOF >&2
		Usage: $HONAME [option]

	Options:
          -h  --help          This help text
          -v  --version       Show HO! version
          -f  --force         Start without checking java-version
          -j  --java <path>   Use this java
          -m  --memory <mem>  Max. size of memory allocation pool for java
          -b  --backup        Do a database backup before HO starts
          -nb --nobackup      Start without backup (by script)
          -r  --restore       Restore the last backup-file
          -rd --restoredate   <date>
                              Restore the backup-file from <date>
          -bd --backupdir     Use this as the backup directory
	EOF
	exit 1
}

# Start HO!

start(){
	cd "${HOHOME}"
        echo "Starting HO from ${HOHOME}..."  

        $JAVA -classpath "${HODIR}" HOLauncher  

  	$JAVA -Xmx$MAX_MEMORY -jar "${HODIR}/ho.jar"

        # check database and print warning
	if [ `grep modified "${DATABASEDIR}/database.properties" | \
			cut -d= -f2` = "no" ] 
	then
		echo "Database OK!"      
	else
		cat <<-EOF >&2
			Database was not relased correctly!
			Probably next time you will have problems starting HO...
			... but you can restore a backup with switches -r or -rd. :-)
		EOF
	fi 
}

# Backup 

backup(){
	# Create the backupdir if there's none
  	if [ ! -d "${BACKUPDIR}" ] 
	then
    	echo "Creating ${BACKUPDIR}" 
	    mkdir -p ${BACKUPDIR}
  	fi
  	cd $BACKUPDIR
	# Delete too old backup files
  	while [ "`ls -r | wc -l`" -gt $MAX_BACKUPS ]
	do
            rm -f `ls -r | tail -n 1`
  	done
  	cd ${DATABASEDIR}
	# THE BIG TRICK: ls gives false (status>0) if one of the files is missing!!! ;-)  
  	if ls ${BACKUPLIST} &> /dev/null
	then
	    # is database OK?    
            if [ `grep modified database.properties | cut -d= -f2` = "no" ]  
	    then
		# f - is needed in case someone has set his $TAPE variable...
		tar -cf - $BACKUPLIST | gzip > $BACKUPDIR/$PREFIX-$DATE.tgz
		# ...and this is shorter, but won't work if there's no GNU tar! :-(
		# tar -czf $BACKUPDIR/$PREFIX-$DATE.tgz $BACKUPLIST  
	    else
		cat <<-EOF >&2
			OLD database was not relased correctly! I will do no backup
			BTW,... if you have problems starting HO, try switches -r or -rd 
		EOF
	    fi
        else
	    echo "Database files not found, so there is nothing to backup."
        fi    
}

# Restore

restore(){
	cd $DATABASEDIR
	if [ -z $RESTOREDATE ]
	then
		if ls $BACKUPDIR/$PREFIX*.tgz &> /dev/null
		then
		    gunzip -c `ls $BACKUPDIR/$PREFIX*.tgz | tail -n 1` | tar -xf -
		else
		    echo "No backup file(s) found!" >&2
		    exit 2
		fi
	else
		if [ -r "$BACKUPDIR/$PREFIX-$RESTOREDATE.tgz" ]
		then
			gunzip -c "$BACKUPDIR/$PREFIX-$RESTOREDATE.tgz" | tar -xf - 
			echo "Restored database from $BACKUPDIR/$PREFIX-$RESTOREDATE.tgz"
		else
			echo "Error reading backup file $BACKUPDIR/$PREFIX-$RESTOREDATE.tgz!" >&2
			exit 2
		fi
	fi
}

# Check java -version

checkjava(){
	test -x "$JAVA" || { echo "Can't find java!" ; exit 1 ; }
	JAVAVER=`$JAVA -version 2>&1 | head -n 1 | \
	         awk -F\" '{print $2}' | sed s/[^0-9\.].*//g`
	JAVAMAJ=`echo $JAVAVER | awk -F. '{print $1}'`
	JAVAMIN=`echo $JAVAVER | awk -F. '{print $2}'`
	JAVAMINMIN=`echo $JAVAVER | awk -F. '{print $3}'`
	JAVAMAJREQ=`echo $JAVAVERREQ | awk -F. '{print $1}'`
	JAVAMINREQ=`echo $JAVAVERREQ | awk -F. '{print $2}'`
	JAVAMINMINREQ=`echo $JAVAVERREQ | awk -F. '{print $3}'`
        if ( [ $JAVAMAJ = "" ] || [ $JAVAMIN = "" ] \
				|| [ $JAVAMINMIN = "" ] ) ; then
          echo -e "Couldn't check java version! \n
		  Try '$HONAME -f' to override the version check" >&2
          exit 3
        fi
	if ( [ $JAVAMAJ -lt $JAVAMAJREQ ] || \
	    ( [ $JAVAMAJ -eq $JAVAMAJREQ ] && \
		[ $JAVAMIN -lt $JAVAMINREQ ] ) || \
	    ( [ $JAVAMAJ -eq $JAVAMAJREQ ] && \
		[ $JAVAMIN -eq $JAVAMINREQ ] && \
		[ $JAVAMINMIN -lt $JAVAMINMINREQ ] ) ) 
	then
        cat <<-EOF >&2
		  	The default Java version is too old!
	        You could try another one: '$HONAME -j <path>'
		EOF
		exit 3
	fi
}

### MAIN

# Run through the params

until [ -z "$1" ]
do
	case "$1" in
		-j|--java)   
			JAVA=$2;
			if [ -z $JAVA ]
			then
				echo "Option j or java needs a parameter!" >&2
				exit 1
			fi
			shift
			;;
		-m|--memory)   
			MAX_MEMORY=$2;
			if [ -z $MAX_MEMORY ]
			then
				echo "Option m or memory needs a parameter!" >&2
				exit 1
			fi
			shift
			;;
		-v|--version)
			echo "This is HO! version $HOVERSION";
			exit 0
			;;
		-nb|--nobackup)
			BACKUP=false
			;;
		-b|--backup)
			BACKUP=true
			;;
		-f|--force)
			CHECK=false
			;;
		-h|--help)
			help
			;;
		-bd|--backupdir)
			BACKUPDIR=$2
			shift
			;;
		-r|--restore)
			RESTORE=true;
			BACKUP=false
			;;
		-rd|--restoredate)
			RESTORE=true
			RESTOREDATE=$2
			if [ -z $RESTOREDATE ]
			then
				echo "Option rd or restoredate needs a date as parameter!" >&2
				if ls $BACKUPDIR/$PREFIX-*.tgz &> /dev/null
				then
 				    echo -e "Available backups: \n`ls -1 $BACKUPDIR/$PREFIX-*.tgz`" >&2
				else
                                    echo "Sorry, there's no Backup available!!!" >&2
				fi    
				exit 1
			fi
			BACKUP=false;
			shift
			;;
		*)
			echo -e "Unknown parameter $1\n
			Try $HONAME --help to get help." >&2
			exit 1
			;;
	esac
	shift
done


# Check for java -version (if called without `force')


`$CHECK` && checkjava

# Check if all needed directories exist

if [ ! -d "${HOHOME}" ]
then
	echo "creating ${HOHOME}"
	mkdir ${HOHOME}
fi

if [ ! -d "${PLUGINSDIR}" ]
then
        if [ ! -d "${HODIR}"/hoplugins ]
        then
          echo "creating ${HODIR}/hoplugins"
          mkdir ${HOHOME}/hoplugins
        else
  	  echo "copying $PLUGINSDIR"
	  cp -r ${HODIR}/hoplugins $HOHOME
        fi
fi

if [ ! -d "${SPRACHDIR}" ]
then
	echo "copying ${SPRACHDIR}"
	cp -r ${HODIR}/sprache ${HOHOME}	
fi

# copy needed parameter files if not already there

#if [ ! -e "${HOHOME}"/ratings.dat ]
#then
#   cp ${HODIR}/ratings.dat ${HOHOME}/ratings.dat
#fi
if [ ! -e "${HOHOME}"/epv.dat ]
then
   cp ${HODIR}/epv.dat ${HOHOME}/epv.dat
fi


# Perform backups or restore only if $DATABASEDIR exists

if [ -d "${DATABASEDIR}" ]
then
	`$BACKUP`  && backup
	`$RESTORE` && restore
else
	echo -e "No directory ${DATABASEDIR} found. \n This is your first HO!-Session, isn't it? Have fun!"
fi

# Start HO! (or the Launcher)

start

# Exit this script

exit 0
